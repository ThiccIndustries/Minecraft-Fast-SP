package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererPiston extends TileEntitySpecialRenderer
{
    /** instance of RenderBlocks used to draw the piston base and extension. */
    private RenderBlocks blockRenderer;

    public TileEntityRendererPiston()
    {
    }

    public void renderPiston(TileEntityPiston par1TileEntityPiston, double par2, double par4, double par6, float par8)
    {
        Block block = Block.blocksList[par1TileEntityPiston.getStoredBlockID()];

        if (block != null && par1TileEntityPiston.getProgress(par8) < 1.0F)
        {
            Tessellator tessellator = Tessellator.instance;
            bindTextureByName("/terrain.png");
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);

            if (Minecraft.isAmbientOcclusionEnabled())
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }
            else
            {
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            tessellator.startDrawingQuads();
            tessellator.setTranslation(((float)par2 - (float)par1TileEntityPiston.xCoord) + par1TileEntityPiston.getOffsetX(par8), ((float)par4 - (float)par1TileEntityPiston.yCoord) + par1TileEntityPiston.getOffsetY(par8), ((float)par6 - (float)par1TileEntityPiston.zCoord) + par1TileEntityPiston.getOffsetZ(par8));
            tessellator.setColorOpaque(1, 1, 1);

            if (block == Block.pistonExtension && par1TileEntityPiston.getProgress(par8) < 0.5F)
            {
                blockRenderer.renderPistonExtensionAllFaces(block, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, false);
            }
            else if (par1TileEntityPiston.shouldRenderHead() && !par1TileEntityPiston.isExtending())
            {
                Block.pistonExtension.setHeadTexture(((BlockPistonBase)block).getPistonExtensionTexture());
                blockRenderer.renderPistonExtensionAllFaces(Block.pistonExtension, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, par1TileEntityPiston.getProgress(par8) < 0.5F);
                Block.pistonExtension.clearHeadTexture();
                tessellator.setTranslation((float)par2 - (float)par1TileEntityPiston.xCoord, (float)par4 - (float)par1TileEntityPiston.yCoord, (float)par6 - (float)par1TileEntityPiston.zCoord);
                blockRenderer.renderPistonBaseAllFaces(block, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
            }
            else
            {
                blockRenderer.renderBlockAllFaces(block, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
            }

            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }

    /**
     * Called from TileEntityRenderer.cacheSpecialRenderInfo() to cache render-related references (currently world
     * only). Used by TileEntityRendererPiston to create and store a RenderBlocks instance in the blockRenderer field.
     */
    public void cacheSpecialRenderInfo(World par1World)
    {
        blockRenderer = new RenderBlocks(par1World);
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderPiston((TileEntityPiston)par1TileEntity, par2, par4, par6, par8);
    }
}
