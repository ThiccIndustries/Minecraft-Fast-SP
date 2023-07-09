import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class SettingsFrame extends JDialog{
	private JCheckBox separate;
	private JCheckBox console;
	private JButton restore;
	private JButton install;
	private JTextArea jvmOptions;
	private LauncherFrame launcher;
	private JLabel label;
	
	public SettingsFrame(LauncherFrame l) {
		this.launcher = l;
		
		setTitle("Minecraft Fast SP Options");
		setResizable(false);
		
		JPanel upper = new JPanel();
		JPanel lower = new JPanel();
		
		upper.setLayout(new FlowLayout());
		lower.setLayout(new FlowLayout());
		
		setLayout(new BorderLayout());;
		setAlwaysOnTop(true);
		install = new JButton("Install Package");
		restore = new JButton("Restore Backup");
		
		separate = new JCheckBox("Separate Java Process");
		console = new JCheckBox("Keep console open");
		
		console.setSelected(false);
		separate.setSelected(false);
		
		console.setEnabled(false);

		jvmOptions = new JTextArea("");
		jvmOptions.setEnabled(false);
		
		JScrollPane jvmOptionsScrollPane = new JScrollPane(jvmOptions);
		jvmOptionsScrollPane.setPreferredSize(new Dimension(380, 40));
		jvmOptionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jvmOptionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		install.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser chooser = new JFileChooser(System.getProperty("user.home","."));
		    	setAlwaysOnTop(false);
		    	FileFilter zipfilter = new FileFilter(){
		    	    public boolean accept(File f){
		    	        if(f.isDirectory()) return true;
		    	        else if(f.getName().endsWith(".zip")) return true;
		    	            else return false;
		    	    }
		    	    public String getDescription(){
		    	        return "Update Packages";
		    	    }
		    	};
		    	
		    	chooser.setFileFilter(zipfilter);
		    	if(chooser.showDialog(null, "Install") == JFileChooser.APPROVE_OPTION) {
		    		launcher.GetPackageInstaller().Install(chooser.getSelectedFile().getAbsolutePath());
		    		restore.setEnabled(launcher.GetPackageInstaller().VerifyInstallation("/previous/"));
		    	}
		    	setAlwaysOnTop(true);
		    	launcher.mp.play.setEnabled(launcher.GetPackageInstaller().VerifyInstallation(""));
		    }
		});
		
		separate.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	jvmOptions.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
		    	label.setText(jvmOptions.isEnabled() ? "Java command arguements:" : "Java command arguements: (Disabled)");
		    	console.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
		    }
		});
		
		restore.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	launcher.GetPackageInstaller().RestoreBackup();
		    	restore.setEnabled(launcher.GetPackageInstaller().VerifyInstallation("/previous/"));
		    	launcher.mp.play.setEnabled(launcher.GetPackageInstaller().VerifyInstallation(""));
		    }
		});
		

		upper.add(install);
		upper.add(restore);
		
		JLabel updateDir = new JLabel("<html>For updates: <a href='.'>http://github.com/ThiccIndustries/Minecraft-Fast-SP/releases");
		upper.add(updateDir);
		
		updateDir.setHorizontalAlignment(JLabel.CENTER);
		updateDir.setPreferredSize(new Dimension(400, 16));
		
		updateDir.addMouseListener(new MouseListener(){
		    public void mouseClicked(MouseEvent e) {
		        try {
					Util.openLink(new URL("http://github.com/ThiccIndustries/Minecraft-Fast-SP/releases").toURI());
				} catch (Exception e2) {
					System.out.println("Failed to open link.");
				}
		    }
		    
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		upper.add(updateDir);
		
		upper.add(separate);
		upper.add(console);
		upper.setPreferredSize(new Dimension(100, 85));
		upper.setVisible(true);
		
		add(upper, BorderLayout.NORTH);
		
		lower.add(label = new JLabel("Java command arguements: (Disabled)"));
		lower.add(jvmOptionsScrollPane);
		lower.setVisible(true);
		
		add(lower, BorderLayout.CENTER);
		
		this.getContentPane().setPreferredSize(new Dimension(400, 155));
		pack();
		
		setLocationRelativeTo(null);
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
		try {
			restore.setEnabled(launcher.GetPackageInstaller().VerifyInstallation("/previous/"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean GetSeparate() {
		return separate.isSelected();
	}
	
	public boolean GetConsole(){
		return console.isSelected();
	}
	
	public String GetJVMArgs() {
		return jvmOptions.getText();
	}
}
