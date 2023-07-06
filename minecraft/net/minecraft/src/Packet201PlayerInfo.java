package net.minecraft.src;

import java.io.*;

public class Packet201PlayerInfo extends Packet
{
    /** The player's name. */
    public String playerName;

    /** Byte that tells whether the player is connected. */
    public boolean isConnected;
    public int ping;

    public Packet201PlayerInfo()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        playerName = readString(par1DataInputStream, 16);
        isConnected = par1DataInputStream.readByte() != 0;
        ping = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(playerName, par1DataOutputStream);
        par1DataOutputStream.writeByte(isConnected ? 1 : 0);
        par1DataOutputStream.writeShort(ping);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlayerInfo(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return playerName.length() + 2 + 1 + 2;
    }
}
