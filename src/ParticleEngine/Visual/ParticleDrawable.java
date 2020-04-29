package ParticleEngine.Visual;


import ParticleEngine.Behavior.GenerationType;
import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.Behavior.ParticleInteraction;
import ParticleEngine.Visual.properties.ParticleLifeEffect;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

import static processing.core.PApplet.abs;


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

    public Shape shape;
    PImage img;
    CustomParticleDraw d;
    ParticleLifeEffect[] plf = {};
    PApplet parent;
    int c;
    float ogr;
    float ogg;
    float ogb;
    int perimc;

    public int w;
    public int h;
    private static int c(int v){
        return v < 1 ? 1 : (v >= 255 ? 254 : v);
    }
    private static int c(float v){
        return c((int)v);
    }
    /**
     * A constructor for a ParticleDrawable using a PShape
     * @param p parent
     * @param shape the Shape
     * @param p1 width
     * @param p2 height
     */
    public ParticleDrawable(PApplet p, Shape shape, int p1, int p2){
        parent=p;
        this.shape=shape;
        w=p1;
        h=p2;
    }

    /**
     * A constructor for a ParticleDrawable using an image
     * @param p parent
     * @param i the PImage to draw
     */
    public ParticleDrawable(PApplet p, PImage i){
        parent=p;
        img = i;
        w = i.width;
        h = i.height;
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
        ogr = parent.red(c);
        ogg = parent.green(c);
        ogb = parent.blue(c);
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

    /**
     * Used internally.
     * @return the radius of the particle
     */
    public final float getR(){
        return (float)w/2;
    }

    private void applyLE(int life, ParticleLifeEffect effect, int x, int y){
        if(effect == null)return;
        switch (effect){
            case Negativealpha:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c),c(255-life));
                return;
            case Positivealpha:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c),c(life));
                return;
            case Negativered:
                c = parent.color(c(ogr-life),parent.green(c),parent.blue(c),parent.alpha(c));
                return;
            case Positivered:
                c = parent.color(c(ogr+life),parent.green(c),parent.blue(c),parent.alpha(c));
                return;
            case Negativegreen:
                c = parent.color(parent.red(c),c(ogg-life),parent.blue(c),parent.alpha(c));
                return;
            case Positivegreen:
                c = parent.color(parent.red(c),c(ogg+life),parent.blue(c),parent.alpha(c));
                return;
            case Negativeblue:
                c = parent.color(parent.red(c),parent.green(c),c(ogb-life),parent.alpha(c));
                return;
            case Positiveblue:
                c = parent.color(parent.red(c),parent.green(c),c(ogb+life),parent.alpha(c));
                return;
            case NoisemapAlpha:
                c = parent.color(parent.red(c),parent.green(c),parent.blue(c),parent.noise(x*.02f,y*.02f)*255);
                break;
            case NoisemapRed:
                c = parent.color(parent.noise(x*.02f,y*.02f)*255,parent.green(c),parent.blue(c),parent.alpha(c));
                break;
            case NoisemapGreen:
                c = parent.color(parent.red(c),parent.noise(x*.02f,y*.02f)*255,parent.blue(c),parent.alpha(c));
                break;
            case NoisemapBlue:
                c = parent.color(parent.red(c),parent.green(c),parent.noise(x*.02f,y*.02f)*255,parent.alpha(c));
                break;
            case NoisemapColor:
                c = parent.color(parent.noise(200+x*.02f,200+y*.02f)*255,parent.noise(100+x*.02f,100+y*.02f)*255,parent.noise(x*.02f,y*.02f)*255,parent.alpha(c));
                break;
        }
    }

    /**
     * Draw the particle
     * @param x coord
     * @param y coord
     * @param l frames
     */
    public void draw(int x, int y, int l){
        if(parent.alpha(c)==0){return;}
        for(ParticleLifeEffect p: plf){
            applyLE(l, p,x,y);
        }

        if(img!=null){
            parent.tint(c);
            parent.image(img,x,y);
            parent.noTint();
            return;
        }

        if(d!=null){
            d.drawParticle(x,y,l);
            return;
        }

        if(shape!=null){
            parent.fill(c);
            parent.stroke(perimc);
            switch (shape){
                case Rect:
                    parent.rect(x,y,w,h);
                    break;
                case Ellipse:
                    parent.ellipse(x,y,w,h);
                    break;
                case Line:
                    parent.line(x,y,w,h);
                    break;
            }
        }
    }

    public void draw(PGraphics g, int x, int y, int l){
        for(ParticleLifeEffect p: plf){
            applyLE(l, p,x,y);
        }

        if(img!=null){
            g.tint(c);
            g.image(img,x,y);
            g.noTint();
            return;
        }

        if(d!=null){
            d.drawParticle(x,y,l);
            return;
        }

        if(shape!=null){
            g.fill(c);
            g.stroke(perimc);
            switch (shape){
                case Rect:
                    g.rect(x,y,w,h);
                    break;
                case Ellipse:
                    g.ellipse(x,y,w,h);
                    break;
                case Line:
                    g.line(x,y,w,h);
                    break;
            }
        }
    }

    public int[] getColorCache(int frames){
        int[] out = new int[frames];
        for(int i = 0 ; i < frames;i++){
            out[i] = c;
            for(ParticleLifeEffect p: plf){
                applyLE(i, p,0,0);
            }
        }
        return out;

    }

    public void drawCached(int x, int y, int color){


        if(img!=null){
            parent.tint(color);
            parent.image(img,x,y);
            parent.noTint();
            return;
        }

        if(d!=null){
            d.drawParticle(x,y,color);
            return;
        }

        if(shape!=null){
            parent.fill(color);
            parent.stroke(perimc);
            switch (shape){
                case Rect:
                    parent.rect(x,y,w,h);
                    break;
                case Ellipse:
                    parent.ellipse(x,y,w,h);
                    break;
                case Line:
                    parent.line(x,y,w,h);
                    break;
            }
        }


    }

}
