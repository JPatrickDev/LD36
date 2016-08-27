package me.jack.LD36.Inventory.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 27/08/2016.
 */
public class Item {

    private Image sprite, icon;

    private int id;

    public static SpriteSheet sprites = null;
    public static SpriteSheet icons = null;

    public Item(int x, int y, int id) {
        this.id = id;
        this.sprite = sprites.getSprite(x, y);
        this.icon = icons.getSprite(x, y);
    }

    public static void init() {
        try {
            sprites = new SpriteSheet("res/itemSprites.png", 8, 8);
            icons = new SpriteSheet("res/itemIcons.png", 16, 16);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public Image getIcon() {
        return icon;
    }

    public Image getSprite() {
        return sprite;
    }
}
