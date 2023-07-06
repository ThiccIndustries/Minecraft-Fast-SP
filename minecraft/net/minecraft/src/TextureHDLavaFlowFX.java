package net.minecraft.src;

public class TextureHDLavaFlowFX extends TextureFX implements TextureHDFX
{
    private TexturePackBase texturePackBase;
    private int tileWidth;
    protected float buf1[];
    protected float buf2[];
    protected float buf3[];
    protected float buf4[];
    int tickCounter;

    public TextureHDLavaFlowFX()
    {
        super(Block.lavaMoving.blockIndexInTexture + 1);
        tileWidth = 0;
        tileWidth = 16;
        imageData = new byte[tileWidth * tileWidth * 4];
        buf1 = new float[tileWidth * tileWidth];
        buf2 = new float[tileWidth * tileWidth];
        buf3 = new float[tileWidth * tileWidth];
        buf4 = new float[tileWidth * tileWidth];
        tickCounter = 0;
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
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
        texturePackBase = texturepackbase;
    }

    public void onTick()
    {
        if (!Config.isAnimatedLava())
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
            for (int k = 0; k < tileWidth; k++)
            {
                float f = 0.0F;
                int j1 = (int)(MathHelper.sin(((float)k * (float)Math.PI * 2.0F) / 16F) * 1.2F);
                int k1 = (int)(MathHelper.sin(((float)j * (float)Math.PI * 2.0F) / 16F) * 1.2F);

                for (int l1 = j - 1; l1 <= j + 1; l1++)
                {
                    for (int j2 = k - 1; j2 <= k + 1; j2++)
                    {
                        int l2 = l1 + j1 & i;
                        int j3 = j2 + k1 & i;
                        f += buf1[l2 + j3 * tileWidth];
                    }
                }

                buf2[j + k * tileWidth] = f / 10F + ((buf3[(j + 0 & i) + (k + 0 & i) * tileWidth] + buf3[(j + 1 & i) + (k + 0 & i) * tileWidth] + buf3[(j + 1 & i) + (k + 1 & i) * tileWidth] + buf3[(j + 0 & i) + (k + 1 & i) * tileWidth]) / 4F) * 0.8F;
                buf3[j + k * tileWidth] += buf4[j + k * tileWidth] * 0.01F;

                if (buf3[j + k * tileWidth] < 0.0F)
                {
                    buf3[j + k * tileWidth] = 0.0F;
                }

                buf4[j + k * tileWidth] -= 0.06F;

                if (Math.random() < 0.0050000000000000001D)
                {
                    buf4[j + k * tileWidth] = 1.5F;
                }
            }
        }

        float af[] = buf2;
        buf2 = buf1;
        buf1 = af;
        int l = tileWidth * tileWidth - 1;

        for (int i1 = 0; i1 < tileWidth * tileWidth; i1++)
        {
            float f1 = buf1[i1 - (tickCounter / 3) * tileWidth & l] * 2.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            float f2 = f1;
            int i2 = (int)(f2 * 100F + 155F);
            int k2 = (int)(f2 * f2 * 255F);
            int i3 = (int)(f2 * f2 * f2 * f2 * 128F);

            if (anaglyphEnabled)
            {
                int k3 = (i2 * 30 + k2 * 59 + i3 * 11) / 100;
                int l3 = (i2 * 30 + k2 * 70) / 100;
                int i4 = (i2 * 30 + i3 * 70) / 100;
                i2 = k3;
                k2 = l3;
                i3 = i4;
            }

            imageData[i1 * 4 + 0] = (byte)i2;
            imageData[i1 * 4 + 1] = (byte)k2;
            imageData[i1 * 4 + 2] = (byte)i3;
            imageData[i1 * 4 + 3] = -1;
        }
    }
}
