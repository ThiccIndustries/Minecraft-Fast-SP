package net.minecraft.src;

import java.util.Random;

public class EntityEnderCrystal extends Entity
{
    /** Used to create the rotation animation when rendering the crystal. */
    public int innerRotation;
    public int health;

    public EntityEnderCrystal(World par1World)
    {
        super(par1World);
        innerRotation = 0;
        preventEntitySpawning = true;
        setSize(2.0F, 2.0F);
        yOffset = height / 2.0F;
        health = 5;
        innerRotation = rand.nextInt(0x186a0);
    }

    public EntityEnderCrystal(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        setPosition(par2, par4, par6);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        dataWatcher.addObject(8, Integer.valueOf(health));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        innerRotation++;
        dataWatcher.updateObject(8, Integer.valueOf(health));
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);

        if (worldObj.getBlockId(i, j, k) != Block.fire.blockID)
        {
            worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (!isDead && !worldObj.isRemote)
        {
            health = 0;

            if (health <= 0)
            {
                if (!worldObj.isRemote)
                {
                    setDead();
                    worldObj.createExplosion(null, posX, posY, posZ, 6F);
                }
                else
                {
                    setDead();
                }
            }
        }

        return true;
    }
}
