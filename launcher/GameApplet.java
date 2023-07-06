import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class GameApplet extends Applet implements AppletStub{
	private static String[] resources = {
			"minecraft.jar",
			"lwjgl.jar",
			"lwjgl_util.jar",
			"jinput.jar"
	};
	
	private String GamePath;
	private Map<String, String> params = new HashMap<String, String>();
	private ClassLoader classloader;
	private Applet applet;
	private boolean active;

	private int context;
	
	public GameApplet(String gamePath, String username) {
		GamePath = gamePath;
		params.put("username",username);
		params.put("sessionid", "bazinga");
		params.put("stand-alone", "true");
		
		setSize(854, 480);
	}
	
	public void Launch() {
		if(classloader == null)
			UpdateClassloader();
		try {
		    System.setProperty("org.lwjgl.librarypath", GamePath + "/bin/natives");
		    System.setProperty("net.java.games.input.librarypath", GamePath + "/bin/natives");
			Class<?> minecraft2 = classloader.loadClass("org.lwjgl.LWJGLException");
			Class<?> minecraft = classloader.loadClass("net.minecraft.client.MinecraftApplet");
			replace((Applet)minecraft.newInstance());
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void replace(Applet applet) {
		this.applet = applet;
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());
		setLayout(new BorderLayout());
		add(applet, "Center");
		applet.init();
		this.active = true;
		applet.start();
		validate();
	}
		  
	public void update(Graphics g) {
		paint(g);
	}
	
	private void UpdateClassloader() {
		try {
			URL[] urls = new URL[resources.length];
			for(int i = 0; i < urls.length; ++i) {
				urls[i] = new File(GamePath + "/bin/" + resources[i]).toURI().toURL();
				System.out.println("URL: " + urls[i].getPath());
			}
			
			classloader = new URLClassLoader(urls);
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public String getParameter(String key) {
		System.out.println(key);
		String res = params.get(key);
		
		if(res != null)
			return res;
		try {
			res = super.getParameter(key);
			return res;
		}catch(Exception e) {
			return null;
		}
	}
	
	public void appletResize(int arg0, int arg1) {}
	public void run() {}
	public URL getDocumentBase() {
		try {
			return new URL("http://www.thiccindustries.com");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	public boolean isActive() {
		if (this.context == 0) {
			this.context = -1;
			try {
				if (getAppletContext() != null)
					this.context = 1;
			} catch (Exception localException) {
			}
		}
		if (this.context == -1)
			return this.active;
		return super.isActive();
	}
}