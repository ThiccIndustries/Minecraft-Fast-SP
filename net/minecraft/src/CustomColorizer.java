package net.minecraft.src;

import java.io.IOException;
import java.util.*;
import net.minecraft.client.Minecraft;

public class CustomColorizer
{
    private static int grassColors[] = null;
    private static int waterColors[] = null;
    private static int foliageColors[] = null;
    private static int foliagePineColors[] = null;
    private static int foliageBirchColors[] = null;
    private static int swampFoliageColors[] = null;
    private static int swampGrassColors[] = null;
    private static int blockPalettes[][] = (int[][])null;
    private static int paletteColors[][] = (int[][])null;
    private static int skyColors[] = null;
    private static int fogColors[] = null;
    private static int underwaterColors[] = null;
    private static float lightMapsColorsRgb[][][] = (float[][][])null;
    private static float sunRgbs[][] = new float[16][3];
    private static float torchRgbs[][] = new float[16][3];
    private static int redstoneColors[] = null;
    private static int stemColors[] = null;
    private static int myceliumParticleColors[] = null;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static Vec3D fogColorNether = null;
    private static Vec3D fogColorEnd = null;
    private static Vec3D skyColorEnd = null;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random = new Random();

    public CustomColorizer()
    {
    }

    public static void update(RenderEngine renderengine)
    {
        grassColors = null;
        waterColors = null;
        foliageColors = null;
        foliageBirchColors = null;
        foliagePineColors = null;
        swampGrassColors = null;
        swampFoliageColors = null;
        skyColors = null;
        fogColors = null;
        underwaterColors = null;
        redstoneColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapsColorsRgb = (float[][][])null;
        lilyPadColor = -1;
        particleWaterColor = -1;
        particlePortalColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        blockPalettes = (int[][])null;
        paletteColors = (int[][])null;
        grassColors = getCustomColors("/misc/grasscolor.png", renderengine, 0x10000);
        foliageColors = getCustomColors("/misc/foliagecolor.png", renderengine, 0x10000);
        waterColors = getCustomColors("/misc/watercolorX.png", renderengine, 0x10000);

        if (!Config.isCustomColors())
        {
            return;
        }

        foliagePineColors = getCustomColors("/misc/pinecolor.png", renderengine, 0x10000);
        foliageBirchColors = getCustomColors("/misc/birchcolor.png", renderengine, 0x10000);
        swampGrassColors = getCustomColors("/misc/swampgrasscolor.png", renderengine, 0x10000);
        swampFoliageColors = getCustomColors("/misc/swampfoliagecolor.png", renderengine, 0x10000);
        skyColors = getCustomColors("/misc/skycolor0.png", renderengine, 0x10000);
        fogColors = getCustomColors("/misc/fogcolor0.png", renderengine, 0x10000);
        underwaterColors = getCustomColors("/misc/underwatercolor.png", renderengine, 0x10000);
        redstoneColors = getCustomColors("/misc/redstonecolor.png", renderengine, 16);
        stemColors = getCustomColors("/misc/stemcolor.png", renderengine, 8);
        myceliumParticleColors = getCustomColors("/misc/myceliumparticlecolor.png", renderengine, -1);
        int ai[][] = new int[3][];
        lightMapsColorsRgb = new float[3][][];

        for (int i = 0; i < ai.length; i++)
        {
            ai[i] = getCustomColors((new StringBuilder()).append("/environment/lightmap").append(i - 1).append(".png").toString(), renderengine, -1);

            if (ai[i] != null)
            {
                lightMapsColorsRgb[i] = toRgb(ai[i]);
            }
        }

        readColorProperties("/color.properties", renderengine);
    }

    private static float[][] toRgb(int ai[])
    {
        float af[][] = new float[ai.length][3];

        for (int i = 0; i < ai.length; i++)
        {
            int j = ai[i];
            float f = (float)(j >> 16 & 0xff) / 255F;
            float f1 = (float)(j >> 8 & 0xff) / 255F;
            float f2 = (float)(j & 0xff) / 255F;
            float af1[] = af[i];
            af1[0] = f;
            af1[1] = f1;
            af1[2] = f2;
        }

        return af;
    }

