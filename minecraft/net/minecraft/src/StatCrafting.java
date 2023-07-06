package net.minecraft.src;

public class StatCrafting extends StatBase
{
    private final int itemID;

    public StatCrafting(int par1, String par2Str, int par3)
    {
        super(par1, par2Str);
        itemID = par3;
    }

    public int getItemID()
    {
        return itemID;
    }
}
