package net.minecraft.src;

public class ChunkCoordIntPair
{
    /** The X position of this Chunk Coordinate Pair */
    public final int chunkXPos;

    /** The Z position of this Chunk Coordinate Pair */
    public final int chunkZPos;

    public ChunkCoordIntPair(int par1, int par2)
    {
        chunkXPos = par1;
        chunkZPos = par2;
    }

    /**
     * converts a chunk coordinate pair to an integer (suitable for hashing)
     */
    public static long chunkXZ2Int(int par0, int par1)
    {
        long l = par0;
        long l1 = par1;
        return l & 0xffffffffL | (l1 & 0xffffffffL) << 32;
    }

    public int hashCode()
    {
        long l = chunkXZ2Int(chunkXPos, chunkZPos);
        int i = (int)l;
        int j = (int)(l >> 32);
        return i ^ j;
    }

    public boolean equals(Object par1Obj)
    {
        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)par1Obj;
        return chunkcoordintpair.chunkXPos == chunkXPos && chunkcoordintpair.chunkZPos == chunkZPos;
    }

    public int getCenterXPos()
    {
        return (chunkXPos << 4) + 8;
    }

    public int getCenterZPos()
    {
        return (chunkZPos << 4) + 8;
    }

    public ChunkPosition getChunkPosition(int par1)
    {
        return new ChunkPosition(getCenterXPos(), par1, getCenterZPos());
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(chunkXPos).append(", ").append(chunkZPos).append("]").toString();
    }
}
