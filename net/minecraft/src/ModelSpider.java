package net.minecraft.src;

public class ModelSpider extends ModelBase
{
    /** The spider's head box */
    public ModelRenderer spiderHead;

    /** The spider's neck box */
    public ModelRenderer spiderNeck;

    /** The spider's body box */
    public ModelRenderer spiderBody;

    /** Spider's first leg */
    public ModelRenderer spiderLeg1;

    /** Spider's second leg */
    public ModelRenderer spiderLeg2;

    /** Spider's third leg */
    public ModelRenderer spiderLeg3;

    /** Spider's fourth leg */
    public ModelRenderer spiderLeg4;

    /** Spider's fifth leg */
    public ModelRenderer spiderLeg5;

    /** Spider's sixth leg */
    public ModelRenderer spiderLeg6;

    /** Spider's seventh leg */
    public ModelRenderer spiderLeg7;

    /** Spider's eight leg */
    public ModelRenderer spiderLeg8;

    public ModelSpider()
    {
        float f = 0.0F;
        int i = 15;
        spiderHead = new ModelRenderer(this, 32, 4);
        spiderHead.addBox(-4F, -4F, -8F, 8, 8, 8, f);
        spiderHead.setRotationPoint(0.0F, i, -3F);
        spiderNeck = new ModelRenderer(this, 0, 0);
        spiderNeck.addBox(-3F, -3F, -3F, 6, 6, 6, f);
        spiderNeck.setRotationPoint(0.0F, i, 0.0F);
        spiderBody = new ModelRenderer(this, 0, 12);
        spiderBody.addBox(-5F, -4F, -6F, 10, 8, 12, f);
        spiderBody.setRotationPoint(0.0F, i, 9F);
        spiderLeg1 = new ModelRenderer(this, 18, 0);
        spiderLeg1.addBox(-15F, -1F, -1F, 16, 2, 2, f);
        spiderLeg1.setRotationPoint(-4F, i, 2.0F);
        spiderLeg2 = new ModelRenderer(this, 18, 0);
        spiderLeg2.addBox(-1F, -1F, -1F, 16, 2, 2, f);
        spiderLeg2.setRotationPoint(4F, i, 2.0F);
        spiderLeg3 = new ModelRenderer(this, 18, 0);
        spiderLeg3.addBox(-15F, -1F, -1F, 16, 2, 2, f);
        spiderLeg3.setRotationPoint(-4F, i, 1.0F);
        spiderLeg4 = new ModelRenderer(this, 18, 0);
        spiderLeg4.addBox(-1F, -1F, -1F, 16, 2, 2, f);
        spiderLeg4.setRotationPoint(4F, i, 1.0F);
        spiderLeg5 = new ModelRenderer(this, 18, 0);
        spiderLeg5.addBox(-15F, -1F, -1F, 16, 2, 2, f);
        spiderLeg5.setRotationPoint(-4F, i, 0.0F);
        spiderLeg6 = new ModelRenderer(this, 18, 0);
        spiderLeg6.addBox(-1F, -1F, -1F, 16, 2, 2, f);
        spiderLeg6.setRotationPoint(4F, i, 0.0F);
        spiderLeg7 = new ModelRenderer(this, 18, 0);
        spiderLeg7.addBox(-15F, -1F, -1F, 16, 2, 2, f);
        spiderLeg7.setRotationPoint(-4F, i, -1F);
        spiderLeg8 = new ModelRenderer(this, 18, 0);
        spiderLeg8.addBox(-1F, -1F, -1F, 16, 2, 2, f);
        spiderLeg8.setRotationPoint(4F, i, -1F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        spiderHead.render(par7);
        spiderNeck.render(par7);
        spiderBody.render(par7);
        spiderLeg1.render(par7);
        spiderLeg2.render(par7);
        spiderLeg3.render(par7);
        spiderLeg4.render(par7);
        spiderLeg5.render(par7);
        spiderLeg6.render(par7);
        spiderLeg7.render(par7);
        spiderLeg8.render(par7);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        spiderHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        spiderHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        float f = ((float)Math.PI / 4F);
        spiderLeg1.rotateAngleZ = -f;
        spiderLeg2.rotateAngleZ = f;
        spiderLeg3.rotateAngleZ = -f * 0.74F;
        spiderLeg4.rotateAngleZ = f * 0.74F;
        spiderLeg5.rotateAngleZ = -f * 0.74F;
        spiderLeg6.rotateAngleZ = f * 0.74F;
        spiderLeg7.rotateAngleZ = -f;
        spiderLeg8.rotateAngleZ = f;
        float f1 = -0F;
        float f2 = 0.3926991F;
        spiderLeg1.rotateAngleY = f2 * 2.0F + f1;
        spiderLeg2.rotateAngleY = -f2 * 2.0F - f1;
        spiderLeg3.rotateAngleY = f2 * 1.0F + f1;
        spiderLeg4.rotateAngleY = -f2 * 1.0F - f1;
        spiderLeg5.rotateAngleY = -f2 * 1.0F + f1;
        spiderLeg6.rotateAngleY = f2 * 1.0F - f1;
        spiderLeg7.rotateAngleY = -f2 * 2.0F + f1;
        spiderLeg8.rotateAngleY = f2 * 2.0F - f1;
        float f3 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
        float f4 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * par2;
        float f5 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * par2;
        float f6 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
        float f7 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
        float f8 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float)Math.PI) * 0.4F) * par2;
        float f9 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * par2;
        float f10 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
        spiderLeg1.rotateAngleY += f3;
        spiderLeg2.rotateAngleY += -f3;
        spiderLeg3.rotateAngleY += f4;
        spiderLeg4.rotateAngleY += -f4;
        spiderLeg5.rotateAngleY += f5;
        spiderLeg6.rotateAngleY += -f5;
        spiderLeg7.rotateAngleY += f6;
        spiderLeg8.rotateAngleY += -f6;
        spiderLeg1.rotateAngleZ += f7;
        spiderLeg2.rotateAngleZ += -f7;
        spiderLeg3.rotateAngleZ += f8;
        spiderLeg4.rotateAngleZ += -f8;
        spiderLeg5.rotateAngleZ += f9;
        spiderLeg6.rotateAngleZ += -f9;
        spiderLeg7.rotateAngleZ += f10;
        spiderLeg8.rotateAngleZ += -f10;
    }
}
