package ParticleEngine.Behavior;

/**
 * InitialBehavior is used to describe how the particles will behave
 * Upon spawning.
 *
 * Only one initial behavior can be used for each ParticleEngine
 *
 * @see ParticleEngine.ParticleEngine#setInitialBehavior(InitialBehavior)
 *
 *
 * Additional information can be given through the initial behavior argument field
 * @see ParticleEngine.ParticleEngine#setInitialBehaviorArg(float)
 *
 */
public enum InitialBehavior {
    /**
     * Particles will trend randomly away from a center point
     * @see ParticleEngine.ParticleEngine#setOrigin(int, int)
     *
     * The Initial Behavior arg will determine the multiplier for the initial velocity
     *
     * Ex: 1 will be a smaller explosion, 10 will be much larger and faster
     *
     *
     *
     */
    Explosive,

    /**
     * Particles will start around the edges of the bounds, and trend randomly
     * Towards a center point
     * @see ParticleEngine.ParticleEngine#setBounds(int, int)
     * @see ParticleEngine.ParticleEngine#setOrigin(int, int)
     *
     *
     * The initial behavior arg will determine the multiplier for the initial velocity
     * Higher = faster
     *
     *
     */
    Implosive,


    /**
     * Particles will fill the space given in bounds following a Perlin Noise pattern
     * @see ParticleEngine.ParticleEngine#setBounds(int, int)
     */
    NoiseFillSpace,
    /**
     * Particles will evenly fill the space given in bounds
     * @see ParticleEngine.ParticleEngine#setBounds(int, int)
     */
    EvenFillSpace,

    /**
     * Particles will do nothing initially
     */
    Static,

    /**
     * Particles will exactly follow an initial velocity
     * @see ParticleEngine.ParticleEngine#setInitialVelocity(int, int)
     * The initial behavior arg will determine the multiplier for the initial velocity
     * Higher = faster
     */
    Exact_IV,

    /**
     * Particles will follow an initial velocity with some random variation
     * @see ParticleEngine.ParticleEngine#setInitialVelocity(int, int)
     * The initial behavior arg will determine the multiplier for the initial velocity
     * Higher = faster
     */
    RandomlyVarying_IV


}
