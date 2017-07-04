import parsingtools.ContextFreeGrammar;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 5/21/17.
 */
public class ContextFreeGrammarTest {

    @Test
    public void productionRuleTest() {
        ContextFreeGrammar expressionCFG = CFGs.getExpressionCFG();

        Set<String> expectedNonterminals = new HashSet();
        expectedNonterminals.add(CFGs.EXPRESSION_EXPRESSION_ID);
        assertEquals(expectedNonterminals, expressionCFG.getNonterminals());

        assertEquals(CFGs.EXPRESSION_EXPRESSION_ID, expressionCFG.getLeadingNonterminal());

        Set<List<String>> expectedProductions = new HashSet();
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"LEFT_BRACKET", CFGs.EXPRESSION_EXPRESSION_ID, "RIGHT_BRACKET"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"NUMBER"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {CFGs.EXPRESSION_EXPRESSION_ID, "PLUS", CFGs.EXPRESSION_EXPRESSION_ID})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {CFGs.EXPRESSION_EXPRESSION_ID, "MINUS", CFGs.EXPRESSION_EXPRESSION_ID})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {CFGs.EXPRESSION_EXPRESSION_ID, "MULTIPLY", CFGs.EXPRESSION_EXPRESSION_ID})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {CFGs.EXPRESSION_EXPRESSION_ID, "DIVIDE", CFGs.EXPRESSION_EXPRESSION_ID})));
        assertEquals(expectedProductions, expressionCFG.getRulesForNonterminal(CFGs.EXPRESSION_EXPRESSION_ID));
    }

    @Test
    public void productionRuleStringInputTest() {
        ContextFreeGrammar expressionCFG = new ContextFreeGrammar();
        expressionCFG.addRule("expression -> LEFT_BRACKET expression RIGHT_BRACKET");
        expressionCFG.addRule("expression -> NUMBER");
        expressionCFG.addRule("expression -> expression PLUS expression");
        expressionCFG.addRule("expression -> expression MINUS expression");
        expressionCFG.addRule("expression -> expression MULTIPLY expression");
        expressionCFG.addRule("expression -> expression DIVIDE expression");
        expressionCFG.setLeadingNonterminal("expression");
        assertEquals(CFGs.getExpressionCFG(), expressionCFG);
    }
}
