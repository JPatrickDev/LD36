package me.jack.LD36.Level.Tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;

/**
 * Created by Jack on 27/08/2016.
 */
public class Tile {

    private int id;
    private Image image;
    private boolean solid;


    public static HashMap<Integer,Tile> tileLookup = new HashMap<>();
    public static int idCounter = 1;
    public static SpriteSheet tiles;

    public static void init(){
        new Tile(0,0,false);
        new Tile(1,0,true);
        new Tile(2,0,false);
        new Tile(3,0,true);
        new Tile(0,1,false);
    }


    public Tile(int x,int y,boolean solid) {
        if(tiles == null){
            try {
                tiles = new SpriteSheet(new Image("res/tiles.png"),16,16);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        image = tiles.getSprite(x,y);
        image.setFilter(Image.FILTER_NEAREST);
        image = image.getScaledCopy(2f);
        id = idCounter;
        idCounter++;
        this.solid = solid;
        tileLookup.put(id,this);
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public boolean isSolid() {
        return solid;
    }
}
