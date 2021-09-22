/**
 * Shot.java
 *
 * Holds information where the player hit a shot
 * so it can be used later on to determine if a
 * robot has been hit or not.
 */
public class Shot {

    private int x;
    private int y;
    private long time;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
        this.time = System.currentTimeMillis();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getTime() {
        return time;
    }
}
