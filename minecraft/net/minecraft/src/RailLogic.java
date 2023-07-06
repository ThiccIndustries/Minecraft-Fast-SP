package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class RailLogic
{
    /** Reference to the World object. */
    private World worldObj;
    private int trackX;
    private int trackY;
    private int trackZ;

    /**
     * A boolean value that is true if the rail is powered, and false if its not.
     */
    private final boolean isPoweredRail;
    private List connectedTracks;
    final BlockRail rail;

    public RailLogic(BlockRail par1BlockRail, World par2World, int par3, int par4, int par5)
    {
        rail = par1BlockRail;
        connectedTracks = new ArrayList();
        worldObj = par2World;
        trackX = par3;
        trackY = par4;
        trackZ = par5;
        int i = par2World.getBlockId(par3, par4, par5);
        int j = par2World.getBlockMetadata(par3, par4, par5);

        if (BlockRail.isPoweredBlockRail((BlockRail)Block.blocksList[i]))
        {
            isPoweredRail = true;
            j &= -9;
        }
        else
        {
            isPoweredRail = false;
        }

        setConnections(j);
    }

    private void setConnections(int par1)
    {
        connectedTracks.clear();

        if (par1 == 0)
        {
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
        }
        else if (par1 == 1)
        {
            connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
        }
        else if (par1 == 2)
        {
            connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX + 1, trackY + 1, trackZ));
        }
        else if (par1 == 3)
        {
            connectedTracks.add(new ChunkPosition(trackX - 1, trackY + 1, trackZ));
            connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
        }
        else if (par1 == 4)
        {
            connectedTracks.add(new ChunkPosition(trackX, trackY + 1, trackZ - 1));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
        }
        else if (par1 == 5)
        {
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
            connectedTracks.add(new ChunkPosition(trackX, trackY + 1, trackZ + 1));
        }
        else if (par1 == 6)
        {
            connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
        }
        else if (par1 == 7)
        {
            connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
        }
        else if (par1 == 8)
        {
            connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
        }
        else if (par1 == 9)
        {
            connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
            connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
        }
    }

    /**
     * Neighboring tracks have potentially been broken, so prune the connected track list
     */
    private void refreshConnectedTracks()
    {
        for (int i = 0; i < connectedTracks.size(); i++)
        {
            RailLogic raillogic = getMinecartTrackLogic((ChunkPosition)connectedTracks.get(i));

            if (raillogic == null || !raillogic.isConnectedTo(this))
            {
                connectedTracks.remove(i--);
            }
            else
            {
                connectedTracks.set(i, new ChunkPosition(raillogic.trackX, raillogic.trackY, raillogic.trackZ));
            }
        }
    }

    private boolean isMinecartTrack(int par1, int par2, int par3)
    {
        if (BlockRail.isRailBlockAt(worldObj, par1, par2, par3))
        {
            return true;
        }

        if (BlockRail.isRailBlockAt(worldObj, par1, par2 + 1, par3))
        {
            return true;
        }

        return BlockRail.isRailBlockAt(worldObj, par1, par2 - 1, par3);
    }

    private RailLogic getMinecartTrackLogic(ChunkPosition par1ChunkPosition)
    {
        if (BlockRail.isRailBlockAt(worldObj, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z))
        {
            return new RailLogic(rail, worldObj, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z);
        }

        if (BlockRail.isRailBlockAt(worldObj, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z))
        {
            return new RailLogic(rail, worldObj, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z);
        }

        if (BlockRail.isRailBlockAt(worldObj, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z))
        {
            return new RailLogic(rail, worldObj, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z);
        }
        else
        {
            return null;
        }
    }

    private boolean isConnectedTo(RailLogic par1RailLogic)
    {
        for (int i = 0; i < connectedTracks.size(); i++)
        {
            ChunkPosition chunkposition = (ChunkPosition)connectedTracks.get(i);

            if (chunkposition.x == par1RailLogic.trackX && chunkposition.z == par1RailLogic.trackZ)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the specified block is in the same railway.
     */
    private boolean isInTrack(int par1, int par2, int par3)
    {
        for (int i = 0; i < connectedTracks.size(); i++)
        {
            ChunkPosition chunkposition = (ChunkPosition)connectedTracks.get(i);

            if (chunkposition.x == par1 && chunkposition.z == par3)
            {
                return true;
            }
        }

        return false;
    }

    private int getAdjacentTracks()
    {
        int i = 0;

        if (isMinecartTrack(trackX, trackY, trackZ - 1))
        {
            i++;
        }

        if (isMinecartTrack(trackX, trackY, trackZ + 1))
        {
            i++;
        }

        if (isMinecartTrack(trackX - 1, trackY, trackZ))
        {
            i++;
        }

        if (isMinecartTrack(trackX + 1, trackY, trackZ))
        {
            i++;
        }

        return i;
    }

    /**
     * Determines whether or not the track can bend to meet the specified rail
     */
    private boolean canConnectTo(RailLogic par1RailLogic)
    {
        if (isConnectedTo(par1RailLogic))
        {
            return true;
        }

        if (connectedTracks.size() == 2)
        {
            return false;
        }

        if (connectedTracks.size() == 0)
        {
            return true;
        }

        ChunkPosition chunkposition = (ChunkPosition)connectedTracks.get(0);
        return par1RailLogic.trackY != trackY || chunkposition.y != trackY ? true : true;
    }

    /**
     * The specified neighbor has just formed a new connection, so update accordingly
     */
    private void connectToNeighbor(RailLogic par1RailLogic)
    {
        connectedTracks.add(new ChunkPosition(par1RailLogic.trackX, par1RailLogic.trackY, par1RailLogic.trackZ));
        boolean flag = isInTrack(trackX, trackY, trackZ - 1);
        boolean flag1 = isInTrack(trackX, trackY, trackZ + 1);
        boolean flag2 = isInTrack(trackX - 1, trackY, trackZ);
        boolean flag3 = isInTrack(trackX + 1, trackY, trackZ);
        byte byte0 = -1;

        if (flag || flag1)
        {
            byte0 = 0;
        }

        if (flag2 || flag3)
        {
            byte0 = 1;
        }

        if (!isPoweredRail)
        {
            if (flag1 && flag3 && !flag && !flag2)
            {
                byte0 = 6;
            }

            if (flag1 && flag2 && !flag && !flag3)
            {
                byte0 = 7;
            }

            if (flag && flag2 && !flag1 && !flag3)
            {
                byte0 = 8;
            }

            if (flag && flag3 && !flag1 && !flag2)
            {
                byte0 = 9;
            }
        }

        if (byte0 == 0)
        {
            if (BlockRail.isRailBlockAt(worldObj, trackX, trackY + 1, trackZ - 1))
            {
                byte0 = 4;
            }

            if (BlockRail.isRailBlockAt(worldObj, trackX, trackY + 1, trackZ + 1))
            {
                byte0 = 5;
            }
        }

        if (byte0 == 1)
        {
            if (BlockRail.isRailBlockAt(worldObj, trackX + 1, trackY + 1, trackZ))
            {
                byte0 = 2;
            }

            if (BlockRail.isRailBlockAt(worldObj, trackX - 1, trackY + 1, trackZ))
            {
                byte0 = 3;
            }
        }

        if (byte0 < 0)
        {
            byte0 = 0;
        }

        int i = byte0;

        if (isPoweredRail)
        {
            i = worldObj.getBlockMetadata(trackX, trackY, trackZ) & 8 | byte0;
        }

        worldObj.setBlockMetadataWithNotify(trackX, trackY, trackZ, i);
    }

    /**
     * Determines whether or not the target rail can connect to this rail
     */
    private boolean canConnectFrom(int par1, int par2, int par3)
    {
        RailLogic raillogic = getMinecartTrackLogic(new ChunkPosition(par1, par2, par3));

        if (raillogic == null)
        {
            return false;
        }
        else
        {
            raillogic.refreshConnectedTracks();
            return raillogic.canConnectTo(this);
        }
    }

    /**
     * Completely recalculates the track shape based on neighboring tracks and power state
     */
    public void refreshTrackShape(boolean par1, boolean par2)
    {
        boolean flag = canConnectFrom(trackX, trackY, trackZ - 1);
        boolean flag1 = canConnectFrom(trackX, trackY, trackZ + 1);
        boolean flag2 = canConnectFrom(trackX - 1, trackY, trackZ);
        boolean flag3 = canConnectFrom(trackX + 1, trackY, trackZ);
        byte byte0 = -1;

        if ((flag || flag1) && !flag2 && !flag3)
        {
            byte0 = 0;
        }

        if ((flag2 || flag3) && !flag && !flag1)
        {
            byte0 = 1;
        }

        if (!isPoweredRail)
        {
            if (flag1 && flag3 && !flag && !flag2)
            {
                byte0 = 6;
            }

            if (flag1 && flag2 && !flag && !flag3)
            {
                byte0 = 7;
            }

            if (flag && flag2 && !flag1 && !flag3)
            {
                byte0 = 8;
            }

            if (flag && flag3 && !flag1 && !flag2)
            {
                byte0 = 9;
            }
        }

        if (byte0 == -1)
        {
            if (flag || flag1)
            {
                byte0 = 0;
            }

            if (flag2 || flag3)
            {
                byte0 = 1;
            }

            if (!isPoweredRail)
            {
                if (par1)
                {
                    if (flag1 && flag3)
                    {
                        byte0 = 6;
                    }

                    if (flag2 && flag1)
                    {
                        byte0 = 7;
                    }

                    if (flag3 && flag)
                    {
                        byte0 = 9;
                    }

                    if (flag && flag2)
                    {
                        byte0 = 8;
                    }
                }
                else
                {
                    if (flag && flag2)
                    {
                        byte0 = 8;
                    }

                    if (flag3 && flag)
                    {
                        byte0 = 9;
                    }

                    if (flag2 && flag1)
                    {
                        byte0 = 7;
                    }

                    if (flag1 && flag3)
                    {
                        byte0 = 6;
                    }
                }
            }
        }

        if (byte0 == 0)
        {
            if (BlockRail.isRailBlockAt(worldObj, trackX, trackY + 1, trackZ - 1))
            {
                byte0 = 4;
            }

            if (BlockRail.isRailBlockAt(worldObj, trackX, trackY + 1, trackZ + 1))
            {
                byte0 = 5;
            }
        }

        if (byte0 == 1)
        {
            if (BlockRail.isRailBlockAt(worldObj, trackX + 1, trackY + 1, trackZ))
            {
                byte0 = 2;
            }

            if (BlockRail.isRailBlockAt(worldObj, trackX - 1, trackY + 1, trackZ))
            {
                byte0 = 3;
            }
        }

        if (byte0 < 0)
        {
            byte0 = 0;
        }

        setConnections(byte0);
        int i = byte0;

        if (isPoweredRail)
        {
            i = worldObj.getBlockMetadata(trackX, trackY, trackZ) & 8 | byte0;
        }

        if (par2 || worldObj.getBlockMetadata(trackX, trackY, trackZ) != i)
        {
            worldObj.setBlockMetadataWithNotify(trackX, trackY, trackZ, i);

            for (int j = 0; j < connectedTracks.size(); j++)
            {
                RailLogic raillogic = getMinecartTrackLogic((ChunkPosition)connectedTracks.get(j));

                if (raillogic == null)
                {
                    continue;
                }

                raillogic.refreshConnectedTracks();

                if (raillogic.canConnectTo(this))
                {
                    raillogic.connectToNeighbor(this);
                }
            }
        }
    }

    /**
     * get number of adjacent tracks
     */
    static int getNAdjacentTracks(RailLogic par0RailLogic)
    {
        return par0RailLogic.getAdjacentTracks();
    }
}
