package ParticleEngine.Particle;

import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.ParticleEngine;
import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.random;

public class Particle {

    PApplet applet;
    public ParticleEngine parent;

    PVector loc;
    PVector vel;

    int life = 0;

    public Particle(PApplet a, int x, int y, float velx, float vely){
        loc = new PVector(x,y);
        vel = new PVector(velx,vely);
        applet = a;
    }

    private void draw(){
        parent.drawer.draw((int)loc.x,(int)loc.y,life);
        life++;
    }

    private void normal(){

        vel = new PVector(vel.x,vel.y+1);
        loc.add(vel);

    }

    private void applyBehavior(ParticleBehavior b){
        switch (b){

            case Static:
                return;
            case Normal_Physics:
                normal();
                return;
            case Random:
                loc = new PVector(applet.random(0,parent.bounds[0]),applet.random(0,parent.bounds[1]));
                return;
            case NormalWithoutGravity:
                loc.add(vel);
                return;
            case NormalWithFriction:
                normal();
                vel.sub(.1f,.1f);
                return;
            case NormalPhysics_WithNoiseOffset:
                normal();
                vel.add(new PVector(applet.noise(life)-0.5f,applet.noise(life)-0.5f));
                return;
            case Particle_Smooth_Random:
                vel = new PVector(applet.noise(life)-0.5f,applet.noise(life)-0.5f);
                loc.add(vel);
                return;
            default:
                normal();

        }
    }

    private void applyBehaviors(ParticleBehavior[] behaviors){
        for(ParticleBehavior b: behaviors){

            applyBehavior(b);
        }
    }

    public final void update(ParticleBehavior[] behaviors){
        try {
            applyBehaviors(behaviors);
            draw();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
