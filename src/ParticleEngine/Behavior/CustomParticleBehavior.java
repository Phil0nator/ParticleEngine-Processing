package ParticleEngine.Behavior;

import ParticleEngine.Particle.Particle;
import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;

/**
 * Use this interface to create your own custom particle behaviors outside of those provided
 * @see ParticleEngine#addCustomBehavior(CustomParticleBehavior)
 * @see ParticleBehavior
 *
 */
@FunctionalInterface
public interface CustomParticleBehavior {

    public void run(Particle p, ParticleEngine parent, PApplet applet);

}
