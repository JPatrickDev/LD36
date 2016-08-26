package uk.co.jdpatrick.JEngine.Particle;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A ParticleSystem to keep track of individual particles.
 *
 * @author Jack Patrick
 */
public class ParticleSystem {

    /**
     * ArrayList of all the Particles belonging to the ParticleSystem
     */
    ArrayList<Particle> particles = new ArrayList<Particle>();


    /**
     * Draw the particles in the ParticleSystem
     *
     * @param g       The Graphics object to use for rendering
     * @param offsetX The offset along the X axis
     * @param offsetY The offset along the Y axis
     */
    public void render(Graphics g, int offsetX, int offsetY) {
        ArrayList<Particle> copy = (ArrayList<Particle>) particles.clone();
        Iterator<Particle> iterator = copy.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.render(g, offsetX, offsetY);
        }
    }

    /**
     * Update all the Particles in the ParticleSystem
     */
    public void update() {
        ArrayList<Particle> copy = (ArrayList<Particle>) particles.clone();
        Iterator<Particle> iterator = copy.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.update(this);
        }
    }

    /**
     * Add a Particle to the ParticleSystem
     *
     * @param p The Particle to add
     */
    public void addParticle(Particle p) {
        particles.add(p);
    }

    /**
     * Remove a Particle from the ParticleSystem
     *
     * @param p The Particle to remove.
     */
    public void removeParticle(Particle p) {
        particles.remove(p);
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }
}