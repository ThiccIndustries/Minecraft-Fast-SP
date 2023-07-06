package net.minecraft.src;

public class ItemLilyPad extends ItemColored
{
    public ItemLilyPad(int par1)
    {
        super(par1, false);
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

            if (par2World.getBlockMaterial(i, j, k) == Material.water && par2World.getBlockMetadata(i, j, k) == 0 && par2World.isAirBlock(i, j + 1, k))
            {
                par2World.setBlockWithNotify(i, j + 1, k, Block.waterlily.blockID);

                if (!par3EntityPlayer.capabilities.isCreativeMode)
                {
                    par1ItemStack.stackSize--;
                }
            }
        }

        return par1ItemStack;
    }

    public int getColorFromDamage(int par1, int par2)
    {
        return Block.waterlily.getRenderColor(par1);
    }
}
