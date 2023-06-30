package net.minecraft.src;

import java.util.Random;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack chestContents[];

    /** determines if the check for adjacent chests has taken place. */
    public boolean adjacentChestChecked;

    /** contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest adjacentChestZNeg;

    /** contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest adjacentChestXPos;

    /** contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest adjacentChestXNeg;

    /** contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest adjacentChestZPos;

    /** the current angle of the lid (between 0 and 1) */
    public float lidAngle;

    /** the angle of the lid last tick */
    public float prevLidAngle;

    /** the number of players currently using this chest */
    public int numUsingPlayers;

    /** server sync counter (once per 20 ticks) */
    private int ticksSinceSync;

    public TileEntityChest()
    {
        chestContents = new ItemStack[36];
        adjacentChestChecked = false;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 27;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return chestContents[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (chestContents[par1] != null)
        {
            if (chestContents[par1].stackSize <= par2)
            {
                ItemStack itemstack = chestContents[par1];
                chestContents[par1] = null;
                onInventoryChanged();
                return itemstack;
            }

            ItemStack itemstack1 = chestContents[par1].splitStack(par2);

            if (chestContents[par1].stackSize == 0)
            {
                chestContents[par1] = null;
            }

            onInventoryChanged();
            return itemstack1;
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (chestContents[par1] != null)
        {
            ItemStack itemstack = chestContents[par1];
            chestContents[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }

        onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.chest";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        chestContents = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xff;

            if (j >= 0 && j < chestContents.length)
            {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < chestContents.length; i++)
        {
            if (chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                chestContents[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    /**
     * causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjcacent chest check
     */
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        adjacentChestChecked = false;
    }

    /**
     * performs the check for adjacent chests to determine if this chest is double or not.
     */
    public void checkForAdjacentChests()
    {
        if (adjacentChestChecked)
        {
            return;
        }

        adjacentChestChecked = true;
        adjacentChestZNeg = null;
        adjacentChestXPos = null;
        adjacentChestXNeg = null;
        adjacentChestZPos = null;

        if (worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == Block.chest.blockID)
        {
            adjacentChestXNeg = (TileEntityChest)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
        }

        if (worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == Block.chest.blockID)
        {
            adjacentChestXPos = (TileEntityChest)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
        }

        if (worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == Block.chest.blockID)
        {
            adjacentChestZNeg = (TileEntityChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
        }

        if (worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == Block.chest.blockID)
        {
            adjacentChestZPos = (TileEntityChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
        }

        if (adjacentChestZNeg != null)
        {
            adjacentChestZNeg.updateContainingBlockInfo();
        }

        if (adjacentChestZPos != null)
        {
            adjacentChestZPos.updateContainingBlockInfo();
        }

        if (adjacentChestXPos != null)
        {
            adjacentChestXPos.updateContainingBlockInfo();
        }

        if (adjacentChestXNeg != null)
        {
            adjacentChestXNeg.updateContainingBlockInfo();
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        checkForAdjacentChests();

        if ((++ticksSinceSync % 20) * 4 == 0)
        {
            worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
        }

        prevLidAngle = lidAngle;
        float f = 0.1F;

        if (numUsingPlayers > 0 && lidAngle == 0.0F && adjacentChestZNeg == null && adjacentChestXNeg == null)
        {
            double d = (double)xCoord + 0.5D;
            double d1 = (double)zCoord + 0.5D;

            if (adjacentChestZPos != null)
            {
                d1 += 0.5D;
            }

            if (adjacentChestXPos != null)
            {
                d += 0.5D;
            }

            worldObj.playSoundEffect(d, (double)yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
        {
            float f1 = lidAngle;

            if (numUsingPlayers > 0)
            {
                lidAngle += f;
            }
            else
            {
                lidAngle -= f;
            }

            if (lidAngle > 1.0F)
            {
                lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (lidAngle < f2 && f1 >= f2 && adjacentChestZNeg == null && adjacentChestXNeg == null)
            {
                double d2 = (double)xCoord + 0.5D;
                double d3 = (double)zCoord + 0.5D;

                if (adjacentChestZPos != null)
                {
                    d3 += 0.5D;
                }

                if (adjacentChestXPos != null)
                {
                    d2 += 0.5D;
                }

                worldObj.playSoundEffect(d2, (double)yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (lidAngle < 0.0F)
            {
                lidAngle = 0.0F;
            }
        }
    }

    public void onTileEntityPowered(int par1, int par2)
    {
        if (par1 == 1)
        {
            numUsingPlayers = par2;
        }
    }

    public void openChest()
    {
        numUsingPlayers++;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
    }

    public void closeChest()
    {
        numUsingPlayers--;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, numUsingPlayers);
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        updateContainingBlockInfo();
        checkForAdjacentChests();
        super.invalidate();
    }
}
