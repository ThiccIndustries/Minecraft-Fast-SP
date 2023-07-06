import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ConsolePanel extends TexturedPanel{
	private JTextArea textArea;
	private PrintStream printStream;
	public ConsolePanel(String res) {
		super(res);
		
		setLayout(new GridLayout(1,1));
		
		JScrollPane jsp = new JScrollPane();
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setForeground(Color.white);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setLineWrap(true);
		textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		jsp = new JScrollPane(textArea);
		jsp.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setBorder(null);
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		add(jsp);
		
		printStream = new PrintStream(new TextAreaStream(textArea));
		setVisible(true);
	}
	
	public PrintStream GetPrintStream(){
		return printStream;
	}
}
