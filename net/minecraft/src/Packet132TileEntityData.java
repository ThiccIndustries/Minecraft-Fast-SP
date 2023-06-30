package net.minecraft.src;

import java.io.*;

public class Packet132TileEntityData extends Packet
{
    /** The X position of the tile entity to update. */
    public int xPosition;

    /** The Y position of the tile entity to update. */
    public int yPosition;

    /** The Z position of the tile entity to update. */
    public int zPosition;

    /** The type of update to perform on the tile entity. */
    public int actionType;

    /** Custom parameter 1 passed to the tile entity on update. */
    public int customParam1;

    /** Custom parameter 2 passed to the tile entity on update. */
    public int customParam2;

    /** Custom parameter 3 passed to the tile entity on update. */
    public int customParam3;

    public Packet132TileEntityData()
    {
        isChunkDataPacket = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readShort();
        zPosition = par1DataInputStream.readInt();
        actionType = par1DataInputStream.readByte();
        customParam1 = par1DataInputStream.readInt();
        customParam2 = par1DataInputStream.readInt();
        customParam3 = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeShort(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeByte((byte)actionType);
        par1DataOutputStream.writeInt(customParam1);
        par1DataOutputStream.writeInt(customParam2);
        par1DataOutputStream.writeInt(customParam3);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleTileEntityData(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 25;
    }
}
