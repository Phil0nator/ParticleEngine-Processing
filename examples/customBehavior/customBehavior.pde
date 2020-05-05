import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;
import ParticleEngine.caches.*;
import java.time.Instant;
HeavyCache hc;
LightCache lc;

ParticleEngine createEngine(){
  ParticleEngine pe;
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 15,15);
  ParticleLifeEffect[] le = {};
  dr.setLifeEffect(le);
  dr.fill(color(255,100,100));
  dr.stroke(color(255,255,255,0));
  pe = new ParticleEngine(this);
  ColorKeyframe keyframes = new ColorKeyframe();
  keyframes.loadKeyframes(this, "color.json");
  dr.addKeyframes(keyframes);
  try{
    pe.setup(loadJSONObject("data.json"),dr);
  }catch(Exception e){
    e.printStackTrace();
  }
  pe.activate();
  return pe;

}

long last = Instant.now().toEpochMilli();
ParticleSystem sys;

void setup(){

  size(500,500);
  sys = new ParticleSystem(this);
  sys.setTemplateEngine(createEngine());
  try{
    sys.setup(loadJSONObject("system.json"));
  }catch(Exception e){
    e.printStackTrace();
    exit();
  }
  sys.handle.activate();
  //lc = new LightCache(this);
  //lc.createFromEngine(pe,5000);

}


void draw(){
  
  background(0,0,0,200);
  sys.run();
  
  
}
