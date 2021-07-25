package app;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private FlowChart flowC = new FlowChart();
    InputStream stream;
    private FlowChart Drawerchart; // = new FlowChart();

    private Stage stage;
    private Stage graphics;
    private Stage drawer;
    private Scene scene;
    private Parent root;

    public void setDrawerchart(FlowChart drawerchart) {
        Drawerchart = drawerchart;
    }

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
    //Drawer
    @FXML private Pane drawerPane;
    @FXML private ImageView closeDrawerButton;
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
    //@FXML private TextView ivtext;

    public void initialize(){
        /*editorTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // this will run whenever text is changed
                //System.out.println("Testing listener");
                test();
            }
        });*/
        if(editorTextArea != null) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            editorTextArea.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        pause.setOnFinished(event -> analizeGrammar());//doSomething(newValue));
                        pause.playFromStart();
                    });
        }
        if(drawerPane != null){
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            drawerPane.widthProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        pause.setOnFinished(event -> startListeningWHDrawer());//doSomething(newValue));
                        pause.playFromStart();
                    });

        }
    }

    public void analizeGrammar(){
        try{
            // create a CharStream that reads from standard input
            // create a lexer that feeds off of input CharStream
            PythonLexer lexer;

            //lexer = new Python3Lexer(CharStreams.fromFileName(args[0]));
            //if (args.length>0)
            //    lexer = new PythonLexer(CharStreams.fromFileName(args[0]));
            //else
            stream = new ByteArrayInputStream(editorTextArea.getText().getBytes(StandardCharsets.UTF_8));
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
            PythonToFlowChart pyflow = new PythonToFlowChart();
            walker.walk(pyflow, tree);
            /*if(pyflow.getDrawerChart() != null){
                //System.out.println("pyflow isnt null!");
                pyflow.getDrawerChart().printFlowchartTrace();
            }*/
            Drawerchart = new FlowChart(pyflow.getDrawerChart());
            /*if(Drawerchart == null){
                System.out.println("Drawchart is null!");
            } else {
                Drawerchart.printFlowchartTrace();
            }*/
            //printToDrawerPane(pyflow.drawerChart);
           // System.out.println("Good code!"); // print a \n after translation
            editorTextArea.setStyle("-fx-text-fill: green");
        } catch (Exception e){
            //System.out.println("Bad code :(");
            editorTextArea.setStyle("-fx-text-fill: red");
            System.err.println("Error (Test): "+ e);
            //e.printStackTrace();
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

    public String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + "\n");
            }

        } catch (FileNotFoundException ex) {
            System.out.print("");
        } catch (IOException ex) {
            System.out.print("");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                System.out.print("");
            }
        }

        return stringBuffer.toString();
    }

    public void onAbrirButtonClicked(MouseEvent event) throws IOException{

        //FileChooser fileC = new FileChooser();
        fileC.setTitle("Abrir Codigo");

        // Agregar filtros para facilitar la busqueda
        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PY", "*.py")
        );

        // Obtener archivo seleccionado
        File txtFile = fileC.showOpenDialog(stage);
        if (txtFile != null) {
            editorTextArea.setText(readFile(txtFile));
        }

    }

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
        //root = FXMLLoader.load(getClass().getResource("drawer.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("drawer.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        root = loader.load();
        drawer = new Stage();
        drawer.initStyle(StageStyle.TRANSPARENT);
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Controller controller = loader.getController();
        controller.setDrawerchart(Drawerchart);
        scene.setFill(Color.TRANSPARENT);
        drawer.setScene(scene);
        ResizeHelper.addResizeListener(drawer);
        drawer.show();
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
        printToGraphicPane();

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

    //Drawer
    public void onCloseDrawerButtonClicked(MouseEvent event){
        //startListeningWHDrawer();
        drawer = (Stage) closeDrawerButton.getScene().getWindow();
        drawer.close();
    }

    private void printToDrawerPane(){
        drawerPane.getChildren().clear();
        /*if(Drawerchart == null){
            System.out.println("Drawerchart null!");
        }*/
        List<Object> flowchart = Drawerchart.getGraphic2((int)drawerPane.getWidth(),(int)drawerPane.getHeight());
        for (int cnt = 0; cnt < flowchart.size(); cnt++){
            drawerPane.getChildren().add((Node)flowchart.get(cnt));
        }
    }

    private void startListeningWHDrawer(){ //Listens to scene width an height after graphicPane is created (to draw the flowchart)
        printToDrawerPane();
        Scene Gscene = (Scene) closeDrawerButton.getScene();
        Gscene.widthProperty().addListener((obs, oldVal, newVal) -> {
            printToDrawerPane();
            //System.out.println("Width: "+drawerPane.getWidth());
            //System.out.println("Height: "+drawerPane.getHeight());
        });

        Gscene.heightProperty().addListener((obs, oldVal, newVal) -> {
        });

    }
}