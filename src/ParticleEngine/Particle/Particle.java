package ParticleEngine.Particle;

import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.Behavior.ParticleInteraction;
import ParticleEngine.ParticleEngine;
import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.random;

public class Particle {

    PApplet applet;
    public ParticleEngine parent;
    float acceloration = 1.f;
    PVector loc;
    PVector vel;
    float randomNoiseSeed = 1;
    public float mapfac = 5.f;
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

    public void applySpeedFactor(float f){
        acceloration=f;
    }

    public void createRandomSeed(float r){
        randomNoiseSeed = applet.random(0,r);
    }

    private void normal(){

        vel = new PVector(vel.x,vel.y+1);
        applyVelocity();

    }

    private float getNoiseX(){
        return (applet.noise(life+randomNoiseSeed)-0.5f)*mapfac;
    }
    private float getNoiseY(){
        return (applet.noise((float)(life/5)+randomNoiseSeed)-0.5f)*mapfac;
    }


    private void applyVelocity(){
        loc.add(vel.copy().mult(acceloration));
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
                applyVelocity();
                return;
            case NormalPhysics_WithNoiseOffset:
                normal();
                vel.add(new PVector(getNoiseX(),getNoiseY()));
                return;
            case Particle_Smooth_Random:
                vel.add(getNoiseX(),getNoiseY());
                applyVelocity();
                return;

            case Friction:
                vel.mult(0.9f);
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

    public final void update(ParticleBehavior[] behaviors, ParticleInteraction[] is){
        try {
            applyBehaviors(behaviors);
            applyInteractions(is);
            draw();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public final void applyInteractions(ParticleInteraction[] is){
        for(ParticleInteraction i : is){
            applyInteraction(i);
        }
    }

    public void applyInteraction(ParticleInteraction i){
        switch (i){
            case None:
                return;
            case Particle_Window_Collision:
                if(loc.x>parent.bounds[0]||loc.x<0){
                    vel.x*=-1;
                }
                if(loc.y > parent.bounds[1]||loc.y<0){
                    vel.y*=-1;
                }
                return;
            default:
                return;
        }
    }

}
