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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {

    private FlowChart flowC = new FlowChart();
    private List<FlowChart> FCList = new ArrayList<>();
    private List<String> FCnames = new ArrayList<>();
    private List<Stage> drawerChilds = new ArrayList<>();
    private FlowChart Drawerchart; // = new FlowChart();
    private String WindowName;
    private String ErrorString = "";
    private int initH = 0;

    public String getErrorString() {
        return ErrorString;
    }

    public void setErrorString(String errorString) {
        ErrorString = errorString;
    }

    public String getWindowName() {
        return WindowName;
    }

    public void setWindowName(String windowName) {
        WindowName = windowName;
    }

    private Stage stage;
    private Stage simulator;
    private Stage drawer;
    private Stage error = new Stage();;
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
    @FXML private ImageView editorButton;
    @FXML private ImageView simulatorButton;
    @FXML private ImageView exitButton;
    @FXML private ImageView loadButton;
    @FXML private ImageView saveButton;
    @FXML private ImageView drawButton;
    @FXML private ImageView closeChildrenButton;
    @FXML private ImageView debugButton;
    //graphics.fxml
    @FXML private AnchorPane editorPane;
    @FXML private ImageView editorArrow;
    @FXML private ImageView builderArrow;
    @FXML private Pane simulatorPane;
    @FXML private ImageView closeSimulatorButton;
    //Drawer
    @FXML private Label drawerLabel;
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
    //error.fxml
    @FXML private ImageView closeErrorButton;
    @FXML private TextArea errorTextArea;

    public void initialize(){
        initEditor();
        initSimulator();
        if(editorTextArea != null) { //If is initializing editor
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            editorTextArea.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        pause.setOnFinished(event -> analizeGrammar());//doSomething(newValue));
                        pause.playFromStart();
                    });
        }
        if(drawerPane != null){ //If is initializing drawer
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            drawerPane.widthProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        pause.setOnFinished(event -> initDrawer());//doSomething(newValue));
                        pause.playFromStart();
                    });
        }

        if(errorTextArea != null) { //If is initializing drawer
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            errorTextArea.widthProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        pause.setOnFinished(event -> initError());//doSomething(newValue));
                        pause.playFromStart();
                    });
        }

    }

    public void setButtonListener(ImageView button){
        Bloom bloom1 = new Bloom();
        bloom1.setThreshold(0.1);

        Bloom bloom2 = new Bloom();
        bloom2.setThreshold(1.0);

        if(button != null){
            button.hoverProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if(newValue) { button.setEffect(bloom1);
                        } else { button.setEffect(bloom2); }
                    });
        }
    }

    //-----------------------------------Editor buttons------------------------------------//
    public void analizeGrammar(){
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
            PythonToFlowChart pyflow = new PythonToFlowChart();
            walker.walk(pyflow, tree);
            /*if(pyflow.getDrawerChart() != null){
                //System.out.println("pyflow isnt null!");
                pyflow.getDrawerChart().printFlowchartTrace();
            }*/
            FCList = new ArrayList<>();

            for(FlowChart elem : pyflow.getFCList()){
                FCList.add(elem);
            }
            FCnames = new ArrayList<>();
            for(String elem : pyflow.getFCnames()){
                FCnames.add(elem);
                System.out.println("FCname: "+ elem);
            }
            //System.out.println("FCList size: "+FCList.size());
            //System.out.println("FCnames size: "+FCList.size());
            Drawerchart = new FlowChart(pyflow.getDrawerChart());
            /*if(Drawerchart == null){
                System.out.println("Drawchart is null!");
            } else {
                Drawerchart.printFlowchartTrace();
            }*/
            //printToDrawerPane(pyflow.drawerChart);
           // System.out.println("Good code!"); // print a \n after translation
            editorTextArea.setStyle("-fx-text-fill: green");
            ErrorString = "";
        } catch (Exception e){
            //System.out.println("Bad code :(");
            editorTextArea.setStyle("-fx-text-fill: red");
            System.err.println("ANTLR Error : "+ e.getMessage());
            ErrorString = e.getMessage();
            e.printStackTrace();
        }
    }

    public void initEditor(){
        setButtonListener(editorButton);
        setButtonListener(simulatorButton);
        setButtonListener(exitButton);
        setButtonListener(loadButton);
        setButtonListener(saveButton);
        setButtonListener(drawButton);
        setButtonListener(closeChildrenButton);
        setButtonListener(debugButton);
    }

    public void onCloseChildrenButtonClicked(MouseEvent event) throws IOException{
        for(Stage elem : drawerChilds){
            drawer = elem;
            drawer.close();
        }
        drawerChilds = new ArrayList<>();
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

    //Upper menu buttons
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

    public void onSimulatorButtonClicked(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulator.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        root = loader.load();
        simulator = new Stage();
        simulator.initStyle(StageStyle.TRANSPARENT);
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        simulator.setScene(scene);
        ResizeHelper.addResizeListener(simulator);
        simulator.show();
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
                new FileChooser.ExtensionFilter("Python script", "*.py")
        );

        // Obtener archivo seleccionado
        File txtFile = fileC.showOpenDialog(stage);
        if (txtFile != null) {
            editorTextArea.setText(readFile(txtFile));
            fileC.setInitialDirectory(txtFile.getParentFile());
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
    }

    public void onDrawButtonClicked(MouseEvent event) throws IOException{
        //root = FXMLLoader.load(getClass().getResource("drawer.fxml"));
        //for(FlowChart elem : FCList){
        for(int elem = 0; elem < FCList.size();elem++){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("drawer.fxml"));
            //stage.initStyle(StageStyle.TRANSPARENT);
            root = loader.load();
            drawer = new Stage();
            drawer.initStyle(StageStyle.TRANSPARENT);
            //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            Controller controller = loader.getController();
            controller.setDrawerchart(FCList.get(elem));
            controller.setWindowName(FCnames.get(elem));
            scene.setFill(Color.TRANSPARENT);
            drawer.setScene(scene);
            ResizeHelper.addResizeListener(drawer);
            drawer.show();
            drawer.setX(100 + 50*elem);
            drawer.setY(100 + 50*elem);
            drawerChilds.add(drawer);
        }

    }

    public void onDebugButtonClicked(MouseEvent event) throws IOException{
        error.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("error.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        root = loader.load();
        error = new Stage();
        error.initStyle(StageStyle.TRANSPARENT);
        //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Controller controller = loader.getController();
        controller.setErrorString(ErrorString);
        scene.setFill(Color.TRANSPARENT);
        error.setScene(scene);
        ResizeHelper.addResizeListener(error);
        error.show();
    }

    public void onExitButtonClicked(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }
    //-------------------------------------------------------------------------------------//

    //-----------------------------------Simulator buttons---------------------------------//

    private void initSimulator(){
        setButtonListener(closeSimulatorButton);
    }

    private void printToSimulatorPane(){
        simulatorPane.getChildren().clear();
        List<Object> flowchart = flowC.getGraphic2((int) simulatorPane.getWidth(),initH);
        for (int cnt = 0; cnt < flowchart.size(); cnt++){
            simulatorPane.getChildren().add((Node)flowchart.get(cnt));
        }
    }

    private void startListeningWH(){ //Listens to scene width an height after graphicPane is created (to draw the flowchart)
        Scene Gscene = (Scene) closeSimulatorButton.getScene();
        Gscene.widthProperty().addListener((obs, oldVal, newVal) -> {
            printToSimulatorPane();
        });

        Gscene.heightProperty().addListener((obs, oldVal, newVal) -> {
        });

    }

    public void onCloseSimulatorButtonClicked(MouseEvent event){
        simulator = (Stage) closeSimulatorButton.getScene().getWindow();
        simulator.close();
    }

    public void onPrintButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("IO","Imprimir \"" + printTextField.getText() + "\"");
        printToSimulatorPane();
    }

    public void onCrearVariableButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("Process","Crear variable \""+ crearVarField.getText() + "\"");
        printToSimulatorPane();

    }

    public void onIfButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("Decision","¿"+ ifField.getText() + "?");
        printToSimulatorPane();
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
        printToSimulatorPane();
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
        startListeningWH();
        flowC.addFlowElement("Loop","¿"+ whileField.getText() + "?");
        printToSimulatorPane();
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
            } else{
                whileTrueFalseButton.setText("true");
                endBlockButton.setText("end block");
                flowC.setWritingBlock(1);
            }
        }
    }

    public void onCntOpenBranchesClicked(MouseEvent event){
        cntOpenBranches.setText(""+openBranches);
    }

    public void onFinalButtonClicked(MouseEvent event){
        startListeningWH();
        flowC.addFlowElement("End","");
        printToSimulatorPane();
    }

    public void onScrollSimulatorPane(ScrollEvent event){
        double deltaY = event.getDeltaY();
        if(deltaY < 0){
            initH += 10;
            printToSimulatorPane();
        } else{
            initH -= 10;
            printToSimulatorPane();
        }
    }
    //-------------------------------------------------------------------------------------//

    //-----------------------------------Drawer buttons------------------------------------//

    public void initDrawer(){
        setButtonListener(closeDrawerButton);
        drawerLabel.setText(WindowName);
        startListeningWHDrawer();
    }

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
        List<Object> flowchart = Drawerchart.getGraphic2((int)drawerPane.getWidth(),initH);
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

    public void onScrollDrawerPane(ScrollEvent event){
        double deltaY = event.getDeltaY();
        if(deltaY < 0){
            initH += 10;
            printToDrawerPane();
        } else{
            initH -= 10;
            printToDrawerPane();
        }
    }
    //-------------------------------------------------------------------------------------//

    //----------------------------------Error-----------------------------------------------//

    public void initError(){
        setButtonListener(closeErrorButton);
        errorTextArea.setText(ErrorString);
    }

    public void onCloseErrorButtonClicked(MouseEvent event){
        error = (Stage) closeErrorButton.getScene().getWindow();
        error.close();
    }

    //-------------------------------------------------------------------------------------//
}