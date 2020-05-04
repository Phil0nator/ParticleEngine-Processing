package ParticleEngine;


import ParticleEngine.Behavior.*;
import ParticleEngine.Exceptions.InvalidJSONException;
import ParticleEngine.Particle.Particle;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;

import static java.lang.System.exit;

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
 * All properties of a ParticleEngine can be set using a JSON file
 * @see ParticleEngine#setup(JSONObject, ParticleDrawable)
 *
 */
public class ParticleEngine implements ParticleRunner {

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


	/**
	 * This contains whatever drawable has been set.
	 * You can use this field to change how particles are drawn.
	 * @see ParticleDrawable
	 */
	public ParticleDrawable drawer;
	private GenerationType gen;



	private int particleMaxLife = 255;
	private int count = 0;
	public int[] bounds = {50,50};
	private InitialBehavior initialBehavior = InitialBehavior.Static;
	private float initialBehaviorArg = 0;
	private PVector origin = new PVector(0,0);
	private boolean activated = false;
	private PVector iv = new PVector(0,0);
	public ArrayList<Particle> particles = new ArrayList<Particle>(0);
	private float particleSpeedFactor = 1.f;
	private float randomNoiseDifferencial = 3.7f;
	private int amountPerFrame = 0;
	private ArrayList<CustomParticleBehavior> customParticleBehaviors = new ArrayList<CustomParticleBehavior>(0);


