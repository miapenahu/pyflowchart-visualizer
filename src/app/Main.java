package app;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            //primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root)); //, 280, 50));
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
        try{
            // create a CharStream that reads from standard input
            // create a lexer that feeds off of input CharStream
            Python3Lexer lexer;

            if (args.length>0)
                lexer = new Python3Lexer(CharStreams.fromFileName(args[0]));
            else
                lexer = new Python3Lexer(CharStreams.fromStream(System.in));
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            Python3Parser parser = new Python3Parser(tokens);
            ParseTree tree = parser.file_input(); // begin parsing at init rule

            // Create a generic parse tree walker that can trigger callbacks
            ParseTreeWalker walker = new ParseTreeWalker();
            // Walk the tree created during the parse, trigger callbacks
            walker.walk(new PythonToFlowChart(), tree);
            System.out.println(); // print a \n after translation
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
        }
        launch(args);
    }
}