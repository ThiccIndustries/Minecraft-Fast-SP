package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockDirectional
{
    public static final double repeaterTorchOffset[] =
    {
        -0.0625D, 0.0625D, 0.1875D, 0.3125D
    };
    private static final int repeaterState[] =
    {
        1, 2, 3, 4
    };

    /** Tells whether the repeater is powered or not */
    private final boolean isRepeaterPowered;

    protected BlockRedstoneRepeater(int par1, boolean par2)
    {
        super(par1, 6, Material.circuits);
        isRepeaterPowered = par2;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isBlockNormalCube(par2, par3 - 1, par4))
        {
            return false;
        }
        else
        {
            return super.canPlaceBlockAt(par1World, par2, par3, par4);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isBlockNormalCube(par2, par3 - 1, par4))
        {
            return false;
        }
        else
        {
            return super.canBlockStay(par1World, par2, par3, par4);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = ignoreTick(par1World, par2, par3, par4, i);

        if (isRepeaterPowered && !flag)
        {
            par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.redstoneRepeaterIdle.blockID, i);
        }
        else if (!isRepeaterPowered)
        {
            par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.redstoneRepeaterActive.blockID, i);

            if (!flag)
            {
                int j = (i & 0xc) >> 2;
                par1World.scheduleBlockUpdate(par2, par3, par4, Block.redstoneRepeaterActive.blockID, repeaterState[j] * 2);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 0)
        {
            return !isRepeaterPowered ? 115 : 99;
        }

        if (par1 == 1)
        {
            return !isRepeaterPowered ? 131 : 147;
        }
        else
        {
            return 5;
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 != 0 && par5 != 1;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 15;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return getBlockTextureFromSideAndMetadata(par1, 0);
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        return isPoweringTo(par1World, par2, par3, par4, par5);
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (!isRepeaterPowered)
        {
            return false;
        }

        int i = getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

        if (i == 0 && par5 == 3)
        {
            return true;
        }

        if (i == 1 && par5 == 4)
        {
            return true;
        }

        if (i == 2 && par5 == 2)
        {
            return true;
        }

        return i == 3 && par5 == 5;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!canBlockStay(par1World, par2, par3, par4))
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
            return;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = ignoreTick(par1World, par2, par3, par4, i);
        int j = (i & 0xc) >> 2;

        if (isRepeaterPowered && !flag)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, repeaterState[j] * 2);
        }
        else if (!isRepeaterPowered && flag)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, repeaterState[j] * 2);
        }
    }

    private boolean ignoreTick(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = getDirection(par5);

        switch (i)
        {
            case 0:
                return par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 + 1, 3) || par1World.getBlockId(par2, par3, par4 + 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 + 1) > 0;

            case 2:
                return par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 - 1, 2) || par1World.getBlockId(par2, par3, par4 - 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 - 1) > 0;

            case 3:
                return par1World.isBlockIndirectlyProvidingPowerTo(par2 + 1, par3, par4, 5) || par1World.getBlockId(par2 + 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 + 1, par3, par4) > 0;

            case 1:
                return par1World.isBlockIndirectlyProvidingPowerTo(par2 - 1, par3, par4, 4) || par1World.getBlockId(par2 - 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 - 1, par3, par4) > 0;
        }

        return false;
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = (i & 0xc) >> 2;
        j = j + 1 << 2 & 0xc;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, j | i & 3);
        return true;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = ((MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3) + 2) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
        boolean flag = ignoreTick(par1World, par2, par3, par4, i);

        if (flag)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 1);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        if (isRepeaterPowered)
        {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
        }

        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
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
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.redstoneRepeater.shiftedIndex;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!isRepeaterPowered)
        {
            return;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = getDirection(i);
        double d = (double)((float)par2 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d1 = (double)((float)par3 + 0.4F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d2 = (double)((float)par4 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d3 = 0.0D;
        double d4 = 0.0D;

        if (par5Random.nextInt(2) == 0)
        {
            switch (j)
            {
                case 0:
                    d4 = -0.3125D;
                    break;

                case 2:
                    d4 = 0.3125D;
                    break;

                case 3:
                    d3 = -0.3125D;
                    break;

                case 1:
                    d3 = 0.3125D;
                    break;
            }
        }
        else
        {
            int k = (i & 0xc) >> 2;

            switch (j)
            {
                case 0:
                    d4 = repeaterTorchOffset[k];
                    break;

                case 2:
                    d4 = -repeaterTorchOffset[k];
                    break;

                case 3:
                    d3 = repeaterTorchOffset[k];
                    break;

                case 1:
                    d3 = -repeaterTorchOffset[k];
                    break;
            }
        }

        par1World.spawnParticle("reddust", d + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
    }
}
