import java.util.Random;

/**
 * Robot.java
 *
 * Holds information on the specific robot, this
 * information is used to determine where the robot
 * can do/ other functions in the folder can do.
 */
public class Robot {

    private int id;
    private String name;
    private int x;
    private int y;
    private int d;
    private boolean alive;

    public Robot(int id, int x, int y) {
        this.id = id;
        this.name = "Robot " + id;
        this.x = x;
        this.y = y;
        this.alive = true;

        //Generate delay
        Random rand = new Random();
        int n = rand.nextInt(500);
        this.d = n + 1500;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDelay() {
        return d;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHealth(double health) {
        if (health <= 0.0) {
            this.alive = false;
        }
    }
}
