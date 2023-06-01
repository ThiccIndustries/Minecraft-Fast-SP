package net.minecraft.src;

public class EntitySnowShovelFX extends EntityFX
{
    float snowDigParticleScale;

    public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
    }

    public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX *= 0.10000000149011612D;
        motionY *= 0.10000000149011612D;
        motionZ *= 0.10000000149011612D;
        motionX += par8;
        motionY += par10;
        motionZ += par12;
        particleRed = particleGreen = particleBlue = 1.0F - (float)(Math.random() * 0.30000001192092896D);
        particleScale *= 0.75F;
        particleScale *= par14;
        snowDigParticleScale = particleScale;
        particleMaxAge = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
        particleMaxAge *= par14;
        noClip = false;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float f = (((float)particleAge + par2) / (float)particleMaxAge) * 32F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        particleScale = snowDigParticleScale * f;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
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

        setParticleTextureIndex(7 - (particleAge * 8) / particleMaxAge);
        motionY -= 0.029999999999999999D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.99000000953674316D;
        motionY *= 0.99000000953674316D;
        motionZ *= 0.99000000953674316D;

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
