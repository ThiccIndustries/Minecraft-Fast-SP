package net.minecraft.src;

public class EntityAICreeperSwell extends EntityAIBase
{
    /** The creeper that is swelling. */
    EntityCreeper swellingCreeper;

    /**
     * The creeper's attack target. This is used for the changing of the creeper's state.
     */
    EntityLiving creeperAttackTarget;

    public EntityAICreeperSwell(EntityCreeper par1EntityCreeper)
    {
        swellingCreeper = par1EntityCreeper;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving entityliving = swellingCreeper.getAttackTarget();
        return swellingCreeper.getCreeperState() > 0 || entityliving != null && swellingCreeper.getDistanceSqToEntity(entityliving) < 9D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        swellingCreeper.getNavigator().clearPathEntity();
        creeperAttackTarget = swellingCreeper.getAttackTarget();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        creeperAttackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (creeperAttackTarget == null)
        {
            swellingCreeper.setCreeperState(-1);
            return;
        }

        if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49D)
        {
            swellingCreeper.setCreeperState(-1);
            return;
        }

        if (!swellingCreeper.func_48090_aM().canSee(creeperAttackTarget))
        {
            swellingCreeper.setCreeperState(-1);
            return;
        }
        else
        {
            swellingCreeper.setCreeperState(1);
            return;
        }
    }
}
