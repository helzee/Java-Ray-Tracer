package shapes;

import mathematics.Node;
import mathematics.Vec3;
import renderer.*;

public class Scene {

    private Node<Shape> shapes;
    private Node<Light> lights;
    private Sphere sky;
    public Camera camera;

    public static boolean flag = false;

    final double GLOBAL_ILLUMINATION;
    final int RECURSION_DEPTH;

    public Scene(double globalIllumination, int recursionDepth, Camera camera, Sphere sky, Shape... shapes) {
        this.lights = new Node<Light>(null);
        this.shapes = new Node<Shape>(null);
        this.camera = camera;

        // global lighting variables
        this.GLOBAL_ILLUMINATION = globalIllumination;
        this.RECURSION_DEPTH = recursionDepth;

        this.sky = sky;

        for (Shape shape : shapes)
            this.shapes.append(shape);

        if (this.shapes.getValue() == null)
            this.shapes = this.shapes.getNext();

    }

    public void addShapes(Shape... shapes) {
        for (Shape s : shapes)
            this.shapes.append(s);

        if (this.shapes.getValue() == null)
            this.shapes = this.shapes.getNext();
    }

    public void addLights(Light... lights) {
        for (Light l : lights)
            this.lights.append(l);

        if (this.lights.getValue() == null)
            this.lights = this.lights.getNext();
    }

    public Color castRay(Ray r) {
        // first, find closest intersection of the ray with any object in the scene
        Intersection intersection = getIntersection(r);

        // then, find how illuminated the point should be
        if (intersection.isIntersecting)
            return getColor(intersection, r, this.RECURSION_DEPTH);
        return sky.getTextureColor(r.direction);

    }

    public boolean rayIntersects(Ray r, Shape sourceShape) {
        // this function is the same as castRay, except it only checks if the ray hits something or not.
        // this will speed up the testing time with most shapes, and thus speed up performance.
        Intersection intersection = getIntersection(r);

        if (intersection.isIntersecting && intersection.shape != sourceShape)
            return true;
        return false;
    }

    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection();

        for (int i = 0; i < this.shapes.length(); i++) {
            // we only need to find the closest point. we need to keep track of the closest weve gotten so far, and only
            // update the closest if we got any closer
            double intersectionPoint = this.shapes.get(i).getIntersection(ray, intersection);
            if (intersectionPoint < intersection.pointOfIntersection) {
                intersection = new Intersection(intersectionPoint, this.shapes.get(i));
            }
        }

        return intersection;
    }

    public Color getColor(Intersection intersection, Ray ray, int recursionLimit) {
        // get the color of this intersection and use it as the base ambient color
        Color result = calculateColor(intersection, ray);

        // if the shape is reflective, we can apply reflections.
        if (intersection.shape.visualProperty.isReflective) {
            // get the next intersection position
            Vec3 intersectionPoint = ray.calculate(intersection.pointOfIntersection);
            Vec3 norm = intersection.shape.getNormal(intersectionPoint);

            // thanks to carl-vbn for his solution of moving the ray slightly
            Vec3 reflectionVector = ray.direction.sub(norm.mult(2 * ray.direction.dot(norm)));
            Vec3 reflectionOrigin = intersectionPoint.add(reflectionVector.mult(.001));
            Ray reflectionRay = new Ray(reflectionOrigin, reflectionVector);

            double reflectivity = intersection.shape.visualProperty.reflectivity;

            if (recursionLimit > 0) {
                // if were under the reflection limit, calculate the next reflection
                Intersection reflectionIntersection = getIntersection(reflectionRay);
                if (reflectionIntersection.isIntersecting)
                    result.linerInterpolation(getColor(reflectionIntersection, reflectionRay, recursionLimit - 1), reflectivity);
                else
                    // if the reflection does not intersect with any other shape, it must intersect with the skybox.
                    // return the color of the sky box instead.
                    result.linerInterpolation(sky.getTextureColor(reflectionRay.direction), reflectivity);
            }
        }
        // if the shape is not reflective, its color will simply be its ambient color with shadows applied.

        // make sure the result is in the valid color range
        result.clamp();
        return result;
    }

    private Color calculateColor(Intersection intersection, Ray ray) {
        // this will shorten up a lot of future calls
        VisualProperty visualProperty = intersection.shape.visualProperty;
        // get the point of intersection in 3d space
        Vec3 intersectionAt3DSpace = ray.calculate(intersection.pointOfIntersection);

        // starting color with ambient lighting
        Color ambient = visualProperty.color.clone(), color = ambient.clone();

        // if the shape is textured, apply the texture first
        if (visualProperty.isTextured)
            // take the color and interpolate it with opposite respect to the reflectivity factor
            color.linerInterpolation(intersection.shape.getTextureColor(intersectionAt3DSpace), 1 - visualProperty.reflectivity);

        // TODO: only the last light renders
        // get illumination based on phong shading algorithm
        for (int i = 0; i < this.lights.length(); i++) {
            Light lightSource = this.lights.get(i);
            // light vector is a normalized vector pointing to the light source from point of intersection
            Vec3 lightVector = intersectionAt3DSpace.sub(lightSource.position).get_normalized();
            Vec3 intersectionPointToLight = lightSource.position.sub(intersectionAt3DSpace).get_normalized();
            // get normal from point of intersection
            Vec3 normal = intersection.shape.getNormal(intersectionAt3DSpace).get_normalized();
            double diffused = 0;

            // first, check if the light intersects with any object
            if (!rayIntersects(new Ray(lightSource.position, lightVector), intersection.shape))
                // if not, we can apply diffused shading

                // to get the illumination we need the dot product of the light vector and the normal vector. since,
                // the dot product will give: magnitude of normal * magnitude of light * cos the angle in between
                // since both the normal vector and the light vector are normalized, their magnitude is equal to one.
                // therefore, we get cos(the angle between them)
                diffused = intersectionPointToLight.dot(normal) * (lightSource.sizeScaler);

            // clamp the diffuse to make sure the global lighting stays consistent
            diffused = Math.max(this.GLOBAL_ILLUMINATION, Math.min(1, diffused));

            // secondly, apply specular brightness
            // camera direction relative to point of intersection
            Vec3 cameraDirection = this.camera.getOrigin().sub(intersectionAt3DSpace).get_normalized();
            Vec3 reflectionVector = lightVector.sub(normal.mult(2 * lightVector.dot(normal)));

            // clamp scalar
            double specularScalar = Math.max(0, Math.min(1, reflectionVector.dot(cameraDirection)));

            double specular = specularScalar * specularScalar * visualProperty.reflectivity * lightSource.sizeScaler;

            // now that we have both specular and diffused lighting, we can modify the color to suit.
            // apply diffused shading to color
            color.mult(diffused);
            // apply specular
            color.add(specular);
            // change color based on light source color
            color.mult(lightSource.color);
        }

        // once were done calculating the light level at a point, we can add the emission of the object
        ambient.mult(visualProperty.emission);
        // and its ambient color
        color.add(ambient);

        return color;
    }

}
