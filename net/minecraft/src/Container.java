package net.minecraft.src;

import java.util.*;

public abstract class Container
{
    /** the list of all items(stacks) for the corresponding slot */
    public List inventoryItemStacks;

    /** the list of all slots in the inventory */
    public List inventorySlots;
    public int windowId;
    private short transactionID;

    /**
     * list of all people that need to be notified when this craftinventory changes
     */
    protected List crafters;
    private Set field_20918_b;

    public Container()
    {
        inventoryItemStacks = new ArrayList();
        inventorySlots = new ArrayList();
        windowId = 0;
        transactionID = 0;
        crafters = new ArrayList();
        field_20918_b = new HashSet();
    }

    /**
     * adds the slot to the inventory it is in
     */
    protected void addSlot(Slot par1Slot)
    {
        par1Slot.slotNumber = inventorySlots.size();
        inventorySlots.add(par1Slot);
        inventoryItemStacks.add(null);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        for (int i = 0; i < inventorySlots.size(); i++)
        {
            ItemStack itemstack = ((Slot)inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)inventoryItemStacks.get(i);

            if (ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                continue;
            }

            itemstack1 = itemstack != null ? itemstack.copy() : null;
            inventoryItemStacks.set(i, itemstack1);

            for (int j = 0; j < crafters.size(); j++)
            {
                ((ICrafting)crafters.get(j)).updateCraftingInventorySlot(this, i, itemstack1);
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
    {
        return false;
    }

    public Slot getSlot(int par1)
    {
        return (Slot)inventorySlots.get(par1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null)
        {
            return slot.getStack();
        }
        else
        {
            return null;
        }
    }

    public ItemStack slotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
    {
        ItemStack itemstack = null;

        if (par2 > 1)
        {
            return null;
        }

        if (par2 == 0 || par2 == 1)
        {
            InventoryPlayer inventoryplayer = par4EntityPlayer.inventory;

            if (par1 == -999)
            {
                if (inventoryplayer.getItemStack() != null && par1 == -999)
                {
                    if (par2 == 0)
                    {
                        par4EntityPlayer.dropPlayerItem(inventoryplayer.getItemStack());
                        inventoryplayer.setItemStack(null);
                    }

                    if (par2 == 1)
                    {
                        par4EntityPlayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));

                        if (inventoryplayer.getItemStack().stackSize == 0)
                        {
                            inventoryplayer.setItemStack(null);
                        }
                    }
                }
            }
            else if (par3)
            {
                ItemStack itemstack1 = transferStackInSlot(par1);

                if (itemstack1 != null)
                {
                    int i = itemstack1.itemID;
                    itemstack = itemstack1.copy();
                    Slot slot1 = (Slot)inventorySlots.get(par1);

                    if (slot1 != null && slot1.getStack() != null && slot1.getStack().itemID == i)
                    {
                        retrySlotClick(par1, par2, par3, par4EntityPlayer);
                    }
                }
            }
            else
            {
                if (par1 < 0)
                {
                    return null;
                }

                Slot slot = (Slot)inventorySlots.get(par1);

                if (slot != null)
                {
                    slot.onSlotChanged();
                    ItemStack itemstack2 = slot.getStack();
                    ItemStack itemstack4 = inventoryplayer.getItemStack();

                    if (itemstack2 != null)
                    {
                        itemstack = itemstack2.copy();
                    }

                    if (itemstack2 == null)
                    {
                        if (itemstack4 != null && slot.isItemValid(itemstack4))
                        {
                            int j = par2 != 0 ? 1 : itemstack4.stackSize;

                            if (j > slot.getSlotStackLimit())
                            {
                                j = slot.getSlotStackLimit();
                            }

                            slot.putStack(itemstack4.splitStack(j));

                            if (itemstack4.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                    }
                    else if (itemstack4 == null)
                    {
                        int k = par2 != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
                        ItemStack itemstack6 = slot.decrStackSize(k);
                        inventoryplayer.setItemStack(itemstack6);

                        if (itemstack2.stackSize == 0)
                        {
                            slot.putStack(null);
                        }

                        slot.onPickupFromSlot(inventoryplayer.getItemStack());
                    }
                    else if (slot.isItemValid(itemstack4))
                    {
                        if (itemstack2.itemID != itemstack4.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack4.getItemDamage() || !ItemStack.func_46154_a(itemstack2, itemstack4))
                        {
                            if (itemstack4.stackSize <= slot.getSlotStackLimit())
                            {
                                ItemStack itemstack5 = itemstack2;
                                slot.putStack(itemstack4);
                                inventoryplayer.setItemStack(itemstack5);
                            }
                        }
                        else
                        {
                            int l = par2 != 0 ? 1 : itemstack4.stackSize;

                            if (l > slot.getSlotStackLimit() - itemstack2.stackSize)
                            {
                                l = slot.getSlotStackLimit() - itemstack2.stackSize;
                            }

                            if (l > itemstack4.getMaxStackSize() - itemstack2.stackSize)
                            {
                                l = itemstack4.getMaxStackSize() - itemstack2.stackSize;
                            }

                            itemstack4.splitStack(l);

                            if (itemstack4.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }

                            itemstack2.stackSize += l;
                        }
                    }
                    else if (itemstack2.itemID == itemstack4.itemID && itemstack4.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack4.getItemDamage()) && ItemStack.func_46154_a(itemstack2, itemstack4))
                    {
                        int i1 = itemstack2.stackSize;

                        if (i1 > 0 && i1 + itemstack4.stackSize <= itemstack4.getMaxStackSize())
                        {
                            itemstack4.stackSize += i1;
                            ItemStack itemstack3 = slot.decrStackSize(i1);

                            if (itemstack3.stackSize == 0)
                            {
                                slot.putStack(null);
                            }

                            slot.onPickupFromSlot(inventoryplayer.getItemStack());
                        }
                    }
                }
            }
        }

        return itemstack;
    }

    protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
    {
        slotClick(par1, par2, par3, par4EntityPlayer);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        InventoryPlayer inventoryplayer = par1EntityPlayer.inventory;

        if (inventoryplayer.getItemStack() != null)
        {
            par1EntityPlayer.dropPlayerItem(inventoryplayer.getItemStack());
            inventoryplayer.setItemStack(null);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        updateCraftingResults();
    }

    /**
     * args: slotID, itemStack to put in slot
     */
    public void putStackInSlot(int par1, ItemStack par2ItemStack)
    {
        getSlot(par1).putStack(par2ItemStack);
    }

    /**
     * places itemstacks in first x slots, x being aitemstack.lenght
     */
    public void putStacksInSlots(ItemStack par1ArrayOfItemStack[])
    {
        for (int i = 0; i < par1ArrayOfItemStack.length; i++)
        {
            getSlot(i).putStack(par1ArrayOfItemStack[i]);
        }
    }

    public void updateProgressBar(int i, int j)
    {
    }

    /**
     * Gets a unique transaction ID. Parameter is unused.
     */
    public short getNextTransactionID(InventoryPlayer par1InventoryPlayer)
    {
        transactionID++;
        return transactionID;
    }

    public void func_20113_a(short word0)
    {
    }

    public void func_20110_b(short word0)
    {
    }

    public abstract boolean canInteractWith(EntityPlayer entityplayer);

    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean flag = false;
        int i = par2;

        if (par4)
        {
            i = par3 - 1;
        }

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.stackSize > 0 && (!par4 && i < par3 || par4 && i >= par2))
            {
                Slot slot = (Slot)inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                if (itemstack != null && itemstack.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack.getItemDamage()) && ItemStack.func_46154_a(par1ItemStack, itemstack))
                {
                    int k = itemstack.stackSize + par1ItemStack.stackSize;

                    if (k <= par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize = 0;
                        itemstack.stackSize = k;
                        slot.onSlotChanged();
                        flag = true;
                    }
                    else if (itemstack.stackSize < par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack.stackSize;
                        itemstack.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                if (par4)
                {
                    i--;
                }
                else
                {
                    i++;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
            int j;

            if (par4)
            {
                j = par3 - 1;
            }
            else
            {
                j = par2;
            }

            do
            {
                if ((par4 || j >= par3) && (!par4 || j < par2))
                {
                    break;
                }

                Slot slot1 = (Slot)inventorySlots.get(j);
                ItemStack itemstack1 = slot1.getStack();

                if (itemstack1 == null)
                {
                    slot1.putStack(par1ItemStack.copy());
                    slot1.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag = true;
                    break;
                }

                if (par4)
                {
                    j--;
                }
                else
                {
                    j++;
                }
            }
            while (true);
        }

        return flag;
    }
}