    private static void readColorProperties(String s, RenderEngine renderengine)
    {
        java.io.InputStream inputstream = renderengine.getTexturePack().selectedTexturePack.getResourceAsStream(s);

        if (inputstream == null)
        {
            return;
        }

        try
        {
            Config.log((new StringBuilder()).append("Loading ").append(s).toString());
            Properties properties = new Properties();
            properties.load(inputstream);
            lilyPadColor = readColor(properties, "lilypad");
            particleWaterColor = readColor(properties, new String[]
                    {
                        "particle.water", "drop.water"
                    });
            particlePortalColor = readColor(properties, "particle.portal");
            fogColorNether = readColorVec3D(properties, "fog.nether");
            fogColorEnd = readColorVec3D(properties, "fog.end");
            skyColorEnd = readColorVec3D(properties, "sky.end");
            readCustomPalettes(properties, renderengine);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private static void readCustomPalettes(Properties properties, RenderEngine renderengine)
    {
        blockPalettes = new int[256][1];

        for (int i = 0; i < 256; i++)
        {
            blockPalettes[i][0] = -1;
        }

        String s = "palette.block.";
        HashMap hashmap = new HashMap();
        Set set = properties.keySet();
        Iterator iterator = set.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s1 = (String)iterator.next();
            String s2 = properties.getProperty(s1);

            if (s1.startsWith(s))
            {
                hashmap.put(s1, s2);
            }
        }
        while (true);

        String as[] = (String[])hashmap.keySet().toArray(new String[hashmap.size()]);
        paletteColors = new int[as.length][];

        for (int j = 0; j < as.length; j++)
        {
            String s3 = as[j];
            String s4 = properties.getProperty(s3);
            Config.log((new StringBuilder()).append("Block palette: ").append(s3).append(" = ").append(s4).toString());
            String s5 = s3.substring(s.length());
            int ai[] = getCustomColors(s5, renderengine, 0x10000);
            paletteColors[j] = ai;
            String as1[] = Config.tokenize(s4, " ,;");

            for (int k = 0; k < as1.length; k++)
            {
                String s6 = as1[k];
                int l = -1;

                if (s6.contains(":"))
                {
                    String as2[] = Config.tokenize(s6, ":");
                    s6 = as2[0];
                    String s7 = as2[1];
                    l = Config.parseInt(s7, -1);

                    if (l < 0 || l > 15)
                    {
                        Config.log((new StringBuilder()).append("Invalid block metadata: ").append(s6).append(" in palette: ").append(s3).toString());
                        continue;
                    }
                }

                int i1 = Config.parseInt(s6, -1);

                if (i1 < 0 || i1 > 255)
                {
                    Config.log((new StringBuilder()).append("Invalid block index: ").append(i1).append(" in palette: ").append(s3).toString());
                    continue;
                }

                if (i1 == Block.grass.blockID || i1 == Block.tallGrass.blockID || i1 == Block.leaves.blockID || i1 == Block.vine.blockID)
                {
                    continue;
                }

                if (l == -1)
                {
                    blockPalettes[i1][0] = j;
                    continue;
                }

                if (blockPalettes[i1].length < 16)
                {
                    blockPalettes[i1] = new int[16];
                    Arrays.fill(blockPalettes[i1], -1);
                }

                blockPalettes[i1][l] = j;
            }
        }
    }

    private static int readColor(Properties properties, String as[])
    {
        for (int i = 0; i < as.length; i++)
        {
            String s = as[i];
            int j = readColor(properties, s);

            if (j >= 0)
            {
                return j;
            }
        }

        return -1;
    }

    private static int readColor(Properties properties, String s)
    {
        String s1 = properties.getProperty(s);

        if (s1 == null)
        {
            return -1;
        }

        try
        {
            int i = Integer.parseInt(s1, 16) & 0xffffff;
            Config.log((new StringBuilder()).append("Custom color: ").append(s).append(" = ").append(s1).toString());
            return i;
        }
        catch (NumberFormatException numberformatexception)
        {
            Config.log((new StringBuilder()).append("Invalid custom color: ").append(s).append(" = ").append(s1).toString());
        }

        return -1;
    }

    private static Vec3D readColorVec3D(Properties properties, String s)
    {
        int i = readColor(properties, s);

        if (i < 0)
        {
            return null;
        }
        else
        {
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            return Vec3D.createVectorHelper(f, f1, f2);
        }
    }

    private static int[] getCustomColors(String s, RenderEngine renderengine, int i)
    {
        java.io.InputStream inputstream = renderengine.getTexturePack().selectedTexturePack.getResourceAsStream(s);

        if (inputstream == null)
        {
            return null;
        }

        int ai[] = renderengine.getTextureContents(s);

        if (ai == null)
        {
            return null;
        }

        if (i > 0 && ai.length != i)
        {
            Config.log((new StringBuilder()).append("Invalid custom colors length: ").append(ai.length).append(", path: ").append(s).toString());
            return null;
        }
        else
        {
            Config.log((new StringBuilder()).append("Loading custom colors: ").append(s).toString());
            return ai;
        }
    }

    public static int getColorMultiplier(Block block, IBlockAccess iblockaccess, int i, int j, int k)
    {
        if (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes())
        {
            return block.colorMultiplier(iblockaccess, i, j, k);
        }

        int ai[] = null;
        int ai1[] = null;

        if (blockPalettes != null)
        {
            int l = block.blockID;

            if (l >= 0 && l < 256)
            {
                int ai2[] = blockPalettes[l];
                int i1 = -1;

                if (ai2.length > 1)
                {
                    int j1 = iblockaccess.getBlockMetadata(i, j, k);
                    i1 = ai2[j1];
                }
                else
                {
                    i1 = ai2[0];
                }

                if (i1 >= 0)
                {
                    ai = paletteColors[i1];
                }
            }

            if (ai != null)
            {
                if (Config.isSmoothBiomes())
                {
                    return getSmoothColorMultiplier(block, iblockaccess, i, j, k, ai, ai1, 0, 0);
                }
                else
                {
                    return getCustomColor(ai, iblockaccess, i, j, k);
                }
            }
        }

        boolean flag = Config.isSwampColors();
        boolean flag1 = false;
        byte byte0 = 0;
        int k1 = 0;

        if (block == Block.grass || block == Block.tallGrass)
        {
            byte0 = 1;
            flag1 = Config.isSmoothBiomes();
            ai = grassColors;

            if (flag)
            {
                ai1 = swampGrassColors;
            }
            else
            {
                ai1 = ai;
            }
        }
        else if (block == Block.leaves)
        {
            byte0 = 2;
            flag1 = Config.isSmoothBiomes();
            k1 = iblockaccess.getBlockMetadata(i, j, k);

            if ((k1 & 3) == 1)
            {
                ai = foliagePineColors;
            }
            else if ((k1 & 3) == 2)
            {
                ai = foliageBirchColors;
            }
            else
            {
                ai = foliageColors;

                if (flag)
                {
                    ai1 = swampFoliageColors;
                }
                else
                {
                    ai1 = ai;
                }
            }
        }
        else if (block == Block.vine)
        {
            byte0 = 2;
            flag1 = Config.isSmoothBiomes();
            ai = foliageColors;

            if (flag)
            {
                ai1 = swampFoliageColors;
            }
            else
            {
                ai1 = ai;
            }
        }

        if (flag1)
        {
            return getSmoothColorMultiplier(block, iblockaccess, i, j, k, ai, ai1, byte0, k1);
        }

        if (ai1 != ai && iblockaccess.getBiomeGenForCoords(i, k) == BiomeGenBase.swampland)
        {
            ai = ai1;
        }

        if (ai != null)
        {
            return getCustomColor(ai, iblockaccess, i, j, k);
        }
        else
        {
            return block.colorMultiplier(iblockaccess, i, j, k);
        }
    }

    private static int getSmoothColorMultiplier(Block block, IBlockAccess iblockaccess, int i, int j, int k, int ai[], int ai1[], int l, int i1)
    {
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;

        for (int i2 = i - 1; i2 <= i + 1; i2++)
        {
            for (int k2 = k - 1; k2 <= k + 1; k2++)
            {
                int ai2[] = ai;

                if (ai1 != ai2 && iblockaccess.getBiomeGenForCoords(i2, k2) == BiomeGenBase.swampland)
                {
                    ai2 = ai1;
                }

                int j3 = 0;

                if (ai2 == null)
                {
                    switch (l)
                    {
                        case 1:
                            j3 = iblockaccess.getBiomeGenForCoords(i2, k2).getBiomeGrassColor();
                            break;

                        case 2:
                            if ((i1 & 3) == 1)
                            {
                                j3 = ColorizerFoliage.getFoliageColorPine();
                            }
                            else if ((i1 & 3) == 2)
                            {
                                j3 = ColorizerFoliage.getFoliageColorBirch();
                            }
                            else
                            {
                                j3 = iblockaccess.getBiomeGenForCoords(i2, k2).getBiomeFoliageColor();
                            }

                            break;

                        default:
                            j3 = block.colorMultiplier(iblockaccess, i2, j, k2);
                            break;
                    }
                }
                else
                {
                    j3 = getCustomColor(ai2, iblockaccess, i2, j, k2);
                }

                j1 += j3 >> 16 & 0xff;
                k1 += j3 >> 8 & 0xff;
                l1 += j3 & 0xff;
            }
        }

        int j2 = j1 / 9;
        int l2 = k1 / 9;
        int i3 = l1 / 9;
        return j2 << 16 | l2 << 8 | i3;
    }

    public static int getFluidColor(Block block, IBlockAccess iblockaccess, int i, int j, int k)
    {
        if (block.blockMaterial != Material.water)
        {
            return block.colorMultiplier(iblockaccess, i, j, k);
        }

        if (waterColors != null)
        {
            if (Config.isSmoothBiomes())
            {
                return getSmoothColor(waterColors, iblockaccess, i, j, k, 3, 1);
            }
            else
            {
                return getCustomColor(waterColors, iblockaccess, i, j, k);
            }
        }

        if (!Config.isSwampColors())
        {
            return 0xffffff;
        }
        else
        {
            return block.colorMultiplier(iblockaccess, i, j, k);
        }
    }

    private static int getCustomColor(int ai[], IBlockAccess iblockaccess, int i, int j, int k)
    {
        BiomeGenBase biomegenbase = iblockaccess.getBiomeGenForCoords(i, k);
        double d = MathHelper.clamp_float(biomegenbase.getFloatTemperature(), 0.0F, 1.0F);
        double d1 = MathHelper.clamp_float(biomegenbase.getFloatRainfall(), 0.0F, 1.0F);
        d1 *= d;
        int l = (int)((1.0D - d) * 255D);
        int i1 = (int)((1.0D - d1) * 255D);
        return ai[i1 << 8 | l] & 0xffffff;
    }

    public static void updatePortalFX(EntityFX entityfx)
    {
        if (particlePortalColor < 0)
        {
            return;
        }
        else
        {
            int i = particlePortalColor;
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            entityfx.particleRed = f;
            entityfx.particleGreen = f1;
            entityfx.particleBlue = f2;
            return;
        }
    }

    public static void updateMyceliumFX(EntityFX entityfx)
    {
        if (myceliumParticleColors == null)
        {
            return;
        }
        else
        {
            int i = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            entityfx.particleRed = f;
            entityfx.particleGreen = f1;
            entityfx.particleBlue = f2;
            return;
        }
    }

    public static void updateReddustFX(EntityFX entityfx, IBlockAccess iblockaccess, double d, double d1, double d2)
    {
        if (redstoneColors == null)
        {
            return;
        }

        int i = iblockaccess.getBlockMetadata((int)d, (int)d1, (int)d2);
        int j = getRedstoneColor(i);

        if (j == -1)
        {
            return;
        }
        else
        {
            int k = j >> 16 & 0xff;
            int l = j >> 8 & 0xff;
            int i1 = j & 0xff;
            float f = (float)k / 255F;
            float f1 = (float)l / 255F;
            float f2 = (float)i1 / 255F;
            entityfx.particleRed = f;
            entityfx.particleGreen = f1;
            entityfx.particleBlue = f2;
            return;
        }
    }

    public static int getRedstoneColor(int i)
    {
        if (redstoneColors == null)
        {
            return -1;
        }

        if (i < 0 || i > 15)
        {
            return -1;
        }
        else
        {
            return redstoneColors[i] & 0xffffff;
        }
    }

    public static void updateWaterFX(EntityFX entityfx, IBlockAccess iblockaccess)
    {
        if (waterColors == null)
        {
            return;
        }

        int i = (int)entityfx.posX;
        int j = (int)entityfx.posY;
        int k = (int)entityfx.posZ;
        int l = getFluidColor(Block.waterStill, iblockaccess, i, j, k);
        int i1 = l >> 16 & 0xff;
        int j1 = l >> 8 & 0xff;
        int k1 = l & 0xff;
        float f = (float)i1 / 255F;
        float f1 = (float)j1 / 255F;
        float f2 = (float)k1 / 255F;

        if (particleWaterColor >= 0)
        {
            int l1 = particleWaterColor >> 16 & 0xff;
            int i2 = particleWaterColor >> 8 & 0xff;
            int j2 = particleWaterColor & 0xff;
            f *= (float)l1 / 255F;
            f1 *= (float)i2 / 255F;
            f2 *= (float)j2 / 255F;
        }

        entityfx.particleRed = f;
        entityfx.particleGreen = f1;
        entityfx.particleBlue = f2;
    }

    public static int getLilypadColor()
    {
        if (lilyPadColor < 0)
        {
            return Block.waterlily.getBlockColor();
        }
        else
        {
            return lilyPadColor;
        }
    }

    public static Vec3D getFogColorNether(Vec3D vec3d)
    {
        if (fogColorNether == null)
        {
            return vec3d;
        }
        else
        {
            return fogColorNether;
        }
    }

    public static Vec3D getFogColorEnd(Vec3D vec3d)
    {
        if (fogColorEnd == null)
        {
            return vec3d;
        }
        else
        {
            return fogColorEnd;
        }
    }

    public static Vec3D getSkyColorEnd(Vec3D vec3d)
    {
        if (skyColorEnd == null)
        {
            return vec3d;
        }
        else
        {
            return skyColorEnd;
        }
    }

    public static Vec3D getSkyColor(Vec3D vec3d, IBlockAccess iblockaccess, double d, double d1, double d2)
    {
        if (skyColors == null)
        {
            return vec3d;
        }
        else
        {
            int i = getSmoothColor(skyColors, iblockaccess, d, d1, d2, 10, 1);
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            float f3 = (float)vec3d.xCoord / 0.5F;
            float f4 = (float)vec3d.yCoord / 0.66275F;
            float f5 = (float)vec3d.zCoord;
            f *= f3;
            f1 *= f4;
            f2 *= f5;
            return Vec3D.createVectorHelper(f, f1, f2);
        }
    }

    public static Vec3D getFogColor(Vec3D vec3d, IBlockAccess iblockaccess, double d, double d1, double d2)
    {
        if (fogColors == null)
        {
            return vec3d;
        }
        else
        {
            int i = getSmoothColor(fogColors, iblockaccess, d, d1, d2, 10, 1);
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            float f3 = (float)vec3d.xCoord / 0.753F;
            float f4 = (float)vec3d.yCoord / 0.8471F;
            float f5 = (float)vec3d.zCoord;
            f *= f3;
            f1 *= f4;
            f2 *= f5;
            return Vec3D.createVectorHelper(f, f1, f2);
        }
    }

    public static Vec3D getUnderwaterColor(IBlockAccess iblockaccess, double d, double d1, double d2)
    {
        if (underwaterColors == null)
        {
            return null;
        }
        else
        {
            int i = getSmoothColor(underwaterColors, iblockaccess, d, d1, d2, 10, 1);
            int j = i >> 16 & 0xff;
            int k = i >> 8 & 0xff;
            int l = i & 0xff;
            float f = (float)j / 255F;
            float f1 = (float)k / 255F;
            float f2 = (float)l / 255F;
            return Vec3D.createVectorHelper(f, f1, f2);
        }
    }

    public static int getSmoothColor(int ai[], IBlockAccess iblockaccess, double d, double d1, double d2, int i, int j)
    {
        if (ai == null)
        {
            return -1;
        }

        int k = (int)Math.floor(d);
        int l = (int)Math.floor(d1);
        int i1 = (int)Math.floor(d2);
        int j1 = (i * j) / 2;
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = k - j1; k2 <= k + j1; k2 += j)
        {
            for (int i3 = i1 - j1; i3 <= i1 + j1; i3 += j)
            {
                int k3 = getCustomColor(ai, iblockaccess, k2, l, i3);
                k1 += k3 >> 16 & 0xff;
                l1 += k3 >> 8 & 0xff;
                i2 += k3 & 0xff;
                j2++;
            }
        }

        int l2 = k1 / j2;
        int j3 = l1 / j2;
        int l3 = i2 / j2;
        return l2 << 16 | j3 << 8 | l3;
    }

