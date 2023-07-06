package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSheep extends RenderLiving
{
    public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super(par1ModelBase, par3);
        setRenderPassModel(par2ModelBase);
    }

    protected int setWoolColorAndRender(EntitySheep par1EntitySheep, int par2, float par3)
    {
        if (par2 == 0 && !par1EntitySheep.getSheared())
        {
            loadTexture("/mob/sheep_fur.png");
            float f = 1.0F;
            int i = par1EntitySheep.getFleeceColor();
            GL11.glColor3f(f * EntitySheep.fleeceColorTable[i][0], f * EntitySheep.fleeceColorTable[i][1], f * EntitySheep.fleeceColorTable[i][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void doRenderSheep(EntitySheep par1EntitySheep, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntitySheep, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return setWoolColorAndRender((EntitySheep)par1EntityLiving, par2, par3);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        doRenderSheep((EntitySheep)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        doRenderSheep((EntitySheep)par1Entity, par2, par4, par6, par8, par9);
    }
}
