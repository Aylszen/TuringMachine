import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {
	Transition transitions;
	private String name;
	private Map<String, String> valuesMap = new HashMap<String, String>();
	private Map<String, Transition> transitionsMap = new HashMap<String, Transition>();
	private Map<String, String> movementDirectionMap = new HashMap<String, String>();
	public State() {

	}

	public State(String name, int value) {
		this.name = name;
		}

	public void setTransitions(String inputString) {

		if (inputString.length() != 0) {
			String[] stringAfterSpaceSplit = inputString.split(" ");
			for (String stringElem : stringAfterSpaceSplit) {
				String[] stringAfterSemicolonSplit = stringElem.split(";");
				String[] stringAfterCommaSplit = stringAfterSemicolonSplit[1].split(",");
				valuesMap.put(stringAfterSemicolonSplit[0], stringAfterCommaSplit[0]);
				transitionsMap.put(stringAfterSemicolonSplit[0], new Transition(Data.listAllStatesMap.get(stringAfterCommaSplit[1])));
				movementDirectionMap.put(stringAfterSemicolonSplit[0], stringAfterCommaSplit[2]);
			}
		}
	}

	public State goToNextState(String string) {
		return transitionsMap.get(string).getNextState();
	}
	
	public char getValue(String string)
	{
		char value = valuesMap.get(string).toCharArray()[0];
		if (value == "-".toCharArray()[0])
		{
			char emptyValue = 0;
			return emptyValue;
		}
		return valuesMap.get(string).toCharArray()[0];
	}

	public String getMovementDirection(String string)
	{
		return movementDirectionMap.get(string);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
