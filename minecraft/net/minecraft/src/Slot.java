package net.minecraft.src;

public class Slot
{
    /** The index of the slot in the inventory. */
    private final int slotIndex;

    /** The inventory we want to extract a slot from. */
    public final IInventory inventory;

    /** the id of the slot(also the index in the inventory arraylist) */
    public int slotNumber;

    /** display position of the inventory slot on the screen x axis */
    public int xDisplayPosition;

    /** display position of the inventory slot on the screen y axis */
    public int yDisplayPosition;

    public Slot(IInventory par1IInventory, int par2, int par3, int par4)
    {
        inventory = par1IInventory;
        slotIndex = par2;
        xDisplayPosition = par3;
        yDisplayPosition = par4;
    }

    public void func_48433_a(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        if (par1ItemStack == null || par2ItemStack == null)
        {
            return;
        }

        if (par1ItemStack.itemID != par2ItemStack.itemID)
        {
            return;
        }

        int i = par2ItemStack.stackSize - par1ItemStack.stackSize;

        if (i > 0)
        {
            func_48435_a(par1ItemStack, i);
        }
    }

    protected void func_48435_a(ItemStack itemstack, int i)
    {
    }

    protected void func_48434_c(ItemStack itemstack)
    {
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        onSlotChanged();
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    public ItemStack getStack()
    {
        return inventory.getStackInSlot(slotIndex);
    }

    /**
     * Returns if this slot contains a stack.
     */
    public boolean getHasStack()
    {
        return getStack() != null;
    }

    /**
     * Helper fnct to put a stack in the slot.
     */
    public void putStack(ItemStack par1ItemStack)
    {
        inventory.setInventorySlotContents(slotIndex, par1ItemStack);
        onSlotChanged();
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        inventory.onInventoryChanged();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public int getBackgroundIconIndex()
    {
        return -1;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        return inventory.decrStackSize(slotIndex, par1);
    }
}
