package ParticleEngine.Visual;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.security.Key;
import java.util.ArrayList;

/**
 * The ColorKeyframe class allows you to specify how the colors of particles should change over time.
 * The best way to create keyframes is by writing them out into JSON, and using loadKeyframes()
 * @see ColorKeyframe#loadKeyframes(PApplet, String)
 *
 * However, you can also manually push new keyframes in your java code using pushKeyframe()
 * @see ColorKeyframe#pushKeyframe(int, int)
 * Be aware that with this option you will have to manually call cacheFrames() to collect the color data.
 *
 * ColorKeyframes should be applied to a ParticleDrawable object
 * @see ParticleDrawable
 * @see ParticleDrawable#addKeyframes(ColorKeyframe)
 */
public class ColorKeyframe {

    class Keyframe{

        public int color;
        public int frame;

        public Keyframe(int color, int frame) {
            this.color = color;
            this.frame = frame;
        }
    }

    ArrayList<Keyframe> keyframes = new ArrayList<Keyframe>();
    public int[] cache;


    private final Keyframe parseKeyframe(JSONObject json, PApplet parent){
        JSONArray c = json.getJSONArray("c");
        int color = parent.color(c.getInt(0),c.getInt(1),c.getInt(2));
        int frame = json.getInt("f");
        return new Keyframe(color,frame);

    }

    /**
     * Load keyframe data from a json file
     *
     * Format:
     *
     * {
     *
     *  "frames":[
     *      {"c":[r,g,b],"f":frame},
     *      {"c":[r,g,b],"f":frame}
     *  ]
     *
     * }
     *
     *
     * @param parent PApplet
     * @param path json file
     *
     *
     *
     *
     *
     */
    public final void loadKeyframes(PApplet parent, String path){
        JSONObject json = parent.loadJSONObject(path);
        JSONArray frames = json.getJSONArray("frames");
        for(int i = 0 ; i < frames.size();i++){
            keyframes.add(parseKeyframe(frames.getJSONObject(i),parent));
        }
        cacheFrames(parent);
    }

    /**
     * Push a new keyframe to the list.
     * Be aware that you will have to manually call cacheFrames() to collect the color data.
     * @param color the color of the keyframe
     * @param frame the frame number
     * @see ColorKeyframe#cacheFrames(PApplet)
     */
    public final void pushKeyframe(int color, int frame){
        keyframes.add(new Keyframe(color,frame));
    }

    public final void cacheFrames(PApplet parent){
        int maxframe = keyframes.get(keyframes.size()-1).frame;
        int minframe = keyframes.get(0).frame;
        int framescached = 0;
        cache = new int[maxframe];
        for(int i =0;i<minframe;i++){
            cache[i] = parent.color(0);
            framescached++;
        }
        for(int i = 0;i < keyframes.size()-1;i++){
            Keyframe topframe = keyframes.get(i+1);
            Keyframe botframe = keyframes.get(i);
            int framedist = topframe.frame - botframe.frame;
            float tr = parent.red(topframe.color);
            float tg = parent.green(topframe.color);
            float tb = parent.blue(topframe.color);
            float br = parent.red(botframe.color);
            float bg = parent.green(botframe.color);
            float bb = parent.blue(botframe.color);
            float dr = tr-br;
            float dg = tg-bg;
            float db = tb-bb;

            for(int j = 0; j < framedist;j++){
                cache[j+framescached] = parent.color(br + (dr * ((float)j/framedist)),bg + (dg * ((float)j/framedist)),bb + (db * ((float)j/framedist)));
            }
            framescached += framedist;

        }
    }

    public final int getColorAt(int frame){
        if(frame < cache.length){
            return cache[frame];
        }
        return cache[cache.length-1];
    }

}
