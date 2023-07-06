package net.minecraft.src;

import java.util.Random;

public class TextureHDPortalFX extends TextureFX implements TextureHDFX
{
    private int tileWidth;
    private int tickCounter;
    private byte buffer[][];

    public TextureHDPortalFX()
    {
        super(Block.portal.blockIndexInTexture);
        tileWidth = 0;
        tileWidth = 16;
        tickCounter = 0;
        setup();
    }

    public void setTileWidth(int i)
    {
        if (i > Config.getMaxDynamicTileWidth())
        {
            i = Config.getMaxDynamicTileWidth();
        }

        tileWidth = i;
        setup();
        tickCounter = 0;
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
    }

    private void setup()
    {
        imageData = new byte[tileWidth * tileWidth * 4];
        buffer = new byte[32][tileWidth * tileWidth * 4];
        Random random = new Random(100L);

        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < tileWidth; j++)
            {
                for (int k = 0; k < tileWidth; k++)
                {
                    float f = 0.0F;

                    for (int l = 0; l < 2; l++)
                    {
                        float f1 = l * (tileWidth / 2);
                        float f2 = l * (tileWidth / 2);
                        float f3 = (((float)j - f1) / (float)tileWidth) * 2.0F;
                        float f4 = (((float)k - f2) / (float)tileWidth) * 2.0F;

                        if (f3 < -1F)
                        {
                            f3 += 2.0F;
                        }

                        if (f3 >= 1.0F)
                        {
                            f3 -= 2.0F;
                        }

                        if (f4 < -1F)
                        {
                            f4 += 2.0F;
                        }

                        if (f4 >= 1.0F)
                        {
                            f4 -= 2.0F;
                        }

                        float f5 = f3 * f3 + f4 * f4;
                        float f6 = (float)Math.atan2(f4, f3) + ((((float)i / 32F) * (float)Math.PI * 2.0F - f5 * 10F) + (float)(l * 2)) * (float)(l * 2 - 1);
                        f6 = (MathHelper.sin(f6) + 1.0F) / 2.0F;
                        f6 /= f5 + 1.0F;
                        f += f6 * 0.5F;
                    }

                    f += random.nextFloat() * 0.1F;
                    int i1 = (int)(f * 100F + 155F);
                    int j1 = (int)(f * f * 200F + 55F);
                    int k1 = (int)(f * f * f * f * 255F);
                    int l1 = (int)(f * 100F + 155F);
                    int i2 = k * tileWidth + j;
                    buffer[i][i2 * 4 + 0] = (byte)j1;
                    buffer[i][i2 * 4 + 1] = (byte)k1;
                    buffer[i][i2 * 4 + 2] = (byte)i1;
                    buffer[i][i2 * 4 + 3] = (byte)l1;
                }
            }
        }
    }

    public void onTick()
    {
        if (!Config.isAnimatedPortal())
        {
            imageData = null;
        }

        if (imageData == null)
        {
            return;
        }

        tickCounter++;
        byte abyte0[] = buffer[tickCounter & 0x1f];

        for (int i = 0; i < tileWidth * tileWidth; i++)
        {
            int j = abyte0[i * 4 + 0] & 0xff;
            int k = abyte0[i * 4 + 1] & 0xff;
            int l = abyte0[i * 4 + 2] & 0xff;
            int i1 = abyte0[i * 4 + 3] & 0xff;

            if (anaglyphEnabled)
            {
                int j1 = (j * 30 + k * 59 + l * 11) / 100;
                int k1 = (j * 30 + k * 70) / 100;
                int l1 = (j * 30 + l * 70) / 100;
                j = j1;
                k = k1;
                l = l1;
            }

            imageData[i * 4 + 0] = (byte)j;
            imageData[i * 4 + 1] = (byte)k;
            imageData[i * 4 + 2] = (byte)l;
            imageData[i * 4 + 3] = (byte)i1;
        }
    }
}
