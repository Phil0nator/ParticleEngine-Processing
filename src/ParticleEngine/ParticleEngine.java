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
 *	The ParticleEngine class wraps together ParticleDrawable, Particle, and applies
 *  many properties.
 * @see ParticleDrawable
 *
 *  Properties are defined in both specific numbers, and enums
 *
 * @see ParticleBehavior
 * @see ParticleInteraction
 * @see GenerationType
 *
 *
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
	private float particleSpeedFactor = 1.f;
	private float randomNoiseDifferencial = 3.7f;



	private float noiseMapFactor = 5.f;
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

	/**
	 * Set the bounds of the system.
	 * When using ParticleInteraction#Particle_Window_Collision this limits where they can go
	 * Otherwise, they will be able to leave these bounds
	 * @param width width of container
	 * @param height height of container
	 */
	public final void setBounds(int width, int height){
		bounds[0]=width;
		bounds[1]=height;
	}

	/**
	 * Setup how particles will behave when spawned
	 *
	 * More information can be specified in ParticleEngine#setInitialBehaviorArg(float)
	 * @param initialBehavior InitialBehavior enum
	 * @see InitialBehavior
	 * @see ParticleEngine#setInitialBehaviorArg(float)
	 */
	public final void setInitialBehavior(InitialBehavior initialBehavior){
		this.initialBehavior=initialBehavior;
	}

	/**
	 * Determine the origin of the system
	 * @param x coord
	 * @param y coord
	 */
	public final void setOrigin(int x, int y){
		origin = new PVector(x,y);
	}

	/**
	 * Determine the specified initial velocity of particles
	 * (Not normalized)
	 *
	 * This will have no affect unless you are using Exact_IV or Randomly_Varying_IV InitialBehaviors
	 *
	 * @param x velocity x
	 * @param y velocity y
	 * @see InitialBehavior
	 * @see ParticleEngine#setInitialBehavior(InitialBehavior)
	 *
	 */
	public final void setInitialVelocity(int x, int y){
		iv = new PVector(x,y);
	}

	/**
	 * Set more specific information about how particles spawn
	 * @param val value
	 * @see InitialBehavior
	 */
	public final void setInitialBehaviorArg(float val){
		initialBehaviorArg=val;
	}

	/**
	 * Slow down or speed up particles.
	 *
	 *
	 * Ex: 0 = stopped
	 * Ex: 1 = normal
	 * Ex: .5 = 50% speed
	 *
	 * @param f value
	 */
	public final void applySpeedFactor(float f){
		particleSpeedFactor=f;
	}

	/**
	 * Set the amount of difference between particles following noise patterns.
	 * Should be between 5 and 20 in most cases
	 * @param f value
	 */
	public final void setRandomNoiseDifferencial(float f){
		randomNoiseDifferencial=f;
	}

	/**
	 * Determines the size to which the noise values are mapped
	 * @param noiseMapFactor factor
	 */
	public final void setNoiseMapFactor(float noiseMapFactor) {
		this.noiseMapFactor = noiseMapFactor;
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
		np.applySpeedFactor(particleSpeedFactor);
		np.createRandomSeed(randomNoiseDifferencial);
		np.mapfac = noiseMapFactor;
		return np;

	}

	/**
	 * Begin particle production
	 */
	public final void activate(){
		activated = true;
		if(gen == GenerationType.AtOnce){
			for(int i = 0 ; i < count;i++){

				particles.add(createParticle());

			}
		}


	}

	/**
	 * The Update method must always be ran in your draw method. This is where particles are updated,
	 * and drawn to the screen
	 */
	public final void update(){
		try {
			if (!activated) return;
			if (gen == GenerationType.OverTime) {
				if (particles.size() < count) {
					particles.add(createParticle());
				}
			}

			for (Particle p : particles) {
				p.update(behaviors, interactions);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * This is used to create a batch of size amt of particles.
	 * Only with GenerationType#OnEvent
	 * @param amt number of particles
	 * @see GenerationType
	 */
	public final void createBatch(int amt){

		for(int i = 0 ; i < amt;i++){
			if(particles.size()>count)return;
			particles.add(createParticle());

		}
	}

}
