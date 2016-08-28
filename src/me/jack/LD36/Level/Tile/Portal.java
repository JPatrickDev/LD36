package me.jack.LD36.Level.Tile;

/**
 * Created by Jack on 28/08/2016.
 */
public class Portal {

    private int tX,tY,world;


    public Portal(int tX, int tY, int world) {
        this.tX = tX;
        this.tY = tY;
        this.world = world;
    }

    public int gettX() {
        return tX;
    }

    public int gettY() {
        return tY;
    }

    public int getWorld() {
        return world;
    }
}
