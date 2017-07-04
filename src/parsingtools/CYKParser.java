package parsingtools;

import java.util.*;

/**
 * Created by michael on 5/30/17.
 */
public class CYKParser extends Parser {

    private List<Parser.Token> mTokens;
    private ContextFreeGrammar mGrammar;
    Map<String, Parser.ParseTree>[][] mMemoizationTable;

    public Parser.ParseTree parse(List<Parser.Token> tokens, ContextFreeGrammar grammar) {
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

    private Parser.ParseTree parse(int startingIndex, int length, String rootElement) {
        Parser.ParseTree memoizedParseTree = mMemoizationTable[startingIndex][length].get(rootElement);
        if (memoizedParseTree != null) {
            return memoizedParseTree;
        } else {
            mMemoizationTable[startingIndex][length].put(rootElement, new Parser.ParseTree());
        }

        if (mGrammar.getNonterminals().contains(rootElement)) {
            for (List<String> productionResult : mGrammar.getRulesForNonterminal(rootElement)) {
                List<Parser.ParseTree> children = getChildren(startingIndex, length, productionResult);
                if (children != null) {
                    Parser.ParseTree parseTree = new Parser.ParseTree(rootElement, children);
                    mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                    return parseTree;
                }
            }
        } else {
            if (length == 0 && rootElement.equals(ContextFreeGrammar.EMPTY_STRING)) {
                Parser.ParseTree parseTree = new Parser.ParseTree(new Parser.Token(ContextFreeGrammar.EMPTY_STRING, ""));
                mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                return parseTree;
            } else if (length == 1 && rootElement.equals(mTokens.get(startingIndex).getTokenId())) {
                Parser.ParseTree parseTree = new Parser.ParseTree(mTokens.get(startingIndex));
                mMemoizationTable[startingIndex][length].put(rootElement, parseTree);
                return parseTree;
            }
        }

        return new Parser.ParseTree();
    }

    List<Parser.ParseTree> getChildren(int startingIndex, int length, List<String> productionResult) {

        if (productionResult.size() >= 2) {
            for (String element : productionResult) {
                for (int i = 0; i <= length; i++) {
                    if (parse(startingIndex, i, element).parsingSuccessful()) {
                        List<String> productionResultWithoutFirstItem = new ArrayList(productionResult);
                        productionResultWithoutFirstItem.remove(0);
                        List<Parser.ParseTree> childrenAfterFirst = getChildren(startingIndex + i, length - i, productionResultWithoutFirstItem);
                        if (childrenAfterFirst != null) {
                            childrenAfterFirst.add(0, mMemoizationTable[startingIndex][i].get(element));
                            return childrenAfterFirst;
                        }
                    }

                    if (i == length) {
                        return null;
                    }
                }
            }
        } else if (parse(startingIndex, length, productionResult.get(0)).parsingSuccessful()) {
            List<Parser.ParseTree> finalTreeList = new ArrayList();
            Parser.ParseTree finalTree = mMemoizationTable[startingIndex][length].get(productionResult.get(0));
            finalTreeList.add(finalTree);
            return finalTreeList;
        }

        return null;
    }
}
