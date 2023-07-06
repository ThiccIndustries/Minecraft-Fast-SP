import java.util.ArrayList;

public class Launcher {
	
	public static void main(String[] args) throws Exception{
		//Get more ram if heap is too small
		float curHeap = (float)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
		System.out.println("Heap size: " + curHeap);
		System.out.println(Runtime.getRuntime().totalMemory() / 1024L / 1024L);
	    if (curHeap < 120.0F) {
	    	
	    	System.out.println("Heap size too low, setting to 512M");
	    	String str = LauncherFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
	    	ArrayList<String> params = new ArrayList<String>();
	    	if (Util.getPlatform().equals(Util.OS.WINDOWS)) {
	        	params.add("javaw");
	        } else {
	        	params.add("java");
	        } 
	    	params.add("-Xmx512M");
	    	params.add("-Dsun.java2d.noddraw=true");
	    	params.add("-Dsun.java2d.d3d=false");
	    	params.add("-Dsun.java2d.opengl=false");
	    	params.add("-Dsun.java2d.pmoffscreen=false");
	    	params.add("-classpath");
	    	params.add(str);
	    	params.add("Launcher");
	    	
	    	ProcessBuilder processBuilder = new ProcessBuilder(params);
        	Process process = processBuilder.start();
        	if (process == null){
        		new LauncherFrame();
        	}
        	System.exit(0);
	    }
	   
		new LauncherFrame();
	}	
}
