package net.minecraft.src;

public abstract class TileEntitySpecialRenderer
{
    /**
     * The TileEntityRenderer instance associated with this TileEntitySpecialRenderer
     */
    protected TileEntityRenderer tileEntityRenderer;

    public TileEntitySpecialRenderer()
    {
    }

    public abstract void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f);

    /**
     * Binds a texture to the renderEngine given a filename from the JAR.
     */
    protected void bindTextureByName(String par1Str)
    {
        RenderEngine renderengine = tileEntityRenderer.renderEngine;

        if (renderengine != null)
        {
            renderengine.bindTexture(renderengine.getTexture(par1Str));
        }
    }

    /**
     * Associate a TileEntityRenderer with this TileEntitySpecialRenderer
     */
    public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer)
    {
        tileEntityRenderer = par1TileEntityRenderer;
    }

    /**
     * Called from TileEntityRenderer.cacheSpecialRenderInfo() to cache render-related references (currently world
     * only). Used by TileEntityRendererPiston to create and store a RenderBlocks instance in the blockRenderer field.
     */
    public void cacheSpecialRenderInfo(World world)
    {
    }

    public FontRenderer getFontRenderer()
    {
        return tileEntityRenderer.getFontRenderer();
    }
}
