package ParticleEngine.Visual;

import processing.core.PApplet;

/**
 * CustomParticleDraw is a way to define your own custom ways of drawing
 * your particles based on an x and y coordinate, and the life of the particles
 * (life is in frames)
 *
 * This is to be applied to a ParticleDrawable
 * @see ParticleDrawable
 * @see ParticleDrawable#ParticleDrawable(PApplet, CustomParticleDraw)
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface CustomParticleDraw {

    public void drawParticle(int x, int y, int life);

}
