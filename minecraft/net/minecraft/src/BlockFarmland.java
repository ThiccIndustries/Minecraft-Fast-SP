package net.minecraft.src;

import java.util.Random;

public class BlockFarmland extends Block
{
    protected BlockFarmland(int par1)
    {
        super(par1, Material.ground);
        blockIndexInTexture = 87;
        setTickRandomly(true);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        setLightOpacity(255);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getBoundingBoxFromPool(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 1 && par2 > 0)
        {
            return blockIndexInTexture - 1;
        }

        if (par1 == 1)
        {
            return blockIndexInTexture;
        }
        else
        {
            return 2;
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (isWaterNearby(par1World, par2, par3, par4) || par1World.canLightningStrikeAt(par2, par3 + 1, par4))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 7);
        }
        else
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);

            if (i > 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, i - 1);
            }
            else if (!isCropsNearby(par1World, par2, par3, par4))
            {
                par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
            }
        }
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (par1World.rand.nextFloat() < par6 - 0.5F)
        {
            par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
        }
    }

    /**
     * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
     */
    private boolean isCropsNearby(World par1World, int par2, int par3, int par4)
    {
        int i = 0;

        for (int j = par2 - i; j <= par2 + i; j++)
        {
            for (int k = par4 - i; k <= par4 + i; k++)
            {
                int l = par1World.getBlockId(j, par3 + 1, k);

                if (l == Block.crops.blockID || l == Block.melonStem.blockID || l == Block.pumpkinStem.blockID)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * returns true if there's water nearby (x-4 to x+4, y to y+1, k-4 to k+4)
     */
    private boolean isWaterNearby(World par1World, int par2, int par3, int par4)
    {
        for (int i = par2 - 4; i <= par2 + 4; i++)
        {
            for (int j = par3; j <= par3 + 1; j++)
            {
                for (int k = par4 - 4; k <= par4 + 4; k++)
                {
                    if (par1World.getBlockMaterial(i, j, k) == Material.water)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        Material material = par1World.getBlockMaterial(par2, par3 + 1, par4);

        if (material.isSolid())
        {
            par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
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
