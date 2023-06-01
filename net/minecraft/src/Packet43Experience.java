package net.minecraft.src;

import java.io.*;

public class Packet43Experience extends Packet
{
    /** The current experience bar points. */
    public float experience;

    /** The total experience points. */
    public int experienceTotal;

    /** The experience level. */
    public int experienceLevel;

    public Packet43Experience()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        experience = par1DataInputStream.readFloat();
        experienceLevel = par1DataInputStream.readShort();
        experienceTotal = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeFloat(experience);
        par1DataOutputStream.writeShort(experienceLevel);
        par1DataOutputStream.writeShort(experienceTotal);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleExperience(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 4;
    }
}
