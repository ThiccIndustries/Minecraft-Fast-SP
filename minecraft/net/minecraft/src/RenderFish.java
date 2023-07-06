package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFish extends Render
{
    public RenderFish()
    {
    }

    /**
     * Actually renders the fishing line and hook
     */
    public void doRenderFishHook(EntityFishHook par1EntityFishHook, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        int i = 1;
        byte byte0 = 2;
        loadTexture("/particles.png");
        Tessellator tessellator = Tessellator.instance;
        float f = (float)(i * 8 + 0) / 128F;
        float f1 = (float)(i * 8 + 8) / 128F;
        float f2 = (float)(byte0 * 8 + 0) / 128F;
        float f3 = (float)(byte0 * 8 + 8) / 128F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.5F;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
        tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
        tessellator.addVertexWithUV(f4 - f5, 1.0F - f6, 0.0D, f1, f2);
        tessellator.addVertexWithUV(0.0F - f5, 1.0F - f6, 0.0D, f, f2);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();

        if (par1EntityFishHook.angler != null)
        {
            float f7 = ((par1EntityFishHook.angler.prevRotationYaw + (par1EntityFishHook.angler.rotationYaw - par1EntityFishHook.angler.prevRotationYaw) * par9) * (float)Math.PI) / 180F;
            double d = MathHelper.sin(f7);
            double d2 = MathHelper.cos(f7);
            float f9 = par1EntityFishHook.angler.getSwingProgress(par9);
            float f10 = MathHelper.sin(MathHelper.sqrt_float(f9) * (float)Math.PI);
            Vec3D vec3d = Vec3D.createVector(-0.5D, 0.029999999999999999D, 0.80000000000000004D);
            vec3d.rotateAroundX((-(par1EntityFishHook.angler.prevRotationPitch + (par1EntityFishHook.angler.rotationPitch - par1EntityFishHook.angler.prevRotationPitch) * par9) * (float)Math.PI) / 180F);
            vec3d.rotateAroundY((-(par1EntityFishHook.angler.prevRotationYaw + (par1EntityFishHook.angler.rotationYaw - par1EntityFishHook.angler.prevRotationYaw) * par9) * (float)Math.PI) / 180F);
            vec3d.rotateAroundY(f10 * 0.5F);
            vec3d.rotateAroundX(-f10 * 0.7F);
            double d4 = par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * (double)par9 + vec3d.xCoord;
            double d5 = par1EntityFishHook.angler.prevPosY + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * (double)par9 + vec3d.yCoord;
            double d6 = par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * (double)par9 + vec3d.zCoord;

            if (renderManager.options.thirdPersonView > 0)
            {
                float f8 = ((par1EntityFishHook.angler.prevRenderYawOffset + (par1EntityFishHook.angler.renderYawOffset - par1EntityFishHook.angler.prevRenderYawOffset) * par9) * (float)Math.PI) / 180F;
                double d1 = MathHelper.sin(f8);
                double d3 = MathHelper.cos(f8);
                d4 = (par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * (double)par9) - d3 * 0.34999999999999998D - d1 * 0.84999999999999998D;
                d5 = (par1EntityFishHook.angler.prevPosY + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * (double)par9) - 0.45000000000000001D;
                d6 = ((par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * (double)par9) - d1 * 0.34999999999999998D) + d3 * 0.84999999999999998D;
            }

            double d7 = par1EntityFishHook.prevPosX + (par1EntityFishHook.posX - par1EntityFishHook.prevPosX) * (double)par9;
            double d8 = par1EntityFishHook.prevPosY + (par1EntityFishHook.posY - par1EntityFishHook.prevPosY) * (double)par9 + 0.25D;
            double d9 = par1EntityFishHook.prevPosZ + (par1EntityFishHook.posZ - par1EntityFishHook.prevPosZ) * (double)par9;
            double d10 = (float)(d4 - d7);
            double d11 = (float)(d5 - d8);
            double d12 = (float)(d6 - d9);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(0);
            int j = 16;

            for (int k = 0; k <= j; k++)
            {
                float f11 = (float)k / (float)j;
                tessellator.addVertex(par2 + d10 * (double)f11, par4 + d11 * (double)(f11 * f11 + f11) * 0.5D + 0.25D, par6 + d12 * (double)f11);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        doRenderFishHook((EntityFishHook)par1Entity, par2, par4, par6, par8, par9);
    }
}
