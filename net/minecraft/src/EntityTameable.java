package net.minecraft.src;

import java.util.Random;

public abstract class EntityTameable extends EntityAnimal
{
    protected EntityAISit aiSit;

    public EntityTameable(World par1World)
    {
        super(par1World);
        aiSit = new EntityAISit(this);
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, "");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (getOwnerName() == null)
        {
            par1NBTTagCompound.setString("Owner", "");
        }
        else
        {
            par1NBTTagCompound.setString("Owner", getOwnerName());
        }

        par1NBTTagCompound.setBoolean("Sitting", isSitting());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        String s = par1NBTTagCompound.getString("Owner");

        if (s.length() > 0)
        {
            setOwner(s);
            setTamed(true);
        }

        aiSit.func_48407_a(par1NBTTagCompound.getBoolean("Sitting"));
    }

    protected void func_48142_a(boolean par1)
    {
        String s = "heart";

        if (!par1)
        {
            s = "smoke";
        }

        for (int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
        }
    }

    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 7)
        {
            func_48142_a(true);
        }
        else if (par1 == 6)
        {
            func_48142_a(false);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public boolean isTamed()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void setTamed(boolean par1)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 4)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }

    public boolean isSitting()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_48140_f(boolean par1)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 1)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -2)));
        }
    }

    public String getOwnerName()
    {
        return dataWatcher.getWatchableObjectString(17);
    }

    public void setOwner(String par1Str)
    {
        dataWatcher.updateObject(17, par1Str);
    }

    public EntityLiving getOwner()
    {
        return worldObj.getPlayerEntityByName(getOwnerName());
    }

    public EntityAISit func_50008_ai()
    {
        return aiSit;
    }
}
