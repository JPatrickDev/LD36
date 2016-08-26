package uk.co.jdpatrick.JEngine.AI.Movement;

import org.newdawn.slick.geom.Point;
/**
 * Created by Jack on 14/10/2014.
 */
public class SmoothMovement extends MovementType{




    public SmoothMovement(int tileSize) {
        super(tileSize);
    }

    @Override
    public Point nextPosition(Point target,Point current) {


        if(target.getX() == current.getX()){
            if(target.getY() == current.getY()){
                return current;
            }
        }
        float xSpeed = ((target.getX()) - current.getX());
        float ySpeed = ((target.getY()) - current.getY());
        float factor = (float) (5  / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));

        xSpeed *= factor;
        ySpeed *= factor;

        Point point = new Point(current.getX()+xSpeed, current.getY() + ySpeed);

        return point;
    }
}
