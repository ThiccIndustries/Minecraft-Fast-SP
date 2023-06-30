package net.minecraft.src;

import java.util.*;

public class EntityPainting extends Entity
{
    private int tickCounter1;

    /** the direction the painting faces */
    public int direction;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public EnumArt art;

    public EntityPainting(World par1World)
    {
        super(par1World);
        tickCounter1 = 0;
        direction = 0;
        yOffset = 0.0F;
        setSize(0.5F, 0.5F);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5)
    {
        this(par1World);
        xPosition = par2;
        yPosition = par3;
        zPosition = par4;
        ArrayList arraylist = new ArrayList();
        EnumArt aenumart[] = EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; j++)
        {
            EnumArt enumart = aenumart[j];
            art = enumart;
            func_412_b(par5);

            if (onValidSurface())
            {
                arraylist.add(enumart);
            }
        }

        if (arraylist.size() > 0)
        {
            art = (EnumArt)arraylist.get(rand.nextInt(arraylist.size()));
        }

        func_412_b(par5);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5, String par6Str)
    {
        this(par1World);
        xPosition = par2;
        yPosition = par3;
        zPosition = par4;
        EnumArt aenumart[] = EnumArt.values();
        int i = aenumart.length;
        int j = 0;

        do
        {
            if (j >= i)
            {
                break;
            }

            EnumArt enumart = aenumart[j];

            if (enumart.title.equals(par6Str))
            {
                art = enumart;
                break;
            }

            j++;
        }
        while (true);

        func_412_b(par5);
    }

    protected void entityInit()
    {
    }

    public void func_412_b(int par1)
    {
        direction = par1;
        prevRotationYaw = rotationYaw = par1 * 90;
        float f = art.sizeX;
        float f1 = art.sizeY;
        float f2 = art.sizeX;

        if (par1 == 0 || par1 == 2)
        {
            f2 = 0.5F;
        }
        else
        {
            f = 0.5F;
        }

        f /= 32F;
        f1 /= 32F;
        f2 /= 32F;
        float f3 = (float)xPosition + 0.5F;
        float f4 = (float)yPosition + 0.5F;
        float f5 = (float)zPosition + 0.5F;
        float f6 = 0.5625F;

        if (par1 == 0)
        {
            f5 -= f6;
        }

        if (par1 == 1)
        {
            f3 -= f6;
        }

        if (par1 == 2)
        {
            f5 += f6;
        }

        if (par1 == 3)
        {
            f3 += f6;
        }

        if (par1 == 0)
        {
            f3 -= func_411_c(art.sizeX);
        }

        if (par1 == 1)
        {
            f5 += func_411_c(art.sizeX);
        }

        if (par1 == 2)
        {
            f3 += func_411_c(art.sizeX);
        }

        if (par1 == 3)
        {
            f5 -= func_411_c(art.sizeX);
        }

        f4 += func_411_c(art.sizeY);
        setPosition(f3, f4, f5);
        float f7 = -0.00625F;
        boundingBox.setBounds(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
    }

    private float func_411_c(int par1)
    {
        if (par1 == 32)
        {
            return 0.5F;
        }

        return par1 != 64 ? 0.0F : 0.5F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (tickCounter1++ == 100 && !worldObj.isRemote)
        {
            tickCounter1 = 0;

            if (!isDead && !onValidSurface())
            {
                setDead();
                worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
            }
        }
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        if (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0)
        {
            return false;
        }

        int i = art.sizeX / 16;
        int j = art.sizeY / 16;
        int k = xPosition;
        int l = yPosition;
        int i1 = zPosition;

        if (direction == 0)
        {
            k = MathHelper.floor_double(posX - (double)((float)art.sizeX / 32F));
        }

        if (direction == 1)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)art.sizeX / 32F));
        }

        if (direction == 2)
        {
            k = MathHelper.floor_double(posX - (double)((float)art.sizeX / 32F));
        }

        if (direction == 3)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)art.sizeX / 32F));
        }

        l = MathHelper.floor_double(posY - (double)((float)art.sizeY / 32F));

        for (int j1 = 0; j1 < i; j1++)
        {
            for (int k1 = 0; k1 < j; k1++)
            {
                Material material;

                if (direction == 0 || direction == 2)
                {
                    material = worldObj.getBlockMaterial(k + j1, l + k1, zPosition);
                }
                else
                {
                    material = worldObj.getBlockMaterial(xPosition, l + k1, i1 + j1);
                }

                if (!material.isSolid())
                {
                    return false;
                }
            }
        }

        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);

        for (int l1 = 0; l1 < list.size(); l1++)
        {
            if (list.get(l1) instanceof EntityPainting)
            {
                return false;
            }
        }

        return true;
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
            setDead();
            setBeenAttacked();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
        }

        return true;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Dir", (byte)direction);
        par1NBTTagCompound.setString("Motive", art.title);
        par1NBTTagCompound.setInteger("TileX", xPosition);
        par1NBTTagCompound.setInteger("TileY", yPosition);
        par1NBTTagCompound.setInteger("TileZ", zPosition);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        direction = par1NBTTagCompound.getByte("Dir");
        xPosition = par1NBTTagCompound.getInteger("TileX");
        yPosition = par1NBTTagCompound.getInteger("TileY");
        zPosition = par1NBTTagCompound.getInteger("TileZ");
        String s = par1NBTTagCompound.getString("Motive");
        EnumArt aenumart[] = EnumArt.values();
        int i = aenumart.length;

        for (int j = 0; j < i; j++)
        {
            EnumArt enumart = aenumart[j];

            if (enumart.title.equals(s))
            {
                art = enumart;
            }
        }

        if (art == null)
        {
            art = EnumArt.Kebab;
        }

        func_412_b(direction);
    }

    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double par1, double par3, double par5)
    {
        if (!worldObj.isRemote && !isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            setDead();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!worldObj.isRemote && !isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            setDead();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
        }
    }
}
