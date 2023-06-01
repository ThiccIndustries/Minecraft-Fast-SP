package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator
{
    final CraftingManager craftingManager;

    RecipeSorter(CraftingManager par1CraftingManager)
    {
        craftingManager = par1CraftingManager;
    }

    public int compareRecipes(IRecipe par1IRecipe, IRecipe par2IRecipe)
    {
        if ((par1IRecipe instanceof ShapelessRecipes) && (par2IRecipe instanceof ShapedRecipes))
        {
            return 1;
        }

        if ((par2IRecipe instanceof ShapelessRecipes) && (par1IRecipe instanceof ShapedRecipes))
        {
            return -1;
        }

        if (par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize())
        {
            return -1;
        }

        return par2IRecipe.getRecipeSize() <= par1IRecipe.getRecipeSize() ? 0 : 1;
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return compareRecipes((IRecipe)par1Obj, (IRecipe)par2Obj);
    }
}
