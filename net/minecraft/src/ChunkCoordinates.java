package net.minecraft.src;

public class ChunkCoordinates implements Comparable
{
    public int posX;

    /** the y coordinate */
    public int posY;

    /** the z coordinate */
    public int posZ;

    public ChunkCoordinates()
    {
    }

    public ChunkCoordinates(int par1, int par2, int par3)
    {
        posX = par1;
        posY = par2;
        posZ = par3;
    }

    public ChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
    {
        posX = par1ChunkCoordinates.posX;
        posY = par1ChunkCoordinates.posY;
        posZ = par1ChunkCoordinates.posZ;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof ChunkCoordinates))
        {
            return false;
        }
        else
        {
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)par1Obj;
            return posX == chunkcoordinates.posX && posY == chunkcoordinates.posY && posZ == chunkcoordinates.posZ;
        }
    }

    public int hashCode()
    {
        return posX + posZ << 8 + posY << 16;
    }

    /**
     * Compare the coordinate with another coordinate
     */
    public int compareChunkCoordinate(ChunkCoordinates par1ChunkCoordinates)
    {
        if (posY == par1ChunkCoordinates.posY)
        {
            if (posZ == par1ChunkCoordinates.posZ)
            {
                return posX - par1ChunkCoordinates.posX;
            }
            else
            {
                return posZ - par1ChunkCoordinates.posZ;
            }
        }
        else
        {
            return posY - par1ChunkCoordinates.posY;
        }
    }

    public void set(int par1, int par2, int par3)
    {
        posX = par1;
        posY = par2;
        posZ = par3;
    }

    /**
     * Returns the euclidean distance of the chunk coordinate to the x, y, z parameters passed.
     */
    public double getEuclideanDistanceTo(int par1, int par2, int par3)
    {
        int i = posX - par1;
        int j = posY - par2;
        int k = posZ - par3;
        return Math.sqrt(i * i + j * j + k * k);
    }

    /**
     * Returns the squared distance between this coordinates and the coordinates given as argument.
     */
    public float getDistanceSquared(int par1, int par2, int par3)
    {
        int i = posX - par1;
        int j = posY - par2;
        int k = posZ - par3;
        return (float)(i * i + j * j + k * k);
    }

    public int compareTo(Object par1Obj)
    {
        return compareChunkCoordinate((ChunkCoordinates)par1Obj);
    }
}
