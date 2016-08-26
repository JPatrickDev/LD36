package uk.co.jdpatrick.JEngine.Level;

import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.Entity.Entity;

import java.util.ArrayList;

/**
 * @author Jack Patrick
 */
public abstract class Level {

    /**
     * The width of the Level
     */
    protected int width;

    /**
     * The height of the Level
     */
    protected int height;

    /**
     * List of Entities in the Level
     */
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    /**
     * The Camera used by the level
     */
    public Camera c;

    /**
     * Creates an empty Level
     *
     * @param width  The width of the Level
     * @param height The height of the Level
     */
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Loads a level from File
     *
     * @param file The location of the file to load from
     */
    public Level(String file) {
        load(file);
    }

    /**
     * Load a level
     *
     * @param file The location of the file to load from
     */
    protected abstract void load(String file);

    /**
     * Draw the Level
     *
     * @param g The Graphics object to use for drawing
     */
    public abstract void render(Graphics g);

    /**
     * Update the level.
     */
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
    }

    /**
     * Get the width of the Level
     *
     * @return The width of the Level
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the Level
     *
     * @return the height of the Level
     */
    public int getHeight() {
        return height;
    }
}
