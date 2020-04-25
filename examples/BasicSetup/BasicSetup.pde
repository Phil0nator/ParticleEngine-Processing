import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;


ParticleEngine pe;

void setup(){
   
  size(500,500);
  pe = new ParticleEngine(this);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Positivegreen};
  ParticleBehavior[] behavors = {ParticleBehavior.Particle_Smooth_Random};
  ParticleInteraction[] interactions = {ParticleInteraction.Particle_Window_Collision};
  ParticleDrawable dr = new ParticleDrawable(this, createShape(RECT, 5,5,5,5));
  dr.fill(color(255,0,0));
  dr.stroke(color(0,0,0,0));
  dr.setLifeEffect(le);
  pe.setup(behavors,interactions,GenerationType.AtOnce, 1000, dr);
  pe.setOrigin(width/2,height/4);
  pe.setInitialBehavior(InitialBehavior.Static);
  pe.setInitialBehaviorArg(150);
  pe.applySpeedFactor(.1);
  pe.setRandomNoiseDifferencial(10);
  pe.activate();
 
}


void draw(){
   
  background(255);
  pe.update();
}
