
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TuringMachine {

	public static void main(String[] args) {
		Data data = new Data();
		data.setInitialValues();
		
		new GUI();
	}

	public static void start2(String dataTape, JTable jtable, JLabel currentStateLabel) {
		if (Data.orderStart.contentEquals("R") == true) {
				String dataFromTable = String.valueOf(jtable.getValueAt(0, Data.iterator));
				String movementDirection = Data.currentState.getMovementDirection(dataFromTable);
				char newValue = Data.currentState.getValue(dataFromTable);
				State tempNewState = Data.currentState.goToNextState(dataFromTable);
				Data.currentState = tempNewState;
				currentStateLabel.setText(Data.currentState.toString());
				jtable.setValueAt(newValue, 0, Data.iterator);
				if (movementDirection.contains("L")) {
					Data.iterator--;
				} else if (movementDirection.contains("R")) {
					Data.iterator++;
				} else {
					Data.iterator--;
				}
				Data.updateStatePath();
			}
		}
	
	public static void start(String dataTape) {
		Data.tape = dataTape;
		int tapeLength = Data.tape.length();
		State state = Data.listAllStatesMap.get(Data.startingState);
		String automatonPath = new String();
		automatonPath += "(" + state.toString() + ")-->";
		if (Data.orderStart.contentEquals("R") == true) {
			String reverseTape = Data.reverseString(Data.tape);
			char[] reverseTapeCharArray = reverseTape.toCharArray();
			System.out.println("Current state: " + state);
			for (int i = 0; i < tapeLength;) {
				//Thread.sleep(2000);
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
