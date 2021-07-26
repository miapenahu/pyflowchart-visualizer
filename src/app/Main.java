package app;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private double xOffset;
    private double yOffset;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("editor.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        ResizeHelper.addResizeListener(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}