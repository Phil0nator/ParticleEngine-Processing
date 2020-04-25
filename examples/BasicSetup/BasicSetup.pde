import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;


ParticleEngine pe;

void setup(){
   
  size(500,500);
  pe = new ParticleEngine(this);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha};
  ParticleBehavior[] behavors = {ParticleBehavior.Normal_Physics};
  ParticleInteraction[] interactions = {ParticleInteraction.InterParticle_Collision};
  ParticleDrawable dr = new ParticleDrawable(this, createShape(RECT, 5,5,5,5));
  dr.fill(color(0,0,0,255));
  dr.stroke(color(0,0,0));
  dr.setLifeEffect(le);
  pe.setup(behavors,interactions,GenerationType.AtOnce, 2, dr);
  pe.setOrigin(width/2,height/2);
  pe.setInitialBehavior(InitialBehavior.Exact_IV);
  pe.activate();
  
}


void draw(){
   
  background(255);
  pe.update();
}
