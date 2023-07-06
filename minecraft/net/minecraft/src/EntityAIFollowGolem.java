package net.minecraft.src;

import java.util.*;

public class EntityAIFollowGolem extends EntityAIBase
{
    private EntityVillager theVillager;
    private EntityIronGolem theGolem;
    private int field_48402_c;
    private boolean field_48400_d;

    public EntityAIFollowGolem(EntityVillager par1EntityVillager)
    {
        field_48400_d = false;
        theVillager = par1EntityVillager;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theVillager.getGrowingAge() >= 0)
        {
            return false;
        }

        if (!theVillager.worldObj.isDaytime())
        {
            return false;
        }

        List list = theVillager.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityIronGolem.class, theVillager.boundingBox.expand(6D, 2D, 6D));

        if (list.size() == 0)
        {
            return false;
        }

        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Entity entity = (Entity)iterator.next();
            EntityIronGolem entityirongolem = (EntityIronGolem)entity;

            if (entityirongolem.func_48117_D_() <= 0)
            {
                continue;
            }

            theGolem = entityirongolem;
            break;
        }
        while (true);

        return theGolem != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return theGolem.func_48117_D_() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48402_c = theVillager.getRNG().nextInt(320);
        field_48400_d = false;
        theGolem.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theGolem = null;
        theVillager.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30F, 30F);

        if (theGolem.func_48117_D_() == field_48402_c)
        {
            theVillager.getNavigator().func_48667_a(theGolem, 0.15F);
            field_48400_d = true;
        }

        if (field_48400_d && theVillager.getDistanceSqToEntity(theGolem) < 4D)
        {
            theGolem.func_48116_a(false);
            theVillager.getNavigator().clearPathEntity();
        }
    }
}
