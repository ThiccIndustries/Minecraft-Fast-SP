package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.*;

public class ImageBufferDownload implements ImageBuffer
{
    private int imageData[];
    private int imageWidth;
    private int imageHeight;

    public ImageBufferDownload()
    {
    }

    public BufferedImage parseUserSkin(BufferedImage par1BufferedImage)
    {
        if (par1BufferedImage == null)
        {
            return null;
        }

        imageWidth = 64;
        imageHeight = 32;

        for (BufferedImage bufferedimage = par1BufferedImage; imageWidth < bufferedimage.getWidth() || imageHeight < bufferedimage.getHeight(); imageHeight *= 2)
        {
            imageWidth *= 2;
        }

        BufferedImage bufferedimage1 = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = bufferedimage1.getGraphics();
        g.drawImage(par1BufferedImage, 0, 0, null);
        g.dispose();
        imageData = ((DataBufferInt)bufferedimage1.getRaster().getDataBuffer()).getData();
        int i = imageWidth;
        int j = imageHeight;
        func_884_b(0, 0, i / 2, j / 2);
        func_885_a(i / 2, 0, i, j);
        func_884_b(0, j / 2, i, j);
        boolean flag = false;

        for (int k = i / 2; k < i; k++)
        {
            for (int i1 = 0; i1 < j / 2; i1++)
            {
                int k1 = imageData[k + i1 * i];

                if ((k1 >> 24 & 0xff) < 128)
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            for (int l = i / 2; l < i; l++)
            {
                for (int j1 = 0; j1 < j / 2; j1++)
                {
                    int l1 = imageData[l + j1 * i];
                    boolean flag1;

                    if ((l1 >> 24 & 0xff) < 128)
                    {
                        flag1 = true;
                    }
                }
            }
        }

        return bufferedimage1;
    }

    private void func_885_a(int par1, int par2, int par3, int par4)
    {
        if (func_886_c(par1, par2, par3, par4))
        {
            return;
        }

        for (int i = par1; i < par3; i++)
        {
            for (int j = par2; j < par4; j++)
            {
                imageData[i + j * imageWidth] &= 0xffffff;
            }
        }
    }

    private void func_884_b(int par1, int par2, int par3, int par4)
    {
        for (int i = par1; i < par3; i++)
        {
            for (int j = par2; j < par4; j++)
            {
                imageData[i + j * imageWidth] |= 0xff000000;
            }
        }
    }

    private boolean func_886_c(int par1, int par2, int par3, int par4)
    {
        for (int i = par1; i < par3; i++)
        {
            for (int j = par2; j < par4; j++)
            {
                int k = imageData[i + j * imageWidth];

                if ((k >> 24 & 0xff) < 128)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
