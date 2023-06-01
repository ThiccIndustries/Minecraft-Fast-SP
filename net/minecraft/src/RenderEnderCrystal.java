package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderEnderCrystal extends Render
{
    private int field_41037_a;
    private ModelBase field_41036_b;

    public RenderEnderCrystal()
    {
        field_41037_a = -1;
        shadowSize = 0.5F;
    }

    public void func_41035_a(EntityEnderCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9)
    {
        if (field_41037_a != 1)
        {
            field_41036_b = new ModelEnderCrystal(0.0F);
            field_41037_a = 1;
        }

        float f = (float)par1EntityEnderCrystal.innerRotation + par9;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        loadTexture("/mob/enderdragon/crystal.png");
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;
        field_41036_b.render(par1EntityEnderCrystal, 0.0F, f * 3F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
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
        func_41035_a((EntityEnderCrystal)par1Entity, par2, par4, par6, par8, par9);
    }
}
