package net.minecraft.src;

import java.util.Random;

public class EntityAILookAtVillager extends EntityAIBase
{
    private EntityIronGolem theGolem;
    private EntityVillager theVillager;
    private int field_48405_c;

    public EntityAILookAtVillager(EntityIronGolem par1EntityIronGolem)
    {
        theGolem = par1EntityIronGolem;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theGolem.worldObj.isDaytime())
        {
            return false;
        }

        if (theGolem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            theVillager = (EntityVillager)theGolem.worldObj.findNearestEntityWithinAABB(net.minecraft.src.EntityVillager.class, theGolem.boundingBox.expand(6D, 2D, 6D), theGolem);
            return theVillager != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48405_c > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48405_c = 400;
        theGolem.func_48116_a(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theGolem.func_48116_a(false);
        theVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theGolem.getLookHelper().setLookPositionWithEntity(theVillager, 30F, 30F);
        field_48405_c--;
    }
}
