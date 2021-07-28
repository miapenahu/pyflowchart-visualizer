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
    private FlowChart classChart;
    boolean insideFunction = false;
    boolean insideClass = false;
    String nameClass = "";
    List<Integer> openBranchesTemp = new ArrayList<>();

    public List<String> getFCnames() {
        return FCnames;
    }

    public void setFCnames(List<String> FCnames) {
        this.FCnames = FCnames;
    }

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

    public void addFlowElemContext(String etype, String str){
        if(insideFunction) {
            functionChart.addFlowElement(etype, str);
        } else if(insideClass){
            classChart.addFlowElement(etype, str);
        } else {
            drawerChart.addFlowElement(etype, str);
        }
    }

    public void setWritingBranchContext(int num){
        if(insideFunction) {
            functionChart.setWritingBranch(num);
        } else if(insideClass){
            classChart.setWritingBranch(num);
        } else {
            drawerChart.setWritingBranch(num);
        }
    }

    public int getOpenBranchesContext(){
        if(insideFunction) {
            return functionChart.getOpenDecisions();
        } else if(insideClass){
            return classChart.getOpenDecisions();
        } else {
            return drawerChart.getOpenDecisions();
        }
    }

    public void addMergeRecursive(){
        while(getOpenBranchesContext() > openBranchesTemp.get(openBranchesTemp.size() - 1)){
            addFlowElemContext("Merge","");
        }
    }

    @Override public void enterRoot(PythonParser.RootContext ctx) {
        System.out.println("0 INICIO programa");
        FCList = new ArrayList<>();
        FCnames = new ArrayList<>();
    }


    @Override public void exitRoot(PythonParser.RootContext ctx) {
        System.out.println("1 FIN programa");
        drawerChart.addFlowElement("End","");
        FCList.add(drawerChart);
        FCnames.add("Principal");
        //drawerChart.printFlowchartTrace();
    }


    @Override public void enterSingle_input(PythonParser.Single_inputContext ctx) {
//        System.out.println("2");
    }


    @Override public void exitSingle_input(PythonParser.Single_inputContext ctx) {
//        System.out.println("3");
    }


    @Override public void enterFile_input(PythonParser.File_inputContext ctx) {
//        System.out.println("4");
    }


    @Override public void exitFile_input(PythonParser.File_inputContext ctx) {
//        System.out.println("5");
    }


    @Override public void enterEval_input(PythonParser.Eval_inputContext ctx) {
        System.out.println("6");
    }


    @Override public void exitEval_input(PythonParser.Eval_inputContext ctx) {
        System.out.println("7");
    }


    @Override public void enterStmt(PythonParser.StmtContext ctx) {
        System.out.println("8 Entrada Declaracion");
    }


    @Override public void exitStmt(PythonParser.StmtContext ctx) {
        System.out.println("9 Salida Declaracion");
    }


    @Override public void enterIf_stmt(PythonParser.If_stmtContext ctx) {
        System.out.println("10 Entrada al IF: "+ ctx.getText());
        String[] parts = ctx.getText().split(":");
        String ans = parts[0].substring(2,parts[0].length());
        openBranchesTemp.add(getOpenBranchesContext());
        addFlowElemContext("Decision",ans);
        //flowC.setWritingBranch(1); //True
    }


    @Override public void exitIf_stmt(PythonParser.If_stmtContext ctx) {
        System.out.println("11 Salida del IF");
        //addFlowElemContext("Merge","");
        addMergeRecursive();
        openBranchesTemp.remove(openBranchesTemp.size()-1);
    }


    @Override public void enterWhile_stmt(PythonParser.While_stmtContext ctx) {
        System.out.println("12 Entrada ciclo while:"+ctx.getText());
        String str = "Mientras " + ctx.test().getText();
        addFlowElemContext("Loop",str);

    }


    @Override public void exitWhile_stmt(PythonParser.While_stmtContext ctx) {
        System.out.println("13 Salida ciclo while:"+ctx.getText());
        addFlowElemContext("EndLoop","");
    }


    @Override public void enterFor_stmt(PythonParser.For_stmtContext ctx) {
        System.out.println("14 Entrada for loop");
        String str = "Para " + ctx.exprlist().getText() + " en " + ctx.testlist().getText();
        addFlowElemContext("Loop",str);
    }


    @Override public void exitFor_stmt(PythonParser.For_stmtContext ctx) {
        System.out.println("15 Salida for loop");
        addFlowElemContext("EndLoop","");
    }


    @Override public void enterTry_stmt(PythonParser.Try_stmtContext ctx) {
        System.out.println("16");
    }


    @Override public void exitTry_stmt(PythonParser.Try_stmtContext ctx) {
        System.out.println("17");
    }


    @Override public void enterWith_stmt(PythonParser.With_stmtContext ctx) {
        System.out.println("18");
    }


    @Override public void exitWith_stmt(PythonParser.With_stmtContext ctx) {
        System.out.println("19");
    }


    @Override public void enterClass_or_func_def_stmt(PythonParser.Class_or_func_def_stmtContext ctx) {
        System.out.println("20 Entrada Declaracion Funcion o clase: "+ctx.getText());

        if(ctx.funcdef() != null){  //Si se declara una función
            functionChart = new FlowChart();
            System.out.println("ctx nombre funcion: "+ ctx.funcdef().name().getText());
            if(insideClass){
                classChart.addFlowElement("KeyProcess","Definición función \""+ctx.funcdef().name().getText()+"\"");
            } else {
                drawerChart.addFlowElement("KeyProcess","Definición función \""+ctx.funcdef().name().getText()+"\"");
            }
            insideFunction = true;
        } else if (ctx.classdef() != null){
            classChart = new FlowChart();
            nameClass = ctx.classdef().name().getText();
            System.out.println("ctx nombre clase: "+ ctx.classdef().name().getText());
            drawerChart.addFlowElement("KeyProcess","Definición clase \""+ctx.classdef().name().getText()+"\"");
            insideClass = true;
        }

    }


    @Override public void exitClass_or_func_def_stmt(PythonParser.Class_or_func_def_stmtContext ctx) {
        System.out.println("21 Salida Declaracion Funcion o clase");

        if(ctx.funcdef() != null){  //Si se declara una función
            functionChart.addFlowElement("End","");
            FCList.add(functionChart);
            if(insideClass){
                FCnames.add("Función "+ ctx.funcdef().name().getText() + " de la clase "+nameClass);
            } else {
                FCnames.add("Función "+ ctx.funcdef().name().getText());
            }
            insideFunction = false;
        } else if (ctx.classdef() != null){
            classChart.addFlowElement("End","");
            FCList.add(classChart);
            FCnames.add("Clase "+ ctx.classdef().name().getText());
            nameClass = "";
            insideClass = false;
        }

    }


    @Override public void enterSuite(PythonParser.SuiteContext ctx) {
        System.out.println("22");
    }


    @Override public void exitSuite(PythonParser.SuiteContext ctx) {
        System.out.println("23");
    }


    @Override public void enterDecorator(PythonParser.DecoratorContext ctx) {
        System.out.println("24");
    }


    @Override public void exitDecorator(PythonParser.DecoratorContext ctx) {
        System.out.println("25");
    }


    @Override public void enterElif_clause(PythonParser.Elif_clauseContext ctx) {
        System.out.println("26 Entrada ELIF");
        setWritingBranchContext(0); //False
        addFlowElemContext("KeyDecision",ctx.test().getText());

    }


    @Override public void exitElif_clause(PythonParser.Elif_clauseContext ctx) {
        System.out.println("27 Salida ELIF");
    }


    @Override public void enterElse_clause(PythonParser.Else_clauseContext ctx) {
        System.out.println("28 Entrada ELSE");
        setWritingBranchContext(0); //False
    }


    @Override public void exitElse_clause(PythonParser.Else_clauseContext ctx) {
        System.out.println("29 Salida ELSE");
        setWritingBranchContext(1); //True
    }


    @Override public void enterFinally_clause(PythonParser.Finally_clauseContext ctx) {
        System.out.println("30");
    }


    @Override public void exitFinally_clause(PythonParser.Finally_clauseContext ctx) {
        System.out.println("31");
    }


    @Override public void enterWith_item(PythonParser.With_itemContext ctx) {
        System.out.println("32");
    }


    @Override public void exitWith_item(PythonParser.With_itemContext ctx) {
        System.out.println("33");
    }


    @Override public void enterExcept_clause(PythonParser.Except_clauseContext ctx) {
        System.out.println("34");
    }


    @Override public void exitExcept_clause(PythonParser.Except_clauseContext ctx) {
        System.out.println("35");
    }


    @Override public void enterClassdef(PythonParser.ClassdefContext ctx) {
        System.out.println("36");
    }


    @Override public void exitClassdef(PythonParser.ClassdefContext ctx) {
        System.out.println("37");
    }


    @Override public void enterFuncdef(PythonParser.FuncdefContext ctx) {
        System.out.println("38 Entrada definición funcion: "+ctx.name().getText());
    }


    @Override public void exitFuncdef(PythonParser.FuncdefContext ctx) {
        System.out.println("39 Salida definición funcion");
    }


    @Override public void enterTypedargslist(PythonParser.TypedargslistContext ctx) {
        System.out.println("40");
    }


    @Override public void exitTypedargslist(PythonParser.TypedargslistContext ctx) {
        System.out.println("41");
    }


    @Override public void enterArgs(PythonParser.ArgsContext ctx) {
        System.out.println("42");
    }


    @Override public void exitArgs(PythonParser.ArgsContext ctx) {
        System.out.println("43");
    }


    @Override public void enterKwargs(PythonParser.KwargsContext ctx) {
        System.out.println("44");
    }


    @Override public void exitKwargs(PythonParser.KwargsContext ctx) {
        System.out.println("45");
    }


    @Override public void enterDef_parameters(PythonParser.Def_parametersContext ctx) {
        System.out.println("46");
    }


    @Override public void exitDef_parameters(PythonParser.Def_parametersContext ctx) {
        System.out.println("47");
    }


    @Override public void enterDef_parameter(PythonParser.Def_parameterContext ctx) {
        System.out.println("48");
    }


    @Override public void exitDef_parameter(PythonParser.Def_parameterContext ctx) {
        System.out.println("49");
    }


    @Override public void enterNamed_parameter(PythonParser.Named_parameterContext ctx) {
        System.out.println("50");
    }


    @Override public void exitNamed_parameter(PythonParser.Named_parameterContext ctx) {
        System.out.println("51");
    }


    @Override public void enterSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        System.out.println("52 Entrada declaración simple");
    }


    @Override public void exitSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        System.out.println("53 Salida declaración simple");
    }


    @Override public void enterExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        System.out.println("54 Entrada declaración expresión: " + ctx.getText());


         if(ctx.getText().contains("print")){
            addFlowElemContext("IO", ctx.getText());
        } else if(ctx.getText().contains("=")){
             if(ctx.getText().contains("input")){
                 addFlowElemContext("KeyIO", "Ingresar "+ctx.getText().substring(0,ctx.getText().indexOf("=")));
             } else {
                 addFlowElemContext("Process", ctx.getText());
             }
        } else if(ctx.getText().contains("()")){
            addFlowElemContext("KeyProcess","Llamar a "+ctx.getText());
        }
    }


    @Override public void exitExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        System.out.println("55 Salida declaración expresión ");
    }


    @Override public void enterPrint_stmt(PythonParser.Print_stmtContext ctx) {
        System.out.println("56");
    }


    @Override public void exitPrint_stmt(PythonParser.Print_stmtContext ctx) {
        System.out.println("57");
    }


    @Override public void enterDel_stmt(PythonParser.Del_stmtContext ctx) {
        System.out.println("58");
    }


    @Override public void exitDel_stmt(PythonParser.Del_stmtContext ctx) {
        System.out.println("59");
    }


    @Override public void enterPass_stmt(PythonParser.Pass_stmtContext ctx) {
        System.out.println("60");
    }


    @Override public void exitPass_stmt(PythonParser.Pass_stmtContext ctx) {
        System.out.println("61");
    }


    @Override public void enterBreak_stmt(PythonParser.Break_stmtContext ctx) {
        System.out.println("62");
    }


    @Override public void exitBreak_stmt(PythonParser.Break_stmtContext ctx) {
        System.out.println("63");
    }


    @Override public void enterContinue_stmt(PythonParser.Continue_stmtContext ctx) {
        System.out.println("64");
    }


    @Override public void exitContinue_stmt(PythonParser.Continue_stmtContext ctx) {
        System.out.println("65");
    }


    @Override public void enterReturn_stmt(PythonParser.Return_stmtContext ctx) {
        System.out.println("66 Entrada return");
        addFlowElemContext("KeyProcess","Retornar "+ctx.testlist().getText());
    }


    @Override public void exitReturn_stmt(PythonParser.Return_stmtContext ctx) {
        System.out.println("67 Salida return");
    }


    @Override public void enterRaise_stmt(PythonParser.Raise_stmtContext ctx) {
        System.out.println("68");
    }


    @Override public void exitRaise_stmt(PythonParser.Raise_stmtContext ctx) {
        System.out.println("69");
    }


    @Override public void enterYield_stmt(PythonParser.Yield_stmtContext ctx) {
        System.out.println("70 Entrada yield");
        if(ctx.yield_expr().yield_arg() != null){
            addFlowElemContext("KeyProcess","Yield (ceder) "+ctx.yield_expr().yield_arg().getText());
        } else {
            addFlowElemContext("KeyProcess","Yield (ceder)");
        }

    }


    @Override public void exitYield_stmt(PythonParser.Yield_stmtContext ctx) {
        System.out.println("71");
    }


    @Override public void enterImport_stmt(PythonParser.Import_stmtContext ctx) {
        System.out.println("72 Entrada import");
        addFlowElemContext("KeyProcess","Importar "+ctx.dotted_as_names().getText());
    }


    @Override public void exitImport_stmt(PythonParser.Import_stmtContext ctx) {
        System.out.println("73");
    }


    @Override public void enterFrom_stmt(PythonParser.From_stmtContext ctx) {
        System.out.println("74 Entrada from");
        String str = "Desde "+ctx.dotted_name().getText() + " importar ";
        //ctx.children.indexOf(ctx.IMPORT().getText())
        boolean openFlag = false;
        for(int i = 0;i < ctx.children.size();i++){
            if(openFlag){
                str += ctx.getChild(i).getText();
            }
            if(ctx.getChild(i).getText().contains("import")){
                openFlag = true;
            }
        }
        addFlowElemContext("KeyProcess",str);
    }


    @Override public void exitFrom_stmt(PythonParser.From_stmtContext ctx) {
        System.out.println("75");
    }


    @Override public void enterGlobal_stmt(PythonParser.Global_stmtContext ctx) {
        System.out.println("76");
    }


    @Override public void exitGlobal_stmt(PythonParser.Global_stmtContext ctx) {
        System.out.println("77");
    }


    @Override public void enterExec_stmt(PythonParser.Exec_stmtContext ctx) {
        System.out.println("78");
    }


    @Override public void exitExec_stmt(PythonParser.Exec_stmtContext ctx) {
        System.out.println("79");
    }


    @Override public void enterAssert_stmt(PythonParser.Assert_stmtContext ctx) {
        System.out.println("80");
    }


    @Override public void exitAssert_stmt(PythonParser.Assert_stmtContext ctx) {
        System.out.println("81");
    }


    @Override public void enterNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx) {
        System.out.println("82");
    }


    @Override public void exitNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx) {
        System.out.println("83");
    }


    @Override public void enterTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx) {
        //System.out.println("84");
    }


    @Override public void exitTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx) {
        //System.out.println("85");
    }


    @Override public void enterStar_expr(PythonParser.Star_exprContext ctx) {
        System.out.println("86");
    }


    @Override public void exitStar_expr(PythonParser.Star_exprContext ctx) {
        System.out.println("87");
    }


    @Override public void enterAssign_part(PythonParser.Assign_partContext ctx) {
        System.out.println("88 Entrada Asignación: "+ctx.getStart().getText());
    }


    @Override public void exitAssign_part(PythonParser.Assign_partContext ctx) {
        System.out.println("89 Salida Asignación: "+ctx.getStart().getText());
    }


    @Override public void enterExprlist(PythonParser.ExprlistContext ctx) {
        System.out.println("90");
    }


    @Override public void exitExprlist(PythonParser.ExprlistContext ctx) {
        System.out.println("91");
    }


    @Override public void enterImport_as_names(PythonParser.Import_as_namesContext ctx) {
        System.out.println("92");
    }


    @Override public void exitImport_as_names(PythonParser.Import_as_namesContext ctx) {
        System.out.println("93");
    }


    @Override public void enterImport_as_name(PythonParser.Import_as_nameContext ctx) {
        System.out.println("94");
    }


    @Override public void exitImport_as_name(PythonParser.Import_as_nameContext ctx) {
        System.out.println("95");
    }


    @Override public void enterDotted_as_names(PythonParser.Dotted_as_namesContext ctx) {
        System.out.println("96");
    }


    @Override public void exitDotted_as_names(PythonParser.Dotted_as_namesContext ctx) {
        System.out.println("97");
    }


    @Override public void enterDotted_as_name(PythonParser.Dotted_as_nameContext ctx) {
        System.out.println("98");
    }


    @Override public void exitDotted_as_name(PythonParser.Dotted_as_nameContext ctx) {
        System.out.println("99");
    }


    @Override public void enterTest(PythonParser.TestContext ctx) {
        //System.out.println("100");
    }


    @Override public void exitTest(PythonParser.TestContext ctx) {
        //System.out.println("101");
    }


    @Override public void enterVarargslist(PythonParser.VarargslistContext ctx) {
        System.out.println("102");
    }


    @Override public void exitVarargslist(PythonParser.VarargslistContext ctx) {
        System.out.println("103");
    }


    @Override public void enterVardef_parameters(PythonParser.Vardef_parametersContext ctx) {
        System.out.println("104");
    }


    @Override public void exitVardef_parameters(PythonParser.Vardef_parametersContext ctx) {
        System.out.println("105");
    }


    @Override public void enterVardef_parameter(PythonParser.Vardef_parameterContext ctx) {
        System.out.println("106");
    }


    @Override public void exitVardef_parameter(PythonParser.Vardef_parameterContext ctx) {
        System.out.println("107");
    }


    @Override public void enterVarargs(PythonParser.VarargsContext ctx) {
        System.out.println("108");
    }


    @Override public void exitVarargs(PythonParser.VarargsContext ctx) {
        System.out.println("109");
    }


    @Override public void enterVarkwargs(PythonParser.VarkwargsContext ctx) {
        System.out.println("110");
    }


    @Override public void exitVarkwargs(PythonParser.VarkwargsContext ctx) {
        System.out.println("111");
    }


    @Override public void enterLogical_test(PythonParser.Logical_testContext ctx) {
        //System.out.println("112 Entrada test logico: "+ctx.getText());
    }


    @Override public void exitLogical_test(PythonParser.Logical_testContext ctx) {
        //System.out.println("113 Salida test logico: "+ctx.getText());
    }


    @Override public void enterComparison(PythonParser.ComparisonContext ctx) {
        //System.out.println("114 Entrada comparación: "+ctx.getText());
    }


    @Override public void exitComparison(PythonParser.ComparisonContext ctx) {
        //System.out.println("115 Salida comparación: "+ctx.getText());
    }


    @Override public void enterExpr(PythonParser.ExprContext ctx) {
        //System.out.println("116 Entrada expresión: "+ctx.getText());
    }


    @Override public void exitExpr(PythonParser.ExprContext ctx) {
        //System.out.println("117 Salida expresión: "+ctx.getText());
    }


    @Override public void enterAtom(PythonParser.AtomContext ctx) {
        System.out.println("118 Entrada Atom: "+ctx.getRuleContext().getText());
    }


    @Override public void exitAtom(PythonParser.AtomContext ctx) {
        System.out.println("119 Salida Atom: "+ctx.getText());
    }


    @Override public void enterDictorsetmaker(PythonParser.DictorsetmakerContext ctx) {
        System.out.println("120");
    }


    @Override public void exitDictorsetmaker(PythonParser.DictorsetmakerContext ctx) {
        System.out.println("121");
    }


    @Override public void enterTestlist_comp(PythonParser.Testlist_compContext ctx) {
        System.out.println("122");
    }


    @Override public void exitTestlist_comp(PythonParser.Testlist_compContext ctx) {
        System.out.println("123");
    }


    @Override public void enterTestlist(PythonParser.TestlistContext ctx) {
        //System.out.println("124");
    }


    @Override public void exitTestlist(PythonParser.TestlistContext ctx) {
        //System.out.println("125");
    }


    @Override public void enterDotted_name(PythonParser.Dotted_nameContext ctx) {
        System.out.println("126");
    }


    @Override public void exitDotted_name(PythonParser.Dotted_nameContext ctx) {
        System.out.println("127");
    }


    @Override public void enterName(PythonParser.NameContext ctx) {
        System.out.println("128 Entrada nombre: "+ctx.getText());
    }


    @Override public void exitName(PythonParser.NameContext ctx) {
        System.out.println("129 Salida nombre: "+ctx.getText());
    }


    @Override public void enterNumber(PythonParser.NumberContext ctx) {
        System.out.println("130 Entrada número: "+ctx.getText());
    }


    @Override public void exitNumber(PythonParser.NumberContext ctx) {
        System.out.println("131 Salida número: "+ctx.getText());
    }


    @Override public void enterInteger(PythonParser.IntegerContext ctx) {
        System.out.println("132 Entrada número entero: "+ctx.getText());
    }


    @Override public void exitInteger(PythonParser.IntegerContext ctx) {
        System.out.println("133 Salida número entero: "+ctx.getText());
    }


    @Override public void enterYield_expr(PythonParser.Yield_exprContext ctx) {
        System.out.println("134");
    }


    @Override public void exitYield_expr(PythonParser.Yield_exprContext ctx) {
        System.out.println("135");
    }


    @Override public void enterYield_arg(PythonParser.Yield_argContext ctx) {
        System.out.println("136");
    }


    @Override public void exitYield_arg(PythonParser.Yield_argContext ctx) {
        System.out.println("137");
    }


    @Override public void enterTrailer(PythonParser.TrailerContext ctx) {
        //System.out.println("138");
    }


    @Override public void exitTrailer(PythonParser.TrailerContext ctx) {
        //System.out.println("139");
    }


    @Override public void enterArguments(PythonParser.ArgumentsContext ctx) {
        //System.out.println("140 Entrada Argumentos: "+ctx.getText());
    }


    @Override public void exitArguments(PythonParser.ArgumentsContext ctx) {
        //System.out.println("141 Salida Argumentos: "+ctx.getText());
    }


    @Override public void enterArglist(PythonParser.ArglistContext ctx) {
        //System.out.println("142 Entrada lista args");
    }


    @Override public void exitArglist(PythonParser.ArglistContext ctx) {
        //System.out.println("143 Salida lista args");
    }


    @Override public void enterArgument(PythonParser.ArgumentContext ctx) {
        //System.out.println("144 Entrada argumento: "+ctx.getText());
    }


    @Override public void exitArgument(PythonParser.ArgumentContext ctx) {
        //System.out.println("145 Salida argumento: "+ctx.getText());
    }


    @Override public void enterSubscriptlist(PythonParser.SubscriptlistContext ctx) {
        System.out.println("146");
    }


    @Override public void exitSubscriptlist(PythonParser.SubscriptlistContext ctx) {
        System.out.println("147");
    }


    @Override public void enterSubscript(PythonParser.SubscriptContext ctx) {
        System.out.println("148");
    }


    @Override public void exitSubscript(PythonParser.SubscriptContext ctx) {
        System.out.println("149");
    }


    @Override public void enterSliceop(PythonParser.SliceopContext ctx) {
        System.out.println("150");
    }


    @Override public void exitSliceop(PythonParser.SliceopContext ctx) {
        System.out.println("151");
    }


    @Override public void enterComp_for(PythonParser.Comp_forContext ctx) {
        System.out.println("152");
    }


    @Override public void exitComp_for(PythonParser.Comp_forContext ctx) {
        System.out.println("153");
    }


    @Override public void enterComp_iter(PythonParser.Comp_iterContext ctx) {
        System.out.println("154");
    }


    @Override public void exitComp_iter(PythonParser.Comp_iterContext ctx) {
        System.out.println("155");
    }



    @Override public void enterEveryRule(ParserRuleContext ctx) {
//        System.out.println("156");
    }


    @Override public void exitEveryRule(ParserRuleContext ctx) {
//        System.out.println("157");
    }


    @Override public void visitTerminal(TerminalNode node) {
//        System.out.println("158");
    }


    @Override public void visitErrorNode(ErrorNode node) {
//        System.out.println("159");
    }
}
