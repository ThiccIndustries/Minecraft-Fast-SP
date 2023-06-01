package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator
{
    /** The entity (usually the player) that the camera is inside. */
    private EntityLiving baseEntity;

    public RenderSorter(EntityLiving par1EntityLiving)
    {
        baseEntity = par1EntityLiving;
    }

    public int doCompare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
    {
        if (par1WorldRenderer.isInFrustum && !par2WorldRenderer.isInFrustum)
        {
            return 1;
        }

        if (par2WorldRenderer.isInFrustum && !par1WorldRenderer.isInFrustum)
        {
            return -1;
        }

        double d = par1WorldRenderer.distanceToEntitySquared(baseEntity);
        double d1 = par2WorldRenderer.distanceToEntitySquared(baseEntity);

        if (d < d1)
        {
            return 1;
        }

        if (d > d1)
        {
            return -1;
        }
        else
        {
            return par1WorldRenderer.chunkIndex >= par2WorldRenderer.chunkIndex ? -1 : 1;
        }
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return doCompare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}
