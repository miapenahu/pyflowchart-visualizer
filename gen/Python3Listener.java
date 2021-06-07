// Generated from D:/Proyectos/LenPro/Proyecto Final/grammar\Python3.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Python3Parser}.
 */
public interface Python3Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Python3Parser#single_input}.
	 * @param ctx the parse tree
	 */
	void enterSingle_input(Python3Parser.Single_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#single_input}.
	 * @param ctx the parse tree
	 */
	void exitSingle_input(Python3Parser.Single_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#file_input}.
	 * @param ctx the parse tree
	 */
	void enterFile_input(Python3Parser.File_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#file_input}.
	 * @param ctx the parse tree
	 */
	void exitFile_input(Python3Parser.File_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(Python3Parser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(Python3Parser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmt(Python3Parser.Simple_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmt(Python3Parser.Simple_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSmall_stmt(Python3Parser.Small_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSmall_stmt(Python3Parser.Small_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#assignment_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_stmt(Python3Parser.Assignment_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#assignment_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_stmt(Python3Parser.Assignment_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFlow_stmt(Python3Parser.Flow_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFlow_stmt(Python3Parser.Flow_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(Python3Parser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(Python3Parser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(Python3Parser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(Python3Parser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCompound_stmt(Python3Parser.Compound_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCompound_stmt(Python3Parser.Compound_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(Python3Parser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(Python3Parser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(Python3Parser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(Python3Parser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(Python3Parser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(Python3Parser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(Python3Parser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(Python3Parser.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#print_stmt}.
	 * @param ctx the parse tree
	 */
	void enterPrint_stmt(Python3Parser.Print_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#print_stmt}.
	 * @param ctx the parse tree
	 */
	void exitPrint_stmt(Python3Parser.Print_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#comp_op}.
	 * @param ctx the parse tree
	 */
	void enterComp_op(Python3Parser.Comp_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#comp_op}.
	 * @param ctx the parse tree
	 */
	void exitComp_op(Python3Parser.Comp_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(Python3Parser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(Python3Parser.ExprContext ctx);
}