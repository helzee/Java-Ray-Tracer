package renderer;

import mathematics.Functions;
import mathematics.Vec3;
import shapes.Shape;

public class Color {

    private double r, g, b;

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(double gray) {
        // grayscale
        this.r = gray;
        this.g = gray;
        this.b = gray;
    }

    public double getRed() {
        return r;
    }

    public double getGreen() {
        return g;
    }

    public double getBlue() {
        return b;
    }

    public void add(Color c) {
        this.r += c.r;
        this.g += c.g;
        this.b += c.b;
    }

    public void add(double scalar) {
        this.r += scalar;
        this.g += scalar;
        this.b += scalar;
    }

    public void mult(Color c) {
        this.r *= c.r;
        this.g *= c.g;
        this.b *= c.b;
    }

    public void mult(double scalar) {
        this.r *= scalar;
        this.g *= scalar;
        this.b *= scalar;
    }

    public void clamp() {
        this.r = Functions.clamp(this.r, 0, 1);
        this.g = Functions.clamp(this.g, 0, 1);
        this.b = Functions.clamp(this.b, 0, 1);
    }

    public Color clone() {
        return new Color(this.r, this.g, this.b);
    }

    private double lerp(double v0, double v1, double t) {
        return (1 - t) * v0 + t * v1;
    }

    public void linerInterpolation(Color c, double factor) {
        // interpolate a new color based on the factor given and the other color to combine.
        this.r = lerp(this.r, c.r, factor);
        this.g = lerp(this.g, c.g, factor);
        this.b = lerp(this.b, c.b, factor);

        //this.clamp();
    }

    public java.awt.Color convert() {
        this.clamp();
        return new java.awt.Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static Color convert(java.awt.Color c) {
        return new Color((double) c.getRed() / 255, (double) c.getGreen() / 255, (double) c.getBlue() / 255);
    }

    public String toString(){
        return "(" + this.r + ", " + this.g + ", " + this.b + ")";
    }
}
