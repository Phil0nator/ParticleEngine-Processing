import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;

ParticleEngine pe;

void setup(){
   size(700,700);
   pe = new ParticleEngine(this);
   
   ParticleBehavior[] b = {ParticleBehavior.Normal_Physics};
   InitialBehavior[] ib = {InitialBehavior.Explosive};
   PImage pimg = loadImage("testimg.jpg");
   pimg.resize(10,10);
   ParticleDrawable dr = new ParticleDrawable(this, pimg);
   ParticleInteraction[] pi = {ParticleInteraction.None}; 
   dr.fill(color(255,255,255));
   dr.stroke(color(255,255,255,0));
   ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha};
   dr.setLifeEffect(le);
   
   
   pe.setup(b,pi, GenerationType.AtOnce, 100,dr);
   pe.setInitialBehavior(InitialBehavior.Explosive);
   pe.setInitialBehaviorArg(100);
   pe.setOrigin(width/2,height/2);
   pe.applySpeedFactor(.05);
   pe.activate();
}


void draw(){
  
  background(255);
  pe.update();

}
