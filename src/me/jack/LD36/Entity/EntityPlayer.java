package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityPlayer extends Entity {

    Inventory inventory = new Inventory();

    private int facing = 0;

    private int health = 100;

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
            facing = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(getX() + 4, getY(), getW(), getH()))
                x += 4;
            facing = 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(getX(), getY() + 4, getW(), getH()))
                y += 4;
            facing = 2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(getX() - 4, getY(), getW(), getH()))
                x -= 4;
            facing = 3;
        }

/*
        if (facing == 0) {
            System.out.println("Player facing up");
        } else if (facing == 1) {
            System.out.println("Player facing right");
        } else if (facing == 2) {
            System.out.println("Player facing down");
        } else if (facing == 3) {
            System.out.println("Player facing left");
        }
        */
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void action(Level level) {
        int tX = getX() / 32;
        int tY = getY() / 32;
        if (facing == 0) {
            level.removeTopTile(tX, tY - 1);
        } else if (facing == 1) {
            level.removeTopTile(tX + 1, tY);
        } else if (facing == 2) {
            level.removeTopTile(tX, tY + 1);
        } else if (facing == 3) {
            level.removeTopTile(tX - 1, tY);
        }
    }

    public int getHealth() {
        return health;
    }
}
