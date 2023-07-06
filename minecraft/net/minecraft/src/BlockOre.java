package net.minecraft.src;

import java.util.Random;

public class BlockOre extends Block
{
    public BlockOre(int par1, int par2)
    {
        super(par1, par2, Material.rock);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        if (blockID == Block.oreCoal.blockID)
        {
            return Item.coal.shiftedIndex;
        }

        if (blockID == Block.oreDiamond.blockID)
        {
            return Item.diamond.shiftedIndex;
        }

        if (blockID == Block.oreLapis.blockID)
        {
            return Item.dyePowder.shiftedIndex;
        }
        else
        {
            return blockID;
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        if (blockID == Block.oreLapis.blockID)
        {
            return 4 + par1Random.nextInt(5);
        }
        else
        {
            return 1;
        }
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        if (par1 > 0 && blockID != idDropped(0, par2Random, par1))
        {
            int i = par2Random.nextInt(par1 + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return quantityDropped(par2Random) * (i + 1);
        }
        else
        {
            return quantityDropped(par2Random);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return blockID != Block.oreLapis.blockID ? 0 : 4;
    }
}
