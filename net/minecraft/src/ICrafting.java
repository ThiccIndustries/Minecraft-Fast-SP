package net.minecraft.src;

public interface ICrafting
{
    /**
     * inform the player of a change in a single slot
     */
    public abstract void updateCraftingInventorySlot(Container container, int i, ItemStack itemstack);

    /**
     * send information about the crafting inventory to the client(currently only for furnace times)
     */
    public abstract void updateCraftingInventoryInfo(Container container, int i, int j);
}
