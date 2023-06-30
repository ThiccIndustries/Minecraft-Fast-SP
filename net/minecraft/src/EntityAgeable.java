package net.minecraft.src;

public abstract class EntityAgeable extends EntityCreature
{
    public EntityAgeable(World par1World)
    {
        super(par1World);
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(12, new Integer(0));
    }

    /**
     * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
     * positive, it get's decremented each tick. Don't confuse this with EntityLiving.getAge. With a negative value the
     * Entity is considered a child.
     */
    public int getGrowingAge()
    {
        return dataWatcher.getWatchableObjectInt(12);
    }

    /**
     * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
     * positive, it get's decremented each tick. With a negative value the Entity is considered a child.
     */
    public void setGrowingAge(int par1)
    {
        dataWatcher.updateObject(12, Integer.valueOf(par1));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Age", getGrowingAge());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        setGrowingAge(par1NBTTagCompound.getInteger("Age"));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        int i = getGrowingAge();

        if (i < 0)
        {
            i++;
            setGrowingAge(i);
        }
        else if (i > 0)
        {
            i--;
            setGrowingAge(i);
        }
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isChild()
    {
        return getGrowingAge() < 0;
    }
}
