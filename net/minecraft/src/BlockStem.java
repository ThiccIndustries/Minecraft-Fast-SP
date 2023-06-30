package net.minecraft.src;

import java.util.Random;

public class BlockStem extends BlockFlower
{
    /** Defines if it is a Melon or a Pumpkin that the stem is producing. */
    private Block fruitType;

    protected BlockStem(int par1, Block par2Block)
    {
        super(par1, 111);
        fruitType = par2Block;
        setTickRandomly(true);
        float f = 0.125F;
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
            float f = getGrowthModifier(par1World, par2, par3, par4);

            if (par5Random.nextInt((int)(25F / f) + 1) == 0)
            {
                int i = par1World.getBlockMetadata(par2, par3, par4);

                if (i < 7)
                {
                    i++;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
                }
                else
                {
                    if (par1World.getBlockId(par2 - 1, par3, par4) == fruitType.blockID)
                    {
                        return;
                    }

                    if (par1World.getBlockId(par2 + 1, par3, par4) == fruitType.blockID)
                    {
                        return;
                    }

                    if (par1World.getBlockId(par2, par3, par4 - 1) == fruitType.blockID)
                    {
                        return;
                    }

                    if (par1World.getBlockId(par2, par3, par4 + 1) == fruitType.blockID)
                    {
                        return;
                    }

                    int j = par5Random.nextInt(4);
                    int k = par2;
                    int l = par4;

                    if (j == 0)
                    {
                        k--;
                    }

                    if (j == 1)
                    {
                        k++;
                    }

                    if (j == 2)
                    {
                        l--;
                    }

                    if (j == 3)
                    {
                        l++;
                    }

                    int i1 = par1World.getBlockId(k, par3 - 1, l);

                    if (par1World.getBlockId(k, par3, l) == 0 && (i1 == Block.tilledField.blockID || i1 == Block.dirt.blockID || i1 == Block.grass.blockID))
                    {
                        par1World.setBlockWithNotify(k, par3, l, fruitType.blockID);
                    }
                }
            }
        }
    }

    public void fertilizeStem(World par1World, int par2, int par3, int par4)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 7);
    }

    private float getGrowthModifier(World par1World, int par2, int par3, int par4)
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
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        int i = par1 * 32;
        int j = 255 - par1 * 8;
        int k = par1 * 4;
        return i << 16 | j << 8 | k;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return getRenderColor(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return blockIndexInTexture;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float f = 0.125F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        maxY = (float)(par1IBlockAccess.getBlockMetadata(par2, par3, par4) * 2 + 2) / 16F;
        float f = 0.125F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)maxY, 0.5F + f);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 19;
    }

    public int func_35296_f(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if (i < 7)
        {
            return -1;
        }

        if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == fruitType.blockID)
        {
            return 0;
        }

        if (par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == fruitType.blockID)
        {
            return 1;
        }

        if (par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == fruitType.blockID)
        {
            return 2;
        }

        return par1IBlockAccess.getBlockId(par2, par3, par4 + 1) != fruitType.blockID ? -1 : 3;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (par1World.isRemote)
        {
            return;
        }

        Item item = null;

        if (fruitType == Block.pumpkin)
        {
            item = Item.pumpkinSeeds;
        }

        if (fruitType == Block.melon)
        {
            item = Item.melonSeeds;
        }

        for (int i = 0; i < 3; i++)
        {
            if (par1World.rand.nextInt(15) <= par5)
            {
                float f = 0.7F;
                float f1 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                float f2 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                float f3 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                EntityItem entityitem = new EntityItem(par1World, (float)par2 + f1, (float)par3 + f2, (float)par4 + f3, new ItemStack(item));
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
        if (par1 != 7);

        return -1;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
}
