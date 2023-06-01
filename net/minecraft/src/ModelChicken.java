package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ModelChicken extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public ModelChicken()
    {
        int i = 16;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-2F, -6F, -2F, 4, 6, 3, 0.0F);
        head.setRotationPoint(0.0F, -1 + i, -4F);
        bill = new ModelRenderer(this, 14, 0);
        bill.addBox(-2F, -4F, -4F, 4, 2, 2, 0.0F);
        bill.setRotationPoint(0.0F, -1 + i, -4F);
        chin = new ModelRenderer(this, 14, 4);
        chin.addBox(-1F, -2F, -3F, 2, 2, 2, 0.0F);
        chin.setRotationPoint(0.0F, -1 + i, -4F);
        body = new ModelRenderer(this, 0, 9);
        body.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        body.setRotationPoint(0.0F, i, 0.0F);
        rightLeg = new ModelRenderer(this, 26, 0);
        rightLeg.addBox(-1F, 0.0F, -3F, 3, 5, 3);
        rightLeg.setRotationPoint(-2F, 3 + i, 1.0F);
        leftLeg = new ModelRenderer(this, 26, 0);
        leftLeg.addBox(-1F, 0.0F, -3F, 3, 5, 3);
        leftLeg.setRotationPoint(1.0F, 3 + i, 1.0F);
        rightWing = new ModelRenderer(this, 24, 13);
        rightWing.addBox(0.0F, 0.0F, -3F, 1, 4, 6);
        rightWing.setRotationPoint(-4F, -3 + i, 0.0F);
        leftWing = new ModelRenderer(this, 24, 13);
        leftWing.addBox(-1F, 0.0F, -3F, 1, 4, 6);
        leftWing.setRotationPoint(4F, -3 + i, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);

        if (isChild)
        {
            float f = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 5F * par7, 2.0F * par7);
            head.render(par7);
            bill.render(par7);
            chin.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
            GL11.glTranslatef(0.0F, 24F * par7, 0.0F);
            body.render(par7);
            rightLeg.render(par7);
            leftLeg.render(par7);
            rightWing.render(par7);
            leftWing.render(par7);
            GL11.glPopMatrix();
        }
        else
        {
            head.render(par7);
            bill.render(par7);
            chin.render(par7);
            body.render(par7);
            rightLeg.render(par7);
            leftLeg.render(par7);
            rightWing.render(par7);
            leftWing.render(par7);
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        head.rotateAngleX = -(par5 / (180F / (float)Math.PI));
        head.rotateAngleY = par4 / (180F / (float)Math.PI);
        bill.rotateAngleX = head.rotateAngleX;
        bill.rotateAngleY = head.rotateAngleY;
        chin.rotateAngleX = head.rotateAngleX;
        chin.rotateAngleY = head.rotateAngleY;
        body.rotateAngleX = ((float)Math.PI / 2F);
        rightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        leftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        rightWing.rotateAngleZ = par3;
        leftWing.rotateAngleZ = -par3;
    }
}
