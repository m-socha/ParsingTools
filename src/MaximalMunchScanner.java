import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/21/17.
 */
public class MaximalMunchScanner extends Scanner {

    public TokenizedString tokenize(DFA dfa, String s) {
        List<Token> tokens = new ArrayList();
        DFA.State lastAcceptingState = null;
        int tokenBeginIndex = 0;
        int tokenLength = 0;

        dfa.start();
        while (tokenBeginIndex != s.length()) {
            for (int i = 0; i < s.length() - tokenBeginIndex; i++) {
                char transChar = s.charAt(tokenBeginIndex + i);
                if (dfa.transition(transChar)) {
                    if (dfa.getCurrentState().isAcceptingState()) {
                        lastAcceptingState = dfa.getCurrentState();
                        tokenLength = i + 1;
                    }
                } else {
                    break;
                }
            }

            if (lastAcceptingState != null) {
                String tokenLexeme = s.substring(tokenBeginIndex, tokenBeginIndex + tokenLength);
                tokens.add(new Token(lastAcceptingState.getTokenId(), tokenLexeme));

                tokenBeginIndex = tokenBeginIndex + tokenLength;
                tokenLength = 0;
                lastAcceptingState = null;
                dfa.start();
            } else {
                return new TokenizedString(tokens, false);
            }
        }

        return new TokenizedString(tokens, true);
    }
}
