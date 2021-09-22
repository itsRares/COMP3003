import java.util.Random;

public class RobotAI implements Runnable {
    private final Robot robot;
    private final RobotCreation rc;
    private final Arena arena;

    public RobotAI(Robot robot, Arena arena, RobotCreation rc) {
        this.robot = robot;
        this.arena = arena;
        this.rc = rc;
    }

    @Override
    public void run() {
        try {
            //Check to make sure the robot is even alive
            if (robot.isAlive()) {
                //Make sure the game is still running
                if (!arena.isCaptured()) {
                    nextMove(); //Get next move
                    Thread.sleep(robot.getDelay());
                    rc.addRobot(robot);
                }
            } else {
                arena.removeRobot(robot);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Gets the next move, picks a number from 1-4 and then
     * with a switch function gets a direction, checks to
     * make sure no robot there, if so it trys an alternative
     * direction.
     *
     * @return void
     */
    public void nextMove() {
        //Pick a random number from 1-4
        Random rand = new Random();
        int n = rand.nextInt(3) + 1;

        boolean nextMove;
        //Depending on number depends on direction
        switch (n) {
            case 1:
                //Move north
                nextMove = moveNorth();
                //If cant move north move west
                if (!nextMove) {
                    moveWest();
                }
                break;
            case 2:
                //Move South
                nextMove = moveSouth();
                //If cant move South move west
                if (!nextMove) {
                    moveWest();
                }
                break;
            case 3:
                //Move East
                nextMove = moveEast();
                //If cant move East move South
                if (!nextMove) {
                    moveSouth();
                }
                break;
            case 4:
                //Move west
                nextMove = moveWest();
                //If cant move west move north
                if (!nextMove) {
                    moveNorth();
                }
                break;
        }
    }

    /**
     * Direction methods, used to move the robot, within
     * the methods it is checked if move is possible and
     * then coordinates updated, alongside the scene.
     *
     * Applies to: moveNorth, moveSouth, moveEast, moveWest
     */

    public boolean moveNorth() {
        //Get y value
        int y = robot.getY();
        //Get x value
        int x = robot.getX();

        //Get new Y value
        int newY = y+1;
        //Check if within the grid and no other robot there
        if (arena.withinGrid(x, newY) && !arena.isRobot(x,newY)) {
            robot.setY(newY);
            arena.updateRobot(robot, x, y);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        //Get y value
        int y = robot.getY();
        //Get x value
        int x = robot.getX();

        //Get new Y value
        int newY = y-1;
        //Check if within the grid and no other robot there
        if (arena.withinGrid(x, newY) && !arena.isRobot(x,newY)) {
            robot.setY(newY);
            arena.updateRobot(robot, x, y);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveEast() {
        //Get y value
        int y = robot.getY();
        //Get x value
        int x = robot.getX();

        //Get new x value
        int newX = x+1;
        //Check if within the grid and no other robot there
        if (arena.withinGrid(newX, y) && !arena.isRobot(newX,y)) {
            robot.setX(newX);
            arena.updateRobot(robot, x, y);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveWest() {
        //Get y value
        int y = robot.getY();
        //Get x value
        int x = robot.getX();

        //Get new x value
        int newX = x-1;
        //Check if within the grid and no other robot there
        if (arena.withinGrid(newX, y) && !arena.isRobot(newX,y)) {
            robot.setX(newX);
            arena.updateRobot(robot, x, y);
            return true;
        } else {
            return false;
        }
    }
}
