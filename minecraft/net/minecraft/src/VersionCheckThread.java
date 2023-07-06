package net.minecraft.src;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionCheckThread extends Thread
{
    public VersionCheckThread()
    {
    }

    public void run()
    {
        Object obj = null;

        try
        {
            Config.dbg("Checking for new version");
            URL url = new URL("http://optifine.net/version/1.2.5/HD.txt");
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            try
            {
                InputStream inputstream = httpurlconnection.getInputStream();
                String s = Config.readInputStream(inputstream);
                inputstream.close();
                String as[] = Config.tokenize(s, "\n\r");

                if (as.length < 1)
                {
                    return;
                }

                String s1 = as[0];
                Config.dbg((new StringBuilder()).append("Version found: ").append(s1).toString());

                if (Config.compareRelease(s1, "C6") <= 0)
                {
                    return;
                }

                Config.setNewRelease(s1);
            }
            finally
            {
                if (httpurlconnection != null)
                {
                    httpurlconnection.disconnect();
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
