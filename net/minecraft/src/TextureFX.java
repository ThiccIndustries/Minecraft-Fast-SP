package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class TextureFX
{
    public byte imageData[];
    public int iconIndex;
    public boolean anaglyphEnabled;

    /** Texture ID */
    public int textureId;
    public int tileSize;
    public int tileImage;

    public TextureFX(int par1)
    {
        imageData = new byte[1024];
        anaglyphEnabled = false;
        textureId = 0;
        tileSize = 1;
        tileImage = 0;
        iconIndex = par1;
    }

    public void onTick()
    {
    }

    public void bindImage(RenderEngine par1RenderEngine)
    {
        if (tileImage == 0)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1RenderEngine.getTexture("/terrain.png"));
        }
        else if (tileImage == 1)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1RenderEngine.getTexture("/gui/items.png"));
        }
    }
}
