package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper
{
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte pixelData[];
    private static int imageData[];

    /**
     * Takes a screenshot and saves it to the screenshots directory. Returns the filename of the screenshot.
     */
    public static String saveScreenshot(File par0File, int par1, int par2)
    {
        return func_35879_a(par0File, null, par1, par2);
    }

    public static String func_35879_a(File par0File, String par1Str, int par2, int par3)
    {
        try
        {
            File file = new File(par0File, "screenshots");
            file.mkdir();

            if (buffer == null || buffer.capacity() < par2 * par3)
            {
                buffer = BufferUtils.createByteBuffer(par2 * par3 * 3);
            }

            if (imageData == null || imageData.length < par2 * par3 * 3)
            {
                pixelData = new byte[par2 * par3 * 3];
                imageData = new int[par2 * par3];
            }

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            buffer.clear();
            GL11.glReadPixels(0, 0, par2, par3, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
            buffer.clear();
            String s = (new StringBuilder()).append("").append(dateFormat.format(new Date())).toString();
            File file1;

            if (par1Str == null)
            {
                for (int i = 1; (file1 = new File(file, (new StringBuilder()).append(s).append(i != 1 ? (new StringBuilder()).append("_").append(i).toString() : "").append(".png").toString())).exists(); i++) { }
            }
            else
            {
                file1 = new File(file, par1Str);
            }

            buffer.get(pixelData);

            for (int j = 0; j < par2; j++)
            {
                for (int k = 0; k < par3; k++)
                {
                    int l = j + (par3 - k - 1) * par2;
                    int i1 = pixelData[l * 3 + 0] & 0xff;
                    int j1 = pixelData[l * 3 + 1] & 0xff;
                    int k1 = pixelData[l * 3 + 2] & 0xff;
                    int l1 = 0xff000000 | i1 << 16 | j1 << 8 | k1;
                    imageData[j + k * par2] = l1;
                }
            }

            BufferedImage bufferedimage = new BufferedImage(par2, par3, 1);
            bufferedimage.setRGB(0, 0, par2, par3, imageData, 0, par2);
            ImageIO.write(bufferedimage, "png", file1);
            return (new StringBuilder()).append("Saved screenshot as ").append(file1.getName()).toString();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return (new StringBuilder()).append("Failed to save: ").append(exception).toString();
        }
    }
}
