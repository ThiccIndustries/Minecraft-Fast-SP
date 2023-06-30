package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes
{
    private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();

    /** The list of smelting results. */
    private Map smeltingList;

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final FurnaceRecipes smelting()
    {
        return smeltingBase;
    }

    private FurnaceRecipes()
    {
        smeltingList = new HashMap();
        addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron));
        addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold));
        addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond));
        addSmelting(Block.sand.blockID, new ItemStack(Block.glass));
        addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked));
        addSmelting(Item.beefRaw.shiftedIndex, new ItemStack(Item.beefCooked));
        addSmelting(Item.chickenRaw.shiftedIndex, new ItemStack(Item.chickenCooked));
        addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked));
        addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone));
        addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick));
        addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2));
        addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1));
        addSmelting(Block.oreCoal.blockID, new ItemStack(Item.coal));
        addSmelting(Block.oreRedstone.blockID, new ItemStack(Item.redstone));
        addSmelting(Block.oreLapis.blockID, new ItemStack(Item.dyePowder, 1, 4));
    }

    /**
     * Adds a smelting recipe.
     */
    public void addSmelting(int par1, ItemStack par2ItemStack)
    {
        smeltingList.put(Integer.valueOf(par1), par2ItemStack);
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(int par1)
    {
        return (ItemStack)smeltingList.get(Integer.valueOf(par1));
    }

    public Map getSmeltingList()
    {
        return smeltingList;
    }
}
