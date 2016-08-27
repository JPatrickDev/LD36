package me.jack.LD36.Entity;

import me.jack.LD36.Level.Level;
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


    public boolean move(int dX,int dY,Level level){
        boolean canMove = level.canMove(getX() + dX,getY() + dY,getW(),getH(),this);
        if(canMove){
            x+=dX;
            y+=dY;
            return true;
        }else{
            return false;
        }
    }
}
