import parsingtools.DFA;

/**
 * Created by michael on 5/21/17.
 */
public class DFAs {

    public static DFA getEvenBinaryStringDfa() {
        DFA evenBinaryStringDfa = new DFA();
        DFA.State evenBinaryString = new DFA.State("even", true);
        DFA.State oddBinaryString = new DFA.State("odd", false);

        evenBinaryString.addTransition('0', evenBinaryString);
        evenBinaryString.addTransition('1', oddBinaryString);

        oddBinaryString.addTransition('0', evenBinaryString);
        oddBinaryString.addTransition('1', oddBinaryString);

        evenBinaryStringDfa.setSourceState(evenBinaryString);

        return evenBinaryStringDfa;
    }

    public static DFA getTwoOrThreeADfa() {
        DFA twoOrThreeADfa = new DFA();
        DFA.State zeroAString = new DFA.State("zero_a", false);
        DFA.State oneAString = new DFA.State("one_a", false);
        DFA.State twoAString = new DFA.State("two_a", true);
        DFA.State threeAString = new DFA.State("three_a", true);

        zeroAString.addTransition('a', oneAString);
        zeroAString.addTransition('b', zeroAString);

        oneAString.addTransition('a', twoAString);
        oneAString.addTransition('b', oneAString);

        twoAString.addTransition('a', threeAString);
        twoAString.addTransition('b', twoAString);

        threeAString.addTransition('b', threeAString);

        twoOrThreeADfa.setSourceState(zeroAString);

        return twoOrThreeADfa;
    }
}
