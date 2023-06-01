package net.minecraft.src;

import java.util.Random;

public class BlockLever extends Block
{
    protected BlockLever(int par1, int par2)
    {
        super(par1, par2, Material.circuits);
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
        return 12;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1 && par1World.isBlockNormalCube(par2, par3 - 1, par4))
        {
            return true;
        }

        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            return true;
        }

        if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            return true;
        }

        if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            return true;
        }

        return par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            return true;
        }

        return par1World.isBlockNormalCube(par2, par3 - 1, par4);
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = i & 8;
        i &= 7;
        i = -1;

        if (par5 == 1 && par1World.isBlockNormalCube(par2, par3 - 1, par4))
        {
            i = 5 + par1World.rand.nextInt(2);
        }

        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            i = 4;
        }

        if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            i = 3;
        }

        if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            i = 2;
        }

        if (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            i = 1;
        }

        if (i == -1)
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return;
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i + j);
            return;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (checkIfAttachedToBlock(par1World, par2, par3, par4))
        {
            int i = par1World.getBlockMetadata(par2, par3, par4) & 7;
            boolean flag = false;

            if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && i == 1)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && i == 2)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && i == 3)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && i == 4)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3 - 1, par4) && i == 5)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3 - 1, par4) && i == 6)
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
     * Checks if the block is attached to another block. If it is not, it returns false and drops the block as an item.
     * If it is it returns true.
     */
    private boolean checkIfAttachedToBlock(World par1World, int par2, int par3, int par4)
    {
        if (!canPlaceBlockAt(par1World, par2, par3, par4))
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
        float f = 0.1875F;

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
            float f1 = 0.25F;
            setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (par1World.isRemote)
        {
            return true;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = i & 7;
        int k = 8 - (i & 8);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, j + k);
        par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
        par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, k <= 0 ? 0.5F : 0.6F);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);

        if (j == 1)
        {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
        }
        else if (j == 2)
        {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
        }
        else if (j == 3)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
        }
        else if (j == 4)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
        }
        else
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        }

        return true;
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if ((i & 8) > 0)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            int j = i & 7;

            if (j == 1)
            {
                par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
            }
            else if (j == 2)
            {
                par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
            }
            else if (j == 3)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
            }
            else if (j == 4)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
            }
            else
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            }
        }

        super.onBlockRemoval(par1World, par2, par3, par4);
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if ((i & 8) == 0)
        {
            return false;
        }

        int j = i & 7;

        if (j == 6 && par5 == 1)
        {
            return true;
        }

        if (j == 5 && par5 == 1)
        {
            return true;
        }

        if (j == 4 && par5 == 2)
        {
            return true;
        }

        if (j == 3 && par5 == 3)
        {
            return true;
        }

        if (j == 2 && par5 == 4)
        {
            return true;
        }

        return j == 1 && par5 == 5;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }
}
