package ParticleEngine;

import ParticleEngine.Visual.ParticleDrawable;
import ParticleEngine.Visual.Shape;
import ParticleEngine.Visual.properties.ParticleLifeEffect;
import processing.core.PApplet;

/**
 * A way to get quick access to simpler engines without too much setup.
 * Some quickstart methods.
 *
 *
 * Any engine you get out of the factory can still be altered and tweaked before use.
 * This means that the drawer for each engine will also still be able to be tweaked under the public field ParticleEngine.drawer
 * @see ParticleEngine
 * @see ParticleDrawable
 * @see ParticleEngine#drawer
 *
 */
public class ParticleEngineFactory {
    /**
     * This gets a default particle engine which can be easily tweaked and work reliably.
     * @return a default particle engine
     */
    PApplet parent;
    public ParticleEngineFactory(PApplet parent){
        this.parent=parent;
    }

    /**
     * This will get a simple engine setup which can look good for click effects or fireworks.
     * @return a basic ParticleEngine
     * @see ParticleEngine
     */
    public final ParticleEngine getBasicEngine(){
        ParticleEngine out = new ParticleEngine(parent);
        ParticleDrawable dr = new ParticleDrawable(parent, Shape.Rect,5,5);
        ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha};
        dr.setLifeEffect(le);
        dr.fill(parent.color(100,200,255));
        dr.stroke(parent.color(255,255,255,0));
        try {
            out.setup(parent.loadJSONObject("defaultEngine1.json"), dr);
        }catch (Exception e){
            e.printStackTrace();
        }
        out.setBounds(parent.width,parent.height);
        return out;
    }

    /**
     * Get a sliding effect that looks like welding sparks
     * @return a template ParticleEngine
     */
    public final ParticleEngine getSlideEffectA(){
        ParticleEngine out = new ParticleEngine(parent);
        ParticleDrawable dr = new ParticleDrawable(parent, Shape.Rect,5,5);
        ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha, ParticleLifeEffect.Negativered};
        dr.setLifeEffect(le);
        dr.fill(parent.color(255,100,100));
        dr.stroke(parent.color(255,255,255,0));
        try {
            out.setup(parent.loadJSONObject("slideEffect1.json"), dr);
        }catch (Exception e){
            e.printStackTrace();
        }
        out.setBounds(parent.width,parent.height);
        return out;
    }

    /**
     * Get a simple explosion effect
     * @return a template ParticleEngine
     */
    public final ParticleEngine getExplosionA(){
        ParticleEngine pe = new ParticleEngine(parent);
        ParticleDrawable dr = new ParticleDrawable(parent, Shape.Ellipse, 10,10);
        ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha, ParticleLifeEffect.Negativered};
        dr.setLifeEffect(le);
        dr.fill(parent.color(255,55,2));
        dr.stroke(parent.color(255,255,255,0));
        try {
            pe.setup(parent.loadJSONObject("data.json"), dr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pe;
    }

    /**
     * Get a simple engine ideal for creating trail effects
     * @return a particle engine
     */
    public final ParticleEngine getSimpleTrail(){

        ParticleEngine pe = new ParticleEngine(parent);
        ParticleDrawable dr = new ParticleDrawable(parent, Shape.Rect, 10,10);
        ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha};
        dr.setLifeEffect(le);
        dr.fill(parent.color(100,100,255));
        dr.stroke(parent.color(255,255,255,0));
        try {
            pe.setup(parent.loadJSONObject("trail1.json"), dr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pe;

    }


}
