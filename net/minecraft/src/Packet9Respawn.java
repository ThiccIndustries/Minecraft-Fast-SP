package net.minecraft.src;

import java.io.*;

public class Packet9Respawn extends Packet
{
    public int respawnDimension;

    /** 0 thru 3 for Peaceful, Easy, Normal, Hard. 1 is always sent c->s */
    public int difficulty;

    /** Defaults to 128 */
    public int worldHeight;

    /** 0 for survival, 1 for creative */
    public int creativeMode;
    public WorldType terrainType;

    public Packet9Respawn()
    {
    }

    public Packet9Respawn(int par1, byte par2, WorldType par3WorldType, int par4, int par5)
    {
        respawnDimension = par1;
        difficulty = par2;
        worldHeight = par4;
        creativeMode = par5;
        terrainType = par3WorldType;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleRespawn(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        respawnDimension = par1DataInputStream.readInt();
        difficulty = par1DataInputStream.readByte();
        creativeMode = par1DataInputStream.readByte();
        worldHeight = par1DataInputStream.readShort();
        String s = readString(par1DataInputStream, 16);
        terrainType = WorldType.parseWorldType(s);

        if (terrainType == null)
        {
            terrainType = WorldType.DEFAULT;
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(respawnDimension);
        par1DataOutputStream.writeByte(difficulty);
        par1DataOutputStream.writeByte(creativeMode);
        par1DataOutputStream.writeShort(worldHeight);
        writeString(terrainType.func_48628_a(), par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 8 + terrainType.func_48628_a().length();
    }
}
