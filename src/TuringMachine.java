
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TuringMachine {

	public static void main(String[] args) {
		new GUI();
	}

	public static void start2(String dataTape, JTable jtable, JLabel currentStateLabel, Data data) {
		if (data.orderStart.contentEquals("R") == true) {
				String dataFromTable = String.valueOf(jtable.getValueAt(0, data.iterator));
				String movementDirection = data.currentState.getMovementDirection(dataFromTable);
				char newValue = data.currentState.getValue(dataFromTable);
				State tempNewState = data.currentState.goToNextState(dataFromTable);
				data.currentState = tempNewState;
				currentStateLabel.setText(data.currentState.toString());
				jtable.setValueAt(newValue, 0, data.iterator);
				if (movementDirection.contains("L")) {
					data.iterator--;
				} else if (movementDirection.contains("R")) {
					data.iterator++;
				} else {
					data.iterator--;
				}
				data.updateStatePath();
			}
		}
}
