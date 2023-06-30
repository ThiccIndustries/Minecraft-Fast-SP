package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider
{
    /** RNG. */
    private Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen1;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen2;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen3;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen4;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen5;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    /** Reference to the World object. */
    private World worldObj;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    private double noiseArray[];
    private double stoneNoise[];
    private MapGenBase caveGenerator;

    /** Holds Stronghold Generator */
    private MapGenStronghold strongholdGenerator;

    /** Holds Village Generator */
    private MapGenVillage villageGenerator;

    /** Holds Mineshaft Generator */
    private MapGenMineshaft mineshaftGenerator;

    /** Holds ravine generator */
    private MapGenBase ravineGenerator;
    private BiomeGenBase biomesForGeneration[];
    double noise3[];
    double noise1[];
    double noise2[];
    double noise5[];
    double noise6[];
    float field_35388_l[];
    int field_914_i[][];

    public ChunkProviderGenerate(World par1World, long par2, boolean par4)
    {
        stoneNoise = new double[256];
        caveGenerator = new MapGenCaves();
        strongholdGenerator = new MapGenStronghold();
        villageGenerator = new MapGenVillage(0);
        mineshaftGenerator = new MapGenMineshaft();
        ravineGenerator = new MapGenRavine();
        field_914_i = new int[32][32];
        worldObj = par1World;
        mapFeaturesEnabled = par4;
        rand = new Random(par2);
        noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
        noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
    }

    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void generateTerrain(int par1, int par2, byte par3ArrayOfByte[])
    {
    	
        byte byte0 = 4;
        byte byte1 = 16;
        byte byte2 = 63;
        int i = byte0 + 1;
        byte byte3 = 17;
        int j = byte0 + 1;
        biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, i + 5, j + 5);
        noiseArray = initializeNoiseField(noiseArray, par1 * byte0, 0, par2 * byte0, i, byte3, j);

        for (int k = 0; k < byte0; k++)
        {
            for (int l = 0; l < byte0; l++)
            {
                for (int i1 = 0; i1 < byte1; i1++)
                {
                    double d = 0.125D;
                    double d1 = noiseArray[((k + 0) * j + (l + 0)) * byte3 + (i1 + 0)];
                    double d2 = noiseArray[((k + 0) * j + (l + 1)) * byte3 + (i1 + 0)];
                    double d3 = noiseArray[((k + 1) * j + (l + 0)) * byte3 + (i1 + 0)];
                    double d4 = noiseArray[((k + 1) * j + (l + 1)) * byte3 + (i1 + 0)];
                    double d5 = (noiseArray[((k + 0) * j + (l + 0)) * byte3 + (i1 + 1)] - d1) * d;
                    double d6 = (noiseArray[((k + 0) * j + (l + 1)) * byte3 + (i1 + 1)] - d2) * d;
                    double d7 = (noiseArray[((k + 1) * j + (l + 0)) * byte3 + (i1 + 1)] - d3) * d;
                    double d8 = (noiseArray[((k + 1) * j + (l + 1)) * byte3 + (i1 + 1)] - d4) * d;

                    for (int j1 = 0; j1 < 8; j1++)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int k1 = 0; k1 < 4; k1++)
                        {
                            int l1 = k1 + k * 4 << 11 | 0 + l * 4 << 7 | i1 * 8 + j1;
                            char c = '\200';
                            l1 -= c;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            d15 -= d16;

                            for (int i2 = 0; i2 < 4; i2++)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    par3ArrayOfByte[l1 += c] = (byte)Block.stone.blockID;
                                    continue;
                                }

                                if (i1 * 8 + j1 < byte2)
                                {
                                    par3ArrayOfByte[l1 += c] = (byte)Block.waterStill.blockID;
                                }
                                else
                                {
                                    par3ArrayOfByte[l1 += c] = 0;
                                }
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

    /**
     * Replaces the stone that was placed in with blocks that match the biome
     */
    public void replaceBlocksForBiome(int par1, int par2, byte par3ArrayOfByte[], BiomeGenBase par4ArrayOfBiomeGenBase[])
    {
        byte byte0 = 63;
        double d = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);

        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[j + i * 16];
                float f = biomegenbase.getFloatTemperature();
                int k = (int)(stoneNoise[i + j * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
                int l = -1;
                byte byte1 = biomegenbase.topBlock;
                byte byte2 = biomegenbase.fillerBlock;

                for (int i1 = 127; i1 >= 0; i1--)
                {
                    int j1 = (j * 16 + i) * 128 + i1;

                    if (i1 <= 0 + rand.nextInt(5))
                    {
                        par3ArrayOfByte[j1] = (byte)Block.bedrock.blockID;
                        continue;
                    }

                    byte byte3 = par3ArrayOfByte[j1];

                    if (byte3 == 0)
                    {
                        l = -1;
                        continue;
                    }

                    if (byte3 != Block.stone.blockID)
                    {
                        continue;
                    }

                    if (l == -1)
                    {
                        if (k <= 0)
                        {
                            byte1 = 0;
                            byte2 = (byte)Block.stone.blockID;
                        }
                        else if (i1 >= byte0 - 4 && i1 <= byte0 + 1)
                        {
                            byte1 = biomegenbase.topBlock;
                            byte2 = biomegenbase.fillerBlock;
                        }

                        if (i1 < byte0 && byte1 == 0)
                        {
                            if (f < 0.15F)
                            {
                                byte1 = (byte)Block.ice.blockID;
                            }
                            else
                            {
                                byte1 = (byte)Block.waterStill.blockID;
                            }
                        }

                        l = k;

                        if (i1 >= byte0 - 1)
                        {
                            par3ArrayOfByte[j1] = byte1;
                        }
                        else
                        {
                            par3ArrayOfByte[j1] = byte2;
                        }

                        continue;
                    }

                    if (l <= 0)
                    {
                        continue;
                    }

                    l--;
                    par3ArrayOfByte[j1] = byte2;

                    if (l == 0 && byte2 == Block.sand.blockID)
                    {
                        l = rand.nextInt(4);
                        byte2 = (byte)Block.sandStone.blockID;
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
    
    //TODO: user size
    public boolean chunkInRange(int par1, int par2){
    	int worldSize = worldObj.getWorldInfo().getWorldSize();
    	return par1 >= -worldSize && par1 <= worldSize && par2 >= -worldSize && par2 <= worldSize;
    }
    
    //TODO: todo
    public void preloadChunk(int par1, int par2){
    	
    }
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {

        rand.setSeed((long)par1 * 0x4f9939f508L + (long)par2 * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        generateTerrain(par1, par2, abyte0);
        biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        replaceBlocksForBiome(par1, par2, abyte0, biomesForGeneration);
        caveGenerator.generate(this, worldObj, par1, par2, abyte0);
        ravineGenerator.generate(this, worldObj, par1, par2, abyte0);

        if (mapFeaturesEnabled)
        {
            mineshaftGenerator.generate(this, worldObj, par1, par2, abyte0);
            villageGenerator.generate(this, worldObj, par1, par2, abyte0);
            strongholdGenerator.generate(this, worldObj, par1, par2, abyte0);
        }

        Chunk chunk = new Chunk(worldObj, abyte0, par1, par2);
        byte abyte1[] = chunk.getBiomeArray();

        for (int i = 0; i < abyte1.length; i++)
        {
            abyte1[i] = (byte)biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double par1ArrayOfDouble[], int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (field_35388_l == null)
        {
            field_35388_l = new float[25];

            for (int i = -2; i <= 2; i++)
            {
                for (int j = -2; j <= 2; j++)
                {
                    float f = 10F / MathHelper.sqrt_float((float)(i * i + j * j) + 0.2F);
                    field_35388_l[i + 2 + (j + 2) * 5] = f;
                }
            }
        }

        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        noise5 = noiseGen5.generateNoiseOctaves(noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        noise6 = noiseGen6.generateNoiseOctaves(noise6, par2, par4, par5, par7, 200D, 200D, 0.5D);
        noise3 = noiseGen3.generateNoiseOctaves(noise3, par2, par3, par4, par5, par6, par7, d / 80D, d1 / 160D, d / 80D);
        noise1 = noiseGen1.generateNoiseOctaves(noise1, par2, par3, par4, par5, par6, par7, d, d1, d);
        noise2 = noiseGen2.generateNoiseOctaves(noise2, par2, par3, par4, par5, par6, par7, d, d1, d);
        par2 = par4 = 0;
        int k = 0;
        int l = 0;

        for (int i1 = 0; i1 < par5; i1++)
        {
            for (int j1 = 0; j1 < par7; j1++)
            {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte byte0 = 2;
                BiomeGenBase biomegenbase = biomesForGeneration[i1 + 2 + (j1 + 2) * (par5 + 5)];

                for (int k1 = -byte0; k1 <= byte0; k1++)
                {
                    for (int l1 = -byte0; l1 <= byte0; l1++)
                    {
                        BiomeGenBase biomegenbase1 = biomesForGeneration[i1 + k1 + 2 + (j1 + l1 + 2) * (par5 + 5)];
                        float f4 = field_35388_l[k1 + 2 + (l1 + 2) * 5] / (biomegenbase1.minHeight + 2.0F);

                        if (biomegenbase1.minHeight > biomegenbase.minHeight)
                        {
                            f4 /= 2.0F;
                        }

                        f1 += biomegenbase1.maxHeight * f4;
                        f2 += biomegenbase1.minHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4F - 1.0F) / 8F;
                double d2 = noise6[l] / 8000D;

                if (d2 < 0.0D)
                {
                    d2 = -d2 * 0.29999999999999999D;
                }

                d2 = d2 * 3D - 2D;

                if (d2 < 0.0D)
                {
                    d2 /= 2D;

                    if (d2 < -1D)
                    {
                        d2 = -1D;
                    }

                    d2 /= 1.3999999999999999D;
                    d2 /= 2D;
                }
                else
                {
                    if (d2 > 1.0D)
                    {
                        d2 = 1.0D;
                    }

                    d2 /= 8D;
                }

                l++;

                for (int i2 = 0; i2 < par6; i2++)
                {
                    double d3 = f2;
                    double d4 = f1;
                    d3 += d2 * 0.20000000000000001D;
                    d3 = (d3 * (double)par6) / 16D;
                    double d5 = (double)par6 / 2D + d3 * 4D;
                    double d6 = 0.0D;
                    double d7 = (((double)i2 - d5) * 12D * 128D) / 128D / d4;

                    if (d7 < 0.0D)
                    {
                        d7 *= 4D;
                    }

                    double d8 = noise1[k] / 512D;
                    double d9 = noise2[k] / 512D;
                    double d10 = (noise3[k] / 10D + 1.0D) / 2D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (i2 > par6 - 4)
                    {
                        double d11 = (float)(i2 - (par6 - 4)) / 3F;
                        d6 = d6 * (1.0D - d11) + -10D * d11;
                    }

                    par1ArrayOfDouble[k] = d6;
                    k++;
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
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(i + 16, j + 16);
        rand.setSeed(worldObj.getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long)par2 * l + (long)par3 * l1 ^ worldObj.getSeed());
        boolean flag = false;

        if (mapFeaturesEnabled)
        {
            mineshaftGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
            flag = villageGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
            strongholdGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
        }

        if (!flag && rand.nextInt(4) == 0)
        {
            int k = i + rand.nextInt(16) + 8;
            int i2 = rand.nextInt(128);
            int i3 = j + rand.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate(worldObj, rand, k, i2, i3);
        }

        if (!flag && rand.nextInt(8) == 0)
        {
            int i1 = i + rand.nextInt(16) + 8;
            int j2 = rand.nextInt(rand.nextInt(120) + 8);
            int j3 = j + rand.nextInt(16) + 8;

            if (j2 < 63 || rand.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaStill.blockID)).generate(worldObj, rand, i1, j2, j3);
            }
        }

        for (int j1 = 0; j1 < 8; j1++)
        {
            int k2 = i + rand.nextInt(16) + 8;
            int k3 = rand.nextInt(128);
            int i4 = j + rand.nextInt(16) + 8;

            if (!(new WorldGenDungeons()).generate(worldObj, rand, k2, k3, i4));
        }

        biomegenbase.decorate(worldObj, rand, i, j);
        SpawnerAnimals.performWorldGenSpawning(worldObj, biomegenbase, i + 8, j + 8, 16, 16, rand);
        i += 8;
        j += 8;

        for (int k1 = 0; k1 < 16; k1++)
        {
            for (int l2 = 0; l2 < 16; l2++)
            {
                int l3 = worldObj.getPrecipitationHeight(i + k1, j + l2);

                if (worldObj.isBlockHydratedDirectly(k1 + i, l3 - 1, l2 + j))
                {
                    worldObj.setBlockWithNotify(k1 + i, l3 - 1, l2 + j, Block.ice.blockID);
                }

                if (worldObj.canSnowAt(k1 + i, l3, l2 + j))
                {
                    worldObj.setBlockWithNotify(k1 + i, l3, l2 + j, Block.snow.blockID);
                }
            }
        }

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
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(par2, par4);

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
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        if ("Stronghold".equals(par2Str) && strongholdGenerator != null)
        {
            return strongholdGenerator.getNearestInstance(par1World, par3, par4, par5);
        }
        else
        {
            return null;
        }
    }
}
