package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ModelWolf extends ModelBase
{
    /** main box for the wolf head */
    public ModelRenderer wolfHeadMain;

    /** The wolf's body */
    public ModelRenderer wolfBody;

    /** Wolf'se first leg */
    public ModelRenderer wolfLeg1;

    /** Wolf's second leg */
    public ModelRenderer wolfLeg2;

    /** Wolf's third leg */
    public ModelRenderer wolfLeg3;

    /** Wolf's fourth leg */
    public ModelRenderer wolfLeg4;

    /** The wolf's tail */
    ModelRenderer wolfTail;

    /** The wolf's mane */
    ModelRenderer wolfMane;

    public ModelWolf()
    {
        float f = 0.0F;
        float f1 = 13.5F;
        wolfHeadMain = new ModelRenderer(this, 0, 0);
        wolfHeadMain.addBox(-3F, -3F, -2F, 6, 6, 4, f);
        wolfHeadMain.setRotationPoint(-1F, f1, -7F);
        wolfBody = new ModelRenderer(this, 18, 14);
        wolfBody.addBox(-4F, -2F, -3F, 6, 9, 6, f);
        wolfBody.setRotationPoint(0.0F, 14F, 2.0F);
        wolfMane = new ModelRenderer(this, 21, 0);
        wolfMane.addBox(-4F, -3F, -3F, 8, 6, 7, f);
        wolfMane.setRotationPoint(-1F, 14F, 2.0F);
        wolfLeg1 = new ModelRenderer(this, 0, 18);
        wolfLeg1.addBox(-1F, 0.0F, -1F, 2, 8, 2, f);
        wolfLeg1.setRotationPoint(-2.5F, 16F, 7F);
        wolfLeg2 = new ModelRenderer(this, 0, 18);
        wolfLeg2.addBox(-1F, 0.0F, -1F, 2, 8, 2, f);
        wolfLeg2.setRotationPoint(0.5F, 16F, 7F);
        wolfLeg3 = new ModelRenderer(this, 0, 18);
        wolfLeg3.addBox(-1F, 0.0F, -1F, 2, 8, 2, f);
        wolfLeg3.setRotationPoint(-2.5F, 16F, -4F);
        wolfLeg4 = new ModelRenderer(this, 0, 18);
        wolfLeg4.addBox(-1F, 0.0F, -1F, 2, 8, 2, f);
        wolfLeg4.setRotationPoint(0.5F, 16F, -4F);
        wolfTail = new ModelRenderer(this, 9, 18);
        wolfTail.addBox(-1F, 0.0F, -1F, 2, 8, 2, f);
        wolfTail.setRotationPoint(-1F, 12F, 8F);
        wolfHeadMain.setTextureOffset(16, 14).addBox(-3F, -5F, 0.0F, 2, 2, 1, f);
        wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5F, 0.0F, 2, 2, 1, f);
        wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5F, 3, 3, 4, f);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        setRotationAngles(par2, par3, par4, par5, par6, par7);

        if (isChild)
        {
            float f = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 5F * par7, 2.0F * par7);
            wolfHeadMain.renderWithRotation(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
            GL11.glTranslatef(0.0F, 24F * par7, 0.0F);
            wolfBody.render(par7);
            wolfLeg1.render(par7);
            wolfLeg2.render(par7);
            wolfLeg3.render(par7);
            wolfLeg4.render(par7);
            wolfTail.renderWithRotation(par7);
            wolfMane.render(par7);
            GL11.glPopMatrix();
        }
        else
        {
            wolfHeadMain.renderWithRotation(par7);
            wolfBody.render(par7);
            wolfLeg1.render(par7);
            wolfLeg2.render(par7);
            wolfLeg3.render(par7);
            wolfLeg4.render(par7);
            wolfTail.renderWithRotation(par7);
            wolfMane.render(par7);
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        EntityWolf entitywolf = (EntityWolf)par1EntityLiving;

        if (entitywolf.isAngry())
        {
            wolfTail.rotateAngleY = 0.0F;
        }
        else
        {
            wolfTail.rotateAngleY = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
        }

        if (entitywolf.isSitting())
        {
            wolfMane.setRotationPoint(-1F, 16F, -3F);
            wolfMane.rotateAngleX = ((float)Math.PI * 2F / 5F);
            wolfMane.rotateAngleY = 0.0F;
            wolfBody.setRotationPoint(0.0F, 18F, 0.0F);
            wolfBody.rotateAngleX = ((float)Math.PI / 4F);
            wolfTail.setRotationPoint(-1F, 21F, 6F);
            wolfLeg1.setRotationPoint(-2.5F, 22F, 2.0F);
            wolfLeg1.rotateAngleX = ((float)Math.PI * 3F / 2F);
            wolfLeg2.setRotationPoint(0.5F, 22F, 2.0F);
            wolfLeg2.rotateAngleX = ((float)Math.PI * 3F / 2F);
            wolfLeg3.rotateAngleX = 5.811947F;
            wolfLeg3.setRotationPoint(-2.49F, 17F, -4F);
            wolfLeg4.rotateAngleX = 5.811947F;
            wolfLeg4.setRotationPoint(0.51F, 17F, -4F);
        }
        else
        {
            wolfBody.setRotationPoint(0.0F, 14F, 2.0F);
            wolfBody.rotateAngleX = ((float)Math.PI / 2F);
            wolfMane.setRotationPoint(-1F, 14F, -3F);
            wolfMane.rotateAngleX = wolfBody.rotateAngleX;
            wolfTail.setRotationPoint(-1F, 12F, 8F);
            wolfLeg1.setRotationPoint(-2.5F, 16F, 7F);
            wolfLeg2.setRotationPoint(0.5F, 16F, 7F);
            wolfLeg3.setRotationPoint(-2.5F, 16F, -4F);
            wolfLeg4.setRotationPoint(0.5F, 16F, -4F);
            wolfLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
            wolfLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
            wolfLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
            wolfLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
        }

        wolfHeadMain.rotateAngleZ = entitywolf.getInterestedAngle(par4) + entitywolf.getShakeAngle(par4, 0.0F);
        wolfMane.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.08F);
        wolfBody.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.16F);
        wolfTail.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.2F);

        if (entitywolf.getWolfShaking())
        {
            float f = entitywolf.getBrightness(par4) * entitywolf.getShadingWhileShaking(par4);
            GL11.glColor3f(f, f, f);
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
        wolfHeadMain.rotateAngleX = par5 / (180F / (float)Math.PI);
        wolfHeadMain.rotateAngleY = par4 / (180F / (float)Math.PI);
        wolfTail.rotateAngleX = par3;
    }
}
