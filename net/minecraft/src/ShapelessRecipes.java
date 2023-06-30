package net.minecraft.src;

import java.util.*;

public class ShapelessRecipes implements IRecipe
{
    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;

    /** Is a List of ItemStack that composes the recipe. */
    private final List recipeItems;

    public ShapelessRecipes(ItemStack par1ItemStack, List par2List)
    {
        recipeOutput = par1ItemStack;
        recipeItems = par2List;
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
        ArrayList arraylist = new ArrayList(recipeItems);
        int i = 0;

        do
        {
            if (i >= 3)
            {
                break;
            }

            for (int j = 0; j < 3; j++)
            {
                ItemStack itemstack = par1InventoryCrafting.getStackInRowAndColumn(j, i);

                if (itemstack == null)
                {
                    continue;
                }

                boolean flag = false;
                Iterator iterator = arraylist.iterator();

                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }

                    ItemStack itemstack1 = (ItemStack)iterator.next();

                    if (itemstack.itemID != itemstack1.itemID || itemstack1.getItemDamage() != -1 && itemstack.getItemDamage() != itemstack1.getItemDamage())
                    {
                        continue;
                    }

                    flag = true;
                    arraylist.remove(itemstack1);
                    break;
                }
                while (true);

                if (!flag)
                {
                    return false;
                }
            }

            i++;
        }
        while (true);

        return arraylist.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        return recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return recipeItems.size();
    }
}
