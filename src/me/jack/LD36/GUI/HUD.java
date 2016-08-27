package me.jack.LD36.GUI;

import me.jack.LD36.Inventory.Inventory;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 27/08/2016.
 */
public class HUD {

    public static void render(Graphics g, InGameState state){
        g.setColor(Color.black);
        g.fillRect(0,528,800,72);
        g.setColor(Color.white);

        Inventory inventory = state.getPlayer().getInventory();
        int x = 0,y = 528;
        for(int i = 0;i!= 6;i++){
            ItemStack stack = inventory.getItems()[i];
            g.drawRect(x,y,32,32);
            if(stack != null){
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon,x,y);
                g.drawString(stack.getStackSize() + "",x,y);
            }
            x+=32;
        }
        y+=36;
        x = 0;
        for(int i = 6;i!= 12;i++){
            ItemStack stack = inventory.getItems()[i];
            g.drawRect(x,y,32,32);
            if(stack != null){
                Image icon = stack.getItem().getIcon();
                g.drawImage(icon,x,y);
                g.drawString(stack.getStackSize() + "",x,y);
            }
            x+=32;
        }


    }
}
