package net.minecraft.src;

import java.util.List;

public class BiomeGenEnd extends BiomeGenBase
{
    public BiomeGenEnd(int par1)
    {
        super(par1);
        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(net.minecraft.src.EntityEnderman.class, 10, 4, 4));
        topBlock = (byte)Block.dirt.blockID;
        fillerBlock = (byte)Block.dirt.blockID;
        biomeDecorator = new BiomeEndDecorator(this);
    }

    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float par1)
    {
        return 0;
    }
}
