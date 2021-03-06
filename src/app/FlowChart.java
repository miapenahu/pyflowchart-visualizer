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
    private List<List<Integer>> splitPoints = new ArrayList<>();
    private int lastSplitPoint = -1;
    private int writingBranch = -1;
    private int openDecisions = 0;
    private int writingBlock = -1;
    private int openLoops = 0;
    private int splitsRight = 0;
    private int splitsLeft = 0;
    private int splits = 0;

    public List<FlowElement> getFlowchart() {
        return flowchart;
    }

    public void setFlowchart(List<FlowElement> flowchart) {
        this.flowchart = flowchart;
    }

    public List<List<Integer>> getSplitPoints() {
        return splitPoints;
    }

    public void setSplitPoints(List<List<Integer>> splitPoints) {
        this.splitPoints = splitPoints;
    }

    public int getLastSplitPoint() {
        return lastSplitPoint;
    }

    public void setLastSplitPoint(int lastSplitPoint) {
        this.lastSplitPoint = lastSplitPoint;
    }

    public int getSplitsRight() {
        return splitsRight;
    }

    public void setSplitsRight(int splitsRight) {
        this.splitsRight = splitsRight;
    }

    public int getSplitsLeft() {
        return splitsLeft;
    }

    public void setSplitsLeft(int splitsLeft) {
        this.splitsLeft = splitsLeft;
    }

    public int getSplits() {
        return splits;
    }

    public void setSplits(int splits) {
        this.splits = splits;
    }

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

    public void printFlowchartTrace() {
        System.out.println("---------- Comienzo FLowChart ------------");
        for (int i = 0; i < flowchart.size(); i++) {
            System.out.println();
            flowchart.get(i).printFlowElement();
        }
        System.out.println();
        System.out.println("---------- Final FLowChart ------------");
    }

    public FlowChart() {
        this.addFlowElement("Start", "");
    }

    public FlowChart(FlowChart another) {
        this.flowchart = another.getFlowchart();
        this.splitPoints = another.getSplitPoints();
        this.lastSplitPoint = another.getLastSplitPoint();
        this.writingBranch = another.getWritingBranch();
        this.openDecisions = another.getOpenDecisions();
        this.writingBlock = another.getWritingBlock();
        this.openLoops = another.getOpenLoops();
        this.splitsRight = another.getSplitsRight();
        this.splitsLeft = another.getSplitsLeft();
        this.splits = another.getSplits();
    }


    public int getLength() {
        return this.flowchart.size();
    }

    public void addFlowElement(String etype, String text) {
        if (etype.equals("Merge")) {
            if (this.openDecisions > 1) {
                this.openDecisions = this.openDecisions - 1;
                this.setWritingBranch(this.getWritingBranch());
            } else if (this.openDecisions == 1) {
                this.openDecisions = this.openDecisions - 1;
                this.setWritingBranch(-1); //None
            }
        }
        if (etype.equals("EndLoop")) {
            if (this.openLoops > 1) {
                this.openLoops = this.openLoops - 1;
                this.setWritingBlock(1); //True
            } else if (this.openLoops == 1) {
                this.openLoops = this.openLoops - 1;
                this.setWritingBlock(-1); //None
            }
        }
        FlowElement elem = new FlowElement(etype, text, writingBranch, writingBlock);
        this.flowchart.add(elem);
        if (etype.contains("Decision")) {
            this.openDecisions = this.openDecisions + 1;
            this.setWritingBranch(1); //True
        } else if (etype.equals("Loop")) {
            this.openLoops = this.openLoops + 1;
            this.setWritingBlock(1); //True
        }

    }

