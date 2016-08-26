package uk.co.jdpatrick.JEngine.Sound;


import org.newdawn.slick.Sound;

import java.util.HashMap;

/**
 * SoundEngine allows you to store {@code Sound} objects and play them back
 *
 * @author Jack Patrick
 */
public class SoundEngine {

    /**
     * The active {@code SoundEngine} only one can be used at a time
     */
    private static SoundEngine instance;

    /**
     * HashMap of the sounds, stored with a String key
     */
    private HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    /**
     * If the sound engine is disabled, use to mute or unmute the sound
     */
    private boolean disabled = false;

    /**
     * Get the active sound engine or creates one and returns it.
     *
     * @return The active SoundEngine
     */
    public static SoundEngine getInstance() {
        if (instance == null) {
            return instance = new SoundEngine();
        } else {
            return instance;
        }
    }

    /**
     * Add a sound to the SoundEngine
     *
     * @param key   The key associated with the {@code Sound}
     * @param sound The {@code Sound} object to store
     */
    public void addSound(String key, Sound sound) {
        sounds.put(key, sound);
    }


    /**
     * Get the Sound associated with the given key
     *
     * @param key The key to the sound
     * @return The sound associated with the key or null if the sound can't be found
     */
    public Sound getSound(String key) {
        if (sounds.containsKey(key)) {
            return sounds.get(key);
        } else {
            System.out.println("Error: The sound " + key + " does not exist");
            return null;
        }
    }


    /**
     * Set if the SoundEngine is disabled
     * If the SoundEngine is disabled, sounds will not be played, but sounds can stil be added
     *
     * @param disable if the SoundEngine should be disabled or not
     */
    public void setDisabled(boolean disable) {
        if (disable)
            System.out.println("Disabling sound");
        else
            System.out.println("Enabling sound");
        this.disabled = disable;
    }

    /**
     * Returns the state of the SoundEngine
     *
     * @return If the SoundEngine is disabled
     */
    public boolean isDisabled() {
        return this.disabled;
    }

    /**
     * Play the sound associated with the given key
     *
     * @param key The key associated with the {@code Sound}
     */
    public void play(String key) {
        if (disabled)
            return;
        if (sounds.containsKey(key)) {
            sounds.get(key).play();
        } else {
            System.out.println("Error: The sound " + key + " does not exist");
        }
    }

}