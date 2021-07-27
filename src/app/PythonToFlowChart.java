package app;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class PythonToFlowChart extends PythonParserBaseListener {

    private List<FlowChart> FCList;
    private List<String> FCnames;
    private FlowChart drawerChart = new FlowChart();
    private FlowChart functionChart;

    public List<String> getFCnames() {
        return FCnames;
    }

    public void setFCnames(List<String> FCnames) {
        this.FCnames = FCnames;
    }

    boolean insideFunction = false;

    public List<FlowChart> getFCList() {
        return FCList;
    }

    public void setFCList(List<FlowChart> FCList) {
        this.FCList = FCList;
    }

    public FlowChart getDrawerChart() {
        return drawerChart;
    }

    public void setDrawerChart(FlowChart drawerChart) {
        this.drawerChart = drawerChart;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterRoot(PythonParser.RootContext ctx) {
        System.out.println("0 INICIO programa");
        FCList = new ArrayList<>();
        FCnames = new ArrayList<>();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitRoot(PythonParser.RootContext ctx) {
        System.out.println("1 FIN programa");
        drawerChart.addFlowElement("End","");
        FCList.add(drawerChart);
        FCnames.add("Principal");
        //drawerChart.printFlowchartTrace();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSingle_input(PythonParser.Single_inputContext ctx) {
//        System.out.println("2");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSingle_input(PythonParser.Single_inputContext ctx) {
//        System.out.println("3");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFile_input(PythonParser.File_inputContext ctx) {
//        System.out.println("4");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFile_input(PythonParser.File_inputContext ctx) {
//        System.out.println("5");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEval_input(PythonParser.Eval_inputContext ctx) {
        System.out.println("6");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEval_input(PythonParser.Eval_inputContext ctx) {
        System.out.println("7");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterStmt(PythonParser.StmtContext ctx) {
        System.out.println("8 Entrada Declaracion");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitStmt(PythonParser.StmtContext ctx) {
        System.out.println("9 Salida Declaracion");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIf_stmt(PythonParser.If_stmtContext ctx) {
        System.out.println("10 Entrada al IF: "+ ctx.getText());
        String[] parts = ctx.getText().split(":");
        String ans = parts[0].substring(2,parts[0].length());
        if(!insideFunction) {
            drawerChart.addFlowElement("Decision", "Si " + ans);
        } else {
            functionChart.addFlowElement("Decision", "Si " + ans);
        }
        //flowC.setWritingBranch(1); //True
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIf_stmt(PythonParser.If_stmtContext ctx) {
        System.out.println("11 Salida del IF");
        if(!insideFunction) {
            drawerChart.addFlowElement("Merge", "");
        } else{
            functionChart.addFlowElement("Merge", "");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWhile_stmt(PythonParser.While_stmtContext ctx) {
        System.out.println("12");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWhile_stmt(PythonParser.While_stmtContext ctx) {
        System.out.println("13");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFor_stmt(PythonParser.For_stmtContext ctx) {
        System.out.println("14 Entrada for loop");
        String str = "Para " + ctx.exprlist().getText() + " en " + ctx.testlist().getText();
        if(!insideFunction) {
            drawerChart.addFlowElement("Loop",str);
        } else{
            functionChart.addFlowElement("Loop",str);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFor_stmt(PythonParser.For_stmtContext ctx) {
        System.out.println("15 Salida for loop");
        if(!insideFunction) {
            drawerChart.addFlowElement("EndLoop","");
        } else{
            functionChart.addFlowElement("EndLoop","");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTry_stmt(PythonParser.Try_stmtContext ctx) {
        System.out.println("16");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTry_stmt(PythonParser.Try_stmtContext ctx) {
        System.out.println("17");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWith_stmt(PythonParser.With_stmtContext ctx) {
        System.out.println("18");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWith_stmt(PythonParser.With_stmtContext ctx) {
        System.out.println("19");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClass_or_func_def_stmt(PythonParser.Class_or_func_def_stmtContext ctx) {
        System.out.println("20 Entrada Declaracion Funcion o clase: "+ctx.getText());
        functionChart = new FlowChart();
        System.out.println("ctx name function: "+ ctx.funcdef().name().getText());

        //functionChart.addFlowElement("Process","Entrar en función \""+ctx.funcdef().name().getText()+"\"");
        drawerChart.addFlowElement("Process","Definición función \""+ctx.funcdef().name().getText()+"\"");
        insideFunction = true;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClass_or_func_def_stmt(PythonParser.Class_or_func_def_stmtContext ctx) {
        System.out.println("21 Salida Declaracion Funcion o clase");
        functionChart.addFlowElement("End","");
        FCList.add(functionChart);
        FCnames.add("Función "+ ctx.funcdef().name().getText());
        insideFunction = false;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSuite(PythonParser.SuiteContext ctx) {
        System.out.println("22");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSuite(PythonParser.SuiteContext ctx) {
        System.out.println("23");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDecorator(PythonParser.DecoratorContext ctx) {
        System.out.println("24");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDecorator(PythonParser.DecoratorContext ctx) {
        System.out.println("25");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElif_clause(PythonParser.Elif_clauseContext ctx) {
        System.out.println("26 Entrada ELIF");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElif_clause(PythonParser.Elif_clauseContext ctx) {
        System.out.println("27 Salida ELIF");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElse_clause(PythonParser.Else_clauseContext ctx) {
        System.out.println("28 Entrada ELSE");
        drawerChart.setWritingBranch(0); //False
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElse_clause(PythonParser.Else_clauseContext ctx) {
        System.out.println("29 Salida ELSE");
        drawerChart.setWritingBranch(1); //True
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFinally_clause(PythonParser.Finally_clauseContext ctx) {
        System.out.println("30");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFinally_clause(PythonParser.Finally_clauseContext ctx) {
        System.out.println("31");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWith_item(PythonParser.With_itemContext ctx) {
        System.out.println("32");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWith_item(PythonParser.With_itemContext ctx) {
        System.out.println("33");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExcept_clause(PythonParser.Except_clauseContext ctx) {
        System.out.println("34");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExcept_clause(PythonParser.Except_clauseContext ctx) {
        System.out.println("35");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClassdef(PythonParser.ClassdefContext ctx) {
        System.out.println("36");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClassdef(PythonParser.ClassdefContext ctx) {
        System.out.println("37");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFuncdef(PythonParser.FuncdefContext ctx) {
        System.out.println("38 Entrada definición funcion: "+ctx.name().getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFuncdef(PythonParser.FuncdefContext ctx) {
        System.out.println("39 Salida definición funcion");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTypedargslist(PythonParser.TypedargslistContext ctx) {
        System.out.println("40");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTypedargslist(PythonParser.TypedargslistContext ctx) {
        System.out.println("41");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgs(PythonParser.ArgsContext ctx) {
        System.out.println("42");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgs(PythonParser.ArgsContext ctx) {
        System.out.println("43");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterKwargs(PythonParser.KwargsContext ctx) {
        System.out.println("44");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitKwargs(PythonParser.KwargsContext ctx) {
        System.out.println("45");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDef_parameters(PythonParser.Def_parametersContext ctx) {
        System.out.println("46");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDef_parameters(PythonParser.Def_parametersContext ctx) {
        System.out.println("47");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDef_parameter(PythonParser.Def_parameterContext ctx) {
        System.out.println("48");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDef_parameter(PythonParser.Def_parameterContext ctx) {
        System.out.println("49");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNamed_parameter(PythonParser.Named_parameterContext ctx) {
        System.out.println("50");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNamed_parameter(PythonParser.Named_parameterContext ctx) {
        System.out.println("51");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        System.out.println("52 Entrada declaración simple");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        System.out.println("53 Salida declaración simple");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        System.out.println("54 Entrada declaración expresión: " + ctx.getText());
        if(ctx.getText().contains("print")){
            if(!insideFunction) {
                drawerChart.addFlowElement("IO", ctx.getText());
            } else {
                functionChart.addFlowElement("IO", ctx.getText());
            }
        } else if(ctx.getText().contains("=")){
            if(!insideFunction) {
                drawerChart.addFlowElement("Process", ctx.getText());
            } else{
                functionChart.addFlowElement("Process", ctx.getText());
            }
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        System.out.println("55 Salida declaración expresión ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPrint_stmt(PythonParser.Print_stmtContext ctx) {
        System.out.println("56");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPrint_stmt(PythonParser.Print_stmtContext ctx) {
        System.out.println("57");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDel_stmt(PythonParser.Del_stmtContext ctx) {
        System.out.println("58");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDel_stmt(PythonParser.Del_stmtContext ctx) {
        System.out.println("59");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPass_stmt(PythonParser.Pass_stmtContext ctx) {
        System.out.println("60");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPass_stmt(PythonParser.Pass_stmtContext ctx) {
        System.out.println("61");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBreak_stmt(PythonParser.Break_stmtContext ctx) {
        System.out.println("62");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBreak_stmt(PythonParser.Break_stmtContext ctx) {
        System.out.println("63");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterContinue_stmt(PythonParser.Continue_stmtContext ctx) {
        System.out.println("64");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitContinue_stmt(PythonParser.Continue_stmtContext ctx) {
        System.out.println("65");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterReturn_stmt(PythonParser.Return_stmtContext ctx) {
        System.out.println("66 Entrada return");
        if(!insideFunction){
            drawerChart.addFlowElement("KeyProcess","Retornar "+ctx.testlist().getText());
        } else {
            functionChart.addFlowElement("KeyProcess","Retornar "+ctx.testlist().getText());
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitReturn_stmt(PythonParser.Return_stmtContext ctx) {
        System.out.println("67 Salida return");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterRaise_stmt(PythonParser.Raise_stmtContext ctx) {
        System.out.println("68");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitRaise_stmt(PythonParser.Raise_stmtContext ctx) {
        System.out.println("69");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterYield_stmt(PythonParser.Yield_stmtContext ctx) {
        System.out.println("70");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitYield_stmt(PythonParser.Yield_stmtContext ctx) {
        System.out.println("71");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImport_stmt(PythonParser.Import_stmtContext ctx) {
        System.out.println("72");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImport_stmt(PythonParser.Import_stmtContext ctx) {
        System.out.println("73");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFrom_stmt(PythonParser.From_stmtContext ctx) {
        System.out.println("74");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFrom_stmt(PythonParser.From_stmtContext ctx) {
        System.out.println("75");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterGlobal_stmt(PythonParser.Global_stmtContext ctx) {
        System.out.println("76");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitGlobal_stmt(PythonParser.Global_stmtContext ctx) {
        System.out.println("77");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExec_stmt(PythonParser.Exec_stmtContext ctx) {
        System.out.println("78");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExec_stmt(PythonParser.Exec_stmtContext ctx) {
        System.out.println("79");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssert_stmt(PythonParser.Assert_stmtContext ctx) {
        System.out.println("80");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssert_stmt(PythonParser.Assert_stmtContext ctx) {
        System.out.println("81");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx) {
        System.out.println("82");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx) {
        System.out.println("83");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx) {
        //System.out.println("84");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx) {
        //System.out.println("85");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterStar_expr(PythonParser.Star_exprContext ctx) {
        System.out.println("86");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitStar_expr(PythonParser.Star_exprContext ctx) {
        System.out.println("87");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssign_part(PythonParser.Assign_partContext ctx) {
        System.out.println("88 Entrada Asignación: "+ctx.getStart().getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssign_part(PythonParser.Assign_partContext ctx) {
        System.out.println("89 Salida Asignación: "+ctx.getStart().getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExprlist(PythonParser.ExprlistContext ctx) {
        System.out.println("90");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExprlist(PythonParser.ExprlistContext ctx) {
        System.out.println("91");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImport_as_names(PythonParser.Import_as_namesContext ctx) {
        System.out.println("92");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImport_as_names(PythonParser.Import_as_namesContext ctx) {
        System.out.println("93");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImport_as_name(PythonParser.Import_as_nameContext ctx) {
        System.out.println("94");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImport_as_name(PythonParser.Import_as_nameContext ctx) {
        System.out.println("95");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDotted_as_names(PythonParser.Dotted_as_namesContext ctx) {
        System.out.println("96");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDotted_as_names(PythonParser.Dotted_as_namesContext ctx) {
        System.out.println("97");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDotted_as_name(PythonParser.Dotted_as_nameContext ctx) {
        System.out.println("98");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDotted_as_name(PythonParser.Dotted_as_nameContext ctx) {
        System.out.println("99");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTest(PythonParser.TestContext ctx) {
        //System.out.println("100");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTest(PythonParser.TestContext ctx) {
        //System.out.println("101");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVarargslist(PythonParser.VarargslistContext ctx) {
        System.out.println("102");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVarargslist(PythonParser.VarargslistContext ctx) {
        System.out.println("103");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVardef_parameters(PythonParser.Vardef_parametersContext ctx) {
        System.out.println("104");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVardef_parameters(PythonParser.Vardef_parametersContext ctx) {
        System.out.println("105");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVardef_parameter(PythonParser.Vardef_parameterContext ctx) {
        System.out.println("106");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVardef_parameter(PythonParser.Vardef_parameterContext ctx) {
        System.out.println("107");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVarargs(PythonParser.VarargsContext ctx) {
        System.out.println("108");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVarargs(PythonParser.VarargsContext ctx) {
        System.out.println("109");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVarkwargs(PythonParser.VarkwargsContext ctx) {
        System.out.println("110");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVarkwargs(PythonParser.VarkwargsContext ctx) {
        System.out.println("111");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLogical_test(PythonParser.Logical_testContext ctx) {
        //System.out.println("112 Entrada test logico: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLogical_test(PythonParser.Logical_testContext ctx) {
        //System.out.println("113 Salida test logico: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterComparison(PythonParser.ComparisonContext ctx) {
        //System.out.println("114 Entrada comparación: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitComparison(PythonParser.ComparisonContext ctx) {
        //System.out.println("115 Salida comparación: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExpr(PythonParser.ExprContext ctx) {
        //System.out.println("116 Entrada expresión: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExpr(PythonParser.ExprContext ctx) {
        //System.out.println("117 Salida expresión: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAtom(PythonParser.AtomContext ctx) {
        System.out.println("118 Entrada Atom: "+ctx.getRuleContext().getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAtom(PythonParser.AtomContext ctx) {
        System.out.println("119 Salida Atom: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDictorsetmaker(PythonParser.DictorsetmakerContext ctx) {
        System.out.println("120");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDictorsetmaker(PythonParser.DictorsetmakerContext ctx) {
        System.out.println("121");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTestlist_comp(PythonParser.Testlist_compContext ctx) {
        System.out.println("122");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTestlist_comp(PythonParser.Testlist_compContext ctx) {
        System.out.println("123");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTestlist(PythonParser.TestlistContext ctx) {
        //System.out.println("124");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTestlist(PythonParser.TestlistContext ctx) {
        //System.out.println("125");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDotted_name(PythonParser.Dotted_nameContext ctx) {
        System.out.println("126");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDotted_name(PythonParser.Dotted_nameContext ctx) {
        System.out.println("127");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterName(PythonParser.NameContext ctx) {
        System.out.println("128 Entrada nombre: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitName(PythonParser.NameContext ctx) {
        System.out.println("129 Salida nombre: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNumber(PythonParser.NumberContext ctx) {
        System.out.println("130 Entrada número: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNumber(PythonParser.NumberContext ctx) {
        System.out.println("131 Salida número: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterInteger(PythonParser.IntegerContext ctx) {
        System.out.println("132 Entrada número entero: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitInteger(PythonParser.IntegerContext ctx) {
        System.out.println("133 Salida número entero: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterYield_expr(PythonParser.Yield_exprContext ctx) {
        System.out.println("134");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitYield_expr(PythonParser.Yield_exprContext ctx) {
        System.out.println("135");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterYield_arg(PythonParser.Yield_argContext ctx) {
        System.out.println("136");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitYield_arg(PythonParser.Yield_argContext ctx) {
        System.out.println("137");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTrailer(PythonParser.TrailerContext ctx) {
        //System.out.println("138");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTrailer(PythonParser.TrailerContext ctx) {
        //System.out.println("139");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArguments(PythonParser.ArgumentsContext ctx) {
        //System.out.println("140 Entrada Argumentos: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArguments(PythonParser.ArgumentsContext ctx) {
        //System.out.println("141 Salida Argumentos: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArglist(PythonParser.ArglistContext ctx) {
        //System.out.println("142 Entrada lista args");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArglist(PythonParser.ArglistContext ctx) {
        //System.out.println("143 Salida lista args");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgument(PythonParser.ArgumentContext ctx) {
        //System.out.println("144 Entrada argumento: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgument(PythonParser.ArgumentContext ctx) {
        //System.out.println("145 Salida argumento: "+ctx.getText());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSubscriptlist(PythonParser.SubscriptlistContext ctx) {
        System.out.println("146");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSubscriptlist(PythonParser.SubscriptlistContext ctx) {
        System.out.println("147");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSubscript(PythonParser.SubscriptContext ctx) {
        System.out.println("148");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSubscript(PythonParser.SubscriptContext ctx) {
        System.out.println("149");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSliceop(PythonParser.SliceopContext ctx) {
        System.out.println("150");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSliceop(PythonParser.SliceopContext ctx) {
        System.out.println("151");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterComp_for(PythonParser.Comp_forContext ctx) {
        System.out.println("152");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitComp_for(PythonParser.Comp_forContext ctx) {
        System.out.println("153");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterComp_iter(PythonParser.Comp_iterContext ctx) {
        System.out.println("154");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitComp_iter(PythonParser.Comp_iterContext ctx) {
        System.out.println("155");
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEveryRule(ParserRuleContext ctx) {
//        System.out.println("156");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEveryRule(ParserRuleContext ctx) {
//        System.out.println("157");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void visitTerminal(TerminalNode node) {
//        System.out.println("158");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void visitErrorNode(ErrorNode node) {
//        System.out.println("159");
    }
}
