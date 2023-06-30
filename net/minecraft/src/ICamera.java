package net.minecraft.src;

public interface ICamera
{
    /**
     * Returns true if the bounding box is inside all 6 clipping planes, otherwise returns false.
     */
    public abstract boolean isBoundingBoxInFrustum(AxisAlignedBB axisalignedbb);

    public abstract boolean isBoundingBoxInFrustumFully(AxisAlignedBB axisalignedbb);

    public abstract void setPosition(double d, double d1, double d2);
}
