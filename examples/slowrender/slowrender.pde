import ParticleEngine.Behavior.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;
import ParticleEngine.caches.*;
import java.time.Instant;
import ParticleEngine.Compression.*;

ParticleEngine pe;
HeavyCache hc;
LightCache lc;

void setup(){

  size(700,700, P2D);
  ParticleDrawable dr = new ParticleDrawable(this, Shape.Ellipse, 5,5);
  ParticleLifeEffect[] le = {ParticleLifeEffect.Negativered, ParticleLifeEffect.Negativegreen,ParticleLifeEffect.Negativealpha};
  dr.setLifeEffect(le);
  dr.fill(color(100,200,255));
  dr.stroke(color(255,255,255,0));
  pe = new ParticleEngine(this);
  pe.setup(loadJSONObject("data.json"),dr);
  pe.activate();
    
  //lc = new LightCache(this);
  //lc.createFromEngineVerbose(pe,1200);
  try{
    //lc.saveFile("compressedCache.cache");
  }catch(Exception e){
    System.out.println("error");
    e.printStackTrace();
  }
  lc = new LightCache(this);
  lc.loadFromFile("compressedCache.cache");
  lc.createColorCache(dr);
  
  
  println("caching finished");
}

long lastBatch = Instant.now().toEpochMilli();
void draw(){
  
  background(0);
  pe.update();
  try{
    //lc.update();
  }catch(Exception e){
    e.printStackTrace();
  }
  //if(Instant.now().toEpochMilli()-lastBatch > 200){
  //  lastBatch = Instant.now().toEpochMilli();
  //  pe.createBatch(1);
  //}
  
  
  
}
