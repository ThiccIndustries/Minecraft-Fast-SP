package net.minecraft.src;

public class PathEntity
{
    private final PathPoint points[];

    /** PathEntity Array Index the Entity is currently targeting */
    private int currentPathIndex;

    /** The total length of the path */
    private int pathLength;

    public PathEntity(PathPoint par1ArrayOfPathPoint[])
    {
        points = par1ArrayOfPathPoint;
        pathLength = par1ArrayOfPathPoint.length;
    }

    /**
     * Directs this path to the next point in its array
     */
    public void incrementPathIndex()
    {
        currentPathIndex++;
    }

    /**
     * Returns true if this path has reached the end
     */
    public boolean isFinished()
    {
        return currentPathIndex >= pathLength;
    }

    /**
     * returns the last PathPoint of the Array
     */
    public PathPoint getFinalPathPoint()
    {
        if (pathLength > 0)
        {
            return points[pathLength - 1];
        }
        else
        {
            return null;
        }
    }

    /**
     * return the PathPoint located at the specified PathIndex, usually the current one
     */
    public PathPoint getPathPointFromIndex(int par1)
    {
        return points[par1];
    }

    public int getCurrentPathLength()
    {
        return pathLength;
    }

    public void setCurrentPathLength(int par1)
    {
        pathLength = par1;
    }

    public int getCurrentPathIndex()
    {
        return currentPathIndex;
    }

    public void setCurrentPathIndex(int par1)
    {
        currentPathIndex = par1;
    }

    /**
     * Gets the vector of the PathPoint associated with the given index.
     */
    public Vec3D getVectorFromIndex(Entity par1Entity, int par2)
    {
        double d = (double)points[par2].xCoord + (double)(int)(par1Entity.width + 1.0F) * 0.5D;
        double d1 = points[par2].yCoord;
        double d2 = (double)points[par2].zCoord + (double)(int)(par1Entity.width + 1.0F) * 0.5D;
        return Vec3D.createVector(d, d1, d2);
    }

    /**
     * returns the current PathEntity target node as Vec3D
     */
    public Vec3D getCurrentNodeVec3d(Entity par1Entity)
    {
        return getVectorFromIndex(par1Entity, currentPathIndex);
    }

    public boolean func_48647_a(PathEntity par1PathEntity)
    {
        if (par1PathEntity == null)
        {
            return false;
        }

        if (par1PathEntity.points.length != points.length)
        {
            return false;
        }

        for (int i = 0; i < points.length; i++)
        {
            if (points[i].xCoord != par1PathEntity.points[i].xCoord || points[i].yCoord != par1PathEntity.points[i].yCoord || points[i].zCoord != par1PathEntity.points[i].zCoord)
            {
                return false;
            }
        }

        return true;
    }

    public boolean func_48639_a(Vec3D par1Vec3D)
    {
        PathPoint pathpoint = getFinalPathPoint();

        if (pathpoint == null)
        {
            return false;
        }
        else
        {
            return pathpoint.xCoord == (int)par1Vec3D.xCoord && pathpoint.zCoord == (int)par1Vec3D.zCoord;
        }
    }
}
