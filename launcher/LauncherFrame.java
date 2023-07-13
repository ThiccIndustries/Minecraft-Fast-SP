
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class LauncherFrame extends JFrame {

	private ArrayList<Component> components = new ArrayList<Component>();
	public MainPanel mp;
	private SettingsFrame settings;
	private PackageInstaller pi;
	private PrintStream defaultOut;
	private PrintStream defaultOutErr;
	
	public LauncherFrame() {
		// Setup window
		super("Minecraft Fast SP");
        Util.getWorkingDirectory();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("!!! Failed to set look and feel !!!");
		}
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		Container c = getContentPane();
		c.setPreferredSize(new Dimension(854, 480));
		
		c.setBackground(Color.BLACK);
		ImageIcon img = new ImageIcon(LauncherFrame.class.getResource("resources/favicon.png"));
		setIconImage(img.getImage());
		setLayout(new BorderLayout());

		TexturedPanel tp = new TexturedPanel("resources/dirt.png");
		LogoPanel lp = new LogoPanel();
		ConsolePanel cp = new ConsolePanel("resources/stone.png");
		
		defaultOut = System.out;
		defaultOutErr = System.err;
		
		PrintStream ps = cp.GetPrintStream();
		System.setOut(ps);
		System.setErr(ps);
		
		float curHeap = (float)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
		System.out.println("Java version: " + System.getProperty("java.version"));
		System.out.println("Memory allocation: " + curHeap + " MB");
		
		if(curHeap < 120)
			System.out.println("!!! Your computer does not have enough memory, game may crash. !!!");
		
		mp = new MainPanel(this);

		components.add(cp);
		components.add(tp);
		components.add(lp);
		components.add(mp);

		settings = new SettingsFrame(this);

		add(tp, BorderLayout.PAGE_END);
		tp.add(lp, BorderLayout.WEST);
		add(cp, BorderLayout.CENTER);
		tp.add(mp, BorderLayout.EAST);
		getRootPane().setDefaultButton(mp.play);
		pack();
		
		setLocationRelativeTo(null);
		
		// Close button behavior
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		setVisible(true);

		pi = new PackageInstaller(Util.getWorkingDirectory());
		File resFile = new File(Util.getWorkingDirectory() + "/resources");
		
		boolean resources = (resFile.exists() && resFile.isDirectory() && resFile.listFiles().length > 2);
		boolean installed = pi.VerifyInstallation("");

		if(!resources || !installed){
			mp.play.setEnabled(false);
			mp.play.setText("Working...");
		}
		
		if (!installed) {
			System.out.println("Game not installed. Installing default package.");

			if (!pi.ExtractJarResource("resources/DefaultPackage.zip", "/temp", "pkg.zip")) {
				System.out.println("!!! Failed to extract package from jar !!!");
				System.out.println("!!! Must install package from settings !!!");
				installed = false;
			}

			else if (!pi.Install("/temp/pkg.zip")) {
				System.out.println("!!! Must install package from settings !!!");
				installed = false;
			}
			
			else
				installed = true;
			
		}
		
		System.out.println("Game Directory: " + Util.getWorkingDirectory());
		if (pi.VerifyInstallation("/previous/"))
			System.out.println("Backup installation found.");

		if(!resources){
			System.out.println("Resources appear to be missing.");
			if(!pi.InstallResources())
				System.out.println("!!! Failed to install resources, sounds will be missing !!!");
		}
		
		if(installed){
			System.out.println("Game installation found, ready to launch.");
		}
		System.out.println("Please report any issues to: http://github.com/ThiccIndustries/Minecraft-Fast-SP/issues");
		mp.play.setEnabled(installed);
		mp.play.setText("Play Game");
	}

	public void LaunchGame(String username) {
		if (settings.GetSeparate()) {
			settings.setVisible(false);
			mp.play.setEnabled(false);
			mp.settings.setEnabled(false);
			GameJava gl = new GameJava(Util.getWorkingDirectory(), settings.GetJVMArgs().split(" "),
					System.out, mp.play, mp.settings, settings.GetConsole());
			gl.Launch();
		} else {

			System.setOut(defaultOut);
			System.setErr(defaultOutErr);
			settings.setVisible(false);

			for (Component c : components) {
				remove(c);
			}

			GameApplet ap = new GameApplet(Util.getWorkingDirectory(), username);
			add(ap);
			pack();
			ap.Launch();
		}
	}
	
	public static void setMinSize(final Component c, int width, int height){
		c.setMinimumSize(new Dimension(width, height));
		
		c.addComponentListener(new ComponentAdapter(){
	        public void componentResized(ComponentEvent e){
	            Dimension d= c.getSize();
	            Dimension minD= c.getMinimumSize();
	            if(d.width<minD.width)
	                d.width=minD.width;
	            if(d.height<minD.height)
	                d.height=minD.height;
	            c.setSize(d);
	        }
	    });
	}

	public void ShowSettings() {
		settings.setVisible(true);
	}

	public PackageInstaller GetPackageInstaller() {
		return pi;
	}
}
