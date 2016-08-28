package me.jack.LD36.Level;

import me.jack.LD36.Entity.*;
import me.jack.LD36.Inventory.Item.ItemBerry;
import me.jack.LD36.Inventory.Item.ItemStack;
import me.jack.LD36.Inventory.Item.ItemStick;
import me.jack.LD36.Inventory.Item.ItemStone;
import me.jack.LD36.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
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
public class Level {

    private int w, h;
    private int[] tiles;
    private int[] topLayer;
    private int[] topLayerHealth;

    private Camera camera;


    private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
    private CopyOnWriteArrayList<Rectangle> hitboxes = new CopyOnWriteArrayList<Rectangle>();

    EntityPlayer player;

    int time = 0;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        this.tiles = new int[w * h];
        this.topLayer = new int[w * h];
        this.topLayerHealth = new int[w * h];
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
                if ((x * 32) - camera.getX() < -32 || (x * 32) - camera.getX() > 800) continue;
                if ((y * 32) - camera.getY() < -32 || (y * 32) - camera.getY() > 800) continue;
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
                if ((x * 32) - camera.getX() < -32 || (x * 32) - camera.getX() > 800) continue;
                if ((y * 32) - camera.getY() < -32 || (y * 32) - camera.getY() > 800) continue;
                int tile = topLayer[x + y * w];
                Tile t = Tile.tileLookup.get(tile);
                if (t == null) continue;
                g.drawImage(t.getImage(), x * 32, y * 32);
                g.drawString(topLayerHealth[x + y * w] + "", x * 32, y * 32);
            }
        }


        g.resetTransform();

        g.setColor(light);
       // g.fillRect(0,0,800,528);

        g.setColor(Color.white);
    }


    public boolean canMove(int x, int y, int w, int h, Entity caller) {
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

        if ((caller instanceof EntityTestEnemy)) {
            Rectangle player = new Rectangle(getPlayer().getX(), getPlayer().getY(), getPlayer().getW(), getPlayer().getH());
            if (rect.intersects(player)) {
                caller.touched(getPlayer(), this);
                return false;
            }
        }
        for (Entity e : entities) {
            if (e == caller) continue;
            Rectangle eR = new Rectangle(e.getX(), e.getY(), e.getW(), e.getH());
            if (eR.intersects(rect)) {
                caller.touched(e, this);
                return false;
            }

        }
        return true;
    }

    Random r = new Random();

    Color light = new Color(0,0,0,200);
    int alpha = 0;
    public void update(long delta) {
        time+=delta;
        if(time > 120 * 1000){
            time = 0;
            System.out.println("Day over");
        }

        light = new Color(0,0,0,getLightLevel(time));
        for (Entity e : entities) {
            e.update(this);
        }
        player.update(this);
        camera.center(player.getX(), player.getY(), w, h);

    /*    if (r.nextInt(10) == 0) {
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
            entities.add(new EntityTestEnemy(sX * 32, sY * 32));
        }*/
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

    public int getTime() {
        return time;
    }

    public int getTileAt(int x, int y) {
        try {
            return tiles[x + y * w];
        } catch (Exception e) {
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
                if(p == 4){
                    topLayerHealth[x + y * w] = 50;
                }else if(p == 6){
                    topLayerHealth[x + y * w] = 100;
                }else if(p == 7){
                    topLayerHealth[x + y * w] = 5;
                }

            } else {
                topLayerHealth[x + y * w] = 0;
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


    public void damageTopTile(int x, int y, int dmg) {
        if (getTileAtTop(x, y) == 0) return;
        int health = topLayerHealth[x + y * w];
        health -= dmg;
        topLayerHealth[x + y * w] = health;
        if (health <= 0) {
            if (getTileAtTop(x, y) == 4) {
                for (int i = 0; i != 4; i++) {
                    ItemStack stack = new ItemStack(2, new ItemStick());
                    drop(stack, x * 32, y * 32);
                }
            }
            if (getTileAtTop(x, y) == 6) {
                for (int i = 0; i != 4; i++) {
                    ItemStack stack = new ItemStack(2, new ItemStone());
                    drop(stack, x * 32, y * 32);
                }
            }
            if(getTileAtTop(x,y) == 7){
                for (int i = 0; i != 10; i++) {
                    ItemStack stack = new ItemStack(1, new ItemBerry());
                    drop(stack, x * 32, y * 32);
                }
            }
            setTileTop(x, y, 0);
            for (Rectangle r : getHitboxes()) {
                if (r.getX() == x * 32 && r.getY() == (y) * 32) {
                    getHitboxes().remove(r);
                }
            }
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }


    public void hurt(int x, int y, int radius, int facing, int dmg) {
        Circle c = new Circle(x, y, radius);
        for (Entity e : entities) {
            if (e instanceof EntityTestEnemy) {
                Rectangle r = new Rectangle(e.getX(), e.getY(), e.getW(), e.getH());
                if (r.intersects(c)) {
                    Mob mob = (Mob) e;
                    mob.setHealth(mob.getHealth() - dmg);
                }
            }
        }
    }

    public void drop(ItemStack stack, int x, int y) {
        EntityItemDrop drop = new EntityItemDrop(x + 16, (y + 16), stack);
        entities.add(drop);
    }


    float level = 200;
    public int getLightLevel(int time){
        if(time >= 30*1000 && time <= 90*1000){
            level = 0;
            return 0;
        }else{
            if(time >= 25 *1000 &&  time < 30 * 1000){
                level -= 0.75f;
                if(level < 0)
                    level = 0;
                return (int) level;

            }
            if(time > 90 * 1000 && time <= 95 * 1000){
                System.out.println(level);
                level+=0.75f;
                if(level > 200) {
                    level = 200;
                    return 200;
                }
                return (int) level;
            }
        }
        return 200;
    }
}
