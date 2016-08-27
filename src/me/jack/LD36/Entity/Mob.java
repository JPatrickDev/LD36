package me.jack.LD36.Entity;

import org.newdawn.slick.util.pathfinding.Mover;

/**
 * Created by Jack on 27/08/2016.
 */
public abstract class Mob extends Entity  implements Mover {

    private int health;
    public Mob(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
