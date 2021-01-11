# Java-Ray-Tracer
A ray tracing algorithm implementation in pure java.
![alt text](https://github.com/SygyzyH/Java-Ray-Tracer/blob/main/Thumbnail.png?raw=true)

## Parameters
I encourge you te mess around with the parameters and the scene to see what instances you can make. If you are unable to run this with your machine, lowering the resolution may help. Changing the rendering mode to FAST_MODE may also help.

## Primatives
The raytracer supports primitives such as Planes, Spheres and Triangles. At some point I would like to add Boxes, Cones, and maybe Cylinders.

## Capabilities
The raytrace has both diffused and specular lighting algorithms, and combines them using the Phong algorithms.
It is also able to recursivly render reflections.

I plan on adding the ability to render textures and convex polygons, in order to render more complex 3D models.

There is no theoretical limits to the renering width and height, however, the best my computer could do was HD (1980 by 1080) and it should definatly be possible to render 4K images if you allocate enough RAM to the JVM.
