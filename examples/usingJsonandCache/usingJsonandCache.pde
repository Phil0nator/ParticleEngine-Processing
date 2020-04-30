import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;
import ParticleEngine.caches.LightCache;

ParticleEngine pe;
LightCache c1;


void setup(){

  size(700,700);
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 10,10);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha, ParticleLifeEffect.Negativered};
  dr.setLifeEffect(le);
  dr.fill(color(255,55,2));
  dr.stroke(color(255,255,255,0));
  pe = new ParticleEngine(this);
  pe.setup(loadJSONObject("data.json"),dr);
  pe.activate();
  println("caching finished");
}


void draw(){
  
  background(0);
  pe.update();
  
}
