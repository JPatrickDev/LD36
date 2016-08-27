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

    @Override
    public void update(Level level) {
        ticks++;
        if (randWalk == 0) {
            int xDist = level.getPlayer().getX() - getX();
            int yDist = level.getPlayer().getY() - getY();
            if (xDist * xDist + yDist * yDist < 2500) {
                xa = 0;
                ya = 0;
                if (xDist < 0) xa = -1;
                if (yDist > 0) xa = 1;
                if (yDist < 0) ya = -1;
                if (yDist > 0) ya = 1;
            }
        }

        int mSpeed = ticks & 1;

        if (!level.canMove(getX() + (xa * mSpeed), getY() + (ya * mSpeed), getW(), getH()) || r.nextInt(50) == 0) {
            randWalk = 10;
            xa = (r.nextInt(3) - 1) * r.nextInt(4);
            ya = (r.nextInt(3) - 1) * r.nextInt(4);
        } else {
            x += (xa * mSpeed);
            y += ya * mSpeed;
        }

        if (randWalk > 0) randWalk--;
    }
}
