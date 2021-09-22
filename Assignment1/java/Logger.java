import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Logger.java
 *
 * The logger is in charge of writing output to the log. It does this in its own thread, but
 * assumes that other threads will call the addMessage() in order to provide messages to log.
 */
public class Logger implements Runnable
{
    private boolean running = true;
    private TextArea logger;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(50);

    public Logger(TextArea logger) {
        this.logger = logger;
    }

    //Adds a new message to the blockingqueue
    public void addMessage(String newMessage) {
        try {
            queue.put(newMessage);
        } catch (InterruptedException e) {
            running = false;
        }
    }

    @Override
    public void run() {
        try {
            while(running) {
                String message = queue.take(); //Get the next msg
                Platform.runLater(() -> {
                    //Using runLater add message to the screen
                    logger.appendText(message+"\n");
                });
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
