package net.minecraft.src;

public class TextureHDWaterFlowFX extends TextureFX implements TextureHDFX
{
    private TexturePackBase texturePackBase;
    private int tileWidth;
    protected float buf1[];
    protected float buf2[];
    protected float buf3[];
    protected float buf4[];
    private int tickCounter;

    public TextureHDWaterFlowFX()
    {
        super(Block.waterMoving.blockIndexInTexture + 1);
        tileWidth = 16;
        tileWidth = 16;
        imageData = new byte[tileWidth * tileWidth * 4];
        buf1 = new float[tileWidth * tileWidth];
        buf2 = new float[tileWidth * tileWidth];
        buf3 = new float[tileWidth * tileWidth];
        buf4 = new float[tileWidth * tileWidth];
        tileSize = 2;
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

                for (int l1 = l - 2; l1 <= l; l1++)
                {
                    int i2 = j & i;
                    int j2 = l1 & i;
                    f += buf1[i2 + j2 * tileWidth];
                }

                buf2[j + l * tileWidth] = f / 3.2F + buf3[j + l * tileWidth] * 0.8F;
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

                buf4[k + i1 * tileWidth] -= 0.3F;

                if (Math.random() < 0.20000000000000001D)
                {
                    buf4[k + i1 * tileWidth] = 0.5F;
                }
            }
        }

        float af[] = buf2;
        buf2 = buf1;
        buf1 = af;
        int j1 = tileWidth * tileWidth - 1;

        for (int k1 = 0; k1 < tileWidth * tileWidth; k1++)
        {
            float f1 = buf1[k1 - tickCounter * tileWidth & j1];

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            float f2 = f1 * f1;
            int k2 = (int)(32F + f2 * 32F);
            int l2 = (int)(50F + f2 * 64F);
            int i3 = 255;
            int j3 = (int)(146F + f2 * 50F);

            if (anaglyphEnabled)
            {
                int k3 = (k2 * 30 + l2 * 59 + i3 * 11) / 100;
                int l3 = (k2 * 30 + l2 * 70) / 100;
                int i4 = (k2 * 30 + i3 * 70) / 100;
                k2 = k3;
                l2 = l3;
                i3 = i4;
            }

            imageData[k1 * 4 + 0] = (byte)k2;
            imageData[k1 * 4 + 1] = (byte)l2;
            imageData[k1 * 4 + 2] = (byte)i3;
            imageData[k1 * 4 + 3] = (byte)j3;
        }
    }
}
