package net.minecraft.src;

public class EntityAIDefendVillage extends EntityAITarget
{
    EntityIronGolem irongolem;

    /**
     * The aggressor of the iron golem's village which is now the golem's attack target.
     */
    EntityLiving villageAgressorTarget;

    public EntityAIDefendVillage(EntityIronGolem par1EntityIronGolem)
    {
        super(par1EntityIronGolem, 16F, false, true);
        irongolem = par1EntityIronGolem;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        Village village = irongolem.getVillage();

        if (village == null)
        {
            return false;
        }
        else
        {
            villageAgressorTarget = village.findNearestVillageAggressor(irongolem);
            return func_48376_a(villageAgressorTarget, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        irongolem.setAttackTarget(villageAgressorTarget);
        super.startExecuting();
    }
}
