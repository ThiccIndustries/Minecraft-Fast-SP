package net.minecraft.src;

import java.util.Random;

public class EntityBubbleFX extends EntityFX
{
    public EntityBubbleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;
        setParticleTextureIndex(32);
        setSize(0.02F, 0.02F);
        particleScale = particleScale * (rand.nextFloat() * 0.6F + 0.2F);
        motionX = par8 * 0.20000000298023224D + (double)((float)(Math.random() * 2D - 1.0D) * 0.02F);
        motionY = par10 * 0.20000000298023224D + (double)((float)(Math.random() * 2D - 1.0D) * 0.02F);
        motionZ = par12 * 0.20000000298023224D + (double)((float)(Math.random() * 2D - 1.0D) * 0.02F);
        particleMaxAge = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY += 0.002D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.85000002384185791D;
        motionY *= 0.85000002384185791D;
        motionZ *= 0.85000002384185791D;

        if (worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) != Material.water)
        {
            setDead();
        }

        if (particleMaxAge-- <= 0)
        {
            setDead();
        }
    }
}
