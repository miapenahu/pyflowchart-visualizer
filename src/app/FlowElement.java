package app;

/* Basic Element Types:
- Start
- End
- IO (Input/Output)
- Process
- Decision
- Loop
- Merge (End od Decision)
- EndLoop (End of Loop)
- LinkTo (In case of loops)
 */

/* Branch (for Decisions) and Block (for Loops)
1 - True
0 - False
 */

public class FlowElement {
    private String ElementType = new String();
    private String TextStr = new String();
    private int codeLine = 0;
    private int branch = -1;
    private int block = -1;

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public FlowElement(){}

    public FlowElement(String etype){
        this.ElementType = etype;
    }

    public FlowElement(String etype, String text){
        this.ElementType = etype;
        this.TextStr = text;
    }

    public FlowElement(String etype, String text, int branch,int block){
        this.ElementType = etype;
        this.TextStr = text;
        this.branch = branch;
        this.block = block;
    }

    public void printFlowElement(){
        System.out.println("etype: "+ this.ElementType);
        System.out.println("textstr: "+ this.TextStr);
        System.out.println("codeline: "+ this.codeLine);
        System.out.println("branch: "+ this.branch);
        System.out.println("block: "+this.block);
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
