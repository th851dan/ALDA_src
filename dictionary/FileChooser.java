package dictionary;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class FileChooser extends JFrame implements ActionListener{
	
	JButton openButton = new JButton("Datei auswählen...");
	JTextArea text;
	JFileChooser fc;
	
	FileChooser() {
		setTitle("Öffne eine Datei");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		text = new JTextArea(20, 80);
		text.setMargin(new Insets(5, 5, 5, 5));
		text.setEditable(true);
		JScrollPane logScrollPane = new JScrollPane(text);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		openButton.addActionListener(this);
		add(buttonPanel, BorderLayout.SOUTH);
		add(logScrollPane, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				text.append("Opening " + file.getName() + ".\n");
			} else {
				text.append("Open command cancelled by user.\n");
			}
			text.setCaretPosition(text.getDocument().getLength());
		}
	}

}
