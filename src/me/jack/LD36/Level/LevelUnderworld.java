package me.jack.LD36.Level;

import me.jack.LD36.Entity.Entity;
import me.jack.LD36.Entity.EntityTestEnemy;
import me.jack.LD36.Level.Tile.Portal;
import me.jack.LD36.States.InGameState;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

/**
 * Created by Jack on 28/08/2016.
 */
public class LevelUnderworld extends Level{


    public LevelUnderworld(int w, int h) {
        super(w, h);
    }

    @Override
    public void postCreate(InGameState state) {
        if(state == null)return;
        this.parent = state;
    }

    @Override
    public void update(long delta) {
        for (Entity e : entities) {
            e.update(this);
        }
        getPlayer().update(this);
        camera.center(getPlayer().getX(), getPlayer().getY(), w, h);


        Rectangle player = new Rectangle(getPlayer().getX(),getPlayer().getY(),getPlayer().getW(),getPlayer().getH());
        for(Rectangle r : parent.getPortals().keySet()){
            if(r.intersects(player)){
                Portal p = parent.getPortals().get(r);
                if(p.getWorld() == 1 && !getPlayer().tpCooldown){
                    getPlayer().setPos(p.gettX(),p.gettY());
                    parent.currentWorld = 0;
                    getPlayer().cooldown(new Rectangle(p.gettX(),p.gettY(),32,32));
                }
            }
        }

    }
}
