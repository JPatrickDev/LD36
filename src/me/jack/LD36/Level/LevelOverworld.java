package me.jack.LD36.Level;

import me.jack.LD36.Entity.Entity;
import me.jack.LD36.Entity.EntityPig;
import me.jack.LD36.Entity.EntityPlayer;
import me.jack.LD36.Entity.EntityWolf;
import me.jack.LD36.Level.Tile.Portal;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

/**
 * Created by Jack on 28/08/2016.
 */
public class LevelOverworld extends Level {
    public LevelOverworld(int w, int h) {
        super(w, h);
    }

    @Override
    public void postCreate(InGameState state) {
        if (state == null) return;
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
        state.player = new EntityPlayer(sX * 32, sY * 32);
        this.parent = state;

        for (int i = 0; i != 100; i++) {
            int x = r.nextInt(w);
            int y = r.nextInt(h);
            if (getTileAt(x, y) == 1 && getTileAtTop(x, y) == 0) {
                int t = r.nextInt(10);
                for (int ii = 0; ii != t; ii++) {
                    entities.add(new EntityPig(x*32,y*32));
                }
            }
        }
    }


    @Override
    public void render(Graphics g) {
        super.render(g);

        g.setColor(light);
        g.fillRect(0, 0, 800, 528);

        g.setColor(Color.white);
    }

    @Override
    public void update(long delta) {
        time += delta;
        if (time > 120 * 1000) {
            time = 0;
            System.out.println("Day over");
        }

        light = new Color(0, 0, 0, getLightLevel(time));
        for (Entity e : entities) {
            e.update(this);
        }
        getPlayer().update(this);
        camera.center(getPlayer().getX(), getPlayer().getY(), w, h);

        if (r.nextInt(15) == 0 && light.getAlpha() == 200) {
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
            entities.add(new EntityWolf(sX * 32, sY * 32));
        }

        Rectangle player = new Rectangle(getPlayer().getX(), getPlayer().getY(), getPlayer().getW(), getPlayer().getH());
        for (Rectangle r : parent.getPortals().keySet()) {
            if (r.intersects(player)) {
                Portal p = parent.getPortals().get(r);
                if (p.getWorld() == 0 && !getPlayer().tpCooldown) {
                    getPlayer().setPos(p.gettX(), p.gettY());
                    parent.currentWorld = 1;
                    getPlayer().cooldown(new Rectangle(p.gettX(), p.gettY(), 32, 32));
                }
            }
        }
    }

    float level = 200;

    public int getLightLevel(int time) {
        if (time >= 30 * 1000 && time <= 90 * 1000) {
            level = 0;
            return 0;
        } else {
            if (time >= 25 * 1000 && time < 30 * 1000) {
                level -= 0.75f;
                if (level < 0)
                    level = 0;
                return (int) level;

            }
            if (time > 90 * 1000 && time <= 95 * 1000) {
                System.out.println(level);
                level += 0.75f;
                if (level > 200) {
                    level = 200;
                    return 200;
                }
                return (int) level;
            }
        }
        return 200;
    }
}
