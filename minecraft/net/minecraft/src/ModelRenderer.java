package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ModelRenderer
{
    /** The size of the texture file's width in pixels. */
    public float textureWidth;

    /** The size of the texture file's height in pixels. */
    public float textureHeight;

    /** The X offset into the texture used for displaying this model */
    private int textureOffsetX;

    /** The Y offset into the texture used for displaying this model */
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;

    /** The GL display list rendered by the Tessellator for this model */
    private int displayList;
    public boolean mirror;
    public boolean showModel;

    /** Hides the model. */
    public boolean isHidden;
    public List cubeList;
    public List childModels;
    public final String boxName;
    private ModelBase baseModel;

    public ModelRenderer(ModelBase par1ModelBase, String par2Str)
    {
        textureWidth = 64F;
        textureHeight = 32F;
        compiled = false;
        displayList = 0;
        mirror = false;
        showModel = true;
        isHidden = false;
        cubeList = new ArrayList();
        baseModel = par1ModelBase;
        par1ModelBase.boxList.add(this);
        boxName = par2Str;
        setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
    }

    public ModelRenderer(ModelBase par1ModelBase)
    {
        this(par1ModelBase, null);
    }

    public ModelRenderer(ModelBase par1ModelBase, int par2, int par3)
    {
        this(par1ModelBase);
        setTextureOffset(par2, par3);
    }

    /**
     * Sets the current box's rotation points and rotation angles to another box.
     */
    public void addChild(ModelRenderer par1ModelRenderer)
    {
        if (childModels == null)
        {
            childModels = new ArrayList();
        }

        childModels.add(par1ModelRenderer);
    }

    public ModelRenderer setTextureOffset(int par1, int par2)
    {
        textureOffsetX = par1;
        textureOffsetY = par2;
        return this;
    }

    public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7)
    {
        par1Str = (new StringBuilder()).append(boxName).append(".").append(par1Str).toString();
        TextureOffset textureoffset = baseModel.getTextureOffset(par1Str);
        setTextureOffset(textureoffset.field_40734_a, textureoffset.field_40733_b);
        cubeList.add((new ModelBox(this, textureOffsetX, textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0F)).func_40671_a(par1Str));
        return this;
    }

    public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6)
    {
        cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0F));
        return this;
    }

    /**
     * Creates a textured box. Args: originX, originY, originZ, width, height, depth, scaleFactor.
     */
    public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
    {
        cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
    }

    public void setRotationPoint(float par1, float par2, float par3)
    {
        rotationPointX = par1;
        rotationPointY = par2;
        rotationPointZ = par3;
    }

    public void render(float par1)
    {
        if (isHidden)
        {
            return;
        }

        if (!showModel)
        {
            return;
        }

        if (!compiled)
        {
            compileDisplayList(par1);
        }

        if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

            if (rotateAngleZ != 0.0F)
            {
                GL11.glRotatef(rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (rotateAngleY != 0.0F)
            {
                GL11.glRotatef(rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotateAngleX != 0.0F)
            {
                GL11.glRotatef(rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

            GL11.glCallList(displayList);

            if (childModels != null)
            {
                for (int i = 0; i < childModels.size(); i++)
                {
                    ((ModelRenderer)childModels.get(i)).render(par1);
                }
            }

            GL11.glPopMatrix();
        }
        else if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F)
        {
            GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
            GL11.glCallList(displayList);

            if (childModels != null)
            {
                for (int j = 0; j < childModels.size(); j++)
                {
                    ((ModelRenderer)childModels.get(j)).render(par1);
                }
            }

            GL11.glTranslatef(-rotationPointX * par1, -rotationPointY * par1, -rotationPointZ * par1);
        }
        else
        {
            GL11.glCallList(displayList);

            if (childModels != null)
            {
                for (int k = 0; k < childModels.size(); k++)
                {
                    ((ModelRenderer)childModels.get(k)).render(par1);
                }
            }
        }
    }

    public void renderWithRotation(float par1)
    {
        if (isHidden)
        {
            return;
        }

        if (!showModel)
        {
            return;
        }

        if (!compiled)
        {
            compileDisplayList(par1);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

        if (rotateAngleY != 0.0F)
        {
            GL11.glRotatef(rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (rotateAngleX != 0.0F)
        {
            GL11.glRotatef(rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        }

        if (rotateAngleZ != 0.0F)
        {
            GL11.glRotatef(rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
        }

        GL11.glCallList(displayList);
        GL11.glPopMatrix();
    }

    /**
     * Allows the changing of Angles after a box has been rendered
     */
    public void postRender(float par1)
    {
        if (isHidden)
        {
            return;
        }

        if (!showModel)
        {
            return;
        }

        if (!compiled)
        {
            compileDisplayList(par1);
        }

        if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F)
        {
            GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

            if (rotateAngleZ != 0.0F)
            {
                GL11.glRotatef(rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (rotateAngleY != 0.0F)
            {
                GL11.glRotatef(rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotateAngleX != 0.0F)
            {
                GL11.glRotatef(rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }
        }
        else if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F)
        {
            GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
        }
    }

    /**
     * Compiles a GL display list for this model
     */
    private void compileDisplayList(float par1)
    {
        displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < cubeList.size(); i++)
        {
            ((ModelBox)cubeList.get(i)).render(tessellator, par1);
        }

        GL11.glEndList();
        compiled = true;
    }

    /**
     * Returns the model renderer with the new texture parameters.
     */
    public ModelRenderer setTextureSize(int par1, int par2)
    {
        textureWidth = par1;
        textureHeight = par2;
        return this;
    }
}
