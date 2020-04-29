package ParticleEngine.caches;

import ParticleEngine.Compression.Compressor;
import ParticleEngine.ParticleEngine;
import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.io.*;
import java.util.zip.DeflaterOutputStream;

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
    private boolean useColorCache = true;

    public final void setUseColorCache(boolean f){
        useColorCache=f;
    }

    /**
     * Initialize a light cache with its parent
     * @param parent your applet
     */
    public LightCache(PApplet parent){
        p=parent;
    }

    /**
     * Save the contents of a cache into a file, which can then be loaded later
     * @param path where to save the file relative to the sketch
     * @see LightCache#loadFromFile(String)
     */
    public final void saveFile(String path){
        try {
            OutputStream o = p.createOutput(path);
            PrintWriter p = new PrintWriter(o);
            p.println(locs.length);
            for (PVector[] Locs : locs) {

                for (PVector l : Locs) {
                    p.print(Compressor.compress(l)+"%");
                }
                p.print("\n");
                p.flush();
            }
            Compressor.compressFile(this.p.sketchPath()+"/"+path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Load from a file
     * @param path path to file
     * @see LightCache#saveFile(String)
     */
    public final void loadFromFile(String path){
        try{

            Compressor.decompressFile(this.p.sketchPath()+"/"+path);

            BufferedReader reader = p.createReader(path);
            locs = new PVector[Integer.valueOf(reader.readLine())][];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(" ", "");
                String[] vecs = line.split("%");
                locs[i] = new PVector[vecs.length];
                int j = 0;
                for(String vec : vecs){
                    String[] coords = vec.split(",");
                    if(coords.length<2)continue;
                    locs[i][j] = Compressor.decompress(vec);
                    j++;
                }
                i++;
            }

            Compressor.compressFile(this.p.sketchPath()+"/"+path);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Fill in fields based on data in a ParticleEngine
     * @param e engine
     * @param frames number of frames to grab from the engine
     * @see ParticleEngine
     * @see LightCache#createFromEngineVerbose(ParticleEngine, int)
     */
    public final void createFromEngine(ParticleEngine e, int frames){
        locs = e.produceLightCache(frames, false);
        dr = e.drawer;
        if(useColorCache)
        colorCache = dr.getColorCache(frames);
    }

    /**
     * Fill in fields based on data in a ParticleEngine, printing out progress
     * @param e engine
     * @param frames number of frames to render from the engine
     * @see ParticleEngine
     * @see LightCache#createFromEngine(ParticleEngine, int)
     */
    public final void createFromEngineVerbose(ParticleEngine e, int frames){
        locs = e.produceLightCache(frames, true);
        dr = e.drawer;
        if(useColorCache)
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
     * Use to create a color cache if you are loading the cache from a file
     * (This is only needed if you are loading a file)
     * (This must be used after you've loaded from a file)
     * @param dr drawable
     * @see LightCache#saveFile(String)
     * @see LightCache#loadFromFile(String)
     * @see ParticleDrawable
     */
    public final void createColorCache(ParticleDrawable dr){
        colorCache = dr.getColorCache(locs.length);
        this.dr = dr;
    }

    /**
     * This will draw the next frame of the animation to the screen
     * (Usually should be put in either the drawloop, or somewhere else where you're drawing things)
     */
    public final void update(){
        if(frame==-1)return;
        for(int i = 0 ; i < locs[frame].length;i++){
            try {
                if(useColorCache) {
                    dr.drawCached((int) locs[frame][i].x, (int) locs[frame][i].y, colorCache[(int) locs[frame][i].z]);
                }else{
                    dr.draw((int) locs[frame][i].x, (int) locs[frame][i].y, (int) locs[frame][i].z);
                }
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
