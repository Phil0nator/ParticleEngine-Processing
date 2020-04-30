package ParticleEngine;

import ParticleEngine.Visual.ParticleDrawable;
import ParticleEngine.Visual.Shape;
import ParticleEngine.Visual.properties.ParticleLifeEffect;
import processing.core.PApplet;

/**
 * A way to get quick access to simpler engines without too much setup.
 * Some quickstart methods
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
        out.setup(parent.loadJSONObject("defaultEngine1.json"),dr);
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
        out.setup(parent.loadJSONObject("slideEffect1.json"),dr);
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
        pe.setup(parent.loadJSONObject("data.json"),dr);
        return pe;
    }




}
