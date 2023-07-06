package net.minecraft.src;

public class EntityAITempt extends EntityAIBase
{
    /** The entity using this AI that is tempted by the player. */
    private EntityCreature temptedEntity;
    private float field_48275_b;
    private double field_48276_c;
    private double field_48273_d;
    private double field_48274_e;
    private double field_48271_f;
    private double field_48272_g;

    /** The player that is tempting the entity that is using this AI. */
    private EntityPlayer temptingPlayer;

    /**
     * A counter that is decremented each time the shouldExecute method is called. The shouldExecute method will always
     * return false if delayTemptCounter is greater than 0.
     */
    private int delayTemptCounter;
    private boolean field_48280_j;

    /**
     * This field saves the ID of the items that can be used to breed entities with this behaviour.
     */
    private int breedingFood;

    /**
     * Whether the entity using this AI will be scared by the tempter's sudden movement.
     */
    private boolean scaredByPlayerMovement;
    private boolean field_48279_m;

    public EntityAITempt(EntityCreature par1EntityCreature, float par2, int par3, boolean par4)
    {
        delayTemptCounter = 0;
        temptedEntity = par1EntityCreature;
        field_48275_b = par2;
        breedingFood = par3;
        scaredByPlayerMovement = par4;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (delayTemptCounter > 0)
        {
            delayTemptCounter--;
            return false;
        }

        temptingPlayer = temptedEntity.worldObj.getClosestPlayerToEntity(temptedEntity, 10D);

        if (temptingPlayer == null)
        {
            return false;
        }

        ItemStack itemstack = temptingPlayer.getCurrentEquippedItem();

        if (itemstack == null)
        {
            return false;
        }

        return itemstack.itemID == breedingFood;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (scaredByPlayerMovement)
        {
            if (temptedEntity.getDistanceSqToEntity(temptingPlayer) < 36D)
            {
                if (temptingPlayer.getDistanceSq(field_48276_c, field_48273_d, field_48274_e) > 0.010000000000000002D)
                {
                    return false;
                }

                if (Math.abs((double)temptingPlayer.rotationPitch - field_48271_f) > 5D || Math.abs((double)temptingPlayer.rotationYaw - field_48272_g) > 5D)
                {
                    return false;
                }
            }
            else
            {
                field_48276_c = temptingPlayer.posX;
                field_48273_d = temptingPlayer.posY;
                field_48274_e = temptingPlayer.posZ;
            }

            field_48271_f = temptingPlayer.rotationPitch;
            field_48272_g = temptingPlayer.rotationYaw;
        }

        return shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48276_c = temptingPlayer.posX;
        field_48273_d = temptingPlayer.posY;
        field_48274_e = temptingPlayer.posZ;
        field_48280_j = true;
        field_48279_m = temptedEntity.getNavigator().func_48658_a();
        temptedEntity.getNavigator().func_48664_a(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        temptingPlayer = null;
        temptedEntity.getNavigator().clearPathEntity();
        delayTemptCounter = 100;
        field_48280_j = false;
        temptedEntity.getNavigator().func_48664_a(field_48279_m);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        temptedEntity.getLookHelper().setLookPositionWithEntity(temptingPlayer, 30F, temptedEntity.getVerticalFaceSpeed());

        if (temptedEntity.getDistanceSqToEntity(temptingPlayer) < 6.25D)
        {
            temptedEntity.getNavigator().clearPathEntity();
        }
        else
        {
            temptedEntity.getNavigator().func_48667_a(temptingPlayer, field_48275_b);
        }
    }

    public boolean func_48270_h()
    {
        return field_48280_j;
    }
}
