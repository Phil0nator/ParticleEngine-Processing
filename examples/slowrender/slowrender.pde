import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;
import ParticleEngine.caches.*;

ParticleEngine pe;
HeavyCache hc;
LightCache lc;

void setup(){

  size(700,700, P2D);
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 10,10);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativered};
  dr.setLifeEffect(le);
  dr.fill(color(100,200,255));
  dr.stroke(color(255,255,255,0));
  pe = new ParticleEngine(this);
  pe.setup(loadJSONObject("data.json"),dr);
  pe.activate();
  //lc = new LightCache(this);
  //lc.createFromEngine(pe,1000);

  println("caching finished");
}


void draw(){
  
  background(0);
  pe.update();
  try{
    //lc.update();
  }catch(Exception e){
    e.printStackTrace();
  }
  
  
  
  
}
