package net.minecraft.src;

import java.util.*;

public class ContainerEnchantment extends Container
{
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory tableInventory;

    /** current world (for bookshelf counting) */
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand;

    /** used as seed for EnchantmentNameParts (see GuiEnchantment) */
    public long nameSeed;
    public int enchantLevels[];

    public ContainerEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        tableInventory = new SlotEnchantmentTable(this, "Enchant", 1);
        rand = new Random();
        enchantLevels = new int[3];
        worldPointer = par2World;
        posX = par3;
        posY = par4;
        posZ = par5;
        addSlot(new SlotEnchantment(this, tableInventory, 0, 25, 47));

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
            icrafting.updateCraftingInventoryInfo(this, 0, enchantLevels[0]);
            icrafting.updateCraftingInventoryInfo(this, 1, enchantLevels[1]);
            icrafting.updateCraftingInventoryInfo(this, 2, enchantLevels[2]);
        }
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 >= 0 && par1 <= 2)
        {
            enchantLevels[par1] = par2;
        }
        else
        {
            super.updateProgressBar(par1, par2);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        if (par1IInventory == tableInventory)
        {
            ItemStack itemstack = par1IInventory.getStackInSlot(0);

            if (itemstack == null || !itemstack.isItemEnchantable())
            {
                for (int i = 0; i < 3; i++)
                {
                    enchantLevels[i] = 0;
                }
            }
            else
            {
                nameSeed = rand.nextLong();

                if (!worldPointer.isRemote)
                {
                    int j = 0;

                    for (int k = -1; k <= 1; k++)
                    {
                        for (int i1 = -1; i1 <= 1; i1++)
                        {
                            if (k == 0 && i1 == 0 || !worldPointer.isAirBlock(posX + i1, posY, posZ + k) || !worldPointer.isAirBlock(posX + i1, posY + 1, posZ + k))
                            {
                                continue;
                            }

                            if (worldPointer.getBlockId(posX + i1 * 2, posY, posZ + k * 2) == Block.bookShelf.blockID)
                            {
                                j++;
                            }

                            if (worldPointer.getBlockId(posX + i1 * 2, posY + 1, posZ + k * 2) == Block.bookShelf.blockID)
                            {
                                j++;
                            }

                            if (i1 == 0 || k == 0)
                            {
                                continue;
                            }

                            if (worldPointer.getBlockId(posX + i1 * 2, posY, posZ + k) == Block.bookShelf.blockID)
                            {
                                j++;
                            }

                            if (worldPointer.getBlockId(posX + i1 * 2, posY + 1, posZ + k) == Block.bookShelf.blockID)
                            {
                                j++;
                            }

                            if (worldPointer.getBlockId(posX + i1, posY, posZ + k * 2) == Block.bookShelf.blockID)
                            {
                                j++;
                            }

                            if (worldPointer.getBlockId(posX + i1, posY + 1, posZ + k * 2) == Block.bookShelf.blockID)
                            {
                                j++;
                            }
                        }
                    }

                    for (int l = 0; l < 3; l++)
                    {
                        enchantLevels[l] = EnchantmentHelper.calcItemStackEnchantability(rand, l, j, itemstack);
                    }

                    updateCraftingResults();
                }
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = tableInventory.getStackInSlot(0);

        if (enchantLevels[par2] > 0 && itemstack != null && (par1EntityPlayer.experienceLevel >= enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
        {
            if (!worldPointer.isRemote)
            {
                List list = EnchantmentHelper.buildEnchantmentList(rand, itemstack, enchantLevels[par2]);

                if (list != null)
                {
                    par1EntityPlayer.removeExperience(enchantLevels[par2]);
                    EnchantmentData enchantmentdata;

                    for (Iterator iterator = list.iterator(); iterator.hasNext(); itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel))
                    {
                        enchantmentdata = (EnchantmentData)iterator.next();
                    }

                    onCraftMatrixChanged(tableInventory);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (worldPointer.isRemote)
        {
            return;
        }

        ItemStack itemstack = tableInventory.getStackInSlotOnClosing(0);

        if (itemstack != null)
        {
            par1EntityPlayer.dropPlayerItem(itemstack);
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        if (worldPointer.getBlockId(posX, posY, posZ) != Block.enchantmentTable.blockID)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSq((double)posX + 0.5D, (double)posY + 0.5D, (double)posZ + 0.5D) <= 64D;
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

            if (par1 == 0)
            {
                if (!mergeItemStack(itemstack1, 1, 37, true))
                {
                    return null;
                }
            }
            else
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
