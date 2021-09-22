import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RobotCreation implements Runnable {
    private final Arena arena;
    private final Logger logger;

    private List<Robot> robots = new LinkedList<>();
    private Object mutex = new Object();
    private int nextID;
    private boolean running = true;
    private BlockingQueue<Robot> queue = new ArrayBlockingQueue<>(15);

    public RobotCreation(Arena arena, Logger logger) {
        this.logger = logger;
        this.arena = arena;
        this.nextID = 1;
    }

    @Override
    public void run() {
        try {
            while (running) {
                //Check to make sure game not over
                if (!arena.isCaptured()) {
                    Robot r = createRobot();
                    updateCreation(r);
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            running = false;
        }
    }

    public Robot getNextRobot() throws InterruptedException {
        return queue.take();
    }

    //Add a robot back to the queue
    public void addRobot(Robot robot) {
        //Check to make sure the robot is alive
        if (robot.isAlive()) {
            queue.add(robot);
        }
    }

    public void stop() {
        running = false;
    }

    /**
     * Creates a new robot, picks a number from 1-4 and then
     * with a switch function gets a corner, but checks to
     * make sure no robot there, if so it does not spawn robot
     *
     * @return Robot
     */
    private Robot createRobot() {
        //Pick a random number from 1-4
        Random rand = new Random();
        int n = rand.nextInt(3) + 1;
        Robot r = null;

        //Depending on number depends on corner
        switch (n) {
            case 1:
                //left top corner
                if (!arena.isRobot(0,0)) {
                    r = new Robot(nextID, 0, 0);
                }
                break;
            case 2:
                //right top corner
                if (!arena.isRobot(8,0)) {
                    r = new Robot(nextID, 8, 0);
                }
                break;
            case 3:
                //left bottom corner
                if (!arena.isRobot(0,8)) {
                    r = new Robot(nextID, 0, 8);
                }
                break;
            case 4:
                //right bottom corner
                if (!arena.isRobot(8,8)) {
                    r = new Robot(nextID, 8, 8);
                }
                break;
        }
        return r;
    }

    /**
     * Once the robot has been created, it is required to
     * update other classes with it, alongside incrementing
     * the nextID it also adds robot to the list, updates
     * the arena and then adds it to the queue.
     */
    private void updateCreation(Robot r) {
        synchronized (mutex) {
            if (r != null) {
                nextID++;
                logger.addMessage("New robot spawned! (" + r.getName() + ")");
                //Add robot to linked list
                robots.add(r);
                //Update arena
                arena.updateRobot(r, r.getX(), r.getY());

                //Sleep for 2 sec, this is done because as soon
                //as the robot is added to the queue it is moves
                //straight away.
                //Causes errors - pulled it
                //try {
                //    Thread.sleep(2000);
                //} catch (InterruptedException e) {
                //    running = false;
                //}

                //Add top blockingqueue
                queue.add(r);
            }
        }
    }

    /**
     * Checks to see if any robot is present at the
     * place the player pressed, aka hitting a robot.
     */
    public Robot didHit(int x, int y) {
        synchronized (mutex) {
            //Checks to see if robot at the spot
            if (arena.isRobot(x, y)) {
                //if so loop through the list
                for (Robot r : robots) {
                    //See which coords match
                    if (r.getX() == x && r.getY() == y) {
                        //Then remove
                        queue.remove(r);
                        robots.remove(r);
                        arena.removeRobot(r);
                        r.setHealth(0.0);
                        return r;
                    }
                }
            }
        }
        return null;
    }
}
