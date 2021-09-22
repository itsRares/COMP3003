import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class App extends Application 
{
    public static void main(String[] args) 
    {
        launch();        
    }
    
    @Override
    public void start(Stage stage) 
    {
        stage.setTitle("Robot Defence");

        //Got from here
        //https://stackoverflow.com/questions/30832692/how-to-create-toolbar-with-left-center-and-right-sections-in-javafx/30841774
        Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );

        /* Toolbar */
        ToolBar toolBar = new ToolBar();
        Button btn1 = new Button("Start");
        Label score = new Label("Score: 0");
        //Styles
        toolBar.setStyle("-fx-padding: 0 15px;");
        toolBar.setMinHeight(50.0);

        //Merging
        toolBar.getItems().addAll(btn1, leftSpacer, score);

        /* Logger + Toolbar */
        SplitPane subSplitPane = new SplitPane();
        TextArea logger = new TextArea();
        //Styles
        logger.setMaxHeight(350.0);
        subSplitPane.setOrientation(Orientation.VERTICAL);
        subSplitPane.setMaxWidth(250.0);
        subSplitPane.setMinWidth(250.0);
        subSplitPane.getItems().addAll(logger, toolBar);


        //Initiating of variables
        Logger log = new Logger(logger);
        Arena a = new Arena(log);
        RobotCreation rc = new RobotCreation(a, log);
        Player p = new Player(log, rc, a, score);
        RobotManagement rm = new RobotManagement(rc, a, log, p);
        JFXArena arena = a.getJFX();

        //Listen for clicks
        arena.addListener((x, y) ->
        {
            Shot s = new Shot(x,y);
            p.addShot(s);
        });

        //Listen for start/exit button click
        btn1.setOnAction(e -> {
            rm.init();
            Thread rmThread = new Thread(rm, "Robot-management");
            rmThread.start();

            String text = btn1.getText();
            btn1.setText(!text.equals("Start") ? "Start" : "Exit");
        });

        /* Arena + (Logger + Toolbar) */
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(arena, subSplitPane);

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(splitPane);
        
        Scene scene = new Scene(contentPane, 630, 375);
        stage.setScene(scene);
        stage.setMaxHeight(400.0);
        stage.setMaxWidth(630);
        stage.setMinHeight(400.0);
        stage.setMinWidth(630);
        stage.show();
        //When clicked close button stop threads
        stage.setOnCloseRequest(we -> rm.stop());
    }
}
