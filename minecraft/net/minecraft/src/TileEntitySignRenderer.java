package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer
{
    /** The ModelSign instance used by the TileEntitySignRenderer */
    private ModelSign modelSign;

    public TileEntitySignRenderer()
    {
        modelSign = new ModelSign();
    }

    public void renderTileEntitySignAt(TileEntitySign par1TileEntitySign, double par2, double par4, double par6, float par8)
    {
        Block block = par1TileEntitySign.getBlockType();
        GL11.glPushMatrix();
        float f = 0.6666667F;

        if (block == Block.signPost)
        {
            GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.75F * f, (float)par6 + 0.5F);
            float f1 = (float)(par1TileEntitySign.getBlockMetadata() * 360) / 16F;
            GL11.glRotatef(-f1, 0.0F, 1.0F, 0.0F);
            modelSign.signStick.showModel = true;
        }
        else
        {
            int i = par1TileEntitySign.getBlockMetadata();
            float f2 = 0.0F;

            if (i == 2)
            {
                f2 = 180F;
            }

            if (i == 4)
            {
                f2 = 90F;
            }

            if (i == 5)
            {
                f2 = -90F;
            }

            GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.75F * f, (float)par6 + 0.5F);
            GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
            modelSign.signStick.showModel = false;
        }

        bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(f, -f, -f);
        modelSign.renderSign();
        GL11.glPopMatrix();
        FontRenderer fontrenderer = getFontRenderer();
        float f3 = 0.01666667F * f;
        GL11.glTranslatef(0.0F, 0.5F * f, 0.07F * f);
        GL11.glScalef(f3, -f3, f3);
        GL11.glNormal3f(0.0F, 0.0F, -1F * f3);
        GL11.glDepthMask(false);
        int j = 0;

        for (int k = 0; k < par1TileEntitySign.signText.length; k++)
        {
            String s = par1TileEntitySign.signText[k];

            if (k == par1TileEntitySign.lineBeingEdited)
            {
                s = (new StringBuilder()).append("> ").append(s).append(" <").toString();
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - par1TileEntitySign.signText.length * 5, j);
            }
            else
            {
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - par1TileEntitySign.signText.length * 5, j);
            }
        }

        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntitySignAt((TileEntitySign)par1TileEntity, par2, par4, par6, par8);
    }
}
