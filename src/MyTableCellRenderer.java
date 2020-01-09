import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

class MyTableCellRender extends DefaultTableCellRenderer {

	public MyTableCellRender() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (Data.startColoring) {
			if (column == Data.iterator + 1) {
				setBackground(Color.yellow);
			} else {
				setBackground(Color.white);
			}
		}

		setText(value != null ? value.toString() : "");
		return this;
	}
}