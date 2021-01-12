package renderer;

import mathematics.Functions;
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
        this.FOV = Functions.clamp(FOV, Functions.asRadians(0.001), Functions.asRadians(89.99));
        this.aspectRatio = screenRatio;
        this.upGuide = upGuide.get_normalized();

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

    public void rotate(double yaw, double pitch) {
        // rotate the cameras target according to movement offsets in the screen.
        // first, translate the 2D coordinates of the screen offsets to 3D coordinates relative to camera;
        Vec3 mouseTranslation3D = this.right.mult(yaw).add(this.up.mult(pitch));

        // we need the direction vector local to the cameras origin instead of the world origin point, to make the
        // calculation simpler
        Vec3 localDirection = this.target.sub(this.origin);

        // the relative new target will be the same distance from the last target to the new target, except it has
        // been moved by the mouse translation.
        Vec3 relativeNewTarget = mouseTranslation3D.add(this.forward);

        // make the relation between the lengths the same
        Vec3 potentialTarget = this.origin.add(relativeNewTarget.mult(localDirection.length() / relativeNewTarget.length()));

        // if the pitch is too big, the forward vector may move behind the cameras origin. This will cause the camera to
        // flip, making to forward vector point the other way around. After which, if the player continues rotating down
        // the forward vector will once again end up behind the origin. To solve this, limit the camera's new target to
        // never be too out of the cameras origin.
        if (!Functions.inRange(this.origin.y - potentialTarget.y, -7, 7))
            potentialTarget = this.target;

        // once the new target is guaranteed to be in range, apply the changes.
        this.target = potentialTarget;

        // lastly, since we changed the target, we must update our guiding vectors.
        updateSpecificationVectors();
    }

    public void translate(Vec3 translationVector) {
        Vec3 movementVector = this.forward.mult(translationVector.x).add(this.up.mult(translationVector.y)).add(this.right.mult(translationVector.z));
        this.origin = this.origin.add(movementVector);
        this.target = this.target.add(movementVector);
        updateSpecificationVectors();
    }

    public Vec3 getUpGuide() { return this.upGuide; }

    public Vec3 getOrigin() { return this.origin; }

    public Vec3 getTarget(){ return this.target; }

    private void updateSpecificationVectors() {
        this.forward = this.target.sub(this.origin).get_normalized();
        this.right = this.upGuide.cross(this.forward).get_normalized();
        this.up = this.right.cross(this.forward);
    }

    public Ray makeRay(double x, double y) {
        // Generates a new ray when a point in the screen is given.
        // x and y must be between +-width and +-height, respectively
        Vec3 direction = this.forward.add(this.right.mult(this.width * x)).add(this.up.mult(this.height * y));

        return new Ray(this.origin, direction.get_normalized());
    }
}
