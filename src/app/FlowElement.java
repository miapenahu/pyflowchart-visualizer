package app;

/* Basic Element Types:
- Start
- End
- IO (Input/Output)
- Process
- Decision
 */

public class FlowElement {
    private String ElementType = new String();
    private String TextStr = new String();
    private int codeLine = 0;

    public FlowElement(){}

    public FlowElement(String etype){
        this.ElementType = etype;
    }

    public FlowElement(String etype, String text){
        this.ElementType = etype;
        this.TextStr = text;
    }

    public FlowElement(String etype, String text, int line){
        this.ElementType = etype;
        this.TextStr = text;
        this.codeLine = line;
    }

    public String getElementType() {
        return ElementType;
    }

    public void setElementType(String elementType) {
        ElementType = elementType;
    }

    public String getTextStr() {
        return TextStr;
    }

    public void setTextStr(String text) {
        TextStr = text;
    }

    public int getCodeLine() {
        return codeLine;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }
}
