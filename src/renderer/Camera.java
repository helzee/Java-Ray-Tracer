package renderer;

import mathematics.Vec3;

public class Camera {

    // input parameters of a camera
    private double FOV, aspectRatio;
    private Vec3 upGuide;

    // variables of a camera
    private Vec3 target, origin;

    // derived parameters
    private Vec3 forward, right, up;
    private double height, width;

    public Camera(double FOV, double screenRatio, Vec3 upGuide, Vec3 target, Vec3 origin) {
        this.FOV = FOV;
        this.aspectRatio = screenRatio;
        this.upGuide = upGuide;

        this.height = Math.tan(this.FOV);
        this.width = this.height * this.aspectRatio;

        this.target = target;
        this.origin = origin;

        updateSpecificationVectors();
    }

    public void setTarget(Vec3 target) {
        this.target = target;
        updateSpecificationVectors();
    }

    public void setOrigin(Vec3 origin) {
        this.origin = origin;
    }

    public void translate(Vec3 translationVector) {
        this.origin = this.origin.add(translationVector);
        this.target = this.target.add(translationVector);
        updateSpecificationVectors();
    }

    public Vec3 getUpGuide() { return this.upGuide; }

    public Vec3 getOrigin() { return this.origin; }

    public Vec3 getTarget(){ return this.target; }

    private void updateSpecificationVectors() {
        this.forward = this.target.sub(this.origin).get_normalized();
        this.right = this.forward.cross(this.upGuide).get_normalized();
        this.up = this.right.cross(this.forward);
    }

    public Ray makeRay(double x, double y) {
        // Generates a new ray when a point in the screen is given.
        // x and y must be between +-width and +-height, respectively
        // forward + (right * x * width) + (up * y * height)
        Vec3 direction = this.forward.add(this.right.mult(this.width * x)).add(this.up.mult(this.height * y));

        return new Ray(this.origin, direction.get_normalized());
    }
}
