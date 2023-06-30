package net.minecraft.src;

import java.io.*;

public class Packet2Handshake extends Packet
{
    /** The username of the player attempting to connect. */
    public String username;

    public Packet2Handshake()
    {
    }

    public Packet2Handshake(String par1Str)
    {
        username = par1Str;
    }

    public Packet2Handshake(String par1Str, String par2Str, int par3)
    {
        username = (new StringBuilder()).append(par1Str).append(";").append(par2Str).append(":").append(par3).toString();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        username = readString(par1DataInputStream, 64);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(username, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleHandshake(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 4 + username.length() + 4;
    }
}
