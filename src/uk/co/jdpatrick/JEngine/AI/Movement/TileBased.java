package uk.co.jdpatrick.JEngine.AI.Movement;


import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TileSet;

/**
 * Created by Jack on 14/10/2014.
 */
public class TileBased extends MovementType{


    public TileBased(int tileSize) {
        super(tileSize);
    }

    @Override
    public Point nextPosition(Point target,Point current) {
        int tX = (int)target.getX();
        int tY = (int)target.getY();

        int cX = (int)target.getX();
        int cY = (int)target.getY();

        Point newPosition = new Point(cX,cY);
        if(tX > cX){
            newPosition.setX(cX + tileSize);
        }

        if(tY > cY){
            newPosition.setY(cY + tileSize);
        }

        if(tX < cX){
            newPosition.setX(cX - tileSize);
        }

        if(tY < cY){
            newPosition.setY(cY - tileSize);
        }



        return newPosition;
    }
}
