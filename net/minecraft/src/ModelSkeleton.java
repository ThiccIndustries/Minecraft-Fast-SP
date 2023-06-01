package net.minecraft.src;

public class ModelSkeleton extends ModelZombie
{
    public ModelSkeleton()
    {
        float f = 0.0F;
        bipedRightArm = new ModelRenderer(this, 40, 16);
        bipedRightArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
        bipedLeftArm = new ModelRenderer(this, 40, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
        bipedRightLeg = new ModelRenderer(this, 0, 16);
        bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 12, 2, f);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0.0F);
        bipedLeftLeg = new ModelRenderer(this, 0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 12, 2, f);
        bipedLeftLeg.setRotationPoint(2.0F, 12F, 0.0F);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        aimedBow = true;
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
    }
}
