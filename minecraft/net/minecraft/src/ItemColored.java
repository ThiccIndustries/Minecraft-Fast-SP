package net.minecraft.src;

public class ItemColored extends ItemBlock
{
    private final Block blockRef;
    private String blockNames[];

    public ItemColored(int par1, boolean par2)
    {
        super(par1);
        blockRef = Block.blocksList[getBlockID()];

        if (par2)
        {
            setMaxDamage(0);
            setHasSubtypes(true);
        }
    }

    public int getColorFromDamage(int par1, int par2)
    {
        return blockRef.getRenderColor(par1);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return blockRef.getBlockTextureFromSideAndMetadata(0, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    /**
     * sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemColored setBlockNames(String par1ArrayOfStr[])
    {
        blockNames = par1ArrayOfStr;
        return this;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        if (blockNames == null)
        {
            return super.getItemNameIS(par1ItemStack);
        }

        int i = par1ItemStack.getItemDamage();

        if (i >= 0 && i < blockNames.length)
        {
            return (new StringBuilder()).append(super.getItemNameIS(par1ItemStack)).append(".").append(blockNames[i]).toString();
        }
        else
        {
            return super.getItemNameIS(par1ItemStack);
        }
    }
}
