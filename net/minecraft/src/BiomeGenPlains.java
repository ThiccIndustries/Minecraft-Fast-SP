package net.minecraft.src;

public class BiomeGenPlains extends BiomeGenBase
{
    protected BiomeGenPlains(int par1)
    {
        super(par1);
        biomeDecorator.treesPerChunk = -999;
        biomeDecorator.flowersPerChunk = 4;
        biomeDecorator.grassPerChunk = 10;
    }
}
