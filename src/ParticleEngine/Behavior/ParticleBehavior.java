package ParticleEngine.Behavior;

import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;

/**
 * Descriptors for different types of particle behavior.
 * For normal physics, only one enum containing "normal" should be used, because more than one will cause the
 * simulation to run normal physics more times.
 *
 * Creating an array of multiple behaviors can be used to mix their effects.
 *
 * @see ParticleEngine.ParticleEngine#setup(ParticleBehavior[], ParticleInteraction[], GenerationType, int, ParticleDrawable)
 */
public enum ParticleBehavior {

    /**
     * Particles are given no velocity, and will remain static or continue to act on any initial behaviors
     */
    Static,

    /**
     * Particles will have gravity, and will fall to the ground
     */
    Normal_Physics,
    /**
     * The velocity of particles will be reduced over time
     */
    Friction,
    /**
     * The locations of particles will be completely random each frame
     */
    Random,
    /**
     * The velocity of particles will be affected by a Perlin Noise effect, causing them to randomly move around
     * In a smooth way
     */
    Particle_Smooth_Random,
    /**
     * Particles will have static velocity, but no gravity
     */
    NormalWithoutGravity,
    /**
     * Particles will follow a sin wave
     */
    Wave_Movement,
    /**
     * Particles will make a ZigZag motion along their velocities
     */
    ZigZag_Movement,

    /**
     * Particles will behave under normal physics, but with an extra noise offset
     */
    NormalPhysics_WithNoiseOffset,
    /**
     * Particles will orbit the origin set in the particle engine
     * @see ParticleEngine.ParticleEngine
     * @see ParticleEngine.ParticleEngine#setOrigin(int, int)
     */
    OrbitOrigin,

    /**
     * Particles will follow normal physics, but will bounce to a lesser degree
     */
    Normal_Physics_NoBounce,

}
