package net.minecraft.src;

public class InventoryCrafting implements IInventory
{
    private ItemStack stackList[];

    /** the width of the crafting inventory */
    private int inventoryWidth;

    /**
     * Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged.
     */
    private Container eventHandler;

    public InventoryCrafting(Container par1Container, int par2, int par3)
    {
        int i = par2 * par3;
        stackList = new ItemStack[i];
        eventHandler = par1Container;
        inventoryWidth = par2;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return stackList.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        if (par1 >= getSizeInventory())
        {
            return null;
        }
        else
        {
            return stackList[par1];
        }
    }

    /**
     * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
     */
    public ItemStack getStackInRowAndColumn(int par1, int par2)
    {
        if (par1 < 0 || par1 >= inventoryWidth)
        {
            return null;
        }
        else
        {
            int i = par1 + par2 * inventoryWidth;
            return getStackInSlot(i);
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.crafting";
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (stackList[par1] != null)
        {
            ItemStack itemstack = stackList[par1];
            stackList[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (stackList[par1] != null)
        {
            if (stackList[par1].stackSize <= par2)
            {
                ItemStack itemstack = stackList[par1];
                stackList[par1] = null;
                eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }

            ItemStack itemstack1 = stackList[par1].splitStack(par2);

            if (stackList[par1].stackSize == 0)
            {
                stackList[par1] = null;
            }

            eventHandler.onCraftMatrixChanged(this);
            return itemstack1;
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
        stackList[par1] = par2ItemStack;
        eventHandler.onCraftMatrixChanged(this);
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
