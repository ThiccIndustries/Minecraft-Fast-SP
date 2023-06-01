package net.minecraft.src;

public class ItemSign extends Item
{
    public ItemSign(int par1)
    {
        super(par1);
        maxStackSize = 1;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        if (par7 == 0)
        {
            return false;
        }

        if (!par3World.getBlockMaterial(par4, par5, par6).isSolid())
        {
            return false;
        }

        if (par7 == 1)
        {
            par5++;
        }

        if (par7 == 2)
        {
            par6--;
        }

        if (par7 == 3)
        {
            par6++;
        }

        if (par7 == 4)
        {
            par4--;
        }

        if (par7 == 5)
        {
            par4++;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        if (!Block.signPost.canPlaceBlockAt(par3World, par4, par5, par6))
        {
            return false;
        }

        if (par7 == 1)
        {
            int i = MathHelper.floor_double((double)(((par2EntityPlayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf;
            par3World.setBlockAndMetadataWithNotify(par4, par5, par6, Block.signPost.blockID, i);
        }
        else
        {
            par3World.setBlockAndMetadataWithNotify(par4, par5, par6, Block.signWall.blockID, par7);
        }

        par1ItemStack.stackSize--;
        TileEntitySign tileentitysign = (TileEntitySign)par3World.getBlockTileEntity(par4, par5, par6);

        if (tileentitysign != null)
        {
            par2EntityPlayer.displayGUIEditSign(tileentitysign);
        }

        return true;
    }
}
