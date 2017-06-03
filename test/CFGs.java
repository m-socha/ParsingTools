import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by michael on 5/21/17.
 */
public class CFGs {

    public static ContextFreeGrammar getExpressionCFG() {
        ContextFreeGrammar expressionCFG = new ContextFreeGrammar();
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"LEFT_BRACKET", "expression", "RIGHT_BRACKET"})));
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"NUMBER"})));
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"expression", "PLUS", "expression"})));
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"expression", "MINUS", "expression"})));
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"expression", "MULTIPLY", "expression"})));
        expressionCFG.addRule("expression", new ArrayList<>(Arrays.asList(new String[] {"expression", "DIVIDE", "expression"})));
        expressionCFG.setLeadingNonterminal("expression");
        return expressionCFG;
    }

}
