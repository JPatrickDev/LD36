package uk.co.jdpatrick.JEngine.AI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.AI.Movement.MovementType;

/**
 * Created by Jack on 13/10/2014.
 */
public class AIPatrol extends AI {



    /**
     * The target Point
     */
    private Point target;

    /**
     *  The MovementType to use
     */
    public MovementType movementType;

    /**
     * Create a new AIPathfinder
     *
     * @param owner The owner of the AI
     * @param movementType The type of movement to use
     */
    public AIPatrol(AIEntity owner,MovementType movementType) {
        this.owner = owner;
        this.movementType = movementType;
    }


    /**
     * Update the AI, attempting to move to the next step
     */
    @Override
    public void update() {
        step();
    }

    /**
     * The collision box of the target
     */
    Rectangle rtarget = null;

    /**
     * Move towards the current step and check if the step has been reached
     */


    private void step(){

    }

    /**
     * Render the debug for the AI, including the current step and path to take.
     * @param graphics The graphics object to draw with
     */
    @Override
    public void renderInfo(Graphics graphics) {

    }
    }
