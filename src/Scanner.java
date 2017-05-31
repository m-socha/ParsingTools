import java.util.List;

/**
 * Created by Michael Socha on 5/14/17.
 */
public abstract class Scanner {

    public static class Token {
        private final String mTokenId;
        private final String mLexeme;

        public Token(String tokenId, String lexeme) {
            mTokenId = tokenId;
            mLexeme = lexeme;
        }

        public String getTokenId() {
            return mTokenId;
        }

        public String getLexeme() {
            return mLexeme;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Token) {
                Token token = (Token) o;
                return mLexeme.equals(token.mLexeme) && mTokenId.equals(token.mTokenId);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 31 * mTokenId.hashCode() + mLexeme.hashCode();
        }
    }

    public static class TokenizedString {
        private List<Token> mTokens;
        private boolean mTokenizationComplete;

        public TokenizedString(List<Token> tokens, boolean tokenizationComplete) {
            mTokens = tokens;
            mTokenizationComplete = tokenizationComplete;
        }

        public List<Token> getTokens() {
            return mTokens;
        }

        public boolean tokenizationComplete() {
            return mTokenizationComplete;
        }
    }

    public abstract TokenizedString tokenize(DFA dfa, String s);

}
