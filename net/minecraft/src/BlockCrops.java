package net.minecraft.src;

import java.util.Random;

public class BlockCrops extends BlockFlower
{
    protected BlockCrops(int par1, int par2)
    {
        super(par1, par2);
        blockIndexInTexture = par2;
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return par1 == Block.tilledField.blockID;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);

            if (i < 7)
            {
                float f = getGrowthRate(par1World, par2, par3, par4);

                if (par5Random.nextInt((int)(25F / f) + 1) == 0)
                {
                    i++;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
                }
            }
        }
    }

    /**
     * Apply bonemeal to the crops.
     */
    public void fertilize(World par1World, int par2, int par3, int par4)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 7);
    }

    /**
     * Gets the growth rate for the crop. Setup to encourage rows by halving growth rate if there is diagonals, crops on
     * different sides that aren't opposing, and by adding growth for every crop next to this one (and for crop below
     * this one). Args: x, y, z
     */
    private float getGrowthRate(World par1World, int par2, int par3, int par4)
    {
        float f = 1.0F;
        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);
        int i1 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
        int j1 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
        int k1 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
        int l1 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
        boolean flag = k == blockID || l == blockID;
        boolean flag1 = i == blockID || j == blockID;
        boolean flag2 = i1 == blockID || j1 == blockID || k1 == blockID || l1 == blockID;

        for (int i2 = par2 - 1; i2 <= par2 + 1; i2++)
        {
            for (int j2 = par4 - 1; j2 <= par4 + 1; j2++)
            {
                int k2 = par1World.getBlockId(i2, par3 - 1, j2);
                float f1 = 0.0F;

                if (k2 == Block.tilledField.blockID)
                {
                    f1 = 1.0F;

                    if (par1World.getBlockMetadata(i2, par3 - 1, j2) > 0)
                    {
                        f1 = 3F;
                    }
                }

                if (i2 != par2 || j2 != par4)
                {
                    f1 /= 4F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par2 < 0)
        {
            par2 = 7;
        }

        return blockIndexInTexture + par2;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 6;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);

        if (par1World.isRemote)
        {
            return;
        }

        int i = 3 + par7;

        for (int j = 0; j < i; j++)
        {
            if (par1World.rand.nextInt(15) <= par5)
            {
                float f = 0.7F;
                float f1 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                float f2 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                float f3 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                EntityItem entityitem = new EntityItem(par1World, (float)par2 + f1, (float)par3 + f2, (float)par4 + f3, new ItemStack(Item.seeds));
                entityitem.delayBeforeCanPickup = 10;
                par1World.spawnEntityInWorld(entityitem);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        if (par1 == 7)
        {
            return Item.wheat.shiftedIndex;
        }
        else
        {
            return -1;
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
}
