package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class EntityFootStepFX extends EntityFX
{
    private int field_27018_a;
    private int field_27020_o;
    private RenderEngine currentFootSteps;

    public EntityFootStepFX(RenderEngine par1RenderEngine, World par2World, double par3, double par5, double par7)
    {
        super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
        field_27018_a = 0;
        field_27020_o = 0;
        currentFootSteps = par1RenderEngine;
        motionX = motionY = motionZ = 0.0D;
        field_27020_o = 200;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float f = ((float)field_27018_a + par2) / (float)field_27020_o;
        f *= f;
        float f1 = 2.0F - f * 2.0F;

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        f1 *= 0.2F;
        GL11.glDisable(GL11.GL_LIGHTING);
        float f2 = 0.125F;
        float f3 = (float)(posX - interpPosX);
        float f4 = (float)(posY - interpPosY);
        float f5 = (float)(posZ - interpPosZ);
        float f6 = worldObj.getLightBrightness(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        currentFootSteps.bindTexture(currentFootSteps.getTexture("/misc/footprint.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorRGBA_F(f6, f6, f6, f1);
        par1Tessellator.addVertexWithUV(f3 - f2, f4, f5 + f2, 0.0D, 1.0D);
        par1Tessellator.addVertexWithUV(f3 + f2, f4, f5 + f2, 1.0D, 1.0D);
        par1Tessellator.addVertexWithUV(f3 + f2, f4, f5 - f2, 1.0D, 0.0D);
        par1Tessellator.addVertexWithUV(f3 - f2, f4, f5 - f2, 0.0D, 0.0D);
        par1Tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        field_27018_a++;

        if (field_27018_a == field_27020_o)
        {
            setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
