package net.minecraft.src;

public interface IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public abstract boolean matches(InventoryCrafting inventorycrafting);

    /**
     * Returns an Item that is the result of this recipe
     */
    public abstract ItemStack getCraftingResult(InventoryCrafting inventorycrafting);

    /**
     * Returns the size of the recipe area
     */
    public abstract int getRecipeSize();

    public abstract ItemStack getRecipeOutput();
}
