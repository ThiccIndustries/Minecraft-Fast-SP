package net.minecraft.src;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer field_1270_b;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public ModelCreeper()
    {
        this(0.0F);
    }

    public ModelCreeper(float par1)
    {
        int i = 4;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -8F, -4F, 8, 8, 8, par1);
        head.setRotationPoint(0.0F, i, 0.0F);
        field_1270_b = new ModelRenderer(this, 32, 0);
        field_1270_b.addBox(-4F, -8F, -4F, 8, 8, 8, par1 + 0.5F);
        field_1270_b.setRotationPoint(0.0F, i, 0.0F);
        body = new ModelRenderer(this, 16, 16);
        body.addBox(-4F, 0.0F, -2F, 8, 12, 4, par1);
        body.setRotationPoint(0.0F, i, 0.0F);
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2F, 0.0F, -2F, 4, 6, 4, par1);
        leg1.setRotationPoint(-2F, 12 + i, 4F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2F, 0.0F, -2F, 4, 6, 4, par1);
        leg2.setRotationPoint(2.0F, 12 + i, 4F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2F, 0.0F, -2F, 4, 6, 4, par1);
        leg3.setRotationPoint(-2F, 12 + i, -4F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2F, 0.0F, -2F, 4, 6, 4, par1);
        leg4.setRotationPoint(2.0F, 12 + i, -4F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        head.render(par7);
        body.render(par7);
        leg1.render(par7);
        leg2.render(par7);
        leg3.render(par7);
        leg4.render(par7);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        head.rotateAngleY = par4 / (180F / (float)Math.PI);
        head.rotateAngleX = par5 / (180F / (float)Math.PI);
        leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    }
}
