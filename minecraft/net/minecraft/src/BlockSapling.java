package net.minecraft.src;

import java.util.Random;

public class BlockSapling extends BlockFlower
{
    protected BlockSapling(int par1, int par2)
    {
        super(par1, par2);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isRemote)
        {
            return;
        }

        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);

            if ((i & 8) == 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 8);
            }
            else
            {
                growTree(par1World, par2, par3, par4, par5Random);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        par2 &= 3;

        if (par2 == 1)
        {
            return 63;
        }

        if (par2 == 2)
        {
            return 79;
        }

        if (par2 == 3)
        {
            return 30;
        }
        else
        {
            return super.getBlockTextureFromSideAndMetadata(par1, par2);
        }
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4) & 3;
        Object obj = null;
        int j = 0;
        int k = 0;
        boolean flag = false;

        if (i == 1)
        {
            obj = new WorldGenTaiga2(true);
        }
        else if (i == 2)
        {
            obj = new WorldGenForest(true);
        }
        else if (i == 3)
        {
            j = 0;

            do
            {
                if (j < -1)
                {
                    break;
                }

                k = 0;

                do
                {
                    if (k < -1)
                    {
                        break;
                    }

                    if (func_50076_f(par1World, par2 + j, par3, par4 + k, 3) && func_50076_f(par1World, par2 + j + 1, par3, par4 + k, 3) && func_50076_f(par1World, par2 + j, par3, par4 + k + 1, 3) && func_50076_f(par1World, par2 + j + 1, par3, par4 + k + 1, 3))
                    {
                        obj = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
                        flag = true;
                        break;
                    }

                    k--;
                }
                while (true);

                if (obj != null)
                {
                    break;
                }

                j--;
            }
            while (true);

            if (obj == null)
            {
                j = k = 0;
                obj = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
            }
        }
        else
        {
            obj = new WorldGenTrees(true);

            if (par5Random.nextInt(10) == 0)
            {
                obj = new WorldGenBigTree(true);
            }
        }

        if (flag)
        {
            par1World.setBlock(par2 + j, par3, par4 + k, 0);
            par1World.setBlock(par2 + j + 1, par3, par4 + k, 0);
            par1World.setBlock(par2 + j, par3, par4 + k + 1, 0);
            par1World.setBlock(par2 + j + 1, par3, par4 + k + 1, 0);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, 0);
        }

        if (!((WorldGenerator)(obj)).generate(par1World, par5Random, par2 + j, par3, par4 + k))
        {
            if (flag)
            {
                par1World.setBlockAndMetadata(par2 + j, par3, par4 + k, blockID, i);
                par1World.setBlockAndMetadata(par2 + j + 1, par3, par4 + k, blockID, i);
                par1World.setBlockAndMetadata(par2 + j, par3, par4 + k + 1, blockID, i);
                par1World.setBlockAndMetadata(par2 + j + 1, par3, par4 + k + 1, blockID, i);
            }
            else
            {
                par1World.setBlockAndMetadata(par2, par3, par4, blockID, i);
            }
        }
    }

    public boolean func_50076_f(World par1World, int par2, int par3, int par4, int par5)
    {
        return par1World.getBlockId(par2, par3, par4) == blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1 & 3;
    }
}
