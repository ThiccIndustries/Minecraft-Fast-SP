package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TerrainTextureManager
{
    private float texCols[];
    private int pixels[];
    private int zBuf[];
    private int waterBuf[];
    private int waterBr[];
    private int yBuf[];
    private int textures[];

    public TerrainTextureManager()
    {
        texCols = new float[768];
        pixels = new int[17408];
        zBuf = new int[17408];
        waterBuf = new int[17408];
        waterBr = new int[17408];
        yBuf = new int[34];
        textures = new int[768];

        try
        {
            BufferedImage bufferedimage = ImageIO.read((net.minecraft.src.TerrainTextureManager.class).getResource("/terrain.png"));
            int ai[] = new int[0x10000];
            bufferedimage.getRGB(0, 0, 256, 256, ai, 0, 256);

            for (int j = 0; j < 256; j++)
            {
                int k = 0;
                int l = 0;
                int i1 = 0;
                int j1 = (j % 16) * 16;
                int k1 = (j / 16) * 16;
                int l1 = 0;

                for (int i2 = 0; i2 < 16; i2++)
                {
                    for (int j2 = 0; j2 < 16; j2++)
                    {
                        int k2 = ai[j2 + j1 + (i2 + k1) * 256];
                        int l2 = k2 >> 24 & 0xff;

                        if (l2 > 128)
                        {
                            k += k2 >> 16 & 0xff;
                            l += k2 >> 8 & 0xff;
                            i1 += k2 & 0xff;
                            l1++;
                        }
                    }

                    if (l1 == 0)
                    {
                        l1++;
                    }

                    texCols[j * 3 + 0] = k / l1;
                    texCols[j * 3 + 1] = l / l1;
                    texCols[j * 3 + 2] = i1 / l1;
                }
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        for (int i = 0; i < 4096; i++)
        {
            if (Block.blocksList[i] != null)
            {
                textures[i * 3 + 0] = Block.blocksList[i].getBlockTextureFromSide(1);
                textures[i * 3 + 1] = Block.blocksList[i].getBlockTextureFromSide(2);
                textures[i * 3 + 2] = Block.blocksList[i].getBlockTextureFromSide(3);
            }
        }
    }

    public void render(IsoImageBuffer par1IsoImageBuffer)
    {
        World world = par1IsoImageBuffer.level;

        if (world == null)
        {
            par1IsoImageBuffer.noContent = true;
            par1IsoImageBuffer.rendered = true;
            return;
        }

        int i = par1IsoImageBuffer.x * 16;
        int j = par1IsoImageBuffer.y * 16;
        int k = i + 16;
        int l = j + 16;
        Chunk chunk = world.getChunkFromChunkCoords(par1IsoImageBuffer.x, par1IsoImageBuffer.y);

        if (chunk.isEmpty())
        {
            par1IsoImageBuffer.noContent = true;
            par1IsoImageBuffer.rendered = true;
            return;
        }

        par1IsoImageBuffer.noContent = false;
        Arrays.fill(zBuf, 0);
        Arrays.fill(waterBuf, 0);
        Arrays.fill(yBuf, 544);

        for (int i1 = l - 1; i1 >= j; i1--)
        {
            for (int j1 = k - 1; j1 >= i; j1--)
            {
                int k1 = j1 - i;
                int l1 = i1 - j;
                int i2 = k1 + l1;
                boolean flag = true;

                for (int j2 = 0; j2 < 128; j2++)
                {
                    int k2 = ((l1 - k1 - j2) + 544) - 16;

                    if (k2 >= yBuf[i2] && k2 >= yBuf[i2 + 1])
                    {
                        continue;
                    }

                    Block block = Block.blocksList[world.getBlockId(j1, j2, i1)];

                    if (block == null)
                    {
                        flag = false;
                        continue;
                    }

                    if (block.blockMaterial == Material.water)
                    {
                        int l2 = world.getBlockId(j1, j2 + 1, i1);

                        if (l2 != 0 && Block.blocksList[l2].blockMaterial == Material.water)
                        {
                            continue;
                        }

                        float f1 = ((float)j2 / 127F) * 0.6F + 0.4F;
                        float f2 = world.getLightBrightness(j1, j2 + 1, i1) * f1;

                        if (k2 < 0 || k2 >= 544)
                        {
                            continue;
                        }

                        int i4 = i2 + k2 * 32;

                        if (i2 >= 0 && i2 <= 32 && waterBuf[i4] <= j2)
                        {
                            waterBuf[i4] = j2;
                            waterBr[i4] = (int)(f2 * 127F);
                        }

                        if (i2 >= -1 && i2 <= 31 && waterBuf[i4 + 1] <= j2)
                        {
                            waterBuf[i4 + 1] = j2;
                            waterBr[i4 + 1] = (int)(f2 * 127F);
                        }

                        flag = false;
                        continue;
                    }

                    if (flag)
                    {
                        if (k2 < yBuf[i2])
                        {
                            yBuf[i2] = k2;
                        }

                        if (k2 < yBuf[i2 + 1])
                        {
                            yBuf[i2 + 1] = k2;
                        }
                    }

                    float f = ((float)j2 / 127F) * 0.6F + 0.4F;

                    if (k2 >= 0 && k2 < 544)
                    {
                        int i3 = i2 + k2 * 32;
                        int k3 = textures[block.blockID * 3 + 0];
                        float f3 = (world.getLightBrightness(j1, j2 + 1, i1) * 0.8F + 0.2F) * f;
                        int j4 = k3;

                        if (i2 >= 0)
                        {
                            float f5 = f3;

                            if (zBuf[i3] <= j2)
                            {
                                zBuf[i3] = j2;
                                pixels[i3] = 0xff000000 | (int)(texCols[j4 * 3 + 0] * f5) << 16 | (int)(texCols[j4 * 3 + 1] * f5) << 8 | (int)(texCols[j4 * 3 + 2] * f5);
                            }
                        }

                        if (i2 < 31)
                        {
                            float f6 = f3 * 0.9F;

                            if (zBuf[i3 + 1] <= j2)
                            {
                                zBuf[i3 + 1] = j2;
                                pixels[i3 + 1] = 0xff000000 | (int)(texCols[j4 * 3 + 0] * f6) << 16 | (int)(texCols[j4 * 3 + 1] * f6) << 8 | (int)(texCols[j4 * 3 + 2] * f6);
                            }
                        }
                    }

                    if (k2 < -1 || k2 >= 543)
                    {
                        continue;
                    }

                    int j3 = i2 + (k2 + 1) * 32;
                    int l3 = textures[block.blockID * 3 + 1];
                    float f4 = world.getLightBrightness(j1 - 1, j2, i1) * 0.8F + 0.2F;
                    int k4 = textures[block.blockID * 3 + 2];
                    float f7 = world.getLightBrightness(j1, j2, i1 + 1) * 0.8F + 0.2F;

                    if (i2 >= 0)
                    {
                        float f8 = f4 * f * 0.6F;

                        if (zBuf[j3] <= j2 - 1)
                        {
                            zBuf[j3] = j2 - 1;
                            pixels[j3] = 0xff000000 | (int)(texCols[l3 * 3 + 0] * f8) << 16 | (int)(texCols[l3 * 3 + 1] * f8) << 8 | (int)(texCols[l3 * 3 + 2] * f8);
                        }
                    }

                    if (i2 >= 31)
                    {
                        continue;
                    }

                    float f9 = f7 * 0.9F * f * 0.4F;

                    if (zBuf[j3 + 1] <= j2 - 1)
                    {
                        zBuf[j3 + 1] = j2 - 1;
                        pixels[j3 + 1] = 0xff000000 | (int)(texCols[k4 * 3 + 0] * f9) << 16 | (int)(texCols[k4 * 3 + 1] * f9) << 8 | (int)(texCols[k4 * 3 + 2] * f9);
                    }
                }
            }
        }

        postProcess();

        if (par1IsoImageBuffer.image == null)
        {
            par1IsoImageBuffer.image = new BufferedImage(32, 544, 2);
        }

        par1IsoImageBuffer.image.setRGB(0, 0, 32, 544, pixels, 0, 32);
        par1IsoImageBuffer.rendered = true;
    }

    private void postProcess()
    {
        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < 544; j++)
            {
                int k = i + j * 32;

                if (zBuf[k] == 0)
                {
                    pixels[k] = 0;
                }

                if (waterBuf[k] <= zBuf[k])
                {
                    continue;
                }

                int l = pixels[k] >> 24 & 0xff;
                pixels[k] = ((pixels[k] & 0xfefefe) >> 1) + waterBr[k];

                if (l < 128)
                {
                    pixels[k] = 0x80000000 + waterBr[k] * 2;
                }
                else
                {
                    pixels[k] |= 0xff000000;
                }
            }
        }
    }
}
