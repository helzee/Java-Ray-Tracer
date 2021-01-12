package shapes;

import mathematics.Vec3;
import renderer.Intersection;
import renderer.Ray;
import renderer.Color;

import java.awt.*;

public abstract class Shape {

    protected Vec3 pos;
    protected VisualProperty visualProperty;

    public Shape(Vec3 pos, VisualProperty visualProperty) {
        this.pos = pos;
        this.visualProperty = visualProperty;
    }

    public Color getColor() {
        return this.visualProperty.color;
    }
    public double getEmission() {
        return this.visualProperty.emission;
    }

    public abstract double getIntersection(Ray r, Intersection intersection);

    public abstract boolean intersects(Ray r, Intersection intersection);

    public abstract Vec3 getNormal(Vec3 pos);

    public abstract boolean equals(Object o);

    public abstract Color getTextureColor(Vec3 p);

}
