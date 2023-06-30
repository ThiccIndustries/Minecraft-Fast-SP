package net.minecraft.src;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class RenderEndPortal extends TileEntitySpecialRenderer
{
    FloatBuffer field_40448_a;

    public RenderEndPortal()
    {
        field_40448_a = GLAllocation.createDirectFloatBuffer(16);
    }

    public void func_40446_a(TileEntityEndPortal par1TileEntityEndPortal, double par2, double par4, double par6, float par8)
    {
        float f = (float)tileEntityRenderer.playerX;
        float f1 = (float)tileEntityRenderer.playerY;
        float f2 = (float)tileEntityRenderer.playerZ;
        GL11.glDisable(GL11.GL_LIGHTING);
        Random random = new Random(31100L);
        float f3 = 0.75F;

        for (int i = 0; i < 16; i++)
        {
            GL11.glPushMatrix();
            float f4 = 16 - i;
            float f5 = 0.0625F;
            float f6 = 1.0F / (f4 + 1.0F);

            if (i == 0)
            {
                bindTextureByName("/misc/tunnel.png");
                f6 = 0.1F;
                f4 = 65F;
                f5 = 0.125F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }

            if (i == 1)
            {
                bindTextureByName("/misc/particlefield.png");
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                f5 = 0.5F;
            }

            float f7 = (float)(-(par4 + (double)f3));
            float f8 = f7 + ActiveRenderInfo.objectY;
            float f9 = f7 + f4 + ActiveRenderInfo.objectY;
            float f10 = f8 / f9;
            f10 = (float)(par4 + (double)f3) + f10;
            GL11.glTranslatef(f, f10, f2);
            GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR);
            GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
            GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, func_40447_a(1.0F, 0.0F, 0.0F, 0.0F));
            GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, func_40447_a(0.0F, 0.0F, 1.0F, 0.0F));
            GL11.glTexGen(GL11.GL_R, GL11.GL_OBJECT_PLANE, func_40447_a(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, func_40447_a(0.0F, 1.0F, 0.0F, 0.0F));
            GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
            GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, (float)(System.currentTimeMillis() % 0xaae60L) / 700000F, 0.0F);
            GL11.glScalef(f5, f5, f5);
            GL11.glTranslatef(0.5F, 0.5F, 0.0F);
            GL11.glRotatef((float)(i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
            GL11.glTranslatef(-f, -f2, -f1);
            f8 = f7 + ActiveRenderInfo.objectY;
            GL11.glTranslatef((ActiveRenderInfo.objectX * f4) / f8, (ActiveRenderInfo.objectZ * f4) / f8, -f1);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            f10 = random.nextFloat() * 0.5F + 0.1F;
            float f11 = random.nextFloat() * 0.5F + 0.4F;
            float f12 = random.nextFloat() * 0.5F + 0.5F;

            if (i == 0)
            {
                f10 = f11 = f12 = 1.0F;
            }

            tessellator.setColorRGBA_F(f10 * f6, f11 * f6, f12 * f6, 1.0F);
            tessellator.addVertex(par2, par4 + (double)f3, par6);
            tessellator.addVertex(par2, par4 + (double)f3, par6 + 1.0D);
            tessellator.addVertex(par2 + 1.0D, par4 + (double)f3, par6 + 1.0D);
            tessellator.addVertex(par2 + 1.0D, par4 + (double)f3, par6);
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
        GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    private FloatBuffer func_40447_a(float par1, float par2, float par3, float par4)
    {
        field_40448_a.clear();
        field_40448_a.put(par1).put(par2).put(par3).put(par4);
        field_40448_a.flip();
        return field_40448_a;
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        func_40446_a((TileEntityEndPortal)par1TileEntity, par2, par4, par6, par8);
    }
}
