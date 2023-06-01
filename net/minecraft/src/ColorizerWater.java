package net.minecraft.src;

public class ColorizerWater
{
    private static int waterBuffer[] = new int[0x10000];

    public ColorizerWater()
    {
    }

    public static void setWaterBiomeColorizer(int par0ArrayOfInteger[])
    {
        waterBuffer = par0ArrayOfInteger;
    }
}
