package ParticleEngine.Particle;

import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.Behavior.ParticleInteraction;
import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.Shape;
import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.random;
import static processing.core.PApplet.*;

/**
 * The particle class is used internally and doesn't need to be referenced unless you are
 * creating custom behaviors or interactions.
 */
public class Particle {

    private final int PTV = 15;
    PApplet applet;
    public ParticleEngine parent;
    float acceloration = 1.f;
    PVector loc;
    PVector vel;
    float randomNoiseSeed = 1;
    public float mapfac = 5.f;
    public int life = 0;
    private boolean nobounce = false;
    public float mass = 5;
    public int index = 0;


    /**
     * Constructor
     * @param a applet
     * @param x coord
     * @param y coord
     * @param velx non-normalized velocity on the x axis
     * @param vely non-normalized velocity on the y axis
     */
    public Particle(PApplet a, int x, int y, float velx, float vely){
        loc = new PVector(x,y);
        vel = new PVector(velx,vely);
        applet = a;
    }

    private void draw(){
        parent.drawer.draw((int)loc.x,(int)loc.y,life);
        life++;
    }

    /**
     * Limit the velocity so particles don't go too fast
     */
    public void constrainVelocity(){
        vel = new PVector(constrain(vel.x,-PTV,PTV),constrain(vel.y,-PTV,PTV));
    }

    /**
     * Apply the speed field
     * @param f speed float
     * @see ParticleEngine#setSpeedFactor(float)
     */
    public void applySpeedFactor(float f){
        acceloration=f;
    }

    /**
     * Create the random noise difference for the particle
     * @param r RND
     * @see ParticleEngine#setRandomNoiseDifferencial(float)
     */
    public void createRandomSeed(float r){
        randomNoiseSeed = applet.random(0,r);
    }

    private void normal(){

        vel = new PVector(vel.x,vel.y+mass);
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

    /**
     * Update the particle for caching (excludes drawing)
     * @param behaviors behaviors
     * @param is interactions
     * @return new location
     * @see Particle#update(ParticleBehavior[], ParticleInteraction[])
     * @see ParticleEngine.caches.LightCache
     */
    public final PVector updateforcache(ParticleBehavior[] behaviors, ParticleInteraction[] is){
        try {
            applyBehaviors(behaviors);
            applyInteractions(is);
            life++;
            return loc;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PVector(-1,-1);
    }

    /**
     * Frame by frame update
     * @param behaviors behaviors
     * @param is interactions
     */
    public final void update(ParticleBehavior[] behaviors, ParticleInteraction[] is){
        try {
            applyBehaviors(behaviors);
            applyInteractions(is);
            draw();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void applyInteractions(ParticleInteraction[] is){
        for(ParticleInteraction i : is){
            applyInteraction(i);
        }
    }

    private void applyInteraction(ParticleInteraction i){
        switch (i){
            case None:
                return;
            case Particle_Window_Collision:
                if(loc.x>parent.bounds[0]-parent.drawer.getR()||loc.x<parent.drawer.getR()){

                    vel.x*=-.5;
                }
                if(loc.y > parent.bounds[1]-parent.drawer.getR()||loc.y<parent.drawer.getR()){
                    vel.y*=-.5;
                }
                return;
            case InterParticle_Collision:
                double radius = parent.drawer.getR();
                if(parent.drawer.shape == Shape.Ellipse) {
                    //https://processing.org/examples/circlecollision.html
                    for (int it = index+1;it<parent.particles.size();it++) {
                        Particle p = parent.particles.get(it);
                        // Get distances between the balls components
                        PVector distanceVect = PVector.sub(p.loc, loc);

                        // Calculate magnitude of the vector separating the balls
                        float distanceVectMag = distanceVect.mag();

                        // Minimum distance before they are touching
                        float minDistance = (float)radius*2;

                        if (distanceVectMag < minDistance) {


                            float distanceCorrection = (float)((minDistance - distanceVectMag) / 2.0);
                            PVector d = distanceVect.copy();
                            PVector correctionVector = d.normalize().mult(distanceCorrection);
                            p.loc.add(correctionVector);
                            loc.sub(correctionVector);


                            float theta = distanceVect.heading();

                            float sine = sin(theta);
                            float cosine = cos(theta);

                            PVector[] bTemp = {
                                    new PVector(), new PVector()
                            };

                            bTemp[1].x = cosine * distanceVect.x + sine * distanceVect.y;
                            bTemp[1].y = cosine * distanceVect.y - sine * distanceVect.x;


                            PVector[] vTemp = {
                                    new PVector(), new PVector()
                            };

                            vTemp[0].x = cosine * vel.x + sine * vel.y;
                            vTemp[0].y = cosine * vel.y - sine * vel.x;
                            vTemp[1].x = cosine * p.vel.x + sine * p.vel.y;
                            vTemp[1].y = cosine * p.vel.y - sine * p.vel.x;

                            PVector[] vFinal = {
                                    new PVector(), new PVector()
                            };


                            vFinal[0].x = ((mass - p.mass) * vTemp[0].x + 2 * p.mass * vTemp[1].x) / (mass + p.mass);
                            vFinal[0].y = vTemp[0].y;


                            vFinal[1].x = ((p.mass - mass) * vTemp[1].x + 2 * mass * vTemp[0].x) / (mass + p.mass);
                            vFinal[1].y = vTemp[1].y;


                            bTemp[0].x += vFinal[0].x;
                            bTemp[1].x += vFinal[1].x;

                            PVector[] bFinal = {
                                    new PVector(), new PVector()
                            };

                            bFinal[0].x = cosine * bTemp[0].x - sine * bTemp[0].y;
                            bFinal[0].y = cosine * bTemp[0].y + sine * bTemp[0].x;
                            bFinal[1].x = cosine * bTemp[1].x - sine * bTemp[1].y;
                            bFinal[1].y = cosine * bTemp[1].y + sine * bTemp[1].x;

                            p.loc.x = loc.x + bFinal[1].x;
                            p.loc.y = loc.y + bFinal[1].y;

                            loc.add(bFinal[0]);

                            vel.x = cosine * vFinal[0].x - sine * vFinal[0].y;
                            vel.y = cosine * vFinal[0].y + sine * vFinal[0].x;
                            p.vel.x = cosine * vFinal[1].x - sine * vFinal[1].y;
                            p.vel.y = cosine * vFinal[1].y + sine * vFinal[1].x;

                            constrainVelocity();
                            p.constrainVelocity();

                        }
                    }
                }
                return;


            default:
                return;
        }
    }

}
