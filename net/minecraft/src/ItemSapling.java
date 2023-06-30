package net.minecraft.src;

public class ItemSapling extends ItemBlock
{
    public ItemSapling(int par1)
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
        return par1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return Block.sapling.getBlockTextureFromSideAndMetadata(0, par1);
    }
}
