package net.minecraft.src;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLiving theEntity;
    EntityLiving field_48362_c;
    int field_48360_d;

    public EntityAIOcelotAttack(EntityLiving par1EntityLiving)
    {
        field_48360_d = 0;
        theEntity = par1EntityLiving;
        theWorld = par1EntityLiving.worldObj;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = theEntity.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_48362_c = entityliving;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!field_48362_c.isEntityAlive())
        {
            return false;
        }

        if (theEntity.getDistanceSqToEntity(field_48362_c) > 225D)
        {
            return false;
        }
        else
        {
            return !theEntity.getNavigator().noPath() || shouldExecute();
        }
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        field_48362_c = null;
        theEntity.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theEntity.getLookHelper().setLookPositionWithEntity(field_48362_c, 30F, 30F);
        double d = theEntity.width * 2.0F * (theEntity.width * 2.0F);
        double d1 = theEntity.getDistanceSq(field_48362_c.posX, field_48362_c.boundingBox.minY, field_48362_c.posZ);
        float f = 0.23F;

        if (d1 > d && d1 < 16D)
        {
            f = 0.4F;
        }
        else if (d1 < 225D)
        {
            f = 0.18F;
        }

        theEntity.getNavigator().func_48667_a(field_48362_c, f);
        field_48360_d = Math.max(field_48360_d - 1, 0);

        if (d1 > d)
        {
            return;
        }

        if (field_48360_d > 0)
        {
            return;
        }
        else
        {
            field_48360_d = 20;
            theEntity.attackEntityAsMob(field_48362_c);
            return;
        }
    }
}
