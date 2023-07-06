package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Bidi;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class FontRenderer
{
    private static final Pattern field_52015_r = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    private float charWidth[];
    public int fontTextureName;

    /** the height in pixels of default text */
    public int FONT_HEIGHT;
    public Random fontRandom;
    private byte glyphWidth[];
    private final int glyphTextureName[];
    private int colorCode[];

    /**
     * The currently bound GL texture ID. Avoids unnecessary glBindTexture() for the same texture if it's already bound.
     */
    private int boundTextureName;

    /** The RenderEngine used to load and setup glyph textures. */
    private final RenderEngine renderEngine;

    /** Current X coordinate at which to draw the next character. */
    private float posX;

    /** Current Y coordinate at which to draw the next character. */
    private float posY;

    /**
     * If true, strings should be rendered with Unicode fonts instead of the default.png font
     */
    private boolean unicodeFlag;

    /**
     * If true, the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    private boolean bidiFlag;
    private float field_50115_n;
    private float field_50116_o;
    private float field_50118_p;
    private float field_50117_q;
    private GameSettings gameSettings;
    private String textureFile;
    private long lastUpdateTime;

    FontRenderer()
    {
        lastUpdateTime = 0L;
        charWidth = new float[256];
        fontTextureName = 0;
        FONT_HEIGHT = 8;
        fontRandom = new Random();
        glyphWidth = new byte[0x10000];
        glyphTextureName = new int[256];
        colorCode = new int[32];
        renderEngine = null;
    }

    public FontRenderer(GameSettings par1GameSettings, String par2Str, RenderEngine par3RenderEngine, boolean par4)
    {
        lastUpdateTime = 0L;
        charWidth = new float[256];
        fontTextureName = 0;
        FONT_HEIGHT = 8;
        fontRandom = new Random();
        glyphWidth = new byte[0x10000];
        glyphTextureName = new int[256];
        colorCode = new int[32];
        renderEngine = par3RenderEngine;
        unicodeFlag = par4;
        gameSettings = par1GameSettings;
        textureFile = par2Str;
        init();
    }

    private void init()
    {
        charWidth = new float[256];
        fontTextureName = 0;
        glyphWidth = new byte[0x10000];
        BufferedImage bufferedimage;

        try
        {
            bufferedimage = ImageIO.read(getFontTexturePack().getResourceAsStream(textureFile));
            InputStream inputstream = getFontTexturePack().getResourceAsStream("/font/glyph_sizes.bin");
            inputstream.read(glyphWidth);
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }

        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int k = i / 16;
        int l = j / 16;
        float f = (float)i / 128F;
        int ai[] = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);

        for (int i1 = 0; i1 < 256; i1++)
        {
            int j1 = i1 % 16;
            int l1 = i1 / 16;
            int j2 = 0;
            j2 = k - 1;

            do
            {
                if (j2 < 0)
                {
                    break;
                }

                int l2 = j1 * k + j2;
                boolean flag1 = true;

                for (int k3 = 0; k3 < l && flag1; k3++)
                {
                    int i4 = (l1 * l + k3) * i;
                    int k4 = ai[l2 + i4];
                    int i5 = k4 >> 24 & 0xff;

                    if (i5 > 16)
                    {
                        flag1 = false;
                    }
                }

                if (!flag1)
                {
                    break;
                }

                j2--;
            }
            while (true);

            if (i1 == 65)
            {
                i1 = i1;
            }

            if (i1 == 32)
            {
                j2 = (int)(1.5D * (double)f);
            }

            charWidth[i1] = (float)(j2 + 1) / f + 1.0F;
        }

        readCustomCharWidths();
        RenderEngine _tmp = renderEngine;
        boolean flag = RenderEngine.useMipmaps;

        try
        {
            RenderEngine _tmp1 = renderEngine;
            RenderEngine.useMipmaps = false;

            if (fontTextureName <= 0)
            {
                fontTextureName = renderEngine.allocateAndSetupTexture(bufferedimage);
            }
            else
            {
                renderEngine.setupTexture(bufferedimage, fontTextureName);
            }
        }
        finally
        {
            RenderEngine _tmp2 = renderEngine;
            RenderEngine.useMipmaps = flag;
        }

        for (int k1 = 0; k1 < 32; k1++)
        {
            int i2 = (k1 >> 3 & 1) * 85;
            int k2 = (k1 >> 2 & 1) * 170 + i2;
            int i3 = (k1 >> 1 & 1) * 170 + i2;
            int j3 = (k1 >> 0 & 1) * 170 + i2;

            if (k1 == 6)
            {
                k2 += 85;
            }

            if (gameSettings.anaglyph)
            {
                int l3 = (k2 * 30 + i3 * 59 + j3 * 11) / 100;
                int j4 = (k2 * 30 + i3 * 70) / 100;
                int l4 = (k2 * 30 + j3 * 70) / 100;
                k2 = l3;
                i3 = j4;
                j3 = l4;
            }

            if (k1 >= 16)
            {
                k2 /= 4;
                i3 /= 4;
                j3 /= 4;
            }

            colorCode[k1] = (k2 & 0xff) << 16 | (i3 & 0xff) << 8 | j3 & 0xff;
        }
    }

    private void readCustomCharWidths()
    {
        String s = ".png";

        if (!textureFile.endsWith(s))
        {
            return;
        }

        String s1 = (new StringBuilder()).append(textureFile.substring(0, textureFile.length() - s.length())).append(".properties").toString();
        InputStream inputstream = getFontTexturePack().getResourceAsStream(s1);

        if (inputstream == null)
        {
            return;
        }

        try
        {
            Config.log((new StringBuilder()).append("Loading ").append(s1).toString());
            Properties properties = new Properties();
            properties.load(inputstream);
            Set set = properties.keySet();
            Iterator iterator = set.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                String s2 = (String)iterator.next();
                String s3 = "width.";

                if (s2.startsWith(s3))
                {
                    String s4 = s2.substring(s3.length());
                    int i = Config.parseInt(s4, -1);

                    if (i >= 0 && i < charWidth.length)
                    {
                        String s5 = properties.getProperty(s2);
                        float f = Config.parseFloat(s5, -1F);

                        if (f >= 0.0F)
                        {
                            charWidth[i] = f;
                        }
                    }
                }
            }
            while (true);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private TexturePackBase getFontTexturePack()
    {
        if (gameSettings.ofCustomFonts)
        {
            return gameSettings.mc.texturePackList.selectedTexturePack;
        }
        else
        {
            return (TexturePackBase)gameSettings.mc.texturePackList.availableTexturePacks().get(0);
        }
    }

    private void checkUpdated()
    {
        if (Config.getTextureUpdateTime() == lastUpdateTime)
        {
            return;
        }
        else
        {
            lastUpdateTime = Config.getTextureUpdateTime();
            init();
            return;
        }
    }

    private float func_50112_a(int par1, char par2, boolean par3)
    {
        if (par2 == ' ')
        {
            return 4F;
        }

        if (par1 > 0 && !unicodeFlag)
        {
            return func_50106_a(par1 + 32, par3);
        }
        else
        {
            return func_50111_a(par2, par3);
        }
    }

    private float func_50106_a(int par1, boolean par2)
    {
        float f = (par1 % 16) * 8;
        float f1 = (par1 / 16) * 8;
        float f2 = par2 ? 1.0F : 0.0F;

        if (boundTextureName != fontTextureName)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureName);
            boundTextureName = fontTextureName;
        }

        float f3 = charWidth[par1] - 0.01F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f / 128F, f1 / 128F);
        GL11.glVertex3f(posX + f2, posY, 0.0F);
        GL11.glTexCoord2f(f / 128F, (f1 + 7.99F) / 128F);
        GL11.glVertex3f(posX - f2, posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f + f3) / 128F, f1 / 128F);
        GL11.glVertex3f(posX + f3 + f2, posY, 0.0F);
        GL11.glTexCoord2f((f + f3) / 128F, (f1 + 7.99F) / 128F);
        GL11.glVertex3f((posX + f3) - f2, posY + 7.99F, 0.0F);
        GL11.glEnd();
        return charWidth[par1];
    }

    /**
     * Load one of the /font/glyph_XX.png into a new GL texture and store the texture ID in glyphTextureName array.
     */
    private void loadGlyphTexture(int par1)
    {
        String s = String.format("/font/glyph_%02X.png", new Object[]
                {
                    Integer.valueOf(par1)
                });
        BufferedImage bufferedimage;

        try
        {
            bufferedimage = ImageIO.read((net.minecraft.src.RenderEngine.class).getResourceAsStream(s));
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }

        glyphTextureName[par1] = renderEngine.allocateAndSetupTexture(bufferedimage);
        boundTextureName = glyphTextureName[par1];
    }

    private float func_50111_a(char par1, boolean par2)
    {
        if (glyphWidth[par1] == 0)
        {
            return 0.0F;
        }

        int i = par1 / 256;

        if (glyphTextureName[i] == 0)
        {
            loadGlyphTexture(i);
        }

        if (boundTextureName != glyphTextureName[i])
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, glyphTextureName[i]);
            boundTextureName = glyphTextureName[i];
        }

        int j = glyphWidth[par1] >>> 4;
        int k = glyphWidth[par1] & 0xf;
        float f = j;
        float f1 = k + 1;
        float f2 = (float)((par1 % 16) * 16) + f;
        float f3 = ((par1 & 0xff) / 16) * 16;
        float f4 = f1 - f - 0.02F;
        float f5 = par2 ? 1.0F : 0.0F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f2 / 256F, f3 / 256F);
        GL11.glVertex3f(posX + f5, posY, 0.0F);
        GL11.glTexCoord2f(f2 / 256F, (f3 + 15.98F) / 256F);
        GL11.glVertex3f(posX - f5, posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f2 + f4) / 256F, f3 / 256F);
        GL11.glVertex3f(posX + f4 / 2.0F + f5, posY, 0.0F);
        GL11.glTexCoord2f((f2 + f4) / 256F, (f3 + 15.98F) / 256F);
        GL11.glVertex3f((posX + f4 / 2.0F) - f5, posY + 7.99F, 0.0F);
        GL11.glEnd();
        return (f1 - f) / 2.0F + 1.0F;
    }

    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String par1Str, int par2, int par3, int par4)
    {
        if (bidiFlag)
        {
            par1Str = bidiReorder(par1Str);
        }

        int i = func_50101_a(par1Str, par2 + 1, par3 + 1, par4, true);
        i = Math.max(i, func_50101_a(par1Str, par2, par3, par4, false));
        return i;
    }

    /**
     * Draws the specified string.
     */
    public void drawString(String par1Str, int par2, int par3, int par4)
    {
        if (bidiFlag)
        {
            par1Str = bidiReorder(par1Str);
        }

        func_50101_a(par1Str, par2, par3, par4, false);
    }

    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidiReorder(String par1Str)
    {
        if (par1Str == null || !Bidi.requiresBidi(par1Str.toCharArray(), 0, par1Str.length()))
        {
            return par1Str;
        }

        Bidi bidi = new Bidi(par1Str, -2);
        byte abyte0[] = new byte[bidi.getRunCount()];
        String as[] = new String[abyte0.length];

        for (int i = 0; i < abyte0.length; i++)
        {
            int j = bidi.getRunStart(i);
            int k = bidi.getRunLimit(i);
            int i1 = bidi.getRunLevel(i);
            String s = par1Str.substring(j, k);
            abyte0[i] = (byte)i1;
            as[i] = s;
        }

        String as1[] = (String[])as.clone();
        Bidi.reorderVisually(abyte0, 0, as, 0, abyte0.length);
        StringBuilder stringbuilder = new StringBuilder();
        label0:

        for (int l = 0; l < as.length; l++)
        {
            byte byte0 = abyte0[l];
            int j1 = 0;

            do
            {
                if (j1 >= as1.length)
                {
                    break;
                }

                if (as1[j1].equals(as[l]))
                {
                    byte0 = abyte0[j1];
                    break;
                }

                j1++;
            }
            while (true);

            if ((byte0 & 1) == 0)
            {
                stringbuilder.append(as[l]);
                continue;
            }

            j1 = as[l].length() - 1;

            do
            {
                if (j1 < 0)
                {
                    continue label0;
                }

                char c = as[l].charAt(j1);

                if (c == '(')
                {
                    c = ')';
                }
                else if (c == ')')
                {
                    c = '(';
                }

                stringbuilder.append(c);
                j1--;
            }
            while (true);
        }

        return stringbuilder.toString();
    }

    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String par1Str, boolean par2)
    {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;

        for (int i = 0; i < par1Str.length(); i++)
        {
            char c = par1Str.charAt(i);

            if (c == '\247' && i + 1 < par1Str.length())
            {
                int j = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(i + 1));

                if (j < 16)
                {
                    flag = false;
                    flag1 = false;
                    flag4 = false;
                    flag3 = false;
                    flag2 = false;

                    if (j < 0 || j > 15)
                    {
                        j = 15;
                    }

                    if (par2)
                    {
                        j += 16;
                    }

                    int l = colorCode[j];
                    GL11.glColor4f((float)(l >> 16) / 255F, (float)(l >> 8 & 0xff) / 255F, (float)(l & 0xff) / 255F, field_50117_q);
                }
                else if (j == 16)
                {
                    flag = true;
                }
                else if (j == 17)
                {
                    flag1 = true;
                }
                else if (j == 18)
                {
                    flag4 = true;
                }
                else if (j == 19)
                {
                    flag3 = true;
                }
                else if (j == 20)
                {
                    flag2 = true;
                }
                else if (j == 21)
                {
                    flag = false;
                    flag1 = false;
                    flag4 = false;
                    flag3 = false;
                    flag2 = false;
                    GL11.glColor4f(field_50115_n, field_50116_o, field_50118_p, field_50117_q);
                }

                i++;
                continue;
            }

            int k = ChatAllowedCharacters.allowedCharacters.indexOf(c);

            if (flag && k > 0)
            {
                int i1;

                do
                {
                    i1 = fontRandom.nextInt(ChatAllowedCharacters.allowedCharacters.length());
                }
                while ((int)charWidth[k + 32] != (int)charWidth[i1 + 32]);

                k = i1;
            }

            float f = func_50112_a(k, c, flag2);

            if (flag1)
            {
                posX++;
                func_50112_a(k, c, flag2);
                posX--;
                f++;
            }

            if (flag4)
            {
                Tessellator tessellator = Tessellator.instance;
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tessellator.startDrawingQuads();
                tessellator.addVertex(posX, posY + (float)(FONT_HEIGHT / 2), 0.0D);
                tessellator.addVertex(posX + f, posY + (float)(FONT_HEIGHT / 2), 0.0D);
                tessellator.addVertex(posX + f, (posY + (float)(FONT_HEIGHT / 2)) - 1.0F, 0.0D);
                tessellator.addVertex(posX, (posY + (float)(FONT_HEIGHT / 2)) - 1.0F, 0.0D);
                tessellator.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            if (flag3)
            {
                Tessellator tessellator1 = Tessellator.instance;
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tessellator1.startDrawingQuads();
                int j1 = flag3 ? -1 : 0;
                tessellator1.addVertex(posX + (float)j1, posY + (float)FONT_HEIGHT, 0.0D);
                tessellator1.addVertex(posX + f, posY + (float)FONT_HEIGHT, 0.0D);
                tessellator1.addVertex(posX + f, (posY + (float)FONT_HEIGHT) - 1.0F, 0.0D);
                tessellator1.addVertex(posX + (float)j1, (posY + (float)FONT_HEIGHT) - 1.0F, 0.0D);
                tessellator1.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            posX += f;
        }
    }

    public int func_50101_a(String par1Str, int par2, int par3, int par4, boolean par5)
    {
        if (par1Str != null)
        {
            boundTextureName = 0;

            if ((par4 & 0xfc000000) == 0)
            {
                par4 |= 0xff000000;
            }

            if (par5)
            {
                par4 = (par4 & 0xfcfcfc) >> 2 | par4 & 0xff000000;
            }

            field_50115_n = (float)(par4 >> 16 & 0xff) / 255F;
            field_50116_o = (float)(par4 >> 8 & 0xff) / 255F;
            field_50118_p = (float)(par4 & 0xff) / 255F;
            field_50117_q = (float)(par4 >> 24 & 0xff) / 255F;
            GL11.glColor4f(field_50115_n, field_50116_o, field_50118_p, field_50117_q);
            posX = par2;
            posY = par3;
            renderStringAtPos(par1Str, par5);
            return (int)posX;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int getStringWidth(String par1Str)
    {
        checkUpdated();

        if (par1Str == null)
        {
            return 0;
        }

        float f = 0.0F;
        boolean flag = false;

        for (int i = 0; i < par1Str.length(); i++)
        {
            char c = par1Str.charAt(i);
            float f1 = getCharWidthFloat(c);

            if (f1 < 0.0F && i < par1Str.length() - 1)
            {
                char c1 = par1Str.charAt(++i);

                if (c1 == 'l' || c1 == 'L')
                {
                    flag = true;
                }
                else if (c1 == 'r' || c1 == 'R')
                {
                    flag = false;
                }

                f1 = getCharWidthFloat(c1);
            }

            f += f1;

            if (flag)
            {
                f++;
            }
        }

        return Math.round(f);
    }

    public int func_50105_a(char par1)
    {
        return Math.round(getCharWidthFloat(par1));
    }

    private float getCharWidthFloat(char c)
    {
        if (c == '\247')
        {
            return -1F;
        }

        int i = ChatAllowedCharacters.allowedCharacters.indexOf(c);

        if (i >= 0 && !unicodeFlag)
        {
            return charWidth[i + 32];
        }

        if (glyphWidth[c] != 0)
        {
            int j = glyphWidth[c] >> 4;
            int k = glyphWidth[c] & 0xf;

            if (k > 7)
            {
                k = 15;
                j = 0;
            }

            return (float)((++k - j) / 2 + 1);
        }
        else
        {
            return 0.0F;
        }
    }

    public String func_50107_a(String par1Str, int par2)
    {
        return func_50104_a(par1Str, par2, false);
    }

    public String func_50104_a(String par1Str, int par2, boolean par3)
    {
        StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        int i = par3 ? par1Str.length() - 1 : 0;
        byte byte0 = (byte)(par3 ? -1 : 1);
        boolean flag = false;
        boolean flag1 = false;

        for (int j = i; j >= 0 && j < par1Str.length() && f < (float)par2; j += byte0)
        {
            char c = par1Str.charAt(j);
            float f1 = getCharWidthFloat(c);

            if (flag)
            {
                flag = false;

                if (c == 'l' || c == 'L')
                {
                    flag1 = true;
                }
                else if (c == 'r' || c == 'R')
                {
                    flag1 = false;
                }
            }
            else if (f1 < 0.0F)
            {
                flag = true;
            }
            else
            {
                f += f1;

                if (flag1)
                {
                    f++;
                }
            }

            if (f > (float)par2)
            {
                break;
            }

            if (par3)
            {
                stringbuilder.insert(0, c);
            }
            else
            {
                stringbuilder.append(c);
            }
        }

        return stringbuilder.toString();
    }

    /**
     * Splits and draws a String with wordwrap (maximum length is parameter k)
     */
    public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5)
    {
        checkUpdated();

        if (bidiFlag)
        {
            par1Str = bidiReorder(par1Str);
        }

        renderSplitStringNoShadow(par1Str, par2, par3, par4, par5);
    }

    /**
     * renders a multi-line string with wordwrap (maximum length is parameter k) by means of renderSplitString
     */
    private void renderSplitStringNoShadow(String par1Str, int par2, int par3, int par4, int par5)
    {
        renderSplitString(par1Str, par2, par3, par4, par5, false);
    }

    /**
     * Perform actual work of rendering a multi-line string with wordwrap (maximum length is parameter k) and with
     * darkre drop shadow color if flag is set
     */
    private void renderSplitString(String par1Str, int par2, int par3, int par4, int par5, boolean par6)
    {
        checkUpdated();
        String as[] = par1Str.split("\n");

        if (as.length > 1)
        {
            for (int i = 0; i < as.length; i++)
            {
                renderSplitStringNoShadow(as[i], par2, par3, par4, par5);
                par3 += splitStringWidth(as[i], par4);
            }

            return;
        }

        String as1[] = par1Str.split(" ");
        int j = 0;
        String s = "";

        do
        {
            if (j >= as1.length)
            {
                break;
            }

            String s1;

            for (s1 = (new StringBuilder()).append(s).append(as1[j++]).append(" ").toString(); j < as1.length && getStringWidth((new StringBuilder()).append(s1).append(as1[j]).toString()) < par4; s1 = (new StringBuilder()).append(s1).append(as1[j++]).append(" ").toString()) { }

            int k;

            for (; getStringWidth(s1) > par4; s1 = (new StringBuilder()).append(s).append(s1.substring(k)).toString())
            {
                for (k = 0; getStringWidth(s1.substring(0, k + 1)) <= par4; k++) { }

                if (s1.substring(0, k).trim().length() <= 0)
                {
                    continue;
                }

                String s2 = s1.substring(0, k);

                if (s2.lastIndexOf("\247") >= 0)
                {
                    s = (new StringBuilder()).append("\247").append(s2.charAt(s2.lastIndexOf("\247") + 1)).toString();
                }

                func_50101_a(s2, par2, par3, par5, par6);
                par3 += FONT_HEIGHT;
            }

            if (getStringWidth(s1.trim()) > 0)
            {
                if (s1.lastIndexOf("\247") >= 0)
                {
                    s = (new StringBuilder()).append("\247").append(s1.charAt(s1.lastIndexOf("\247") + 1)).toString();
                }

                func_50101_a(s1, par2, par3, par5, par6);
                par3 += FONT_HEIGHT;
            }
        }
        while (true);
    }

    /**
     * Returns the width of the wordwrapped String (maximum length is parameter k)
     */
    public int splitStringWidth(String par1Str, int par2)
    {
        checkUpdated();
        String as[] = par1Str.split("\n");

        if (as.length > 1)
        {
            int i = 0;

            for (int j = 0; j < as.length; j++)
            {
                i += splitStringWidth(as[j], par2);
            }

            return i;
        }

        String as1[] = par1Str.split(" ");
        int k = 0;
        int l = 0;

        do
        {
            if (k >= as1.length)
            {
                break;
            }

            String s;

            for (s = (new StringBuilder()).append(as1[k++]).append(" ").toString(); k < as1.length && getStringWidth((new StringBuilder()).append(s).append(as1[k]).toString()) < par2; s = (new StringBuilder()).append(s).append(as1[k++]).append(" ").toString()) { }

            int i1;

            for (; getStringWidth(s) > par2; s = s.substring(i1))
            {
                for (i1 = 0; getStringWidth(s.substring(0, i1 + 1)) <= par2; i1++) { }

                if (s.substring(0, i1).trim().length() > 0)
                {
                    l += FONT_HEIGHT;
                }
            }

            if (s.trim().length() > 0)
            {
                l += FONT_HEIGHT;
            }
        }
        while (true);

        if (l < FONT_HEIGHT)
        {
            l += FONT_HEIGHT;
        }

        return l;
    }

    /**
     * Set unicodeFlag controlling whether strings should be rendered with Unicode fonts instead of the default.png
     * font.
     */
    public void setUnicodeFlag(boolean par1)
    {
        unicodeFlag = par1;
    }

    /**
     * Set bidiFlag to control if the Unicode Bidirectional Algorithm should be run before rendering any string.
     */
    public void setBidiFlag(boolean par1)
    {
        bidiFlag = par1;
    }

    public java.util.List func_50108_c(String par1Str, int par2)
    {
        return Arrays.asList(func_50113_d(par1Str, par2).split("\n"));
    }

    String func_50113_d(String par1Str, int par2)
    {
        int i = func_50102_e(par1Str, par2);

        if (par1Str.length() <= i)
        {
            return par1Str;
        }
        else
        {
            String s = par1Str.substring(0, i);
            String s1 = (new StringBuilder()).append(func_50114_c(s)).append(par1Str.substring(i + (par1Str.charAt(i) == ' ' ? 1 : 0))).toString();
            return (new StringBuilder()).append(s).append("\n").append(func_50113_d(s1, par2)).toString();
        }
    }

    private int func_50102_e(String par1Str, int par2)
    {
        int i = par1Str.length();
        float f = 0.0F;
        int j = 0;
        int k = -1;
        boolean flag = false;

        do
        {
            if (j >= i)
            {
                break;
            }

            char c = par1Str.charAt(j);

            switch (c)
            {
                case 167:
                    if (j != i)
                    {
                        char c1 = par1Str.charAt(++j);

                        if (c1 == 'l' || c1 == 'L')
                        {
                            flag = true;
                        }
                        else if (c1 == 'r' || c1 == 'R')
                        {
                            flag = false;
                        }
                    }

                    break;

                case 32:
                    k = j;

                default:
                    f += getCharWidthFloat(c);

                    if (flag)
                    {
                        f++;
                    }

                    break;
            }

            if (c == '\n')
            {
                k = ++j;
                break;
            }

            if (f > (float)par2)
            {
                break;
            }

            j++;
        }
        while (true);

        if (j != i && k != -1 && k < j)
        {
            return k;
        }
        else
        {
            return j;
        }
    }

    private static boolean func_50110_b(char par0)
    {
        return par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F';
    }

    private static boolean func_50109_c(char par0)
    {
        return par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R';
    }

    private static String func_50114_c(String par0Str)
    {
        String s = "";
        int i = -1;
        int j = par0Str.length();

        do
        {
            if ((i = par0Str.indexOf('\247', i + 1)) == -1)
            {
                break;
            }

            if (i < j - 1)
            {
                char c = par0Str.charAt(i + 1);

                if (func_50110_b(c))
                {
                    s = (new StringBuilder()).append("\247").append(c).toString();
                }
                else if (func_50109_c(c))
                {
                    s = (new StringBuilder()).append(s).append("\247").append(c).toString();
                }
            }
        }
        while (true);

        return s;
    }

    public static String func_52014_d(String par0Str)
    {
        return field_52015_r.matcher(par0Str).replaceAll("");
    }
}
