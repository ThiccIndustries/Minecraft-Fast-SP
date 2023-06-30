package net.minecraft.src;

public class ModelSheep1 extends ModelQuadruped
{
    private float field_44016_o;

    public ModelSheep1()
    {
        super(12, 0.0F);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-3F, -4F, -4F, 6, 6, 6, 0.6F);
        head.setRotationPoint(0.0F, 6F, -8F);
        body = new ModelRenderer(this, 28, 8);
        body.addBox(-4F, -10F, -7F, 8, 16, 6, 1.75F);
        body.setRotationPoint(0.0F, 5F, 2.0F);
        float f = 0.5F;
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
        leg1.setRotationPoint(-3F, 12F, 7F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
        leg2.setRotationPoint(3F, 12F, 7F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
        leg3.setRotationPoint(-3F, 12F, -5F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
        leg4.setRotationPoint(3F, 12F, -5F);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
        head.rotationPointY = 6F + ((EntitySheep)par1EntityLiving).func_44003_c(par4) * 9F;
        field_44016_o = ((EntitySheep)par1EntityLiving).func_44002_d(par4);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        head.rotateAngleX = field_44016_o;
    }
}
