package net.minecraft.src;

import java.io.*;

public class Packet14BlockDig extends Packet
{
    /** Block X position. */
    public int xPosition;

    /** Block Y position. */
    public int yPosition;

    /** Block Z position. */
    public int zPosition;

    /** Punched face of the block. */
    public int face;

    /** Status of the digging (started, ongoing, broken). */
    public int status;

    public Packet14BlockDig()
    {
    }

    public Packet14BlockDig(int par1, int par2, int par3, int par4, int par5)
    {
        status = par1;
        xPosition = par2;
        yPosition = par3;
        zPosition = par4;
        face = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        status = par1DataInputStream.read();
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.read();
        zPosition = par1DataInputStream.readInt();
        face = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.write(status);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.write(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.write(face);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleBlockDig(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 11;
    }
}
