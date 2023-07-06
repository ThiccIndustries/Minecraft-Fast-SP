package net.minecraft.src;

public class RecipesWeapons
{
    private String recipePatterns[][] =
    {
        {
            "X", "X", "#"
        }
    };
    private Object recipeItems[][];

    public RecipesWeapons()
    {
        recipeItems = (new Object[][]
                {
                    new Object[] {
                        Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold
                    }, new Object[] {
                        Item.swordWood, Item.swordStone, Item.swordSteel, Item.swordDiamond, Item.swordGold
                    }
                });
    }

    /**
     * Adds the weapon recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        for (int i = 0; i < recipeItems[0].length; i++)
        {
            Object obj = recipeItems[0][i];

            for (int j = 0; j < recipeItems.length - 1; j++)
            {
                Item item = (Item)recipeItems[j + 1][i];
                par1CraftingManager.addRecipe(new ItemStack(item), new Object[]
                        {
                            recipePatterns[j], '#', Item.stick, 'X', obj
                        });
            }
        }

        par1CraftingManager.addRecipe(new ItemStack(Item.bow, 1), new Object[]
                {
                    " #X", "# X", " #X", 'X', Item.silk, '#', Item.stick
                });
        par1CraftingManager.addRecipe(new ItemStack(Item.arrow, 4), new Object[]
                {
                    "X", "#", "Y", 'Y', Item.feather, 'X', Item.flint, '#', Item.stick
                });
    }
}