    public static int mixColors(int i, int j, float f)
    {
        if (f <= 0.0F)
        {
            return j;
        }

        if (f >= 1.0F)
        {
            return i;
        }
        else
        {
            float f1 = 1.0F - f;
            int k = i >> 16 & 0xff;
            int l = i >> 8 & 0xff;
            int i1 = i & 0xff;
            int j1 = j >> 16 & 0xff;
            int k1 = j >> 8 & 0xff;
            int l1 = j & 0xff;
            int i2 = (int)((float)k * f + (float)j1 * f1);
            int j2 = (int)((float)l * f + (float)k1 * f1);
            int k2 = (int)((float)i1 * f + (float)l1 * f1);
            return i2 << 16 | j2 << 8 | k2;
        }
    }

    private static int averageColor(int i, int j)
    {
        int k = i >> 16 & 0xff;
        int l = i >> 8 & 0xff;
        int i1 = i & 0xff;
        int j1 = j >> 16 & 0xff;
        int k1 = j >> 8 & 0xff;
        int l1 = j & 0xff;
        int i2 = (k + j1) / 2;
        int j2 = (l + k1) / 2;
        int k2 = (i1 + l1) / 2;
        return i2 << 16 | j2 << 8 | k2;
    }

    public static int getStemColorMultiplier(BlockStem blockstem, IBlockAccess iblockaccess, int i, int j, int k)
    {
        if (stemColors == null)
        {
            return blockstem.colorMultiplier(iblockaccess, i, j, k);
        }

        int l = iblockaccess.getBlockMetadata(i, j, k);

        if (l < 0)
        {
            l = 0;
        }

        if (l >= stemColors.length)
        {
            l = stemColors.length - 1;
        }

        return stemColors[l];
    }

