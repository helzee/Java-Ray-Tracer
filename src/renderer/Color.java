package renderer;

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
        this.r = Math.min(1, Math.max(this.r, 0));
        this.g = Math.min(1, Math.max(this.g, 0));
        this.b = Math.min(1, Math.max(this.b, 0));
    }

    public Color clone() {
        return new Color(this.r, this.g, this.b);
    }

    public String toString(){
        return "(" + this.r + ", " + this.g + ", " + this.b + ")";
    }
}
