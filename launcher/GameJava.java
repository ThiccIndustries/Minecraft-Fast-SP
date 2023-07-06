import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JButton;

public class GameJava {

	private String GameDirectory;
	private String[] args;
	private OutputStream output;
	
	private JButton b1, b2;
	private boolean keepLauncherProcess;
	
	private static String defaultArgs = "-Xmx512M";
	
	public GameJava(String dir, String[] args, OutputStream output, JButton b1, JButton b2, boolean keepLauncherProcess) {
		GameDirectory = dir;
		this.args = args;
		this.output = output;
		
		this.b1 = b1;
		this.b2 = b2;
		this.keepLauncherProcess = keepLauncherProcess;
	}

	public void Launch() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add("java");
		params.add("-classpath");
		params.add(BuildClasspath());
		params.add(BuildNativesParam());
		
		if(args.length == 1 && args[0].equals("")){
			params.add(defaultArgs);
		}else{
		for(String arg : args)
			if(!arg.equals(""))
				params.add(arg);
		}
			
		params.add("net.minecraft.client.Minecraft");
		
		System.out.println("Java command: ");
		for(String param : params) {
			System.out.print(param + " ");
		}
		
		System.out.println("\n\n");
		Thread mcThread = new Thread() {
			public void run() {
				try {
					ProcessBuilder processBuilder = new ProcessBuilder(params);
					Process process = processBuilder.start();
					InputStream is = process.getInputStream();
					
					if(process == null){
						System.out.println("!!! Process launch failed. !!!");
						return;
					}
					
					b1.setText("Playing...");
					
					if(!keepLauncherProcess)
						System.exit(0);
					
					int read;
					byte[] buffer = new byte[1024];
					while((read = is.read(buffer)) > -1){
						output.write(buffer, 0, read);
					}
					
					System.out.println("Minecraft process exited with status: " + process.exitValue());
					
					b1.setText("Play Game");
					b1.setEnabled(true);
					b2.setEnabled(true);
					
				}catch(Exception e) {
					//Failed to start
					return;
				}
			}
		};
		mcThread.start();
	}

	private String BuildClasspath() {
		String[] jars = new String[] { "minecraft.jar", "lwjgl.jar", "lwjgl_util.jar", "jinput.jar" };

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < jars.length - 1; ++i) {
			if (Util.getPlatform() == Util.OS.WINDOWS)
				sb.append(GameDirectory + "\\bin\\" + jars[i] + ";");
			else
				sb.append(GameDirectory + "/bin/" + jars[i] + ":");
		}

		return sb.toString();
	}

	private String BuildNativesParam() {
		StringBuilder sb = new StringBuilder();
		sb.append("-Djava.library.path=");
		sb.append(GameDirectory);
		if (Util.getPlatform() == Util.OS.WINDOWS)
			sb.append("\\bin\\natives");
		else
			sb.append("/bin/natives");

		return sb.toString();
	}
}
