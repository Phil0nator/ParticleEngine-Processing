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
  ParticleInteraction[] interactions = {ParticleInteraction.Particle_Window_Collision};
  ParticleDrawable dr = new ParticleDrawable(this, createShape(RECT, 1,1,1,1));
  dr.fill(color(0,0,0,255));
  dr.stroke(color(0,0,0));
  dr.setLifeEffect(le);
  pe.setup(behavors,interactions,GenerationType.AtOnce, 1000, dr);
  pe.setOrigin(width/2,height/4);
  pe.setInitialBehavior(InitialBehavior.Explosive);
  pe.setInitialBehaviorArg(15);
  pe.activate();
  
}


void draw(){
   
  background(255);
  pe.update();
}
