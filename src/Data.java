import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Data {

	static Map<String, State> listAllStatesMap = new HashMap<String, State>();
	static String startingState = new String();
	static ArrayList<State> acceptingStates = new ArrayList<State>();
	static ArrayList<String> availableSymbols = new ArrayList<String>();
	static String tape = new String();
	static String orderStart = new String();
	static boolean isBegin = true;
	static State currentState;
	static int iterator;
	static int tapeLength;
	public void setInitialValues() {
		// Setting up states
		createStates(5);
		// Setting starting state
		startingState = "q0";

		// Setting available symbols
		String[] availableSymbols = { "0", "1" };
		setAvailableSymbols(availableSymbols);

		// Setting accepting states
		addAcceptingStates(listAllStatesMap.get("q3"));

		setTransitions("q0", "0;1,q1,L 1;0,q2,L ~;1,q1,L");
		setTransitions("q1", "0;1,q3,L 1;0,q4,L ~;1,q3,-");
		setTransitions("q2", "0;0,q4,L 1;1,q4,L ~;0,q4,L");
		setTransitions("q3", "0;0,q3,L 1;1,q3,L ~;-,q3,-");
		setTransitions("q4", "0;1,q3,L 1;0,q4,L ~;1,q3,-");
		
		currentState = listAllStatesMap.get(startingState);
		iterator = 0;
	}

	public void createStates(int numberOfStates) {
		for (int i = 0; i < numberOfStates; i++) {
			String stateName = "q" + i;
			listAllStatesMap.put(stateName, new State(stateName, i));
		}
	}

	public void setAvailableSymbols(String[] symbols) {
		for (String symbol : symbols) {
			availableSymbols.add(symbol);
		}
	}

	public void setTransitions(String stateName, String allTransitions) {
		listAllStatesMap.get(stateName).setTransitions(allTransitions);
	}

	public void addAcceptingStates(State acceptingState) {
		acceptingStates.add(acceptingState);
	}

	public static void printInputData(String[] inputDataStringTable) {
		System.out.println("Input data:");
		for (String inputDataStringElem : inputDataStringTable) {
			System.out.println(inputDataStringElem);
		}
		System.out.println("#####");

	}

	public static String reverseString(String input) {
		byte[] strAsByteArray = input.getBytes();

		byte[] result = new byte[strAsByteArray.length];

		// Store result in reverse order into the
		// result byte[]
		for (int i = 0; i < strAsByteArray.length; i++)
			result[i] = strAsByteArray[strAsByteArray.length - i - 1];

		return new String(result);
	}
}
