package net.minecraft.src;

import java.io.*;

public class Packet254ServerPing extends Packet
{
    public Packet254ServerPing()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleServerPing(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 0;
    }
}
