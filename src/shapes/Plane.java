package shapes;

import mathematics.Vec3;
import renderer.Intersection;
import renderer.Ray;
import renderer.Color;

public class Plane extends Shape {

    private Vec3 norm;

    public Plane(Vec3 p, Vec3 n, VisualProperty visualProperty) {
        super(p, visualProperty);
        this.norm = n.get_normalized();
    }

    @Override
    public double getIntersection(Ray r, Intersection intersection) {
        // point of intersection = ray pos DOT plane norm - plane pos DOT plane norm / ray dir DOT plane norm
        // denominator would determine definition space, so its advantageous to calculate it first and see what happens.
        double dirDotNorm = r.direction.dot(this.norm);

        if (dirDotNorm == 0)
            // assume ray cannot be intersecting with all points within the plane
            return Double.POSITIVE_INFINITY;


        // point of intersection within ray vector
        double point = this.pos.sub(r.origin).dot(this.norm) / dirDotNorm;

        if (point <= 0.0001 || point >= intersection.pointOfIntersection)
            return Double.POSITIVE_INFINITY;
        return point;

    }

    @Override
    public boolean intersects(Ray r, Intersection intersection) {
        // in case the denominator is equal to zero in the point of intersection formula for a plane, there will be
        // no intersection. else, there will be
        double dirDotNorm = r.direction.dot(this.norm);

        if (dirDotNorm == 0)
            // assume ray cannot be intersecting with all points within the plane
            return false;

        double point = this.pos.sub(r.origin).dot(this.norm) / dirDotNorm;

        if (point <= 0.0001 || point >= intersection.pointOfIntersection)
            return false;
        return true;
    }

    @Override
    public Vec3 getNormal(Vec3 pos) {
        // the normal of a plane is a part of its definition
        return this.norm;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Plane)
            if (this.pos == ((Plane) o).pos && this.norm == ((Plane) o).norm)
                return true;
        return false;
    }

    @Override
    public Color getTextureColor(Vec3 p) {
        // TODO
        return null;
    }
}
