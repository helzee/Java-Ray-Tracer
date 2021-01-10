import gui.ImageFactory;
import gui.View;
import mathematics.Node;
import mathematics.Vec3;
import renderer.Light;
import renderer.Ray;
import renderer.Camera;
import renderer.Color;
import shapes.*;
import sun.text.resources.ro.CollationData_ro;

import java.io.IOException;

public class main {

    public static int width = 1920, height = 1080;

    public static void main(String[] args) {
        new View();
        // initialize default parameters and driver code
        // upguide, target, origin
        /*Camera camera = new Camera(25 * Math.PI / 180, (double) width / height,
                                    new Vec3(0, 1, 0), new Vec3(0,1,0), new Vec3(-5,0,1));

        Scene scene = new Scene(.1, 1, camera,
                //new Plane(new Vec3(0, 1, 0), new Vec3(0, 1, 0), new Color(0, 0, 1), .1, .4),
                new Sphere(new Vec3(-2,.5,0), 1, new Color(0,1,0), .1, .8),
                new Sphere(new Vec3(-3, 0, 1), 1, new Color(.5, 1, .9), .1,.2),
                //new Sphere(new Vec3(0, -1, 2), .1, new Color(1), .1, .4),
                new Triangle(new Vec3(0,2,1), new Vec3(0,.5,0), new Vec3(2,.3,1), new Color(1, 0,0), .1,.4)
        );

        scene.addLights(new Light(new Vec3(1, 0, 2)));

        ImageFactory.width = width;
        ImageFactory.height = height;
        ImageFactory.start();

        // begin raytracing
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // first, get x and y where -width < x < width and -height < y < height
                double currentX = (2 * (double) x / width) - 1, currentY = (2 * (double) y / height) - 1;

                // make the camera generate a ray
                Ray ray = camera.makeRay(currentX, currentY);

                // pass that ray to the scene, and get the emergant pixel color
                //System.out.print((int) scene.castRay(ray).getRed());
                ImageFactory.setPixel(x, y, scene.castRay(ray));

                // draw the pixel color
            }

            // System.out.println();
        }
        try {
            ImageFactory.create();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
