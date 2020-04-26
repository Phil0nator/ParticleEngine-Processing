import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;


ParticleEngine pe;

void setup(){
   
  size(500,500);
  pe = new ParticleEngine(this);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativeblue,ParticleLifeEffect.Positivegreen,ParticleLifeEffect.Negativealpha};
  //ParticleLifeEffect[] le = {};  
  ParticleBehavior[] behavors = {ParticleBehavior.Particle_Smooth_Random};
  ParticleInteraction[] interactions = {ParticleInteraction.Particle_Window_Collision};
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 10,10);
  dr.fill(color(255,0,255,255));
  dr.stroke(color(255,255,255,0));
  dr.setLifeEffect(le);
  pe.setup(behavors,interactions,GenerationType.OverTime, -1, dr);
  pe.setAmountPerFrame(5);
  pe.setOrigin(width/2,height/4);
  pe.setInitialBehavior(InitialBehavior.Explosive);
  pe.setInitialBehaviorArg(5);
  pe.setSpeedFactor(.1);
  pe.setRandomNoiseDifferencial(10); //<>//
  pe.setParticleMaxLife(255);
  
  pe.activate();
}


void draw(){
   
  background(255);
  pe.update();
  println(pe.particles.get(pe.particles.size()-1).life);
  pe.setOrigin(mouseX,mouseY);
}
