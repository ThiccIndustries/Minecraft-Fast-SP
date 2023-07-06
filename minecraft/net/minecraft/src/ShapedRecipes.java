package net.minecraft.src;

public class ShapedRecipes implements IRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private int recipeWidth;

    /** How many vertical slots this recipe uses. */
    private int recipeHeight;
    private ItemStack recipeItems[];

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;

    /** Is the itemID of the output item that you get when craft the recipe. */
    public final int recipeOutputItemID;

    public ShapedRecipes(int par1, int par2, ItemStack par3ArrayOfItemStack[], ItemStack par4ItemStack)
    {
        recipeOutputItemID = par4ItemStack.itemID;
        recipeWidth = par1;
        recipeHeight = par2;
        recipeItems = par3ArrayOfItemStack;
        recipeOutput = par4ItemStack;
    }

    public ItemStack getRecipeOutput()
    {
        return recipeOutput;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting)
    {
        for (int i = 0; i <= 3 - recipeWidth; i++)
        {
            for (int j = 0; j <= 3 - recipeHeight; j++)
            {
                if (checkMatch(par1InventoryCrafting, i, j, true))
                {
                    return true;
                }

                if (checkMatch(par1InventoryCrafting, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int k = i - par2;
                int l = j - par3;
                ItemStack itemstack = null;

                if (k >= 0 && l >= 0 && k < recipeWidth && l < recipeHeight)
                {
                    if (par4)
                    {
                        itemstack = recipeItems[(recipeWidth - k - 1) + l * recipeWidth];
                    }
                    else
                    {
                        itemstack = recipeItems[k + l * recipeWidth];
                    }
                }

                ItemStack itemstack1 = par1InventoryCrafting.getStackInRowAndColumn(i, j);

                if (itemstack1 == null && itemstack == null)
                {
                    continue;
                }

                if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
                {
                    return false;
                }

                if (itemstack.itemID != itemstack1.itemID)
                {
                    return false;
                }

                if (itemstack.getItemDamage() != -1 && itemstack.getItemDamage() != itemstack1.getItemDamage())
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        return new ItemStack(recipeOutput.itemID, recipeOutput.stackSize, recipeOutput.getItemDamage());
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return recipeWidth * recipeHeight;
    }
}
