package me.jack.LD36.Level;

import me.jack.LD36.Entity.Entity;
import me.jack.LD36.Entity.EntityItemDrop;
import me.jack.LD36.Entity.EntityPlayer;
import me.jack.LD36.Entity.EntityTestEnemy;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.w3c.dom.css.Rect;
import uk.co.jdpatrick.JEngine.JEngine;
import uk.co.jdpatrick.JEngine.Level.Camera;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 27/08/2016.
 */
public class Level implements TileBasedMap {

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
        camera = new Camera(0, 0, 800, 528, 32);

    }


    public void postCreate() {
        boolean found = false;
        Random r = new Random();
        int sX = -1, sY = -1;
        while (!found) {
            int x = r.nextInt(w);
            int y = r.nextInt(h);
            int i = tiles[x + y * w];
            if (i == 1 && topLayer[x + y * w] == 0) {
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
                if (t == null) continue;
                g.drawImage(t.getImage(), x * 32, y * 32);
            }
        }


        g.resetTransform();


    }


    public boolean canMove(int x, int y, int w, int h,Entity caller) {
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

        if((caller instanceof EntityTestEnemy)){
            Rectangle player = new Rectangle(getPlayer().getX(),getPlayer().getY(),getPlayer().getW(),getPlayer().getH());
            if(rect.intersects(player)){
                caller.touched(getPlayer(),this);
                return false;
            }
        }
        for(Entity e : entities){
            if(e == caller)continue;
            Rectangle eR = new Rectangle(e.getX(),e.getY(),e.getW(),e.getH());
            if(eR.intersects(rect)){
                caller.touched(e,this);
                return false;
            }

        }
        return true;
    }
    Random r = new Random();
    public void update() {
        for (Entity e : entities) {
            e.update(this);
        }
        player.update(this);
        camera.center(player.getX(), player.getY(), w, h);

        if(r.nextInt(10) == 0){
            boolean found = false;
            Random r = new Random();
            int sX = -1, sY = -1;
            while (!found) {
                int x = r.nextInt(w);
                int y = r.nextInt(h);
                int i = tiles[x + y * w];
                if (i == 1 && topLayer[x + y * w] == 0) {
                    found = true;
                    sX = x;
                    sY = y;
                    break;
                }
            }
            entities.add(new EntityTestEnemy(sX * 32,sY * 32));
        }
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
        try {
            return tiles[x + y * w];
        }catch (Exception e){
            return -1;
        }
    }

    public int getTileAtTop(int x, int y) {
        return topLayer[x + y * w];
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

    public CopyOnWriteArrayList<Rectangle> getHitboxes() {
        return hitboxes;
    }


    public void removeTopTile(int x, int y) {
        if (getTileAtTop(x, y) == 0) return;
        if (getTileAtTop(x, y) == 4) {

            for (int i = 0; i != 4; i++) {
                ItemStack stack = new ItemStack(2, new ItemStick());
                EntityItemDrop drop = new EntityItemDrop((x * 32) + 16, (y * 32) + 16, stack);
                entities.add(drop);
            }
            System.out.println("Stack dropped");
        }
        setTileTop(x, y, 0);
        for (Rectangle r : getHitboxes()) {
            if (r.getX() == x * 32 && r.getY() == (y) * 32) {
                getHitboxes().remove(r);
            }
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public int getWidthInTiles() {
        return getW();
    }

    @Override
    public int getHeightInTiles() {
        return getH();
    }

    @Override
    public void pathFinderVisited(int i, int i1) {

    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
        try {
            return Tile.tileLookup.get(tiles[i + i1 * w]).isSolid();
        }catch (Exception e){
            return true;
        }
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        try {
            return Tile.tileLookup.get(tiles[i + i1 * w]).getCost();
        }catch (Exception e){
            return 0;
        }
    }
}
