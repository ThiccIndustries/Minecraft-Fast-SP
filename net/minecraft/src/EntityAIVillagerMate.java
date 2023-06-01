package net.minecraft.src;

import java.util.Random;

public class EntityAIVillagerMate extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityVillager mate;
    private World worldObj;
    private int matingTimeout;
    Village villageObj;

    public EntityAIVillagerMate(EntityVillager par1EntityVillager)
    {
        matingTimeout = 0;
        villagerObj = par1EntityVillager;
        worldObj = par1EntityVillager.worldObj;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (villagerObj.getGrowingAge() != 0)
        {
            return false;
        }

        if (villagerObj.getRNG().nextInt(500) != 0)
        {
            return false;
        }

        villageObj = worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(villagerObj.posX), MathHelper.floor_double(villagerObj.posY), MathHelper.floor_double(villagerObj.posZ), 0);

        if (villageObj == null)
        {
            return false;
        }

        if (!checkSufficientDoorsPresentForNewVillager())
        {
            return false;
        }

        Entity entity = worldObj.findNearestEntityWithinAABB(net.minecraft.src.EntityVillager.class, villagerObj.boundingBox.expand(8D, 3D, 8D), villagerObj);

        if (entity == null)
        {
            return false;
        }

        mate = (EntityVillager)entity;
        return mate.getGrowingAge() == 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        matingTimeout = 300;
        villagerObj.setIsMatingFlag(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villageObj = null;
        mate = null;
        villagerObj.setIsMatingFlag(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return matingTimeout >= 0 && checkSufficientDoorsPresentForNewVillager() && villagerObj.getGrowingAge() == 0;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        matingTimeout--;
        villagerObj.getLookHelper().setLookPositionWithEntity(mate, 10F, 30F);

        if (villagerObj.getDistanceSqToEntity(mate) > 2.25D)
        {
            villagerObj.getNavigator().func_48667_a(mate, 0.25F);
        }
        else if (matingTimeout == 0 && mate.getIsMatingFlag())
        {
            giveBirth();
        }

        if (villagerObj.getRNG().nextInt(35) == 0)
        {
            spawnHeartParticles(villagerObj);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager()
    {
        int i = (int)((double)(float)villageObj.getNumVillageDoors() * 0.34999999999999998D);
        return villageObj.getNumVillagers() < i;
    }

    private void giveBirth()
    {
        EntityVillager entityvillager = new EntityVillager(worldObj);
        mate.setGrowingAge(6000);
        villagerObj.setGrowingAge(6000);
        entityvillager.setGrowingAge(-24000);
        entityvillager.setProfession(villagerObj.getRNG().nextInt(5));
        entityvillager.setLocationAndAngles(villagerObj.posX, villagerObj.posY, villagerObj.posZ, 0.0F, 0.0F);
        worldObj.spawnEntityInWorld(entityvillager);
        spawnHeartParticles(entityvillager);
    }

    private void spawnHeartParticles(EntityLiving par1EntityLiving)
    {
        Random random = par1EntityLiving.getRNG();

        for (int i = 0; i < 5; i++)
        {
            double d = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            worldObj.spawnParticle("heart", (par1EntityLiving.posX + (double)(random.nextFloat() * par1EntityLiving.width * 2.0F)) - (double)par1EntityLiving.width, par1EntityLiving.posY + 1.0D + (double)(random.nextFloat() * par1EntityLiving.height), (par1EntityLiving.posZ + (double)(random.nextFloat() * par1EntityLiving.width * 2.0F)) - (double)par1EntityLiving.width, d, d1, d2);
        }
    }
}
