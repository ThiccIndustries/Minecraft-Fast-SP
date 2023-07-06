package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable field_48394_a;
    EntityLiving field_48393_b;

    public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        field_48394_a = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!field_48394_a.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = field_48394_a.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_48393_b = entityliving.getAITarget();
            return func_48376_a(field_48393_b, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(field_48393_b);
        super.startExecuting();
    }
}
