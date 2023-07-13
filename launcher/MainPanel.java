import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	
	public JButton play;
	public JButton settings;

	public LauncherFrame launcher;
	
	public MainPanel(LauncherFrame lf) {
		launcher = lf;
			
		GridLayout l = new GridLayout(2, 2);
		l.setVgap(5);
		l.setHgap(5);
		setLayout(l);
		
		setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 26));
		
		play = new JButton("Play Game");
		settings = new JButton("Settings / Update");
		
		play.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        launcher.LaunchGame("thiccindustries");
		    }
		});
		
		settings.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        launcher.ShowSettings();
		    }
		});
		
		add(settings);
		add(play);
		
		setVisible(true);
		setOpaque(false);
	}
}
