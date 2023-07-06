package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFireball extends Render
{
    private float field_40269_a;

    public RenderFireball(float par1)
    {
        field_40269_a = par1;
    }

    public void doRenderFireball(EntityFireball par1EntityFireball, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f = field_40269_a;
        GL11.glScalef(f / 1.0F, f / 1.0F, f / 1.0F);
        byte byte0 = 46;
        loadTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.instance;
        float f1 = (float)((byte0 % 16) * 16 + 0) / 256F;
        float f2 = (float)((byte0 % 16) * 16 + 16) / 256F;
        float f3 = (float)((byte0 / 16) * 16 + 0) / 256F;
        float f4 = (float)((byte0 / 16) * 16 + 16) / 256F;
        float f5 = 1.0F;
        float f6 = 0.5F;
        float f7 = 0.25F;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f6, 0.0F - f7, 0.0D, f1, f4);
        tessellator.addVertexWithUV(f5 - f6, 0.0F - f7, 0.0D, f2, f4);
        tessellator.addVertexWithUV(f5 - f6, 1.0F - f7, 0.0D, f2, f3);
        tessellator.addVertexWithUV(0.0F - f6, 1.0F - f7, 0.0D, f1, f3);
        tessellator.draw();
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
        doRenderFireball((EntityFireball)par1Entity, par2, par4, par6, par8, par9);
    }
}
