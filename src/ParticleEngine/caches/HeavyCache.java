package ParticleEngine.caches;

import ParticleEngine.ParticleEngine;
import processing.core.*;

import javax.crypto.interfaces.PBEKey;

/**
 * HeavyCache is a wrapper for a light cache.
 * HeavyCaches are used for very expensive particle effects with many particles.
 * Producing the heavy cache will take a very long time, as it will need to render the animation
 * to each frame. HeavyCache objects will also be very memory intensive, and using too many at once might
 * cause you to run out of memory. So, only use them when and if you really need to.
 *
 * However, it will be much faster than just running a particle engine because it won't have to do any math.
 *
 * @see LightCache
 * @see ParticleEngine
 *
 */
@Deprecated
public class HeavyCache {

    private LightCache l;
    private PApplet parent;
    public PImage[] frames;
    private PVector locs[];
    private int x,y;
    private int frame = 0;

    public HeavyCache(PApplet p){
        parent = p;
    }

    public final void createFromEngine(ParticleEngine engine, int f){
        try {
            l = new LightCache(parent);
            l.createFromEngine(engine, f);
            this.frames = new PImage[f];
            locs = new PVector[f];
            for (int i = 0; i < f; i++) {
                int[] dat = l.getFrameData(i);
                int w = dat[2] - dat[0];
                int h = dat[3] - dat[1];
                int sx = dat[0];
                int sy = dat[1];
                PGraphics buffer = parent.createGraphics(parent.width,parent.height, PConstants.P2D);
                locs[i] = new PVector(sx, sy);
                buffer.beginDraw();
                l.fillframe(buffer, i);
                buffer.endDraw();
                PImage c;
                c = buffer.get();
                buffer.dispose();
                frames[i]=c;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public final void free(){
        this.frames = null;
        this.locs = null;
    }

    public final void playAt(int x, int y){
        this.x=x;
        this.y=y;
    }

    public final void update(){


        parent.image(frames[frame], 0, 0);
        frame++;
        if(frame>=frames.length){
            frame = 0;
        }
        parent.updatePixels();
    }




}
