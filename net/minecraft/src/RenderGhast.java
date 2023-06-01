package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderGhast extends RenderLiving
{
    public RenderGhast()
    {
        super(new ModelGhast(), 0.5F);
    }

    protected void func_4014_a(EntityGhast par1EntityGhast, float par2)
    {
        EntityGhast entityghast = par1EntityGhast;
        float f = ((float)entityghast.prevAttackCounter + (float)(entityghast.attackCounter - entityghast.prevAttackCounter) * par2) / 20F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        f = 1.0F / (f * f * f * f * f * 2.0F + 1.0F);
        float f1 = (8F + f) / 2.0F;
        float f2 = (8F + 1.0F / f) / 2.0F;
        GL11.glScalef(f2, f1, f2);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        func_4014_a((EntityGhast)par1EntityLiving, par2);
    }
}
