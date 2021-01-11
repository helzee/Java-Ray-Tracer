package renderer;

import mathematics.Vec3;

public class Light {

    public Vec3 position;
    public Color color;
    public double sizeScaler;

    public Light(Vec3 position, Color color, double sizeScaler)
    {
        this.position = position;
        this.color = color;
        this.sizeScaler = sizeScaler;
    }
}
