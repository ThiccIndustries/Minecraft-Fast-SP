package net.minecraft.src;

import java.util.Random;

public class EntityExplodeFX extends EntityFX
{
    public EntityExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX = par8 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        motionY = par10 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        motionZ = par12 + (double)((float)(Math.random() * 2D - 1.0D) * 0.05F);
        particleRed = particleGreen = particleBlue = rand.nextFloat() * 0.3F + 0.7F;
        particleScale = rand.nextFloat() * rand.nextFloat() * 6F + 1.0F;
        particleMaxAge = (int)(16D / ((double)rand.nextFloat() * 0.80000000000000004D + 0.20000000000000001D)) + 2;
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
        motionY += 0.0040000000000000001D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.89999997615814209D;
        motionY *= 0.89999997615814209D;
        motionZ *= 0.89999997615814209D;

        if (onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }
    }
}
