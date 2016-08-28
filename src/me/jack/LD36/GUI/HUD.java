package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import java.util.HashMap;

/**
 * Created by Jack on 27/08/2016.
 */
public class HUD {

    static SpriteSheet icons = null;
    static Image heart, halfHeart;

    static HashMap<Rectangle, Integer> itemSlots = new HashMap<>();

    static {
        int x = 0, y = 528;
        for (int i = 0; i != 6; i++) {
            Rectangle rect = new Rectangle(x, y, 32, 32);
            itemSlots.put(rect, i);
            x += 34;
        }
        y += 36;
        x = 0;
        for (int i = 6; i != 12; i++) {
            Rectangle rect = new Rectangle(x, y, 32, 32);
            itemSlots.put(rect, i);
            x += 34;
        }
    }

    static boolean changeItemSlot = false;
    static int newSlot = -1;

    static boolean drop = false;
    static int dropping = -1;

    public static void render(Graphics g, InGameState state) {

        if (icons == null) {
            try {
                icons = new SpriteSheet("res/icons.png", 16, 16);
                heart = icons.getSprite(0, 0);
                halfHeart = icons.getSprite(1, 0);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        g.setColor(Color.black);
        g.fillRect(0, 528, 800, 72);
        g.setColor(Color.white);

        Inventory inventory = state.getPlayer().getInventory();
        int x = 0, y = 528;
        for (int i = 0; i != 6; i++) {
            ItemStack stack = inventory.getItems()[i];
            if (i == inventory.getItemInHand()) {
                g.setColor(Color.red);
            }
            g.drawRect(x, y, 32, 32);
            g.setColor(Color.white);
            if (stack != null) {
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, x, y);
                g.drawString(stack.getStackSize() + "", x, y);
            }
            x += 34;
        }
        y += 36;
        x = 0;
        for (int i = 6; i != 12; i++) {
            ItemStack stack = inventory.getItems()[i];
            if (i == inventory.getItemInHand()) {
                g.setColor(Color.red);
            }
            g.drawRect(x, y, 32, 32);
            g.setColor(Color.white);
            if (stack != null) {
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, x, y);
                g.drawString(stack.getStackSize() + "", x, y);
            }
            x += 34;
        }

        if (state.getPlayer().getHealth() < 0) return;
        int fullHearts = (int) Math.floor(state.getPlayer().getHealth() / 10);
        x = 204;
        y = 528;
        for (int i = 0; i != fullHearts; i++) {
            g.drawImage(heart, x, y);
            x += 16;
        }

        x = 204;
        y = 550;
        g.drawString("Crafting", x, y);
        y+=25;
        g.drawString("Time: " + state.getLevel().getTime(),x,y);
        y-=25;
        x+=72;
        g.drawString("Hunger: " + state.getPlayer().getHunger(),x,y);

    }


    public static void update(InGameState state) {
        if (changeItemSlot) {
            state.getPlayer().getInventory().setItemInHand(newSlot);
            newSlot = -1;
            changeItemSlot = false;
        }

        if(drop){
            ItemStack stack = state.getPlayer().getInventory().getItems()[dropping];
            state.getLevel().drop(stack,state.getPlayer().getX(),state.getPlayer().getY());
            state.getPlayer().getInventory().removeItemStack(dropping);
            drop = false;
            dropping = -1;
        }
    }

    static Rectangle craftingButton = new Rectangle(200, 550, 80, 20);

    public static void mouseClick(int button, int x, int y) {
        if (button == 0) {
            if (craftingButton.contains(x, y)) {
                InGameState.showingCrafting = true;
            }
        }

        for (Rectangle r : itemSlots.keySet()) {
            if (r.contains(x, y)) {
                if(button == 0) {
                    int i = itemSlots.get(r);
                    newSlot = i;
                    changeItemSlot = true;
                }else if (button == 1){
                    dropping = itemSlots.get(r);
                    drop = true;
                }
            }
        }

    }

}
