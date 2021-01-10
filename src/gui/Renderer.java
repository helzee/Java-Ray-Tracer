package gui;

import renderer.Ray;
import shapes.Scene;

import java.awt.*;

public class Renderer {

    public static void render(Graphics g, Scene context) {
        for (int y = 0; y < View.HEIGHT; y+=2) {
            for (int x = 0; x < View.WIDTH; x+=2) {
                // first, get x and y where -width < x < width and -height < y < height
                double currentX = (2 * (double) x / View.WIDTH) - 1, currentY = (2 * (double) y / View.HEIGHT) - 1;

                // make the camera generate a ray
                Ray ray = context.camera.makeRay(currentX, currentY);

                // pass that ray to the scene, and get the emergant pixel color
                //System.out.print((int) scene.castRay(ray).getRed());
                renderer.Color nativeColor = context.castRay(ray);
                g.setColor(new Color((int) (255 * nativeColor.getRed()), (int) (255 * nativeColor.getGreen()), (int) (255 * nativeColor.getBlue())));
                g.drawRect(x, y, 1, 1);

                // draw the pixel color
            }

            // System.out.println();
        }
    }
}
