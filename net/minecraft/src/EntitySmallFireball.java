package net.minecraft.src;

public class EntitySmallFireball extends EntityFireball
{
    public EntitySmallFireball(World par1World)
    {
        super(par1World);
        setSize(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLiving, par3, par5, par7);
        setSize(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        setSize(0.3125F, 0.3125F);
    }

    protected void func_40071_a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!worldObj.isRemote)
        {
            if (par1MovingObjectPosition.entityHit != null)
            {
                if (!par1MovingObjectPosition.entityHit.isImmuneToFire() && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 5))
                {
                    par1MovingObjectPosition.entityHit.setFire(5);
                }
            }
            else
            {
                int i = par1MovingObjectPosition.blockX;
                int j = par1MovingObjectPosition.blockY;
                int k = par1MovingObjectPosition.blockZ;

                switch (par1MovingObjectPosition.sideHit)
                {
                    case 1:
                        j++;
                        break;

                    case 0:
                        j--;
                        break;

                    case 2:
                        k--;
                        break;

                    case 3:
                        k++;
                        break;

                    case 5:
                        i++;
                        break;

                    case 4:
                        i--;
                        break;
                }

                if (worldObj.isAirBlock(i, j, k))
                {
                    worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
                }
            }

            setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return false;
    }
}
