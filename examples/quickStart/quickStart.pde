import ParticleEngine.Behavior.*;
import ParticleEngine.Compression.*;
import ParticleEngine.*;
import ParticleEngine.Particle.*;
import ParticleEngine.Visual.*;
import ParticleEngine.Visual.properties.*;
import ParticleEngine.caches.*;
ParticleEngine pe;
void setup(){

  size(500,500);
  ParticleEngineFactory pef = new ParticleEngineFactory(this);
  pe = pef.getSimpleTrail();
  pe.activate();
}

void draw(){
  background(255);
  pe.update();
  pe.createBatch(5);
  pe.setOrigin(mouseX,mouseY);
}
void mousePressed(){
  
  
}
