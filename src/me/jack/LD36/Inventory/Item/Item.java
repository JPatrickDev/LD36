package me.jack.LD36.Inventory.Item;

import me.jack.LD36.Inventory.Item.Shelters.Shelter;
import me.jack.LD36.Inventory.Item.Tools.Tool;
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
    public static SpriteSheet tools = null;
    public static SpriteSheet shelters = null;

    private String name,description;

    public Item(int x, int y, int id,String name,String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        if(this instanceof Tool){
            sprite = tools.getSprite(x,y);
            icon = tools.getSprite(x,y);
            sprite.setFilter(Image.FILTER_NEAREST);
            icon.setFilter(Image.FILTER_NEAREST);
            sprite = sprite.getScaledCopy(2f);
            icon = icon.getScaledCopy(2f);

        }else if(this instanceof Shelter) {
            sprite = shelters.getSprite(x,y);
            icon = shelters.getSprite(x,y);
        }else{
            this.sprite = sprites.getSprite(x, y);
            this.icon = icons.getSprite(x, y);
        }
    }

    public static void init() {
        try {
            sprites = new SpriteSheet("res/itemSprites.png", 8, 8);
            icons = new SpriteSheet("res/itemIcons.png", 16, 16);
            tools = new SpriteSheet("res/tools.png",16,16);
            shelters = new SpriteSheet("res/shelters.png",16,16);
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
