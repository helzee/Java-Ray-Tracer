package renderer;

import mathematics.Vec3;
import shapes.Scene;
import shapes.Shape;

public class Intersection {

    public Double pointOfIntersection;
    public Shape shape;

    public Intersection() {
        this.pointOfIntersection = Double.POSITIVE_INFINITY;
        this.shape = null;
    }

    public Intersection(Double pointOfIntersection, Shape s) {
        this.pointOfIntersection = pointOfIntersection;
        this.shape = s;
    }

}
