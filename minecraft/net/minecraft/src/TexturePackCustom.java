package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackCustom extends TexturePackBase
{
    private ZipFile texturePackZipFile;

    /**
     * The allocated OpenGL texture name for this texture pack, or -1 if it hasn't been allocated yet.
     */
    private int texturePackName;
    private BufferedImage texturePackThumbnail;
    private File texturePackFile;

    public TexturePackCustom(File par1File)
    {
        texturePackName = -1;
        texturePackFileName = par1File.getName();
        texturePackFile = par1File;
    }

    /**
     * Truncates the specified string to 34 characters in length and returns it.
     */
    private String truncateString(String par1Str)
    {
        if (par1Str != null && par1Str.length() > 34)
        {
            par1Str = par1Str.substring(0, 34);
        }

        return par1Str;
    }

    public void func_6485_a(Minecraft par1Minecraft) throws IOException
    {
        ZipFile zipfile = null;
        InputStream inputstream = null;

        try
        {
            zipfile = new ZipFile(texturePackFile);

            try
            {
                inputstream = zipfile.getInputStream(zipfile.getEntry("pack.txt"));
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
                firstDescriptionLine = truncateString(bufferedreader.readLine());
                secondDescriptionLine = truncateString(bufferedreader.readLine());
                bufferedreader.close();
                inputstream.close();
            }
            catch (Exception exception) { }

            try
            {
                inputstream = zipfile.getInputStream(zipfile.getEntry("pack.png"));
                texturePackThumbnail = ImageIO.read(inputstream);
                inputstream.close();
            }
            catch (Exception exception1) { }

            zipfile.close();
        }
        catch (Exception exception2)
        {
            exception2.printStackTrace();
        }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch (Exception exception4) { }

            try
            {
                zipfile.close();
            }
            catch (Exception exception5) { }
        }
    }

    /**
     * Unbinds the thumbnail texture for texture pack screen
     */
    public void unbindThumbnailTexture(Minecraft par1Minecraft)
    {
        if (texturePackThumbnail != null)
        {
            par1Minecraft.renderEngine.deleteTexture(texturePackName);
        }

        closeTexturePackFile();
    }

    /**
     * binds the texture corresponding to the pack's thumbnail image
     */
    public void bindThumbnailTexture(Minecraft par1Minecraft)
    {
        if (texturePackThumbnail != null && texturePackName < 0)
        {
            texturePackName = par1Minecraft.renderEngine.allocateAndSetupTexture(texturePackThumbnail);
        }

        if (texturePackThumbnail != null)
        {
            par1Minecraft.renderEngine.bindTexture(texturePackName);
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }

    public void func_6482_a()
    {
        try
        {
            texturePackZipFile = new ZipFile(texturePackFile);
        }
        catch (Exception exception) { }
    }

    /**
     * Closes the zipfile associated to this texture pack. Does nothing for the default texture pack.
     */
    public void closeTexturePackFile()
    {
        try
        {
            texturePackZipFile.close();
        }
        catch (Exception exception) { }

        texturePackZipFile = null;
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        try
        {
            java.util.zip.ZipEntry zipentry = texturePackZipFile.getEntry(par1Str.substring(1));

            if (zipentry != null)
            {
                return texturePackZipFile.getInputStream(zipentry);
            }
        }
        catch (Exception exception) { }

        return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
    }
}
