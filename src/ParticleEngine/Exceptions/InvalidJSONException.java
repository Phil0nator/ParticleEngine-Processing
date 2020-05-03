package ParticleEngine.Exceptions;

import ParticleEngine.Visual.ParticleDrawable;
import processing.core.PApplet;
import processing.data.JSONObject;

/**
 * InvalidJSONException is thrown when the JSON used to initiate an engine or keyframe is incorrectly formatted
 * @see ParticleEngine.ParticleEngine#setup(JSONObject, ParticleDrawable)
 * @see ParticleEngine.Visual.ColorKeyframe#loadKeyframes(PApplet, String)
 */
public class InvalidJSONException extends Exception {

    public InvalidJSONException(String msg){
        super(msg);
    }

}
