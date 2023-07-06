package net.minecraft.src;

import java.util.Random;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    EntityLiving attacker;
    EntityLiving entityTarget;
    int field_46091_d;
    float field_48266_e;
    boolean field_48264_f;
    PathEntity field_48265_g;
    Class classTarget;
    private int field_48269_i;

    public EntityAIAttackOnCollide(EntityLiving par1EntityLiving, Class par2Class, float par3, boolean par4)
    {
        this(par1EntityLiving, par3, par4);
        classTarget = par2Class;
    }

    public EntityAIAttackOnCollide(EntityLiving par1EntityLiving, float par2, boolean par3)
    {
        field_46091_d = 0;
        attacker = par1EntityLiving;
        worldObj = par1EntityLiving.worldObj;
        field_48266_e = par2;
        field_48264_f = par3;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = attacker.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }

        if (classTarget != null && !classTarget.isAssignableFrom(entityliving.getClass()))
        {
            return false;
        }
        else
        {
            entityTarget = entityliving;
            field_48265_g = attacker.getNavigator().func_48679_a(entityTarget);
            return field_48265_g != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        EntityLiving entityliving = attacker.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }

        if (!entityTarget.isEntityAlive())
        {
            return false;
        }

        if (!field_48264_f)
        {
            return !attacker.getNavigator().noPath();
        }

        return attacker.isWithinHomeDistance(MathHelper.floor_double(entityTarget.posX), MathHelper.floor_double(entityTarget.posY), MathHelper.floor_double(entityTarget.posZ));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        attacker.getNavigator().setPath(field_48265_g, field_48266_e);
        field_48269_i = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        entityTarget = null;
        attacker.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30F, 30F);

        if ((field_48264_f || attacker.func_48090_aM().canSee(entityTarget)) && --field_48269_i <= 0)
        {
            field_48269_i = 4 + attacker.getRNG().nextInt(7);
            attacker.getNavigator().func_48667_a(entityTarget, field_48266_e);
        }

        field_46091_d = Math.max(field_46091_d - 1, 0);
        double d = attacker.width * 2.0F * (attacker.width * 2.0F);

        if (attacker.getDistanceSq(entityTarget.posX, entityTarget.boundingBox.minY, entityTarget.posZ) > d)
        {
            return;
        }

        if (field_46091_d > 0)
        {
            return;
        }
        else
        {
            field_46091_d = 20;
            attacker.attackEntityAsMob(entityTarget);
            return;
        }
    }
}
