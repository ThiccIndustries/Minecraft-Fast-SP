package net.minecraft.src;

import java.util.Random;

public class BlockTorch extends Block
{
    protected BlockTorch(int par1, int par2)
    {
        super(par1, par2, Material.circuits);
        setTickRandomly(true);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i)
    {
        return null;
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
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 2;
    }

    /**
     * Gets if we can place a torch on a block.
     */
    private boolean canPlaceTorchOn(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCubeDefault(par2, par3, par4, true))
        {
            return true;
        }

        int i = par1World.getBlockId(par2, par3, par4);

        if (i == Block.fence.blockID || i == Block.netherFence.blockID || i == Block.glass.blockID)
        {
            return true;
        }

        if (Block.blocksList[i] != null && (Block.blocksList[i] instanceof BlockStairs))
        {
            int j = par1World.getBlockMetadata(par2, par3, par4);

            if ((4 & j) != 0)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            return true;
        }

        if (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            return true;
        }

        if (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            return true;
        }

        if (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            return true;
        }

        return canPlaceTorchOn(par1World, par2, par3 - 1, par4);
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if (par5 == 1 && canPlaceTorchOn(par1World, par2, par3 - 1, par4))
        {
            i = 5;
        }

        if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            i = 4;
        }

        if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            i = 3;
        }

        if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            i = 2;
        }

        if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            i = 1;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            onBlockAdded(par1World, par2, par3, par4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
        }
        else if (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }
        else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }
        else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
        else if (canPlaceTorchOn(par1World, par2, par3 - 1, par4))
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        dropTorchIfCantStay(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (dropTorchIfCantStay(par1World, par2, par3, par4))
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);
            boolean flag = false;

            if (!par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) && i == 1)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) && i == 2)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) && i == 3)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) && i == 4)
            {
                flag = true;
            }

            if (!canPlaceTorchOn(par1World, par2, par3 - 1, par4) && i == 5)
            {
                flag = true;
            }

            if (flag)
            {
                dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
        }
    }

    /**
     * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
     * True if it can stay and False if it drops. Args: world, x, y, z
     */
    private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
    {
        if (!canPlaceBlockAt(par1World, par2, par3, par4))
        {
            if (par1World.getBlockId(par2, par3, par4) == blockID)
            {
                dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3D par5Vec3D, Vec3D par6Vec3D)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4) & 7;
        float f = 0.15F;

        if (i == 1)
        {
            setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (i == 2)
        {
            setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (i == 3)
        {
            setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (i == 4)
        {
            setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
        else
        {
            float f1 = 0.1F;
            setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }

        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3D, par6Vec3D);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        double d = (float)par2 + 0.5F;
        double d1 = (float)par3 + 0.7F;
        double d2 = (float)par4 + 0.5F;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;

        if (i == 1)
        {
            par1World.spawnParticle("smoke", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 2)
        {
            par1World.spawnParticle("smoke", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 3)
        {
            par1World.spawnParticle("smoke", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 4)
        {
            par1World.spawnParticle("smoke", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            par1World.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
