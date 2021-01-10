package mathematics;

public class Vec3 {

    public double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 v) {
        return new Vec3(v.x + this.x, v.y + this.y, v.z + this.z);
    }

    public Vec3 sub(Vec3 v) {
        return new Vec3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vec3 mult(double num) {
        return new Vec3(num * this.x, num * this.y, num * this.z);
    }

    public Vec3 div(double num) {
        return new Vec3(this.x / num, this.y / num, this.z / num);
    }

    public Vec3 mult(Vec3 v) {
        return new Vec3(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    public Vec3 div(Vec3 v) {
        return new Vec3(this.x / v.x, this.y / v.y, this.z / v.z);
    }

    public double dot(Vec3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vec3 cross(Vec3 v) {
        return new Vec3(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * y + this.z * this.z);
    }

    public Vec3 get_normalized() {
        double len = this.length();
        return new Vec3(this.x / len, this.y / len, this.z / len);
    }

    public void normalize() {
        double len = this.length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
    }

    public Vec3 clone() {
        return new Vec3(this.x, this.y, this.z);
    }

    public boolean equals(Object o) {
        if (o instanceof Vec3)
            if (this.x == ((Vec3) o).x && this.y == ((Vec3) o).y && this.z == ((Vec3) o).z)
                return true;
        return false;
    }

    public String toString() {
        return "{" + this.x + ", " + this.y + ", " + this.z + "}";
    }
}
