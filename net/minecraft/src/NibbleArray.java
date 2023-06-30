package net.minecraft.src;

public class NibbleArray
{
    public final byte data[];

    /**
     * Log base 2 of the chunk height (128); applied as a shift on Z coordinate
     */
    private final int depthBits;

    /**
     * Log base 2 of the chunk height (128) * width (16); applied as a shift on X coordinate
     */
    private final int depthBitsPlusFour;

    public NibbleArray(int par1, int par2)
    {
        data = new byte[par1 >> 1];
        depthBits = par2;
        depthBitsPlusFour = par2 + 4;
    }

    public NibbleArray(byte par1ArrayOfByte[], int par2)
    {
        data = par1ArrayOfByte;
        depthBits = par2;
        depthBitsPlusFour = par2 + 4;
    }

    /**
     * Returns the nibble of data corresponding to the passed in x, y, z. y is at most 6 bits, z is at most 4.
     */
    public int get(int par1, int par2, int par3)
    {
        int i = par2 << depthBitsPlusFour | par3 << depthBits | par1;
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

    /**
     * Arguments are x, y, z, val. Sets the nibble of data at x << 11 | z << 7 | y to val.
     */
    public void set(int par1, int par2, int par3, int par4)
    {
        int i = par2 << depthBitsPlusFour | par3 << depthBits | par1;
        int j = i >> 1;
        int k = i & 1;

        if (k == 0)
        {
            data[j] = (byte)(data[j] & 0xf0 | par4 & 0xf);
        }
        else
        {
            data[j] = (byte)(data[j] & 0xf | (par4 & 0xf) << 4);
        }
    }
}
