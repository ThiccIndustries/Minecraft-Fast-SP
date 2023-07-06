package net.minecraft.src;

import java.io.*;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.minecraft.client.Minecraft;
import org.w3c.dom.*;

public class ThreadDownloadResources extends Thread
{
    /** The folder to store the resources in. */
    public File resourcesFolder;

    /** A reference to the Minecraft object. */
    private Minecraft mc;

    /** Set to true when Minecraft is closing down. */
    private boolean closing;

    public ThreadDownloadResources(File par1File, Minecraft par2Minecraft)
    {
        closing = false;
        mc = par2Minecraft;
        setName("Resource download thread");
        setDaemon(true);
        resourcesFolder = new File(par1File, "resources/");

        if (!resourcesFolder.exists() && !resourcesFolder.mkdirs())
        {
            throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(resourcesFolder).toString());
        }
        else
        {
            return;
        }
    }

    public void run()
    {
        try
        {
            URL url = new URL("http://s3.amazonaws.com/MinecraftResources/");
            DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
            Document document = documentbuilder.parse(url.openStream());
            NodeList nodelist = document.getElementsByTagName("Contents");

            for (int i = 0; i < 2; i++)
            {
                for (int j = 0; j < nodelist.getLength(); j++)
                {
                    Node node = nodelist.item(j);

                    if (node.getNodeType() != 1)
                    {
                        continue;
                    }

                    Element element = (Element)node;
                    String s = ((Element)element.getElementsByTagName("Key").item(0)).getChildNodes().item(0).getNodeValue();
                    long l = Long.parseLong(((Element)element.getElementsByTagName("Size").item(0)).getChildNodes().item(0).getNodeValue());

                    if (l <= 0L)
                    {
                        continue;
                    }

                    downloadAndInstallResource(url, s, l, i);

                    if (closing)
                    {
                        return;
                    }
                }
            }
        }
        catch (Exception exception)
        {
            loadResource(resourcesFolder, "");
            exception.printStackTrace();
        }
    }

    /**
     * Reloads the resource folder and passes the resources to Minecraft to install.
     */
    public void reloadResources()
    {
        loadResource(resourcesFolder, "");
    }

    /**
     * Loads a resource and passes it to Minecraft to install.
     */
    private void loadResource(File par1File, String par2Str)
    {
        File afile[] = par1File.listFiles();

        for (int i = 0; i < afile.length; i++)
        {
            if (afile[i].isDirectory())
            {
                loadResource(afile[i], (new StringBuilder()).append(par2Str).append(afile[i].getName()).append("/").toString());
                continue;
            }

            try
            {
                mc.installResource((new StringBuilder()).append(par2Str).append(afile[i].getName()).toString(), afile[i]);
            }
            catch (Exception exception)
            {
                System.out.println((new StringBuilder()).append("Failed to add ").append(par2Str).append(afile[i].getName()).toString());
            }
        }
    }

    /**
     * Downloads the resource and saves it to disk then installs it.
     */
    private void downloadAndInstallResource(URL par1URL, String par2Str, long par3, int par5)
    {
        try
        {
            int i = par2Str.indexOf("/");
            String s = par2Str.substring(0, i);

            if (s.equals("sound") || s.equals("newsound"))
            {
                if (par5 != 0)
                {
                    return;
                }
            }
            else if (par5 != 1)
            {
                return;
            }

            File file = new File(resourcesFolder, par2Str);

            if (!file.exists() || file.length() != par3)
            {
                file.getParentFile().mkdirs();
                String s1 = par2Str.replaceAll(" ", "%20");
                downloadResource(new URL(par1URL, s1), file, par3);

                if (closing)
                {
                    return;
                }
            }

            mc.installResource(par2Str, file);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Downloads the resource and saves it to disk.
     */
    private void downloadResource(URL par1URL, File par2File, long par3) throws IOException
    {
        byte abyte0[] = new byte[4096];
        DataInputStream datainputstream = new DataInputStream(par1URL.openStream());
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(par2File));

        for (int i = 0; (i = datainputstream.read(abyte0)) >= 0;)
        {
            dataoutputstream.write(abyte0, 0, i);

            if (closing)
            {
                return;
            }
        }

        datainputstream.close();
        dataoutputstream.close();
    }

    /**
     * Called when Minecraft is closing down.
     */
    public void closeMinecraft()
    {
        closing = true;
    }
}
