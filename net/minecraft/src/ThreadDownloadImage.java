package net.minecraft.src;

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
        HttpURLConnection httpurlconnection = null;

        try
        {
            URL url = new URL(location);
            httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            if (httpurlconnection.getResponseCode() / 100 == 4)
            {
                return;
            }

            if (buffer == null)
            {
                imageData.image = ImageIO.read(httpurlconnection.getInputStream());
            }
            else
            {
                imageData.image = buffer.parseUserSkin(ImageIO.read(httpurlconnection.getInputStream()));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            httpurlconnection.disconnect();
        }
    }
}
