package me.jack.LD36.Entity;

import me.jack.LD36.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.Random;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityTestEnemy extends Mob {

    public EntityTestEnemy(int x, int y) {
        super(x, y, 16, 16);
        setHealth(20);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillRect(x, y, w, h);
        g.setColor(Color.white);
    }


    int tX = -1, tY = -1;


    Random r = new Random();

    int randWalk = 0;

    int xa, ya;

    int ticks = 0;

    boolean rushing = false;

    @Override
    public void update(Level level) {
        if(level.light.getAlpha() != 200){
            if(r.nextInt(500) == 0){
                level.removeEntity(this);
                return;
            }
        }
        if(getHealth() <= 0){
            level.removeEntity(this);
            return;
        }
        ticks++;
        int mSpeed = ticks & 1;
        mSpeed *= 2;
        if (randWalk == 0) {
            int xDist = level.getPlayer().getX() - getX();
            int yDist = level.getPlayer().getY() - getY();
            if (xDist * xDist + yDist * yDist < 8000) {
                xa = 0;
                ya = 0;
                if (xDist < 0) xa = -1;
                if (yDist > 0) xa = 1;
                if (yDist < 0) ya = -1;
                if (yDist > 0) ya = 1;
            }
            if (xDist * xDist + yDist * yDist < 2500) {
                rushing = true;
                if (level.getPlayer().getX() > getX()) {
                    move(mSpeed, 0, level);
                }
                if (level.getPlayer().getX() < getX()) {
                    move(-mSpeed, 0, level);
                }

                if (level.getPlayer().getY() > getY()) {
                    move(0, mSpeed, level);
                }
                if (level.getPlayer().getY() < getY()) {
                    move(0, -mSpeed, level);
                }
            } else {
                rushing = false;
            }
        }


        if (!move(xa * mSpeed, ya * mSpeed, level) && !rushing || r.nextInt(200) == 0) {
            randWalk = 10;
            xa = (r.nextInt(3) - 1) * r.nextInt(4);
            ya = (r.nextInt(3) - 1) * r.nextInt(4);
        }
        if (randWalk > 0) randWalk--;
    }

    @Override
    public void touched(Entity e, Level level) {
        if (e instanceof EntityPlayer) {
            level.getPlayer().setHealth(level.getPlayer().getHealth() - 1);
        }
    }
}
