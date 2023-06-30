package net.minecraft.src;

public class Path
{
    private PathPoint pathPoints[];

    /** The number of points in this path */
    private int count;

    public Path()
    {
        pathPoints = new PathPoint[1024];
        count = 0;
    }

    /**
     * Adds a point to the path
     */
    public PathPoint addPoint(PathPoint par1PathPoint)
    {
        if (par1PathPoint.index >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }

        if (count == pathPoints.length)
        {
            PathPoint apathpoint[] = new PathPoint[count << 1];
            System.arraycopy(pathPoints, 0, apathpoint, 0, count);
            pathPoints = apathpoint;
        }

        pathPoints[count] = par1PathPoint;
        par1PathPoint.index = count;
        sortBack(count++);
        return par1PathPoint;
    }

    /**
     * Clears the path
     */
    public void clearPath()
    {
        count = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public PathPoint dequeue()
    {
        PathPoint pathpoint = pathPoints[0];
        pathPoints[0] = pathPoints[--count];
        pathPoints[count] = null;

        if (count > 0)
        {
            sortForward(0);
        }

        pathpoint.index = -1;
        return pathpoint;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void changeDistance(PathPoint par1PathPoint, float par2)
    {
        float f = par1PathPoint.distanceToTarget;
        par1PathPoint.distanceToTarget = par2;

        if (par2 < f)
        {
            sortBack(par1PathPoint.index);
        }
        else
        {
            sortForward(par1PathPoint.index);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void sortBack(int par1)
    {
        PathPoint pathpoint = pathPoints[par1];
        float f = pathpoint.distanceToTarget;

        do
        {
            if (par1 <= 0)
            {
                break;
            }

            int i = par1 - 1 >> 1;
            PathPoint pathpoint1 = pathPoints[i];

            if (f >= pathpoint1.distanceToTarget)
            {
                break;
            }

            pathPoints[par1] = pathpoint1;
            pathpoint1.index = par1;
            par1 = i;
        }
        while (true);

        pathPoints[par1] = pathpoint;
        pathpoint.index = par1;
    }

    /**
     * Sorts a point to the right
     */
    private void sortForward(int par1)
    {
        PathPoint pathpoint = pathPoints[par1];
        float f = pathpoint.distanceToTarget;

        do
        {
            int i = 1 + (par1 << 1);
            int j = i + 1;

            if (i >= count)
            {
                break;
            }

            PathPoint pathpoint1 = pathPoints[i];
            float f1 = pathpoint1.distanceToTarget;
            PathPoint pathpoint2;
            float f2;

            if (j >= count)
            {
                pathpoint2 = null;
                f2 = (1.0F / 0.0F);
            }
            else
            {
                pathpoint2 = pathPoints[j];
                f2 = pathpoint2.distanceToTarget;
            }

            if (f1 < f2)
            {
                if (f1 >= f)
                {
                    break;
                }

                pathPoints[par1] = pathpoint1;
                pathpoint1.index = par1;
                par1 = i;
                continue;
            }

            if (f2 >= f)
            {
                break;
            }

            pathPoints[par1] = pathpoint2;
            pathpoint2.index = par1;
            par1 = j;
        }
        while (true);

        pathPoints[par1] = pathpoint;
        pathpoint.index = par1;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty()
    {
        return count == 0;
    }
}
