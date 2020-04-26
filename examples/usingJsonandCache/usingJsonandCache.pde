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
  ParticleDrawable dr = new ParticleDrawable(this, createShape(RECT, 5,5,5,5));
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativealpha};
  dr.setLifeEffect(le);
  dr.fill(color(255,255,255));
  dr.stroke(color(0,0,0));
  pe = new ParticleEngine(this);
  pe.setup(loadJSONObject("data.json"),dr);
  pe.activate();
  c1 = new LightCache(this);
  c1.createFromEngine(pe, 255);
  c1.playAt(width/2,height/2);
  println("caching finished");
}


void draw(){
  
  background(0);
  c1.update();
  
}
