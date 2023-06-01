package net.minecraft.src;

import java.io.*;

public class Packet1Login extends Packet
{
    /** The protocol version in use. Current version is 2. */
    public int protocolVersion;

    /** The name of the user attempting to login. */
    public String username;
    public WorldType terrainType;

    /** 0 for survival, 1 for creative */
    public int serverMode;
    public int field_48170_e;

    /** The difficulty setting byte. */
    public byte difficultySetting;

    /** Defaults to 128 */
    public byte worldHeight;

    /** The maximum players. */
    public byte maxPlayers;

    public Packet1Login()
    {
    }

    public Packet1Login(String par1Str, int par2)
    {
        username = par1Str;
        protocolVersion = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        protocolVersion = par1DataInputStream.readInt();
        username = readString(par1DataInputStream, 16);
        String s = readString(par1DataInputStream, 16);
        terrainType = WorldType.parseWorldType(s);

        if (terrainType == null)
        {
            terrainType = WorldType.DEFAULT;
        }

        serverMode = par1DataInputStream.readInt();
        field_48170_e = par1DataInputStream.readInt();
        difficultySetting = par1DataInputStream.readByte();
        worldHeight = par1DataInputStream.readByte();
        maxPlayers = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(protocolVersion);
        writeString(username, par1DataOutputStream);

        if (terrainType == null)
        {
            writeString("", par1DataOutputStream);
        }
        else
        {
            writeString(terrainType.func_48628_a(), par1DataOutputStream);
        }

        par1DataOutputStream.writeInt(serverMode);
        par1DataOutputStream.writeInt(field_48170_e);
        par1DataOutputStream.writeByte(difficultySetting);
        par1DataOutputStream.writeByte(worldHeight);
        par1DataOutputStream.writeByte(maxPlayers);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleLogin(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        int i = 0;

        if (terrainType != null)
        {
            i = terrainType.func_48628_a().length();
        }

        return 4 + username.length() + 4 + 7 + 7 + i;
    }
}
