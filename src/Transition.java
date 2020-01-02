import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transition {
	private State nextState;

	public Transition(State nextState) {
		this.nextState = nextState;
	}

	public State getNextState() {
		return nextState;
	}
}