package gui;

import mathematics.Vec3;
import renderer.Camera;

public class Handler {

    public static void handle(){
        Camera cam = View.scene.camera;

        // w
        if (Driver.activeKeys[0])
            cam.translate(new Vec3(.1, 0, 0));

        // s
        if (Driver.activeKeys[2])
            cam.translate(new Vec3(-.1, 0, 0));

        // a
        if (Driver.activeKeys[1])
            cam.translate(new Vec3(0, 0, -.1));

        // d
        if (Driver.activeKeys[3])
            cam.translate(new Vec3(0, 0, .1));

        // shift
        if (Driver.activeKeys[4])
            cam.translate(cam.getUpGuide().mult(.1));

        // space
        if (Driver.activeKeys[5])
            cam.translate(cam.getUpGuide().mult(-.1));

        // move the camera based on last movement with the mouse
        cam.rotate(Driver.mousePosition.x * Driver.lastTime / 4e17, Driver.mousePosition.y * Driver.lastTime / 4e17);

        if (Window.frame.isActive() && Window.frame.isFocused())
            // only move the mouse if the user is actually in the application
            Driver.robot.mouseMove(Driver.cenX, Driver.cenY);
    }
}
