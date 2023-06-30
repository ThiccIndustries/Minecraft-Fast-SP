package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;

public class CraftingManager
{
    /** The static instance of this class */
    private static final CraftingManager instance = new CraftingManager();

    /** A list of all the recipes added */
    private List recipes;

    /**
     * Returns the static instance of this class
     */
    public static final CraftingManager getInstance()
    {
        return instance;
    }

    private CraftingManager()
    {
        recipes = new ArrayList();
        (new RecipesTools()).addRecipes(this);
        (new RecipesWeapons()).addRecipes(this);
        (new RecipesIngots()).addRecipes(this);
        (new RecipesFood()).addRecipes(this);
        (new RecipesCrafting()).addRecipes(this);
        (new RecipesArmor()).addRecipes(this);
        (new RecipesDyes()).addRecipes(this);
        addRecipe(new ItemStack(Item.paper, 3), new Object[]
                {
                    "###", '#', Item.reed
                });
        addRecipe(new ItemStack(Item.book, 1), new Object[]
                {
                    "#", "#", "#", '#', Item.paper
                });
        addRecipe(new ItemStack(Block.fence, 2), new Object[]
                {
                    "###", "###", '#', Item.stick
                });
        addRecipe(new ItemStack(Block.netherFence, 6), new Object[]
                {
                    "###", "###", '#', Block.netherBrick
                });
        addRecipe(new ItemStack(Block.fenceGate, 1), new Object[]
                {
                    "#W#", "#W#", '#', Item.stick, 'W', Block.planks
                });
        addRecipe(new ItemStack(Block.jukebox, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Block.planks, 'X', Item.diamond
                });
        addRecipe(new ItemStack(Block.music, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Block.planks, 'X', Item.redstone
                });
        addRecipe(new ItemStack(Block.bookShelf, 1), new Object[]
                {
                    "###", "XXX", "###", '#', Block.planks, 'X', Item.book
                });
        addRecipe(new ItemStack(Block.blockSnow, 1), new Object[]
                {
                    "##", "##", '#', Item.snowball
                });
        addRecipe(new ItemStack(Block.blockClay, 1), new Object[]
                {
                    "##", "##", '#', Item.clay
                });
        addRecipe(new ItemStack(Block.brick, 1), new Object[]
                {
                    "##", "##", '#', Item.brick
                });
        addRecipe(new ItemStack(Block.glowStone, 1), new Object[]
                {
                    "##", "##", '#', Item.lightStoneDust
                });
        addRecipe(new ItemStack(Block.cloth, 1), new Object[]
                {
                    "##", "##", '#', Item.silk
                });
        addRecipe(new ItemStack(Block.tnt, 1), new Object[]
                {
                    "X#X", "#X#", "X#X", 'X', Item.gunpowder, '#', Block.sand
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 3), new Object[]
                {
                    "###", '#', Block.cobblestone
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 0), new Object[]
                {
                    "###", '#', Block.stone
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 1), new Object[]
                {
                    "###", '#', Block.sandStone
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 2), new Object[]
                {
                    "###", '#', Block.planks
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 4), new Object[]
                {
                    "###", '#', Block.brick
                });
        addRecipe(new ItemStack(Block.stairSingle, 6, 5), new Object[]
                {
                    "###", '#', Block.stoneBrick
                });
        addRecipe(new ItemStack(Block.ladder, 3), new Object[]
                {
                    "# #", "###", "# #", '#', Item.stick
                });
        addRecipe(new ItemStack(Item.doorWood, 1), new Object[]
                {
                    "##", "##", "##", '#', Block.planks
                });
        addRecipe(new ItemStack(Block.trapdoor, 2), new Object[]
                {
                    "###", "###", '#', Block.planks
                });
        addRecipe(new ItemStack(Item.doorSteel, 1), new Object[]
                {
                    "##", "##", "##", '#', Item.ingotIron
                });
        addRecipe(new ItemStack(Item.sign, 1), new Object[]
                {
                    "###", "###", " X ", '#', Block.planks, 'X', Item.stick
                });
        addRecipe(new ItemStack(Item.cake, 1), new Object[]
                {
                    "AAA", "BEB", "CCC", 'A', Item.bucketMilk, 'B', Item.sugar, 'C', Item.wheat, 'E',
                    Item.egg
                });
        addRecipe(new ItemStack(Item.sugar, 1), new Object[]
                {
                    "#", '#', Item.reed
                });
        addRecipe(new ItemStack(Block.planks, 4, 0), new Object[]
                {
                    "#", '#', new ItemStack(Block.wood, 1, 0)
                });
        addRecipe(new ItemStack(Block.planks, 4, 1), new Object[]
                {
                    "#", '#', new ItemStack(Block.wood, 1, 1)
                });
        addRecipe(new ItemStack(Block.planks, 4, 2), new Object[]
                {
                    "#", '#', new ItemStack(Block.wood, 1, 2)
                });
        addRecipe(new ItemStack(Block.planks, 4, 3), new Object[]
                {
                    "#", '#', new ItemStack(Block.wood, 1, 3)
                });
        addRecipe(new ItemStack(Item.stick, 4), new Object[]
                {
                    "#", "#", '#', Block.planks
                });
        addRecipe(new ItemStack(Block.torchWood, 4), new Object[]
                {
                    "X", "#", 'X', Item.coal, '#', Item.stick
                });
        addRecipe(new ItemStack(Block.torchWood, 4), new Object[]
                {
                    "X", "#", 'X', new ItemStack(Item.coal, 1, 1), '#', Item.stick
                });
        addRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[]
                {
                    "# #", " # ", '#', Block.planks
                });
        addRecipe(new ItemStack(Item.glassBottle, 3), new Object[]
                {
                    "# #", " # ", '#', Block.glass
                });
        addRecipe(new ItemStack(Block.rail, 16), new Object[]
                {
                    "X X", "X#X", "X X", 'X', Item.ingotIron, '#', Item.stick
                });
        addRecipe(new ItemStack(Block.railPowered, 6), new Object[]
                {
                    "X X", "X#X", "XRX", 'X', Item.ingotGold, 'R', Item.redstone, '#', Item.stick
                });
        addRecipe(new ItemStack(Block.railDetector, 6), new Object[]
                {
                    "X X", "X#X", "XRX", 'X', Item.ingotIron, 'R', Item.redstone, '#', Block.pressurePlateStone
                });
        addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[]
                {
                    "# #", "###", '#', Item.ingotIron
                });
        addRecipe(new ItemStack(Item.cauldron, 1), new Object[]
                {
                    "# #", "# #", "###", '#', Item.ingotIron
                });
        addRecipe(new ItemStack(Item.brewingStand, 1), new Object[]
                {
                    " B ", "###", '#', Block.cobblestone, 'B', Item.blazeRod
                });
        addRecipe(new ItemStack(Block.pumpkinLantern, 1), new Object[]
                {
                    "A", "B", 'A', Block.pumpkin, 'B', Block.torchWood
                });
        addRecipe(new ItemStack(Item.minecartCrate, 1), new Object[]
                {
                    "A", "B", 'A', Block.chest, 'B', Item.minecartEmpty
                });
        addRecipe(new ItemStack(Item.minecartPowered, 1), new Object[]
                {
                    "A", "B", 'A', Block.stoneOvenIdle, 'B', Item.minecartEmpty
                });
        addRecipe(new ItemStack(Item.boat, 1), new Object[]
                {
                    "# #", "###", '#', Block.planks
                });
        addRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[]
                {
                    "# #", " # ", '#', Item.ingotIron
                });
        addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[]
                {
                    "A ", " B", 'A', Item.ingotIron, 'B', Item.flint
                });
        addRecipe(new ItemStack(Item.bread, 1), new Object[]
                {
                    "###", '#', Item.wheat
                });
        addRecipe(new ItemStack(Block.stairCompactPlanks, 4), new Object[]
                {
                    "#  ", "## ", "###", '#', Block.planks
                });
        addRecipe(new ItemStack(Item.fishingRod, 1), new Object[]
                {
                    "  #", " #X", "# X", '#', Item.stick, 'X', Item.silk
                });
        addRecipe(new ItemStack(Block.stairCompactCobblestone, 4), new Object[]
                {
                    "#  ", "## ", "###", '#', Block.cobblestone
                });
        addRecipe(new ItemStack(Block.stairsBrick, 4), new Object[]
                {
                    "#  ", "## ", "###", '#', Block.brick
                });
        addRecipe(new ItemStack(Block.stairsStoneBrickSmooth, 4), new Object[]
                {
                    "#  ", "## ", "###", '#', Block.stoneBrick
                });
        addRecipe(new ItemStack(Block.stairsNetherBrick, 4), new Object[]
                {
                    "#  ", "## ", "###", '#', Block.netherBrick
                });
        addRecipe(new ItemStack(Item.painting, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Item.stick, 'X', Block.cloth
                });
        addRecipe(new ItemStack(Item.appleGold, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Item.goldNugget, 'X', Item.appleRed
                });
        addRecipe(new ItemStack(Block.lever, 1), new Object[]
                {
                    "X", "#", '#', Block.cobblestone, 'X', Item.stick
                });
        addRecipe(new ItemStack(Block.torchRedstoneActive, 1), new Object[]
                {
                    "X", "#", '#', Item.stick, 'X', Item.redstone
                });
        addRecipe(new ItemStack(Item.redstoneRepeater, 1), new Object[]
                {
                    "#X#", "III", '#', Block.torchRedstoneActive, 'X', Item.redstone, 'I', Block.stone
                });
        addRecipe(new ItemStack(Item.pocketSundial, 1), new Object[]
                {
                    " # ", "#X#", " # ", '#', Item.ingotGold, 'X', Item.redstone
                });
        addRecipe(new ItemStack(Item.compass, 1), new Object[]
                {
                    " # ", "#X#", " # ", '#', Item.ingotIron, 'X', Item.redstone
                });
        addRecipe(new ItemStack(Item.map, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Item.paper, 'X', Item.compass
                });
        addRecipe(new ItemStack(Block.button, 1), new Object[]
                {
                    "#", "#", '#', Block.stone
                });
        addRecipe(new ItemStack(Block.pressurePlateStone, 1), new Object[]
                {
                    "##", '#', Block.stone
                });
        addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), new Object[]
                {
                    "##", '#', Block.planks
                });
        addRecipe(new ItemStack(Block.dispenser, 1), new Object[]
                {
                    "###", "#X#", "#R#", '#', Block.cobblestone, 'X', Item.bow, 'R', Item.redstone
                });
        addRecipe(new ItemStack(Block.pistonBase, 1), new Object[]
                {
                    "TTT", "#X#", "#R#", '#', Block.cobblestone, 'X', Item.ingotIron, 'R', Item.redstone, 'T',
                    Block.planks
                });
        addRecipe(new ItemStack(Block.pistonStickyBase, 1), new Object[]
                {
                    "S", "P", 'S', Item.slimeBall, 'P', Block.pistonBase
                });
        addRecipe(new ItemStack(Item.bed, 1), new Object[]
                {
                    "###", "XXX", '#', Block.cloth, 'X', Block.planks
                });
        addRecipe(new ItemStack(Block.enchantmentTable, 1), new Object[]
                {
                    " B ", "D#D", "###", '#', Block.obsidian, 'B', Item.book, 'D', Item.diamond
                });
        addShapelessRecipe(new ItemStack(Item.eyeOfEnder, 1), new Object[]
                {
                    Item.enderPearl, Item.blazePowder
                });
        addShapelessRecipe(new ItemStack(Item.fireballCharge, 3), new Object[]
                {
                    Item.gunpowder, Item.blazePowder, Item.coal
                });
        addShapelessRecipe(new ItemStack(Item.fireballCharge, 3), new Object[]
                {
                    Item.gunpowder, Item.blazePowder, new ItemStack(Item.coal, 1, 1)
                });
        Collections.sort(recipes, new RecipeSorter(this));
        System.out.println((new StringBuilder()).append(recipes.size()).append(" recipes").toString());
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    void addRecipe(ItemStack par1ItemStack, Object par2ArrayOfObj[])
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (par2ArrayOfObj[i] instanceof String[])
        {
            String as[] = (String[])par2ArrayOfObj[i++];

            for (int l = 0; l < as.length; l++)
            {
                String s2 = as[l];
                k++;
                j = s2.length();
                s = (new StringBuilder()).append(s).append(s2).toString();
            }
        }
        else
        {
            while (par2ArrayOfObj[i] instanceof String)
            {
                String s1 = (String)par2ArrayOfObj[i++];
                k++;
                j = s1.length();
                s = (new StringBuilder()).append(s).append(s1).toString();
            }
        }

        HashMap hashmap = new HashMap();

        for (; i < par2ArrayOfObj.length; i += 2)
        {
            Character character = (Character)par2ArrayOfObj[i];
            ItemStack itemstack = null;

            if (par2ArrayOfObj[i + 1] instanceof Item)
            {
                itemstack = new ItemStack((Item)par2ArrayOfObj[i + 1]);
            }
            else if (par2ArrayOfObj[i + 1] instanceof Block)
            {
                itemstack = new ItemStack((Block)par2ArrayOfObj[i + 1], 1, -1);
            }
            else if (par2ArrayOfObj[i + 1] instanceof ItemStack)
            {
                itemstack = (ItemStack)par2ArrayOfObj[i + 1];
            }

            hashmap.put(character, itemstack);
        }

        ItemStack aitemstack[] = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; i1++)
        {
            char c = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c))).copy();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        recipes.add(new ShapedRecipes(j, k, aitemstack, par1ItemStack));
    }

    void addShapelessRecipe(ItemStack par1ItemStack, Object par2ArrayOfObj[])
    {
        ArrayList arraylist = new ArrayList();
        Object aobj[] = par2ArrayOfObj;
        int i = aobj.length;

        for (int j = 0; j < i; j++)
        {
            Object obj = aobj[j];

            if (obj instanceof ItemStack)
            {
                arraylist.add(((ItemStack)obj).copy());
                continue;
            }

            if (obj instanceof Item)
            {
                arraylist.add(new ItemStack((Item)obj));
                continue;
            }

            if (obj instanceof Block)
            {
                arraylist.add(new ItemStack((Block)obj));
            }
            else
            {
                throw new RuntimeException("Invalid shapeless recipy!");
            }
        }

        recipes.add(new ShapelessRecipes(par1ItemStack, arraylist));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting)
    {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;

        for (int j = 0; j < par1InventoryCrafting.getSizeInventory(); j++)
        {
            ItemStack itemstack2 = par1InventoryCrafting.getStackInSlot(j);

            if (itemstack2 == null)
            {
                continue;
            }

            if (i == 0)
            {
                itemstack = itemstack2;
            }

            if (i == 1)
            {
                itemstack1 = itemstack2;
            }

            i++;
        }

        if (i == 2 && itemstack.itemID == itemstack1.itemID && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && Item.itemsList[itemstack.itemID].isDamageable())
        {
            Item item = Item.itemsList[itemstack.itemID];
            int l = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int i1 = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int j1 = l + i1 + (item.getMaxDamage() * 10) / 100;
            int k1 = item.getMaxDamage() - j1;

            if (k1 < 0)
            {
                k1 = 0;
            }

            return new ItemStack(itemstack.itemID, 1, k1);
        }

        for (int k = 0; k < recipes.size(); k++)
        {
            IRecipe irecipe = (IRecipe)recipes.get(k);

            if (irecipe.matches(par1InventoryCrafting))
            {
                return irecipe.getCraftingResult(par1InventoryCrafting);
            }
        }

        return null;
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return recipes;
    }
}
