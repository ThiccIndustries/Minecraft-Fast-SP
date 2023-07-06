package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureHDCompassFX extends TextureFX implements TextureHDFX
{
    private Minecraft mc;
    private int tileWidth;
    private TexturePackBase texturePackBase;
    private byte baseImageData[];
    private int compassIconImageData[];
    private double showAngle;
    private double angleDiff;

    public TextureHDCompassFX(Minecraft minecraft)
    {
        super(Item.compass.getIconFromDamage(0));
        tileWidth = 0;
        texturePackBase = null;
        mc = minecraft;
        tileWidth = 16;
        setup();
    }

    public void setTileWidth(int i)
    {
        tileWidth = i;
        setup();
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
        texturePackBase = texturepackbase;
    }

    private void setup()
    {
        imageData = new byte[tileWidth * tileWidth * 4];
        compassIconImageData = new int[tileWidth * tileWidth];
        tileImage = 1;

        try
        {
            BufferedImage bufferedimage = ImageIO.read((net.minecraft.client.Minecraft.class).getResource("/gui/items.png"));

            if (texturePackBase != null)
            {
                bufferedimage = ImageIO.read(texturePackBase.getResourceAsStream("/gui/items.png"));
            }

            tileWidth = bufferedimage.getWidth() / 16;
            imageData = new byte[tileWidth * tileWidth * 4];
            compassIconImageData = new int[tileWidth * tileWidth];
            int i = (iconIndex % 16) * tileWidth;
            int j = (iconIndex / 16) * tileWidth;
            bufferedimage.getRGB(i, j, tileWidth, tileWidth, compassIconImageData, 0, tileWidth);
            baseImageData = new byte[imageData.length];
            int k = tileWidth * tileWidth;

            for (int l = 0; l < k; l++)
            {
                int i1 = compassIconImageData[l] >> 24 & 0xff;
                int j1 = compassIconImageData[l] >> 16 & 0xff;
                int k1 = compassIconImageData[l] >> 8 & 0xff;
                int l1 = compassIconImageData[l] >> 0 & 0xff;

                if (anaglyphEnabled)
                {
                    int i2 = (j1 * 30 + k1 * 59 + l1 * 11) / 100;
                    int j2 = (j1 * 30 + k1 * 70) / 100;
                    int k2 = (j1 * 30 + l1 * 70) / 100;
                    j1 = i2;
                    k1 = j2;
                    l1 = k2;
                }

                baseImageData[l * 4 + 0] = (byte)j1;
                baseImageData[l * 4 + 1] = (byte)k1;
                baseImageData[l * 4 + 2] = (byte)l1;
                baseImageData[l * 4 + 3] = (byte)i1;
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void onTick()
    {
        int i = tileWidth * tileWidth;
        double d = (double)(tileWidth / 2) + 0.5D;
        double d1 = (double)(tileWidth / 2) - 0.5D;
        double d2 = 0.29999999999999999D * (double)(tileWidth / 16);
        System.arraycopy(baseImageData, 0, imageData, 0, imageData.length);
        double d3 = 0.0D;

        if (mc.theWorld != null && mc.thePlayer != null)
        {
            ChunkCoordinates chunkcoordinates = mc.theWorld.getSpawnPoint();
            double d5 = (double)chunkcoordinates.posX - mc.thePlayer.posX;
            double d7 = (double)chunkcoordinates.posZ - mc.thePlayer.posZ;
            d3 = ((double)(mc.thePlayer.rotationYaw - 90F) * Math.PI) / 180D - Math.atan2(d7, d5);

            if (!mc.theWorld.worldProvider.func_48217_e())
            {
                d3 = Math.random() * Math.PI * 2D;
            }
        }

        double d4;

        for (d4 = d3 - showAngle; d4 < -Math.PI; d4 += (Math.PI * 2D)) { }

        for (; d4 >= Math.PI; d4 -= (Math.PI * 2D)) { }

        if (d4 < -1D)
        {
            d4 = -1D;
        }

        if (d4 > 1.0D)
        {
            d4 = 1.0D;
        }

        angleDiff += d4 * 0.10000000000000001D;
        angleDiff *= 0.80000000000000004D;
        showAngle += angleDiff;
        double d6 = Math.sin(showAngle);
        double d8 = Math.cos(showAngle);

        for (int j = -4; j <= 4; j++)
        {
            int l = (int)(d + d8 * (double)j * d2);
            int j1 = (int)(d1 - d6 * (double)j * d2 * 0.5D);
            int l1 = j1 * tileWidth + l;
            int j2 = 100;
            int l2 = 100;
            int j3 = 100;
            char c = '\377';

            if (anaglyphEnabled)
            {
                int l3 = (j2 * 30 + l2 * 59 + j3 * 11) / 100;
                int l4 = (j2 * 30 + l2 * 70) / 100;
                int j5 = (j2 * 30 + j3 * 70) / 100;
                j2 = l3;
                l2 = l4;
                j3 = j5;
            }

            int i4 = l1 * 4;
            imageData[i4 + 0] = (byte)j2;
            imageData[i4 + 1] = (byte)l2;
            imageData[i4 + 2] = (byte)j3;
            imageData[i4 + 3] = (byte)c;
        }

        for (int k = -8; k <= 16; k++)
        {
            int i1 = (int)(d + d6 * (double)k * d2);
            int k1 = (int)(d1 + d8 * (double)k * d2 * 0.5D);
            int i2 = k1 * tileWidth + i1;
            int k2 = k >= 0 ? 255 : 100;
            int i3 = k >= 0 ? 20 : 100;
            int k3 = k >= 0 ? 20 : 100;
            char c1 = '\377';

            if (anaglyphEnabled)
            {
                int j4 = (k2 * 30 + i3 * 59 + k3 * 11) / 100;
                int i5 = (k2 * 30 + i3 * 70) / 100;
                int k5 = (k2 * 30 + k3 * 70) / 100;
                k2 = j4;
                i3 = i5;
                k3 = k5;
            }

            int k4 = i2 * 4;
            imageData[k4 + 0] = (byte)k2;
            imageData[k4 + 1] = (byte)i3;
            imageData[k4 + 2] = (byte)k3;
            imageData[k4 + 3] = (byte)c1;
        }
    }
}
