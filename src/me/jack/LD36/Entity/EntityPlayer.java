package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.Tools.Tool;
import me.jack.LD36.Inventory.Item.Tools.ToolType;
import me.jack.LD36.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityPlayer extends Mob {

    Inventory inventory = new Inventory();

    private int facing = 0;

    public EntityPlayer(int x, int y) {
        super(x, y, 16, 16);
        setHealth(100);
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
            move(0, -4, level);
            facing = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            move(4, 0, level);
            facing = 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            move(0, 4, level);
            facing = 2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            move(-4, 0, level);
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

    @Override
    public void touched(Entity e, Level level) {
        if (e instanceof EntityItemDrop) {
            EntityItemDrop drop = (EntityItemDrop) e;
            level.removeEntity(drop);
            level.getPlayer().getInventory().addItem(drop.getStack().getItem(), drop.getStack().getStackSize());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void action(Level level) {
        int tX = getX() / 32;
        int tY = getY() / 32;

        int damage = 10;

        ItemStack inHand = level.getPlayer().getInventory().getStackInHand();

        if (inHand != null) {
            if (inHand.getItem() instanceof Tool) {
                Tool tool = (Tool) inHand.getItem();
                ToolType type = tool.getType();
                int tile = -1;
                if (facing == 0) {
                    tile = level.getTileAtTop(tX, tY - 1);
                } else if (facing == 1) {
                    tile = level.getTileAtTop(tX + 1, tY);
                } else if (facing == 2) {
                    tile = level.getTileAtTop(tX, tY + 1);
                } else if (facing == 3) {
                    tile = level.getTileAtTop(tX - 1, tY);
                }
                boolean found = false;
                for(int i : type.getEffectiveAgainst()){
                    if(tile == i){
                        damage = 50;
                        found = true;
                    }
                }
                if(!found){
                    damage = 15;
                }else{
                    damage *= tool.getMaterial().getMultiplier();
                }
            }
        }
        if (facing == 0) {
            level.damageTopTile(tX, tY - 1, damage);
        } else if (facing == 1) {
            level.damageTopTile(tX + 1, tY, damage);
        } else if (facing == 2) {
            level.damageTopTile(tX, tY + 1, damage);
        } else if (facing == 3) {
            level.damageTopTile(tX - 1, tY, damage);
        }
        level.hurt(getX(), getY(), 48, facing, 5);
    }

}
