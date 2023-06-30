package net.minecraft.src;

public class ItemBed extends Item
{
    public ItemBed(int par1)
    {
        super(par1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        if (par7 != 1)
        {
            return false;
        }

        par5++;
        BlockBed blockbed = (BlockBed)Block.bed;
        int i = MathHelper.floor_double((double)((par2EntityPlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        byte byte0 = 0;
        byte byte1 = 0;

        if (i == 0)
        {
            byte1 = 1;
        }

        if (i == 1)
        {
            byte0 = -1;
        }

        if (i == 2)
        {
            byte1 = -1;
        }

        if (i == 3)
        {
            byte0 = 1;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6) || !par2EntityPlayer.canPlayerEdit(par4 + byte0, par5, par6 + byte1))
        {
            return false;
        }

        if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + byte0, par5, par6 + byte1) && par3World.isBlockNormalCube(par4, par5 - 1, par6) && par3World.isBlockNormalCube(par4 + byte0, par5 - 1, par6 + byte1))
        {
            par3World.setBlockAndMetadataWithNotify(par4, par5, par6, blockbed.blockID, i);

            if (par3World.getBlockId(par4, par5, par6) == blockbed.blockID)
            {
                par3World.setBlockAndMetadataWithNotify(par4 + byte0, par5, par6 + byte1, blockbed.blockID, i + 8);
            }

            par1ItemStack.stackSize--;
            return true;
        }
        else
        {
            return false;
        }
    }
}
