package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBiped extends RenderLiving
{
    protected ModelBiped modelBipedMain;
    protected float field_40296_d;

    public RenderBiped(ModelBiped par1ModelBiped, float par2)
    {
        this(par1ModelBiped, par2, 1.0F);
        modelBipedMain = par1ModelBiped;
    }

    public RenderBiped(ModelBiped par1ModelBiped, float par2, float par3)
    {
        super(par1ModelBiped, par2);
        modelBipedMain = par1ModelBiped;
        field_40296_d = par3;
    }

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        super.renderEquippedItems(par1EntityLiving, par2);
        ItemStack itemstack = par1EntityLiving.getHeldItem();

        if (itemstack != null)
        {
            GL11.glPushMatrix();
            modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
            {
                float f = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f *= 0.75F;
                GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f, -f, f);
            }
            else if (itemstack.itemID == Item.bow.shiftedIndex)
            {
                float f1 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[itemstack.itemID].isFull3D())
            {
                float f2 = 0.625F;
                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f2, -f2, f2);
                GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                float f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f3, f3, f3);
                GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
            }

            renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, 0);

            if (itemstack.getItem().func_46058_c())
            {
                renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, 1);
            }

            GL11.glPopMatrix();
        }
    }
}
