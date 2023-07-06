package net.minecraft.src;

import java.util.List;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand tileBrewingStand;
    private int brewTime;

    public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
    {
        brewTime = 0;
        tileBrewingStand = par2TileEntityBrewingStand;
        addSlot(new SlotBrewingStandPotion(this, par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        addSlot(new SlotBrewingStandPotion(this, par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        addSlot(new SlotBrewingStandPotion(this, par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        addSlot(new SlotBrewingStandIngredient(this, par2TileEntityBrewingStand, 3, 79, 17));

        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                addSlot(new Slot(par1InventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            addSlot(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);

            if (brewTime != tileBrewingStand.getBrewTime())
            {
                icrafting.updateCraftingInventoryInfo(this, 0, tileBrewingStand.getBrewTime());
            }
        }

        brewTime = tileBrewingStand.getBrewTime();
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            tileBrewingStand.setBrewTime(par2);
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par1 >= 0 && par1 <= 2 || par1 == 3)
            {
                if (!mergeItemStack(itemstack1, 4, 40, true))
                {
                    return null;
                }

                slot.func_48433_a(itemstack1, itemstack);
            }
            else if (par1 >= 4 && par1 < 31)
            {
                if (!mergeItemStack(itemstack1, 31, 40, false))
                {
                    return null;
                }
            }
            else if (par1 >= 31 && par1 < 40)
            {
                if (!mergeItemStack(itemstack1, 4, 31, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 4, 40, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize != itemstack.stackSize)
            {
                slot.onPickupFromSlot(itemstack1);
            }
            else
            {
                return null;
            }
        }

        return itemstack;
    }
}
