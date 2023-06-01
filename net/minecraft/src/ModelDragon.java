package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ModelDragon extends ModelBase
{
    /** The head Model renderer of the dragon */
    private ModelRenderer head;

    /** The neck Model renderer of the dragon */
    private ModelRenderer neck;

    /** The jaw Model renderer of the dragon */
    private ModelRenderer jaw;

    /** The body Model renderer of the dragon */
    private ModelRenderer body;

    /** The rear leg Model renderer of the dragon */
    private ModelRenderer rearLeg;

    /** The front leg Model renderer of the dragon */
    private ModelRenderer frontLeg;

    /** The rear leg tip Model renderer of the dragon */
    private ModelRenderer rearLegTip;

    /** The front leg tip Model renderer of the dragon */
    private ModelRenderer frontLegTip;

    /** The rear foot Model renderer of the dragon */
    private ModelRenderer rearFoot;

    /** The front foot Model renderer of the dragon */
    private ModelRenderer frontFoot;

    /** The wing Model renderer of the dragon */
    private ModelRenderer wing;

    /** The wing tip Model renderer of the dragon */
    private ModelRenderer wingTip;
    private float field_40317_s;

    public ModelDragon(float par1)
    {
        textureWidth = 256;
        textureHeight = 256;
        setTextureOffset("body.body", 0, 0);
        setTextureOffset("wing.skin", -56, 88);
        setTextureOffset("wingtip.skin", -56, 144);
        setTextureOffset("rearleg.main", 0, 0);
        setTextureOffset("rearfoot.main", 112, 0);
        setTextureOffset("rearlegtip.main", 196, 0);
        setTextureOffset("head.upperhead", 112, 30);
        setTextureOffset("wing.bone", 112, 88);
        setTextureOffset("head.upperlip", 176, 44);
        setTextureOffset("jaw.jaw", 176, 65);
        setTextureOffset("frontleg.main", 112, 104);
        setTextureOffset("wingtip.bone", 112, 136);
        setTextureOffset("frontfoot.main", 144, 104);
        setTextureOffset("neck.box", 192, 104);
        setTextureOffset("frontlegtip.main", 226, 138);
        setTextureOffset("body.scale", 220, 53);
        setTextureOffset("head.scale", 0, 0);
        setTextureOffset("neck.scale", 48, 0);
        setTextureOffset("head.nostril", 112, 0);
        float f = -16F;
        head = new ModelRenderer(this, "head");
        head.addBox("upperlip", -6F, -1F, -8F + f, 12, 5, 16);
        head.addBox("upperhead", -8F, -8F, 6F + f, 16, 16, 16);
        head.mirror = true;
        head.addBox("scale", -5F, -12F, 12F + f, 2, 4, 6);
        head.addBox("nostril", -5F, -3F, -6F + f, 2, 2, 4);
        head.mirror = false;
        head.addBox("scale", 3F, -12F, 12F + f, 2, 4, 6);
        head.addBox("nostril", 3F, -3F, -6F + f, 2, 2, 4);
        jaw = new ModelRenderer(this, "jaw");
        jaw.setRotationPoint(0.0F, 4F, 8F + f);
        jaw.addBox("jaw", -6F, 0.0F, -16F, 12, 4, 16);
        head.addChild(jaw);
        neck = new ModelRenderer(this, "neck");
        neck.addBox("box", -5F, -5F, -5F, 10, 10, 10);
        neck.addBox("scale", -1F, -9F, -3F, 2, 4, 6);
        body = new ModelRenderer(this, "body");
        body.setRotationPoint(0.0F, 4F, 8F);
        body.addBox("body", -12F, 0.0F, -16F, 24, 24, 64);
        body.addBox("scale", -1F, -6F, -10F, 2, 6, 12);
        body.addBox("scale", -1F, -6F, 10F, 2, 6, 12);
        body.addBox("scale", -1F, -6F, 30F, 2, 6, 12);
        wing = new ModelRenderer(this, "wing");
        wing.setRotationPoint(-12F, 5F, 2.0F);
        wing.addBox("bone", -56F, -4F, -4F, 56, 8, 8);
        wing.addBox("skin", -56F, 0.0F, 2.0F, 56, 0, 56);
        wingTip = new ModelRenderer(this, "wingtip");
        wingTip.setRotationPoint(-56F, 0.0F, 0.0F);
        wingTip.addBox("bone", -56F, -2F, -2F, 56, 4, 4);
        wingTip.addBox("skin", -56F, 0.0F, 2.0F, 56, 0, 56);
        wing.addChild(wingTip);
        frontLeg = new ModelRenderer(this, "frontleg");
        frontLeg.setRotationPoint(-12F, 20F, 2.0F);
        frontLeg.addBox("main", -4F, -4F, -4F, 8, 24, 8);
        frontLegTip = new ModelRenderer(this, "frontlegtip");
        frontLegTip.setRotationPoint(0.0F, 20F, -1F);
        frontLegTip.addBox("main", -3F, -1F, -3F, 6, 24, 6);
        frontLeg.addChild(frontLegTip);
        frontFoot = new ModelRenderer(this, "frontfoot");
        frontFoot.setRotationPoint(0.0F, 23F, 0.0F);
        frontFoot.addBox("main", -4F, 0.0F, -12F, 8, 4, 16);
        frontLegTip.addChild(frontFoot);
        rearLeg = new ModelRenderer(this, "rearleg");
        rearLeg.setRotationPoint(-16F, 16F, 42F);
        rearLeg.addBox("main", -8F, -4F, -8F, 16, 32, 16);
        rearLegTip = new ModelRenderer(this, "rearlegtip");
        rearLegTip.setRotationPoint(0.0F, 32F, -4F);
        rearLegTip.addBox("main", -6F, -2F, 0.0F, 12, 32, 12);
        rearLeg.addChild(rearLegTip);
        rearFoot = new ModelRenderer(this, "rearfoot");
        rearFoot.setRotationPoint(0.0F, 31F, 4F);
        rearFoot.addBox("main", -9F, 0.0F, -20F, 18, 6, 24);
        rearLegTip.addChild(rearFoot);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        field_40317_s = par4;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        GL11.glPushMatrix();
        EntityDragon entitydragon = (EntityDragon)par1Entity;
        float f = entitydragon.field_40173_aw + (entitydragon.field_40172_ax - entitydragon.field_40173_aw) * field_40317_s;
        jaw.rotateAngleX = (float)(Math.sin(f * (float)Math.PI * 2.0F) + 1.0D) * 0.2F;
        float f1 = (float)(Math.sin(f * (float)Math.PI * 2.0F - 1.0F) + 1.0D);
        f1 = (f1 * f1 * 1.0F + f1 * 2.0F) * 0.05F;
        GL11.glTranslatef(0.0F, f1 - 2.0F, -3F);
        GL11.glRotatef(f1 * 2.0F, 1.0F, 0.0F, 0.0F);
        float f2 = -30F;
        float f4 = 0.0F;
        float f5 = 1.5F;
        double ad[] = entitydragon.func_40160_a(6, field_40317_s);
        float f6 = updateRotations(entitydragon.func_40160_a(5, field_40317_s)[0] - entitydragon.func_40160_a(10, field_40317_s)[0]);
        float f7 = updateRotations(entitydragon.func_40160_a(5, field_40317_s)[0] + (double)(f6 / 2.0F));
        f2 += 2.0F;
        float f8 = f * (float)Math.PI * 2.0F;
        f2 = 20F;
        float f3 = -12F;

        for (int i = 0; i < 5; i++)
        {
            double ad3[] = entitydragon.func_40160_a(5 - i, field_40317_s);
            float f10 = (float)Math.cos((float)i * 0.45F + f8) * 0.15F;
            neck.rotateAngleY = ((updateRotations(ad3[0] - ad[0]) * (float)Math.PI) / 180F) * f5;
            neck.rotateAngleX = f10 + (((float)(ad3[1] - ad[1]) * (float)Math.PI) / 180F) * f5 * 5F;
            neck.rotateAngleZ = ((-updateRotations(ad3[0] - (double)f7) * (float)Math.PI) / 180F) * f5;
            neck.rotationPointY = f2;
            neck.rotationPointZ = f3;
            neck.rotationPointX = f4;
            f2 = (float)((double)f2 + Math.sin(neck.rotateAngleX) * 10D);
            f3 = (float)((double)f3 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10D);
            f4 = (float)((double)f4 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10D);
            neck.render(par7);
        }

        head.rotationPointY = f2;
        head.rotationPointZ = f3;
        head.rotationPointX = f4;
        double ad1[] = entitydragon.func_40160_a(0, field_40317_s);
        head.rotateAngleY = ((updateRotations(ad1[0] - ad[0]) * (float)Math.PI) / 180F) * 1.0F;
        head.rotateAngleZ = ((-updateRotations(ad1[0] - (double)f7) * (float)Math.PI) / 180F) * 1.0F;
        head.render(par7);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f6 * f5 * 1.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -1F, 0.0F);
        body.rotateAngleZ = 0.0F;
        body.render(par7);

        for (int j = 0; j < 2; j++)
        {
            GL11.glEnable(GL11.GL_CULL_FACE);
            float f11 = f * (float)Math.PI * 2.0F;
            wing.rotateAngleX = 0.125F - (float)Math.cos(f11) * 0.2F;
            wing.rotateAngleY = 0.25F;
            wing.rotateAngleZ = (float)(Math.sin(f11) + 0.125D) * 0.8F;
            wingTip.rotateAngleZ = -(float)(Math.sin(f11 + 2.0F) + 0.5D) * 0.75F;
            rearLeg.rotateAngleX = 1.0F + f1 * 0.1F;
            rearLegTip.rotateAngleX = 0.5F + f1 * 0.1F;
            rearFoot.rotateAngleX = 0.75F + f1 * 0.1F;
            frontLeg.rotateAngleX = 1.3F + f1 * 0.1F;
            frontLegTip.rotateAngleX = -0.5F - f1 * 0.1F;
            frontFoot.rotateAngleX = 0.75F + f1 * 0.1F;
            wing.render(par7);
            frontLeg.render(par7);
            rearLeg.render(par7);
            GL11.glScalef(-1F, 1.0F, 1.0F);

            if (j == 0)
            {
                GL11.glCullFace(GL11.GL_FRONT);
            }
        }

        GL11.glPopMatrix();
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glDisable(GL11.GL_CULL_FACE);
        float f9 = -(float)Math.sin(f * (float)Math.PI * 2.0F) * 0.0F;
        f8 = f * (float)Math.PI * 2.0F;
        f2 = 10F;
        f3 = 60F;
        f4 = 0.0F;
        ad = entitydragon.func_40160_a(11, field_40317_s);

        for (int k = 0; k < 12; k++)
        {
            double ad2[] = entitydragon.func_40160_a(12 + k, field_40317_s);
            f9 = (float)((double)f9 + Math.sin((float)k * 0.45F + f8) * 0.05000000074505806D);
            neck.rotateAngleY = ((updateRotations(ad2[0] - ad[0]) * f5 + 180F) * (float)Math.PI) / 180F;
            neck.rotateAngleX = f9 + (((float)(ad2[1] - ad[1]) * (float)Math.PI) / 180F) * f5 * 5F;
            neck.rotateAngleZ = ((updateRotations(ad2[0] - (double)f7) * (float)Math.PI) / 180F) * f5;
            neck.rotationPointY = f2;
            neck.rotationPointZ = f3;
            neck.rotationPointX = f4;
            f2 = (float)((double)f2 + Math.sin(neck.rotateAngleX) * 10D);
            f3 = (float)((double)f3 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10D);
            f4 = (float)((double)f4 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10D);
            neck.render(par7);
        }

        GL11.glPopMatrix();
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6);
    }

    /**
     * Updates the rotations in the parameters for rotations greater than 180 degrees or less than -180 degrees. It adds
     * or subtracts 360 degrees, so that the appearance is the same, although the numbers are then simplified to range
     * -180 to 180
     */
    private float updateRotations(double par1)
    {
        for (; par1 >= 180D; par1 -= 360D) { }

        for (; par1 < -180D; par1 += 360D) { }

        return (float)par1;
    }
}
