package net.minecraft.src;

public class StructureBoundingBox
{
    /** The first x coordinate of a bounding box. */
    public int minX;

    /** The first y coordinate of a bounding box. */
    public int minY;

    /** The first z coordinate of a bounding box. */
    public int minZ;

    /** The second x coordinate of a bounding box. */
    public int maxX;

    /** The second y coordinate of a bounding box. */
    public int maxY;

    /** The second z coordinate of a bounding box. */
    public int maxZ;

    public StructureBoundingBox()
    {
    }

    /**
     * 'returns a new StructureBoundingBox with MAX values'
     */
    public static StructureBoundingBox getNewBoundingBox()
    {
        return new StructureBoundingBox(0x7fffffff, 0x7fffffff, 0x7fffffff, 0x80000000, 0x80000000, 0x80000000);
    }

    /**
     * 'used to project a possible new component Bounding Box - to check if it would cut anything already spawned'
     */
    public static StructureBoundingBox getComponentToAddBoundingBox(int par0, int par1, int par2, int par3, int par4, int par5, int par6, int par7, int par8, int par9)
    {
        switch (par9)
        {
            default:
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, ((par0 + par6) - 1) + par3, ((par1 + par7) - 1) + par4, ((par2 + par8) - 1) + par5);

            case 2:
                return new StructureBoundingBox(par0 + par3, par1 + par4, (par2 - par8) + 1 + par5, ((par0 + par6) - 1) + par3, ((par1 + par7) - 1) + par4, par2 + par5);

            case 0:
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, ((par0 + par6) - 1) + par3, ((par1 + par7) - 1) + par4, ((par2 + par8) - 1) + par5);

            case 1:
                return new StructureBoundingBox((par0 - par8) + 1 + par5, par1 + par4, par2 + par3, par0 + par5, ((par1 + par7) - 1) + par4, ((par2 + par6) - 1) + par3);

            case 3:
                return new StructureBoundingBox(par0 + par5, par1 + par4, par2 + par3, ((par0 + par8) - 1) + par5, ((par1 + par7) - 1) + par4, ((par2 + par6) - 1) + par3);
        }
    }

    public StructureBoundingBox(StructureBoundingBox par1StructureBoundingBox)
    {
        minX = par1StructureBoundingBox.minX;
        minY = par1StructureBoundingBox.minY;
        minZ = par1StructureBoundingBox.minZ;
        maxX = par1StructureBoundingBox.maxX;
        maxY = par1StructureBoundingBox.maxY;
        maxZ = par1StructureBoundingBox.maxZ;
    }

    public StructureBoundingBox(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        minX = par1;
        minY = par2;
        minZ = par3;
        maxX = par4;
        maxY = par5;
        maxZ = par6;
    }

    public StructureBoundingBox(int par1, int par2, int par3, int par4)
    {
        minX = par1;
        minZ = par2;
        maxX = par3;
        maxZ = par4;
        minY = 1;
        maxY = 512;
    }

    /**
     * Returns whether the given bounding box intersects with this one. Args: structureboundingbox
     */
    public boolean intersectsWith(StructureBoundingBox par1StructureBoundingBox)
    {
        return maxX >= par1StructureBoundingBox.minX && minX <= par1StructureBoundingBox.maxX && maxZ >= par1StructureBoundingBox.minZ && minZ <= par1StructureBoundingBox.maxZ && maxY >= par1StructureBoundingBox.minY && minY <= par1StructureBoundingBox.maxY;
    }

    /**
     * Discover if a coordinate is inside the bounding box area.
     */
    public boolean intersectsWith(int par1, int par2, int par3, int par4)
    {
        return maxX >= par1 && minX <= par3 && maxZ >= par2 && minZ <= par4;
    }

    /**
     * Expands a bounding box's dimensions to include the supplied bounding box.
     */
    public void expandTo(StructureBoundingBox par1StructureBoundingBox)
    {
        minX = Math.min(minX, par1StructureBoundingBox.minX);
        minY = Math.min(minY, par1StructureBoundingBox.minY);
        minZ = Math.min(minZ, par1StructureBoundingBox.minZ);
        maxX = Math.max(maxX, par1StructureBoundingBox.maxX);
        maxY = Math.max(maxY, par1StructureBoundingBox.maxY);
        maxZ = Math.max(maxZ, par1StructureBoundingBox.maxZ);
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public void offset(int par1, int par2, int par3)
    {
        minX += par1;
        minY += par2;
        minZ += par3;
        maxX += par1;
        maxY += par2;
        maxZ += par3;
    }

    /**
     * Returns true if block is inside bounding box
     */
    public boolean isVecInside(int par1, int par2, int par3)
    {
        return par1 >= minX && par1 <= maxX && par3 >= minZ && par3 <= maxZ && par2 >= minY && par2 <= maxY;
    }

    /**
     * Returns width of a bounding box
     */
    public int getXSize()
    {
        return (maxX - minX) + 1;
    }

    /**
     * Returns height of a bounding box
     */
    public int getYSize()
    {
        return (maxY - minY) + 1;
    }

    /**
     * Returns length of a bounding box
     */
    public int getZSize()
    {
        return (maxZ - minZ) + 1;
    }

    public int getCenterX()
    {
        return minX + ((maxX - minX) + 1) / 2;
    }

    public int getCenterY()
    {
        return minY + ((maxY - minY) + 1) / 2;
    }

    public int getCenterZ()
    {
        return minZ + ((maxZ - minZ) + 1) / 2;
    }

    public String toString()
    {
        return (new StringBuilder()).append("(").append(minX).append(", ").append(minY).append(", ").append(minZ).append("; ").append(maxX).append(", ").append(maxY).append(", ").append(maxZ).append(")").toString();
    }
}
