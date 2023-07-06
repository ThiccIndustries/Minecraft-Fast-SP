package net.minecraft.src;

import java.util.Random;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float field_40107_a;
    private double field_40109_aw;
    private double field_40108_ax;
    private double field_40106_ay;

    public EntityEnchantmentTableParticleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX = par8;
        motionY = par10;
        motionZ = par12;
        field_40109_aw = posX = par2;
        field_40108_ax = posY = par4;
        field_40106_ay = posZ = par6;
        float f = rand.nextFloat() * 0.6F + 0.4F;
        field_40107_a = particleScale = rand.nextFloat() * 0.5F + 0.2F;
        particleRed = particleGreen = particleBlue = 1.0F * f;
        particleGreen *= 0.9F;
        particleRed *= 0.9F;
        particleMaxAge = (int)(Math.random() * 10D) + 30;
        noClip = true;
        setParticleTextureIndex((int)(Math.random() * 26D + 1.0D + 224D));
    }

    public int getBrightnessForRender(float par1)
    {
        int i = super.getBrightnessForRender(par1);
        float f = (float)particleAge / (float)particleMaxAge;
        f *= f;
        f *= f;
        int j = i & 0xff;
        int k = i >> 16 & 0xff;
        k += (int)(f * 15F * 16F);

        if (k > 240)
        {
            k = 240;
        }

        return j | k << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float f = super.getBrightness(par1);
        float f1 = (float)particleAge / (float)particleMaxAge;
        f1 *= f1;
        f1 *= f1;
        return f * (1.0F - f1) + f1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float f = (float)particleAge / (float)particleMaxAge;
        f = 1.0F - f;
        float f1 = 1.0F - f;
        f1 *= f1;
        f1 *= f1;
        posX = field_40109_aw + motionX * (double)f;
        posY = (field_40108_ax + motionY * (double)f) - (double)(f1 * 1.2F);
        posZ = field_40106_ay + motionZ * (double)f;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }
    }
}
