package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderEnchantmentTable extends TileEntitySpecialRenderer
{
    private ModelBook field_40450_a;

    public RenderEnchantmentTable()
    {
        field_40450_a = new ModelBook();
    }

    public void func_40449_a(TileEntityEnchantmentTable par1TileEntityEnchantmentTable, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.75F, (float)par6 + 0.5F);
        float f = (float)par1TileEntityEnchantmentTable.tickCount + par8;
        GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
        float f1;

        for (f1 = par1TileEntityEnchantmentTable.bookRotation2 - par1TileEntityEnchantmentTable.bookRotationPrev; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) { }

        for (; f1 < -(float)Math.PI; f1 += ((float)Math.PI * 2F)) { }

        float f2 = par1TileEntityEnchantmentTable.bookRotationPrev + f1 * par8;
        GL11.glRotatef((-f2 * 180F) / (float)Math.PI, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(80F, 0.0F, 0.0F, 1.0F);
        bindTextureByName("/item/book.png");
        float f3 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.25F;
        float f4 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.75F;
        f3 = (f3 - (float)MathHelper.func_40346_b(f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float)MathHelper.func_40346_b(f4)) * 1.6F - 0.3F;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        float f5 = par1TileEntityEnchantmentTable.bookSpreadPrev + (par1TileEntityEnchantmentTable.bookSpread - par1TileEntityEnchantmentTable.bookSpreadPrev) * par8;
        field_40450_a.render(null, f, f3, f4, f5, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        func_40449_a((TileEntityEnchantmentTable)par1TileEntity, par2, par4, par6, par8);
    }
}
