package net.minecraft.src;

import java.util.Random;

public class BlockStationary extends BlockFluid
{
    protected BlockStationary(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setTickRandomly(false);

        if (par2Material == Material.lava)
        {
            setTickRandomly(true);
        }
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return blockMaterial != Material.lava;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);

        if (par1World.getBlockId(par2, par3, par4) == blockID)
        {
            setNotStationary(par1World, par2, par3, par4);
        }
    }

    /**
     * Changes the block ID to that of an updating fluid.
     */
    private void setNotStationary(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        par1World.editingBlocks = true;
        par1World.setBlockAndMetadata(par2, par3, par4, blockID - 1, i);
        par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
        par1World.scheduleBlockUpdate(par2, par3, par4, blockID - 1, tickRate());
        par1World.editingBlocks = false;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (blockMaterial == Material.lava)
        {
            int i = par5Random.nextInt(3);

            for (int j = 0; j < i; j++)
            {
                par2 += par5Random.nextInt(3) - 1;
                par3++;
                par4 += par5Random.nextInt(3) - 1;
                int l = par1World.getBlockId(par2, par3, par4);

                if (l == 0)
                {
                    if (isFlammable(par1World, par2 - 1, par3, par4) || isFlammable(par1World, par2 + 1, par3, par4) || isFlammable(par1World, par2, par3, par4 - 1) || isFlammable(par1World, par2, par3, par4 + 1) || isFlammable(par1World, par2, par3 - 1, par4) || isFlammable(par1World, par2, par3 + 1, par4))
                    {
                        par1World.setBlockWithNotify(par2, par3, par4, Block.fire.blockID);
                        return;
                    }

                    continue;
                }

                if (Block.blocksList[l].blockMaterial.blocksMovement())
                {
                    return;
                }
            }

            if (i == 0)
            {
                int k = par2;
                int i1 = par4;

                for (int j1 = 0; j1 < 3; j1++)
                {
                    par2 = (k + par5Random.nextInt(3)) - 1;
                    par4 = (i1 + par5Random.nextInt(3)) - 1;

                    if (par1World.isAirBlock(par2, par3 + 1, par4) && isFlammable(par1World, par2, par3, par4))
                    {
                        par1World.setBlockWithNotify(par2, par3 + 1, par4, Block.fire.blockID);
                    }
                }
            }
        }
    }

    /**
     * Checks to see if the block is flammable.
     */
    private boolean isFlammable(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMaterial(par2, par3, par4).getCanBurn();
    }
}
