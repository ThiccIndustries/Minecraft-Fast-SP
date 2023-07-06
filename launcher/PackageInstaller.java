import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageInstaller {
	private String GameDirectory;
	
	private static String[] natives = {
			"linux_natives.jar",
			"solaris_natives.jar",
			"windows_natives.jar",
			"macosx_natives.jar"
	};
	
	public PackageInstaller(String dir) {
		GameDirectory = dir;
	}
	
	public boolean RestoreBackup() {
		System.out.println("Restoring backup...");
		if(!VerifyInstallation("/previous/")) {
			System.out.println("!!! No backup exists !!!");
			return false;
		}
		
		File bin = new File(GameDirectory, "bin");
		File bkp = new File(GameDirectory, "bin/previous");
		
		for(File f : bin.listFiles()) {
			if(f.equals(bkp))
				continue;
			DeleteRecursive(f);
		}
		
		for(File f : bkp.listFiles()) {
			System.out.println(f.getName() + " --> " + GameDirectory + "/bin/");
			if(!CopyFile(f.getAbsolutePath(), GameDirectory + "/bin/" + f.getName())) {
				System.out.println("!!! Failed to copy file !!!");
			}
		}
		
		DeleteRecursive(bkp);
		
		System.out.println("Previous version restored");
		return true;
	}
	
	//Do first time resource install
	public boolean InstallResources(){
		System.out.println("Installing missing sounds...");
		if(!ExtractJarResource("resources/Resources.zip", "/temp", "Resources.zip"))
			return false;
		
		if(!ExtractZip(GameDirectory + "/temp/Resources.zip", GameDirectory + "/resources", false))
			return false;
		
		return true;
	}
	
	public boolean Install(String PkgDirectory) {
		System.out.println("Installing from package: " + PkgDirectory);
		
		if(VerifyInstallation("")) {
			if(!Backup()) {
			System.out.println("!!! Failed to create backup !!!");
			}
		}
		
		File pkgFile = new File(PkgDirectory);
		String PkgName = pkgFile.getName();

		try {
			File tempdir = new File(GameDirectory, "temp");
			
			if(!tempdir.isDirectory())
				tempdir.delete();
		
			if(!tempdir.exists())
				tempdir.mkdirs();
			
			//Copy package to temp directory
			System.out.println("Copying Package...\n     " + PkgName + " --> " + GameDirectory + "/temp/");
			CopyFile(PkgDirectory, GameDirectory + "/temp/" + PkgName);
			//Extract archive to bin
			
			ExtractZip(GameDirectory + "/temp/" + PkgName, GameDirectory + "/bin", true);
			
			//Select natives, delete others
			String plat = Util.getPlatform().name().toLowerCase();
			String native_pkg = null;
			for(String plat_natives : natives) {
				if(plat_natives.toLowerCase().contains(plat)) {
					System.out.println("Selected natives: " + plat_natives);
					native_pkg = GameDirectory + "/bin/" + plat_natives;
				}else {
					System.out.println("Deleting natives: " + plat_natives);
					File native_delete = new File(GameDirectory + "/bin/" + plat_natives);
					native_delete.delete();
				}
			}
			
			System.out.println("Extracting natives...");
			//Unsupported platform
			if(native_pkg == null) {
				System.out.println("!!! No compatible natives package !!!");
				return false;
			}
			
			//Extract selected natives package
			if(!ExtractZip(native_pkg, GameDirectory + "/bin/natives", true)) {
				System.out.println("!!! Failed to install natives package !!!");
				return false;
			}
			
			tempdir.delete();
			
			System.out.println("Package " + PkgName + " installed successfully.");
			return true;
		}catch(Exception e) {
			System.out.println("!!! IO Exception while installing package !!!");
			e.printStackTrace();
			return false;
		}
			
	}

	public boolean Backup() {
		File bkpDir = new File(GameDirectory, "bin/previous");
		File binDir = new File(GameDirectory, "bin");
		
		if(bkpDir.exists()) {
			System.out.println("Removing old backup...");
			DeleteRecursive(bkpDir);
		}
		
		bkpDir.mkdir();
		
		System.out.println("Backing up old version...");
		for(File f : binDir.listFiles()){
			if(f.equals(bkpDir))
				continue;
			System.out.println("    " + "/bin/" + f.getName() + " --> " + "/bin/previous/" + f.getName());
			CopyFile(f.getAbsolutePath(), bkpDir.getAbsolutePath() + "/" + f.getName());
		}
		return true;
	}

	private boolean DeleteRecursive(File dir) {
		System.out.println("Deleting " + dir.getName());
		return DeleteRecursiveRec(dir, 0);
	}
	
	private boolean DeleteRecursiveRec(File dir, int depth) {
		if(depth > 0) {
			for(int i = 0; i < depth; ++i)
				System.out.print("    ");
			System.out.print("|_ Deleting " + dir.getName());
			System.out.println("");
		}
		
		if(!dir.isDirectory()) {
			return(dir.delete());
		}
		
		for(File child : dir.listFiles()) {
			boolean res = DeleteRecursiveRec(child, depth + 1);
			if(!res)
				return false;
		}

		return dir.delete();
	}
	
	private boolean CopyFile(String in, String out) {
		File inFile = new File(in);
		File outFile = new File(out);
		
		if(!inFile.exists())
			return false;
		
		if(inFile.isDirectory()) {
			outFile.mkdir();
			CopyDir(inFile, outFile.getAbsolutePath());
			return true;
		}
		try {
			InputStream instream = new BufferedInputStream(new FileInputStream(inFile));
			OutputStream outstream = new BufferedOutputStream(new FileOutputStream(outFile));
			
			byte[] buffer = new byte[1024];
			int read;
			while((read = instream.read(buffer)) > 0) {
				outstream.write(buffer, 0, read);
				outstream.flush();
			}
			
			instream.close();
			outstream.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void CopyDir(File in, String parent) {
		for(File f : in.listFiles())
			CopyFile(f.getAbsolutePath(), parent + "/" + f.getName());
	}
	
	private boolean ExtractZip(String inPath, String outPath, boolean delete) {
		File pkgFile = new File(inPath);
		
		System.out.println("Extracting: " + pkgFile.getName());
		try {
			ZipInputStream zip = new ZipInputStream(new FileInputStream(pkgFile));
			ZipEntry entry = zip.getNextEntry();

			while (entry != null) {
				File out = new File(outPath + "/" + entry.getName());

				File parent = new File(out.getParent());
				if (!parent.exists())
					parent.mkdirs();

				boolean isDir = entry.getName().endsWith("/");
				System.out.println("    " + entry.getName() + " --> " + outPath);

				if (isDir) {
					out.mkdirs();
					entry = zip.getNextEntry();
					continue;
				}

				FileOutputStream fos = new FileOutputStream(out);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = zip.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				entry = zip.getNextEntry();
			}

			zip.close();
			if (delete)
				pkgFile.delete();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean VerifyInstallation(String root) {
		//Check jars
		String[] essentialJars = {
				"minecraft.jar",
				"lwjgl.jar",
				"lwjgl_util.jar",
				"jinput.jar"
		};
		
		File f;
		for(String jar : essentialJars) {
			f = new File(GameDirectory + "/bin/" + root + jar);
			if(!f.exists())
				return false;
		}
		
		//Check natives folder
		f = new File(GameDirectory, "/bin/" + root + "natives");
		if(!f.exists() || !f.isDirectory())
			return false;
		
		if(f.listFiles().length < 1)
			return false;
		
		return true;
	}
	
	public boolean ExtractJarResource(String key, String out, String name) {
		System.out.println("jar: " + key + " --> " + out);
		try {
			File o = new File(GameDirectory + out);
			
			o.mkdirs();
			
			o = new File(GameDirectory + out + "/" + name);
			
			InputStream is = new BufferedInputStream(PackageInstaller.class.getResourceAsStream(key));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(o));
			int read = 0;
			byte[] buffer = new byte[1024];
			
			while((read = is.read(buffer)) > 0) {
				os.write(buffer, 0, read);
			}
			
			os.close();
			is.close();
		}catch(Exception e) {
			System.out.println("!!! Unable to copy jar resource !!!");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