    public static boolean updateLightmap(World world, EntityRenderer entityrenderer, int ai[])
    {
        if (world == null)
        {
            return false;
        }

        if (lightMapsColorsRgb == null)
        {
            return false;
        }

        if (!Config.isCustomColors())
        {
            return false;
        }

        int i = world.worldProvider.worldType;

        if (i < -1 || i > 1)
        {
            return false;
        }

        int j = i + 1;
        float af[][] = lightMapsColorsRgb[j];

        if (af == null)
        {
            return false;
        }

        byte byte0 = 32;
        int k = af.length / byte0;

        if (k < 16)
        {
            Config.dbg((new StringBuilder()).append("Invalid lightmap width: ").append(k).append(" for: /environment/lightmap").append(i).append(".png").toString());
            lightMapsColorsRgb[j] = (float[][])null;
            return false;
        }

        float f = 1.166667F * (world.func_35464_b(1.0F) - 0.2F);

        if (world.lightningFlash > 0)
        {
            f = 1.0F;
        }

        f = Config.limitTo1(f);
        float f1 = f * (float)(k - 1);
        float f2 = Config.limitTo1(entityrenderer.torchFlickerX + 0.5F) * (float)(k - 1);
        float f3 = Config.limitTo1(Config.getMinecraft().gameSettings.gammaSetting);
        boolean flag = f3 > 0.0001F;
        getLightMapColumn(af, f1, 0, k, sunRgbs);
        getLightMapColumn(af, f2, 16 * k, k, torchRgbs);
        float af1[] = new float[3];

        for (int l = 0; l < 16; l++)
        {
            for (int i1 = 0; i1 < 16; i1++)
            {
                for (int j1 = 0; j1 < 3; j1++)
                {
                    float f4 = Config.limitTo1(sunRgbs[l][j1] + torchRgbs[i1][j1]);

                    if (flag)
                    {
                        float f5 = 1.0F - f4;
                        f5 = 1.0F - f5 * f5 * f5 * f5;
                        f4 = f3 * f5 + (1.0F - f3) * f4;
                    }

                    af1[j1] = f4;
                }

                int k1 = (int)(af1[0] * 255F);
                int l1 = (int)(af1[1] * 255F);
                int i2 = (int)(af1[2] * 255F);
                ai[l * 16 + i1] = 0xff000000 | k1 << 16 | l1 << 8 | i2;
            }
        }

        return true;
    }

    private static void getLightMapColumn(float af[][], float f, int i, int j, float af1[][])
    {
        int k = (int)Math.floor(f);
        int l = (int)Math.ceil(f);

        if (k == l)
        {
            for (int i1 = 0; i1 < 16; i1++)
            {
                float af2[] = af[i + i1 * j + k];
                float af3[] = af1[i1];

                for (int k1 = 0; k1 < 3; k1++)
                {
                    af3[k1] = af2[k1];
                }
            }

            return;
        }

        float f1 = 1.0F - (f - (float)k);
        float f2 = 1.0F - ((float)l - f);

        for (int j1 = 0; j1 < 16; j1++)
        {
            float af4[] = af[i + j1 * j + k];
            float af5[] = af[i + j1 * j + l];
            float af6[] = af1[j1];

            for (int l1 = 0; l1 < 3; l1++)
            {
                af6[l1] = af4[l1] * f1 + af5[l1] * f2;
            }
        }
    }
}
