import parsingtools.DFA;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by michael on 5/9/17.
 */
public class DFATest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testStartWithoutSourceState() {
        DFA sourcelessDfa = new DFA();
        sourcelessDfa.addState(new DFA.State("token1", false));

        expectedException.expect(IllegalStateException.class);
        sourcelessDfa.start();
    }

    @Test
    public void testEvenBinaryStrings() {
        DFA evenBinaryStringDfa = DFAs.getEvenBinaryStringDfa();

        evenBinaryStringDfa.start();

        assertEquals(evenBinaryStringDfa.getCurrentState().getTokenId(), "even");
        assertTrue(evenBinaryStringDfa.getCurrentState().isAcceptingState());

        boolean transitionSuccessful = evenBinaryStringDfa.transition('0');
        assertTrue(transitionSuccessful);
        assertEquals(evenBinaryStringDfa.getCurrentState().getTokenId(), "even");
        assertTrue(evenBinaryStringDfa.getCurrentState().isAcceptingState());

        transitionSuccessful = evenBinaryStringDfa.transition('1');
        assertTrue(transitionSuccessful);
        assertEquals(evenBinaryStringDfa.getCurrentState().getTokenId(), "odd");
        assertFalse(evenBinaryStringDfa.getCurrentState().isAcceptingState());

        transitionSuccessful = evenBinaryStringDfa.transition('1');
        assertTrue(transitionSuccessful);
        assertEquals(evenBinaryStringDfa.getCurrentState().getTokenId(), "odd");
        assertFalse(evenBinaryStringDfa.getCurrentState().isAcceptingState());

        transitionSuccessful = evenBinaryStringDfa.transition('0');
        assertTrue(transitionSuccessful);
        assertEquals(evenBinaryStringDfa.getCurrentState().getTokenId(), "even");
        assertTrue(evenBinaryStringDfa.getCurrentState().isAcceptingState());

        transitionSuccessful = evenBinaryStringDfa.transition('2');
        assertFalse(transitionSuccessful);
    }
}
