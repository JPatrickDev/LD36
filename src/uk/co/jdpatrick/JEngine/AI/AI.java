package uk.co.jdpatrick.JEngine.AI;

import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.Entity.Entity;

/**
 * Created by Jack on 11/10/2014.
 */
public abstract class AI {

    /**
     * The Entity the AI is applied to
     */
    protected AIEntity owner;


    /**
     * Update the AI
     */
    public abstract void update();

    /**
     * Render debug info for the AI
     *
     * @param graphics The graphics object to draw with
     */
    public abstract void renderInfo(Graphics graphics);

}
