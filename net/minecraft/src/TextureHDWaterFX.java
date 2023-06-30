package net.minecraft.src;

public class TextureHDWaterFX extends TextureFX implements TextureHDFX
{
    private TexturePackBase texturePackBase;
    private int tileWidth;
    protected float buf1[];
    protected float buf2[];
    protected float buf3[];
    protected float buf4[];
    private int tickCounter;

    public TextureHDWaterFX()
    {
        super(Block.waterMoving.blockIndexInTexture);
        tileWidth = 0;
        tileWidth = 16;
        imageData = new byte[tileWidth * tileWidth * 4];
        buf1 = new float[tileWidth * tileWidth];
        buf2 = new float[tileWidth * tileWidth];
        buf3 = new float[tileWidth * tileWidth];
        buf4 = new float[tileWidth * tileWidth];
        tickCounter = 0;
    }

    public void setTileWidth(int i)
    {
        if (i > Config.getMaxDynamicTileWidth())
        {
            i = Config.getMaxDynamicTileWidth();
        }

        tileWidth = i;
        imageData = new byte[i * i * 4];
        buf1 = new float[i * i];
        buf2 = new float[i * i];
        buf3 = new float[i * i];
        buf4 = new float[i * i];
        tickCounter = 0;
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
        texturePackBase = texturepackbase;
    }

    public void onTick()
    {
        if (!Config.isAnimatedWater())
        {
            imageData = null;
        }

        if (imageData == null)
        {
            return;
        }

        tickCounter++;
        int i = tileWidth - 1;

        for (int j = 0; j < tileWidth; j++)
        {
            for (int l = 0; l < tileWidth; l++)
            {
                float f = 0.0F;

                for (int k1 = j - 1; k1 <= j + 1; k1++)
                {
                    int l1 = k1 & i;
                    int j2 = l & i;
                    f += buf1[l1 + j2 * tileWidth];
                }

                buf2[j + l * tileWidth] = f / 3.3F + buf3[j + l * tileWidth] * 0.8F;
            }
        }

        for (int k = 0; k < tileWidth; k++)
        {
            for (int i1 = 0; i1 < tileWidth; i1++)
            {
                buf3[k + i1 * tileWidth] += buf4[k + i1 * tileWidth] * 0.05F;

                if (buf3[k + i1 * tileWidth] < 0.0F)
                {
                    buf3[k + i1 * tileWidth] = 0.0F;
                }

                buf4[k + i1 * tileWidth] -= 0.1F;

                if (Math.random() < 0.050000000000000003D)
                {
                    buf4[k + i1 * tileWidth] = 0.5F;
                }
            }
        }

        float af[] = buf2;
        buf2 = buf1;
        buf1 = af;

        for (int j1 = 0; j1 < tileWidth * tileWidth; j1++)
        {
            float f1 = buf1[j1];

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            float f2 = f1 * f1;
            int i2 = (int)(32F + f2 * 32F);
            int k2 = (int)(50F + f2 * 64F);
            int l2 = 255;
            int i3 = (int)(146F + f2 * 50F);

            if (anaglyphEnabled)
            {
                int j3 = (i2 * 30 + k2 * 59 + l2 * 11) / 100;
                int k3 = (i2 * 30 + k2 * 70) / 100;
                int l3 = (i2 * 30 + l2 * 70) / 100;
                i2 = j3;
                k2 = k3;
                l2 = l3;
            }

            imageData[j1 * 4 + 0] = (byte)i2;
            imageData[j1 * 4 + 1] = (byte)k2;
            imageData[j1 * 4 + 2] = (byte)l2;
            imageData[j1 * 4 + 3] = (byte)i3;
        }
    }
}
