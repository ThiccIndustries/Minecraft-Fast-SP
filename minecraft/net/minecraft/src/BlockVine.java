package net.minecraft.src;

import java.util.Random;

public class BlockVine extends Block
{
    public BlockVine(int par1)
    {
        super(par1, 143, Material.vine);
        setTickRandomly(true);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 20;
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        boolean flag = i > 0;

        if ((i & 2) != 0)
        {
            f3 = Math.max(f3, 0.0625F);
            f = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((i & 8) != 0)
        {
            f = Math.min(f, 0.9375F);
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((i & 4) != 0)
        {
            f5 = Math.max(f5, 0.0625F);
            f2 = 0.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if ((i & 1) != 0)
        {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if (!flag && canBePlacedOn(par1IBlockAccess.getBlockId(par2, par3 + 1, par4)))
        {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
        }

        setBlockBounds(f, f1, f2, f3, f4, f5);
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
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        switch (par5)
        {
            default:
                return false;

            case 1:
                return canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4));

            case 2:
                return canBePlacedOn(par1World.getBlockId(par2, par3, par4 + 1));

            case 3:
                return canBePlacedOn(par1World.getBlockId(par2, par3, par4 - 1));

            case 5:
                return canBePlacedOn(par1World.getBlockId(par2 - 1, par3, par4));

            case 4:
                return canBePlacedOn(par1World.getBlockId(par2 + 1, par3, par4));
        }
    }

    /**
     * returns true if a vine can be placed on that block (checks for render as normal block and if it is solid)
     */
    private boolean canBePlacedOn(int par1)
    {
        if (par1 == 0)
        {
            return false;
        }

        Block block = Block.blocksList[par1];
        return block.renderAsNormalBlock() && block.blockMaterial.blocksMovement();
    }

    /**
     * Returns if the vine can stay in the world. It also changes the metadata according to neighboring blocks.
     */
    private boolean canVineStay(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = i;

        if (j > 0)
        {
            for (int k = 0; k <= 3; k++)
            {
                int l = 1 << k;

                if ((i & l) != 0 && !canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[k], par3, par4 + Direction.offsetZ[k])) && (par1World.getBlockId(par2, par3 + 1, par4) != blockID || (par1World.getBlockMetadata(par2, par3 + 1, par4) & l) == 0))
                {
                    j &= ~l;
                }
            }
        }

        if (j == 0 && !canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4)))
        {
            return false;
        }

        if (j != i)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, j);
        }

        return true;
    }

    public int getBlockColor()
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeFoliageColor();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote && !canVineStay(par1World, par2, par3, par4))
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
        if (!par1World.isRemote && par1World.rand.nextInt(4) == 0)
        {
            byte byte0 = 4;
            int i = 5;
            boolean flag = false;
            int j = par2 - byte0;
            label0:

            do
            {
                if (j > par2 + byte0)
                {
                    break;
                }

                label1:

                for (int k = par4 - byte0; k <= par4 + byte0; k++)
                {
                    int i1 = par3 - 1;

                    do
                    {
                        if (i1 > par3 + 1)
                        {
                            continue label1;
                        }

                        if (par1World.getBlockId(j, i1, k) == blockID && --i <= 0)
                        {
                            flag = true;
                            break label0;
                        }

                        i1++;
                    }
                    while (true);
                }

                j++;
            }
            while (true);

            j = par1World.getBlockMetadata(par2, par3, par4);
            int l = par1World.rand.nextInt(6);
            int j1 = Direction.vineGrowth[l];

            if (l == 1 && par3 < 255 && par1World.isAirBlock(par2, par3 + 1, par4))
            {
                if (flag)
                {
                    return;
                }

                int k1 = par1World.rand.nextInt(16) & j;

                if (k1 > 0)
                {
                    for (int j2 = 0; j2 <= 3; j2++)
                    {
                        if (!canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[j2], par3 + 1, par4 + Direction.offsetZ[j2])))
                        {
                            k1 &= ~(1 << j2);
                        }
                    }

                    if (k1 > 0)
                    {
                        par1World.setBlockAndMetadataWithNotify(par2, par3 + 1, par4, blockID, k1);
                    }
                }
            }
            else if (l >= 2 && l <= 5 && (j & 1 << j1) == 0)
            {
                if (flag)
                {
                    return;
                }

                int l1 = par1World.getBlockId(par2 + Direction.offsetX[j1], par3, par4 + Direction.offsetZ[j1]);

                if (l1 == 0 || Block.blocksList[l1] == null)
                {
                    int k2 = j1 + 1 & 3;
                    int j3 = j1 + 3 & 3;

                    if ((j & 1 << k2) != 0 && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[j1] + Direction.offsetX[k2], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[k2])))
                    {
                        par1World.setBlockAndMetadataWithNotify(par2 + Direction.offsetX[j1], par3, par4 + Direction.offsetZ[j1], blockID, 1 << k2);
                    }
                    else if ((j & 1 << j3) != 0 && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[j1] + Direction.offsetX[j3], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[j3])))
                    {
                        par1World.setBlockAndMetadataWithNotify(par2 + Direction.offsetX[j1], par3, par4 + Direction.offsetZ[j1], blockID, 1 << j3);
                    }
                    else if ((j & 1 << k2) != 0 && par1World.isAirBlock(par2 + Direction.offsetX[j1] + Direction.offsetX[k2], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[k2]) && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[k2], par3, par4 + Direction.offsetZ[k2])))
                    {
                        par1World.setBlockAndMetadataWithNotify(par2 + Direction.offsetX[j1] + Direction.offsetX[k2], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[k2], blockID, 1 << (j1 + 2 & 3));
                    }
                    else if ((j & 1 << j3) != 0 && par1World.isAirBlock(par2 + Direction.offsetX[j1] + Direction.offsetX[j3], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[j3]) && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[j3], par3, par4 + Direction.offsetZ[j3])))
                    {
                        par1World.setBlockAndMetadataWithNotify(par2 + Direction.offsetX[j1] + Direction.offsetX[j3], par3, par4 + Direction.offsetZ[j1] + Direction.offsetZ[j3], blockID, 1 << (j1 + 2 & 3));
                    }
                    else if (canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[j1], par3 + 1, par4 + Direction.offsetZ[j1])))
                    {
                        par1World.setBlockAndMetadataWithNotify(par2 + Direction.offsetX[j1], par3, par4 + Direction.offsetZ[j1], blockID, 0);
                    }
                }
                else if (Block.blocksList[l1].blockMaterial.isOpaque() && Block.blocksList[l1].renderAsNormalBlock())
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, j | 1 << j1);
                }
            }
            else if (par3 > 1)
            {
                int i2 = par1World.getBlockId(par2, par3 - 1, par4);

                if (i2 == 0)
                {
                    int l2 = par1World.rand.nextInt(16) & j;
                    if (l2 > 0)
                    {
                        //par1World.setBlockAndMetadataWithNotify(par2, par3 - 1, par4, blockID, l2);
                    }
                }
                else if (i2 == blockID)
                {
                    int i3 = par1World.rand.nextInt(16) & j;
                    int k3 = par1World.getBlockMetadata(par2, par3 - 1, par4);

                    if (k3 != (k3 | i3))
                    {
                        //par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, k3 | i3);
                    }
                }
            }
        }
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        byte byte0 = 0;

        switch (par5)
        {
            case 2:
                byte0 = 1;
                break;

            case 3:
                byte0 = 4;
                break;

            case 4:
                byte0 = 8;
                break;

            case 5:
                byte0 = 2;
                break;
        }

        if (byte0 != 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex)
        {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.vine, 1, 0));
        }
        else
        {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
}
