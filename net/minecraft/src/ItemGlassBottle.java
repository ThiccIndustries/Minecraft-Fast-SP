package net.minecraft.src;

public class ItemGlassBottle extends Item
{
    public ItemGlassBottle(int par1)
    {
        super(par1);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (movingobjectposition == null)
        {
            return par1ItemStack;
        }

        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
            {
                return par1ItemStack;
            }

            if (!par3EntityPlayer.canPlayerEdit(i, j, k))
            {
                return par1ItemStack;
            }

            if (par2World.getBlockMaterial(i, j, k) == Material.water)
            {
                par1ItemStack.stackSize--;

                if (par1ItemStack.stackSize <= 0)
                {
                    return new ItemStack(Item.potion);
                }

                if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.potion)))
                {
                    par3EntityPlayer.dropPlayerItem(new ItemStack(Item.potion.shiftedIndex, 1, 0));
                }
            }
        }

        return par1ItemStack;
    }
}
