import parsingtools.MaximalMunchScanner;
import parsingtools.Scanner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michael on 5/21/17.
 */
public class MaximalMunchScannerTest {

    @Test
    public void testCharNotInLanguage() {
        Scanner maximalMunchScanner = new MaximalMunchScanner();
        Scanner.TokenizedString tokenizedString = maximalMunchScanner.tokenize(DFAs.getEvenBinaryStringDfa(), "01012010");
        assertFalse(tokenizedString.tokenizationComplete());
    }

    @Test
    public void testNoValidTokens() {
        Scanner maximalMunchScanner = new MaximalMunchScanner();
        Scanner.TokenizedString tokenizedString = maximalMunchScanner.tokenize(DFAs.getEvenBinaryStringDfa(), "1111111");
        assertFalse(tokenizedString.tokenizationComplete());
    }

    @Test
    public void testMunchIsMaximal() {
        Scanner maximalMunchScanner = new MaximalMunchScanner();
        Scanner.TokenizedString tokenizedString = maximalMunchScanner.tokenize(DFAs.getEvenBinaryStringDfa(), "10101010");
        assertTrue(tokenizedString.tokenizationComplete());

        List<Scanner.Token> expectedTokens = new ArrayList();
        expectedTokens.add(new Scanner.Token("even", "10101010"));
        assertEquals(expectedTokens, tokenizedString.getTokens());
    }

    @Test
    public void testMaximalityCausingTokenizationFailure() {
        Scanner maximalMunchScanner = new MaximalMunchScanner();
        Scanner.TokenizedString tokenizedString = maximalMunchScanner.tokenize(DFAs.getTwoOrThreeADfa(), "abaababaaa");
        assertFalse(tokenizedString.tokenizationComplete());
    }

    @Test
    public void testMultiTokenScan() {
        Scanner maximalMunchScanner = new MaximalMunchScanner();
        Scanner.TokenizedString tokenizedString = maximalMunchScanner.tokenize(DFAs.getTwoOrThreeADfa(), "abaababaaaa");
        assertTrue(tokenizedString.tokenizationComplete());

        List<Scanner.Token> expectedTokens = new ArrayList();
        expectedTokens.add(new Scanner.Token("three_a", "abaab"));
        expectedTokens.add(new Scanner.Token("three_a", "abaa"));
        expectedTokens.add(new Scanner.Token("two_a", "aa"));
        assertEquals(expectedTokens, tokenizedString.getTokens());
    }
}
