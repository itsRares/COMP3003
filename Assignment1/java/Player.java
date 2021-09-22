import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Player implements Runnable {
    private final Logger logger;
    private final RobotCreation rc;
    private final Arena a;

    private int currentScore;
    private Object mutex = new Object();
    private Label score;
    private boolean running = true;
    private BlockingQueue<Shot> queue = new ArrayBlockingQueue<>(50);

    public Player(Logger logger, RobotCreation rc, Arena a, Label score) {
        this.logger = logger;
        this.rc = rc;
        this.score = score;
        this.a = a;
        this.currentScore = 0;
    }

    public void addShot(Shot newShot) {
        try {
            queue.put(newShot);
        } catch (InterruptedException e) {
            running = false;
        }
    }

    private Runnable createPlayerScore() {
        return () -> {
            try {
                while(running) {
                    if (!a.isCaptured()) {
                        updateScore(10);
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private void updateScore(int newScore) {
        synchronized (mutex) {
            Platform.runLater(() -> {
                score.setText("Score: " + (currentScore + newScore));
                currentScore += newScore;
            });
        }
    }

    @Override
    public void run() {
        Runnable runnable = createPlayerScore();
        new Thread(runnable, "player-score").start();
        try {
            while(running) {
                if (!a.isCaptured()) {
                    Shot shot = queue.take(); //Get the recent shot
                    //Check and see if a robot at where the player shot
                    Robot r = rc.didHit(shot.getX(), shot.getY());

                    if (r != null) {
                        //Hit!
                        //Using runLater update the score
                        //Get millisecond difference
                        long t = System.currentTimeMillis() - shot.getTime();
                        int newScore = 10 + (100 * (Math.toIntExact(t) / r.getDelay())); //Calc score
                        updateScore(newScore);
                    } else {
                        //Missed
                        logger.addMessage("Shot missed! Try again.");
                    }
                }
                Thread.sleep(1000);
            }
        } catch(Exception e) {
            running = false;
        }
    }

    //Stop the thread
    public void stop() {
        running = false;
    }
}
