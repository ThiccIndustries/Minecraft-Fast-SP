package net.minecraft.src;

import java.io.*;

public class Packet250CustomPayload extends Packet
{
    /** Name of the 'channel' used to send data */
    public String channel;

    /** Length of the data to be read */
    public int length;
    public byte data[];

    public Packet250CustomPayload()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        channel = readString(par1DataInputStream, 16);
        length = par1DataInputStream.readShort();

        if (length > 0 && length < 32767)
        {
            data = new byte[length];
            par1DataInputStream.readFully(data);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(channel, par1DataOutputStream);
        par1DataOutputStream.writeShort((short)length);

        if (data != null)
        {
            par1DataOutputStream.write(data);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleCustomPayload(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + channel.length() * 2 + 2 + length;
    }
}
