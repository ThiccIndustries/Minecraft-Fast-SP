package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackDefault extends TexturePackBase
{
    /**
     * The allocated OpenGL for this TexturePack, or -1 if it hasn't been loaded yet.
     */
    private int texturePackName;
    private BufferedImage texturePackThumbnail;

    public TexturePackDefault()
    {
        texturePackName = -1;
        texturePackFileName = "Default";
        firstDescriptionLine = "The default look of Minecraft";

        try
        {
            texturePackThumbnail = ImageIO.read((net.minecraft.src.TexturePackDefault.class).getResource("/pack.png"));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
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
}
