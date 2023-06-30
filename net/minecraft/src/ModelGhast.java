package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class ModelGhast extends ModelBase
{
    ModelRenderer body;
    ModelRenderer tentacles[];

    public ModelGhast()
    {
        tentacles = new ModelRenderer[9];
        byte byte0 = -16;
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-8F, -8F, -8F, 16, 16, 16);
        body.rotationPointY += 24 + byte0;
        Random random = new Random(1660L);

        for (int i = 0; i < tentacles.length; i++)
        {
            tentacles[i] = new ModelRenderer(this, 0, 0);
            float f = (((((float)(i % 3) - (float)((i / 3) % 2) * 0.5F) + 0.25F) / 2.0F) * 2.0F - 1.0F) * 5F;
            float f1 = (((float)(i / 3) / 2.0F) * 2.0F - 1.0F) * 5F;
            int j = random.nextInt(7) + 8;
            tentacles[i].addBox(-1F, 0.0F, -1F, 2, j, 2);
            tentacles[i].rotationPointX = f;
            tentacles[i].rotationPointZ = f1;
            tentacles[i].rotationPointY = 31 + byte0;
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        for (int i = 0; i < tentacles.length; i++)
        {
            tentacles[i].rotateAngleX = 0.2F * MathHelper.sin(par3 * 0.3F + (float)i) + 0.4F;
        }
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.6F, 0.0F);
        body.render(par7);
        ModelRenderer amodelrenderer[] = tentacles;
        int i = amodelrenderer.length;

        for (int j = 0; j < i; j++)
        {
            ModelRenderer modelrenderer = amodelrenderer[j];
            modelrenderer.render(par7);
        }

        GL11.glPopMatrix();
    }
}
