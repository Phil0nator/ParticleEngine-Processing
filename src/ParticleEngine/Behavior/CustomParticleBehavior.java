package ParticleEngine.Behavior;

import ParticleEngine.Particle.Particle;
import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;

/**
 * Use this interface to create your own particle behaviors
 * @see ParticleEngine#addCustomBehavior(CustomParticleBehavior)
 */
@FunctionalInterface
public interface CustomParticleBehavior {

    public void run(Particle p, ParticleEngine parent, PApplet applet);

}
