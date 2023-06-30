package net.minecraft.src;

public class ModelBoat extends ModelBase
{
    public ModelRenderer boatSides[];

    public ModelBoat()
    {
        boatSides = new ModelRenderer[5];
        boatSides[0] = new ModelRenderer(this, 0, 8);
        boatSides[1] = new ModelRenderer(this, 0, 0);
        boatSides[2] = new ModelRenderer(this, 0, 0);
        boatSides[3] = new ModelRenderer(this, 0, 0);
        boatSides[4] = new ModelRenderer(this, 0, 0);
        byte byte0 = 24;
        byte byte1 = 6;
        byte byte2 = 20;
        int i = 4;
        boatSides[0].addBox(-byte0 / 2, -byte2 / 2 + 2, -3F, byte0, byte2 - 4, 4, 0.0F);
        boatSides[0].setRotationPoint(0.0F, i, 0.0F);
        boatSides[1].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        boatSides[1].setRotationPoint(-byte0 / 2 + 1, i, 0.0F);
        boatSides[2].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        boatSides[2].setRotationPoint(byte0 / 2 - 1, i, 0.0F);
        boatSides[3].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        boatSides[3].setRotationPoint(0.0F, i, -byte2 / 2 + 1);
        boatSides[4].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2, 0.0F);
        boatSides[4].setRotationPoint(0.0F, i, byte2 / 2 - 1);
        boatSides[0].rotateAngleX = ((float)Math.PI / 2F);
        boatSides[1].rotateAngleY = ((float)Math.PI * 3F / 2F);
        boatSides[2].rotateAngleY = ((float)Math.PI / 2F);
        boatSides[3].rotateAngleY = (float)Math.PI;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        for (int i = 0; i < 5; i++)
        {
            boatSides[i].render(par7);
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }
}
