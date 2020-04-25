package ParticleEngine.Visual;


import ParticleEngine.Visual.properties.ParticleLifeEffect;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class ParticleDrawable {

    PShape shape;
    PImage img;
    CustomParticleDraw d;
    ParticleLifeEffect[] plf = null;
    PApplet parent;
    int c;
    int perimc;
    public ParticleDrawable(PApplet p, PShape shape){
        parent=p;
        this.shape=shape;
    }
    public ParticleDrawable(PApplet p, PImage i){
        parent=p;
        img = i;
    }
    public ParticleDrawable(PApplet p, CustomParticleDraw dr){
        parent=p;
        d=dr;
    }
    public void setLifeEffect(ParticleLifeEffect[] lf){
        plf=lf;
    }
    public void setParent(PApplet p){
        parent=p;
        c = parent.color(0,0,0);
    }
    public void fill(int c){
        this.c=c;
    }
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
            parent.shape(shape,x,y);
        }
    }

}
