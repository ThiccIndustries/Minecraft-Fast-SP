package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
    /**
     * The damage value of the thrown potion that this EntityPotion represents.
     */
    private int potionDamage;

    public EntityPotion(World par1World)
    {
        super(par1World);
    }

    public EntityPotion(World par1World, EntityLiving par2EntityLiving, int par3)
    {
        super(par1World, par2EntityLiving);
        potionDamage = par3;
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World, par2, par4, par6);
        potionDamage = par8;
    }

    protected float func_40075_e()
    {
        return 0.05F;
    }

    protected float func_40077_c()
    {
        return 0.5F;
    }

    protected float func_40074_d()
    {
        return -20F;
    }

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
    public int getPotionDamage()
    {
        return potionDamage;
    }

    /**
     * Called when the throwable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!worldObj.isRemote)
        {
            List list = Item.potion.getEffects(potionDamage);

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = boundingBox.expand(4D, 2D, 4D);
                List list1 = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    for (Iterator iterator = list1.iterator(); iterator.hasNext();)
                    {
                        Entity entity = (Entity)iterator.next();
                        double d = getDistanceSqToEntity(entity);

                        if (d < 16D)
                        {
                            double d1 = 1.0D - Math.sqrt(d) / 4D;

                            if (entity == par1MovingObjectPosition.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                    Potion.potionTypes[i].affectEntity(thrower, (EntityLiving)entity, potioneffect.getAmplifier(), d1);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        ((EntityLiving)entity).addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            worldObj.playAuxSFX(2002, (int)Math.round(posX), (int)Math.round(posY), (int)Math.round(posZ), potionDamage);
            setDead();
        }
    }
}
