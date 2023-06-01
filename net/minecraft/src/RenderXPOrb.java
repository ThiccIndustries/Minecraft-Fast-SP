package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderXPOrb extends Render
{
    private RenderBlocks field_35439_b;
    public boolean field_35440_a;

    public RenderXPOrb()
    {
        field_35439_b = new RenderBlocks();
        field_35440_a = true;
        shadowSize = 0.15F;
        shadowOpaque = 0.75F;
    }

    public void func_35438_a(EntityXPOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        int i = par1EntityXPOrb.getTextureByXP();
        loadTexture("/item/xporb.png");
        Tessellator tessellator = Tessellator.instance;
        float f = (float)((i % 4) * 16 + 0) / 64F;
        float f1 = (float)((i % 4) * 16 + 16) / 64F;
        float f2 = (float)((i / 4) * 16 + 0) / 64F;
        float f3 = (float)((i / 4) * 16 + 16) / 64F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        int i7 = par1EntityXPOrb.getBrightnessForRender(par9);
        float f8 = i7 % 0x10000;
        int j = i7 / 0x10000;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)f8 / 1.0F, (float)j / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f7 = 255F;
        f8 = ((float)par1EntityXPOrb.xpColor + par9) / 2.0F;
        j = (int)((MathHelper.sin(f8 + 0.0F) + 1.0F) * 0.5F * f7);
        int k = (int)f7;
        int l = (int)((MathHelper.sin(f8 + 4.18879F) + 1.0F) * 0.1F * f7);
        int i1 = j << 16 | k << 8 | l;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.3F;
        GL11.glScalef(f9, f9, f9);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(i1, 128);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
        tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
        tessellator.addVertexWithUV(f4 - f5, 1.0F - f6, 0.0D, f1, f2);
        tessellator.addVertexWithUV(0.0F - f5, 1.0F - f6, 0.0D, f, f2);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        func_35438_a((EntityXPOrb)par1Entity, par2, par4, par6, par8, par9);
    }
}
