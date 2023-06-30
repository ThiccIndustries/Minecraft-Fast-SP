package net.minecraft.src;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack dispenserContents[];

    /**
     * random number generator for instance. Used in random item stack selection.
     */
    private Random dispenserRandom;

    public TileEntityDispenser()
    {
        dispenserContents = new ItemStack[9];
        dispenserRandom = new Random();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 9;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return dispenserContents[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (dispenserContents[par1] != null)
        {
            if (dispenserContents[par1].stackSize <= par2)
            {
                ItemStack itemstack = dispenserContents[par1];
                dispenserContents[par1] = null;
                onInventoryChanged();
                return itemstack;
            }

            ItemStack itemstack1 = dispenserContents[par1].splitStack(par2);

            if (dispenserContents[par1].stackSize == 0)
            {
                dispenserContents[par1] = null;
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
        if (dispenserContents[par1] != null)
        {
            ItemStack itemstack = dispenserContents[par1];
            dispenserContents[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * gets stack of one item extracted from a stack chosen at random from the block inventory
     */
    public ItemStack getRandomStackFromInventory()
    {
        int i = -1;
        int j = 1;

        for (int k = 0; k < dispenserContents.length; k++)
        {
            if (dispenserContents[k] != null && dispenserRandom.nextInt(j++) == 0)
            {
                i = k;
            }
        }

        if (i >= 0)
        {
            return decrStackSize(i, 1);
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
        dispenserContents[par1] = par2ItemStack;

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
        return "container.dispenser";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        dispenserContents = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xff;

            if (j >= 0 && j < dispenserContents.length)
            {
                dispenserContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
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

        for (int i = 0; i < dispenserContents.length; i++)
        {
            if (dispenserContents[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                dispenserContents[i].writeToNBT(nbttagcompound);
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

    public void openChest()
    {
    }

    public void closeChest()
    {
    }
}
