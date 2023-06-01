package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderArrow extends Render
{
    public RenderArrow()
    {
    }

    public void renderArrow(EntityArrow par1EntityArrow, double par2, double par4, double par6, float par8, float par9)
    {
        loadTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef((par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        int i = 0;
        float f = 0.0F;
        float f1 = 0.5F;
        float f2 = (float)(0 + i * 10) / 32F;
        float f3 = (float)(5 + i * 10) / 32F;
        float f4 = 0.0F;
        float f5 = 0.15625F;
        float f6 = (float)(5 + i * 10) / 32F;
        float f7 = (float)(10 + i * 10) / 32F;
        float f8 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f9 = (float)par1EntityArrow.arrowShake - par9;

        if (f9 > 0.0F)
        {
            float f10 = -MathHelper.sin(f9 * 3F) * f9;
            GL11.glRotatef(f10, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f8, f8, f8);
        GL11.glTranslatef(-4F, 0.0F, 0.0F);
        GL11.glNormal3f(f8, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7D, -2D, -2D, f4, f6);
        tessellator.addVertexWithUV(-7D, -2D, 2D, f5, f6);
        tessellator.addVertexWithUV(-7D, 2D, 2D, f5, f7);
        tessellator.addVertexWithUV(-7D, 2D, -2D, f4, f7);
        tessellator.draw();
        GL11.glNormal3f(-f8, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7D, 2D, -2D, f4, f6);
        tessellator.addVertexWithUV(-7D, 2D, 2D, f5, f6);
        tessellator.addVertexWithUV(-7D, -2D, 2D, f5, f7);
        tessellator.addVertexWithUV(-7D, -2D, -2D, f4, f7);
        tessellator.draw();

        for (int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f8);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8D, -2D, 0.0D, f, f2);
            tessellator.addVertexWithUV(8D, -2D, 0.0D, f1, f2);
            tessellator.addVertexWithUV(8D, 2D, 0.0D, f1, f3);
            tessellator.addVertexWithUV(-8D, 2D, 0.0D, f, f3);
            tessellator.draw();
        }

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
        renderArrow((EntityArrow)par1Entity, par2, par4, par6, par8, par9);
    }
}
