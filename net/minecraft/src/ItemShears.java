package net.minecraft.src;

public class ItemShears extends Item
{
    public ItemShears(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setMaxDamage(238);
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
    {
        if (par2 == Block.leaves.blockID || par2 == Block.web.blockID || par2 == Block.tallGrass.blockID || par2 == Block.vine.blockID)
        {
            par1ItemStack.damageItem(1, par6EntityLiving);
            return true;
        }
        else
        {
            return super.onBlockDestroyed(par1ItemStack, par2, par3, par4, par5, par6EntityLiving);
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block.blockID == Block.web.blockID || par2Block.blockID == Block.leaves.blockID)
        {
            return 15F;
        }

        if (par2Block.blockID == Block.cloth.blockID)
        {
            return 5F;
        }
        else
        {
            return super.getStrVsBlock(par1ItemStack, par2Block);
        }
    }
}
