package app;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class FlowChart {
    private List<FlowElement> flowchart = new ArrayList<FlowElement>();

    public FlowChart(){
        this.addFlowElement("Start","");
    }

    public int getLength(){
        return this.flowchart.size();
    }

    public void addFlowElement(String etype, String text){  //, int line){
        FlowElement elem = new FlowElement(etype,text);
        this.flowchart.add(elem);
    }

    private List<Object> fitText(int actualX, int actualY, int maxw,int maxh, String text, int fontSize){
        List<Object> elements = new ArrayList<Object>();
        Text processText = new Text(text);
        processText.setFont(Font.font("Verdana",fontSize));
        while ((processText.getLayoutBounds().getWidth() > maxw ) && (fontSize >= 10)){
            fontSize--;
            processText.setFont(Font.font("Verdana",fontSize));
        }

        if(processText.getLayoutBounds().getWidth() > maxw){
            String[] splitted = text.split(" ");
            if (splitted.length > 1){
                String temp = "";
                for(int parts = 0; parts < (int)splitted.length/2; parts++){
                    temp = temp + splitted[parts] + " ";
                }
                processText.setText(temp);
                processText.setX((actualX - (processText.getLayoutBounds().getWidth()/2)));
                processText.setY(actualY + maxh/3 + processText.getLayoutBounds().getHeight()/4);
                elements.add(processText);
                temp = "";
                for(int parts = (int)(splitted.length/2); parts < splitted.length ; parts++){
                    temp = temp + splitted[parts] + " ";
                }
                Text processText2 = new Text(temp);
                processText2.setFont(Font.font("Verdana",fontSize));
                processText2.setX((actualX - (processText2.getLayoutBounds().getWidth()/2)));
                processText2.setY(actualY + (maxh*2)/3 + processText2.getLayoutBounds().getHeight()/4);
                elements.add(processText2);
            } else{
                processText.setX((actualX - (processText.getLayoutBounds().getWidth()/2)));
                processText.setY(actualY + maxh/2 + processText.getLayoutBounds().getHeight()/4);
                elements.add(processText);
            }
        } else{
            processText.setX((actualX - (processText.getLayoutBounds().getWidth()/2)));
            processText.setY(actualY + maxh/2 + processText.getLayoutBounds().getHeight()/4);
            elements.add(processText);
        }
        return elements;
    }

    private Object drawDownLine(int actualX, int actualY, int spaceObj){
        MoveTo point1 = new MoveTo(actualX,actualY - spaceObj);
        MoveTo point2 = new MoveTo(actualX-5,actualY-5);
        MoveTo point3 = new MoveTo(actualX+5,actualY-5);
        LineTo line = new LineTo(actualX,actualY);
        return new Path(point1,line,point2,line,point3,line);
    }

    public List<Object> getGraphic(double w, double h){
        int actualY = 0, actualX = (int) w/2, widthObj = 100, heightObj = 50, spaceObj = 25;
        int fontSize = 20;
        List<Object> elements = new ArrayList<Object>();

        actualY = spaceObj;
        Ellipse start = new Ellipse(actualX,actualY + heightObj/2,widthObj/2,heightObj/2);
        start.setStroke(Color.BLACK);
        start.setFill(Color.LIGHTGREEN);
        Text startText = new Text("Inicio");
        startText.setFont(Font.font("Verdana",fontSize));
        startText.setX((actualX - (startText.getLayoutBounds().getWidth()/2)));
        startText.setY(actualY + heightObj/2 + startText.getLayoutBounds().getHeight()/4);
        elements.add(start);
        elements.add(startText);

        actualY = actualY + heightObj + spaceObj;

        for(int i = 1; i < this.getLength(); i++){
            elements.add(drawDownLine(actualX,actualY,spaceObj));
            FlowElement elem = this.flowchart.get(i);
            if(elem.getElementType().equals("Process")){
                fontSize = 15;
                widthObj = 150;
                Rectangle process = new Rectangle(actualX - widthObj/2,actualY,widthObj,heightObj);
                process.setStroke(Color.BLACK);
                process.setFill(Color.LIGHTBLUE);
                elements.add(process);
                List<Object> processText = fitText(actualX,actualY,widthObj,heightObj,elem.getTextStr(),fontSize);
                for(int line = 0; line < processText.size();line++){
                    elements.add(processText.get(line));
                }
                actualY = actualY + heightObj + spaceObj;

            } else if(elem.getElementType().equals("End")) {
                fontSize = 20;
                widthObj = 100;
                Ellipse end = new Ellipse(actualX,actualY + heightObj/2,widthObj/2,heightObj/2);
                end.setStroke(Color.BLACK);
                end.setFill(Color.SALMON);
                Text endText = new Text("Final");
                endText.setFont(Font.font("Verdana",fontSize));
                endText.setX((actualX - (endText.getLayoutBounds().getWidth()/2)));
                endText.setY(actualY + heightObj/2 + endText.getLayoutBounds().getHeight()/4);
                elements.add(end);
                elements.add(endText);
            } else {
                System.out.println("Warning: Invalid Element Type ");
            }
        }
        return elements;
    }
}
