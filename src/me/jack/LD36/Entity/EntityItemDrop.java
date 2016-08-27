package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Level.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityItemDrop extends Entity {

    private ItemStack stack;

    int xVel, yVel;
    public EntityItemDrop(int x, int y, ItemStack stack) {
        super(x, y, 8, 8);
        this.stack = stack;
        Random r = new Random();
        xVel = r.nextInt(50) - 25;
        yVel = r.nextInt(50) - 25;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(stack.getItem().getSprite(),x,y);
    }

    @Override
    public void update(Level level) {
        x += xVel;
        y += yVel;
        xVel /=2;
        yVel/=2;

        Rectangle me = new Rectangle(x-4,y-4,w * 2,h * 2);
        Rectangle player = new Rectangle(level.getPlayer().getX(),level.getPlayer().getY(),level.getPlayer().getW(),level.getPlayer().getH());
        if(me.intersects(player)){

        }
    }

    @Override
    public void touched(Entity e,Level level) {

    }

    public ItemStack getStack() {
        return stack;
    }
}
