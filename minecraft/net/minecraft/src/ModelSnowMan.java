package net.minecraft.src;

public class ModelSnowMan extends ModelBase
{
    public ModelRenderer field_40306_a;
    public ModelRenderer field_40304_b;
    public ModelRenderer field_40305_c;
    public ModelRenderer field_40302_d;
    public ModelRenderer field_40303_e;

    public ModelSnowMan()
    {
        float f = 4F;
        float f1 = 0.0F;
        field_40305_c = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        field_40305_c.addBox(-4F, -8F, -4F, 8, 8, 8, f1 - 0.5F);
        field_40305_c.setRotationPoint(0.0F, 0.0F + f, 0.0F);
        field_40302_d = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
        field_40302_d.addBox(-1F, 0.0F, -1F, 12, 2, 2, f1 - 0.5F);
        field_40302_d.setRotationPoint(0.0F, (0.0F + f + 9F) - 7F, 0.0F);
        field_40303_e = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
        field_40303_e.addBox(-1F, 0.0F, -1F, 12, 2, 2, f1 - 0.5F);
        field_40303_e.setRotationPoint(0.0F, (0.0F + f + 9F) - 7F, 0.0F);
        field_40306_a = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
        field_40306_a.addBox(-5F, -10F, -5F, 10, 10, 10, f1 - 0.5F);
        field_40306_a.setRotationPoint(0.0F, 0.0F + f + 9F, 0.0F);
        field_40304_b = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
        field_40304_b.addBox(-6F, -12F, -6F, 12, 12, 12, f1 - 0.5F);
        field_40304_b.setRotationPoint(0.0F, 0.0F + f + 20F, 0.0F);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        field_40305_c.rotateAngleY = par4 / (180F / (float)Math.PI);
        field_40305_c.rotateAngleX = par5 / (180F / (float)Math.PI);
        field_40306_a.rotateAngleY = (par4 / (180F / (float)Math.PI)) * 0.25F;
        float f = MathHelper.sin(field_40306_a.rotateAngleY);
        float f1 = MathHelper.cos(field_40306_a.rotateAngleY);
        field_40302_d.rotateAngleZ = 1.0F;
        field_40303_e.rotateAngleZ = -1F;
        field_40302_d.rotateAngleY = 0.0F + field_40306_a.rotateAngleY;
        field_40303_e.rotateAngleY = (float)Math.PI + field_40306_a.rotateAngleY;
        field_40302_d.rotationPointX = f1 * 5F;
        field_40302_d.rotationPointZ = -f * 5F;
        field_40303_e.rotationPointX = -f1 * 5F;
        field_40303_e.rotationPointZ = f * 5F;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        field_40306_a.render(par7);
        field_40304_b.render(par7);
        field_40305_c.render(par7);
        field_40302_d.render(par7);
        field_40303_e.render(par7);
    }
}
