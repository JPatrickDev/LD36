package uk.co.jdpatrick.JEngine.AI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Mover;
import uk.co.jdpatrick.JEngine.Entity.Entity;

/**
 * Created by Jack on 12/10/2014.
 */
public abstract class AIEntity extends Entity implements Mover {

    /**
     * Create a new Entity
     *
     * @param x The x coordinate of the entity
     * @param y The y coordinate of the entity
     */
    public AIEntity(int x, int y) {
        super(x, y);
    }


    /**
     * @return The x tile position of the Entity
     */
    public abstract int getTileX();

    /**
     * @return The y tile position of the Entity
     */
    public abstract int getTileY();


    /**
     * Called when the AI completes
     */
    public abstract void aiComplete();
}
