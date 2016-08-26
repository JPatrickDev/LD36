package uk.co.jdpatrick.JEngine.AI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.AI.Movement.MovementType;



/**
 * Created by Jack on 12/10/2014.
 */
public class AIPathfinder extends AI {


    /**
     * The TileBasedMap for the pathfinder to use
     */
    private TileBasedMap map;

    /**
     * The target Point
     */
    private Point target;

    /**
     * The path to take
     */
    private Path path;

    /**
     * The pathFinder to use
     */
    private AStarPathFinder pathFinder = null;

    /**
     *  The MovementType to use
     */
    public MovementType movementType;

    /**
     * The current position in the path
     */
    private int currentStep = 0;


    /**
     * Create a new AIPathfinder
     *
     * @param map The TileBasedMap
     * @param owner The owner of the AI
     * @param movementType The type of movement to use
     */
    public AIPathfinder(TileBasedMap map, AIEntity owner,MovementType movementType) {
        this.map = map;
        this.owner = owner;
        pathFinder = new AStarPathFinder(map, 100, true);
        this.movementType = movementType;
    }

    /**
     * Set the target of the AI
     *
     * @param target The Point to aim for
     */
    public void setTarget(Point target) {
        this.target = target;
        calculatePath();
    }

    /**
     * Calculate the path to the target
     */
    private void calculatePath() {
        if (target == null) return;
        new Thread(new Runnable(){

            @Override
            public void run() {
                path = pathFinder.findPath(owner, owner.getTileX(), owner.getTileY(), (int) target.getX(), (int) target.getY());

                if (path == null) {
                    System.out.println("Unable to find path");
                    owner.aiComplete();
                }else
                rtarget = new Rectangle(path.getStep(0).getX()*movementType.tileSize,path.getStep(0).getY()*movementType.tileSize,movementType.tileSize,movementType.tileSize);
            }
        }).start();

    }

    /**
     * Update the AI, attempting to move to the next step
     */
    @Override
    public void update() {
        step();
    }

    /**
     * The collision box of the target
     */
    Rectangle rtarget = null;

    /**
     * Move towards the current step and check if the step has been reached
     */
    public void step(){
        if(path == null)return;

                if(currentStep == path.getLength()){
                    path = null;
                    currentStep = 0;
                    owner.aiComplete();
                    return;
                }

                Path.Step nextStep = path.getStep(currentStep);
                Point newPos = movementType.nextPosition(new Point(nextStep.getX()*movementType.tileSize,nextStep.getY()*movementType.tileSize),new Point(owner.getX(),owner.getY()));
                owner.setX((int)newPos.getX());
                owner.setY((int)newPos.getY());
                Rectangle current = new Rectangle(owner.getX(),owner.getY(),movementType.tileSize,movementType.tileSize);
                Rectangle small = new Rectangle(current.getCenterX(),current.getCenterY(),5,5);
                if(small.intersects(rtarget) || current.getX() == rtarget.getX() && current.getY() == rtarget.getY()){
                    currentStep++;
                    if(currentStep != path.getLength()){
                        nextStep = path.getStep(currentStep);
                        rtarget =  new Rectangle(nextStep.getX()*movementType.tileSize,nextStep.getY()*movementType.tileSize,movementType.tileSize,movementType.tileSize);
                    }

                }


    }

    /**
     * Render the debug for the AI, including the current step and path to take.
     * @param graphics The graphics object to draw with
     */
    @Override
    public void renderInfo(Graphics graphics) {
        if (path == null) return;
        for (int i = currentStep; i != path.getLength(); i++) {
            if(i == currentStep){
                graphics.setColor(Color.green);
            }else{
                graphics.setColor(Color.orange);
            }
            Path.Step step = path.getStep(i);
            graphics.drawRect(step.getX() * movementType.tileSize, step.getY() * movementType.tileSize, movementType.tileSize, movementType.tileSize);
        }
    }


}
