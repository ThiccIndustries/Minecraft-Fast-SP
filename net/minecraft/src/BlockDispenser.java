package net.minecraft.src;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    private Random random;

    protected BlockDispenser(int par1)
    {
        super(par1, Material.rock);
        random = new Random();
        blockIndexInTexture = 45;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dispenser.blockID;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        setDispenserDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * sets Dispenser block direction so that the front faces an non-opaque block; chooses west to be direction if all
     * surrounding blocks are opaque.
     */
    private void setDispenserDefaultDirection(World par1World, int par2, int par3, int par4)
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
        else
        {
            return blockIndexInTexture + 1;
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
            return blockIndexInTexture + 1;
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

        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            par5EntityPlayer.displayGUIDispenser(tileentitydispenser);
        }

        return true;
    }

    /**
     * dispenses an item from a randomly selected item stack from the blocks inventory into the game world.
     */
    private void dispenseItem(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = 0;
        int k = 0;

        if (i == 3)
        {
            k = 1;
        }
        else if (i == 2)
        {
            k = -1;
        }
        else if (i == 5)
        {
            j = 1;
        }
        else
        {
            j = -1;
        }

        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            ItemStack itemstack = tileentitydispenser.getRandomStackFromInventory();
            double d = (double)par2 + (double)j * 0.59999999999999998D + 0.5D;
            double d1 = (double)par3 + 0.5D;
            double d2 = (double)par4 + (double)k * 0.59999999999999998D + 0.5D;

            if (itemstack == null)
            {
                par1World.playAuxSFX(1001, par2, par3, par4, 0);
            }
            else
            {
                if (itemstack.itemID == Item.arrow.shiftedIndex)
                {
                    EntityArrow entityarrow = new EntityArrow(par1World, d, d1, d2);
                    entityarrow.setArrowHeading(j, 0.10000000149011612D, k, 1.1F, 6F);
                    entityarrow.doesArrowBelongToPlayer = true;
                    par1World.spawnEntityInWorld(entityarrow);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.egg.shiftedIndex)
                {
                    EntityEgg entityegg = new EntityEgg(par1World, d, d1, d2);
                    entityegg.setThrowableHeading(j, 0.10000000149011612D, k, 1.1F, 6F);
                    par1World.spawnEntityInWorld(entityegg);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.snowball.shiftedIndex)
                {
                    EntitySnowball entitysnowball = new EntitySnowball(par1World, d, d1, d2);
                    entitysnowball.setThrowableHeading(j, 0.10000000149011612D, k, 1.1F, 6F);
                    par1World.spawnEntityInWorld(entitysnowball);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.potion.shiftedIndex && ItemPotion.isSplash(itemstack.getItemDamage()))
                {
                    EntityPotion entitypotion = new EntityPotion(par1World, d, d1, d2, itemstack.getItemDamage());
                    entitypotion.setThrowableHeading(j, 0.10000000149011612D, k, 1.375F, 3F);
                    par1World.spawnEntityInWorld(entitypotion);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.expBottle.shiftedIndex)
                {
                    EntityExpBottle entityexpbottle = new EntityExpBottle(par1World, d, d1, d2);
                    entityexpbottle.setThrowableHeading(j, 0.10000000149011612D, k, 1.375F, 3F);
                    par1World.spawnEntityInWorld(entityexpbottle);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.monsterPlacer.shiftedIndex)
                {
                    ItemMonsterPlacer.func_48440_a(par1World, itemstack.getItemDamage(), d + (double)j * 0.29999999999999999D, d1 - 0.29999999999999999D, d2 + (double)k * 0.29999999999999999D);
                    par1World.playAuxSFX(1002, par2, par3, par4, 0);
                }
                else if (itemstack.itemID == Item.fireballCharge.shiftedIndex)
                {
                    EntitySmallFireball entitysmallfireball = new EntitySmallFireball(par1World, d + (double)j * 0.29999999999999999D, d1, d2 + (double)k * 0.29999999999999999D, (double)j + par5Random.nextGaussian() * 0.050000000000000003D, par5Random.nextGaussian() * 0.050000000000000003D, (double)k + par5Random.nextGaussian() * 0.050000000000000003D);
                    par1World.spawnEntityInWorld(entitysmallfireball);
                    par1World.playAuxSFX(1009, par2, par3, par4, 0);
                }
                else
                {
                    EntityItem entityitem = new EntityItem(par1World, d, d1 - 0.29999999999999999D, d2, itemstack);
                    double d3 = par5Random.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
                    entityitem.motionX = (double)j * d3;
                    entityitem.motionY = 0.20000000298023224D;
                    entityitem.motionZ = (double)k * d3;
                    entityitem.motionX += par5Random.nextGaussian() * 0.0074999998323619366D * 6D;
                    entityitem.motionY += par5Random.nextGaussian() * 0.0074999998323619366D * 6D;
                    entityitem.motionZ += par5Random.nextGaussian() * 0.0074999998323619366D * 6D;
                    par1World.spawnEntityInWorld(entityitem);
                    par1World.playAuxSFX(1000, par2, par3, par4, 0);
                }

                par1World.playAuxSFX(2000, par2, par3, par4, j + 1 + (k + 1) * 3);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0 && Block.blocksList[par5].canProvidePower())
        {
            boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

            if (flag)
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote && (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4)))
        {
            dispenseItem(par1World, par2, par3, par4, par5Random);
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityDispenser();
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
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            label0:

            for (int i = 0; i < tileentitydispenser.getSizeInventory(); i++)
            {
                ItemStack itemstack = tileentitydispenser.getStackInSlot(i);

                if (itemstack == null)
                {
                    continue;
                }

                float f = random.nextFloat() * 0.8F + 0.1F;
                float f1 = random.nextFloat() * 0.8F + 0.1F;
                float f2 = random.nextFloat() * 0.8F + 0.1F;

                do
                {
                    if (itemstack.stackSize <= 0)
                    {
                        continue label0;
                    }

                    int j = random.nextInt(21) + 10;

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
                    entityitem.motionX = (float)random.nextGaussian() * f3;
                    entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)random.nextGaussian() * f3;
                    par1World.spawnEntityInWorld(entityitem);
                }
                while (true);
            }
        }

        super.onBlockRemoval(par1World, par2, par3, par4);
    }
}
