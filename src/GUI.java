import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUI {
	static JTextField dataTextField;
	static JFrame jFrame;
	static JButton submitButton;
	static JButton oneStepButton;
	static JButton automaticButton;
	static JLabel enteredDataLabel;
	static JLabel dataLabel;
	static JLabel currentStateLabel;
	static JLabel statePathLabel;
	static JTable dataTable;
	static JPanel firstPanel;
	static JPanel secondPanel;
	static JPanel thirdPanel;
	static JScrollPane sp;
	static JPanel fourthPanel;
	static JPanel fifthPanel;
	static DefaultTableModel dm;
	static int place;
	static boolean isRunning = false;

	public GUI() {
		Data data = new Data();
		data.setInitialValues();
		jFrame = new JFrame("Turing Machine");
		enteredDataLabel = new JLabel("No Data entered");
		dataLabel = new JLabel("Number:");
		currentStateLabel = new JLabel();
		dataTextField = new JTextField();
		submitButton = new JButton("Submit");
		oneStepButton = new JButton("One step");
		automaticButton = new JButton("Automatic");
		firstPanel = new JPanel();
		secondPanel = new JPanel();
		thirdPanel = new JPanel();
		fourthPanel = new JPanel();
		fifthPanel = new JPanel();
		dm = new DefaultTableModel(0, 0);
		dataTable = new JTable();
		statePathLabel = new JLabel();
		dataTable.setDefaultRenderer(Object.class, new MyTableCellRender());

		firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.PAGE_AXIS));
		firstPanel.add(dataTextField, BorderLayout.NORTH);

		secondPanel.setLayout(new GridLayout(1, 3));
		secondPanel.add(submitButton);
		secondPanel.add(dataLabel);
		dataLabel.setHorizontalAlignment(JLabel.CENTER);
		secondPanel.add(enteredDataLabel);

		thirdPanel.setLayout(new GridLayout(1, 3));
		thirdPanel.add(currentStateLabel);
		thirdPanel.add(oneStepButton);
		thirdPanel.add(automaticButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enteredDataLabel.setText(dataTextField.getText());
				String tempTapeString = dataTextField.getText();
				if (Data.orderStart.contentEquals("R") == true) {
					if (tempTapeString.length() == 1 && tempTapeString.contains("1")) {
						Data.tape = "~~" + tempTapeString;
						Data.tapeLength = Data.tape.length();
					} else {
						Data.tape = "~" + tempTapeString;
						Data.tapeLength = Data.tape.length();
					}
				} else if (Data.orderStart.contentEquals("L") == true) {
					//
				}
				fillUpTable(Data.tape);
				Data.currentState = Data.listAllStatesMap.get(Data.startingState);
				if (Data.orderStart.contentEquals("R") == true) {
					Data.iterator = Data.tapeLength - 1;
				} else if (Data.orderStart.contentEquals("L") == true) {
					Data.iterator = 0;
				}
				currentStateLabel.setText(Data.currentState.toString());
				Data.statePath = "(" + Data.currentState.toString() + ")-->";
				Data.startColoring = true;
				statePathLabel.setText("");
				isRunning = false;
			}
		});

		automaticButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRunning = true;
				Thread thread = new Thread(new Runnable() {
					public void run() {
						int sleepTime = 4000;
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						while (isRunning) {
							if (Data.iterator >= 0 && Data.iterator < Data.tapeLength) {
								TuringMachine.start2(enteredDataLabel.getText(), dataTable, currentStateLabel, data);
								refreshColors();
								if (!(Data.iterator >= 0 && Data.iterator < Data.tapeLength)) {
									statePathLabel.setText(Data.statePath);
									statePathLabel.revalidate();
									fifthPanel.revalidate();
								}
								try {
									Thread.sleep(sleepTime);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								isRunning = false;
							}
						}
					}
				});
				thread.start();
			}
		});

		oneStepButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Data.iterator >= 0 && Data.iterator < Data.tapeLength) {
					TuringMachine.start2(enteredDataLabel.getText(), dataTable, currentStateLabel, data);
					refreshColors();
					if (!(Data.iterator >= 0 && Data.iterator < Data.tapeLength)) {
						statePathLabel.setText(Data.statePath);
						statePathLabel.revalidate();
						fifthPanel.revalidate();
					}
				}

			}
		});

		fourthPanel.setLayout(new BoxLayout(fourthPanel, BoxLayout.PAGE_AXIS));

		dataTable.setTableHeader(null);
		String header[] = new String[] { " ", " " };
		dm.setColumnIdentifiers(header);
		dataTable.setModel(dm);
		fillUpTable("no data");
		sp = new JScrollPane(dataTable);
		fourthPanel.add(sp);

		fifthPanel.setLayout(new GridLayout(1, 1));
		fifthPanel.add(statePathLabel);
		statePathLabel.setHorizontalAlignment(JLabel.CENTER);

		jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.PAGE_AXIS));
		// add panel to frame
		jFrame.add(firstPanel);
		jFrame.add(secondPanel);
		jFrame.add(thirdPanel);
		jFrame.add(fourthPanel);
		jFrame.add(fifthPanel);
		// set the size of frame
		jFrame.setSize(400, 300);

		jFrame.show();
	}

	public void fillUpTable(String data) {
		String[] dataArr = data.split("");
		dm.setColumnIdentifiers(dataArr);
		if (dm.getRowCount() > 0) {
			dm.removeRow(0);
		}
		Vector<Object> rowData = new Vector<Object>();
		for (int i = 0; i < data.length(); i++) {
			rowData.add(data.charAt(i));
		}
		dm.addRow(rowData);
		dm.fireTableDataChanged();
		dataTable.revalidate();
		dataTable.repaint();

		thirdPanel.revalidate();
	}

	public void refreshColors() {
		// To refresh colors...
		for (int i = 0; i < Data.tapeLength; i++) {
			String dataFromTable = String.valueOf(dataTable.getValueAt(0, i));
			dataTable.setValueAt(dataFromTable, 0, i);
		}
		//
	}

}