	private float particlemass = 10.0f;

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
		parent.noiseDetail(count);
	}

	/**
	 * Load the properties of the engine through a JSON file.
	 *
	 * Format:
	 *
	 * {
	 *
	 *     "setup":{
	 *         "behaviors":[behaviorA, behaviorB],
	 *         "interactions":[interactionA,interactionB],
	 *         "gentype":gentype,
	 *         "number":how many particles,
	 *
	 *     }
	 *
	 *     "args":{ //(all args are optional)
	 *			"bounds":[w,h],
	 *			"initial":InitialBehavior,
	 *			"initialArg":InitialBehaviorArg,
	 *			"origin":[x,y],
	 *			"IV":[x,y], //Initial Velocity
	 *			"speed":speedFactor,
	 *			"RND":random noise differential,
	 *			"NMF":noise map factor,
	 *			"maxlife":max life of particles,
	 *			"APF":amount per frame
	 *     }
	 *
	 * }
	 *
	 * All of the "args" match mutator methods of this class. Each has its own documentation.
	 * @param params Some json file you have loaded that is formatted correctly
	 * @param dr a Particle drawer
	 * @see ParticleEngine#setup(ParticleBehavior[], ParticleInteraction[], GenerationType, int, ParticleDrawable)
	 * @see ParticleDrawable
	 * @see ParticleEngine
	 * @see InvalidJSONException
	 */
	public final void setup(JSONObject params, ParticleDrawable dr)throws InvalidJSONException{
		try {
			JSONObject set = params.getJSONObject("setup");
			JSONArray pre_b = set.getJSONArray("behaviors");
			behaviors = new ParticleBehavior[pre_b.size()];
			for (int i = 0; i < pre_b.size(); i++) {
				behaviors[i] = ParticleBehavior.valueOf(pre_b.getString(i));
			}
			JSONArray pre_i = set.getJSONArray("interactions");
			interactions = new ParticleInteraction[pre_i.size()];
			for (int i = 0; i < pre_i.size(); i++) {
				interactions[i] = ParticleInteraction.valueOf(pre_i.getString(i));
			}

			gen = GenerationType.valueOf(set.getString("gentype"));

			count = set.getInt("number");
			parent.noiseDetail(count);
			JSONObject args = params.getJSONObject("args");
			if(args.hasKey("bounds")){
				JSONArray pre_bounds = args.getJSONArray("bounds");
				bounds[0] = pre_bounds.getInt(0);
				bounds[1] = pre_bounds.getInt(1);
			}
			if(args.hasKey("initial")){
				initialBehavior = InitialBehavior.valueOf(args.getString("initial"));
			}
			if(args.hasKey("initialArg")){
				initialBehaviorArg = args.getFloat("initialArg");
			}
			if(args.hasKey("origin")){
				JSONArray pre_o = args.getJSONArray("origin");
				origin.x = pre_o.getInt(0);
				origin.y = pre_o.getInt(1);
			}
			if(args.hasKey("IV")){
				JSONArray pre_iv = args.getJSONArray("IV");
				iv.x = pre_iv.getFloat(0);
				iv.y=pre_iv.getFloat(1);
			}
			if(args.hasKey("speed")){
				setSpeedFactor(args.getFloat("speed"));
			}
			if(args.hasKey("RND")){
				setRandomNoiseDifferencial(args.getFloat("RND"));
			}
			if(args.hasKey("NMF")){
				setNoiseMapFactor(args.getFloat("NMF"));
			}
			if(args.hasKey("maxlife")){
				setParticleMaxLife(args.getInt("maxlife"));
			}
			if(args.hasKey("APF")){
				setAmountPerFrame(args.getInt("APF"));
			}
			if(args.hasKey("particleMass")){
				setParticlemass(args.getFloat("particleMass"));
			}
		}catch (Exception e){
			throw new InvalidJSONException(e.getLocalizedMessage());
		}
		this.drawer = dr;
		return;
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
	@Override
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
	 * Set the mass for particles
	 * @param particlemass new mass
	 */
	public final void setParticlemass(float particlemass) {
		this.particlemass = particlemass;
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
	public final void setSpeedFactor(float f){
		particleSpeedFactor=f;
	}

	/**
	 * Set the particle drawer of an engine (or change it if it already exists)
	 * @param drawer new drawer
	 * @see ParticleDrawable
	 */
	public final void setDrawer(ParticleDrawable drawer) {
		this.drawer = drawer;
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

	public final void addCustomBehavior(CustomParticleBehavior n){
		customParticleBehaviors.add(n);
	}


	/**
	 * Set the maximum number of frames for which a particle lives
	 * @param particleMaxLife life in frames (Use 0 to signify that particles should live forever)
	 */
	public void setParticleMaxLife(int particleMaxLife) {
		this.particleMaxLife = particleMaxLife;
	}

	/**
	 * Clear all particles
	 */
	public final void forceEmpty(){
		particles.clear();
	}

	/**
	 * Set the number of particles to produce per frame when using OverTime gentype
	 * @param amt new amount
	 * @see GenerationType
	 * @see GenerationType#OnEvent
	 */
	public final void setAmountPerFrame(int amt){
		amountPerFrame=amt;
	}


	private final int[] getcoords(){
		int[] out = new int[2];
		out[0] = particles.size()%bounds[0];
		out[1] = particles.size()/bounds[0];
		return out;
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
			case NoiseFillSpace:
				int[] c = getcoords();
				np = new Particle(parent, (int)parent.noise(c[0])*bounds[0],(int)parent.noise(c[1])*bounds[1],0,0);
				break;
			case EvenFillSpace:
				np = new Particle(parent, (int)particles.size()%bounds[0],(int)particles.size()/bounds[0],0,0);
			default:
				np = new Particle(parent,(int)origin.x,(int)origin.y,0,0);
		}
		np.parent = this;
		np.applySpeedFactor(particleSpeedFactor);
		np.createRandomSeed(randomNoiseDifferencial);
		np.mapfac = noiseMapFactor;
		np.mass = particlemass;
		np.index = particles.size();
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
	 * Stop the engine from running, even if update() is called
	 */
	public final void deActivate(){
		activated = false;
	}

	/**
	 * Stop the engine from running, even if update is called
	 */
	public final void stop(){
		deActivate();
	}

	/**
	 * Clear existing particles and start the engine again
	 */
	public final void reActivate(){
		forceEmpty();
		activate();
	}

	/**
	 * The Update method must always be ran in your draw method. This is where particles are updated,
	 * and drawn to the screen
	 */
	public final void update(){
		try {
			if (!activated) return;
			if (gen == GenerationType.OverTime) {
				if (particles.size() < count||count == -1) {
					createBatch(amountPerFrame);
				}
			}

			for (int i = 0 ; i < particles.size();i++) {
				Particle p = particles.get(i);
				p.update(behaviors, interactions);
				if(particleMaxLife!=0) {
					if (p.life > particleMaxLife) {
						particles.set(i,null);
						particles.remove(i);
						i--;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * Accessor method for the engine's state
	 * @return if engine is active
	 * @see ParticleEngine#activate()
	 * @see ParticleEngine#deActivate()
	 */
	public final boolean isActivated(){
		return activated;
	}

	/**
	 * This is used to create a batch of size amt of particles.
	 * Only with GenerationType#OnEvent
	 * @param amt number of particles
	 * @see GenerationType
	 */
	public final void createBatch(int amt){

		for(int i = 0 ; i < amt;i++){
			if(particles.size()>count&&count!=-1)return;
			particles.add(createParticle());

		}
	}

	/**
	 * Used internally.
	 * (EXPENSIVE): depending on the number of frames and particles, this could cause you to run out of memory
	 *
	 * Used to generate the data used in a LightCache
	 *
	 * @param frames number of frames to generate
	 * @return cache data
	 * @see ParticleEngine.caches.LightCache
	 */
	public final PVector[][] produceLightCache(int frames, boolean verbose){
		PVector[][] data = new PVector[frames][];
		for(int i = 0 ; i < frames;i++){

			if (gen == GenerationType.OverTime) {
				if (particles.size() < count||count == -1) {
					createBatch(amountPerFrame);
				}
			}
			data[i] = new PVector[particles.size()];
			for (int j = 0 ; j < particles.size();j++) {
				Particle p = particles.get(j);
				data[i][j] = p.updateforcache(behaviors, interactions).copy();
				data[i][j].z = p.life; //store life in z for convenience
				if(particleMaxLife!=0) {
					if (p.life > particleMaxLife) {
						particles.remove(j);
						j--;
					}
				}
			}

			if(verbose){
				System.out.println("Producing light cache: "+i+"/"+frames);
			}


		}
		return data;
	}

	//getters

	/**
	 * Determine if the engine has no particles
	 * @return if the number of particles is equal to zero
	 */
	public final boolean isEmpty(){
		return particles.size()==0;
	}

	@Override
	public void run(){
		update();
	}
}
