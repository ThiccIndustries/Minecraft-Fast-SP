package net.minecraft.src;

public class EntityOtherPlayerMP extends EntityPlayer
{
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;

    public EntityOtherPlayerMP(World par1World, String par2Str)
    {
        super(par1World);
        isItemInUse = false;
        username = par2Str;
        yOffset = 0.0F;
        stepHeight = 0.0F;

        if (par2Str != null && par2Str.length() > 0)
        {
            skinUrl = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(par2Str).append(".png").toString();
        }

        noClip = true;
        field_22062_y = 0.25F;
        renderDistanceWeight = 10D;
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void resetHeight()
    {
        yOffset = 0.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return true;
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        otherPlayerMPX = par1;
        otherPlayerMPY = par3;
        otherPlayerMPZ = par5;
        otherPlayerMPYaw = par7;
        otherPlayerMPPitch = par8;
        otherPlayerMPPosRotationIncrements = par9;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        field_22062_y = 0.0F;
        super.onUpdate();
        field_705_Q = field_704_R;
        double d = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f = MathHelper.sqrt_double(d * d + d1 * d1) * 4F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        field_704_R += (f - field_704_R) * 0.4F;
        field_703_S += field_704_R;

        if (!isItemInUse && isEating() && inventory.mainInventory[inventory.currentItem] != null)
        {
            ItemStack itemstack = inventory.mainInventory[inventory.currentItem];
            setItemInUse(inventory.mainInventory[inventory.currentItem], Item.itemsList[itemstack.itemID].getMaxItemUseDuration(itemstack));
            isItemInUse = true;
        }
        else if (isItemInUse && !isEating())
        {
            clearItemInUse();
            isItemInUse = false;
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.updateEntityActionState();

        if (otherPlayerMPPosRotationIncrements > 0)
        {
            double d = posX + (otherPlayerMPX - posX) / (double)otherPlayerMPPosRotationIncrements;
            double d1 = posY + (otherPlayerMPY - posY) / (double)otherPlayerMPPosRotationIncrements;
            double d2 = posZ + (otherPlayerMPZ - posZ) / (double)otherPlayerMPPosRotationIncrements;
            double d3;

            for (d3 = otherPlayerMPYaw - (double)rotationYaw; d3 < -180D; d3 += 360D) { }

            for (; d3 >= 180D; d3 -= 360D) { }

            rotationYaw += d3 / (double)otherPlayerMPPosRotationIncrements;
            rotationPitch += (otherPlayerMPPitch - (double)rotationPitch) / (double)otherPlayerMPPosRotationIncrements;
            otherPlayerMPPosRotationIncrements--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
        }

        prevCameraYaw = cameraYaw;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        float f1 = (float)Math.atan(-motionY * 0.20000000298023224D) * 15F;

        if (f > 0.1F)
        {
            f = 0.1F;
        }

        if (!onGround || getHealth() <= 0)
        {
            f = 0.0F;
        }

        if (onGround || getHealth() <= 0)
        {
            f1 = 0.0F;
        }

        cameraYaw += (f - cameraYaw) * 0.4F;
        cameraPitch += (f1 - cameraPitch) * 0.8F;
    }

    /**
     * Parameters: item slot, item ID, item damage. If slot >= 0 a new item will be generated with the specified item ID
     * damage.
     */
    public void outfitWithItem(int par1, int par2, int par3)
    {
        ItemStack itemstack = null;

        if (par2 >= 0)
        {
            itemstack = new ItemStack(par2, 1, par3);
        }

        if (par1 == 0)
        {
            inventory.mainInventory[inventory.currentItem] = itemstack;
        }
        else
        {
            inventory.armorInventory[par1 - 1] = itemstack;
        }
    }

    public void func_6420_o()
    {
    }

    public float getEyeHeight()
    {
        return 1.82F;
    }
}
