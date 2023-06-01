package net.minecraft.src;

public class ItemBlock extends Item
{
    /** The block ID of the Block associated with this ItemBlock */
    private int blockID;

    public ItemBlock(int par1)
    {
        super(par1);
        blockID = par1 + 256;
        setIconIndex(Block.blocksList[par1 + 256].getBlockTextureFromSide(2));
    }

    /**
     * Returns the blockID for this Item
     */
    public int getBlockID()
    {
        return blockID;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        int i = par3World.getBlockId(par4, par5, par6);

        if (i == Block.snow.blockID)
        {
            par7 = 1;
        }
        else if (i != Block.vine.blockID && i != Block.tallGrass.blockID && i != Block.deadBush.blockID)
        {
            if (par7 == 0)
            {
                par5--;
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
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        if (par5 == 255 && Block.blocksList[blockID].blockMaterial.isSolid())
        {
            return false;
        }

        if (par3World.canBlockBePlacedAt(blockID, par4, par5, par6, false, par7))
        {
            Block block = Block.blocksList[blockID];

            if (par3World.setBlockAndMetadataWithNotify(par4, par5, par6, blockID, getMetadata(par1ItemStack.getItemDamage())))
            {
                if (par3World.getBlockId(par4, par5, par6) == blockID)
                {
                    Block.blocksList[blockID].onBlockPlaced(par3World, par4, par5, par6, par7);
                    Block.blocksList[blockID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer);
                }

                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize--;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return Block.blocksList[blockID].getBlockName();
    }

    public String getItemName()
    {
        return Block.blocksList[blockID].getBlockName();
    }
}
