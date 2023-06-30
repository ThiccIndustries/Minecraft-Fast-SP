package net.minecraft.src;

class RedstoneUpdateInfo
{
    int x;
    int y;
    int z;
    long updateTime;

    public RedstoneUpdateInfo(int par1, int par2, int par3, long par4)
    {
        x = par1;
        y = par2;
        z = par3;
        updateTime = par4;
    }
}
