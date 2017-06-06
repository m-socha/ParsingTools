import java.util.List;
import java.util.Objects;
import java.util.Observer;

/**
 * Created by michael on 5/30/17.
 */
public abstract class Parser {

    public static class Token {
        private String mTokenId;
        private String mLexeme;

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

    public static class ParseTree {
        private String mElementId;
        private Token mToken;
        private List<ParseTree> mChildren;
        private boolean mParsingSuccessful;

        private ParseTree(String elementId, Token token, List<ParseTree> children, boolean parsingSuccessful) {
            mElementId = elementId;
            mToken = token;
            mChildren = children;
            mParsingSuccessful = parsingSuccessful;
        }

        public ParseTree(Token token) {
            this(null, token, null, true);
        }

        public ParseTree(String elementId, List<ParseTree> children) {
            this(elementId, null, children, true);
        }

        public ParseTree() {
            this(null, null, null, false);
        }

        public String getId() {
            return mElementId != null ? mElementId : mToken.getTokenId();
        }

        public List<ParseTree> getChildren() {
            return mChildren;
        }

        public boolean parsingSuccessful() {
            return mParsingSuccessful;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ParseTree) {
                ParseTree parseTree = (ParseTree) o;
                return Objects.equals(mElementId, parseTree.mElementId)
                        && Objects.equals(mToken, parseTree.mToken)
                        && Objects.equals(mChildren, parseTree.mChildren)
                        && mParsingSuccessful == parseTree.mParsingSuccessful;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hashCode = 1;

            if (mElementId != null) {
                hashCode += 31 * hashCode + mElementId.hashCode();
            }

            if (mToken != null) {
                hashCode += 31 * hashCode + mToken.hashCode();
            }

            if (mChildren != null) {
                hashCode += 31 * hashCode + mChildren.hashCode();
            }

            hashCode += 31 * Boolean.valueOf(mParsingSuccessful).hashCode();

            return hashCode;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("{\n");

            if (!mParsingSuccessful) {
                builder.append("\tParsing failed\n");
            } else {
                builder.append("\tid: " + getId() + "\n");
                if (mToken != null) {
                    builder.append("\tlexeme: " + mToken.getLexeme() + "\n");
                } else {
                    for (ParseTree parseTree : mChildren) {
                        builder.append("\t" + parseTree.toString().replace("\n", "\n\t"));
                        builder.setLength(builder.length() - 1);
                    }
                }
            }

            builder.append("}\n");

            return builder.toString();
        }
    }

    public abstract ParseTree parse(List<Token> tokens, ContextFreeGrammar grammar);
}
