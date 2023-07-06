package net.minecraft.src;

import java.util.*;

public class BlockRedstoneWire extends Block
{
    /**
     * When false, power transmission methods do not look at other redstone wires.  Used internally during
     * updateCurrentStrength.
     */
    private boolean wiresProvidePower;
    private Set blocksNeedingUpdate;

    public BlockRedstoneWire(int par1, int par2)
    {
        super(par1, par2, Material.circuits);
        wiresProvidePower = true;
        blocksNeedingUpdate = new HashSet();
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return blockIndexInTexture;
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
        return 5;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 0x800000;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCube(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
    }

    /**
     * Sets the strength of the wire current (0-15) for this block based on neighboring blocks and propagates to
     * neighboring redstone wires
     */
    private void updateAndPropagateCurrentStrength(World par1World, int par2, int par3, int par4)
    {
        calculateCurrentChanges(par1World, par2, par3, par4, par2, par3, par4);
        ArrayList arraylist = new ArrayList(blocksNeedingUpdate);
        blocksNeedingUpdate.clear();

        for (int i = 0; i < arraylist.size(); i++)
        {
            ChunkPosition chunkposition = (ChunkPosition)arraylist.get(i);
            par1World.notifyBlocksOfNeighborChange(chunkposition.x, chunkposition.y, chunkposition.z, blockID);
        }
    }

    private void calculateCurrentChanges(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = 0;
        wiresProvidePower = false;
        boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
        wiresProvidePower = true;

        if (flag)
        {
            j = 15;
        }
        else
        {
            for (int k = 0; k < 4; k++)
            {
                int i1 = par2;
                int k1 = par4;

                if (k == 0)
                {
                    i1--;
                }

                if (k == 1)
                {
                    i1++;
                }

                if (k == 2)
                {
                    k1--;
                }

                if (k == 3)
                {
                    k1++;
                }

                if (i1 != par5 || par3 != par6 || k1 != par7)
                {
                    j = getMaxCurrentStrength(par1World, i1, par3, k1, j);
                }

                if (par1World.isBlockNormalCube(i1, par3, k1) && !par1World.isBlockNormalCube(par2, par3 + 1, par4))
                {
                    if (i1 != par5 || par3 + 1 != par6 || k1 != par7)
                    {
                        j = getMaxCurrentStrength(par1World, i1, par3 + 1, k1, j);
                    }

                    continue;
                }

                if (!par1World.isBlockNormalCube(i1, par3, k1) && (i1 != par5 || par3 - 1 != par6 || k1 != par7))
                {
                    j = getMaxCurrentStrength(par1World, i1, par3 - 1, k1, j);
                }
            }

            if (j > 0)
            {
                j--;
            }
            else
            {
                j = 0;
            }
        }

        if (i != j)
        {
            par1World.editingBlocks = true;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, j);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.editingBlocks = false;

            for (int l = 0; l < 4; l++)
            {
                int j1 = par2;
                int l1 = par4;
                int i2 = par3 - 1;

                if (l == 0)
                {
                    j1--;
                }

                if (l == 1)
                {
                    j1++;
                }

                if (l == 2)
                {
                    l1--;
                }

                if (l == 3)
                {
                    l1++;
                }

                if (par1World.isBlockNormalCube(j1, par3, l1))
                {
                    i2 += 2;
                }

                int j2 = 0;
                j2 = getMaxCurrentStrength(par1World, j1, par3, l1, -1);
                j = par1World.getBlockMetadata(par2, par3, par4);

                if (j > 0)
                {
                    j--;
                }

                if (j2 >= 0 && j2 != j)
                {
                    calculateCurrentChanges(par1World, j1, par3, l1, par2, par3, par4);
                }

                j2 = getMaxCurrentStrength(par1World, j1, i2, l1, -1);
                j = par1World.getBlockMetadata(par2, par3, par4);

                if (j > 0)
                {
                    j--;
                }

                if (j2 >= 0 && j2 != j)
                {
                    calculateCurrentChanges(par1World, j1, i2, l1, par2, par3, par4);
                }
            }

            if (i < j || j == 0)
            {
                blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4));
                blocksNeedingUpdate.add(new ChunkPosition(par2 - 1, par3, par4));
                blocksNeedingUpdate.add(new ChunkPosition(par2 + 1, par3, par4));
                blocksNeedingUpdate.add(new ChunkPosition(par2, par3 - 1, par4));
                blocksNeedingUpdate.add(new ChunkPosition(par2, par3 + 1, par4));
                blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 - 1));
                blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 + 1));
            }
        }
    }

    /**
     * Calls World.notifyBlocksOfNeighborChange() for all neighboring blocks, but only if the given block is a redstone
     * wire.
     */
    private void notifyWireNeighborsOfNeighborChange(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockId(par2, par3, par4) != blockID)
        {
            return;
        }
        else
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
            return;
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

        if (par1World.isRemote)
        {
            return;
        }

        updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
        notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
        notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
        notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);

        if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
        }

        if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        super.onBlockRemoval(par1World, par2, par3, par4);

        if (par1World.isRemote)
        {
            return;
        }

        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
        updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
        notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
        notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
        notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
        notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);

        if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
        }

        if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
        }
        else
        {
            notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
        }
    }

    /**
     * Returns the current strength at the specified block if it is greater than the passed value, or the passed value
     * otherwise.  Signature: (world, x, y, z, strength)
     */
    private int getMaxCurrentStrength(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getBlockId(par2, par3, par4) != blockID)
        {
            return par5;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);

        if (i > par5)
        {
            return i;
        }
        else
        {
            return par5;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.isRemote)
        {
            return;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = canPlaceBlockAt(par1World, par2, par3, par4);

        if (!flag)
        {
            dropBlockAsItem(par1World, par2, par3, par4, i, 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
        else
        {
            updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
        }

        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.redstone.shiftedIndex;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!wiresProvidePower)
        {
            return false;
        }
        else
        {
            return isPoweringTo(par1World, par2, par3, par4, par5);
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (!wiresProvidePower)
        {
            return false;
        }

        if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 0)
        {
            return false;
        }

        if (par5 == 1)
        {
            return true;
        }

        boolean flag = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
        boolean flag1 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
        boolean flag2 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
        boolean flag3 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);

        if (!par1IBlockAccess.isBlockNormalCube(par2, par3 + 1, par4))
        {
            if (par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1))
            {
                flag = true;
            }

            if (par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1))
            {
                flag1 = true;
            }

            if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1))
            {
                flag2 = true;
            }

            if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1))
            {
                flag3 = true;
            }
        }

        if (!flag2 && !flag1 && !flag && !flag3 && par5 >= 2 && par5 <= 5)
        {
            return true;
        }

        if (par5 == 2 && flag2 && !flag && !flag1)
        {
            return true;
        }

        if (par5 == 3 && flag3 && !flag && !flag1)
        {
            return true;
        }

        if (par5 == 4 && flag && !flag2 && !flag3)
        {
            return true;
        }

        return par5 == 5 && flag1 && !flag2 && !flag3;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return wiresProvidePower;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if (i > 0)
        {
            double d = (double)par2 + 0.5D + ((double)par5Random.nextFloat() - 0.5D) * 0.20000000000000001D;
            double d1 = (float)par3 + 0.0625F;
            double d2 = (double)par4 + 0.5D + ((double)par5Random.nextFloat() - 0.5D) * 0.20000000000000001D;
            float f = (float)i / 15F;
            float f1 = f * 0.6F + 0.4F;

            if (i == 0)
            {
                f1 = 0.0F;
            }

            float f2 = f * f * 0.7F - 0.5F;
            float f3 = f * f * 0.6F - 0.7F;

            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }

            if (f3 < 0.0F)
            {
                f3 = 0.0F;
            }

            par1World.spawnParticle("reddust", d, d1, d2, f1, f2, f3);
        }
    }

    /**
     * Returns true if the block coordinate passed can provide power, or is a redstone wire.
     */
    public static boolean isPowerProviderOrWire(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        int i = par0IBlockAccess.getBlockId(par1, par2, par3);

        if (i == Block.redstoneWire.blockID)
        {
            return true;
        }

        if (i == 0)
        {
            return false;
        }

        if (i == Block.redstoneRepeaterIdle.blockID || i == Block.redstoneRepeaterActive.blockID)
        {
            int j = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
            return par4 == (j & 3) || par4 == Direction.footInvisibleFaceRemap[j & 3];
        }

        return Block.blocksList[i].canProvidePower() && par4 != -1;
    }

    /**
     * Returns true if the block coordinate passed can provide power, or is a redstone wire, or if its a repeater that
     * is powered.
     */
    public static boolean isPoweredOrRepeater(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        if (isPowerProviderOrWire(par0IBlockAccess, par1, par2, par3, par4))
        {
            return true;
        }

        int i = par0IBlockAccess.getBlockId(par1, par2, par3);

        if (i == Block.redstoneRepeaterActive.blockID)
        {
            int j = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
            return par4 == (j & 3);
        }
        else
        {
            return false;
        }
    }
}
