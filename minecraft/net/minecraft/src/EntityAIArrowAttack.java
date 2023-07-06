package net.minecraft.src;

import java.util.Random;

public class EntityAIArrowAttack extends EntityAIBase
{
    World worldObj;

    /** The entity the AI instance has been applied to */
    EntityLiving entityHost;
    EntityLiving attackTarget;

    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    int rangedAttackTime;
    float field_48370_e;
    int field_48367_f;

    /**
     * The ID of this ranged attack AI. This chooses which entity is to be used as a ranged attack.
     */
    int rangedAttackID;

    /**
     * The maximum time the AI has to wait before peforming another ranged attack.
     */
    int maxRangedAttackTime;

    public EntityAIArrowAttack(EntityLiving par1EntityLiving, float par2, int par3, int par4)
    {
        rangedAttackTime = 0;
        field_48367_f = 0;
        entityHost = par1EntityLiving;
        worldObj = par1EntityLiving.worldObj;
        field_48370_e = par2;
        rangedAttackID = par3;
        maxRangedAttackTime = par4;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = entityHost.getAttackTarget();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            attackTarget = entityliving;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return shouldExecute() || !entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        attackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        double d = 100D;
        double d1 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
        boolean flag = entityHost.func_48090_aM().canSee(attackTarget);

        if (flag)
        {
            field_48367_f++;
        }
        else
        {
            field_48367_f = 0;
        }

        if (d1 > d || field_48367_f < 20)
        {
            entityHost.getNavigator().func_48667_a(attackTarget, field_48370_e);
        }
        else
        {
            entityHost.getNavigator().clearPathEntity();
        }

        entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30F, 30F);
        rangedAttackTime = Math.max(rangedAttackTime - 1, 0);

        if (rangedAttackTime > 0)
        {
            return;
        }

        if (d1 > d || !flag)
        {
            return;
        }
        else
        {
            doRangedAttack();
            rangedAttackTime = maxRangedAttackTime;
            return;
        }
    }

    /**
     * Performs a ranged attack according to the AI's rangedAttackID.
     */
    private void doRangedAttack()
    {
        if (rangedAttackID == 1)
        {
            EntityArrow entityarrow = new EntityArrow(worldObj, entityHost, attackTarget, 1.6F, 12F);
            worldObj.playSoundAtEntity(entityHost, "random.bow", 1.0F, 1.0F / (entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
            worldObj.spawnEntityInWorld(entityarrow);
        }
        else if (rangedAttackID == 2)
        {
            EntitySnowball entitysnowball = new EntitySnowball(worldObj, entityHost);
            double d = attackTarget.posX - entityHost.posX;
            double d1 = (attackTarget.posY + (double)attackTarget.getEyeHeight()) - 1.1000000238418579D - entitysnowball.posY;
            double d2 = attackTarget.posZ - entityHost.posZ;
            float f = MathHelper.sqrt_double(d * d + d2 * d2) * 0.2F;
            entitysnowball.setThrowableHeading(d, d1 + (double)f, d2, 1.6F, 12F);
            worldObj.playSoundAtEntity(entityHost, "random.bow", 1.0F, 1.0F / (entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
            worldObj.spawnEntityInWorld(entitysnowball);
        }
    }
}
