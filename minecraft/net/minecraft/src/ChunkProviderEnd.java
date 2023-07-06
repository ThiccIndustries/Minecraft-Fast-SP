package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderEnd implements IChunkProvider
{
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double densities[];
    private BiomeGenBase biomesForGeneration[];
    double noiseData1[];
    double noiseData2[];
    double noiseData3[];
    double noiseData4[];
    double noiseData5[];
    int field_40395_h[][];

    public ChunkProviderEnd(World par1World, long par2)
    {
        field_40395_h = new int[32][32];
        endWorld = par1World;
        endRNG = new Random(par2);
        noiseGen1 = new NoiseGeneratorOctaves(endRNG, 16);
        noiseGen2 = new NoiseGeneratorOctaves(endRNG, 16);
        noiseGen3 = new NoiseGeneratorOctaves(endRNG, 8);
        noiseGen4 = new NoiseGeneratorOctaves(endRNG, 10);
        noiseGen5 = new NoiseGeneratorOctaves(endRNG, 16);
    }

    public void func_40380_a(int par1, int par2, byte par3ArrayOfByte[], BiomeGenBase par4ArrayOfBiomeGenBase[])
    {
        byte byte0 = 2;
        int i = byte0 + 1;
        byte byte1 = 33;
        int j = byte0 + 1;
        densities = func_40379_a(densities, par1 * byte0, 0, par2 * byte0, i, byte1, j);

        for (int k = 0; k < byte0; k++)
        {
            for (int l = 0; l < byte0; l++)
            {
                for (int i1 = 0; i1 < 32; i1++)
                {
                    double d = 0.25D;
                    double d1 = densities[((k + 0) * j + (l + 0)) * byte1 + (i1 + 0)];
                    double d2 = densities[((k + 0) * j + (l + 1)) * byte1 + (i1 + 0)];
                    double d3 = densities[((k + 1) * j + (l + 0)) * byte1 + (i1 + 0)];
                    double d4 = densities[((k + 1) * j + (l + 1)) * byte1 + (i1 + 0)];
                    double d5 = (densities[((k + 0) * j + (l + 0)) * byte1 + (i1 + 1)] - d1) * d;
                    double d6 = (densities[((k + 0) * j + (l + 1)) * byte1 + (i1 + 1)] - d2) * d;
                    double d7 = (densities[((k + 1) * j + (l + 0)) * byte1 + (i1 + 1)] - d3) * d;
                    double d8 = (densities[((k + 1) * j + (l + 1)) * byte1 + (i1 + 1)] - d4) * d;

                    for (int j1 = 0; j1 < 4; j1++)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int k1 = 0; k1 < 8; k1++)
                        {
                            int l1 = k1 + k * 8 << 11 | 0 + l * 8 << 7 | i1 * 4 + j1;
                            char c = '\200';
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int i2 = 0; i2 < 8; i2++)
                            {
                                int j2 = 0;

                                if (d15 > 0.0D)
                                {
                                    j2 = Block.whiteStone.blockID;
                                }

                                par3ArrayOfByte[l1] = (byte)j2;
                                l1 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void func_40381_b(int par1, int par2, byte par3ArrayOfByte[], BiomeGenBase par4ArrayOfBiomeGenBase[])
    {
        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                int k = 1;
                int l = -1;
                byte byte0 = (byte)Block.whiteStone.blockID;
                byte byte1 = (byte)Block.whiteStone.blockID;

                for (int i1 = 127; i1 >= 0; i1--)
                {
                    int j1 = (j * 16 + i) * 128 + i1;
                    byte byte2 = par3ArrayOfByte[j1];

                    if (byte2 == 0)
                    {
                        l = -1;
                        continue;
                    }

                    if (byte2 != Block.stone.blockID)
                    {
                        continue;
                    }

                    if (l == -1)
                    {
                        if (k <= 0)
                        {
                            byte0 = 0;
                            byte1 = (byte)Block.whiteStone.blockID;
                        }

                        l = k;

                        if (i1 >= 0)
                        {
                            par3ArrayOfByte[j1] = byte0;
                        }
                        else
                        {
                            par3ArrayOfByte[j1] = byte1;
                        }

                        continue;
                    }

                    if (l > 0)
                    {
                        l--;
                        par3ArrayOfByte[j1] = byte1;
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return provideChunk(par1, par2);
    }
    
    //TODO: todo
    public void preloadChunk(int par1, int par2){
    	
    }
    
    //TODO: user size
    public boolean chunkInRange(int par1, int par2){
    	int worldSize = endWorld.getWorldInfo().getWorldSize();
    	return par1 >= -worldSize && par1 <= worldSize && par2 >= -worldSize && par2 <= worldSize;
    }
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        endRNG.setSeed((long)par1 * 0x4f9939f508L + (long)par2 * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        biomesForGeneration = endWorld.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        func_40380_a(par1, par2, abyte0, biomesForGeneration);
        func_40381_b(par1, par2, abyte0, biomesForGeneration);
        Chunk chunk = new Chunk(endWorld, abyte0, par1, par2);
        byte abyte1[] = chunk.getBiomeArray();

        for (int i = 0; i < abyte1.length; i++)
        {
            abyte1[i] = (byte)biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] func_40379_a(double par1ArrayOfDouble[], int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, par2, par4, par5, par7, 200D, 200D, 0.5D);
        d *= 2D;
        noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, par2, par3, par4, par5, par6, par7, d / 80D, d1 / 160D, d / 80D);
        noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, par2, par3, par4, par5, par6, par7, d, d1, d);
        noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, par2, par3, par4, par5, par6, par7, d, d1, d);
        int i = 0;
        int j = 0;

        for (int k = 0; k < par5; k++)
        {
            for (int l = 0; l < par7; l++)
            {
                double d2 = (noiseData4[j] + 256D) / 512D;

                if (d2 > 1.0D)
                {
                    d2 = 1.0D;
                }

                double d3 = noiseData5[j] / 8000D;

                if (d3 < 0.0D)
                {
                    d3 = -d3 * 0.29999999999999999D;
                }

                d3 = d3 * 3D - 2D;
                float f = (float)((k + par2) - 0) / 1.0F;
                float f1 = (float)((l + par4) - 0) / 1.0F;
                float f2 = 100F - MathHelper.sqrt_float(f * f + f1 * f1) * 8F;

                if (f2 > 80F)
                {
                    f2 = 80F;
                }

                if (f2 < -100F)
                {
                    f2 = -100F;
                }

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                d3 /= 8D;
                d3 = 0.0D;

                if (d2 < 0.0D)
                {
                    d2 = 0.0D;
                }

                d2 += 0.5D;
                d3 = (d3 * (double)par6) / 16D;
                j++;
                double d4 = (double)par6 / 2D;

                for (int i1 = 0; i1 < par6; i1++)
                {
                    double d5 = 0.0D;
                    double d6 = (((double)i1 - d4) * 8D) / d2;

                    if (d6 < 0.0D)
                    {
                        d6 *= -1D;
                    }

                    double d7 = noiseData2[i] / 512D;
                    double d8 = noiseData3[i] / 512D;
                    double d9 = (noiseData1[i] / 10D + 1.0D) / 2D;

                    if (d9 < 0.0D)
                    {
                        d5 = d7;
                    }
                    else if (d9 > 1.0D)
                    {
                        d5 = d8;
                    }
                    else
                    {
                        d5 = d7 + (d8 - d7) * d9;
                    }

                    d5 -= 8D;
                    d5 += f2;
                    int j1 = 2;

                    if (i1 > par6 / 2 - j1)
                    {
                        double d10 = (float)(i1 - (par6 / 2 - j1)) / 64F;

                        if (d10 < 0.0D)
                        {
                            d10 = 0.0D;
                        }

                        if (d10 > 1.0D)
                        {
                            d10 = 1.0D;
                        }

                        d5 = d5 * (1.0D - d10) + -3000D * d10;
                    }

                    j1 = 8;

                    if (i1 < j1)
                    {
                        double d11 = (float)(j1 - i1) / ((float)j1 - 1.0F);
                        d5 = d5 * (1.0D - d11) + -30D * d11;
                    }

                    par1ArrayOfDouble[i] = d5;
                    i++;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;
        int i = par2 * 16;
        int j = par3 * 16;
        BiomeGenBase biomegenbase = endWorld.getBiomeGenForCoords(i + 16, j + 16);
        biomegenbase.decorate(endWorld, endWorld.rand, i, j);
        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, boolean drop, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase biomegenbase = endWorld.getBiomeGenForCoords(par2, par4);

        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase.getSpawnableList(par1EnumCreatureType);
        }
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int i, int j)
    {
        return null;
    }
}
