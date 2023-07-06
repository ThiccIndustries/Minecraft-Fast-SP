import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LogoPanel extends JPanel {
	private Image bgImage;

	public LogoPanel() {
		setOpaque(true);
		try {
			BufferedImage bufferedImage = ImageIO.read(LogoPanel.class.getResource("resources/logo.png"));
			int i = bufferedImage.getWidth();
			int j = bufferedImage.getHeight();
			this.bgImage = bufferedImage.getScaledInstance(i, j, 16);
			setPreferredSize(new Dimension(i + 32, j + 32));
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}

	public void update(Graphics paramGraphics) {
		paint(paramGraphics);
	}

	public void paintComponent(Graphics paramGraphics) {
		paramGraphics.drawImage(this.bgImage, 16, 16, null);
	}
}