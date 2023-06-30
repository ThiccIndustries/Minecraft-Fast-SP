package net.minecraft.src;

public class ChunkPosition
{
    /** The x coordinate of this ChunkPosition */
    public final int x;

    /** The y coordinate of this ChunkPosition */
    public final int y;

    /** The z coordinate of this ChunkPosition */
    public final int z;

    public ChunkPosition(int par1, int par2, int par3)
    {
        x = par1;
        y = par2;
        z = par3;
    }

    public ChunkPosition(Vec3D par1Vec3D)
    {
        this(MathHelper.floor_double(par1Vec3D.xCoord), MathHelper.floor_double(par1Vec3D.yCoord), MathHelper.floor_double(par1Vec3D.zCoord));
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj instanceof ChunkPosition)
        {
            ChunkPosition chunkposition = (ChunkPosition)par1Obj;
            return chunkposition.x == x && chunkposition.y == y && chunkposition.z == z;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return x * 0x88f9fa + y * 0xef88b + z;
    }
}
