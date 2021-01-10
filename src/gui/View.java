package gui;

import mathematics.Vec3;
import renderer.Camera;
import renderer.Light;
import shapes.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class View extends Canvas implements Runnable {

    public static final int WIDTH = 1980/3, HEIGHT = 1080/3;

    public static Point mousePosition = new Point(0, 0);
    public static int frames = 0;

    public static Robot robot;
    public static Window window;

    public static Scene scene;
    public static Camera camera;

    public static boolean requestStop = false;

    public static boolean activeKeys[];

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

        activeKeys = new boolean[6]; // w, a, s, d, shift, space


        window = new Window(WIDTH, HEIGHT, "Ray Tracer", this);

        camera = new Camera(25 * Math.PI / 180, (double) WIDTH / HEIGHT,
                new Vec3(0, 1, 0).get_normalized(), new Vec3(0,1,0), new Vec3(5,0,1));

        scene = new Scene(.2, 1, camera,
                new Plane(new Vec3(0, .5, 0), new Vec3(0, -1, 0), new renderer.Color(0, 0, 1), .1, .4),
                new Sphere(new Vec3(-2,-1,2), 1, new renderer.Color(0,1,0), .1, .8),
                new Sphere(new Vec3(-3, 0, 1), 1, new renderer.Color(.5, 1, .9), .1,.2),
                new Sphere(new Vec3(0, -1, 2), .1, new renderer.Color(1), .1, .4),
                new Triangle(new Vec3(1,2,2), new Vec3(0,.2,0), new Vec3(0,0,3), new renderer.Color(1, 0,0), .1,.4)
        );

        scene.addLights(new Light(new Vec3(1, -2, 2), new renderer.Color(.5)));

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        // give the secondary thread to initialize all of the other game variables
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }

        }
        stop();
    }

    private void tick() {
        Handler.handle();

        if (requestStop)
            stop();

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
