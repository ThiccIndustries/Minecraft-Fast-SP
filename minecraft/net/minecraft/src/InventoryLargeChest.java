package net.minecraft.src;

public class InventoryLargeChest implements IInventory
{
    /** Name of the chest. */
    private String name;

    /** Inventory object corresponding to double chest upper part */
    private IInventory upperChest;

    /** Inventory object corresponding to double chest lower part */
    private IInventory lowerChest;

    public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory)
    {
        name = par1Str;

        if (par2IInventory == null)
        {
            par2IInventory = par3IInventory;
        }

        if (par3IInventory == null)
        {
            par3IInventory = par2IInventory;
        }

        upperChest = par2IInventory;
        lowerChest = par3IInventory;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return upperChest.getSizeInventory() + lowerChest.getSizeInventory();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return name;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        if (par1 >= upperChest.getSizeInventory())
        {
            return lowerChest.getStackInSlot(par1 - upperChest.getSizeInventory());
        }
        else
        {
            return upperChest.getStackInSlot(par1);
        }
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (par1 >= upperChest.getSizeInventory())
        {
            return lowerChest.decrStackSize(par1 - upperChest.getSizeInventory(), par2);
        }
        else
        {
            return upperChest.decrStackSize(par1, par2);
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (par1 >= upperChest.getSizeInventory())
        {
            return lowerChest.getStackInSlotOnClosing(par1 - upperChest.getSizeInventory());
        }
        else
        {
            return upperChest.getStackInSlotOnClosing(par1);
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 >= upperChest.getSizeInventory())
        {
            lowerChest.setInventorySlotContents(par1 - upperChest.getSizeInventory(), par2ItemStack);
        }
        else
        {
            upperChest.setInventorySlotContents(par1, par2ItemStack);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return upperChest.getInventoryStackLimit();
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        upperChest.onInventoryChanged();
        lowerChest.onInventoryChanged();
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return upperChest.isUseableByPlayer(par1EntityPlayer) && lowerChest.isUseableByPlayer(par1EntityPlayer);
    }

    public void openChest()
    {
        upperChest.openChest();
        lowerChest.openChest();
    }

    public void closeChest()
    {
        upperChest.closeChest();
        lowerChest.closeChest();
    }
}
