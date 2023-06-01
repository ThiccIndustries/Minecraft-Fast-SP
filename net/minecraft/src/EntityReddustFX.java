package net.minecraft.src;

public class EntityReddustFX extends EntityFX
{
    float reddustParticleScale;

    public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
    {
        this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
    }

    public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        motionX *= 0.10000000149011612D;
        motionY *= 0.10000000149011612D;
        motionZ *= 0.10000000149011612D;

        if (par9 == 0.0F)
        {
            par9 = 1.0F;
        }

        float f = (float)Math.random() * 0.4F + 0.6F;
        particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * par9 * f;
        particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * par10 * f;
        particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * par11 * f;
        particleScale *= 0.75F;
        particleScale *= par8;
        reddustParticleScale = particleScale;
        particleMaxAge = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
        particleMaxAge *= par8;
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

        particleScale = reddustParticleScale * f;
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
        moveEntity(motionX, motionY, motionZ);

        if (posY == prevPosY)
        {
            motionX *= 1.1000000000000001D;
            motionZ *= 1.1000000000000001D;
        }

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
