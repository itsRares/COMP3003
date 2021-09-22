import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RobotManagement implements Runnable {
    private final RobotCreation rc;
    private final Arena arena;
    private final Logger log;
    private final Player p;

    private boolean running = true;
    private boolean started = false;
    private ExecutorService executor;

    public RobotManagement(RobotCreation rc, Arena arena, Logger log, Player p) {
        this.rc = rc;
        this.arena = arena;
        this.log = log;
        this.p = p;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void run() {
        try {
            while (running) {
                //Check to make sure game not over
                if (!arena.isCaptured()) {
                    //Create a new ai
                    RobotAI ai = new RobotAI(rc.getNextRobot(), arena, rc);
                    //Execute it using the ExecutorService
                    executor.execute(ai);
                    Thread.sleep(100); //Sleep for a little
                } else {
                    running = false;
                    log.addMessage("Game over! Try again.");
                }
            }
        } catch (Exception e) {
            running = false;
        }
    }

    /**
     * Stops the threads
     */
    public void stop() {
        try {
            rc.stop();
            p.stop();
            log.stop();
            running = false;
            executor.shutdownNow();

            if (!executor.awaitTermination(100, TimeUnit.MICROSECONDS)) {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            running = false;
        }
    }

    /**
     * Initiates the threads
     */ 
    public void init() {
        if (!started) {
            Thread rcThread = new Thread(rc, "Robot-Creation");
            rcThread.start();
            Thread logThread = new Thread(log, "Logger");
            logThread.start();
            Thread pThread = new Thread(p, "Player");
            pThread.start();
            started = true;
        } else {
            stop();
        }
    }
}
