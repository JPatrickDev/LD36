package uk.co.jdpatrick.JEngine.AI.Movement;

import org.newdawn.slick.geom.Point;

/**
 * Created by Jack on 14/10/2014.
 */
public abstract class MovementType {

    /**
     * The Tilesize
     */
    public int tileSize = -1;

    /**
     * Create a new MovementType
     * @param tileSize The size of tiles in the game world
     */
    public MovementType(int tileSize){
        this.tileSize = tileSize;
    }

    /**
     * Calculate the next position
     *
     * @param target The target
     * @param current The current position
     * @return The new position
     */
    public abstract Point nextPosition(Point target,Point current);
}
