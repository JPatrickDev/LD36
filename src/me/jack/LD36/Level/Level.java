package me.jack.LD36.Level;

import me.jack.LD36.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.JEngine;
import uk.co.jdpatrick.JEngine.Level.Camera;

/**
 * Created by Jack on 27/08/2016.
 */
public class Level {

    private int w, h;
    private int[] tiles;


    private Camera camera;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        this.tiles = new int[w * h];
        camera = new Camera(0, 0, 800, 600, 32);
    }


    int x =0,y=0;
    public void render(Graphics g) {
        g.translate(-camera.getX(), -camera.getY());

        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                int tile = tiles[x+y*w];
                Tile t = Tile.tileLookup.get(tile);
                g.drawImage(t.getImage(),x*16,y*16);
            }
        }

        g.fillRect(x,y,5,5);
        g.resetTransform();


        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            y-=4;
        } if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            x+=4;
        } if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            y+=4;
        } if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            x-=4;
        }
        camera.center(x,y,w,h);
    }


    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getTileAt(int x, int y) {
        return tiles[x + y * w];
    }

    public void setTile(int x, int y, int p) {
        tiles[x+y*w] = p;
    }
}
