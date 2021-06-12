package app;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class FlowChart {
    private List<FlowElement> flowchart = new ArrayList<FlowElement>();
    private List<Integer> splitPoints = new ArrayList<Integer>();
    private int lastSplitPoint = -1;
    private int writingBranch = -1;
    private int openDecisions = 0;
    private int writingBlock = -1;
    private int openLoops = 0;
    private int splitsRight = 0;
    private int splitsLeft = 0;
    private int splits = 0;

    public int getWritingBranch() {
        return writingBranch;
    }

    public void setWritingBranch(int writingBranch) {
        this.writingBranch = writingBranch;
    }

    public int getOpenDecisions() {
        return openDecisions;
    }

    public void setOpenDecisions(int openDecisions) {
        this.openDecisions = openDecisions;
    }

    public int getWritingBlock() {
        return writingBlock;
    }

    public void setWritingBlock(int writingBlock) {
        this.writingBlock = writingBlock;
    }

    public int getOpenLoops() {
        return openLoops;
    }

    public void setOpenLoops(int openLoops) {
        this.openLoops = openLoops;
    }

    public void printFlowchart() {
        System.out.println("---------- Comienzo FLowChart ------------");
        for(int i = 0; i < flowchart.size();i++){
            System.out.println();
            flowchart.get(i).printFlowElement();
        }
        System.out.println();
        System.out.println("---------- Final FLowChart ------------");
    }

    public FlowChart(){
        this.addFlowElement("Start","");
    }

    public int getLength(){
        return this.flowchart.size();
    }

    public void addFlowElement(String etype, String text){
        FlowElement elem = new FlowElement(etype,text,writingBranch,writingBlock);
        this.flowchart.add(elem);
        if(etype.equals("Decision")){
            this.openDecisions = this.openDecisions + 1;
            this.setWritingBranch(1); //True
        } else if(etype.equals("Loop")){
            this.openLoops = this.openLoops + 1;
            this.setWritingBlock(1); //True
        }
        if(etype.equals("Merge")){
            this.openDecisions = this.openDecisions - 1;
            if(openDecisions > 0){
                this.setWritingBranch(1); //True
            } else{
                this.setWritingBranch(-1); //None
            }
        }
        if(etype.equals("EndLoop")){
            this.openLoops = this.openLoops - 1;
            if(openLoops > 0){
                this.setWritingBlock(1); //True
            } else {
                this.setWritingBlock(-1); //None
            }
        }
    }

/*    public void addFlowElement(String etype, String text,int branch, int block){  //, int line){
        FlowElement elem = new FlowElement(etype,text,branch,block);
        this.flowchart.add(elem);
    }*/

    private List<Object> fitText(int actualX, int actualY, int maxw,int maxh, String text, int fontSize){
        List<Object> elements = new ArrayList<Object>();
        Text processText = new Text(text);
        processText.setBlendMode(BlendMode.SRC_ATOP);
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

    private Shape drawDownLine(int actualX, int actualY, int spaceObj){
        MoveTo point1 = new MoveTo(actualX,actualY - spaceObj);
        MoveTo point2 = new MoveTo(actualX-5,actualY-5);
        MoveTo point3 = new MoveTo(actualX+5,actualY-5);
        LineTo line = new LineTo(actualX,actualY);
        return new Path(point1,line,point2,line,point3,line);
    }

    private Shape drawParallelogram(int actualX, int actualY, int widthObj, int heightObj, int inclination){
        MoveTo point1 = new MoveTo(actualX - widthObj/2 + inclination,actualY);
        LineTo line1 = new LineTo(actualX + widthObj/2 + inclination,actualY);
        LineTo line2 = new LineTo(actualX + widthObj/2 - inclination,actualY + heightObj);
        LineTo line3 = new LineTo(actualX - widthObj/2 - inclination,actualY + heightObj);
        LineTo line4 = new LineTo(actualX - widthObj/2 + inclination,actualY);
        return new Path(point1,line1,line2,line3,line4);
    }

    private Shape drawRhombus(int actualX, int actualY, int widthObj, int heightObj){
        MoveTo point1 = new MoveTo(actualX, actualY);
        LineTo line1 = new LineTo(actualX + widthObj/2, actualY + heightObj/2);
        LineTo line2 = new LineTo(actualX,actualY + heightObj);
        LineTo line3 = new LineTo(actualX - widthObj/2,actualY + heightObj/2);
        LineTo line4 = new LineTo(actualX,actualY);
        return new Path(point1,line1,line2,line3,line4);
    }

    public void computeSplits(){
        this.splitPoints.clear();
        this.lastSplitPoint = -1;
        List<Integer> temp = new ArrayList<Integer>();
        for(int i = 0; i < this.flowchart.size();i++){
            if(flowchart.get(i).getElementType().equals("Decision") && this.splitPoints.size() == 0){
                this.lastSplitPoint = i;
                temp.add(i);
                this.splitPoints = new ArrayList<Integer>(temp);
            } else if(flowchart.get(i).getElementType().equals("Decision") ){
                temp.clear();
                if(flowchart.get(i).getBranch() == 1){  //If there is another if inside the true block of first if
                    int ind = this.splitPoints.indexOf(this.lastSplitPoint);
                    if(ind > 0){
                        temp = new ArrayList<Integer>(this.splitPoints.subList(0,ind));
                    }
                    temp.add(i);
                    temp.addAll(this.splitPoints.subList(ind,this.splitPoints.size()));
                    this.lastSplitPoint = i;
                    this.splitPoints.clear();
                    this.splitPoints = new ArrayList<Integer>(temp);
                } else if (flowchart.get(i).getBranch() == 0){
                    int ind = this.splitPoints.indexOf(this.lastSplitPoint);
                    temp = new ArrayList<Integer>(this.splitPoints.subList(0,ind + 1));
                    temp.add(i);
                    temp.addAll(this.splitPoints.subList(ind+1,this.splitPoints.size()));
                    this.lastSplitPoint = i;
                    this.splitPoints = new ArrayList<Integer>(temp);
                }
            }
        }
    }

    public List<Integer> computeSplitsToNode(int node){
        //List<Integer> ans = new ArrayList<Integer>();
        this.splitPoints.clear();
        int result[] = new int[]{0,0,0};
        List<Integer> temp = new ArrayList<Integer>();
        for(int i = 0; i < node;i++){
            if(flowchart.get(i).getElementType().equals("Decision") && result[0] == 0){
                result[0]++;
                this.lastSplitPoint = i;
                temp.add(i);
                this.splitPoints = new ArrayList<Integer>(temp);
            } else if(flowchart.get(i).getElementType().equals("Decision") ){
                result[0]++;
                temp.clear();
                if(flowchart.get(i).getBranch() == 1){  //If there is another if inside the true block of first if
                    result[1]++;

                    int ind = this.splitPoints.indexOf(this.lastSplitPoint);
                    if(ind > 0){
                        temp = new ArrayList<Integer>(this.splitPoints.subList(0,ind));
                    }
                    temp.add(i);
                    temp.addAll(this.splitPoints.subList(ind,this.splitPoints.size()));
                    this.lastSplitPoint = i;
                    this.splitPoints.clear();
                    this.splitPoints = new ArrayList<Integer>(temp);
                } else if (flowchart.get(i).getBranch() == 0){
                    result[2]++;
                    int ind = this.splitPoints.indexOf(this.lastSplitPoint);
                    temp = new ArrayList<Integer>(this.splitPoints.subList(0,ind + 1));
                    temp.add(i);
                    temp.addAll(this.splitPoints.subList(ind+1,this.splitPoints.size()));
                    this.lastSplitPoint = i;
                    this.splitPoints = new ArrayList<Integer>(temp);
                }
            }
        }
        return this.splitPoints;
    }

    public List<Integer> getXLayout(int w, int objw){
        int XSpaceObj = 25;
        this.computeSplits();
        List<Integer> results = new ArrayList<Integer>();
        int spc = XSpaceObj + objw;
        int border = (int)(w - (spc*(this.splitPoints.size()+1)));

        for(int i = 0;i < this.splitPoints.size();i++){
            results.add(border + (i*spc));
        }
        //System.out.println("Minimum splitPoints: "+Collections.min(this.splitPoints));
        return results;
    }

    public List<Object> getGraphic2(int w, int h){
        int actualY = 0, actualX, widthObj = 120, heightObj = 50, YSpaceObj = 25, XSpaceObj = 25;
        int fontSize = 20, actualXlay = -1, initXlay = -1;
        List<Object> elements = new ArrayList<Object>();
        List<Integer> xlay = getXLayout(w,widthObj);
        int ylay[] = new int[xlay.size()+2]; //n+1 spaces for n splits, plus initalYlayout in last position
        if(xlay.size() == 0){
            actualX = w/2;
        } else{
            initXlay = this.splitPoints.indexOf(Collections.min(this.splitPoints));
            actualX = xlay.get(initXlay);
        }
        ylay[xlay.size()+1] = YSpaceObj;
        actualY = ylay[xlay.size()+1];
        Ellipse start = new Ellipse(actualX,actualY + heightObj/2,widthObj/2,heightObj/2);
        start.setStroke(Color.BLACK);
        start.setFill(Color.LIGHTGREEN);
        start.setBlendMode(BlendMode.SRC_ATOP);
        Text startText = new Text("Inicio");
        startText.setFont(Font.font("Verdana",fontSize));
        startText.setX((actualX - (startText.getLayoutBounds().getWidth()/2)));
        startText.setBlendMode(BlendMode.SRC_ATOP);
        startText.setY(actualY + heightObj/2 + startText.getLayoutBounds().getHeight()/4);
        elements.add(start);
        elements.add(startText);

        //ylay[actualXlay] = actualY + heightObj + YSpaceObj;
        int finalActualY = actualY;
        ylay = Arrays.stream(ylay).map(i -> finalActualY + heightObj + YSpaceObj).toArray();

        for(int i = 1; i < this.getLength(); i++){
            //elements.add(drawDownLine(actualX,actualY,spaceObj));
            FlowElement elem = this.flowchart.get(i);
            if(elem.getElementType().equals("Process")){
                if(elem.getBranch() != -1){
                    if(elem.getBranch() == 1){
                        actualX = xlay.get(actualXlay) - (XSpaceObj + widthObj)/2;
                        actualY = ylay[actualXlay];
                    } else{
                        actualX = xlay.get(actualXlay) + (XSpaceObj + widthObj)/2;
                        actualY = ylay[actualXlay+1];
                    }
                } else {
                    if(xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                fontSize = 15;
                widthObj = 150;
                Rectangle process = new Rectangle(actualX - widthObj/2,actualY,widthObj,heightObj);
                process.setStroke(Color.BLACK);
                process.setFill(Color.LIGHTBLUE);
                process.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(process);
                List<Object> processText = fitText(actualX,actualY,widthObj,heightObj,elem.getTextStr(),fontSize);
                for(int line = 0; line < processText.size();line++){
                    elements.add(processText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;
                if(elem.getBranch() != -1){
                    if(elem.getBranch() == 1){ ylay[actualXlay] = actualY + heightObj + YSpaceObj;
                    } else{ ylay[actualXlay+1] = actualY + heightObj + YSpaceObj; }
                } else { ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj; }

            } else if(elem.getElementType().equals("IO")) {
                if(elem.getBranch() != -1){
                    if(elem.getBranch() == 1){
                        actualX = xlay.get(actualXlay) - (XSpaceObj + widthObj)/2;
                        actualY = ylay[actualXlay];
                    } else{
                        actualX = xlay.get(actualXlay) + (XSpaceObj + widthObj)/2;
                        actualY = ylay[actualXlay+1];
                    }
                } else {
                    if(xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                fontSize = 15;
                widthObj = 150;
                Shape io = drawParallelogram(actualX,actualY,widthObj,heightObj,10);
                io.setStroke(Color.BLACK);
                io.setFill(Color.LIGHTYELLOW);
                io.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(io);
                List<Object> ioText = fitText(actualX,actualY,widthObj,heightObj,elem.getTextStr(),fontSize);
                for(int line = 0; line < ioText.size();line++){
                    elements.add(ioText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;
                if(elem.getBranch() != -1){
                    if(elem.getBranch() == 1){ ylay[actualXlay] = actualY + heightObj + YSpaceObj;
                    } else{ ylay[actualXlay+1] = actualY + heightObj + YSpaceObj; }
                } else { ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj; }
            } else if(elem.getElementType().equals("Decision")) {
                if(elem.getBranch() != -1){
                    if(elem.getBranch() == 1){
                        actualY = ylay[actualXlay];
                    } else{
                        actualY = ylay[actualXlay+1];
                    }
                } else {
                    actualY = ylay[xlay.size() + 1];
                }
                actualXlay = this.splitPoints.indexOf(i);
                actualX = xlay.get(actualXlay);
                fontSize = 15;
                widthObj = 150;
                Shape decision = drawRhombus(actualX,actualY,widthObj,heightObj);
                decision.setStroke(Color.BLACK);
                decision.setFill(Color.MEDIUMORCHID);
                decision.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(decision);
                List<Object> decisionText = fitText(actualX,actualY+heightObj/10,4*widthObj/5,4*heightObj/5,elem.getTextStr(),fontSize);
                for(int line = 0; line < decisionText.size();line++){
                    elements.add(decisionText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;
                ylay[actualXlay] = actualY + heightObj + YSpaceObj;
                ylay[actualXlay+1] = actualY + heightObj + YSpaceObj;
            } else if(elem.getElementType().equals("End")) {
                fontSize = 20;
                widthObj = 100;
                Ellipse end = new Ellipse(actualX,actualY + heightObj/2,widthObj/2,heightObj/2);
                end.setStroke(Color.BLACK);
                end.setFill(Color.SALMON);
                end.setBlendMode(BlendMode.SRC_ATOP);
                Text endText = new Text("Final");
                endText.setFont(Font.font("Verdana",fontSize));
                endText.setBlendMode(BlendMode.SRC_ATOP);
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

            } else if(elem.getElementType().equals("IO")) {
                fontSize = 15;
                widthObj = 150;
                Shape io = drawParallelogram(actualX,actualY,widthObj,heightObj,10);
                io.setStroke(Color.BLACK);
                io.setFill(Color.LIGHTYELLOW);
                elements.add(io);
                List<Object> ioText = fitText(actualX,actualY,widthObj,heightObj,elem.getTextStr(),fontSize);
                for(int line = 0; line < ioText.size();line++){
                    elements.add(ioText.get(line));
                }
                actualY = actualY + heightObj + spaceObj;
            } else if(elem.getElementType().equals("Decision")) {
                fontSize = 15;
                widthObj = 150;
                Shape decision = drawRhombus(actualX,actualY,widthObj,heightObj);
                decision.setStroke(Color.BLACK);
                decision.setFill(Color.MEDIUMORCHID);
                elements.add(decision);
                List<Object> decisionText = fitText(actualX,actualY+heightObj/10,4*widthObj/5,4*heightObj/5,elem.getTextStr(),fontSize);
                for(int line = 0; line < decisionText.size();line++){
                    elements.add(decisionText.get(line));
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
