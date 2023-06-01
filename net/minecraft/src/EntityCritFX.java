package net.minecraft.src;

public class EntityCritFX extends EntityFX
{
    float field_35137_a;

    public EntityCritFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
    }

    public EntityCritFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        motionX *= 0.10000000149011612D;
        motionY *= 0.10000000149011612D;
        motionZ *= 0.10000000149011612D;
        motionX += par8 * 0.40000000000000002D;
        motionY += par10 * 0.40000000000000002D;
        motionZ += par12 * 0.40000000000000002D;
        particleRed = particleGreen = particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.60000002384185791D);
        particleScale *= 0.75F;
        particleScale *= par14;
        field_35137_a = particleScale;
        particleMaxAge = (int)(6D / (Math.random() * 0.80000000000000004D + 0.59999999999999998D));
        particleMaxAge *= par14;
        noClip = false;
        setParticleTextureIndex(65);
        onUpdate();
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

        particleScale = field_35137_a * f;
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
        particleGreen *= 0.95999999999999996D;
        particleBlue *= 0.90000000000000002D;
        motionX *= 0.69999998807907104D;
        motionY *= 0.69999998807907104D;
        motionZ *= 0.69999998807907104D;
        motionY -= 0.019999999552965164D;

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
