package ParticleEngine;


import ParticleEngine.Behavior.GenerationType;
import ParticleEngine.Behavior.InitialBehavior;
import ParticleEngine.Behavior.ParticleBehavior;
import ParticleEngine.Behavior.ParticleInteraction;
import ParticleEngine.Particle.Particle;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.*;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;

/**
 *
 */

public class ParticleEngine {

	/* PROCESSING STUFF */

	// myParent is a reference to the parent sketch
	PApplet parent;
	private final static String VERSION = "##library.prettyVersion##";
	public ParticleEngine(PApplet theParent) {
		parent = theParent;
		welcome();
	}
	private void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}
	public String sayHello() {
		return "particle library.";
	}
	/**
	 * return the version of the Library.
	 *
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	/* ~PROCESSING STUFF */

	private ParticleBehavior[] behaviors;
	private ParticleInteraction[] interactions;
	public ParticleDrawable drawer;
	private GenerationType gen;
	public int particleMaxLife = 255;
	private int count = 0;
	public int[] bounds = {50,50};
	private InitialBehavior initialBehavior = InitialBehavior.Static;
	private float initialBehaviorArg = 0;
	private PVector origin = new PVector(0,0);
	private boolean activated = false;
	private PVector iv = new PVector(0,0);
	private ArrayList<Particle> particles = new ArrayList<Particle>(0);

	/**
	 * Setup the properties of the engine
	 * @param behaviors	A list of behaviors the particles will follow
	 * @param interactions A list of interaction types
	 * @param generationType How the particles will be generated
	 * @param count The number of particles
	 * @param drawer A ParticleDrawable object to determine how to draw the particles
	 * @see ParticleDrawable
	 * @see ParticleInteraction
	 * @see ParticleBehavior
	 * @see GenerationType
	 */
	public final void setup(ParticleBehavior[] behaviors, ParticleInteraction[] interactions, GenerationType generationType, int count, ParticleDrawable drawer){
		this.behaviors=behaviors;
		this.interactions=interactions;
		this.drawer=drawer;
		this.drawer.setParent(parent);
		this.gen=generationType;
		this.count = count;
		this.bounds[0] = parent.width;
		this.bounds[1] = parent.height;
	}

	public final void setBounds(int width, int height){
		bounds[0]=width;
		bounds[1]=height;
	}

	public final void setInitialBehavior(InitialBehavior initialBehavior){
		this.initialBehavior=initialBehavior;
	}

	public final void setOrigin(int x, int y){
		origin = new PVector(x,y);
	}

	public final void setInitialVelocity(int x, int y){
		iv = new PVector(x,y);
	}

	private Particle createParticle(){
		Particle np = new Particle(parent,0,0,0,0);
		switch (initialBehavior){
			case Static:
				np = new Particle(parent,(int)origin.x,(int)origin.y,0,0);
				break;
			case Explosive:
				PVector dir = new PVector(parent.random(-initialBehaviorArg,initialBehaviorArg),parent.random(-initialBehaviorArg,initialBehaviorArg));
				np = new Particle(parent, (int)origin.x,(int)origin.y,dir.x,dir.y);
				break;
			case Implosive:
				break;
			case Exact_IV:
				np = new Particle(parent,(int)origin.x,(int)origin.y,iv.x,iv.y);
				break;
			case RandomlyVarying_IV:
				np = new Particle(parent,(int)origin.x,(int)origin.y,iv.x+parent.random(-initialBehaviorArg,initialBehaviorArg),iv.y+parent.random(-initialBehaviorArg,initialBehaviorArg));
				break;
			default:
				np = new Particle(parent,(int)origin.x,(int)origin.y,0,0);
		}
		np.parent = this;
		return np;

	}

	public final void activate(){
		activated = true;
		if(gen == GenerationType.AtOnce){
			for(int i = 0 ; i < count;i++){

				particles.add(createParticle());

			}
		}


	}

	public final void update(){
		try {
			if (!activated) return;
			if (gen == GenerationType.OverTime) {
				if (particles.size() < count) {
					particles.add(createParticle());
				}
			}

			for (Particle p : particles) {
				p.update(behaviors);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
