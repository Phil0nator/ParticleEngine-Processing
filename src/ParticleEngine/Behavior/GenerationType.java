package ParticleEngine.Behavior;

import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;

/**
 * A GenerationType dictates how particles will be created
 *
 *
 *
 */
public enum GenerationType {
    /**
     * All particles are created in one frame
     */
    AtOnce,
    /**
     * Particles are created of a given number of frames
     */
    OverTime,

    NoiseType,
    /**
     * A given number of particles are created when the createBatch(int) method is called
     * @see ParticleEngine.ParticleEngine#createBatch(int)
     * @see ParticleEngine.ParticleEngine#setup(ParticleBehavior[], ParticleInteraction[], GenerationType, int, ParticleDrawable)
     */
    OnEvent,
}
