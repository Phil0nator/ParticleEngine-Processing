package ParticleEngine.Behavior;

import ParticleEngine.Visual.ParticleDrawable;

/**
 * ParticleInteraction dictates how particles will interact with their environment
 * and each other.
 *
 * Some interaction modes can cause significant slowdowns, and should only be used with heavy
 * Caching in order to preserve frame rate. Slower modes are marked SLOW in their documentation
 *
 * @see ParticleEngine.ParticleEngine
 * @see ParticleEngine.ParticleEngine#setup(ParticleBehavior[], ParticleInteraction[], GenerationType, int, ParticleDrawable)
 */
public enum ParticleInteraction {
    /**
     * SLOW
     *
     * Particles will slowly move towards other particles
     *
     */
    InterParticle_Attraction,

    /**
     * SLOW
     *
     * Particles will slowly push away from other particles
     */

    InterParticle_Repulsion,

    /**
     * SLOW
     *
     * Particles will collide with each other
     */
    InterParticle_Collision,

    /**
     * Particles will collide with the bounds set in the engine
     * @see ParticleEngine.ParticleEngine#setBounds(int, int)
     */
    Particle_Window_Collision,

    /**
     * SLOW
     *
     * Particles will collide with each other, and then stick together
     *
     */
    Particle_Sticky,

    /**
     * Particles will be blind to each other and the environment
     */

    None
}
