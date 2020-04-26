package ParticleEngine.caches;

import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * The LightCache class is used to create faster caches based on particle engines.
 * HeavyCaches use images, and take a long time to produce. Light caches take less time to produce,
 * but may run somewhat slower than heavy caches depending on complexity.
 *
 * For particle effects with more than 200 particles, one should probably use a heavy cache.
 * Otherwise, a light cache will probably be more efficient.
 *
 * @see ParticleEngine
 * @see HeavyCache
 *
 */
public class LightCache {

    public PVector[][] locs;
    private int[] colorCache;
    private PApplet p;
    public ParticleDrawable dr;
    private int x;
    private int y;
    public int frame = 0;
    private boolean stopAfterPlay = false;
    /**
     * Initialize a light cache with its parent
     * @param parent your applet
     */
    public LightCache(PApplet parent){
        p=parent;
    }

    /**
     * Fill in fields based on data in a ParticleEngine
     * @param e engine
     * @param frames number of frames to grab from the engine
     * @see ParticleEngine
     */
    public final void createFromEngine(ParticleEngine e, int frames){
        locs = e.produceLightCache(frames);
        dr = e.drawer;
        colorCache = dr.getColorCache(frames);
    }

    /**
     * Restart the animation
     */
    public final void restart(){
        frame= 0;
    }

    /**
     * Set the center location for the cache to play at
     * (This won't start the animation)
     * @param x coord
     * @param y coord
     */
    public final void playAt(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Determine weather the animation should repeat or stop after it reaches
     * its last frame
     * @param val true = stop, false = repeat
     */
    public final void setStopAfterPlay(boolean val){
        stopAfterPlay = val;
    }

    /**
     * This will draw the next frame of the animation to the screen
     * (Usually should be put in either the drawloop, or somewhere else where you're drawing things)
     */
    public final void update(){
        if(frame==-1)return;
        for(int i = 0 ; i < locs[frame].length;i++){
            try {
                dr.drawCached((int) locs[frame][i].x, (int) locs[frame][i].y, colorCache[(int) locs[frame][i].z]);
            }catch(Exception e){

            }
        }
        frame++;
        if(frame>=locs.length){
            if(stopAfterPlay){

                frame=-1;
            } else{
                frame=0;
            }
        }
    }

    public final int[] getFrameData(int f){
        int minx = 99999;
        int miny = 99999;
        int maxx = 0;
        int maxy = 0;
        for(int i = 0 ; i < locs[frame].length;i++){
            //dr.draw((int)locs[frame][i].x,(int)locs[frame][i].y,(int)locs[frame][i].z);
            if(locs[frame][i].x<minx){
                minx = (int)locs[frame][i].x;
            }
            if(locs[frame][i].x>maxx){
                maxx=(int)locs[frame][i].x;
            }
            if(locs[frame][i].y>maxy){
                maxy=(int)locs[frame][i].y;
            }
            if(locs[frame][i].y<miny){
                miny=(int)locs[frame][i].y;
            }
        }
        int[] out = {PApplet.constrain(minx,0,p.width),PApplet.constrain(miny,0,p.height),PApplet.constrain(maxx,0,p.width),PApplet.constrain(maxy,0,p.height)};

        return out;
    }

    public final void fillframe(PGraphics g, int f){
        for(int i = 0 ; i < locs[f].length;i++){
            dr.draw(g,(int)locs[f][i].x,(int)locs[f][i].y,(int)locs[f][i].z);
        }
    }

    /**
     * Use dispose once you have finished using the cache.
     * Calling any other methods after dispose() will cause a nullPointerException
     */
    public final void dispose(){

        this.locs = null;
        this.colorCache = null;

    }
}
