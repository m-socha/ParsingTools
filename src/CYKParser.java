import java.util.*;

/**
 * Created by michael on 5/30/17.
 */
public class CYKParser extends Parser {

    private List<Token> mTokens;
    private ContextFreeGrammar mGrammar;
    Map<String, ParseTree>[][] mMemoizationTable;

    public ParseTree parse(List<Token> tokens, ContextFreeGrammar grammar) {
        mTokens = tokens;
        mGrammar = grammar;
        mMemoizationTable = new HashMap[tokens.size() + 1][tokens.size() + 1];
        for (int i = 0; i <= tokens.size(); i++) {
            for (int j = 0; j <= tokens.size(); j++) {
                mMemoizationTable[i][j] = new HashMap();
            }
        }

        return parse(0, tokens.size(), grammar.getLeadingNonterminal());
    }

    private ParseTree parse(int startingIndex, int length, String rootElement) {
        ParseTree memoizedParseTree = mMemoizationTable[startingIndex][length].get(rootElement);
        if (memoizedParseTree != null) {
            return memoizedParseTree;
        } else {
            mMemoizationTable[startingIndex][length].put(rootElement, new ParseTree());
        }

        if (mGrammar.getNonterminals().contains(rootElement)) {
            for (List<String> productionResult : mGrammar.getRulesForNonterminal(rootElement)) {
                List<ParseTree> children = getChildren(startingIndex, length, productionResult);
                if (children != null) {
                    ParseTree parseTree = new ParseTree(rootElement, children);
                    mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                    return parseTree;
                }
            }
        } else {
            if (length == 0 && rootElement.equals(ContextFreeGrammar.EMPTY_STRING)) {
                ParseTree parseTree = new ParseTree(new Token(ContextFreeGrammar.EMPTY_STRING, ""));
                mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                return parseTree;
            } else if (length == 1 && rootElement.equals(mTokens.get(startingIndex).getTokenId())) {
                ParseTree parseTree = new ParseTree(mTokens.get(startingIndex));
                mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                return parseTree;
            }
        }

        return new ParseTree();
    }

    List<ParseTree> getChildren(int startingIndex, int length, List<String> productionResult) {

        if (productionResult.size() >= 2) {
            for (String element : productionResult) {
                for (int i = 0; i <= length; i++) {
                    if (parse(startingIndex, i, element).parsingSuccessful()) {
                        List<String> productionResultWithoutFirstItem = new ArrayList(productionResult);
                        productionResultWithoutFirstItem.remove(0);
                        List<ParseTree> childrenAfterFirst = getChildren(startingIndex + i, length - i, productionResultWithoutFirstItem);
                        if (childrenAfterFirst != null) {
                            childrenAfterFirst.add(0, mMemoizationTable[startingIndex][i].get(element));
                            return childrenAfterFirst;
                        }
                    }
                }
            }
        } else if (parse(startingIndex, length, productionResult.get(0)).parsingSuccessful()) {
            List<ParseTree> finalTreeList = new ArrayList();
            ParseTree finalTree = mMemoizationTable[startingIndex][length].get(productionResult.get(0));
            finalTreeList.add(finalTree);
            return finalTreeList;
        }

        return null;
    }
}
