package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Driver extends Canvas implements Runnable {

    // window parameters and definition
    public static final int WIDTH = 1980/3, HEIGHT = 1080/3;
    public static int cenX = 0, cenY = 0, frames = 0;
    public static long lastTime = 0;
    public static Window window;
    public static Point2D.Double mousePosition = new Point2D.Double(0, 0);
    public static Robot robot;

    // request stop: will be flipped once the user attempts to leave the program.
    // waiting: will be flipped once the main thread has finished construction the scene instance.
    public static boolean requestStop = false, waiting = true, activeKeys[];

    // threading parameters
    private Thread thread;
    private boolean running = false;

    // this image will be used if the requested image cannot be found.
    public static final String defaultImage = "sky.png";

    public Driver() {
        // listen to mouse and keyboard events
        this.addMouseMotionListener(new MouseMovementInput());
        this.addKeyListener(new KeyInput());

        // enable centering the mouse
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
            requestStop = true;
        }

        activeKeys = new boolean[6]; // w, a, s, d, shift, space


        window = new Window(WIDTH, HEIGHT, "Ray Tracer", this);

        // calculate the center of the frame
        cenX = Driver.WIDTH / 2 + Window.frame.getX();
        cenY = Driver.HEIGHT / 2 + Window.frame.getY();

        // transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // set the blank cursor to the JFrame.
        this.setCursor(blankCursor);

        // allow view to define itself
        new View();

        // start tracking the screen to produce images in the future
        ImageFactory.start();

        // once initialization of the scene is done, we can begin rendering
        waiting = false;
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            running = false;
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        // time control loop
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (!waiting)
                    tick();
                delta--;
            }

            // only render if the scene is ready to be rendered. else, idle
            if (running && !waiting)
                render();

            // keep track of frames
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                Driver.frames = frames;
                frames = 0;
            }

        }

        // close window
        Window.frame.setVisible(false);
        Window.frame.dispose();

        // merge threads and leave
        stop();
    }

    private void tick() {
        // handle any event that should happen in a tick
        Handler.handle();

        // stop the program in case a stop is requested
        if (requestStop)
            this.running = false;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        View.render(g);

        g.dispose();
        bs.show();
    }

    public static BufferedImage loadImage(String path) {
        System.out.println("Attempting to load " + path + ".");

        try {
            BufferedImage image = ImageIO.read(new File(Driver.class.getResource(path).getPath()));
            System.out.println("Success.");
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Attempting to load placeholder image...");
            try {
                return ImageIO.read(new File(Driver.class.getResource(Driver.defaultImage).getPath()));
            } catch (IOException ioException) {
                System.out.println("Failed.");

                // if failed to load the placeholder image, than we may have un-loadable textures.
                // this issue cannot be resolved in - engine without limit what the placeholder could be.
                Driver.requestStop = true;

                e.printStackTrace();

                return null;
            }
        }
    }
}
