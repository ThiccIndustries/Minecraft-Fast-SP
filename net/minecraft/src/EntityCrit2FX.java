package net.minecraft.src;

import java.util.Random;

public class EntityCrit2FX extends EntityFX
{
    private Entity field_35134_a;
    private int currentLife;
    private int maximumLife;
    private String particleName;

    public EntityCrit2FX(World par1World, Entity par2Entity)
    {
        this(par1World, par2Entity, "crit");
    }

    public EntityCrit2FX(World par1World, Entity par2Entity, String par3Str)
    {
        super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + (double)(par2Entity.height / 2.0F), par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        currentLife = 0;
        maximumLife = 0;
        field_35134_a = par2Entity;
        maximumLife = 3;
        particleName = par3Str;
        onUpdate();
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        for (int i = 0; i < 16; i++)
        {
            double d = rand.nextFloat() * 2.0F - 1.0F;
            double d1 = rand.nextFloat() * 2.0F - 1.0F;
            double d2 = rand.nextFloat() * 2.0F - 1.0F;

            if (d * d + d1 * d1 + d2 * d2 <= 1.0D)
            {
                double d3 = field_35134_a.posX + (d * (double)field_35134_a.width) / 4D;
                double d4 = field_35134_a.boundingBox.minY + (double)(field_35134_a.height / 2.0F) + (d1 * (double)field_35134_a.height) / 4D;
                double d5 = field_35134_a.posZ + (d2 * (double)field_35134_a.width) / 4D;
                worldObj.spawnParticle(particleName, d3, d4, d5, d, d1 + 0.20000000000000001D, d2);
            }
        }

        currentLife++;

        if (currentLife >= maximumLife)
        {
            setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
