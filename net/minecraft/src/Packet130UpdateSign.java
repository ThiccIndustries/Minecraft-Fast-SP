package net.minecraft.src;

import java.io.*;

public class Packet130UpdateSign extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public String signLines[];

    public Packet130UpdateSign()
    {
        isChunkDataPacket = true;
    }

    public Packet130UpdateSign(int par1, int par2, int par3, String par4ArrayOfStr[])
    {
        isChunkDataPacket = true;
        xPosition = par1;
        yPosition = par2;
        zPosition = par3;
        signLines = par4ArrayOfStr;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readShort();
        zPosition = par1DataInputStream.readInt();
        signLines = new String[4];

        for (int i = 0; i < 4; i++)
        {
            signLines[i] = readString(par1DataInputStream, 15);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeShort(yPosition);
        par1DataOutputStream.writeInt(zPosition);

        for (int i = 0; i < 4; i++)
        {
            writeString(signLines[i], par1DataOutputStream);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleUpdateSign(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        int i = 0;

        for (int j = 0; j < 4; j++)
        {
            i += signLines[j].length();
        }

        return i;
    }
}
