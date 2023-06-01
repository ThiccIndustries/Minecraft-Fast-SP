package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureHDWatchFX extends TextureFX implements TextureHDFX
{
    private Minecraft mc;
    private int tileWidth;
    private TexturePackBase texturePackBase;
    private int watchIconImageData[];
    private int dialImageData[];
    private byte watchBaseData[];
    private byte dialBaseData[];
    private double showAngle;
    private double angleDiff;

    public TextureHDWatchFX(Minecraft minecraft)
    {
        super(Item.pocketSundial.getIconFromDamage(0));
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
        watchIconImageData = new int[tileWidth * tileWidth];
        dialImageData = new int[tileWidth * tileWidth];
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
            watchIconImageData = new int[tileWidth * tileWidth];
            dialImageData = new int[tileWidth * tileWidth];
            int i = (iconIndex % 16) * tileWidth;
            int j = (iconIndex / 16) * tileWidth;
            bufferedimage.getRGB(i, j, tileWidth, tileWidth, watchIconImageData, 0, tileWidth);
            bufferedimage = ImageIO.read((net.minecraft.client.Minecraft.class).getResource("/misc/dial.png"));

            if (texturePackBase != null)
            {
                bufferedimage = ImageIO.read(texturePackBase.getResourceAsStream("/misc/dial.png"));
            }

            if (bufferedimage.getWidth() != tileWidth)
            {
                bufferedimage = RenderEngine.scaleBufferedImage(bufferedimage, tileWidth, tileWidth);
            }

            bufferedimage.getRGB(0, 0, tileWidth, tileWidth, dialImageData, 0, tileWidth);
            watchBaseData = new byte[watchIconImageData.length * 4];
            dialBaseData = new byte[dialImageData.length * 4];
            int k = tileWidth * tileWidth;

            for (int l = 0; l < k; l++)
            {
                int j1 = watchIconImageData[l] >> 24 & 0xff;
                int l1 = watchIconImageData[l] >> 16 & 0xff;
                int j2 = watchIconImageData[l] >> 8 & 0xff;
                int l2 = watchIconImageData[l] >> 0 & 0xff;
                boolean flag;

                if (l1 == l2 && j2 == 0 && l2 > 0)
                {
                    flag = false;
                }

                watchBaseData[l * 4 + 0] = (byte)l1;
                watchBaseData[l * 4 + 1] = (byte)j2;
                watchBaseData[l * 4 + 2] = (byte)l2;
                watchBaseData[l * 4 + 3] = (byte)j1;
            }

            for (int i1 = 0; i1 < k; i1++)
            {
                int k1 = dialImageData[i1] >> 24 & 0xff;
                int i2 = dialImageData[i1] >> 16 & 0xff;
                int k2 = dialImageData[i1] >> 8 & 0xff;
                int i3 = dialImageData[i1] >> 0 & 0xff;
                dialBaseData[i1 * 4 + 0] = (byte)i2;
                dialBaseData[i1 * 4 + 1] = (byte)k2;
                dialBaseData[i1 * 4 + 2] = (byte)i3;
                dialBaseData[i1 * 4 + 3] = (byte)k1;
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void onTick()
    {
        double d = 0.0D;

        if (mc.theWorld != null && mc.thePlayer != null)
        {
            float f = mc.theWorld.getCelestialAngle(1.0F);
            d = -f * (float)Math.PI * 2.0F;

            if (!mc.theWorld.worldProvider.func_48217_e())
            {
                d = Math.random() * Math.PI * 2D;
            }
        }

        double d1;

        for (d1 = d - showAngle; d1 < -Math.PI; d1 += (Math.PI * 2D)) { }

        for (; d1 >= Math.PI; d1 -= (Math.PI * 2D)) { }

        if (d1 < -1D)
        {
            d1 = -1D;
        }

        if (d1 > 1.0D)
        {
            d1 = 1.0D;
        }

        angleDiff += d1 * 0.10000000000000001D;
        angleDiff *= 0.80000000000000004D;
        showAngle += angleDiff;
        double d2 = Math.sin(showAngle);
        double d3 = Math.cos(showAngle);
        int i = tileWidth * tileWidth;
        int j = tileWidth - 1;
        double d4 = j;

        for (int k = 0; k < i; k++)
        {
            int l = k * 4;
            int i1 = watchBaseData[l + 0] & 0xff;
            int j1 = watchBaseData[l + 1] & 0xff;
            int k1 = watchBaseData[l + 2] & 0xff;
            int l1 = watchBaseData[l + 3] & 0xff;

            if (i1 == k1 && j1 == 0 && k1 > 0)
            {
                double d5 = -((double)(k % tileWidth) / d4 - 0.5D);
                double d6 = (double)(k / tileWidth) / d4 - 0.5D;
                int l2 = i1;
                int i3 = (int)((d5 * d3 + d6 * d2 + 0.5D) * (double)tileWidth);
                int j3 = (int)(((d6 * d3 - d5 * d2) + 0.5D) * (double)tileWidth);
                int k3 = (i3 & j) + (j3 & j) * tileWidth;
                int l3 = k3 * 4;
                i1 = ((dialBaseData[l3 + 0] & 0xff) * l2) / 255;
                j1 = ((dialBaseData[l3 + 1] & 0xff) * l2) / 255;
                k1 = ((dialBaseData[l3 + 2] & 0xff) * l2) / 255;
                l1 = dialBaseData[l3 + 3] & 0xff;
            }

            if (anaglyphEnabled)
            {
                int i2 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
                int j2 = (i1 * 30 + j1 * 70) / 100;
                int k2 = (i1 * 30 + k1 * 70) / 100;
                i1 = i2;
                j1 = j2;
                k1 = k2;
            }

            imageData[l + 0] = (byte)i1;
            imageData[l + 1] = (byte)j1;
            imageData[l + 2] = (byte)k1;
            imageData[l + 3] = (byte)l1;
        }
    }
}
