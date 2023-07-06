package net.minecraft.src;

import java.util.ArrayList;

public class EntitySenses
{
    EntityLiving entityObj;
    ArrayList canSeeCachePositive;
    ArrayList canSeeCacheNegative;

    public EntitySenses(EntityLiving par1EntityLiving)
    {
        canSeeCachePositive = new ArrayList();
        canSeeCacheNegative = new ArrayList();
        entityObj = par1EntityLiving;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    public void clearSensingCache()
    {
        canSeeCachePositive.clear();
        canSeeCacheNegative.clear();
    }

    /**
     * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
     */
    public boolean canSee(Entity par1Entity)
    {
        if (canSeeCachePositive.contains(par1Entity))
        {
            return true;
        }

        if (canSeeCacheNegative.contains(par1Entity))
        {
            return false;
        }

        Profiler.startSection("canSee");
        boolean flag = entityObj.canEntityBeSeen(par1Entity);
        Profiler.endSection();

        if (flag)
        {
            canSeeCachePositive.add(par1Entity);
        }
        else
        {
            canSeeCacheNegative.add(par1Entity);
        }

        return flag;
    }
}
