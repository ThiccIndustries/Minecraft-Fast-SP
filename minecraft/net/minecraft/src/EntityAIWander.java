package net.minecraft.src;

import java.util.Random;

public class EntityAIWander extends EntityAIBase
{
    private EntityCreature entity;
    private double field_46098_b;
    private double field_46099_c;
    private double field_46097_d;
    private float field_48317_e;

    public EntityAIWander(EntityCreature par1EntityCreature, float par2)
    {
        entity = par1EntityCreature;
        field_48317_e = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (entity.getAge() >= 100)
        {
            return false;
        }

        if (entity.getRNG().nextInt(120) != 0)
        {
            return false;
        }

        Vec3D vec3d = RandomPositionGenerator.func_48622_a(entity, 10, 7);

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            field_46098_b = vec3d.xCoord;
            field_46099_c = vec3d.yCoord;
            field_46097_d = vec3d.zCoord;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        entity.getNavigator().func_48666_a(field_46098_b, field_46099_c, field_46097_d, field_48317_e);
    }
}
