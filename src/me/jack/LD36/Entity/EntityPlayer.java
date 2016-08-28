package me.jack.LD36.Entity;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.Item;
import me.jack.LD36.Inventory.Item.ItemBerry;
import me.jack.LD36.Inventory.Item.ItemRawPork;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.Tools.Tool;
import me.jack.LD36.Inventory.Item.Tools.ToolType;
import me.jack.LD36.Inventory.Item.Tools.Weapon;
import me.jack.LD36.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

/**
 * Created by Jack on 27/08/2016.
 */
public class EntityPlayer extends Mob {

    Inventory inventory = new Inventory();

    private int facing = 0;

    private float hunger = 200;

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

    Random r = new Random();

    @Override
    public void update(Level level) {
        float hungerRate = 0.01f;
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            move(0, -4, level);
            facing = 0;
            hungerRate = 0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            move(4, 0, level);
            facing = 1;
            hungerRate = 0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            move(0, 4, level);
            facing = 2;
            hungerRate = 0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            move(-4, 0, level);
            facing = 3;
            hungerRate = 0.1f;
        }
        hunger -= hungerRate;
        if (hunger < 0)
            hunger = 0;

        if (hunger == 0 && r.nextInt(30) == 0) {
            setHealth(getHealth() - 5);
        }

        if (hunger > 200)
            hunger = 200;

        Rectangle me = new Rectangle(getX(),getY(),getW(),getH());
        if(tpCooldown){
            if(!me.intersects(cooling)){
                tpCooldown = false;
            }
        }
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

    public void action(Level level, int button) {
        if (button != 0) {
            if (getInventory().getStackInHand() == null) return;
            if (getInventory().getStackInHand().getItem() instanceof ItemBerry) {
                hunger += 5;
                getInventory().getStackInHand().remove(1);
                if (getInventory().getStackInHand().getStackSize() == 0) {
                    getInventory().removeItemStack(getInventory().getItemInHand());
                }
            }
            if (getInventory().getStackInHand().getItem() instanceof ItemRawPork) {
                hunger += 20;
                getInventory().getStackInHand().remove(1);
                if (getInventory().getStackInHand().getStackSize() == 0) {
                    getInventory().removeItemStack(getInventory().getItemInHand());
                }
            }

            return;
        }
        hunger -= 1;
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
                for (int i : type.getEffectiveAgainst()) {
                    if (tile == i) {
                        damage = 50;
                        found = true;
                    }
                }
                if (!found) {
                    damage = 15;
                } else {
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

        int eDamage = 5;
        if (inHand != null) {
            Item inHandItem = inHand.getItem();
            if (inHandItem instanceof Weapon) {
                Weapon w = (Weapon) inHandItem;
                eDamage = w.getDamage();
            }
        }
        level.hurt(getX(), getY(), 48, facing, eDamage);


    }

    public boolean tpCooldown = false;

    public Rectangle cooling = null;

    public void cooldown(Rectangle rect) {
        tpCooldown = true;
        cooling = rect;
    }


    public float getHunger() {
        return hunger;
    }

    public void setPos(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }
}
