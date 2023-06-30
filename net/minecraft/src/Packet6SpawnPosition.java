package net.minecraft.src;

import java.io.*;

public class Packet6SpawnPosition extends Packet
{
    /** X coordinate of spawn. */
    public int xPosition;

    /** Y coordinate of spawn. */
    public int yPosition;

    /** Z coordinate of spawn. */
    public int zPosition;

    public Packet6SpawnPosition()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSpawnPosition(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 12;
    }
}
