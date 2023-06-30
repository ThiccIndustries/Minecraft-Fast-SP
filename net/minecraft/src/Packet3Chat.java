package net.minecraft.src;

import java.io.*;

public class Packet3Chat extends Packet
{
    public static int field_52010_b = 119;

    /** The message being sent. */
    public String message;

    public Packet3Chat()
    {
    }

    public Packet3Chat(String par1Str)
    {
        if (par1Str.length() > field_52010_b)
        {
            par1Str = par1Str.substring(0, field_52010_b);
        }

        message = par1Str;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        message = readString(par1DataInputStream, field_52010_b);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(message, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleChat(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + message.length() * 2;
    }
}
