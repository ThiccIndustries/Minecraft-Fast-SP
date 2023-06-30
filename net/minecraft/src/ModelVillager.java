package net.minecraft.src;

public class ModelVillager extends ModelBase
{
    public ModelRenderer field_40340_a;
    public ModelRenderer field_40338_b;
    public ModelRenderer field_40339_c;
    public ModelRenderer field_40336_d;
    public ModelRenderer field_40337_e;

    public ModelVillager(float par1)
    {
        this(par1, 0.0F);
    }

    public ModelVillager(float par1, float par2)
    {
        byte byte0 = 64;
        byte byte1 = 64;
        field_40340_a = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
        field_40340_a.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
        field_40340_a.setTextureOffset(0, 0).addBox(-4F, -10F, -4F, 8, 10, 8, par1);
        field_40340_a.setTextureOffset(24, 0).addBox(-1F, -3F, -6F, 2, 4, 2, par1);
        field_40338_b = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
        field_40338_b.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
        field_40338_b.setTextureOffset(16, 20).addBox(-4F, 0.0F, -3F, 8, 12, 6, par1);
        field_40338_b.setTextureOffset(0, 38).addBox(-4F, 0.0F, -3F, 8, 18, 6, par1 + 0.5F);
        field_40339_c = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
        field_40339_c.setRotationPoint(0.0F, 0.0F + par2 + 2.0F, 0.0F);
        field_40339_c.setTextureOffset(44, 22).addBox(-8F, -2F, -2F, 4, 8, 4, par1);
        field_40339_c.setTextureOffset(44, 22).addBox(4F, -2F, -2F, 4, 8, 4, par1);
        field_40339_c.setTextureOffset(40, 38).addBox(-4F, 2.0F, -2F, 8, 4, 4, par1);
        field_40336_d = (new ModelRenderer(this, 0, 22)).setTextureSize(byte0, byte1);
        field_40336_d.setRotationPoint(-2F, 12F + par2, 0.0F);
        field_40336_d.addBox(-2F, 0.0F, -2F, 4, 12, 4, par1);
        field_40337_e = (new ModelRenderer(this, 0, 22)).setTextureSize(byte0, byte1);
        field_40337_e.mirror = true;
        field_40337_e.setRotationPoint(2.0F, 12F + par2, 0.0F);
        field_40337_e.addBox(-2F, 0.0F, -2F, 4, 12, 4, par1);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        field_40340_a.render(par7);
        field_40338_b.render(par7);
        field_40336_d.render(par7);
        field_40337_e.render(par7);
        field_40339_c.render(par7);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        field_40340_a.rotateAngleY = par4 / (180F / (float)Math.PI);
        field_40340_a.rotateAngleX = par5 / (180F / (float)Math.PI);
        field_40339_c.rotationPointY = 3F;
        field_40339_c.rotationPointZ = -1F;
        field_40339_c.rotateAngleX = -0.75F;
        field_40336_d.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2 * 0.5F;
        field_40337_e.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2 * 0.5F;
        field_40336_d.rotateAngleY = 0.0F;
        field_40337_e.rotateAngleY = 0.0F;
    }
}
