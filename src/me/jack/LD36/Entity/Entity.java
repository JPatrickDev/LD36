package me.jack.LD36.Entity;

import me.jack.LD36.Level.Level;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 27/08/2016.
 */
public abstract class Entity {

    protected int x, y, w, h;


    public Entity(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    public abstract void render(Graphics g);

    public abstract void update(Level level);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public abstract void touched(Entity e,Level level);
}
