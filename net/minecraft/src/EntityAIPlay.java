package net.minecraft.src;

import java.util.*;

public class EntityAIPlay extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityLiving targetVillager;
    private float field_48358_c;
    private int field_48356_d;

    public EntityAIPlay(EntityVillager par1EntityVillager, float par2)
    {
        villagerObj = par1EntityVillager;
        field_48358_c = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (villagerObj.getGrowingAge() >= 0)
        {
            return false;
        }

        if (villagerObj.getRNG().nextInt(400) != 0)
        {
            return false;
        }

        List list = villagerObj.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityVillager.class, villagerObj.boundingBox.expand(6D, 3D, 6D));
        double d = Double.MAX_VALUE;
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Entity entity = (Entity)iterator.next();

            if (entity != villagerObj)
            {
                EntityVillager entityvillager = (EntityVillager)entity;

                if (!entityvillager.getIsPlayingFlag() && entityvillager.getGrowingAge() < 0)
                {
                    double d1 = entityvillager.getDistanceSqToEntity(villagerObj);

                    if (d1 <= d)
                    {
                        d = d1;
                        targetVillager = entityvillager;
                    }
                }
            }
        }
        while (true);

        if (targetVillager == null)
        {
            Vec3D vec3d = RandomPositionGenerator.func_48622_a(villagerObj, 16, 3);

            if (vec3d == null)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48356_d > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (targetVillager != null)
        {
            villagerObj.setIsPlayingFlag(true);
        }

        field_48356_d = 1000;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villagerObj.setIsPlayingFlag(false);
        targetVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_48356_d--;

        if (targetVillager != null)
        {
            if (villagerObj.getDistanceSqToEntity(targetVillager) > 4D)
            {
                villagerObj.getNavigator().func_48667_a(targetVillager, field_48358_c);
            }
        }
        else if (villagerObj.getNavigator().noPath())
        {
            Vec3D vec3d = RandomPositionGenerator.func_48622_a(villagerObj, 16, 3);

            if (vec3d == null)
            {
                return;
            }

            villagerObj.getNavigator().func_48666_a(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, field_48358_c);
        }
    }
}
