package parsingtools;

import java.util.*;

/**
 * Created by michael on 5/21/17.
 */
public class ContextFreeGrammar {

    public static final String EMPTY_STRING = "ε";

    private Map<String, Set<List<String>>> mRuleMap = new HashMap();
    private String mLeadingNonterminal;

    public void addRule(String rule) {
        String[] splitRule = rule.split("\\s+");
        String leftHandNonterminal = splitRule[0];
        List<String> rightHandProduction = new ArrayList();
        for (int i = 2; i < splitRule.length; i++) {
            rightHandProduction.add(splitRule[i]);
        }
        addRule(leftHandNonterminal, rightHandProduction);
    }

    public void addRule(String nonterminal, List<String> production) {
        if (mRuleMap.get(nonterminal) != null) {
            mRuleMap.get(nonterminal).add(production);
        } else {
            Set<List<String>> productionList = new HashSet();
            productionList.add(production);
            mRuleMap.put(nonterminal, productionList);
        }
    }

    public void setLeadingNonterminal(String leadingNonterminal) {
        mLeadingNonterminal = leadingNonterminal;
    }

    public Set<String> getNonterminals() {
        return mRuleMap.keySet();
    }

    public Set<List<String>> getRulesForNonterminal(String nonterminal) {
        return mRuleMap.get(nonterminal);
    }

    public String getLeadingNonterminal() {
        return mLeadingNonterminal;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ContextFreeGrammar) {
            ContextFreeGrammar contextFreeGrammar = (ContextFreeGrammar) o;
            return mRuleMap.equals(contextFreeGrammar.mRuleMap)
                    && mLeadingNonterminal.equals(contextFreeGrammar.mLeadingNonterminal);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * mRuleMap.hashCode() + mLeadingNonterminal.hashCode();
    }
}
