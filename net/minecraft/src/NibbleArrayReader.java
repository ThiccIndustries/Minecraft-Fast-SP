package net.minecraft.src;

public class NibbleArrayReader
{
    public final byte data[];
    private final int depthBits;
    private final int depthBitsPlusFour;

    public NibbleArrayReader(byte par1ArrayOfByte[], int par2)
    {
        data = par1ArrayOfByte;
        depthBits = par2;
        depthBitsPlusFour = par2 + 4;
    }

    public int get(int par1, int par2, int par3)
    {
        int i = par1 << depthBitsPlusFour | par3 << depthBits | par2;
        int j = i >> 1;
        int k = i & 1;

        if (k == 0)
        {
            return data[j] & 0xf;
        }
        else
        {
            return data[j] >> 4 & 0xf;
        }
    }
}
