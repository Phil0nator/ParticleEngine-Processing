package ParticleEngine.Visual;


import ParticleEngine.Behavior.GenerationType;
import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.Behavior.ParticleInteraction;
import ParticleEngine.Visual.properties.ParticleLifeEffect;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;


/**
 * A wrapper to define how particles of a given engine will be drawn.
 * There are three options:
 *      PShape
 *          ex: rect, circle, etc...
 *      PImage
 *          anything you can load as an image
 *      CustomParticleDraw
 *          you can write your own draw method using int x, int y, int life
 *          as parameters
 *
 * @see PShape
 * @see PImage
 * @see CustomParticleDraw
 *
 *
 * @see ParticleEngine.ParticleEngine
 * @see ParticleEngine.ParticleEngine#setup(ParticleBehavior[], ParticleInteraction[], GenerationType, int, ParticleDrawable)
 *
 */

public class ParticleDrawable {

    PShape shape;
    PImage img;
    CustomParticleDraw d;
    ParticleLifeEffect[] plf = null;
    PApplet parent;
    int c;
    int perimc;

    /**
     * A constructor for a ParticleDrawable using a PShape
     * @param p parent
     * @param shape the PShape
     */
    public ParticleDrawable(PApplet p, PShape shape){
        parent=p;
        this.shape=shape;
    }

    /**
     * A constructor for a ParticleDrawable using an image
     * @param p parent
     * @param i the PImage to draw
     */
    public ParticleDrawable(PApplet p, PImage i){
        parent=p;
        img = i;
    }

    /**
     * A constructor for a ParticleDrawable using custom methods
     * @param p parent
     * @param dr Your own CustomParticleDraw object with the overridden method
     * @see CustomParticleDraw
     */
    public ParticleDrawable(PApplet p, CustomParticleDraw dr){
        parent=p;
        d=dr;
    }

    /**
     * Set the visual life effect for the drawer
     * @param lf life effect
     * @see ParticleLifeEffect
     */
    public void setLifeEffect(ParticleLifeEffect[] lf){
        plf=lf;
    }

    public void setParent(PApplet p){
        parent=p;
        c = parent.color(255,0,255);
    }

    /**
     * Set fill color
     * (Only for drawables using a PShape)
     * @param c new color
     * @see PApplet#color
     */
    public void fill(int c){
        this.c=c;
    }

    /**
     * Set stroke color
     * (Only for drawables using a PShape)
     * @param c new color
     * @see PApplet#color
     */
    public void stroke(int c){
        this.perimc=c;
    }

    private void applyLE(int life, ParticleLifeEffect effect){
        if(effect == null)return;
        switch (effect){
            case Negativealpha:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c),parent.alpha(c)/life);
                return;
            case Positivealpha:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c),parent.alpha(c)+life);
                return;
            case Negativered:
                c = parent.color(parent.red(c)/life,parent.green(c),parent.blue(c),parent.alpha(c));
                return;
            case Positivered:
                c = parent.color(parent.red(c)+life,parent.green(c),parent.blue(c),parent.alpha(c));
                return;
            case Negativegreen:
                c = parent.color(parent.red(c),parent.green(c)/life,parent.blue(c),parent.alpha(c));
                return;
            case Positivegreen:
                c = parent.color(parent.red(c),parent.green(c)+life,parent.blue(c),parent.alpha(c));
                return;
            case Negativeblue:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c)/life,parent.alpha(c));
                return;
            case Positiveblue:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c)+life,parent.alpha(c));
        }
    }

    /**
     * Draw the particle
     * @param x coord
     * @param y coord
     * @param l frames
     */
    public void draw(int x, int y, int l){
        for(ParticleLifeEffect p: plf){
            applyLE(l, p);
        }
        if(img!=null){
            parent.tint(c);
            parent.image(img,x,y);
            return;
        }
        shape.setFill(c);
        shape.setStroke(perimc);
        if(d!=null){
            d.drawParticle(x,y,l);
            return;
        }

        if(shape!=null){
            parent.fill(c);
            parent.stroke(perimc);
            parent.shape(shape,x,y);
        }
    }

}
