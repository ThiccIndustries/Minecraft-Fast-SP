package net.minecraft.src;

import java.io.*;

public class Packet105UpdateProgressbar extends Packet
{
    /** The id of the window that the progress bar is in. */
    public int windowId;

    /**
     * Which of the progress bars that should be updated. (For furnaces, 0 = progress arrow, 1 = fire icon)
     */
    public int progressBar;

    /**
     * The value of the progress bar. The maximum values vary depending on the progress bar. Presumably the values are
     * specified as in-game ticks. Some progress bar values increase, while others decrease. For furnaces, 0 is empty,
     * full progress arrow = about 180, full fire icon = about 250)
     */
    public int progressBarValue;

    public Packet105UpdateProgressbar()
    {
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleUpdateProgressbar(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte();
        progressBar = par1DataInputStream.readShort();
        progressBarValue = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId);
        par1DataOutputStream.writeShort(progressBar);
        par1DataOutputStream.writeShort(progressBarValue);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 5;
    }
}
