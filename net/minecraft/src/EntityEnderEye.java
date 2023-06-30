package net.minecraft.src;

import java.util.Random;

public class EntityEnderEye extends Entity
{
    public int field_40096_a;
    private double field_40094_b;
    private double field_40095_c;
    private double field_40091_d;
    private int despawnTimer;
    private boolean shatterOrDrop;

    public EntityEnderEye(World par1World)
    {
        super(par1World);
        field_40096_a = 0;
        setSize(0.25F, 0.25F);
    }

    protected void entityInit()
    {
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double par1)
    {
        double d = boundingBox.getAverageEdgeLength() * 4D;
        d *= 64D;
        return par1 < d * d;
    }

    public EntityEnderEye(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        field_40096_a = 0;
        despawnTimer = 0;
        setSize(0.25F, 0.25F);
        setPosition(par2, par4, par6);
        yOffset = 0.0F;
    }

    public void func_40090_a(double par1, int par3, double par4)
    {
        double d = par1 - posX;
        double d1 = par4 - posZ;
        float f = MathHelper.sqrt_double(d * d + d1 * d1);

        if (f > 12F)
        {
            field_40094_b = posX + (d / (double)f) * 12D;
            field_40091_d = posZ + (d1 / (double)f) * 12D;
            field_40095_c = posY + 8D;
        }
        else
        {
            field_40094_b = par1;
            field_40095_c = par3;
            field_40091_d = par4;
        }

        despawnTimer = 0;
        shatterOrDrop = rand.nextInt(5) > 0;
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double par1, double par3, double par5)
    {
        motionX = par1;
        motionY = par3;
        motionZ = par5;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(par1, par5) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(par3, f) * 180D) / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for (rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }

        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }

        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

        if (!worldObj.isRemote)
        {
            double d = field_40094_b - posX;
            double d1 = field_40091_d - posZ;
            float f2 = (float)Math.sqrt(d * d + d1 * d1);
            float f3 = (float)Math.atan2(d1, d);
            double d2 = (double)f + (double)(f2 - f) * 0.0025000000000000001D;

            if (f2 < 1.0F)
            {
                d2 *= 0.80000000000000004D;
                motionY *= 0.80000000000000004D;
            }

            motionX = Math.cos(f3) * d2;
            motionZ = Math.sin(f3) * d2;

            if (posY < field_40095_c)
            {
                motionY = motionY + (1.0D - motionY) * 0.014999999664723873D;
            }
            else
            {
                motionY = motionY + (-1D - motionY) * 0.014999999664723873D;
            }
        }

        float f1 = 0.25F;

        if (isInWater())
        {
            for (int i = 0; i < 4; i++)
            {
                worldObj.spawnParticle("bubble", posX - motionX * (double)f1, posY - motionY * (double)f1, posZ - motionZ * (double)f1, motionX, motionY, motionZ);
            }
        }
        else
        {
            worldObj.spawnParticle("portal", ((posX - motionX * (double)f1) + rand.nextDouble() * 0.59999999999999998D) - 0.29999999999999999D, posY - motionY * (double)f1 - 0.5D, ((posZ - motionZ * (double)f1) + rand.nextDouble() * 0.59999999999999998D) - 0.29999999999999999D, motionX, motionY, motionZ);
        }

        if (!worldObj.isRemote)
        {
            setPosition(posX, posY, posZ);
            despawnTimer++;

            if (despawnTimer > 80 && !worldObj.isRemote)
            {
                setDead();

                if (shatterOrDrop)
                {
                    worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.eyeOfEnder)));
                }
                else
                {
                    worldObj.playAuxSFX(2003, (int)Math.round(posX), (int)Math.round(posY), (int)Math.round(posZ), 0);
                }
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

    public int getBrightnessForRender(float par1)
    {
        return 0xf000f0;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }
}
