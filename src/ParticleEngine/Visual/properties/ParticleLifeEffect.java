package ParticleEngine.Visual.properties;

/**
 * Particle Life effects are visual effects that change the color of particles
 */
public enum ParticleLifeEffect {
    /**
     * As a particle's life increases, its alpha decreases
     */
    Negativealpha,
    /**
     * As a particle's life increases, its red decreases
     */
    Negativered,
    /**
     * As a particle's life increases, its green decreases
     */
    Negativegreen,
    /**
     * As a particle's life increases, its blue decreases
     */
    Negativeblue,
    /**
     * As a particle's life increases, its alpha increases
     */
    Positivealpha,
    /**
     * As a particle's life increases, its red increases
     */
    Positivered,
    /**
     * As a particle's life increases, its green increases
     */
    Positivegreen,
    /**
     * As a particle's life increases, its blue increases
     */
    Positiveblue,
    /**
     * The alpha value for each particle is changed based on a noise map
     */
    NoisemapAlpha,
    /**
     * The red value for each particle is changed based on a noise map
     */
    NoisemapRed,
    /**
     * The green value for each particle is changed based on a noise map
     */
    NoisemapGreen,
    /**
     * The blue value for each particle is changed based on a noise map
     */
    NoisemapBlue,
    /**
     * The overall color value for each particle is changed based on a noise map
     */
    NoisemapColor,

}
