package net.minecraft.src;

import java.io.*;
import java.util.*;

public class Packet60Explosion extends Packet
{
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public float explosionSize;
    public Set destroyedBlockPositions;

    public Packet60Explosion()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        explosionX = par1DataInputStream.readDouble();
        explosionY = par1DataInputStream.readDouble();
        explosionZ = par1DataInputStream.readDouble();
        explosionSize = par1DataInputStream.readFloat();
        int i = par1DataInputStream.readInt();
        destroyedBlockPositions = new HashSet();
        int j = (int)explosionX;
        int k = (int)explosionY;
        int l = (int)explosionZ;

        for (int i1 = 0; i1 < i; i1++)
        {
            int j1 = par1DataInputStream.readByte() + j;
            int k1 = par1DataInputStream.readByte() + k;
            int l1 = par1DataInputStream.readByte() + l;
            destroyedBlockPositions.add(new ChunkPosition(j1, k1, l1));
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeDouble(explosionX);
        par1DataOutputStream.writeDouble(explosionY);
        par1DataOutputStream.writeDouble(explosionZ);
        par1DataOutputStream.writeFloat(explosionSize);
        par1DataOutputStream.writeInt(destroyedBlockPositions.size());
        int i = (int)explosionX;
        int j = (int)explosionY;
        int k = (int)explosionZ;
        int j1;

        for (Iterator iterator = destroyedBlockPositions.iterator(); iterator.hasNext(); par1DataOutputStream.writeByte(j1))
        {
            ChunkPosition chunkposition = (ChunkPosition)iterator.next();
            int l = chunkposition.x - i;
            int i1 = chunkposition.y - j;
            j1 = chunkposition.z - k;
            par1DataOutputStream.writeByte(l);
            par1DataOutputStream.writeByte(i1);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleExplosion(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 32 + destroyedBlockPositions.size() * 3;
    }
}
