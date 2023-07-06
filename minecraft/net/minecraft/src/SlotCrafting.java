package net.minecraft.src;

public class SlotCrafting extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final IInventory craftMatrix;

    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    private int field_48436_g;

    public SlotCrafting(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
    {
        super(par3IInventory, par4, par5, par6);
        thePlayer = par1EntityPlayer;
        craftMatrix = par2IInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        if (getHasStack())
        {
            field_48436_g += Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    protected void func_48435_a(ItemStack par1ItemStack, int par2)
    {
        field_48436_g += par2;
        func_48434_c(par1ItemStack);
    }

    protected void func_48434_c(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, field_48436_g);
        field_48436_g = 0;

        if (par1ItemStack.itemID == Block.workbench.blockID)
        {
            thePlayer.addStat(AchievementList.buildWorkBench, 1);
        }
        else if (par1ItemStack.itemID == Item.pickaxeWood.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.buildPickaxe, 1);
        }
        else if (par1ItemStack.itemID == Block.stoneOvenIdle.blockID)
        {
            thePlayer.addStat(AchievementList.buildFurnace, 1);
        }
        else if (par1ItemStack.itemID == Item.hoeWood.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.buildHoe, 1);
        }
        else if (par1ItemStack.itemID == Item.bread.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.makeBread, 1);
        }
        else if (par1ItemStack.itemID == Item.cake.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.bakeCake, 1);
        }
        else if (par1ItemStack.itemID == Item.pickaxeStone.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
        }
        else if (par1ItemStack.itemID == Item.swordWood.shiftedIndex)
        {
            thePlayer.addStat(AchievementList.buildSword, 1);
        }
        else if (par1ItemStack.itemID == Block.enchantmentTable.blockID)
        {
            thePlayer.addStat(AchievementList.enchantments, 1);
        }
        else if (par1ItemStack.itemID == Block.bookShelf.blockID)
        {
            thePlayer.addStat(AchievementList.bookcase, 1);
        }
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        func_48434_c(par1ItemStack);

        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack itemstack = craftMatrix.getStackInSlot(i);

            if (itemstack == null)
            {
                continue;
            }

            craftMatrix.decrStackSize(i, 1);

            if (!itemstack.getItem().hasContainerItem())
            {
                continue;
            }

            ItemStack itemstack1 = new ItemStack(itemstack.getItem().getContainerItem());

            if (itemstack.getItem().doesContainerItemLeaveCraftingGrid(itemstack) && thePlayer.inventory.addItemStackToInventory(itemstack1))
            {
                continue;
            }

            if (craftMatrix.getStackInSlot(i) == null)
            {
                craftMatrix.setInventorySlotContents(i, itemstack1);
            }
            else
            {
                thePlayer.dropPlayerItem(itemstack1);
            }
        }
    }
}
