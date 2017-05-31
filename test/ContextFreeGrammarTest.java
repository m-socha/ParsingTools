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
        expectedNonterminals.add("expression");
        assertEquals(expectedNonterminals, expressionCFG.getNonterminals());

        Set<List<String>> expectedProductions = new HashSet();
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"LEFT_BRACKET", "expression", "RIGHT_BRACKET"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"NUMBER"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"expression", "PLUS", "expression"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"expression", "MINUS", "expression"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"expression", "MULTIPLY", "expression"})));
        expectedProductions.add(new ArrayList(Arrays.asList(new String[] {"expression", "DIVIDE", "expression"})));
        assertEquals(expectedProductions, expressionCFG.getRulesForNonterminal("expression"));
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
        assertEquals(CFGs.getExpressionCFG(), expressionCFG);
    }
}
