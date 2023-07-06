package net.minecraft.src;

public class EntitySplashFX extends EntityRainFX
{
    public EntitySplashFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6);
        particleGravity = 0.04F;
        setParticleTextureIndex(getParticleTextureIndex() + 1);

        if (par10 == 0.0D && (par8 != 0.0D || par12 != 0.0D))
        {
            motionX = par8;
            motionY = par10 + 0.10000000000000001D;
            motionZ = par12;
        }
    }
}
