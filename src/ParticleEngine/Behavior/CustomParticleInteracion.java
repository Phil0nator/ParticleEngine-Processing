package ParticleEngine.Behavior;

import ParticleEngine.Particle.Particle;
import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;

/**
 * Use this interface to create custom interactions between particles outside of those provided
 * @see ParticleInteraction
 * @see Particle
 *
 */
@FunctionalInterface
public interface CustomParticleInteracion {


    public void run(Particle p, Particle[] particles, ParticleDrawable drawer, ParticleEngine parent, PApplet applet);


}