/*    public void addFlowElement(String etype, String text,int branch, int block){  //, int line){
        FlowElement elem = new FlowElement(etype,text,branch,block);
        this.flowchart.add(elem);
    }*/

    private List<Object> fitText(int actualX, int actualY, int maxw, int maxh, String text, int fontSize) {
        List<Object> elements = new ArrayList<Object>();
        Text processText = new Text(text);
        processText.setBlendMode(BlendMode.SRC_ATOP);
        processText.setFont(Font.font("Verdana", fontSize));
        while ((processText.getLayoutBounds().getWidth() > maxw) && (fontSize >= 10)) {
            fontSize--;
            processText.setFont(Font.font("Verdana", fontSize));
        }

        if (processText.getLayoutBounds().getWidth() > maxw) {
            String[] splitted = text.split(" ");
            if (splitted.length > 1) {
                String temp = "";
                for (int parts = 0; parts < (int) splitted.length / 2; parts++) {
                    temp = temp + splitted[parts] + " ";
                }
                processText.setText(temp);
                processText.setX((actualX - (processText.getLayoutBounds().getWidth() / 2)));
                processText.setY(actualY + maxh / 3 + processText.getLayoutBounds().getHeight() / 4);
                elements.add(processText);
                temp = "";
                for (int parts = (int) (splitted.length / 2); parts < splitted.length; parts++) {
                    temp = temp + splitted[parts] + " ";
                }
                Text processText2 = new Text(temp);
                processText2.setBlendMode(BlendMode.SRC_ATOP);
                processText2.setFont(Font.font("Verdana", fontSize));
                processText2.setX((actualX - (processText2.getLayoutBounds().getWidth() / 2)));
                processText2.setY(actualY + (maxh * 2) / 3 + processText2.getLayoutBounds().getHeight() / 4);
                elements.add(processText2);
            } else {
                processText.setX((actualX - (processText.getLayoutBounds().getWidth() / 2)));
                processText.setY(actualY + maxh / 2 + processText.getLayoutBounds().getHeight() / 4);
                elements.add(processText);
            }
        } else {
            processText.setX((actualX - (processText.getLayoutBounds().getWidth() / 2)));
            processText.setY(actualY + maxh / 2 + processText.getLayoutBounds().getHeight() / 4);
            elements.add(processText);
        }
        return elements;
    }

    private Shape drawDownLine(int actualX, int actualY, int spaceObj) {
        MoveTo point1 = new MoveTo(actualX, actualY - spaceObj);
        MoveTo point2 = new MoveTo(actualX - 5, actualY - 5);
        MoveTo point3 = new MoveTo(actualX + 5, actualY - 5);
        LineTo line = new LineTo(actualX, actualY);
        Path ans = new Path(point1, line, point2, line, point3, line);
        ans.setBlendMode(BlendMode.SRC_ATOP);
        return ans;
    }

    private Shape drawDecisionLine(int actualX, int actualY, int YSpaceObj, int destX) {
        MoveTo point1 = new MoveTo(actualX, actualY - YSpaceObj);
        LineTo line1 = new LineTo(actualX, actualY - YSpaceObj / 2);
        LineTo line2 = new LineTo(destX, actualY - YSpaceObj / 2);
        MoveTo point2 = new MoveTo(destX - 5, actualY - 5);
        MoveTo point3 = new MoveTo(destX + 5, actualY - 5);
        LineTo lineEnd = new LineTo(destX, actualY);
        Path ans = new Path(point1, line1, line2, lineEnd, point2, lineEnd, point3, lineEnd);
        ans.setBlendMode(BlendMode.SRC_ATOP);
        return ans;
    }

    //drawLoopLine(actualX,actualY,widthObj,actualLoopReturn)
    private Shape drawLoopLines(int actualX, int actualY, int margin, int widthObj, int heightObj, int returnY, int YSpaceObj) {
        MoveTo point1 = new MoveTo(actualX, actualY);
        LineTo line1 = new LineTo(actualX, actualY + (YSpaceObj / 4));
        LineTo line2 = new LineTo(actualX - (widthObj / 2) - margin, actualY + (YSpaceObj / 4));
        LineTo line3 = new LineTo(actualX - (widthObj / 2) - margin, returnY - (YSpaceObj / 4));
        LineTo lineEnd = new LineTo(actualX, returnY - (YSpaceObj / 4));
        MoveTo point2 = new MoveTo(actualX - 5, returnY - (YSpaceObj / 4) - 5);
        MoveTo point3 = new MoveTo(actualX - 5, returnY - (YSpaceObj / 4) + 5);
        MoveTo point4 = new MoveTo(actualX + (widthObj / 2) + margin, returnY + heightObj);
        LineTo line4 = new LineTo(actualX + (widthObj / 2) + margin, actualY + (YSpaceObj / 2));
        LineTo line5 = new LineTo(actualX, actualY + (YSpaceObj / 2));
        Path ans = new Path(point1, line1, line2, line3, lineEnd, point2, lineEnd, point3, lineEnd, point4, line4, line5);
        ans.setBlendMode(BlendMode.SRC_ATOP);
        return ans;
    }

    private Shape drawBranchesLine(int initDestXY1[], int initDestXY2[]) {
        MoveTo point1 = new MoveTo(initDestXY1[0], initDestXY1[1]);
        LineTo line1 = new LineTo(initDestXY1[2], initDestXY1[1]);
        LineTo line2 = new LineTo(initDestXY1[2], initDestXY1[3]);
        MoveTo point2 = new MoveTo(initDestXY2[0], initDestXY2[1]);
        LineTo line3 = new LineTo(initDestXY2[2], initDestXY2[1]);
        LineTo line4 = new LineTo(initDestXY2[2], initDestXY2[3]);
        Path ans = new Path(point1, line1, line2, point2, line3, line4);
        ans.setBlendMode(BlendMode.SRC_ATOP);
        return ans;
    }

    private List<Text> drawBranchesText(int initDestXY1[], int initDestXY2[]) {
        List<Text> text = new ArrayList<Text>();
        Text yesText = new Text("Si");
        yesText.setFont(Font.font("Verdana", 15));
        yesText.setX((initDestXY1[2] - yesText.getLayoutBounds().getWidth() - 5));
        yesText.setY((initDestXY1[1] + initDestXY1[3]) / 2 + yesText.getLayoutBounds().getHeight() / 4);
        yesText.setBlendMode(BlendMode.SRC_ATOP);
        text.add(yesText);
        Text noText = new Text("No");
        noText.setFont(Font.font("Verdana", 15));
        noText.setX(initDestXY2[2] + 5);
        noText.setY((initDestXY2[1] + initDestXY2[3]) / 2 + noText.getLayoutBounds().getHeight() / 4);
        noText.setBlendMode(BlendMode.SRC_ATOP);
        text.add(noText);
        return text;
    }

    private Shape drawMergeLine(int actualX1, int actualY1, int actualX2, int actualY2, int YSpaceObj, int destX) {
/*        MoveTo point1 = new MoveTo(initX1,actualY - YSpaceObj);
        LineTo line1 = new LineTo(initX1,actualY - YSpaceObj/2);
        MoveTo point2 = new MoveTo(initX2,actualY - YSpaceObj);
        LineTo line2 = new LineTo(initX2,actualY - YSpaceObj/2);
        MoveTo point3 = new MoveTo(destX,actualY - YSpaceObj/2);
        LineTo line3 = new LineTo(initX1,actualY - YSpaceObj/2);
        LineTo line4 = new LineTo(initX2,actualY - YSpaceObj/2);
        LineTo line5 = new LineTo(destX,actualY);
        Path ans = new Path(point1,line1,point2,line2,point3,line3,point3,line4,point3,line5);*/
        int maxY;
        if (actualY1 < actualY2) {
            maxY = actualY2;
        } else {
            maxY = actualY1;
        }
        MoveTo point1 = new MoveTo(actualX1, actualY1);
        LineTo line1 = new LineTo(actualX1, maxY + YSpaceObj / 2);
        LineTo line2 = new LineTo(actualX2, maxY + YSpaceObj / 2);
        LineTo line3 = new LineTo(actualX2, actualY2);
        Path ans = new Path(point1, line1, line2, line3);
        ans.setBlendMode(BlendMode.SRC_ATOP);
        return ans;
    }


    private Shape drawParallelogram(int actualX, int actualY, int widthObj, int heightObj, int inclination) {
        MoveTo point1 = new MoveTo(actualX - widthObj / 2 + inclination, actualY);
        LineTo line1 = new LineTo(actualX + widthObj / 2 + inclination, actualY);
        LineTo line2 = new LineTo(actualX + widthObj / 2 - inclination, actualY + heightObj);
        LineTo line3 = new LineTo(actualX - widthObj / 2 - inclination, actualY + heightObj);
        LineTo line4 = new LineTo(actualX - widthObj / 2 + inclination, actualY);
        return new Path(point1, line1, line2, line3, line4);
    }

    private Shape drawRhombus(int actualX, int actualY, int widthObj, int heightObj) {
        MoveTo point1 = new MoveTo(actualX, actualY);
        LineTo line1 = new LineTo(actualX + widthObj / 2, actualY + heightObj / 2);
        LineTo line2 = new LineTo(actualX, actualY + heightObj);
        LineTo line3 = new LineTo(actualX - widthObj / 2, actualY + heightObj / 2);
        LineTo line4 = new LineTo(actualX, actualY);
        return new Path(point1, line1, line2, line3, line4);
    }

    public List<List<Integer>> getChildrenTrue(int node) {
        List<List<Integer>> splitPoints = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        temp.add(node);
        for (int i = node + 1; i < this.flowchart.size(); i++) {
            if (flowchart.get(i).getElementType().contains("Decision") && flowchart.get(i).getBranch() == 1) {
                splitPoints = new ArrayList<>(getChildrenTrue(i));
                splitPoints.add(temp);
                return splitPoints;
            }
        }
        splitPoints.add(temp);
        return splitPoints;
    }

    public List<List<Integer>> getChildrenFalse(int node) {
        List<List<Integer>> splitPoints = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        temp.add(node);
        for (int i = node + 1; i < this.flowchart.size(); i++) {
            if (flowchart.get(i).getElementType().contains("Decision") && flowchart.get(i).getBranch() == 0) {
                splitPoints.add(temp);
                splitPoints.addAll(getChildrenFalse(i));
                return splitPoints;
            }
        }
        splitPoints.add(temp);
        return splitPoints;
    }

    public void computeSplits2() {
        List<List<Integer>> splitPoints = new ArrayList<>();
        //System.out.println();
        //System.out.println("Inicio computesplits2");
        for (int i = 0; i < this.flowchart.size(); i++) {
            if (flowchart.get(i).getElementType().contains("Decision")) {
                splitPoints.addAll(getChildrenTrue(i));
                //System.out.println("Splitpoints: "+splitPoints);
                splitPoints = new ArrayList<>(removeElemListofList(splitPoints, i));
                splitPoints.addAll(getChildrenFalse(i));
                break;
            }
        }
        //System.out.println("Splitpoints: "+splitPoints);
        //System.out.println("Fin computesplits2");
        //System.out.println();
        this.splitPoints = splitPoints;
    }

    public List<Integer> getXLayout(int w, int objw, int XSpaceObj) {
        //int XSpaceObj = 25;
        this.computeSplits2();
        List<Integer> results = new ArrayList<>();
        int spc = XSpaceObj + objw;
        int border = (int) (w - (spc * (this.splitPoints.size() + 1))) / 2;

        for (int i = 0; i < this.splitPoints.size(); i++) {
            results.add(border + ((i + 1) * spc));
        }
        //System.out.println("Minimum splitPoints: "+Collections.min(this.splitPoints));
        return results;
    }

    public int maxIndexListofList(List<List<Integer>> lista) {
        //System.out.println("Max lista: "+lista);
        int max = 0;
        int index = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (max < Collections.max(lista.get(i))) {
                max = Collections.max(lista.get(i));
                index = i;
            }
        }
        return index;
    }

    public int maxListofList(List<List<Integer>> lista) {
        //System.out.println("Max lista: "+lista);
        int max = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (max < Collections.max(lista.get(i))) {
                max = Collections.max(lista.get(i));
            }
        }
        return max;
    }

    public int minIndexListofList(List<List<Integer>> lista) {
        int min = 100000000;
        int index = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (min > Collections.min(lista.get(i))) {
                min = Collections.min(lista.get(i));
                index = i;
            }
        }
        return index;
    }

    public int getIndexListofList(List<List<Integer>> lista, int elem) {
        int index = 0;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).size(); j++) {
                if (elem == lista.get(i).get(j)) {
                    index = i;
                    break;
                }
            }

        }
        return index;
    }

    public List<List<Integer>> removeElemListofList(List<List<Integer>> lista, int elem) {
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).size(); j++) {
                if (elem == lista.get(i).get(j)) {
                    lista.get(i).remove(j);
                    if (lista.get(i).size() == 0) {
                        lista.remove(i);
                    }
                    break;
                }
            }

        }
        return lista;
    }

    public List<List<Integer>> removeMaxElemListofList(List<List<Integer>> lista, int elem) {
        List<List<Integer>> ans = new ArrayList<>(lista);
        List<Integer> temp = new ArrayList<>(ans.get(elem));
        temp.remove(ans.get(elem).indexOf(Collections.max(ans.get(elem))));
        //System.out.println("temp size:"+temp.size());
        if (temp.size() > 0) {
            ans.set(elem, temp);
        } else {
            ans.remove(elem);
        }
        //System.out.println("removed ans: "+ans);
        return ans;
    }

    public int[] getConditionalLayout(int node, List<Integer> xlay, int actualXlay, int spc) {
        //System.out.println("ConditionalLayout init");
        int ans[] = new int[4];
        int cntMerge = 0;
        int maxIndex = 0;
        List<List<Integer>> sPoints = new ArrayList<>(this.splitPoints);
        if (xlay.size() > 0) {
            ans[0] = xlay.get(actualXlay) - (spc / 2);
            ans[1] = xlay.get(actualXlay) + (spc / 2);
            ans[2] = 0; //Num Merges on Left
            ans[3] = 0; //Num Merges on Right
            //System.out.println("sPoints: "+sPoints);
            maxIndex = maxIndexListofList(sPoints);
            sPoints = new ArrayList<>(removeMaxElemListofList(sPoints, maxIndex));
        }
        for (int i = 0; i < node; i++) {

            if (this.flowchart.get(i).getElementType().contains("Decision") && cntMerge == 1) {
                //System.out.println("Inside decision revert");
                ans[0] = xlay.get(actualXlay) - (spc / 2);
                ans[1] = xlay.get(actualXlay) + (spc / 2);
                ans[2] = 0; //Num Merges on Left
                ans[3] = 0; //Num Merges on Right
                cntMerge--;
            }

            if (this.flowchart.get(i).getElementType().equals("Merge")) {
                if (maxIndexListofList(sPoints) >= maxIndex /*||
                  ((maxIndexListofList(sPoints) == maxIndex) &&
                   (getIndexListofList(this.splitPoints,maxListofList(sPoints)) !=
                    getIndexListofList(this.splitPoints,maxIndex)))*/) {  //Merge on Right
                    ans[0] = ans[0] + (spc / 2);
                    if ((getIndexListofList(this.splitPoints, maxListofList(sPoints)) ==
                            getIndexListofList(this.splitPoints, maxIndex))) {
                        ans[1] = xlay.get(getIndexListofList(this.splitPoints, maxListofList(sPoints)) + 1) + (spc / 2);
                    } else {
                        ans[1] = xlay.get(getIndexListofList(this.splitPoints, maxListofList(sPoints))) + (spc / 2);
                    }
                    ans[3]++;
                } /*else if((maxIndexListofList(sPoints) == maxIndex) &&
                        (getIndexListofList(this.splitPoints,maxListofList(sPoints)) ==
                         getIndexListofList(this.splitPoints,maxIndex))){
                    ans[0] = xlay.get(actualXlay) - (spc/2);
                    ans[1] = xlay.get(actualXlay) + (spc/2);
                    ans[2] = 0; //Num Merges on Left
                    ans[3] = 0; //Num Merges on Right
                } */ else { //Merge on Left
                    ans[0] = xlay.get(getIndexListofList(this.splitPoints, maxListofList(sPoints))) - (spc / 2);
                    ans[1] = ans[1] - (spc / 2);
                    ans[2]++;
                }
                maxIndex = maxIndexListofList(sPoints);
                sPoints = new ArrayList<>(removeMaxElemListofList(sPoints, maxIndex));
                cntMerge++;
            }
        }
        /*if(xlay.size() > 0){
        //            ans[0] = xlay.get(actualXlay) - (spc/2);
        //            ans[1] = xlay.get(actualXlay) + (spc/2);
        //            ans[2] = 0; //Num Merges on Left
        //            ans[3] = 0; //Num Merges on Right
        //            //System.out.println("sPoints: "+sPoints);
        //            maxIndex = maxIndexListofList(sPoints);
        //            sPoints = new ArrayList<>(removeElemListofList(sPoints,maxIndex));
        //            //sPoints.remove(maxIndex);
        //            for(int j = 0;j < cntMerge;j++){
        //                if(maxIndexListofList(sPoints) > maxIndex){  //Merge on Right
        //                    ans[0] = ans[0] +(spc/2);
        //                    ans[1] = xlay.get(getIndexListofList(this.splitPoints,maxListofList(sPoints))) + (spc/2);
        //                    ans[3]++;
        //                } else if(maxIndexListofList(sPoints) == maxIndex){
        //                    ans[0] = xlay.get(actualXlay) - (spc/2);
        //                    ans[1] = xlay.get(actualXlay) + (spc/2);
        //                    ans[2] = 0; //Num Merges on Left
        //                    ans[3] = 0; //Num Merges on Right
        //                } else { //Merge on Left
        //                    ans[0] = xlay.get(getIndexListofList(this.splitPoints,maxListofList(sPoints))) - (spc/2);
        //                    ans[1] = ans[1] -(spc/2);
        //                    ans[2]++;
        //                }
        //                maxIndex = maxIndexListofList(sPoints);
        //                sPoints = new ArrayList<>(removeElemListofList(sPoints,maxIndex));
        //            }
        //        }
        //        //System.out.println("ConditionalLayout end");*/
        return ans;
    }

    public List<Object> getGraphic2(int w, int initH) {
        computeSplits2();
        int actualY = initH, actualX, widthObj = 150, heightObj = 50, YSpaceObj = 25, XSpaceObj = 25;
        int fontSize = 20, actualXlay = -1, initXlay = -1, spc = XSpaceObj + widthObj;
        List<Integer> parentDecision = new ArrayList<>();
        int actualYmax = 0;
        int LoopLineSpc = XSpaceObj / 2;
        parentDecision.add(-1); //Intial branch
        boolean flagMerge = false, openTrueBlock = false, openEndLoop = false;
        List<Object> elements = new ArrayList<Object>();
        List<Integer> xlay = getXLayout(w, widthObj, XSpaceObj), loopReturn = new ArrayList<>();
        int ylay[] = new int[xlay.size() + 2]; //n+1 spaces for n splits, plus initalYlayout in last position
        if (xlay.size() == 0) {
            actualX = w / 2;
        } else {
            initXlay = minIndexListofList(this.splitPoints);
            actualX = xlay.get(initXlay);
        }
        widthObj = 120;
        ylay[xlay.size() + 1] = YSpaceObj;
        actualY += ylay[xlay.size() + 1];
        Ellipse start = new Ellipse(actualX, actualY + heightObj / 2, widthObj / 2, heightObj / 2);
        start.setStroke(Color.BLACK);
        start.setFill(Color.LIGHTGREEN);
        start.setBlendMode(BlendMode.SRC_ATOP);
        Text startText = new Text("Inicio");
        startText.setFont(Font.font("Verdana", fontSize));
        startText.setX((actualX - (startText.getLayoutBounds().getWidth() / 2)));
        startText.setBlendMode(BlendMode.SRC_ATOP);
        startText.setY(actualY + heightObj / 2 + startText.getLayoutBounds().getHeight() / 4);
        elements.add(start);
        elements.add(startText);

        //ylay[actualXlay] = actualY + heightObj + YSpaceObj;
        int finalActualY = actualY;
        ylay = Arrays.stream(ylay).map(i -> finalActualY + heightObj + YSpaceObj).toArray();
        //System.out.println("xlay: "+ xlay.toString()+",ylay: "+Arrays.toString(ylay));
        for (int i = 1; i < this.getLength(); i++) {
            //elements.add(drawDownLine(actualX,actualY,spaceObj));
            //System.out.print("xlay:"+ xlay.toString()+",ylay:"+Arrays.toString(ylay));
            FlowElement elem = this.flowchart.get(i);
            //System.out.print(",branch: "+elem.getBranch()+",block: "+elem.getBlock());
            //System.out.print(", parDec:"+parentDecision);
            if (elem.getElementType().contains("Process")) {
                int clay[] = {0, 0, 0, 0};
                //System.out.print(", type: Process");
                if (elem.getBranch() != -1) {
                    clay = getConditionalLayout(i, xlay, actualXlay, spc);
                    //System.out.print(", clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                    if (elem.getBranch() == 1) {
                        //actualX = xlay.get(actualXlay) - (XSpaceObj + widthObj)/2;
                        actualX = clay[0];
                        actualY = ylay[actualXlay - clay[2]];
                    } else {
                        //actualX = xlay.get(actualXlay) + (XSpaceObj + widthObj)/2;
                        actualX = clay[1];
                        actualY = ylay[actualXlay + 1 + clay[3]];
                    }
                } else {
                    if (xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                    flagMerge = false;
                    elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                } else {
                    if (openTrueBlock && (elem.getBlock() == 0 || openEndLoop)) {
                        openTrueBlock = false;
                        openEndLoop = false;
                        elements.add(drawLoopLines(actualX, actualY - YSpaceObj, LoopLineSpc, widthObj, heightObj, loopReturn.get(loopReturn.size() - 1), YSpaceObj));
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                    } else {
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj));
                    }

                }
                fontSize = 15;
                widthObj = 150;
                Rectangle process = new Rectangle(actualX - widthObj / 2, actualY, widthObj, heightObj);
                process.setStroke(Color.BLACK);
                if (elem.getElementType().contains("Key")) {
                    process.setFill(Color.LIGHTPINK);
                } else {
                    process.setFill(Color.SKYBLUE);
                }
                process.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(process);
                List<Object> processText = fitText(actualX, actualY, widthObj, heightObj, elem.getTextStr(), fontSize);
                for (int line = 0; line < processText.size(); line++) {
                    elements.add(processText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;
                if (elem.getBranch() != -1) {
                    if (elem.getBranch() == 1) {
                        ylay[actualXlay - clay[2]] = actualY + heightObj + YSpaceObj;
                    } else {
                        ylay[actualXlay + 1 + clay[3]] = actualY + heightObj + YSpaceObj;
                    }
                } else {
                    ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                }

            } else if (elem.getElementType().equals("Loop")) {
                int clay[] = {0, 0, 0, 0};
                openTrueBlock = true;
                //System.out.print(", type: Loop");
                if (elem.getBranch() != -1) {
                    clay = getConditionalLayout(i, xlay, actualXlay, spc);
                    //System.out.print(", clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                    if (elem.getBranch() == 1) {
                        //actualX = xlay.get(actualXlay) - (XSpaceObj + widthObj)/2;
                        actualX = clay[0];
                        actualY = ylay[actualXlay - clay[2]];
                    } else {
                        //actualX = xlay.get(actualXlay) + (XSpaceObj + widthObj)/2;
                        actualX = clay[1];
                        actualY = ylay[actualXlay + 1 + clay[3]];
                    }
                } else {
                    if (xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                    flagMerge = false;
                    elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                } else {
                    if (openTrueBlock && (elem.getBlock() == 0 || openEndLoop)) {
                        openEndLoop = false;
                        elements.add(drawLoopLines(actualX, actualY - YSpaceObj, LoopLineSpc, widthObj, heightObj, loopReturn.get(loopReturn.size() - 1), YSpaceObj));
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                    } else {
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj));
                    }
                }
                LoopLineSpc -= 3;
                loopReturn.add(actualY);
                fontSize = 15;
                widthObj = 150;
                // Shape io = drawParallelogram(actualX,actualY,widthObj-10,heightObj,5);
                Shape loop = drawRhombus(actualX, actualY, widthObj, heightObj);
                loop.setStroke(Color.BLACK);
                loop.setFill(Color.ORANGE);
                loop.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(loop);

                List<Object> loopText = fitText(actualX, actualY + heightObj / 10, 4 * widthObj / 5, 4 * heightObj / 5, elem.getTextStr(), fontSize);
                for (int line = 0; line < loopText.size(); line++) {
                    elements.add(loopText.get(line));
                }

                int initDestXY1[] = {actualX - (widthObj / 2), actualY + heightObj / 2, actualX - (widthObj / 2), actualY + heightObj / 2};
                int initDestXY2[] = {actualX + (widthObj / 2), actualY + (heightObj / 2), actualX + (widthObj / 2) + (LoopLineSpc), actualY + (heightObj)};
                elements.add(drawBranchesLine(initDestXY1, initDestXY2));
                initDestXY1 = new int[]{actualX - (widthObj / 4), actualY + (heightObj / 2) + 20, actualX - (widthObj / 4), actualY + (heightObj / 2) + 20};
                initDestXY2 = new int[]{actualX + (widthObj / 4), actualY + (heightObj / 2) + 20, actualX + (widthObj / 4), actualY + (heightObj / 2) + 20};
                List<Text> branchesText = drawBranchesText(initDestXY1, initDestXY2);
                for (int line = 0; line < branchesText.size(); line++) {
                    elements.add(branchesText.get(line));
                }


                //actualY = actualY + heightObj + YSpaceObj;
                //ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                if (elem.getBranch() != -1) {
                    if (elem.getBranch() == 1) {
                        ylay[actualXlay - clay[2]] = actualY + heightObj + YSpaceObj;
                    } else {
                        ylay[actualXlay + 1 + clay[3]] = actualY + heightObj + YSpaceObj;
                    }
                } else {
                    ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                }
            } else if (elem.getElementType().equals("EndLoop")) {
                //System.out.print(", type: EndLoop");
                openEndLoop = true;
                if (!openTrueBlock) {
                    loopReturn.remove(loopReturn.size() - 1);
                    LoopLineSpc += 3;
                }
                //if(getOpenLoops() > 0){
                openTrueBlock = true;
                //}
            } else if (elem.getElementType().contains("IO")) {
                int clay[] = {0, 0, 0, 0};
                //System.out.print(", type: IO");
                if (elem.getBranch() != -1) {
                    clay = getConditionalLayout(i, xlay, actualXlay, spc);
                    //System.out.print(", clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                    if (elem.getBranch() == 1) {
                        //actualX = xlay.get(actualXlay) - (XSpaceObj + widthObj)/2;
                        actualX = clay[0];
                        actualY = ylay[actualXlay - clay[2]];
                    } else {
                        //actualX = xlay.get(actualXlay) + (XSpaceObj + widthObj)/2;
                        actualX = clay[1];
                        actualY = ylay[actualXlay + 1 + clay[3]];
                    }
                } else {
                    if (xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                    flagMerge = false;
                    elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                } else {
                    if (openTrueBlock && (elem.getBlock() == 0 || openEndLoop)) {
                        openTrueBlock = false;
                        openEndLoop = false;
                        elements.add(drawLoopLines(actualX, actualY - YSpaceObj, LoopLineSpc, widthObj, heightObj, loopReturn.get(loopReturn.size() - 1), YSpaceObj));
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                    } else {
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj));
                    }
                }
                fontSize = 15;
                widthObj = 150;
                Shape io = drawParallelogram(actualX, actualY, widthObj - 10, heightObj, 5);
                io.setStroke(Color.BLACK);
                if (elem.getElementType().contains("Key")) {
                    io.setFill(Color.GREENYELLOW);
                } else{
                    io.setFill(Color.LIGHTYELLOW);
                }

                io.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(io);
                List<Object> ioText = fitText(actualX, actualY, widthObj - 10, heightObj, elem.getTextStr(), fontSize);
                for (int line = 0; line < ioText.size(); line++) {
                    elements.add(ioText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;
                //ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                if (elem.getBranch() != -1) {
                    if (elem.getBranch() == 1) {
                        ylay[actualXlay - clay[2]] = actualY + heightObj + YSpaceObj;
                    } else {
                        ylay[actualXlay + 1 + clay[3]] = actualY + heightObj + YSpaceObj;
                    }
                } else {
                    ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                }
            } else if (elem.getElementType().contains("Decision")) {
                //System.out.print(", type: Decision");
                if (elem.getBranch() != -1) {
                    parentDecision.add(elem.getBranch());
                    int clay[] = getConditionalLayout(i, xlay, actualXlay, spc);
                    //System.out.print(", clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                    if (elem.getBranch() == 1) {
                        actualX = clay[0];
                        actualY = ylay[actualXlay - clay[2]];
                    } else {
                        actualX = clay[1];
                        actualY = ylay[actualXlay + 1 + clay[3]];
                    }

                } else {

                    if (xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }
                //actualXlay = this.splitPoints.indexOf(i);
                actualXlay = getIndexListofList(this.splitPoints, i);
                if (elem.getBranch() != -1) {

                    if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                        flagMerge = false;
                        elements.add(drawDownLine(xlay.get(actualXlay), actualY, YSpaceObj / 2));
                    } else {
                        elements.add(drawDecisionLine(actualX, actualY, YSpaceObj, xlay.get(actualXlay)));
                    }
                } else {
                    if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                        flagMerge = false;
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                    } else {
                        if (openTrueBlock && (elem.getBlock() == 0 || openEndLoop)) {
                            openTrueBlock = false;
                            openEndLoop = false;
                            elements.add(drawLoopLines(actualX, actualY - YSpaceObj, LoopLineSpc, widthObj, heightObj, loopReturn.get(loopReturn.size() - 1), YSpaceObj));
                            elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                        } else {
                            elements.add(drawDownLine(actualX, actualY, YSpaceObj));
                        }
                    }
                }
                actualX = xlay.get(actualXlay);
                fontSize = 15;
                widthObj = 150;
                Shape decision = drawRhombus(actualX, actualY, widthObj, heightObj);
                decision.setStroke(Color.BLACK);
                if (elem.getElementType().contains("Key")) {
                    decision.setFill(Color.LIGHTCYAN);
                } else {
                    decision.setFill(Color.LAVENDER);
                }
                decision.setBlendMode(BlendMode.SRC_ATOP);
                elements.add(decision);
                List<Object> decisionText = fitText(actualX, actualY + heightObj / 10, 4 * widthObj / 5, 4 * heightObj / 5, elem.getTextStr(), fontSize);
                for (int line = 0; line < decisionText.size(); line++) {
                    elements.add(decisionText.get(line));
                }
                //actualY = actualY + heightObj + YSpaceObj;

                int initDestXY1[] = {actualX - (widthObj / 2), actualY + (heightObj / 2), actualX - (widthObj / 2) - (XSpaceObj / 2), actualY + (heightObj)};
                int initDestXY2[] = {actualX + (widthObj / 2), actualY + (heightObj / 2), actualX + (widthObj / 2) + (XSpaceObj / 2), actualY + (heightObj)};
                elements.add(drawBranchesLine(initDestXY1, initDestXY2));
                List<Text> branchesText = drawBranchesText(initDestXY1, initDestXY2);
                for (int line = 0; line < branchesText.size(); line++) {
                    elements.add(branchesText.get(line));
                }
                ylay[actualXlay] = actualY + heightObj + YSpaceObj;
                ylay[actualXlay + 1] = actualY + heightObj + YSpaceObj;
            } else if (elem.getElementType().equals("End")) {
                int clay[] = {0, 0, 0, 0};
                //System.out.print(", type: End");
                if (elem.getBranch() != -1) {
                    clay = getConditionalLayout(i, xlay, actualXlay, spc);
                    //System.out.print(", clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                    if (elem.getBranch() == 1) {
                        actualX = clay[0];
                        actualY = ylay[actualXlay - clay[2]];
                    } else {
                        actualX = clay[1];
                        actualY = ylay[actualXlay + 1 + clay[3]];
                    }

                } else {
                    if (xlay.size() > 0) {
                        actualX = xlay.get(initXlay);
                    }
                    actualY = ylay[xlay.size() + 1];
                }

                if (flagMerge && (parentDecision.get(parentDecision.size() - 1) == elem.getBranch())) {
                    flagMerge = false;
                    elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                } else {
                    if (openTrueBlock && (elem.getBlock() == 0 || openEndLoop)) {
                        openTrueBlock = false;
                        openEndLoop = false;
                        elements.add(drawLoopLines(actualX, actualY - YSpaceObj, LoopLineSpc, widthObj, heightObj, loopReturn.get(loopReturn.size() - 1), YSpaceObj));
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj / 2));
                    } else {
                        elements.add(drawDownLine(actualX, actualY, YSpaceObj));
                    }
                }

                fontSize = 20;
                widthObj = 100;
                Ellipse end = new Ellipse(actualX, actualY + heightObj / 2, widthObj / 2, heightObj / 2);
                end.setStroke(Color.BLACK);
                end.setFill(Color.SALMON);
                end.setBlendMode(BlendMode.SRC_ATOP);
                Text endText = new Text("Final");
                endText.setFont(Font.font("Verdana", fontSize));
                endText.setBlendMode(BlendMode.SRC_ATOP);
                endText.setX((actualX - (endText.getLayoutBounds().getWidth() / 2)));
                endText.setY(actualY + heightObj / 2 + endText.getLayoutBounds().getHeight() / 4);
                elements.add(end);
                elements.add(endText);
                if (elem.getBranch() != -1) {
                    if (elem.getBranch() == 1) {
                        ylay[actualXlay - clay[2]] = actualY + heightObj + YSpaceObj;
                    } else {
                        ylay[actualXlay + 1 + clay[3]] = actualY + heightObj + YSpaceObj;
                    }
                } else {
                    ylay[xlay.size() + 1] = actualY + heightObj + YSpaceObj;
                }
            } else if (elem.getElementType().equals("Merge")) {
                flagMerge = true;

                int clay[] = getConditionalLayout(i, xlay, actualXlay, spc);
                int actualY1 = ylay[actualXlay - clay[2]], actualY2 = ylay[actualXlay + 1 + clay[3]];

                if (parentDecision.size() > 1 && ((clay[2] + clay[3] != 0))) {
                    parentDecision.remove(parentDecision.size() - 1);
                }

                //System.out.print(", type: Merge, clay: "+clay[0]+", "+clay[1]+", "+clay[2]+", "+clay[3]);
                if (elem.getBranch() != -1) {
                    if (elem.getBranch() == 1) {
                        //actualY1 = ylay[actualXlay - clay[2]];
                        actualX = clay[0];
                    } else {
                        //actualY2 = ylay[actualXlay+1 + clay[3]];
                        actualX = clay[1];
                    }
                } else {
                    //if(xlay.size() > 0) {
                    //actualX = xlay.get(initXlay);
                    //} else {
                    actualX = w / 2;
                    //}
                    int maxYlay = 0;
                    for (int e = 0; e < ylay.length; e++) {
                        if (ylay[e] > maxYlay) {
                            maxYlay = ylay[e];
                        }
                    }
                    ylay[xlay.size() + 1] = maxYlay;
                    actualY = ylay[xlay.size() + 1];

                }
                int ymax = actualY1 > actualY2 ? actualY1 : actualY2;
                //System.out.print(", ymax: "+ymax);
                //if(this.flowchart.get(i-1).getElementType().equals("Merge")){
                if (actualYmax == ymax) {
                    if (ymax == actualY1) {
                        elements.add(drawMergeLine(clay[0], actualY1 - YSpaceObj / 2, clay[1], actualY2 - YSpaceObj, 0, actualX));
                    } else {
                        elements.add(drawMergeLine(clay[0], actualY1 - YSpaceObj, clay[1], actualY2 - YSpaceObj / 2, 0, actualX));
                    }
                } else {
                    elements.add(drawMergeLine(clay[0], actualY1 - YSpaceObj, clay[1], actualY2 - YSpaceObj, YSpaceObj, actualX));
                    actualYmax = ymax;
                }
                ylay[actualXlay - clay[2]] = ymax;
                ylay[actualXlay + 1 + clay[3]] = ymax;
            } else {
                //System.out.println("Warning: Invalid Element Type ");
            }
            //System.out.println();
        }
        //this.printFlowchartTrace();
        //System.out.println("------------------------------------------------");
        return elements;
    }
}