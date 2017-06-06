import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by michael on 5/21/17.
 */
public class CFGs {

    public static final String EXPRESSION_EXPRESSION_ID = "expression";
    public static final String EXPRESSION_LEFT_BRACKET_ID = "LEFT_BRACKET";
    public static final String EXPRESSION_RIGHT_BRACKET_ID = "RIGHT_BRACKET";
    public static final String EXPRESSION_NUMBER_ID = "NUMBER";
    public static final String EXPRESSION_PLUS_ID = "PLUS";
    public static final String EXPRESSION_MINUS_ID = "MINUS";
    public static final String EXPRESSION_MULTIPLY_ID = "MULTIPLY";
    public static final String EXPRESSION_DIVIDE_ID = "DIVIDE";

    public static final String EMPTY_STRING_TEST_A = "A";
    public static final String EMPTY_STRING_TEST_B = "B";
    public static final String EMPTY_STRING_TEST_C = "c";

    public static ContextFreeGrammar getExpressionCFG() {
        ContextFreeGrammar expressionCFG = new ContextFreeGrammar();
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_LEFT_BRACKET_ID, EXPRESSION_EXPRESSION_ID, EXPRESSION_RIGHT_BRACKET_ID})));
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_NUMBER_ID})));
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_EXPRESSION_ID, EXPRESSION_PLUS_ID, EXPRESSION_EXPRESSION_ID})));
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_EXPRESSION_ID, EXPRESSION_MINUS_ID, EXPRESSION_EXPRESSION_ID})));
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_EXPRESSION_ID, EXPRESSION_MULTIPLY_ID, EXPRESSION_EXPRESSION_ID})));
        expressionCFG.addRule(EXPRESSION_EXPRESSION_ID, new ArrayList<>(Arrays.asList(new String[] {EXPRESSION_EXPRESSION_ID, EXPRESSION_DIVIDE_ID, EXPRESSION_EXPRESSION_ID})));
        expressionCFG.setLeadingNonterminal(EXPRESSION_EXPRESSION_ID);
        return expressionCFG;
    }

    public static ContextFreeGrammar getEmptyStringTestCFG() {
        ContextFreeGrammar emptyStringCFG = new ContextFreeGrammar();
        emptyStringCFG.addRule(EMPTY_STRING_TEST_A, new ArrayList<>(Arrays.asList(new String[] {EMPTY_STRING_TEST_A, EMPTY_STRING_TEST_B, EMPTY_STRING_TEST_B, EMPTY_STRING_TEST_B})));
        emptyStringCFG.addRule(EMPTY_STRING_TEST_A, new ArrayList<>(Arrays.asList(new String[] {EMPTY_STRING_TEST_B})));
        emptyStringCFG.addRule(EMPTY_STRING_TEST_B, new ArrayList<>(Arrays.asList(new String[] {EMPTY_STRING_TEST_C})));
        emptyStringCFG.addRule(EMPTY_STRING_TEST_B, new ArrayList<>(Arrays.asList(new String[] {ContextFreeGrammar.EMPTY_STRING})));
        emptyStringCFG.setLeadingNonterminal(EMPTY_STRING_TEST_A);
        return emptyStringCFG;
    }
}
