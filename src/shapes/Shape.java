// TODO: rewrite shapes with mathematical functions in mind, to hopefully optimize them.

package shapes;

import mathematics.Vec3;
import renderer.Intersection;
import renderer.Ray;
import renderer.Color;

public abstract class Shape {

    protected Vec3 pos;

    // ambient color
    protected Color color;
    protected double emission, reflectivity;

    public Shape(Vec3 pos, Color c, double emission, double reflectivity) {
        this.pos = pos;
        this.color = c;
        this.emission = emission;
        this.reflectivity = reflectivity;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract double getIntersection(Ray r, Intersection intersection);

    public abstract boolean intersects(Ray r, Intersection intersection);

    public abstract Vec3 getNormal(Vec3 pos);

    public abstract boolean equals(Object o);

}
