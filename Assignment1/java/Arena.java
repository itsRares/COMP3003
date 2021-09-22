import java.util.HashMap;

public class Arena {
    private final Logger logger;
    private final JFXArena jfx;

    private HashMap<String, Robot> robots = new HashMap<>();
    private boolean[][] grid;
    private boolean captured;


    public Arena(Logger logger) {
        this.logger = logger;
        this.jfx = new JFXArena(this);
        grid = new boolean[9][9];
        this.captured = false;
    }

    public HashMap<String, Robot> getRobots() {
        return robots;
    }

    public JFXArena getJFX() {
        return jfx;
    }

    public boolean isCaptured() {
        return captured;
    }

    //Check to see if robot is at grid position
    public boolean isRobot(int x, int y) {
        return grid[x][y];
    }

    /**
     * Makes sure robot is within the grid
     */
    public boolean withinGrid(int row, int col) {
        if((col < 0) || (row <0) ) {
            return false;
        }
        if((col >= 9) || (row >= 9)) {
            return false;
        }
        return true;
    }

    /**
     * Updates the robot within the arena and jfx, firstly
     * it updates the robot in the hashmap and the grid pos
     * then triggers a UI update.
     */
    public void updateRobot(Robot r, int oldx, int oldy) {
        //Sees if robot at the fortress, if so game over
        if (r.getX() == 4 && r.getY() == 4) {
            this.captured = true;
        }
        grid[oldx][oldy] = false; //Old spot opens up
        grid[r.getX()][r.getY()] = true; //Claims new spot
        robots.put(r.getName(), r);
        jfx.triggerUpdate();
    }

    /**
     * Removes the robot from the arena and updates the
     * jfx alongside.
     */
    public void removeRobot(Robot r) {
        grid[r.getX()][r.getY()] = false; //Old spot opens up
        robots.remove(r.getName());
        logger.addMessage("Robot destroyed! ("+r.getName()+")");
        jfx.triggerUpdate();
    }
}
