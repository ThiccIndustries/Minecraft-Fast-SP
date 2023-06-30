package net.minecraft.src;

import java.util.Random;

public abstract class WorldGenerator
{
    /**
     * Sets wither or not the generator should notify blocks of blocks it changes. When the world is first generated,
     * this is false, when saplings grow, this is true.
     */
    private final boolean doBlockNotify;

    public WorldGenerator()
    {
        doBlockNotify = false;
    }

    public WorldGenerator(boolean par1)
    {
        doBlockNotify = par1;
    }

    public abstract boolean generate(World world, Random random, int i, int j, int k);

    /**
     * Rescales the generator settings, only used in WorldGenBigTree
     */
    public void setScale(double d, double d1, double d2)
    {
    }

    protected void func_50073_a(World par1World, int par2, int par3, int par4, int par5)
    {
        setBlockAndMetadata(par1World, par2, par3, par4, par5, 0);
    }

    /**
     * Sets the block in the world, notifying neighbors if enabled.
     */
    protected void setBlockAndMetadata(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (doBlockNotify)
        {
            par1World.setBlockAndMetadataWithNotify(par2, par3, par4, par5, par6);
        }
        else if (par1World.blockExists(par2, par3, par4) && par1World.getChunkFromBlockCoords(par2, par4).field_50120_o)
        {
            if (par1World.setBlockAndMetadata(par2, par3, par4, par5, par6))
            {
                par1World.markBlockNeedsUpdate(par2, par3, par4);
            }
        }
        else
        {
            par1World.setBlockAndMetadata(par2, par3, par4, par5, par6);
        }
    }
}
