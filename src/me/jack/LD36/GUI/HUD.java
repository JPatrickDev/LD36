package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.*;

/**
 * Created by Jack on 27/08/2016.
 */
public class HUD {

    static SpriteSheet icons = null;
    static Image heart, halfHeart;

    public static void render(Graphics g, InGameState state) {

        if (icons == null) {
            try {
                icons = new SpriteSheet("res/icons.png", 16,16);
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
            g.drawRect(x, y, 32, 32);
            if (stack != null) {
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, x, y);
                g.drawString(stack.getStackSize() + "", x, y);
            }
            x += 32;
        }
        y += 36;
        x = 0;
        for (int i = 6; i != 12; i++) {
            ItemStack stack = inventory.getItems()[i];
            g.drawRect(x, y, 32, 32);
            if (stack != null) {
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon, x, y);
                g.drawString(stack.getStackSize() + "", x, y);
            }
            x += 32;
        }

        if(state.getPlayer().getHealth() < 0)return;
        int fullHearts = (int) Math.floor(state.getPlayer().getHealth() / 10);
        x = 200;
        y = 528;
        for (int i = 0; i != fullHearts; i++) {
            g.drawImage(heart,x,y);
            x+=16;
        }

    }
}
