package net.minecraft.src;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random furnaceRand;

    /** True if this is an active furnace, false if idle */
    private final boolean isActive;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    protected BlockFurnace(int par1, boolean par2)
    {
        super(par1, Material.rock);
        furnaceRand = new Random();
        isActive = par2;
        blockIndexInTexture = 45;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.stoneOvenIdle.blockID;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        setDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isRemote)
        {
            return;
        }

        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);
        byte byte0 = 3;

        if (Block.opaqueCubeLookup[i] && !Block.opaqueCubeLookup[j])
        {
            byte0 = 3;
        }

        if (Block.opaqueCubeLookup[j] && !Block.opaqueCubeLookup[i])
        {
            byte0 = 2;
        }

        if (Block.opaqueCubeLookup[k] && !Block.opaqueCubeLookup[l])
        {
            byte0 = 5;
        }

        if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[k])
        {
            byte0 = 4;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return blockIndexInTexture + 17;
        }

        if (par5 == 0)
        {
            return blockIndexInTexture + 17;
        }

        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if (par5 != i)
        {
            return blockIndexInTexture;
        }

        if (isActive)
        {
            return blockIndexInTexture + 16;
        }
        else
        {
            return blockIndexInTexture - 1;
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!isActive)
        {
            return;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        float f = (float)par2 + 0.5F;
        float f1 = (float)par3 + 0.0F + (par5Random.nextFloat() * 6F) / 16F;
        float f2 = (float)par4 + 0.5F;
        float f3 = 0.52F;
        float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

        if (i == 4)
        {
            par1World.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 5)
        {
            par1World.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 2)
        {
            par1World.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
        }
        else if (i == 3)
        {
            par1World.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 == 1)
        {
            return blockIndexInTexture + 17;
        }

        if (par1 == 0)
        {
            return blockIndexInTexture + 17;
        }

        if (par1 == 3)
        {
            return blockIndexInTexture - 1;
        }
        else
        {
            return blockIndexInTexture;
        }
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

        TileEntityFurnace tileentityfurnace = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentityfurnace != null)
        {
            par5EntityPlayer.displayGUIFurnace(tileentityfurnace);
        }

        return true;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        keepFurnaceInventory = true;

        if (par0)
        {
            par1World.setBlockWithNotify(par2, par3, par4, Block.stoneOvenActive.blockID);
        }
        else
        {
            par1World.setBlockWithNotify(par2, par3, par4, Block.stoneOvenIdle.blockID);
        }

        keepFurnaceInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);

        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityFurnace();
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

        if (i == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (i == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        if (i == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (i == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityFurnace tileentityfurnace = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null)
            {
                label0:

                for (int i = 0; i < tileentityfurnace.getSizeInventory(); i++)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i);

                    if (itemstack == null)
                    {
                        continue;
                    }

                    float f = furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float f1 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float f2 = furnaceRand.nextFloat() * 0.8F + 0.1F;

                    do
                    {
                        if (itemstack.stackSize <= 0)
                        {
                            continue label0;
                        }

                        int j = furnaceRand.nextInt(21) + 10;

                        if (j > itemstack.stackSize)
                        {
                            j = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j;
                        EntityItem entityitem = new EntityItem(par1World, (float)par2 + f, (float)par3 + f1, (float)par4 + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float)furnaceRand.nextGaussian() * f3;
                        entityitem.motionY = (float)furnaceRand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float)furnaceRand.nextGaussian() * f3;
                        par1World.spawnEntityInWorld(entityitem);
                    }
                    while (true);
                }
            }
        }

        super.onBlockRemoval(par1World, par2, par3, par4);
    }
}
