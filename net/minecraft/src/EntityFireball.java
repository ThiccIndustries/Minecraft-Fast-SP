package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EntityFireball extends Entity
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public EntityLiving shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    public EntityFireball(World par1World)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        setSize(1.0F, 1.0F);
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

    public EntityFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        setSize(1.0F, 1.0F);
        setLocationAndAngles(par2, par4, par6, rotationYaw, rotationPitch);
        setPosition(par2, par4, par6);
        double d = MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
        accelerationX = (par8 / d) * 0.10000000000000001D;
        accelerationY = (par10 / d) * 0.10000000000000001D;
        accelerationZ = (par12 / d) * 0.10000000000000001D;
    }

    public EntityFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        shootingEntity = par2EntityLiving;
        setSize(1.0F, 1.0F);
        setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = motionY = motionZ = 0.0D;
        par3 += rand.nextGaussian() * 0.40000000000000002D;
        par5 += rand.nextGaussian() * 0.40000000000000002D;
        par7 += rand.nextGaussian() * 0.40000000000000002D;
        double d = MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
        accelerationX = (par3 / d) * 0.10000000000000001D;
        accelerationY = (par5 / d) * 0.10000000000000001D;
        accelerationZ = (par7 / d) * 0.10000000000000001D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int)posX, (int)posY, (int)posZ)))
        {
            setDead();
            return;
        }

        super.onUpdate();
        setFire(1);

        if (inGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);

            if (i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksAlive = 0;
                ticksInAir = 0;
            }
            else
            {
                ticksAlive++;

                if (ticksAlive == 600)
                {
                    setDead();
                }

                return;
            }
        }
        else
        {
            ticksInAir++;
        }

        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);

        if (movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;

        for (int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);

            if (!entity1.canBeCollidedWith() || entity1.isEntityEqual(shootingEntity) && ticksInAir < 25)
            {
                continue;
            }

            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

            if (movingobjectposition1 == null)
            {
                continue;
            }

            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if (d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null)
        {
            func_40071_a(movingobjectposition);
        }

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
        float f1 = 0.95F;

        if (isInWater())
        {
            for (int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }

        motionX += accelerationX;
        motionY += accelerationY;
        motionZ += accelerationZ;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        setPosition(posX, posY, posZ);
    }

    protected void func_40071_a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!worldObj.isRemote)
        {
            if (par1MovingObjectPosition.entityHit != null)
            {
                if (!par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 4));
            }

            worldObj.newExplosion(null, posX, posY, posZ, 1.0F, true);
            setDead();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)xTile);
        par1NBTTagCompound.setShort("yTile", (short)yTile);
        par1NBTTagCompound.setShort("zTile", (short)zTile);
        par1NBTTagCompound.setByte("inTile", (byte)inTile);
        par1NBTTagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        xTile = par1NBTTagCompound.getShort("xTile");
        yTile = par1NBTTagCompound.getShort("yTile");
        zTile = par1NBTTagCompound.getShort("zTile");
        inTile = par1NBTTagCompound.getByte("inTile") & 0xff;
        inGround = par1NBTTagCompound.getByte("inGround") == 1;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    public float getCollisionBorderSize()
    {
        return 1.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        setBeenAttacked();

        if (par1DamageSource.getEntity() != null)
        {
            Vec3D vec3d = par1DamageSource.getEntity().getLookVec();

            if (vec3d != null)
            {
                motionX = vec3d.xCoord;
                motionY = vec3d.yCoord;
                motionZ = vec3d.zCoord;
                accelerationX = motionX * 0.10000000000000001D;
                accelerationY = motionY * 0.10000000000000001D;
                accelerationZ = motionZ * 0.10000000000000001D;
            }

            if (par1DamageSource.getEntity() instanceof EntityLiving)
            {
                shootingEntity = (EntityLiving)par1DamageSource.getEntity();
            }

            return true;
        }
        else
        {
            return false;
        }
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
}
