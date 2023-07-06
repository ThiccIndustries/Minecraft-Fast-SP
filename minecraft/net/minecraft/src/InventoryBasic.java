package net.minecraft.src;

import java.util.List;

public class InventoryBasic implements IInventory
{
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack inventoryContents[];
    private List field_20073_d;

    public InventoryBasic(String par1Str, int par2)
    {
        inventoryTitle = par1Str;
        slotsCount = par2;
        inventoryContents = new ItemStack[par2];
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return inventoryContents[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (inventoryContents[par1] != null)
        {
            if (inventoryContents[par1].stackSize <= par2)
            {
                ItemStack itemstack = inventoryContents[par1];
                inventoryContents[par1] = null;
                onInventoryChanged();
                return itemstack;
            }

            ItemStack itemstack1 = inventoryContents[par1].splitStack(par2);

            if (inventoryContents[par1].stackSize == 0)
            {
                inventoryContents[par1] = null;
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
        if (inventoryContents[par1] != null)
        {
            ItemStack itemstack = inventoryContents[par1];
            inventoryContents[par1] = null;
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
        inventoryContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }

        onInventoryChanged();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return slotsCount;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return inventoryTitle;
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
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        if (field_20073_d != null)
        {
            for (int i = 0; i < field_20073_d.size(); i++)
            {
                ((IInvBasic)field_20073_d.get(i)).onInventoryChanged(this);
            }
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }
}
