package net.minecraft.src;

public class ModelSheep2 extends ModelQuadruped
{
    private float field_44017_o;

    public ModelSheep2()
    {
        super(12, 0.0F);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-3F, -4F, -6F, 6, 6, 8, 0.0F);
        head.setRotationPoint(0.0F, 6F, -8F);
        body = new ModelRenderer(this, 28, 8);
        body.addBox(-4F, -10F, -7F, 8, 16, 6, 0.0F);
        body.setRotationPoint(0.0F, 5F, 2.0F);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
        head.rotationPointY = 6F + ((EntitySheep)par1EntityLiving).func_44003_c(par4) * 9F;
        field_44017_o = ((EntitySheep)par1EntityLiving).func_44002_d(par4);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        head.rotateAngleX = field_44017_o;
    }
}
