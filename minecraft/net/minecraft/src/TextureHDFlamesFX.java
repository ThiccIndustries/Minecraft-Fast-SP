package net.minecraft.src;

import java.util.Random;

public class TextureHDFlamesFX extends TextureFX implements TextureHDFX
{
    private int tileWidth;
    private int fireHeight;
    protected float buf1[];
    protected float buf2[];
    private Random random;

    public TextureHDFlamesFX(int i)
    {
        super(Block.fire.blockIndexInTexture + i * 16);
        tileWidth = 0;
        fireHeight = 0;
        random = new Random();
        tileWidth = 16;
        fireHeight = tileWidth + tileWidth / 4;
        imageData = new byte[tileWidth * tileWidth * 4];
        buf1 = new float[tileWidth * fireHeight];
        buf2 = new float[tileWidth * fireHeight];
    }

    public void setTileWidth(int i)
    {
        if (i > Config.getMaxDynamicTileWidth())
        {
            i = Config.getMaxDynamicTileWidth();
        }

        tileWidth = i;
        fireHeight = i + i / 4;
        imageData = new byte[i * i * 4];
        buf1 = new float[i * fireHeight];
        buf2 = new float[i * fireHeight];
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
    }

    public void onTick()
    {
        if (!Config.isAnimatedFire())
        {
            imageData = null;
        }

        if (imageData == null)
        {
            return;
        }

        float f = 1.01F + 0.8F / (float)tileWidth;
        float f1 = 3F + (float)tileWidth / 16F;

        for (int i = 0; i < tileWidth; i++)
        {
            for (int j = 0; j < fireHeight; j++)
            {
                int l = fireHeight - tileWidth / 8;
                float f2 = buf1[i + ((j + 1) % fireHeight) * tileWidth] * (float)l;

                for (int j1 = i - 1; j1 <= i + 1; j1++)
                {
                    for (int k1 = j; k1 <= j + 1; k1++)
                    {
                        int i2 = j1;
                        int k2 = k1;

                        if (i2 >= 0 && k2 >= 0 && i2 < tileWidth && k2 < fireHeight)
                        {
                            f2 += buf1[i2 + k2 * tileWidth];
                        }

                        l++;
                    }
                }

                buf2[i + j * tileWidth] = f2 / ((float)l * f);

                if (j >= fireHeight - tileWidth / 16)
                {
                    buf2[i + j * tileWidth] = random.nextFloat() * random.nextFloat() * random.nextFloat() * f1 + random.nextFloat() * 0.1F + 0.2F;
                }
            }
        }

        float af[] = buf2;
        buf2 = buf1;
        buf1 = af;
        int k = tileWidth * tileWidth;

        for (int i1 = 0; i1 < k; i1++)
        {
            float f3 = buf1[i1] * 1.8F;

            if (f3 > 1.0F)
            {
                f3 = 1.0F;
            }

            if (f3 < 0.0F)
            {
                f3 = 0.0F;
            }

            float f4 = f3;
            int l1 = (int)(f4 * 155F + 100F);
            int j2 = (int)(f4 * f4 * 255F);
            int l2 = (int)(f4 * f4 * f4 * f4 * f4 * f4 * f4 * f4 * f4 * f4 * 255F);
            char c = '\377';

            if (f4 < 0.5F)
            {
                c = '\0';
            }

            f4 = (f4 - 0.5F) * 2.0F;

            if (anaglyphEnabled)
            {
                int i3 = (l1 * 30 + j2 * 59 + l2 * 11) / 100;
                int k3 = (l1 * 30 + j2 * 70) / 100;
                int l3 = (l1 * 30 + l2 * 70) / 100;
                l1 = i3;
                j2 = k3;
                l2 = l3;
            }

            int j3 = i1 * 4;
            imageData[j3 + 0] = (byte)l1;
            imageData[j3 + 1] = (byte)j2;
            imageData[j3 + 2] = (byte)l2;
            imageData[j3 + 3] = (byte)c;
        }
    }
}
