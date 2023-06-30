package net.minecraft.src;

public class EntityNoteFX extends EntityFX
{
    float noteParticleScale;

    public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
    }

    public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        motionX *= 0.0099999997764825821D;
        motionY *= 0.0099999997764825821D;
        motionZ *= 0.0099999997764825821D;
        motionY += 0.20000000000000001D;
        particleRed = MathHelper.sin(((float)par8 + 0.0F) * (float)Math.PI * 2.0F) * 0.65F + 0.35F;
        particleGreen = MathHelper.sin(((float)par8 + 0.3333333F) * (float)Math.PI * 2.0F) * 0.65F + 0.35F;
        particleBlue = MathHelper.sin(((float)par8 + 0.6666667F) * (float)Math.PI * 2.0F) * 0.65F + 0.35F;
        particleScale *= 0.75F;
        particleScale *= par14;
        noteParticleScale = particleScale;
        particleMaxAge = 6;
        noClip = false;
        setParticleTextureIndex(64);
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

        particleScale = noteParticleScale * f;
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

        moveEntity(motionX, motionY, motionZ);

        if (posY == prevPosY)
        {
            motionX *= 1.1000000000000001D;
            motionZ *= 1.1000000000000001D;
        }

        motionX *= 0.6600000262260437D;
        motionY *= 0.6600000262260437D;
        motionZ *= 0.6600000262260437D;

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
