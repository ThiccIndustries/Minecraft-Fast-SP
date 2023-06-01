package net.minecraft.src;

public class ColorizerGrass
{
    private static int grassBuffer[] = new int[0x10000];

    public ColorizerGrass()
    {
    }

    public static void setGrassBiomeColorizer(int par0ArrayOfInteger[])
    {
        grassBuffer = par0ArrayOfInteger;
    }

    /**
     * Gets grass color from temperature and humidity. Args: temperature, humidity
     */
    public static int getGrassColor(double par0, double par2)
    {
        par2 *= par0;
        int i = (int)((1.0D - par0) * 255D);
        int j = (int)((1.0D - par2) * 255D);
        return grassBuffer[j << 8 | i];
    }
}
