package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityPlayer extends Entity {

    Inventory inventory = new Inventory();

    public EntityPlayer(int x, int y) {
        super(x, y, 16, 16);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, w, h);
        g.setColor(Color.white);
    }

    @Override
    public void update(Level level) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(getX(), getY() - 4, getW(), getH()))
                y -= 4;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(getX() + 4, getY(), getW(), getH()))
                x += 4;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(getX(), getY() + 4, getW(), getH()))
                y += 4;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(getX() - 4, getY(), getW(), getH()))
                x -= 4;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
