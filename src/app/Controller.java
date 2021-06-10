package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.stage.StageStyle;
import org.jcp.xml.dsig.internal.MacOutputStream;

import java.io.IOException;
import java.util.*;
import java.util.List;

public class Controller {

    private FlowChart flowC = new FlowChart();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private AnchorPane editorPane;
    @FXML private ImageView editorArrow;
    @FXML private ImageView builderArrow;
    @FXML private AnchorPane graphicPane;
    @FXML private TextField printTextField;

    public void switchToMenu(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEditor(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("editor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGraphics(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("graphics.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void onGraficarButtonClicked(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("graphics.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void onEditorButtonClicked(MouseEvent event){
        if(editorPane.isVisible()){
            editorArrow.setVisible(false);
            editorPane.setVisible(false);
        } else{
            editorArrow.setVisible(true);
            editorPane.setVisible(true);
        }
        builderArrow.setVisible(false);
    }


    public void onExitButtonClicked(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void onPrintButtonClicked(MouseEvent event){
        Group chart = new Group();
        //Scene scene1 = new Scene(chart);
        //Stage stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
        graphicPane.getChildren().clear();
/*        Text text = new Text();
        text.setText("WHOOOOOOA!!");
        text.setX(50);
        text.setY(50);

        Text text2 = new Text();
        text2.setText("WHOOOOOOA!!");
        text2.setX(70);
        text2.setY(70);

        List<Object> test = new ArrayList<Object>();
        test.add(text);
        test.add(text2);*/
        flowC.addFlowElement("Process","Imprimir \"" + printTextField.getText() + "\"") ;
        List<Object> flowchart = flowC.getGraphic(graphicPane.getWidth(),graphicPane.getHeight());
        for (int cnt = 0; cnt < flowchart.size(); cnt++){
            graphicPane.getChildren().add((Node)flowchart.get(cnt));
        }
        //System.out.println(flowC.getLength());

        //chart.getChildren().add((Node)event.getSource());
        //chart.getChildren().add(text);
        //stage1.setScene(scene1);
        //stage1.show();
    }

    public void onFinalButtonClicked(MouseEvent event){
        graphicPane.getChildren().clear();
        flowC.addFlowElement("End","");
        List<Object> flowchart = flowC.getGraphic(graphicPane.getWidth(),graphicPane.getHeight());
        for (int cnt = 0; cnt < flowchart.size(); cnt++){
            graphicPane.getChildren().add((Node)flowchart.get(cnt));
        }
    }
}
