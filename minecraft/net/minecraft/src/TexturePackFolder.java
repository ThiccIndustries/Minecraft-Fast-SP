package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackFolder extends TexturePackBase
{
    private int field_48191_e;
    private BufferedImage field_48189_f;
    private File field_48190_g;

    public TexturePackFolder(File par1File)
    {
        field_48191_e = -1;
        texturePackFileName = par1File.getName();
        field_48190_g = par1File;
    }

    private String func_48188_b(String par1Str)
    {
        if (par1Str != null && par1Str.length() > 34)
        {
            par1Str = par1Str.substring(0, 34);
        }

        return par1Str;
    }

    public void func_6485_a(Minecraft par1Minecraft) throws IOException
    {
        label0:
        {
            InputStream inputstream = null;

            try
            {
                try
                {
                    try
                    {
                        inputstream = getResourceAsStream("pack.txt");
                        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
                        firstDescriptionLine = func_48188_b(bufferedreader.readLine());
                        secondDescriptionLine = func_48188_b(bufferedreader.readLine());
                        bufferedreader.close();
                        inputstream.close();
                    }
                    catch (Exception exception) { }

                    try
                    {
                        inputstream = getResourceAsStream("pack.png");
                        field_48189_f = ImageIO.read(inputstream);
                        inputstream.close();
                    }
                    catch (Exception exception1) { }
                }
                catch (Exception exception2)
                {
                    exception2.printStackTrace();
                    break label0;
                }

                break label0;
            }
            finally
            {
                try
                {
                    inputstream.close();
                }
                catch (Exception exception4) { }
            }
        }
    }

    /**
     * Unbinds the thumbnail texture for texture pack screen
     */
    public void unbindThumbnailTexture(Minecraft par1Minecraft)
    {
        if (field_48189_f != null)
        {
            par1Minecraft.renderEngine.deleteTexture(field_48191_e);
        }

        closeTexturePackFile();
    }

    /**
     * binds the texture corresponding to the pack's thumbnail image
     */
    public void bindThumbnailTexture(Minecraft par1Minecraft)
    {
        if (field_48189_f != null && field_48191_e < 0)
        {
            field_48191_e = par1Minecraft.renderEngine.allocateAndSetupTexture(field_48189_f);
        }

        if (field_48189_f != null)
        {
            par1Minecraft.renderEngine.bindTexture(field_48191_e);
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }

    public void func_6482_a()
    {
    }

    /**
     * Closes the zipfile associated to this texture pack. Does nothing for the default texture pack.
     */
    public void closeTexturePackFile()
    {
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        try
        {
            File file = new File(field_48190_g, par1Str.substring(1));

            if (file.exists())
            {
                return new BufferedInputStream(new FileInputStream(file));
            }
        }
        catch (Exception exception) { }

        return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
    }
}
