import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Michael Socha on 5/9/17.
 */
public class DFA {

    public static class State {
        private String mTokenId;
        private boolean mIsAccepting;
        private String mDescription;
        private Map<Character, State> mTransitions = new TreeMap();

        public State(String tokenId, boolean isAccepting, String description) {
            mTokenId = tokenId;
            mIsAccepting = isAccepting;
            mDescription = description;
        }

        public State(String tokenId, boolean isAccepting) {
            this(tokenId, isAccepting, null);
        }

        public void addTransition(char transitionChar, State nextState) {
            mTransitions.put(transitionChar, nextState);
        }

        public State getNextState(char transitionChar) {
            return mTransitions.get(transitionChar);
        }

        public String getTokenId() {
            return mTokenId;
        }

        public boolean isAcceptingState() {
            return mIsAccepting;
        }

        public String getDescription() {
            return mDescription;
        }
    }

    private List<State> mStates = new ArrayList();
    private State mSourceState;
    private State mCurrentState;

    public DFA() {}

    public void addState(State state) {
        mStates.add(state);
    }

    public void start() {
        if (mSourceState != null) {
            mCurrentState = mSourceState;
        } else {
            throw new IllegalStateException("DFA has no source state.");
        }
    }

    public boolean transition(char transitionChar) {
        mCurrentState = mCurrentState.getNextState(transitionChar);
        return mCurrentState != null;
    }

    public void setSourceState(State state) {
        mSourceState = state;
    }

    public State getCurrentState() {
        return mCurrentState;
    }
}
