package shapes;

import mathematics.Vec3;
import renderer.Intersection;
import renderer.Ray;
import renderer.Color;

import java.awt.*;

public class Sphere extends Shape {

    private double radius;

    public Sphere(Vec3 p, double r, VisualProperty visualProperty) {
        super(p, visualProperty);
        this.radius = r;
    }

    @Override
    public double getIntersection(Ray r, Intersection intersection) {
        /* for finding a ray and sphere intersection, the derived formula is a quadratic formula, since the ray can
        intersect at two, one, or zero points. were only interested in the closest one;
        the sphere is also localized, to cancel out the translation factors and make the formula a little easier to compute.

        the formula is:
        a = length(ray dir)^2
        b = 2 * ray pos DOT ray dir
        c = length(ray pos)^2 - rad^2
        */

        Ray local = r.clone();
        // translate ray to be in point of origin
        local.origin = local.origin.sub(this.pos);

        // using the formula, get a, b, and c
        double a = local.direction.length() * local.direction.length();
        double b = 2 * local.origin.dot(local.direction);
        double c = (local.origin.length() * local.origin.length()) - (this.radius * this.radius);

        // to find if an intersection even exists, we just need the discriminant; since its in the square root, if its
        // negative the function is undefined, and thus there are no common points between the ray and the sphere.
        double discriminant = (b * b) - (4 * a * c);

        // if the discriminate is negative, it has no square root and thus no solution. in that case, there is no intersection
        if (discriminant < 0)
            return Double.POSITIVE_INFINITY;

        discriminant = Math.sqrt(discriminant);

        // there is at least one point of intersection. find it
        double p1 = (-b - discriminant) / (2 * a);
        double p2 = (-b + discriminant) / (2 * a);

        // were only interested in the closest point of intersection, therefore, we only need to return the smallest one.
        if (p1 > 0.0001 && p1 < p2 && p1 < intersection.pointOfIntersection)
            return p1;
        if (p2 > 0.0001 && p2 < p1 && p2 < intersection.pointOfIntersection)
            return p2;
        return Double.POSITIVE_INFINITY;

    }

    @Override
    public boolean intersects(Ray r, Intersection intersection) {
        // using the same method used to find a point of intersection, we can simply check if the emergant quadratic
        // formula's discriminant is negative. if it is, the function is undefined and therefore there will be no point
        // of intersection. if it isn't, there will be at least one.

        Ray local = r.clone();
        // translate ray to be in point of origin
        local.origin = local.origin.sub(this.pos);

        // using the formula, get a, b, and c
        double a = local.direction.length() * local.direction.length();
        double b = 2 * local.origin.dot(local.direction);
        double c = (local.origin.length() * local.origin.length()) - (this.radius * this.radius);

        // to find if an intersection even exists, we just need the discriminant
        double discriminant = (b * b) - (4 * a * c);

        if (discriminant < 0)
            return false;

        discriminant = Math.sqrt(discriminant);

        double p1 = (-b - discriminant) / (2 * a);
        double p2 = (-b + discriminant) / (2 * a);

        if ((p1 > 0.0001 || p2 > 0.0001) && (p1 < intersection.pointOfIntersection || p2 < intersection.pointOfIntersection))
            return true;
        return false;
    }

    @Override
    public Vec3 getNormal(Vec3 pos) {
        // the normal of a unit sphere at point p is just p. just account for displacement and radius
        return pos.sub(this.pos).div(this.radius).get_normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sphere)
            if (this.pos == ((Sphere) o).pos && this.radius == ((Sphere) o).radius)
                return true;
        return false;
    }

    @Override
    public Color getTextureColor(Vec3 p) {
        // courtesy of the Wikipedia UV Mapping article
        // default values constructor
        double u, v;

        // localize sphere
        Vec3 localPoint = p.sub(this.pos).get_normalized();

        // get u, v as x, y coordinates
        u = 0.5 + Math.atan2(localPoint.x, localPoint.z) / (2 * Math.PI);
        v = 0.5 - Math.asin(localPoint.y) / Math.PI;

        // get u, v as x, y coordinates on the picture
        u *= this.visualProperty.texture.getWidth();
        v *= this.visualProperty.texture.getHeight();

        // convert the color value in the image
        return Color.convert(new java.awt.Color(this.visualProperty.texture.getRGB((int) u, (int) v), true));
    }
}
