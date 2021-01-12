# Java-Ray-Tracer
A ray tracing algorithm implementation in pure java.
![alt text](https://github.com/SygyzyH/Java-Ray-Tracer/blob/main/Thumbnail.png?raw=true)

## Parameters
I encourge you te mess around with the parameters and the scene to see what instances you can make. If you are unable to run this with your machine, lowering the resolution may help. Changing the rendering mode to FAST_MODE may also help.

There are also other rendering modes, depending on what you would like to achive:
- **FAST_MODE**: as mentioned before, this mode will attempt to render the least rays possible. Best performance, worst looks
- **IMAGE_MODE**: this mode enables taking pictures by pressing P, and improves the rendering quality.
- **FULL_RENDER_MODE**: this mode will render every single pixel on the screen, and also allows for taking screenshots. Worst performance, best looks

Some more parameters that may effect performance and looks are:
- **RECURSION_DEPTH**: amount of recursion allowed while rendering reflections. Setting this to any value greater than 4 for small scenes will probably not make any visual changes, but will cost a lot of additional computations.
- **FOV**: if the camera's FOV is greater, more objects may intersect with the ray, and take more computation time. This will cause a negligible change in performance, but still.
- **WIDTH** and **HEIGHT**: this will make a lot of diffrance. Changing this parameter will change the window size, and the amount of rays in need of calculation. 

To change all these modes and parameters, edit the View class file.

## Primatives
The raytracer supports primitives such as 
- [X] Planes
- [X] Spheres
- [X] Triangles

At some point I would like to add 
- [ ] Boxes
- [ ] Cones
- [ ] Cylinders.

## Capabilities
The raytrace has both diffused and specular lighting algorithms, and combines them using the Phong algorithms.
It is also able to recursivly render reflections.

~~I plan on adding the ability to render textures~~ and convex polygons, in order to render more complex 3D models.
The renderer can now render textures on most surfaces, and an implementation to all primitives is almost done.

There is no theoretical limits to the renering width and height, however, the best my computer could do was HD (1980 by 1080) and it should definatly be possible to render 4K images if you allocate enough RAM to the JVM.
