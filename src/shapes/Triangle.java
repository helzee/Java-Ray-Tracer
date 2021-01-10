package shapes;

import mathematics.Vec3;
import renderer.Intersection;
import renderer.Ray;
import renderer.Color;

public class Triangle extends Shape {

    private Vec3 pos2, pos3;

    public Triangle(Vec3 pos, Vec3 pos2, Vec3 pos3, Color c, double emission, double reflectivity) {
        super(pos, c, emission, reflectivity);
        this.pos2 = pos2;
        this.pos3 = pos3;
    }

    @Override
    public double getIntersection(Ray r, Intersection intersection) {
        // Möller–Trumbore intersection algorithm

        Vec3 edge1 = this.pos2.sub(this.pos), edge2 = this.pos3.sub(this.pos), h = r.direction.cross(edge2);
        double a = edge1.dot(h);

        if (a == 0)
            return Double.POSITIVE_INFINITY;

        Vec3 s = r.origin.sub(this.pos);
        double f = 1 / a, u = f * (s.dot(h));

        if (u < 0.0 || u > 1.0)
            return Double.POSITIVE_INFINITY;

        Vec3 q = s.cross(edge1);
        double v = f * r.direction.dot(q);

        if (v < 0.0 || u + v > 1.0)
            return Double.POSITIVE_INFINITY;

        double t = f * edge2.dot(q);
        if (t > 0.0001 && t < intersection.pointOfIntersection)
            return t;
        return Double.POSITIVE_INFINITY;

    }

    @Override
    public boolean intersects(Ray r, Intersection intersection) {
        // theres no point in redoing the same algorith again. if the ray intersects at any point that isn't infinity,
        // then it must intersect somewhere. unfortunatly not optimaizations can be done

        return getIntersection(r, intersection) != Double.POSITIVE_INFINITY;
    }

    @Override
    public Vec3 getNormal(Vec3 pos) {
        // the normal of a triangle is the vector cross product of two edges of that triangle
        Vec3 v = this.pos2.sub(this.pos), w = this.pos3.sub(this.pos);

        return v.cross(w).get_normalized().mult(-1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Triangle)
            if (this.pos == ((Triangle) o).pos && this.pos2 == ((Triangle) o).pos2 && this.pos3 == ((Triangle) o).pos3)
                return true;
        return false;
    }
}
