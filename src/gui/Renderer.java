package gui;

import renderer.Ray;
import shapes.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {

    public static final int FAST_MODE = 3;
    public static final int IMAGE_MODE = 2;
    public static final int FULL_RENDER_MODE = 1;

    public static int RENDERING_MODE;

    public static void render(Graphics g, Scene context) {
        for (int y = 0; y < Driver.HEIGHT; y += RENDERING_MODE) {
            for (int x = 0; x < Driver.WIDTH; x += RENDERING_MODE) {
                // first, get x and y where -width < x < width and -height < y < height, and the values are scaled in
                // range of -1 to 1.
                double currentX = (2 * (double) x / Driver.WIDTH) - 1, currentY = (2 * (double) y / Driver.HEIGHT) - 1;

                // make the camera generate a ray from the screen coordinates we just gathered
                Ray ray = context.camera.makeRay(currentX, currentY);

                // pass that ray to the scene, and get the emergant pixel color
                renderer.Color nativeColor = context.castRay(ray);

                // convert the color from the native color scheme to the java color object
                g.setColor(nativeColor.convert());

                // in case well need to turn this frame into an image, copy the pixel to the image factory as well
                // we only need to do this if the rendering mode is set to image mode or full render mode
                if (RENDERING_MODE < 3)
                    ImageFactory.setPixel(x, y, nativeColor);

                // draw the pixel
                // this causes a lot of drawing overhead, but after some testing it seems like the best option.
                g.drawRect(x - RENDERING_MODE - 1, y - RENDERING_MODE - 1, RENDERING_MODE + 1, RENDERING_MODE + 1);
            }
        }

        // draw the fps count
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g.drawString("Frames: " + Driver.frames, 0,10);
    }
}
