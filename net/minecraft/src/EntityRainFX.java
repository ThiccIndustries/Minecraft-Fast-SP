package net.minecraft.src;

import java.util.Random;

public class EntityRainFX extends EntityFX
{
    public EntityRainFX(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        motionX *= 0.30000001192092896D;
        motionY = (float)Math.random() * 0.2F + 0.1F;
        motionZ *= 0.30000001192092896D;
        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;
        setParticleTextureIndex(19 + rand.nextInt(4));
        setSize(0.01F, 0.01F);
        particleGravity = 0.06F;
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
        motionY -= particleGravity;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;

        if (particleMaxAge-- <= 0)
        {
            setDead();
        }

        if (onGround)
        {
            if (Math.random() < 0.5D)
            {
                setDead();
            }

            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
        }

        Material material = worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));

        if (material.isLiquid() || material.isSolid())
        {
            double d = (float)(MathHelper.floor_double(posY) + 1) - BlockFluid.getFluidHeightPercent(worldObj.getBlockMetadata(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));

            if (posY < d)
            {
                setDead();
            }
        }
    }
}
