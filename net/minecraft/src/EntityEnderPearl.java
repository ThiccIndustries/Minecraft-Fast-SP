package net.minecraft.src;

import java.util.Random;

public class EntityEnderPearl extends EntityThrowable
{
    public EntityEnderPearl(World par1World)
    {
        super(par1World);
    }

    public EntityEnderPearl(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntityEnderPearl(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when the throwable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            if (!par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0));
        }

        for (int i = 0; i < 32; i++)
        {
            worldObj.spawnParticle("portal", posX, posY + rand.nextDouble() * 2D, posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
        }

        if (!worldObj.isRemote)
        {
            if (thrower != null)
            {
                thrower.setPositionAndUpdate(posX, posY, posZ);
                thrower.fallDistance = 0.0F;
                thrower.attackEntityFrom(DamageSource.fall, 5);
            }

            setDead();
        }
    }
}
