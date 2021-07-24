package app;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    private FlowChart flowC = new FlowChart();;

    private Stage stage;
    private Stage graphics;
    private Scene scene;
    private Parent root;

    private int openBranches = 0;
    //private int writingBranch = -1;
    private int openWhileBlocks = 0;
    //editor.fxml
    @FXML private TextArea editorTextArea;
    //graphics.fxml
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

    public void initialize(){
        /*editorTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // this will run whenever text is changed
                //System.out.println("Testing listener");
                test();
            }
        });*/
//        PauseTransition pause = new PauseTransition(Duration.seconds(1));
//        editorTextArea.textProperty().addListener(
//                (observable, oldValue, newValue) -> {
//                    pause.setOnFinished(event -> test());//doSomething(newValue));
//                    pause.playFromStart();
//                });
    }

    public void test(){
        try{
            // create a CharStream that reads from standard input
            // create a lexer that feeds off of input CharStream
            PythonLexer lexer;

            //lexer = new Python3Lexer(CharStreams.fromFileName(args[0]));
            //if (args.length>0)
            //    lexer = new PythonLexer(CharStreams.fromFileName(args[0]));
            //else
            InputStream stream = new ByteArrayInputStream(editorTextArea.getText().getBytes(StandardCharsets.UTF_8));
            lexer = new PythonLexer(CharStreams.fromStream(stream));
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            PythonParser parser = new PythonParser(tokens);
            //ParseTree tree = parser.single_input(); // begin parsing at init rule

            //ParseTree tree = parser.test();
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);
            ParseTree tree = parser.root(); // begin parsing at init rule
            //for (ANTLRErrorListener listener : parser.getErrorListeners()) { //View all error listeners
            //    System.out.println(listener);
            //}
            // Create a generic parse tree walker that can trigger callbacks
            ParseTreeWalker walker = new ParseTreeWalker();
            // Walk the tree created during the parse, trigger callbacks
            walker.walk(new PythonToFlowChart(), tree);
           // System.out.println("Good code!"); // print a \n after translation
            editorTextArea.setStyle("-fx-text-fill: green");
        } catch (Exception e){
            //System.out.println("Bad code :(");
            editorTextArea.setStyle("-fx-text-fill: red");
            System.err.println("Error (Test): " + e);
        }
    }

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

    FileChooser fileC = new FileChooser();



    public void onGuardarButtonClicked(MouseEvent event) throws IOException{
        //fileC.setInitialDirectory(new File("C:\\"));

        Window stage = editorTextArea.getScene().getWindow();
        fileC.setTitle("Guardar Código");
        fileC.setInitialFileName("script");
        fileC.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Python script", "*.py"));
        try {
            File file = fileC.showSaveDialog(stage);
            ObservableList<CharSequence> paragraph = editorTextArea.getParagraphs();
            Iterator<CharSequence> iter = paragraph.iterator();
            try {
                BufferedWriter bf = new BufferedWriter(new FileWriter(new File(file.getAbsolutePath())));
                while(iter.hasNext()) {
                    CharSequence seq = iter.next();
                    bf.append(seq);
                    bf.newLine();
                }
                bf.flush();
                bf.close();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e);
            }
            //System.out.println("Abs path: "+ file.getAbsolutePath());
            fileC.setInitialDirectory(file.getParentFile());
        } catch(Exception e){
            System.err.println("Error al guardar: " + e);
        }
        /**/
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
        /*List<Integer> test = flowC.computeSplitsToNode(Integer.parseInt(splitsInField.getText()));
        for(int i = 0; i < test.size(); i++){
            System.out.print(test.get(i)+", ");
        }
        System.out.println();
        test = flowC.getXLayout((int)graphicPane.getWidth(),100,25);
        for(int i = 0; i < test.size(); i++){
            System.out.print(test.get(i)+", ");
        }
        System.out.println();*/
    }

    public void onFinalButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("End","");
        printToGraphicPane();
    }
}