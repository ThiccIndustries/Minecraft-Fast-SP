package net.minecraft.src;

public class ModelSquid extends ModelBase
{
    /** The squid's body */
    ModelRenderer squidBody;
    ModelRenderer squidTentacles[];

    public ModelSquid()
    {
        squidTentacles = new ModelRenderer[8];
        byte byte0 = -16;
        squidBody = new ModelRenderer(this, 0, 0);
        squidBody.addBox(-6F, -8F, -6F, 12, 16, 12);
        squidBody.rotationPointY += 24 + byte0;

        for (int i = 0; i < squidTentacles.length; i++)
        {
            squidTentacles[i] = new ModelRenderer(this, 48, 0);
            double d = ((double)i * Math.PI * 2D) / (double)squidTentacles.length;
            float f = (float)Math.cos(d) * 5F;
            float f1 = (float)Math.sin(d) * 5F;
            squidTentacles[i].addBox(-1F, 0.0F, -1F, 2, 18, 2);
            squidTentacles[i].rotationPointX = f;
            squidTentacles[i].rotationPointZ = f1;
            squidTentacles[i].rotationPointY = 31 + byte0;
            d = ((double)i * Math.PI * -2D) / (double)squidTentacles.length + (Math.PI / 2D);
            squidTentacles[i].rotateAngleY = (float)d;
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        ModelRenderer amodelrenderer[] = squidTentacles;
        int i = amodelrenderer.length;

        for (int j = 0; j < i; j++)
        {
            ModelRenderer modelrenderer = amodelrenderer[j];
            modelrenderer.rotateAngleX = par3;
        }
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        squidBody.render(par7);

        for (int i = 0; i < squidTentacles.length; i++)
        {
            squidTentacles[i].render(par7);
        }
    }
}
