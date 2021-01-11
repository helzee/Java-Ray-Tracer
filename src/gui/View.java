// TODO: most of the game loop can be moved to a different class, the View class is not meant to represent all of the
// TODO: game parameters.

package gui;

import mathematics.Functions;
import mathematics.Vec3;
import renderer.Camera;
import renderer.Light;
import shapes.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class View extends Canvas implements Runnable {

    public static final int WIDTH = 1980/3, HEIGHT = 1080/3;
    public static int cenX = 0, cenY = 0, frames = 0;
    public static long lastTime = 0;

    public static Point2D.Double mousePosition = new Point2D.Double(0, 0);

    public static Robot robot;

    public static Window window;
    public static Scene scene;
    public static Camera camera;

    // request stop: will be flipped once the user attempts to leave the program.
    // waiting: will be flipped once the main thread has finished construction the scene instance.
    public static boolean requestStop = false, waiting = true, activeKeys[];

    private Thread thread;
    private boolean running = false;

    public View() {
        this.addMouseMotionListener(new MouseMovementInput());
        this.addKeyListener(new KeyInput());
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Renderer.RENDERING_MODE = Renderer.FAST_MODE;

        activeKeys = new boolean[6]; // w, a, s, d, shift, space


        window = new Window(WIDTH, HEIGHT, "Ray Tracer", this);

        camera = new Camera(Functions.asRadians(25), (double) WIDTH / HEIGHT,
                new Vec3(0, 1, 0), new Vec3(0, -.8, 0), new Vec3(4.374448249167299, -4.702087519758727, -1.6718531362642548));

        scene = new Scene(0, 3, camera,
                new Plane(new Vec3(0, .5, 0), new Vec3(0, -1, 0), new renderer.Color(0.313,0.552,0.941), .3, .3),
                new Plane(new Vec3(0, -9.5, 0), new Vec3(0, 1, 0), new renderer.Color(0.313,0.552,0.941), .3, .3),
                new Plane(new Vec3(5, 0, 0), new Vec3(-1, 0, 0), new renderer.Color(0.686, 0.941, 0.313), .3, .3),
                new Plane(new Vec3(-5, 0, 0), new Vec3(-1, 0, 0), new renderer.Color(0.823, 0.313, 0.941), .3, .3),
                new Plane(new Vec3(0, 0, 5), new Vec3(0, 0, -1), new renderer.Color(0.313, 0.886, 0.941), .3, .3),
                new Plane(new Vec3(0, 0, -5), new Vec3(0, 0, -1), new renderer.Color(1, 1, 0), .3, .3),
                new Sphere(new Vec3(-2,-1,2), 1, new renderer.Color(0.941,.529,0.313), .6, .5),
                new Sphere(new Vec3(-3, 0, 1), 1, new renderer.Color(.5, 1, .9), .3,.2),
                new Sphere(new Vec3(0, -1, 2), .5, new renderer.Color(1), .1, .7)
                //new Triangle(new Vec3(1,2,2), new Vec3(0,.2,0), new Vec3(0,0,3), new renderer.Color(1, 0,0), .1,.4)
        );

        scene.addLights(new Light(new Vec3(1, -2, 2), new renderer.Color(.8), 1));

        // calculate the center of the frame
        cenX = View.WIDTH / 2 + Window.frame.getX();
        cenY = View.HEIGHT / 2 + Window.frame.getY();

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        this.setCursor(blankCursor);

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
                View.frames = frames;
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

        Renderer.render(g, scene);

        g.dispose();
        bs.show();
    }

}
