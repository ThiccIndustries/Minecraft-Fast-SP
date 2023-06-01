package net.minecraft.src;

import java.io.*;

public class Packet52MultiBlockChange extends Packet
{
    /** Chunk X position. */
    public int xPosition;

    /** Chunk Z position. */
    public int zPosition;
    public byte metadataArray[];

    /** The size of the arrays. */
    public int size;
    private static byte field_48168_e[] = new byte[0];

    public Packet52MultiBlockChange()
    {
        isChunkDataPacket = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        size = par1DataInputStream.readShort() & 0xffff;
        int i = par1DataInputStream.readInt();

        if (i > 0)
        {
            metadataArray = new byte[i];
            par1DataInputStream.readFully(metadataArray);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeShort((short)size);

        if (metadataArray != null)
        {
            par1DataOutputStream.writeInt(metadataArray.length);
            par1DataOutputStream.write(metadataArray);
        }
        else
        {
            par1DataOutputStream.writeInt(0);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMultiBlockChange(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 10 + size * 4;
    }
}
