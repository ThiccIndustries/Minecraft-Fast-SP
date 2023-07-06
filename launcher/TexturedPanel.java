import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TexturedPanel extends JPanel {
	private Image img;
	private Image bgImage;

	public TexturedPanel(String res) {
		setVisible(true);
		setOpaque(true);
		setLayout(new BorderLayout());
		try {
			this.bgImage = ImageIO.read(TexturedPanel.class.getResource(res)).getScaledInstance(32, 32, 16);
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
		
		
	}

	public void makeImage(){
		int i = getWidth() / 2 + 1;
		int j = getHeight() / 2 + 1;
		this.img = createImage(i, j);
		Graphics graphics = this.img.getGraphics();
		for (byte b = 0; b <= i / 32; b++) {
			for (byte b1 = 0; b1 <= j / 32; b1++)
				graphics.drawImage(this.bgImage, b * 32, b1 * 32, null);
		}
		if (graphics instanceof Graphics2D) {
			Graphics2D graphics2D = (Graphics2D) graphics;
			int k = 1;
			graphics2D.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(553648127, true),
					new Point2D.Float(0.0F, k), new Color(0, true)));
			graphics2D.fillRect(0, 0, i, k);
			k = j;
			graphics2D.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0, true),
					new Point2D.Float(0.0F, k), new Color(1610612736, true)));
			graphics2D.fillRect(0, 0, i, k);
		}
		graphics.dispose();
	}
	
	public void update(Graphics paramGraphics) {
		super.update(paramGraphics);
		paint(paramGraphics);
	}

	public void paintComponent(Graphics paramGraphics) {
		super.paintComponent(paramGraphics);
		
		
			makeImage();
		
		int i = getWidth() / 2 + 1;
		int j = getHeight() / 2 + 1;
		
		if(img == null || img.getHeight(null) != j || img.getWidth(null)!= i);
			
		paramGraphics.drawImage(this.img, 0, 0, i * 2, j * 2, null);
	}
}
