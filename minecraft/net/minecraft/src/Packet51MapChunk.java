package net.minecraft.src;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Packet51MapChunk extends Packet
{
    /** The x-position of the transmitted chunk, in chunk coordinates. */
    public int xCh;

    /** The z-position of the transmitted chunk, in chunk coordinates. */
    public int zCh;

    /**
     * The y-position of the lowest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int yChMin;

    /**
     * The y-position of the highest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int yChMax;
    public byte chunkData[];

    /**
     * Whether to initialize the Chunk before applying the effect of the Packet51MapChunk.
     */
    public boolean includeInitialize;

    /** The length of the compressed chunk data byte array. */
    private int tempLength;
    private int field_48178_h;
    private static byte temp[] = new byte[0];

    public Packet51MapChunk()
    {
        isChunkDataPacket = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xCh = par1DataInputStream.readInt();
        zCh = par1DataInputStream.readInt();
        includeInitialize = par1DataInputStream.readBoolean();
        yChMin = par1DataInputStream.readShort();
        yChMax = par1DataInputStream.readShort();
        tempLength = par1DataInputStream.readInt();
        field_48178_h = par1DataInputStream.readInt();

        if (temp.length < tempLength)
        {
            temp = new byte[tempLength];
        }

        par1DataInputStream.readFully(temp, 0, tempLength);
        int i = 0;

        for (int j = 0; j < 16; j++)
        {
            i += yChMin >> j & 1;
        }

        int k = 12288 * i;

        if (includeInitialize)
        {
            k += 256;
        }

        chunkData = new byte[k];
        Inflater inflater = new Inflater();
        inflater.setInput(temp, 0, tempLength);

        try
        {
            inflater.inflate(chunkData);
        }
        catch (DataFormatException dataformatexception)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            inflater.end();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xCh);
        par1DataOutputStream.writeInt(zCh);
        par1DataOutputStream.writeBoolean(includeInitialize);
        par1DataOutputStream.writeShort((short)(yChMin & 0xffff));
        par1DataOutputStream.writeShort((short)(yChMax & 0xffff));
        par1DataOutputStream.writeInt(tempLength);
        par1DataOutputStream.writeInt(field_48178_h);
        par1DataOutputStream.write(chunkData, 0, tempLength);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_48487_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 17 + tempLength;
    }
}
