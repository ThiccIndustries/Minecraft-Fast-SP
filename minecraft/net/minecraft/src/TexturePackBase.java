package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;

public abstract class TexturePackBase
{
    /**
     * The file name of the texture pack, or Default if not from a custom texture pack.
     */
    public String texturePackFileName;

    /**
     * The first line of the texture pack description (read from the pack.txt file)
     */
    public String firstDescriptionLine;

    /**
     * The second line of the texture pack description (read from the pack.txt file)
     */
    public String secondDescriptionLine;

    /** Texture pack ID */
    public String texturePackID;

    public TexturePackBase()
    {
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

    public void func_6485_a(Minecraft minecraft) throws IOException
    {
    }

    /**
     * Unbinds the thumbnail texture for texture pack screen
     */
    public void unbindThumbnailTexture(Minecraft minecraft)
    {
    }

    /**
     * binds the texture corresponding to the pack's thumbnail image
     */
    public void bindThumbnailTexture(Minecraft minecraft)
    {
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
    }
}
