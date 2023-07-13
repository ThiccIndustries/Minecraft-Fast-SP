import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class MainPanel extends JPanel{
	
	public JButton play, settings, cape, skin;

	public LauncherFrame launcher;
	
	public MainPanel(LauncherFrame lf) {
		launcher = lf;
			
		GridLayout l = new GridLayout(2, 2);
		l.setVgap(5);
		l.setHgap(5);
		setLayout(l);
		
		setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 26));
		
		cape = new JButton("Set Cape");
		skin = new JButton("Set Skin");
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
		
		add(skin);
		add(settings);
		add(cape);
		add(play);
		
		cape.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser chooser = new JFileChooser(System.getProperty("user.home","."));
		    	FileFilter pngfilter = new FileFilter(){
		    		public boolean accept(File f){
		    	        if(f.isDirectory()) return true;
		    	        else if(f.getName().endsWith(".png")) return true;
		    	            else return false;
		    	    }
		    	    public String getDescription(){
		    	        return "Cape files";
		    	    }
		    	};
		    	
		    	chooser.setFileFilter(pngfilter);
		    	if(chooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION){
		    		boolean res = launcher.GetPackageInstaller().CopyFile(chooser.getSelectedFile().getAbsolutePath(), launcher.GetPackageInstaller().GetGameDirectory() + "/cape.png");
		    		if(res)
		    			System.out.println("Selected cape file: " + chooser.getSelectedFile().getAbsolutePath());
		    		else
		    			System.out.println("Unable to copy cape file: " + chooser.getSelectedFile().getAbsolutePath());
		    	}
		    }
		});
		
		skin.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser chooser = new JFileChooser(System.getProperty("user.home","."));
		    	FileFilter pngfilter = new FileFilter(){
		    		public boolean accept(File f){
		    	        if(f.isDirectory()) return true;
		    	        else if(f.getName().endsWith(".png")) return true;
		    	            else return false;
		    	    }
		    	    public String getDescription(){
		    	        return "Skin files";
		    	    }
		    	};
		    	
		    	chooser.setFileFilter(pngfilter);
		    	if(chooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION){
		    		boolean res = launcher.GetPackageInstaller().CopyFile(chooser.getSelectedFile().getAbsolutePath(), launcher.GetPackageInstaller().GetGameDirectory() + "/skin.png");
		    		if(res)
		    			System.out.println("Selected skin file: " + chooser.getSelectedFile().getAbsolutePath());
		    		else
		    			System.out.println("Unable to copy skin file: " + chooser.getSelectedFile().getAbsolutePath());
		    	}
		    }
		});
		
		setVisible(true);
		setOpaque(false);
	}
}
