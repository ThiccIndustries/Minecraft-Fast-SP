package net.minecraft.src;

import java.util.*;

public abstract class ModelBase
{
    public float onGround;
    public boolean isRiding;

    /**
     * This is a list of all the boxes (ModelRenderer.class) in the current model.
     */
    public List boxList;
    public boolean isChild;

    /** A mapping for all texture offsets */
    private Map modelTextureMap;
    public int textureWidth;
    public int textureHeight;

    public ModelBase()
    {
        isRiding = false;
        boxList = new ArrayList();
        isChild = true;
        modelTextureMap = new HashMap();
        textureWidth = 64;
        textureHeight = 32;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving entityliving, float f, float f1, float f2)
    {
    }

    protected void setTextureOffset(String par1Str, int par2, int par3)
    {
        modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
    }

    public TextureOffset getTextureOffset(String par1Str)
    {
        return (TextureOffset)modelTextureMap.get(par1Str);
    }
}
