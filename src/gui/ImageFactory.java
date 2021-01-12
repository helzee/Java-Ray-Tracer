package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFactory {

    public static int width = Driver.WIDTH, height = Driver.HEIGHT;
    public static Color[][] pixels = new Color[width][height];

    public static void start() {
        for (int i = 0; i < pixels.length; i++)
            for (int j = 0; j < pixels[i].length; j++)
                pixels[i][j] = new Color(0, 0, 0);
    }

    public static void setPixel(int x, int y, renderer.Color c) {
        pixels[x][y] = new Color((int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() *255));
    }

    public static void create() throws IOException {
        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create a graphics which can be used to draw into the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();

        // fill all the image with black
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);

        // copy pixels to image
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                g2d.setColor(pixels[i][j]);
                g2d.drawRect(i, j, 1, 1);
            }

        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();

        // Save as PNG
        File file = new File("frame.png");
        ImageIO.write(bufferedImage, "png", file);
    }
}
