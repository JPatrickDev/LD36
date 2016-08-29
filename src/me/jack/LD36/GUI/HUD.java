package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.Item;
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
    static Image heart, halfHeart,bg,pizza,bubble;

    public static HashMap<Rectangle, Integer> itemSlots = new HashMap<>();

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

    public static void render(Graphics g, InGameState state, int mX, int mY) {

        if (icons == null) {
            try {
                icons = new SpriteSheet("res/icons.png", 16, 16);
                heart = icons.getSprite(0, 0);
                pizza = icons.getSprite(0,1);
                bubble = icons.getSprite(1,1);
                halfHeart = icons.getSprite(1, 0);
                bg = new Image("res/bg.png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        g.setColor(Color.black);
        g.drawImage(bg,0,528);
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
                g.drawImage(icon, x + 8, y+2);
                g.drawString(stack.getStackSize() + "", x + 10, y + 16);
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
                g.drawImage(icon, x + 8, y+2);
                g.drawString(stack.getStackSize() + "", x + 10, y + 16);
            }
            x += 34;
        }

        if (state.getPlayer().getHealth() < 0) return;
        int fullHearts = (int) Math.ceil(state.getPlayer().getHealth() / 10);
        x = 204;
        y = 528;
        for (int i = 0; i != fullHearts; i++) {
            g.drawImage(heart, x, y);
            x += 16;
        }


        int fullPizza = (int) Math.floor(state.getPlayer().getHunger() / 20);
        x = 204;
        y = 528+16;
        for (int i = 0; i != fullPizza; i++) {
            g.drawImage(pizza, x, y);
            x += 16;
        }

        if(state.getPlayer().underWater){
            int fullBubbles = (int) Math.floor(state.getPlayer().breath / 10);
            x = 204;
            y = 528+32;
            for (int i = 0; i != fullBubbles; i++) {
                g.drawImage(bubble, x, y);
                x += 16;
            }
        }

        x = 524;
        y = 528;
        g.drawString("Crafting", x, y);
        y += 25;
        g.drawString("Time: " + state.getLevel().getTime(), x, y);
        y+=25;
        g.drawString("Score: " + state.score,x,y);
        y -= 50;
        x += 72;

        if (mY > 528) {
            for (Rectangle r : itemSlots.keySet()) {
                if (r.contains(mX, mY)) {
                    ItemStack stack = state.getPlayer().getInventory().getItems()[itemSlots.get(r)];
                    if (stack != null) {
                        g.setColor(Color.gray);
                        g.fillRect(mX,mY-16,g.getFont().getWidth(stack.getItem().getName()),g.getFont().getHeight(stack.getItem().getName()));
                        g.setColor(Color.white);
                        g.drawString(stack.getItem().getName(), mX, mY-16-2);
                    }
                }
            }
        }

    }


    public static void update(InGameState state) {
        if (changeItemSlot) {
            state.getPlayer().getInventory().setItemInHand(newSlot);
            newSlot = -1;
            changeItemSlot = false;
        }


        if (drop) {
            ItemStack stack = state.getPlayer().getInventory().getItems()[dropping];
            state.getLevel().drop(stack, state.getPlayer().getX(), state.getPlayer().getY());
            state.getPlayer().getInventory().removeItemStack(dropping);
            drop = false;
            dropping = -1;
        }
    }

    static Rectangle craftingButton = new Rectangle(524, 528, 80, 20);

    public static void mouseClick(int button, int x, int y) {
        if (button == 0) {
            if (craftingButton.contains(x, y)) {
                InGameState.showingCrafting = true;
            }
        }

        for (Rectangle r : itemSlots.keySet()) {
            if (r.contains(x, y)) {
                if (button == 0) {
                    int i = itemSlots.get(r);
                    newSlot = i;
                    changeItemSlot = true;
                } else if (button == 1) {
                    dropping = itemSlots.get(r);
                    drop = true;
                }
            }
        }

    }

}
