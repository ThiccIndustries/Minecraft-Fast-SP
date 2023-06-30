package net.minecraft.src;

import java.util.Random;

public class EntityFlameFX extends EntityFX
{
    /** the scale of the flame FX */
    private float flameScale;

    public EntityFlameFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX = motionX * 0.0099999997764825821D + par8;
        motionY = motionY * 0.0099999997764825821D + par10;
        motionZ = motionZ * 0.0099999997764825821D + par12;
        par2 += (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        par4 += (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        par6 += (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        flameScale = particleScale;
        particleRed = particleGreen = particleBlue = 1.0F;
        particleMaxAge = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D)) + 4;
        noClip = true;
        setParticleTextureIndex(48);
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float f = ((float)particleAge + par2) / (float)particleMaxAge;
        particleScale = flameScale * (1.0F - f * f * 0.5F);
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    public int getBrightnessForRender(float par1)
    {
        float f = ((float)particleAge + par1) / (float)particleMaxAge;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 0xff;
        int k = i >> 16 & 0xff;
        j += (int)(f * 15F * 16F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float f = ((float)particleAge + par1) / (float)particleMaxAge;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        float f1 = super.getBrightness(par1);
        return f1 * f + (1.0F - f);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.95999997854232788D;
        motionY *= 0.95999997854232788D;
        motionZ *= 0.95999997854232788D;

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
