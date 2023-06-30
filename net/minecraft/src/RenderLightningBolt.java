package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class RenderLightningBolt extends Render
{
    public RenderLightningBolt()
    {
    }

    /**
     * Actually renders the lightning bolt. This method is called through the doRender method.
     */
    public void doRenderLightningBolt(EntityLightningBolt par1EntityLightningBolt, double par2, double par4, double par6, float par8, float par9)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        double ad[] = new double[8];
        double ad1[] = new double[8];
        double d = 0.0D;
        double d1 = 0.0D;
        Random random = new Random(par1EntityLightningBolt.boltVertex);

        for (int j = 7; j >= 0; j--)
        {
            ad[j] = d;
            ad1[j] = d1;
            d += random.nextInt(11) - 5;
            d1 += random.nextInt(11) - 5;
        }

        for (int i = 0; i < 4; i++)
        {
            Random random1 = new Random(par1EntityLightningBolt.boltVertex);

            for (int k = 0; k < 3; k++)
            {
                int l = 7;
                int i1 = 0;

                if (k > 0)
                {
                    l = 7 - k;
                }

                if (k > 0)
                {
                    i1 = l - 2;
                }

                double d2 = ad[l] - d;
                double d3 = ad1[l] - d1;

                for (int j1 = l; j1 >= i1; j1--)
                {
                    double d4 = d2;
                    double d5 = d3;

                    if (k == 0)
                    {
                        d2 += random1.nextInt(11) - 5;
                        d3 += random1.nextInt(11) - 5;
                    }
                    else
                    {
                        d2 += random1.nextInt(31) - 15;
                        d3 += random1.nextInt(31) - 15;
                    }

                    tessellator.startDrawing(5);
                    float f = 0.5F;
                    tessellator.setColorRGBA_F(0.9F * f, 0.9F * f, 1.0F * f, 0.3F);
                    double d6 = 0.10000000000000001D + (double)i * 0.20000000000000001D;

                    if (k == 0)
                    {
                        d6 *= (double)j1 * 0.10000000000000001D + 1.0D;
                    }

                    double d7 = 0.10000000000000001D + (double)i * 0.20000000000000001D;

                    if (k == 0)
                    {
                        d7 *= (double)(j1 - 1) * 0.10000000000000001D + 1.0D;
                    }

                    for (int k1 = 0; k1 < 5; k1++)
                    {
                        double d8 = (par2 + 0.5D) - d6;
                        double d9 = (par6 + 0.5D) - d6;

                        if (k1 == 1 || k1 == 2)
                        {
                            d8 += d6 * 2D;
                        }

                        if (k1 == 2 || k1 == 3)
                        {
                            d9 += d6 * 2D;
                        }

                        double d10 = (par2 + 0.5D) - d7;
                        double d11 = (par6 + 0.5D) - d7;

                        if (k1 == 1 || k1 == 2)
                        {
                            d10 += d7 * 2D;
                        }

                        if (k1 == 2 || k1 == 3)
                        {
                            d11 += d7 * 2D;
                        }

                        tessellator.addVertex(d10 + d2, par4 + (double)(j1 * 16), d11 + d3);
                        tessellator.addVertex(d8 + d4, par4 + (double)((j1 + 1) * 16), d9 + d5);
                    }

                    tessellator.draw();
                }
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        doRenderLightningBolt((EntityLightningBolt)par1Entity, par2, par4, par6, par8, par9);
    }
}
