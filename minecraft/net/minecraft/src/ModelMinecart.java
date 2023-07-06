package net.minecraft.src;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer sideModels[];

    public ModelMinecart()
    {
        sideModels = new ModelRenderer[7];
        sideModels[0] = new ModelRenderer(this, 0, 10);
        sideModels[1] = new ModelRenderer(this, 0, 0);
        sideModels[2] = new ModelRenderer(this, 0, 0);
        sideModels[3] = new ModelRenderer(this, 0, 0);
        sideModels[4] = new ModelRenderer(this, 0, 0);
        sideModels[5] = new ModelRenderer(this, 44, 10);
        byte byte0 = 20;
        byte byte1 = 8;
        byte byte2 = 16;
        int i = 4;
        sideModels[0].addBox(-byte0 / 2, -byte2 / 2, -1F, byte0, byte2, 2, 0.0F);
        sideModels[0].setRotationPoint(0.0F, i, 0.0F);
        sideModels[5].addBox(-byte0 / 2 + 1, -byte2 / 2 + 1, -1F, byte0 - 2, byte2 - 2, 1, 0.0F);
        sideModels[5].setRotationPoint(0.0F, i, 0.0F);
        sideModels[1].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        sideModels[1].setRotationPoint(-byte0 / 2 + 1, i, 0.0F);
        sideModels[2].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        sideModels[2].setRotationPoint(byte0 / 2 - 1, i, 0.0F);
        sideModels[3].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        sideModels[3].setRotationPoint(0.0F, i, -byte2 / 2 + 1);
        sideModels[4].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        sideModels[4].setRotationPoint(0.0F, i, byte2 / 2 - 1);
        sideModels[0].rotateAngleX = ((float)Math.PI / 2F);
        sideModels[1].rotateAngleY = ((float)Math.PI * 3F / 2F);
        sideModels[2].rotateAngleY = ((float)Math.PI / 2F);
        sideModels[3].rotateAngleY = (float)Math.PI;
        sideModels[5].rotateAngleX = -((float)Math.PI / 2F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        sideModels[5].rotationPointY = 4F - par4;

        for (int i = 0; i < 6; i++)
        {
            sideModels[i].render(par7);
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }
}
