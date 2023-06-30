package net.minecraft.src;

import java.util.Random;

public class BlockMycelium extends Block
{
    protected BlockMycelium(int par1)
    {
        super(par1, Material.grass);
        blockIndexInTexture = 77;
        setTickRandomly(true);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 1)
        {
            return 78;
        }

        return par1 != 0 ? 77 : 2;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return 78;
        }

        if (par5 == 0)
        {
            return 2;
        }

        Material material = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
        return material != Material.snow && material != Material.craftedSnow ? 77 : 68;
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

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2)
        {
            par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
        }
        else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            for (int i = 0; i < 4; i++)
            {
                int j = (par2 + par5Random.nextInt(3)) - 1;
                int k = (par3 + par5Random.nextInt(5)) - 3;
                int l = (par4 + par5Random.nextInt(3)) - 1;
                int i1 = par1World.getBlockId(j, k + 1, l);

                if (par1World.getBlockId(j, k, l) == Block.dirt.blockID && par1World.getBlockLightValue(j, k + 1, l) >= 4 && Block.lightOpacity[i1] <= 2)
                {
                    par1World.setBlockWithNotify(j, k, l, blockID);
                }
            }
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        if (par5Random.nextInt(10) == 0)
        {
            par1World.spawnParticle("townaura", (float)par2 + par5Random.nextFloat(), (float)par3 + 1.1F, (float)par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dirt.idDropped(0, par2Random, par3);
    }
}
