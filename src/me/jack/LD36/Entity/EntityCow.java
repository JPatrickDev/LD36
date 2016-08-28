package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Item.ItemLeather;
import me.jack.LD36.Inventory.Item.ItemRawBeef;
import me.jack.LD36.Inventory.Item.ItemRawPork;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Created by Jack on 28/08/2016.
 */
public class EntityCow extends Mob {

    public EntityCow(int x, int y) {
        super(x, y, 16, 16);
        setHealth(20);

        xVel = r.nextInt(40) - 20;
        yVel = r.nextInt(40) - 20;
    }

    int xVel, yVel;

    Color c = new Color(100, 100, 100);

    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, w, h);
        g.setColor(Color.white);
    }


    Random r = new Random();

    int randWalk = 0;

    int xa, ya;

    int ticks = 0;


    @Override
    public void update(Level level) {
        if (xVel != 0) {
            move(xVel, yVel, level);
            xVel /= 2;
        }
        if (getHealth() <= 0) {
            level.removeEntity(this);
            level.drop(new ItemStack(1,new ItemRawBeef()),getX(),getY());
            level.drop(new ItemStack(1,new ItemLeather()),getX(),getY());
            return;
        }
        ticks++;
        int mSpeed = ticks & 1;

        if (!move(xa * mSpeed, ya * mSpeed, level) || r.nextInt(200) == 0) {
            randWalk = 10;
            xa = (r.nextInt(3) - 1) * r.nextInt(2);
            ya = (r.nextInt(3) - 1) * r.nextInt(2);
        }
        if (randWalk > 0) randWalk--;


    }

    @Override
    public void touched(Entity e, Level level) {

    }
}