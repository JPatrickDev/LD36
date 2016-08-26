package uk.co.jdpatrick.JEngine.Particle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import java.util.Random;

/**
 * A Particle object, override to create unique particles.
 *
 * @author Jack Patrick
 */
public abstract class Particle {

    /**
     * The {@code Random} to use to decide movement.
     */
    protected Random random = new Random();

    /**
     * How many ticks the {@code Particle} has been alive for.
     */
    protected int time = 0;

    /**
     * X and Y velocity values, added to xx and yy every tick.
     */
    protected double xa, ya;

    /**
     * Double position of the Particle, used to keep track of position until rendering, where the Integer versions are used.
     */
    protected double xx, yy;

    /**
     * The shape of the particle, used for rendering.
     */
    protected Shape particle;

    /**
     * Integer X position of the Particle, used for rendering.
     */
    protected int x;
    /**
     * Integer Y position of the Particle, used for rendering.
     */
    protected int y;

    /**
     * Create a new Particle
     *
     * @param x Starting X coordinate
     * @param y Starting Y coordinate
     */
    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the particle
     *
     * @param g       Graphics object to use for rendering
     * @param offsetX The offset along the X axis
     * @param offsetY The offset along the Y axis
     */
    public abstract void render(Graphics g, int offsetX, int offsetY);

    /**
     * Update the particle
     *
     * @param system The {@code ParticleSystem} the Particle belongs to
     */
    public abstract void update(ParticleSystem system);

    public Shape getHitbox() {
        return particle;
    }

    public boolean remove = false;
}

