package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AxisAlignedBB
{
    /** List of bounding boxes (not all necessarily being actively used) */
    private static List boundingBoxes = new ArrayList();

    /** Tracks how many bounding boxes are being used */
    private static int numBoundingBoxesInUse = 0;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public static AxisAlignedBB getBoundingBox(double par0, double par2, double par4, double par6, double par8, double par10)
    {
        return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
    }

    public static void clearBoundingBoxes()
    {
        boundingBoxes.clear();
        numBoundingBoxesInUse = 0;
    }

    /**
     * Sets the number of bounding boxes in use from the pool to 0 so they will be reused
     */
    public static void clearBoundingBoxPool()
    {
        numBoundingBoxesInUse = 0;
    }

    /**
     * Returns a bounding box with the specified bounds from the pool.  Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public static AxisAlignedBB getBoundingBoxFromPool(double par0, double par2, double par4, double par6, double par8, double par10)
    {
        if (numBoundingBoxesInUse >= boundingBoxes.size())
        {
            boundingBoxes.add(getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        }

        return ((AxisAlignedBB)boundingBoxes.get(numBoundingBoxesInUse++)).setBounds(par0, par2, par4, par6, par8, par10);
    }

    private AxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        minX = par1;
        minY = par3;
        minZ = par5;
        maxX = par7;
        maxY = par9;
        maxZ = par11;
    }

    /**
     * Sets the bounds of the bounding box. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public AxisAlignedBB setBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        minX = par1;
        minY = par3;
        minZ = par5;
        maxX = par7;
        maxY = par9;
        maxZ = par11;
        return this;
    }

    /**
     * Adds the coordinates to the bounding box extending it if the point lies outside the current ranges. Args: x, y, z
     */
    public AxisAlignedBB addCoord(double par1, double par3, double par5)
    {
        double d = minX;
        double d1 = minY;
        double d2 = minZ;
        double d3 = maxX;
        double d4 = maxY;
        double d5 = maxZ;

        if (par1 < 0.0D)
        {
            d += par1;
        }

        if (par1 > 0.0D)
        {
            d3 += par1;
        }

        if (par3 < 0.0D)
        {
            d1 += par3;
        }

        if (par3 > 0.0D)
        {
            d4 += par3;
        }

        if (par5 < 0.0D)
        {
            d2 += par5;
        }

        if (par5 > 0.0D)
        {
            d5 += par5;
        }

        return getBoundingBoxFromPool(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB expand(double par1, double par3, double par5)
    {
        double d = minX - par1;
        double d1 = minY - par3;
        double d2 = minZ - par5;
        double d3 = maxX + par1;
        double d4 = maxY + par3;
        double d5 = maxZ + par5;
        return getBoundingBoxFromPool(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB getOffsetBoundingBox(double par1, double par3, double par5)
    {
        return getBoundingBoxFromPool(minX + par1, minY + par3, minZ + par5, maxX + par1, maxY + par3, maxZ + par5);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxZ <= minZ || par1AxisAlignedBB.minZ >= maxZ)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxX <= minX)
        {
            double d = minX - par1AxisAlignedBB.maxX;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minX >= maxX)
        {
            double d1 = maxX - par1AxisAlignedBB.minX;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxZ <= minZ || par1AxisAlignedBB.minZ >= maxZ)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxY <= minY)
        {
            double d = minY - par1AxisAlignedBB.maxY;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minY >= maxY)
        {
            double d1 = maxY - par1AxisAlignedBB.minY;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxZ <= minZ)
        {
            double d = minZ - par1AxisAlignedBB.maxZ;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minZ >= maxZ)
        {
            double d1 = maxZ - par1AxisAlignedBB.minZ;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * Returns whether the given bounding box intersects with this one. Args: axisAlignedBB
     */
    public boolean intersectsWith(AxisAlignedBB par1AxisAlignedBB)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return false;
        }

        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return false;
        }

        return par1AxisAlignedBB.maxZ > minZ && par1AxisAlignedBB.minZ < maxZ;
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public AxisAlignedBB offset(double par1, double par3, double par5)
    {
        minX += par1;
        minY += par3;
        minZ += par5;
        maxX += par1;
        maxY += par3;
        maxZ += par5;
        return this;
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean isVecInside(Vec3D par1Vec3D)
    {
        if (par1Vec3D.xCoord <= minX || par1Vec3D.xCoord >= maxX)
        {
            return false;
        }

        if (par1Vec3D.yCoord <= minY || par1Vec3D.yCoord >= maxY)
        {
            return false;
        }

        return par1Vec3D.zCoord > minZ && par1Vec3D.zCoord < maxZ;
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double getAverageEdgeLength()
    {
        double d = maxX - minX;
        double d1 = maxY - minY;
        double d2 = maxZ - minZ;
        return (d + d1 + d2) / 3D;
    }

    /**
     * Returns a bounding box that is inset by the specified amounts
     */
    public AxisAlignedBB contract(double par1, double par3, double par5)
    {
        double d = minX + par1;
        double d1 = minY + par3;
        double d2 = minZ + par5;
        double d3 = maxX - par1;
        double d4 = maxY - par3;
        double d5 = maxZ - par5;
        return getBoundingBoxFromPool(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a copy of the bounding box.
     */
    public AxisAlignedBB copy()
    {
        return getBoundingBoxFromPool(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public MovingObjectPosition calculateIntercept(Vec3D par1Vec3D, Vec3D par2Vec3D)
    {
        Vec3D vec3d = par1Vec3D.getIntermediateWithXValue(par2Vec3D, minX);
        Vec3D vec3d1 = par1Vec3D.getIntermediateWithXValue(par2Vec3D, maxX);
        Vec3D vec3d2 = par1Vec3D.getIntermediateWithYValue(par2Vec3D, minY);
        Vec3D vec3d3 = par1Vec3D.getIntermediateWithYValue(par2Vec3D, maxY);
        Vec3D vec3d4 = par1Vec3D.getIntermediateWithZValue(par2Vec3D, minZ);
        Vec3D vec3d5 = par1Vec3D.getIntermediateWithZValue(par2Vec3D, maxZ);

        if (!isVecInYZ(vec3d))
        {
            vec3d = null;
        }

        if (!isVecInYZ(vec3d1))
        {
            vec3d1 = null;
        }

        if (!isVecInXZ(vec3d2))
        {
            vec3d2 = null;
        }

        if (!isVecInXZ(vec3d3))
        {
            vec3d3 = null;
        }

        if (!isVecInXY(vec3d4))
        {
            vec3d4 = null;
        }

        if (!isVecInXY(vec3d5))
        {
            vec3d5 = null;
        }

        Vec3D vec3d6 = null;

        if (vec3d != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d;
        }

        if (vec3d1 != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d1) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d1;
        }

        if (vec3d2 != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d2) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d2;
        }

        if (vec3d3 != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d3) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d3;
        }

        if (vec3d4 != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d4) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d4;
        }

        if (vec3d5 != null && (vec3d6 == null || par1Vec3D.squareDistanceTo(vec3d5) < par1Vec3D.squareDistanceTo(vec3d6)))
        {
            vec3d6 = vec3d5;
        }

        if (vec3d6 == null)
        {
            return null;
        }

        byte byte0 = -1;

        if (vec3d6 == vec3d)
        {
            byte0 = 4;
        }

        if (vec3d6 == vec3d1)
        {
            byte0 = 5;
        }

        if (vec3d6 == vec3d2)
        {
            byte0 = 0;
        }

        if (vec3d6 == vec3d3)
        {
            byte0 = 1;
        }

        if (vec3d6 == vec3d4)
        {
            byte0 = 2;
        }

        if (vec3d6 == vec3d5)
        {
            byte0 = 3;
        }

        return new MovingObjectPosition(0, 0, 0, byte0, vec3d6);
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInYZ(Vec3D par1Vec3D)
    {
        if (par1Vec3D == null)
        {
            return false;
        }
        else
        {
            return par1Vec3D.yCoord >= minY && par1Vec3D.yCoord <= maxY && par1Vec3D.zCoord >= minZ && par1Vec3D.zCoord <= maxZ;
        }
    }

    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXZ(Vec3D par1Vec3D)
    {
        if (par1Vec3D == null)
        {
            return false;
        }
        else
        {
            return par1Vec3D.xCoord >= minX && par1Vec3D.xCoord <= maxX && par1Vec3D.zCoord >= minZ && par1Vec3D.zCoord <= maxZ;
        }
    }

    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXY(Vec3D par1Vec3D)
    {
        if (par1Vec3D == null)
        {
            return false;
        }
        else
        {
            return par1Vec3D.xCoord >= minX && par1Vec3D.xCoord <= maxX && par1Vec3D.yCoord >= minY && par1Vec3D.yCoord <= maxY;
        }
    }

    /**
     * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
     */
    public void setBB(AxisAlignedBB par1AxisAlignedBB)
    {
        minX = par1AxisAlignedBB.minX;
        minY = par1AxisAlignedBB.minY;
        minZ = par1AxisAlignedBB.minZ;
        maxX = par1AxisAlignedBB.maxX;
        maxY = par1AxisAlignedBB.maxY;
        maxZ = par1AxisAlignedBB.maxZ;
    }

    public String toString()
    {
        return (new StringBuilder()).append("box[").append(minX).append(", ").append(minY).append(", ").append(minZ).append(" -> ").append(maxX).append(", ").append(maxY).append(", ").append(maxZ).append("]").toString();
    }
}
