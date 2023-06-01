package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EntityLightningBolt extends EntityWeatherEffect
{
    /**
     * Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc.
     */
    private int lightningState;

    /**
     * A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt
     */
    public long boltVertex;

    /**
     * Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time.
     */
    private int boltLivingTime;

    public EntityLightningBolt(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        boltVertex = 0L;
        setLocationAndAngles(par2, par4, par6, 0.0F, 0.0F);
        lightningState = 2;
        boltVertex = rand.nextLong();
        boltLivingTime = rand.nextInt(3) + 1;

        if (par1World.difficultySetting >= 2 && par1World.doChunksNearChunkExist(MathHelper.floor_double(par2), MathHelper.floor_double(par4), MathHelper.floor_double(par6), 10))
        {
            int i = MathHelper.floor_double(par2);
            int k = MathHelper.floor_double(par4);
            int i1 = MathHelper.floor_double(par6);

            if (par1World.getBlockId(i, k, i1) == 0 && Block.fire.canPlaceBlockAt(par1World, i, k, i1))
            {
                par1World.setBlockWithNotify(i, k, i1, Block.fire.blockID);
            }

            for (int j = 0; j < 4; j++)
            {
                int l = (MathHelper.floor_double(par2) + rand.nextInt(3)) - 1;
                int j1 = (MathHelper.floor_double(par4) + rand.nextInt(3)) - 1;
                int k1 = (MathHelper.floor_double(par6) + rand.nextInt(3)) - 1;

                if (par1World.getBlockId(l, j1, k1) == 0 && Block.fire.canPlaceBlockAt(par1World, l, j1, k1))
                {
                    par1World.setBlockWithNotify(l, j1, k1, Block.fire.blockID);
                }
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (lightningState == 2)
        {
            worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000F, 0.8F + rand.nextFloat() * 0.2F);
            worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F, 0.5F + rand.nextFloat() * 0.2F);
        }

        lightningState--;

        if (lightningState < 0)
        {
            if (boltLivingTime == 0)
            {
                setDead();
            }
            else if (lightningState < -rand.nextInt(10))
            {
                boltLivingTime--;
                lightningState = 1;
                boltVertex = rand.nextLong();

                if (worldObj.doChunksNearChunkExist(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 10))
                {
                    int i = MathHelper.floor_double(posX);
                    int j = MathHelper.floor_double(posY);
                    int k = MathHelper.floor_double(posZ);

                    if (worldObj.getBlockId(i, j, k) == 0 && Block.fire.canPlaceBlockAt(worldObj, i, j, k))
                    {
                        worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
                    }
                }
            }
        }

        if (lightningState >= 0)
        {
            double d = 3D;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBoxFromPool(posX - d, posY - d, posZ - d, posX + d, posY + 6D + d, posZ + d));

            for (int l = 0; l < list.size(); l++)
            {
                Entity entity = (Entity)list.get(l);
                entity.onStruckByLightning(this);
            }

            worldObj.lightningFlash = 2;
        }
    }

    protected void entityInit()
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * Checks using a Vec3d to determine if this entity is within range of that vector to be rendered. Args: vec3D
     */
    public boolean isInRangeToRenderVec3D(Vec3D par1Vec3D)
    {
        return lightningState >= 0;
    }
}
