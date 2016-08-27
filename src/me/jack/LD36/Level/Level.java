package me.jack.LD36.Level;

import me.jack.LD36.Entity.EntityPlayer;
import me.jack.LD36.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jdpatrick.JEngine.Entity.Entity;
import uk.co.jdpatrick.JEngine.JEngine;
import uk.co.jdpatrick.JEngine.Level.Camera;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 27/08/2016.
 */
public class Level {

    private int w, h;
    private int[] tiles;
    private int[] topLayer;


    private Camera camera;


    private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
    private CopyOnWriteArrayList<Rectangle> hitboxes = new CopyOnWriteArrayList<Rectangle>();

    EntityPlayer player;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        this.tiles = new int[w * h];
        this.topLayer = new int[w * h];
        camera = new Camera(0, 0, 800, 600, 32);

    }


    public void postCreate() {
        boolean found = false;
        Random r = new Random();
        int sX = -1, sY = -1;
        while (!found) {
            int x = r.nextInt(w);
            int y = r.nextInt(h);
            int i = tiles[x + y * w];
            if (i == 1 && topLayer[x+y*w] == 0) {
                found = true;
                sX = x;
                sY = y;
                break;
            }
        }
        player = new EntityPlayer(sX * 32, sY * 32);
    }




    public void render(Graphics g) {
        g.translate(-camera.getX(), -camera.getY());

        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                int tile = tiles[x + y * w];
                Tile t = Tile.tileLookup.get(tile);
                g.drawImage(t.getImage(), x * 32, y * 32);
            }
        }


        for (Entity e : entities) {
            e.render(g);
        }
        player.render(g);
        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                int tile = topLayer[x + y * w];
                Tile t = Tile.tileLookup.get(tile);
                if(t == null)continue;
                g.drawImage(t.getImage(), x * 32, y * 32);
            }
        }
        g.resetTransform();


    }


    public boolean canMove(int x, int y, int w, int h) {
        if (x < 0 || x > this.w * 32) {
            return false;
        }
        if (y < 0 || y > this.h * 32) {
            return false;
        }

        Rectangle rect = new Rectangle(x, y, w, h);
        for (Rectangle r : hitboxes) {
            if (rect.intersects(r)) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        for (Entity e : entities) {
            e.update(this);
        }
        player.update(this);
        camera.center(player.getX(), player.getY(), w, h);
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

    public int getTileAtTop(int x,int y){
        return topLayer[x+y*w];
    }
    public void setTile(int x, int y, int p) {
        Tile t = Tile.tileLookup.get(p);
        if (t != null) {
            if (t.isSolid()) {
                hitboxes.add(new Rectangle(x * 32, y * 32, 32, 32));
            }
        }
        tiles[x + y * w] = p;
    }

    public void setTileTop(int x, int y, int p) {
        Tile t = Tile.tileLookup.get(p);
        if (t != null) {
            if (t.isSolid()) {
                hitboxes.add(new Rectangle(x * 32, y * 32, 32, 32));
            }
        }
        topLayer[x + y * w] = p;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
