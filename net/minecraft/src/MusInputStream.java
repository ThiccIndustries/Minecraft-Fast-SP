package net.minecraft.src;

import java.io.InputStream;
import java.net.URL;

class MusInputStream extends InputStream
{
    private int hash;
    private InputStream inputStream;
    byte buffer[];
    final CodecMus codec;

    public MusInputStream(CodecMus par1CodecMus, URL par2URL, InputStream par3InputStream)
    {
        codec = par1CodecMus;
        buffer = new byte[1];
        inputStream = par3InputStream;
        String s = par2URL.getPath();
        s = s.substring(s.lastIndexOf("/") + 1);
        hash = s.hashCode();
    }

    public int read()
    {
        int i = read(buffer, 0, 1);

        if (i < 0)
        {
            return i;
        }
        else
        {
            return buffer[0];
        }
    }

    public int read(byte par1ArrayOfByte[], int par2, int par3)
    {
        try
        {
            par3 = inputStream.read(par1ArrayOfByte, par2, par3);
        }
        catch (Throwable t)
        {
            return 0;
        }

        for (int i = 0; i < par3; i++)
        {
            byte byte0 = par1ArrayOfByte[par2 + i] ^= hash >> 8;
            hash = hash * 0x1dba038f + 0x14ee3 * byte0;
        }

        return par3;
    }
}
