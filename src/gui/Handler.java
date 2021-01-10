package gui;

import mathematics.Vec3;
import renderer.Camera;

public class Handler {

    public static void handle(){
        Vec3 camDir = View.scene.camera.getTarget().get_normalized();
        Camera cam = View.scene.camera;

        // w
        if (View.activeKeys[0])
            cam.translate(new Vec3(-.1, 0, 0));

        // s
        if (View.activeKeys[2])
            cam.translate(new Vec3(.1, 0, 0));

        // a
        if (View.activeKeys[1])
            cam.translate(new Vec3(0, 0, .1));

        // d
        if (View.activeKeys[3])
            cam.translate(new Vec3(0, 0, -.1));

        // shift
        if (View.activeKeys[4])
            cam.translate(cam.getUpGuide().mult(.1));

        // space
        if (View.activeKeys[5])
            cam.translate(cam.getUpGuide().mult(-.1));

        // TODO: mouse movement
    }
}
