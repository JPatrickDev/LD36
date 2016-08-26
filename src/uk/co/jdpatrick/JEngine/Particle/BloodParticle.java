package uk.co.jdpatrick.JEngine.Particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

/**
 * A particle to represent blood, can be used to indicate damage.
 *
 * @author Jack Patrick
 */
public class BloodParticle extends Particle {


    /**
     * Create a new BloodParticle
     *
     * @param x Starting X coordinate
     * @param y Starting Y coordinate
     */
    public BloodParticle(int x, int y) {
        super(x, y);
        xx = x;
        yy = y;

        xa = random.nextGaussian() * 1.5;
        ya = random.nextGaussian() * 1.5;

        particle = new Rectangle((float) xx, (float) yy, 10, 10);
    }

    /**
     * Update the particle
     *
     * @param system The {@code ParticleSystem} the Particle belongs to
     */
    @Override
    public void update(ParticleSystem system) {
        time++;
        if (time > 50) {
            system.removeParticle(this);
            xa /= 20;
            ya/=20;
        }
        xx += xa;
        yy += ya;

        x = (int) xx;
        y = (int) yy;

        if(xa > 0){
            xa-=0.05;
        }else{
            xa+=0.05;
        }

        if(ya > 0){
            ya-=0.1;
        }else{
            ya+=0.1;
        }

        particle.setX(x);
        particle.setY(y);
    }

    /**
     * The start colour of the gradient
     */
    Color start = Color.decode("#FF0000");
    /**
     * The end colour of the gradient
     */
    Color end = Color.decode("#360303");

    /**
     * Draw the particle
     *
     * @param g       Graphics object to use for rendering
     * @param offsetX The offset along the X axis
     * @param offsetY The offset along the Y axis
     */
    @Override
    public void render(Graphics g, int offsetX, int offsetY) {
        particle.setX(x - offsetX);
        particle.setY(y - offsetY);
        GradientFill gradient = new GradientFill(particle.getX(), particle.getY(), start, particle.getX() + particle.getWidth(), particle.getY() + particle.getHeight(), end);
        g.fill(particle, gradient);
        particle.setX(x);
        particle.setY(y);
    }
}
