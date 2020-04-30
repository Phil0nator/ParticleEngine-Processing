package ParticleEngine;

/**
 * The interface ParticleRunner allows you to switch easily between Engines and Caches.
 *
 * When using a ParticleEngine, calling run is the same as update.
 * When using a LightCache, calling run is the same as update.
 *
 * @see ParticleEngine.caches.LightCache
 * @see ParticleEngine
 *
 */
public interface ParticleRunner {

    public void run();

    public void setOrigin(int x, int y);


}
