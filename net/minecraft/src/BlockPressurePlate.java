package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPressurePlate extends Block
{
    /** The mob type that can trigger this pressure plate. */
    private EnumMobType triggerMobType;

    protected BlockPressurePlate(int par1, int par2, EnumMobType par3EnumMobType, Material par4Material)
    {
        super(par1, par2, par4Material);
        triggerMobType = par3EnumMobType;
        setTickRandomly(true);
        float f = 0.0625F;
        setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 20;
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

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i)
    {
        return true;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCube(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.fence.blockID;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        boolean flag = false;

        if (!par1World.isBlockNormalCube(par2, par3 - 1, par4) && par1World.getBlockId(par2, par3 - 1, par4) != Block.fence.blockID)
        {
            flag = true;
        }

        if (flag)
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
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

        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            return;
        }
        else
        {
            setStateIfMobInteractsWithPlate(par1World, par2, par3, par4);
            return;
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if (par1World.getBlockMetadata(par2, par3, par4) == 1)
        {
            return;
        }
        else
        {
            setStateIfMobInteractsWithPlate(par1World, par2, par3, par4);
            return;
        }
    }

    /**
     * Checks if there are mobs on the plate. If a mob is on the plate and it is off, it turns it on, and vice versa.
     */
    private void setStateIfMobInteractsWithPlate(World par1World, int par2, int par3, int par4)
    {
        boolean flag = par1World.getBlockMetadata(par2, par3, par4) == 1;
        boolean flag1 = false;
        float f = 0.125F;
        List list = null;

        if (triggerMobType == EnumMobType.everything)
        {
            list = par1World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
        }

        if (triggerMobType == EnumMobType.mobs)
        {
            list = par1World.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
        }

        if (triggerMobType == EnumMobType.players)
        {
            list = par1World.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
        }

        if (list.size() > 0)
        {
            flag1 = true;
        }

        if (flag1 && !flag)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!flag1 && flag)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag1)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if (i > 0)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        }

        super.onBlockRemoval(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean flag = par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1;
        float f = 0.0625F;

        if (flag)
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        }
        else
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getBlockMetadata(par2, par3, par4) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            return false;
        }
        else
        {
            return par5 == 1;
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;
        setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }
}
