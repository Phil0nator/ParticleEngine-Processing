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

  size(500,500);
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 1,1);
  ParticleLifeEffect[] le = {};
  dr.setLifeEffect(le);
  dr.fill(color(255,100,100));
  dr.stroke(color(255,255,255,0));
  pe = new ParticleEngine(this);
  ColorKeyframe keyframes = new ColorKeyframe();
  keyframes.loadKeyframes(this, "color.json");
  for(int i = 0 ; i < keyframes.cache.length;i++){
    println(red(keyframes.cache[i]),green(keyframes.cache[i]), blue(keyframes.cache[i]), alpha(keyframes.cache[i]));
  }
  dr.addKeyframes(keyframes);
  try{
    pe.setup(loadJSONObject("data.json"),dr);
  }catch(Exception e){
    e.printStackTrace();
  }
  pe.activate();
  //lc = new LightCache(this);
  //lc.createFromEngine(pe,5000);

  println("caching finished");
}


void draw(){
  
  background(0,0,0,200);
  
  try{
    pe.update();
  }catch(Exception e){
    e.printStackTrace();
  }
  
  
}
