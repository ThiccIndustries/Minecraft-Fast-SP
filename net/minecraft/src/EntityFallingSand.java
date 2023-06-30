package net.minecraft.src;

public class EntityFallingSand extends Entity
{
    public int blockID;

    /** How long the block has been falling for. */
    public int fallTime;

    public EntityFallingSand(World par1World)
    {
        super(par1World);
        fallTime = 0;
    }

    public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World);
        fallTime = 0;
        blockID = par8;
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(par2, par4, par6);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = par2;
        prevPosY = par4;
        prevPosZ = par6;
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
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (blockID == 0)
        {
            setDead();
            return;
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        fallTime++;
        motionY -= 0.039999999105930328D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);

        if (fallTime == 1 && worldObj.getBlockId(i, j, k) == blockID)
        {
            worldObj.setBlockWithNotify(i, j, k, 0);
        }
        else if (!worldObj.isRemote && fallTime == 1)
        {
            setDead();
        }

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
            motionY *= -0.5D;

            if (worldObj.getBlockId(i, j, k) != Block.pistonMoving.blockID)
            {
                setDead();

                if ((!worldObj.canBlockBePlacedAt(blockID, i, j, k, true, 1) || BlockSand.canFallBelow(worldObj, i, j - 1, k) || !worldObj.setBlockWithNotify(i, j, k, blockID)) && !worldObj.isRemote)
                {
                    dropItem(blockID, 1);
                }
            }
        }
        else if (fallTime > 100 && !worldObj.isRemote && (j < 1 || j > 256) || fallTime > 600)
        {
            dropItem(blockID, 1);
            setDead();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Tile", (byte)blockID);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        blockID = par1NBTTagCompound.getByte("Tile") & 0xff;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public World getWorld()
    {
        return worldObj;
    }
}
