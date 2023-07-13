import java.io.File;
import java.net.URI;

public class Util {
	private static String workDir;

	public enum OS {
		LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;
	}

	public static OS getPlatform() {
		String str = System.getProperty("os.name").toLowerCase();
		if (str.contains("win"))
			return OS.WINDOWS;
		if (str.contains("mac"))
			return OS.MACOS;
		if (str.contains("solaris"))
			return OS.SOLARIS;
		if (str.contains("sunos"))
			return OS.SOLARIS;
		if (str.contains("linux"))
			return OS.LINUX;
		if (str.contains("unix"))
			return OS.LINUX;
		return OS.UNKNOWN;
	}

	public static String getWorkingDirectory() {
		if (workDir == null)
			workDir = getWorkingDirectory("minecraftfsp");
		return workDir;
	}

	public static String getWorkingDirectory(String paramString) {
		File file;
		String str2, str3, str1 = System.getProperty("user.home", ".");
		switch (getPlatform()) {
		case LINUX:
		case SOLARIS:
			file = new File(str1, '.' + paramString + '/');
			break;
		case WINDOWS:
			str2 = System.getenv("APPDATA");
			str3 = (str2 != null) ? str2 : str1;
			file = new File(str3, '.' + paramString + '/');
			break;
		case MACOS:
			file = new File(str1, "Library/Application Support/" + paramString);
			break;
		default:
			file = new File(str1, paramString + '/');
			break;
		}

		if (!file.exists() && !file.mkdirs())
			throw new RuntimeException(
					"The working directory could not be created: " + file);
		return file.getAbsolutePath();
	}

	public static void openLink(URI uri) {
		try {
			Object o = Class.forName("java.awt.Desktop")
					.getMethod("getDesktop", new Class[0])
					.invoke(null, new Object[0]);
			o.getClass().getMethod("browse", new Class[] { URI.class })
					.invoke(o, new Object[] { uri });
		} catch (Throwable e) {
			System.out.println("Failed to open link " + uri.toString());
			System.out.println("This is unsupported in Java 5.");
		}
	}
}
