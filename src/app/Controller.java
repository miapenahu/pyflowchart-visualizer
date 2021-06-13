package app;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class Controller {

    private FlowChart flowC = new FlowChart();;

    private Stage stage;
    private Stage graphics;
    private Scene scene;
    private Parent root;

    private int openBranches = 0;
    //private int writingBranch = -1;
    private int openWhileBlocks = 0;

    @FXML private AnchorPane editorPane;
    @FXML private ImageView editorArrow;
    @FXML private ImageView builderArrow;
    @FXML private Pane graphicPane;
    @FXML private ImageView closeGraphicsButton;
    //Text fields to complement flowchart
    @FXML private TextField printTextField;
    @FXML private TextField crearVarField;
    @FXML private TextField ifField;
    @FXML private Button ifTrueFalseButton;
    @FXML private TextField cntOpenBranches;
    @FXML private TextField whileField;
    @FXML private Button whileTrueFalseButton;
    @FXML private Button endBlockButton;
    @FXML private TextField cntOpenWhileBlocks;
    @FXML private TextField splitsInField;

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
        graphics = new Stage();
        graphics.initStyle(StageStyle.TRANSPARENT);
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        graphics.setScene(scene);
        ResizeHelper.addResizeListener(graphics);
        graphics.show();
    }

    private void printToGraphicPane(){
        graphicPane.getChildren().clear();
        List<Object> flowchart = flowC.getGraphic2((int)graphicPane.getWidth(),(int)graphicPane.getHeight());
        for (int cnt = 0; cnt < flowchart.size(); cnt++){
            graphicPane.getChildren().add((Node)flowchart.get(cnt));
        }
    }

    private void startListeningWH(){ //Listens to scene width an height after graphicPane is created (to draw the flowchart)
        Scene Gscene = (Scene) closeGraphicsButton.getScene();
        Gscene.widthProperty().addListener((obs, oldVal, newVal) -> {
            printToGraphicPane();
        });

        Gscene.heightProperty().addListener((obs, oldVal, newVal) -> {
        });
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

    public void onCloseGraphicsButtonClicked(MouseEvent event){
        graphics = (Stage) closeGraphicsButton.getScene().getWindow();
        graphics.close();
    }

    public void onExitButtonClicked(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void onPrintButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("IO","Imprimir \"" + printTextField.getText() + "\"");
        printToGraphicPane();
    }

    public void onCrearVariableButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("Process","Crear variable \""+ crearVarField.getText() + "\"");
        printToGraphicPane();;

    }

    public void onIfButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("Decision","¿"+ ifField.getText() + "?");
        printToGraphicPane();
        cntOpenBranches.setText(""+flowC.getOpenDecisions());
        ifTrueFalseButton.setText("true");
    }

    public void onTrueFalseButtonClicked(MouseEvent event){
        //System.out.println(ifTrueFalseButton.getText());
        if(ifTrueFalseButton.getText().equals("true")){
            flowC.setWritingBranch(0); //False
            ifTrueFalseButton.setText("false");
        } else{
            flowC.setWritingBranch(1); //True
            ifTrueFalseButton.setText("true");
        }
    }

    public void onMergeButtonClicked(MouseEvent event){
        flowC.addFlowElement("Merge","");
        printToGraphicPane();
        cntOpenBranches.setText(""+flowC.getOpenDecisions());
        if(flowC.getOpenDecisions() == 0){
            ifTrueFalseButton.setText("none");
        } else {
            if (flowC.getWritingBranch() == 0) {
                ifTrueFalseButton.setText("false");
            } else if (flowC.getWritingBranch() == 1) {
                ifTrueFalseButton.setText("true");
            }
        }
    }

    public void onWhileButtonClicked(MouseEvent event){
        graphicPane.getChildren().clear();
        flowC.addFlowElement("Loop","¿"+ ifField.getText() + "?");
        cntOpenWhileBlocks.setText(""+flowC.getOpenLoops());
        whileTrueFalseButton.setText("true");
    }

    public void onEndBlockButtonClicked(MouseEvent event){
        if(whileTrueFalseButton.getText().equals("true")){
            flowC.setWritingBlock(0); //False
            whileTrueFalseButton.setText("false");
            endBlockButton.setText("end loop");
        } else if(whileTrueFalseButton.getText().equals("false")){
            flowC.addFlowElement("EndLoop","");
            cntOpenWhileBlocks.setText(""+flowC.getOpenLoops());
            if(flowC.getOpenLoops() == 0){
                whileTrueFalseButton.setText("none");
            }
        }
    }

    public void onCntOpenBranchesClicked(MouseEvent event){
        cntOpenBranches.setText(""+openBranches);
    }

    public void onSplitsInButtonClicked(MouseEvent event){
        List<Integer> test = flowC.computeSplitsToNode(Integer.parseInt(splitsInField.getText()));
        for(int i = 0; i < test.size(); i++){
            System.out.print(test.get(i)+", ");
        }
        System.out.println();
        test = flowC.getXLayout((int)graphicPane.getWidth(),100);
        for(int i = 0; i < test.size(); i++){
            System.out.print(test.get(i)+", ");
        }
        System.out.println();
    }

    public void onFinalButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("End","");
        printToGraphicPane();
    }
}