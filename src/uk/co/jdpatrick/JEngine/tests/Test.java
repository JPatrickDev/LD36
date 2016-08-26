package uk.co.jdpatrick.JEngine.tests;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.AI.AIEntity;
import uk.co.jdpatrick.JEngine.AI.AIPathfinder;
import uk.co.jdpatrick.JEngine.AI.Movement.SmoothMovement;
import uk.co.jdpatrick.JEngine.AI.Movement.TileBased;
import uk.co.jdpatrick.JEngine.Entity.Entity;
import uk.co.jdpatrick.JEngine.JEngineGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jack Patrick
 */
public class Test extends JEngineGame {


    public Test(String title) {
        super(title);
    }


    Level level = new Level(50, 50);

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
       // for(int i = 0;i!= 750;i++)
           level.entities.add(new BaseEntity(280,200,level));

     //   Random r = new Random();
        for(int i = 0;i!=15;i++){
         //   addWall(r.nextInt(50),r.nextInt(35));
        }
    }

    public void addWall(int x,int y){
        for(int i = 0;i!= 10;i++){
           level.tiles[x+(y+i)*level.w]= 1;
        }
    }


    int i = 0;

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        super.update(gameContainer, i);
        level.update();

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        level.render(graphics);
        super.render(gameContainer, graphics);
    }


    class Level implements TileBasedMap {

        public int w;
        public int h;
        public int[] tiles;

        ArrayList<Entity> entities = new ArrayList<Entity>();

        public Level(int w, int h) {
            this.w = w;
            this.h = h;
            this.tiles = new int[w * h];
        }


        public void render(Graphics g) {

            for (int x = 0; x != w; x++) {
                for (int y = 0; y != h; y++) {
                    int i = tiles[x+y*w];
                    if(i == 0)
                    g.setColor(Color.gray);
                    else
                    g.setColor(Color.black
                    );
                    g.fillRect(x * 20, y * 20, 20, 20);
                    g.setColor(Color.black);
                    g.drawRect(x * 20, y * 20, 20, 20);
                }
            }
            for (Entity e : (ArrayList<Entity>) entities.clone())
                e.render(g);
        }

        @Override
        public int getWidthInTiles() {
            return w;
        }

        @Override
        public int getHeightInTiles() {
            return h;
        }

        @Override
        public void pathFinderVisited(int i, int i2) {

        }

        @Override
        public boolean blocked(PathFindingContext pathFindingContext, int i, int i2) {
            return tiles[i+i2*w] == 1;
        }

        @Override
        public float getCost(PathFindingContext pathFindingContext, int i, int i2) {
            return 0;
        }

        public void update() {
            for (Entity e : (ArrayList<Entity>) entities)e.update();
        }
    }


    class BaseEntity extends AIEntity {

        Level parent;

        Color c;
        /**
         * Create a new Entity
         *
         * @param x The x coordinate of the entity
         * @param y The y coordinate of the entity
         */
        public BaseEntity(int x, int y, Level parent) {
            super(x, y);
            this.parent = parent;
            pathfinder = new AIPathfinder(parent,this, new TileBased(20));
            Random r = new Random();
            pathfinder.setTarget( new Point(r.nextInt(4),r.nextInt(4)));
            c = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        }

        @Override
        public void aiComplete() {
            Random r = new Random();
            Point p = new Point(r.nextInt(50),r.nextInt(50));
            pathfinder.setTarget(p);
        }

        @Override
        public void render(Graphics g) {
            pathfinder.renderInfo(g);
            g.setColor(c);
         //   g.fillRect(getX(), getY(), 20, 20);
            Rectangle current = new Rectangle(getX()+ (20/2),getY() + (20 /2),20/4,20/4);

            g.fillRect((int) current.getX(), (int) current.getY(), (int) current.getWidth(), (int) current.getHeight());
        }

        AIPathfinder pathfinder = null;

        int i = 0;
        @Override
        public void update() {
         //   if(i == 5) {
                pathfinder.update();
           //     i = 0;
          //  }
         //   i++;
        }


        @Override
        public int getTileX() {
            return getX() / 20;
        }

        @Override
        public int getTileY() {
            return getY() / 20;
        }

    }
}
