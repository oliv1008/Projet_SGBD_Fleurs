package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JLog extends JPanel {

	private JTextArea textArea = new JTextArea(14, 20);

	public JLog() {
		setPreferredSize(new Dimension(240, 230));
		add(new JScrollPane(textArea));
		setVisible(true);
	}

	public void append(String data) {
		textArea.append(data + "\n");
		this.validate();
	}

	public void clear() {
		textArea.setText("");
	}
}
