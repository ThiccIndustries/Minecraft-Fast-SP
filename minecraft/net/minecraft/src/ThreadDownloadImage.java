package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

class ThreadDownloadImage extends Thread
{
    /** The URL of the image to download. */
    final String location;

    /** The image buffer to use. */
    final ImageBuffer buffer;

    /** The image data. */
    final ThreadDownloadImageData imageData;

    ThreadDownloadImage(ThreadDownloadImageData par1ThreadDownloadImageData, String par2Str, ImageBuffer par3ImageBuffer)
    {
        imageData = par1ThreadDownloadImageData;
        location = par2Str;
        buffer = par3ImageBuffer;
    }

    public void run()
    {
    	
    	System.out.println("Stream resource: " + location);
    	File f = new File(location);
    	
    	if(!f.exists()){
    		System.out.println(location + " not found.");
    		return;
    	}
    	
    	try{
    		FileInputStream is = new FileInputStream(f);
    		if (buffer == null)
    		{
    			imageData.image = ImageIO.read(is);
    		}
    		else
    		{
    			imageData.image = buffer.parseUserSkin(ImageIO.read(is));
    		}
    		
    		is.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
