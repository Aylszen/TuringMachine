import java.util.Scanner;

public class TuringMachine {

	public static void main(String[] args) {
		Data data = new Data();
		data.setInitialValues();
		data.orderStart = "R";
		start();
	}

	public static void start() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number:");
		Data.tape = sc.next();
		int tapeLength = Data.tape.length();
		State state = Data.listAllStatesMap.get(Data.startingState);
		String automatonPath = new String();
		automatonPath += "(" + state.toString() + ")-->";
		if (Data.orderStart.contentEquals("R") == true) {
			String reverseTape = Data.reverseString(Data.tape);
			char[] reverseTapeCharArray = reverseTape.toCharArray();
			System.out.println("Current state: " + state);
			for (int i = 0; i < tapeLength;) {

				String movementDirection = state.getMovementDirection(String.valueOf(reverseTapeCharArray[i]));
				System.out.println("Data: " + reverseTape.charAt(i));
				char newValue = state.getValue(String.valueOf(reverseTapeCharArray[i]));
				state = state.goToNextState(String.valueOf(reverseTapeCharArray[i]));
				reverseTapeCharArray[i] = newValue;
				System.out.println("Data changed: " + reverseTapeCharArray[i]);
				System.out.println("Current state: " + state);

				if (Data.acceptingStates.contains(state)) {
					automatonPath += "((" + state.toString() + "))-->";
				} else {
					automatonPath += "(" + state.toString() + ")-->";
				}
				
				if (movementDirection.contains("L")) {
					i++;
				} else if (movementDirection.contains("R")) {
					i--;
				} else {
					break;
				}
			}
			reverseTape = Data.reverseString(String.valueOf(reverseTapeCharArray));
			System.out.println("Tape: " + reverseTape);
			System.out.println(automatonPath);

		}
	}
}
