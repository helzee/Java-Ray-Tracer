package renderer;

import mathematics.Vec3;

public class Light {

    public Vec3 position;
    public Color color;

    public Light(Vec3 position, Color color)
    {
        this.position = position;
        this.color = color;
    }
}
