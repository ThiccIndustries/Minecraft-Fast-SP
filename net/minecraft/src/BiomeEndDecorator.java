package net.minecraft.src;

import java.util.Random;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen;

    public BiomeEndDecorator(BiomeGenBase par1BiomeGenBase)
    {
        super(par1BiomeGenBase);
        spikeGen = new WorldGenSpikes(Block.whiteStone.blockID);
    }

    /**
     * The method that does the work of actually decorating chunks
     */
    protected void decorate()
    {
        generateOres();

        if (randomGenerator.nextInt(5) == 0)
        {
            int i = chunk_X + randomGenerator.nextInt(16) + 8;
            int j = chunk_Z + randomGenerator.nextInt(16) + 8;
            int k = currentWorld.getTopSolidOrLiquidBlock(i, j);

            if (k <= 0);

            spikeGen.generate(currentWorld, randomGenerator, i, k, j);
        }

        if (chunk_X == 0 && chunk_Z == 0)
        {
            EntityDragon entitydragon = new EntityDragon(currentWorld);
            entitydragon.setLocationAndAngles(0.0D, 128D, 0.0D, randomGenerator.nextFloat() * 360F, 0.0F);
            currentWorld.spawnEntityInWorld(entitydragon);
        }
    }
}
