import parsingtools.CYKParser;
import parsingtools.ContextFreeGrammar;
import parsingtools.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by michael on 6/3/17.
 */
public class CYKParserTest {

    @Test
    public void testTokensNotInAlphabet() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));
        tokens.add(new Parser.Token("tokenNotInGrammer", "-"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "7"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getExpressionCFG());

        assertFalse(expressionParseTree.parsingSuccessful());
    }

    @Test
    public void testNonterminalToken() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_EXPRESSION_ID, "5"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "7"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_RIGHT_BRACKET_ID, ")"));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getExpressionCFG());

        assertFalse(expressionParseTree.parsingSuccessful());
    }

    @Test
    public void testNotInGrammar() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "7"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getExpressionCFG());

        assertFalse(expressionParseTree.parsingSuccessful());
    }

    @Test
    public void testSingleToken() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getExpressionCFG());

        Parser.ParseTree expectedParseTree = new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
            add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5")));
        }});

        assertEquals(expectedParseTree, expressionParseTree);
    }

    @Test
    public void testExpression() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "("));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "3"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_PLUS_ID, "+"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "4"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_RIGHT_BRACKET_ID, ")"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_DIVIDE_ID, "/"));
        tokens.add(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5"));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getExpressionCFG());

        Parser.ParseTree expectedParseTree = new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
            add(new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
                add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_LEFT_BRACKET_ID, "(")));
                add(new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
                    add(new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
                        add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "3")));
                    }}));
                    add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_PLUS_ID, "+")));
                    add(new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
                        add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "4")));
                    }}));
                }}));
                add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_RIGHT_BRACKET_ID, ")")));
            }}));
            add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_DIVIDE_ID, "/")));
            add(new Parser.ParseTree(CFGs.EXPRESSION_EXPRESSION_ID, new ArrayList() {{
                add(new Parser.ParseTree(new Parser.Token(CFGs.EXPRESSION_NUMBER_ID, "5")));
            }}));
        }});

        assertEquals(expectedParseTree, expressionParseTree);
    }

    @Test
    public void emptyStringTest() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c"));
        tokens.add(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c"));
        tokens.add(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c"));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getEmptyStringTestCFG());

        Parser.ParseTree expectedParseTree = new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_A, new ArrayList() {{
            add(new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_A, new ArrayList() {{
                add(new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_B, new ArrayList() {{
                    add(new Parser.ParseTree(new Parser.Token(ContextFreeGrammar.EMPTY_STRING, "")));
                }}));
            }}));
            add(new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_B, new ArrayList() {{
                add(new Parser.ParseTree(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c")));
            }}));
            add(new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_B, new ArrayList() {{
                add(new Parser.ParseTree(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c")));
            }}));
            add(new Parser.ParseTree(CFGs.EMPTY_STRING_TEST_B, new ArrayList() {{
                add(new Parser.ParseTree(new Parser.Token(CFGs.EMPTY_STRING_TEST_C, "c")));
            }}));
        }});

        assertEquals(expectedParseTree, expressionParseTree);
    }

    @Test
    public void matrixMultGrammarTest() {
        List<Parser.Token> tokens = new ArrayList();
        tokens.add(new Parser.Token(CFGs.MATRIX_TOKEN, "A"));
        tokens.add(new Parser.Token(CFGs.MATRIX_TOKEN, "B"));

        Parser parser = new CYKParser();
        Parser.ParseTree expressionParseTree = parser.parse(tokens, CFGs.getMatrixMultGrammar());

        Parser.ParseTree expectedParseTree = new Parser.ParseTree(CFGs.MATRIX_NUMBER_EXPRESSION, new ArrayList() {{
            add(new Parser.ParseTree(CFGs.MATRIX_EXPRESSION, new ArrayList() {{
                add(new Parser.ParseTree(CFGs.MATRIX_MULT_EXPRESSION, new ArrayList() {{
                    add(new Parser.ParseTree(CFGs.MATRIX_EXPRESSION, new ArrayList() {{
                        add(new Parser.ParseTree(new Parser.Token(CFGs.MATRIX_TOKEN, "A")));
                    }}));
                    add(new Parser.ParseTree(CFGs.MATRIX_EXPRESSION, new ArrayList() {{
                        add(new Parser.ParseTree(new Parser.Token(CFGs.MATRIX_TOKEN, "B")));
                    }}));
                }}));
            }}));
        }});

        assertEquals(expectedParseTree, expressionParseTree);
    }
}
