package net.minecraft.src;

public class ItemLeaves extends ItemBlock
{
    public ItemLeaves(int par1)
    {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1 | 4;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return Block.leaves.getBlockTextureFromSideAndMetadata(0, par1);
    }

    public int getColorFromDamage(int par1, int par2)
    {
        if ((par1 & 1) == 1)
        {
            return ColorizerFoliage.getFoliageColorPine();
        }

        if ((par1 & 2) == 2)
        {
            return ColorizerFoliage.getFoliageColorBirch();
        }
        else
        {
            return ColorizerFoliage.getFoliageColorBasic();
        }
    }
}
