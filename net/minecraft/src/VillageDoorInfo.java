package net.minecraft.src;

public class VillageDoorInfo
{
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int insideDirectionX;
    public final int insideDirectionZ;
    public int lastActivityTimestamp;
    public boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;

    public VillageDoorInfo(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        isDetachedFromVillageFlag = false;
        doorOpeningRestrictionCounter = 0;
        posX = par1;
        posY = par2;
        posZ = par3;
        insideDirectionX = par4;
        insideDirectionZ = par5;
        lastActivityTimestamp = par6;
    }

    /**
     * Returns the squared distance between this door and the given coordinate.
     */
    public int getDistanceSquared(int par1, int par2, int par3)
    {
        int i = par1 - posX;
        int j = par2 - posY;
        int k = par3 - posZ;
        return i * i + j * j + k * k;
    }

    /**
     * Get the square of the distance from a location 2 blocks away from the door considered 'inside' and the given
     * arguments
     */
    public int getInsideDistanceSquare(int par1, int par2, int par3)
    {
        int i = par1 - posX - insideDirectionX;
        int j = par2 - posY;
        int k = par3 - posZ - insideDirectionZ;
        return i * i + j * j + k * k;
    }

    public int getInsidePosX()
    {
        return posX + insideDirectionX;
    }

    public int getInsidePosY()
    {
        return posY;
    }

    public int getInsidePosZ()
    {
        return posZ + insideDirectionZ;
    }

    public boolean isInside(int par1, int par2)
    {
        int i = par1 - posX;
        int j = par2 - posZ;
        return i * insideDirectionX + j * insideDirectionZ >= 0;
    }

    public void resetDoorOpeningRestrictionCounter()
    {
        doorOpeningRestrictionCounter = 0;
    }

    public void incrementDoorOpeningRestrictionCounter()
    {
        doorOpeningRestrictionCounter++;
    }

    public int getDoorOpeningRestrictionCounter()
    {
        return doorOpeningRestrictionCounter;
    }
}
