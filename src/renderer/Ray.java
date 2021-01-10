package renderer;

import mathematics.Vec3;

public class Ray {

    public Vec3 origin;
    public Vec3 direction;

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction.get_normalized();
    }

    public Vec3 calculate(double d) {
        return this.origin.add(this.direction.mult(d));
    }

    public Ray clone() {
        return new Ray(origin, direction);
    }
}
