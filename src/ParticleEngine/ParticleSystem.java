package ParticleEngine;


import ParticleEngine.Exceptions.InvalidJSONException;
import ParticleEngine.Particle.Particle;
import ParticleEngine.Visual.ParticleDrawable;
import ParticleEngine.Visual.Shape;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.util.ArrayList;

/**
 *
 * The ParticleSystem class is a wrapper to contain many seperate ParticleEngines. You can use this to create
 * more complex simulations and effects.
 *
 *
 */
public final class ParticleSystem implements ParticleRunner {

    ArrayList<ParticleEngine> engines = new ArrayList<ParticleEngine>(0);
    private ParticleEngine templateEngine;
    private PApplet parent;

    public ParticleSystem(PApplet p){
        parent=p;
    }


    /**
     * The "handle" field is used to affect the behavior and function of the particles.
     * handle is a ParticleEngine object for which each particle is one of the engines of this system: an engine of engines.
     *
     * Handle does not need, and should not have a drawer.
     * Use handle to define all the properties of the system, just as you would an engine.
     * @see ParticleEngine
     *
     */
    public ParticleEngine handle;


    /**
     * Push your own engine to the array of engines
     * @param e your engine
     */
    public final void pushEngine(ParticleEngine e){
        engines.add(e);
    }

    /**
     * Set a template engine to be used
     * @see ParticleSystem#pushFromTemplate()
     * @param e a template engine
     */
    public final void setTemplateEngine(ParticleEngine e){
        templateEngine=e;
    }

    /**
     * Produce a new copy of the template engine, and add it to the array
     */
    public final void pushFromTemplate(){
        engines.add(templateEngine.copy());
    }

    /**
     * Faster access to a setup method for the handle
     * @param data json info
     * @throws InvalidJSONException for invalid json
     * @see InvalidJSONException
     * @see ParticleSystem#handle
     * @see ParticleEngine
     * @see ParticleEngine#setup(JSONObject, ParticleDrawable)
     */
    public final void setup(JSONObject data) throws InvalidJSONException {
        handle = new ParticleEngine(parent);
        handle.setup(data, new ParticleDrawable(parent, Shape.Ellipse, 0,0));
    }

    /**
     * @see ParticleRunner
     */
    @Override
    public final void run(){
        int i = 0;
        int namt = handle.updateForSystem(this);
        for(int a = 0; a < namt;a++){
            pushFromTemplate();
        }
        for(ParticleEngine e:engines){
            if(e.isEmpty()){
                handle.createBatch(1);
            }else if (i >= handle.particles.size()){
                Particle np = new Particle(parent,(int)e.getOrigin().x,(int)e.getOrigin().y,0,0);
            }
            e.setOrigin((int)e.particles.get(i).loc.x,(int)e.particles.get(i).loc.y);
            e.update();
            i++;
        }
    }

    /**
     * @see ParticleRunner
     * @param x coord
     * @param y coord
     */
    @Override
    public final void setOrigin(int x, int y){
        handle.setOrigin(x,y);
    }

}
