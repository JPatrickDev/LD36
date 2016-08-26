package uk.co.jdpatrick.JEngine.Entity;

import org.newdawn.slick.Graphics;

/**
 * A basic entity, only provides a place to store x and y coordinates.
 *
 * @author Jack Patrick
 */
public abstract class Entity {

    /**
     * X coordinate of the entity
     */
    private int x;
    /**
     * Y coordinate of the entity
     */
    private int y;

    /**
     * Create a new Entity
     *
     * @param x The x coordinate of the entity
     * @param y The y coordinate of the entity
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the entity to the screen
     *
     * @param g The graphics object to use for rendering
     */
    public abstract void render(Graphics g);

    /**
     * Update the entity(Location, handle input, etc)
     */
    public abstract void update();


    /**
     * Get the x coordinate
     *
     * @return The x coordinate of the Entity
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate
     *
     * @return The y coordinate of the Entity
     */
    public int getY() {
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
