import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaStream extends OutputStream {
	private final JTextArea textArea;
	private final StringBuilder sb = new StringBuilder();
	   
	public TextAreaStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void write(int b) throws IOException {

		if (b == '\r')
			return;

		if (b == '\n') {
			final String text = sb.toString() + "\n";
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textArea.append(text);
				}
			});
			sb.setLength(0);

			return;
		}

		sb.append((char) b);
	}
}
