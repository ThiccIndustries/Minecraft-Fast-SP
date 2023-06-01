package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable field_48392_a;
    EntityLiving field_48391_b;

    public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        field_48392_a = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!field_48392_a.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = field_48392_a.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_48391_b = entityliving.getLastAttackingEntity();
            return func_48376_a(field_48391_b, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(field_48391_b);
        super.startExecuting();
    }
}
