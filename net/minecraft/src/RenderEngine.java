package net.minecraft.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;

public class RenderEngine
{
    /** Use mipmaps for all bound textures (unused at present) */
    public static boolean useMipmaps = true;
    private HashMap textureMap;

    /** Texture contents map (key: texture name, value: int[] contents) */
    private HashMap textureContentsMap;

    /** A mapping from GL texture names (integers) to BufferedImage instances */
    private IntHashMap textureNameToImageMap;

    /** An IntBuffer storing 1 int used as scratch space in RenderEngine */
    private IntBuffer singleIntBuffer;

    /** Stores the image data for the texture. */
    private ByteBuffer imageData;
    public java.util.List textureList;

    /** A mapping from image URLs to ThreadDownloadImageData instances */
    private Map urlToImageDataMap;

    /** Reference to the GameSettings object */
    private GameSettings options;

    /** Flag set when a texture should not be repeated */
    public boolean clampTexture;

    /** Flag set when a texture should use blurry resizing */
    public boolean blurTexture;

    /** Texture pack */
    public TexturePackList texturePack;

    /** Missing texture image */
    private BufferedImage missingTextureImage;
    private int field_48512_n;
    public int terrainTextureId;
    public int guiItemsTextureId;
    public int ctmTextureId;
    private boolean hdTexturesInstalled;
    private Map textureDimensionsMap;
    private Map textureDataMap;
    private int tickCounter;
    private ByteBuffer mipImageDatas[];
    private boolean dynamicTexturesUpdated;
    private Map textureFxMap;
    private Map mipDataBufsMap;
    private Map customAnimationMap;
    private CustomAnimation textureAnimations[];
    public static Logger log = Logger.getAnonymousLogger();

    public RenderEngine(TexturePackList par1TexturePackList, GameSettings par2GameSettings)
    {
        terrainTextureId = -1;
        guiItemsTextureId = -1;
        ctmTextureId = -1;
        hdTexturesInstalled = false;
        textureDimensionsMap = new HashMap();
        textureDataMap = new HashMap();
        tickCounter = 0;
        dynamicTexturesUpdated = false;
        textureFxMap = new IdentityHashMap();
        mipDataBufsMap = new HashMap();
        customAnimationMap = new HashMap();
        textureAnimations = null;
        textureMap = new HashMap();
        textureContentsMap = new HashMap();
        textureNameToImageMap = new IntHashMap();
        singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
        allocateImageData(256, 256);
        textureList = new ArrayList();
        urlToImageDataMap = new HashMap();
        clampTexture = false;
        blurTexture = false;
        missingTextureImage = new BufferedImage(64, 64, 2);
        field_48512_n = 16;
        texturePack = par1TexturePackList;
        options = par2GameSettings;
        Graphics g = missingTextureImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.BLACK);
        g.drawString("missingtex", 1, 10);
        g.dispose();
    }

    public int[] getTextureContents(String par1Str)
    {
        TexturePackBase texturepackbase = texturePack.selectedTexturePack;
        int ai[] = (int[])textureContentsMap.get(par1Str);

        if (ai != null)
        {
            return ai;
        }

        try
        {
            int ai1[] = null;

            if (par1Str.startsWith("##"))
            {
                ai1 = getImageContentsAndAllocate(unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(2)))));
            }
            else if (par1Str.startsWith("%clamp%"))
            {
                clampTexture = true;
                ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(7))));
                clampTexture = false;
            }
            else if (par1Str.startsWith("%blur%"))
            {
                blurTexture = true;
                clampTexture = true;
                ai1 = getImageContentsAndAllocate(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(6))));
                clampTexture = false;
                blurTexture = false;
            }
            else
            {
                InputStream inputstream = texturepackbase.getResourceAsStream(par1Str);

                if (inputstream == null)
                {
                    ai1 = getImageContentsAndAllocate(missingTextureImage);
                }
                else
                {
                    ai1 = getImageContentsAndAllocate(readTextureImage(inputstream));
                }
            }

            textureContentsMap.put(par1Str, ai1);
            return ai1;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        int ai2[] = getImageContentsAndAllocate(missingTextureImage);
        textureContentsMap.put(par1Str, ai2);
        return ai2;
    }

    private int[] getImageContentsAndAllocate(BufferedImage par1BufferedImage)
    {
        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        int ai[] = new int[i * j];
        par1BufferedImage.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    private int[] getImageContents(BufferedImage par1BufferedImage, int par2ArrayOfInteger[])
    {
        int i = par1BufferedImage.getWidth();
        int j = par1BufferedImage.getHeight();
        par1BufferedImage.getRGB(0, 0, i, j, par2ArrayOfInteger, 0, i);
        return par2ArrayOfInteger;
    }

    public int getTexture(String par1Str)
    {
        TexturePackBase texturepackbase = texturePack.selectedTexturePack;
        Integer integer = (Integer)textureMap.get(par1Str);

        if (integer != null)
        {
            return integer.intValue();
        }

        try
        {
            if (Reflector.hasClass(1))
            {
                Reflector.callVoid(18, new Object[]
                        {
                            par1Str
                        });
            }

            singleIntBuffer.clear();
            GLAllocation.generateTextureNames(singleIntBuffer);

            if (Tessellator.renderingWorldRenderer)
            {
                System.out.printf("Warning: Texture %s not preloaded, will cause render glitches!\n", new Object[]
                        {
                            par1Str
                        });
            }

            int i = singleIntBuffer.get(0);
            Config.dbg((new StringBuilder()).append("setupTexture: \"").append(par1Str).append("\", id: ").append(i).toString());

            if (par1Str.startsWith("##"))
            {
                setupTexture(unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(2)))), i);
            }
            else if (par1Str.startsWith("%clamp%"))
            {
                clampTexture = true;
                setupTexture(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(7))), i);
                clampTexture = false;
            }
            else if (par1Str.startsWith("%blur%"))
            {
                blurTexture = true;
                setupTexture(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(6))), i);
                blurTexture = false;
            }
            else if (par1Str.startsWith("%blurclamp%"))
            {
                blurTexture = true;
                clampTexture = true;
                setupTexture(readTextureImage(texturepackbase.getResourceAsStream(par1Str.substring(11))), i);
                blurTexture = false;
                clampTexture = false;
            }
            else
            {
                InputStream inputstream = texturepackbase.getResourceAsStream(par1Str);

                if (inputstream == null)
                {
                    setupTexture(missingTextureImage, i);
                }
                else
                {
                    if (par1Str.equals("/terrain.png"))
                    {
                        terrainTextureId = i;
                    }

                    if (par1Str.equals("/gui/items.png"))
                    {
                        guiItemsTextureId = i;
                    }

                    if (par1Str.equals("/ctm.png"))
                    {
                        ctmTextureId = i;
                    }

                    setupTexture(readTextureImage(inputstream), i);
                }
            }

            textureMap.put(par1Str, Integer.valueOf(i));

            if (Reflector.hasClass(1))
            {
                Reflector.callVoid(19, new Object[]
                        {
                            par1Str, Integer.valueOf(i)
                        });
            }

            return i;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        GLAllocation.generateTextureNames(singleIntBuffer);
        int j = singleIntBuffer.get(0);
        setupTexture(missingTextureImage, j);
        textureMap.put(par1Str, Integer.valueOf(j));
        return j;
    }

    /**
     * Takes an image with multiple 16-pixel-wide columns and creates a new 16-pixel-wide image where the columns are
     * stacked vertically
     */
    private BufferedImage unwrapImageByColumns(BufferedImage par1BufferedImage)
    {
        int i = par1BufferedImage.getWidth() / 16;
        BufferedImage bufferedimage = new BufferedImage(16, par1BufferedImage.getHeight() * i, 2);
        Graphics g = bufferedimage.getGraphics();

        for (int j = 0; j < i; j++)
        {
            g.drawImage(par1BufferedImage, -j * 16, j * par1BufferedImage.getHeight(), null);
        }

        g.dispose();
        return bufferedimage;
    }

    /**
     * Copy the supplied image onto a newly-allocated OpenGL texture, returning the allocated texture name
     */
    public int allocateAndSetupTexture(BufferedImage par1BufferedImage)
    {
        singleIntBuffer.clear();
        GLAllocation.generateTextureNames(singleIntBuffer);
        int i = singleIntBuffer.get(0);
        setupTexture(par1BufferedImage, i);
        textureNameToImageMap.addKey(i, par1BufferedImage);
        return i;
    }

    /**
     * Copy the supplied image onto the specified OpenGL texture
     */
    public void setupTexture(BufferedImage par1BufferedImage, int par2)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par2);
        boolean flag = useMipmaps && Config.isUseMipmaps();

        if (flag && par2 != guiItemsTextureId)
        {
            int i = Config.getMipmapType();
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, i);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            if (GLContext.getCapabilities().OpenGL12)
            {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
                int k = Config.getMipmapLevel();

                if (k >= 4)
                {
                    int i1 = Math.min(par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
                    k = getMaxMipmapLevel(i1) - 4;

                    if (k < 0)
                    {
                        k = 0;
                    }
                }

                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, k);
            }
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }

        if (blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        int j = par1BufferedImage.getWidth();
        int l = par1BufferedImage.getHeight();
        setTextureDimension(par2, new Dimension(j, l));

        if (Reflector.hasClass(7))
        {
            Reflector.callVoid(70, new Object[]
                    {
                        Integer.valueOf(par2), Integer.valueOf(j), Integer.valueOf(l), textureList
                    });
        }

        int ai[] = new int[j * l];
        byte abyte0[] = new byte[j * l * 4];
        par1BufferedImage.getRGB(0, 0, j, l, ai, 0, j);
        int ai1[] = new int[256];

        if (flag && isTerrainTexture(par2))
        {
            for (int j1 = 0; j1 < 16; j1++)
            {
                for (int l1 = 0; l1 < 16; l1++)
                {
                    ai1[j1 * 16 + l1] = getAverageOpaqueColor(ai, l1, j1, j, l);
                }
            }
        }

        for (int k1 = 0; k1 < ai.length; k1++)
        {
            int i2 = ai[k1] >> 24 & 0xff;
            int j2 = ai[k1] >> 16 & 0xff;
            int k2 = ai[k1] >> 8 & 0xff;
            int l2 = ai[k1] & 0xff;

            if (options != null && options.anaglyph)
            {
                int i3 = (j2 * 30 + k2 * 59 + l2 * 11) / 100;
                int k3 = (j2 * 30 + k2 * 70) / 100;
                int i4 = (j2 * 30 + l2 * 70) / 100;
                j2 = i3;
                k2 = k3;
                l2 = i4;
            }

            if (i2 == 0)
            {
                if (isTerrainTexture(par2))
                {
                    j2 = 255;
                    k2 = 255;
                    l2 = 255;

                    if (flag)
                    {
                        int j3 = k1 % j;
                        int l3 = k1 / j;
                        int j4 = j3 / (j / 16);
                        int k4 = l3 / (l / 16);
                        int l4 = ai1[k4 * 16 + j4];

                        if (l4 != 0)
                        {
                            j2 = l4 >> 16 & 0xff;
                            k2 = l4 >> 8 & 0xff;
                            l2 = l4 & 0xff;
                        }
                    }
                }
                else
                {
                    j2 = 0;
                    k2 = 0;
                    l2 = 0;
                }
            }

            abyte0[k1 * 4 + 0] = (byte)j2;
            abyte0[k1 * 4 + 1] = (byte)k2;
            abyte0[k1 * 4 + 2] = (byte)l2;
            abyte0[k1 * 4 + 3] = (byte)i2;
        }

        checkImageDataSize(j, l);
        imageData.clear();
        imageData.put(abyte0);
        imageData.position(0).limit(abyte0.length);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, j, l, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);

        if (useMipmaps)
        {
            generateMipMaps(imageData, j, l);
        }
    }

    private int getAverageOpaqueColor(int ai[], int i, int j, int k, int l)
    {
        int i1 = k / 16;
        int j1 = l / 16;
        int k1 = j * j1 * k + i * i1;
        long l1 = 0L;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;

        for (int i2 = 0; i2 < j1; i2++)
        {
            for (int j2 = 0; j2 < i1; j2++)
            {
                int i3 = k1 + i2 * k + j2;
                int k3 = ai[i3] >> 24 & 0xff;

                if (k3 != 0)
                {
                    int j4 = ai[i3] >> 16 & 0xff;
                    int k4 = ai[i3] >> 8 & 0xff;
                    int i5 = ai[i3] & 0xff;
                    l1 += j4;
                    l2 += k4;
                    l3 += i5;
                    l4++;
                }
            }
        }

        if (l4 <= 0L)
        {
            return 0;
        }
        else
        {
            char c = '\377';
            int k2 = (int)(l1 / l4);
            int j3 = (int)(l2 / l4);
            int i4 = (int)(l3 / l4);
            return c << 24 | k2 << 16 | j3 << 8 | i4;
        }
    }

    private boolean isTerrainTexture(int i)
    {
        if (i == terrainTextureId)
        {
            return true;
        }

        return i == ctmTextureId;
    }

    private void generateMipMaps(ByteBuffer bytebuffer, int i, int j)
    {
        ByteBuffer bytebuffer1 = bytebuffer;
        int k = 1;

        do
        {
            if (k > 16)
            {
                break;
            }

            int l = i >> k - 1;
            int i1 = i >> k;
            int j1 = j >> k;

            if (i1 <= 0 || j1 <= 0)
            {
                break;
            }

            ByteBuffer bytebuffer2 = mipImageDatas[k - 1];
            bytebuffer2.limit(i1 * j1 * 4);

            for (int k1 = 0; k1 < i1; k1++)
            {
                for (int l1 = 0; l1 < j1; l1++)
                {
                    int i2 = bytebuffer1.getInt((k1 * 2 + 0 + (l1 * 2 + 0) * l) * 4);
                    int j2 = bytebuffer1.getInt((k1 * 2 + 1 + (l1 * 2 + 0) * l) * 4);
                    int k2 = bytebuffer1.getInt((k1 * 2 + 1 + (l1 * 2 + 1) * l) * 4);
                    int l2 = bytebuffer1.getInt((k1 * 2 + 0 + (l1 * 2 + 1) * l) * 4);
                    int i3 = alphaBlend(i2, j2, k2, l2);
                    bytebuffer2.putInt((k1 + l1 * i1) * 4, i3);
                }
            }

            bytebuffer2.rewind();
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, k, GL11.GL_RGBA, i1, j1, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytebuffer2);
            bytebuffer1 = bytebuffer2;
            k++;
        }
        while (true);
    }

    public void createTextureFromBytes(int par1ArrayOfInteger[], int par2, int par3, int par4)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par4);

        if (useMipmaps && Config.isUseMipmaps())
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }

        if (blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        byte abyte0[] = new byte[par2 * par3 * 4];

        for (int i = 0; i < par1ArrayOfInteger.length; i++)
        {
            int j = par1ArrayOfInteger[i] >> 24 & 0xff;
            int k = par1ArrayOfInteger[i] >> 16 & 0xff;
            int l = par1ArrayOfInteger[i] >> 8 & 0xff;
            int i1 = par1ArrayOfInteger[i] & 0xff;

            if (options != null && options.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            abyte0[i * 4 + 0] = (byte)k;
            abyte0[i * 4 + 1] = (byte)l;
            abyte0[i * 4 + 2] = (byte)i1;
            abyte0[i * 4 + 3] = (byte)j;
        }

        imageData.clear();
        imageData.put(abyte0);
        imageData.position(0).limit(abyte0.length);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, par2, par3, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
    }

    /**
     * Deletes a single GL texture
     */
    public void deleteTexture(int par1)
    {
        textureNameToImageMap.removeObject(par1);
        singleIntBuffer.clear();
        singleIntBuffer.put(par1);
        singleIntBuffer.flip();
        GL11.glDeleteTextures(singleIntBuffer);
    }

    /**
     * Takes a URL of a downloadable image and the name of the local image to be used as a fallback.  If the image has
     * been downloaded, returns the GL texture of the downloaded image, otherwise returns the GL texture of the fallback
     * image.
     */
    public int getTextureForDownloadableImage(String par1Str, String par2Str)
    {
        if (Config.isRandomMobs())
        {
            int i = RandomMobs.getTexture(par1Str, par2Str);

            if (i >= 0)
            {
                return i;
            }
        }

        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata != null && threaddownloadimagedata.image != null && !threaddownloadimagedata.textureSetupComplete)
        {
            if (threaddownloadimagedata.textureName < 0)
            {
                threaddownloadimagedata.textureName = allocateAndSetupTexture(threaddownloadimagedata.image);
            }
            else
            {
                setupTexture(threaddownloadimagedata.image, threaddownloadimagedata.textureName);
            }

            threaddownloadimagedata.textureSetupComplete = true;
        }

        if (threaddownloadimagedata == null || threaddownloadimagedata.textureName < 0)
        {
            if (par2Str == null)
            {
                return -1;
            }
            else
            {
                return getTexture(par2Str);
            }
        }
        else
        {
            return threaddownloadimagedata.textureName;
        }
    }

    /**
     * Return a ThreadDownloadImageData instance for the given URL.  If it does not already exist, it is created and
     * uses the passed ImageBuffer.  If it does, its reference count is incremented.
     */
    public ThreadDownloadImageData obtainImageData(String par1Str, ImageBuffer par2ImageBuffer)
    {
        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata == null)
        {
            urlToImageDataMap.put(par1Str, new ThreadDownloadImageData(par1Str, par2ImageBuffer));
        }
        else
        {
            threaddownloadimagedata.referenceCount++;
        }

        return threaddownloadimagedata;
    }

    /**
     * Decrements the reference count for a given URL, deleting the image data if the reference count hits 0
     */
    public void releaseImageData(String par1Str)
    {
        ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)urlToImageDataMap.get(par1Str);

        if (threaddownloadimagedata != null)
        {
            threaddownloadimagedata.referenceCount--;

            if (threaddownloadimagedata.referenceCount == 0)
            {
                if (threaddownloadimagedata.textureName >= 0)
                {
                    deleteTexture(threaddownloadimagedata.textureName);
                }

                urlToImageDataMap.remove(par1Str);
            }
        }
    }

    public void registerTextureFX(TextureFX par1TextureFX)
    {
        if (Reflector.hasClass(7))
        {
            Reflector.callVoid(71, new Object[]
                    {
                        par1TextureFX
                    });
        }

        int i = getTextureId(par1TextureFX);

        for (int j = 0; j < textureList.size(); j++)
        {
            TextureFX texturefx = (TextureFX)textureList.get(j);
            int k = getTextureId(texturefx);

            if (k == i && texturefx.iconIndex == par1TextureFX.iconIndex)
            {
                textureList.remove(j);
                j--;
                Config.log((new StringBuilder()).append("TextureFX removed: ").append(texturefx).append(", texId: ").append(k).append(", index: ").append(texturefx.iconIndex).toString());
            }
        }

        if (par1TextureFX instanceof TextureHDFX)
        {
            TextureHDFX texturehdfx = (TextureHDFX)par1TextureFX;
            texturehdfx.setTexturePackBase(texturePack.selectedTexturePack);
            Dimension dimension = getTextureDimensions(i);

            if (dimension != null)
            {
                texturehdfx.setTileWidth(dimension.width / 16);
            }
        }

        textureList.add(par1TextureFX);
        par1TextureFX.onTick();
        Config.log((new StringBuilder()).append("TextureFX registered: ").append(par1TextureFX).append(", texId: ").append(i).append(", index: ").append(par1TextureFX.iconIndex).toString());
        dynamicTexturesUpdated = false;
    }

    private int getTextureId(TextureFX texturefx)
    {
        Integer integer = (Integer)textureFxMap.get(texturefx);

        if (integer != null)
        {
            return integer.intValue();
        }
        else
        {
            int i = getBoundTexture();
            texturefx.bindImage(this);
            int j = getBoundTexture();
            bindTexture(i);
            textureFxMap.put(texturefx, new Integer(j));
            return j;
        }
    }

    private int getBoundTexture()
    {
        int i = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        return i;
    }

    private void generateMipMapsSub(int i, int j, int k, int l, ByteBuffer bytebuffer, int i1, boolean flag, String s)
    {
        ByteBuffer bytebuffer1 = bytebuffer;
        byte abyte0[][] = (byte[][])null;

        if (s.length() > 0)
        {
            abyte0 = (byte[][])mipDataBufsMap.get(s);

            if (abyte0 == null)
            {
                abyte0 = new byte[17][];
                mipDataBufsMap.put(s, abyte0);
            }
        }

        int j1 = 1;

        do
        {
            if (j1 > 16)
            {
                break;
            }

            int k1 = k >> j1 - 1;
            int l1 = k >> j1;
            int i2 = l >> j1;
            int j2 = i >> j1;
            int k2 = j >> j1;

            if (l1 <= 0 || i2 <= 0)
            {
                break;
            }

            ByteBuffer bytebuffer2 = mipImageDatas[j1 - 1];
            bytebuffer2.limit(l1 * i2 * 4);
            byte abyte1[] = null;

            if (abyte0 != null)
            {
                abyte1 = abyte0[j1];
            }

            if (abyte1 != null && abyte1.length != l1 * i2 * 4)
            {
                abyte1 = null;
            }

            if (abyte1 == null)
            {
                if (abyte0 != null)
                {
                    abyte1 = new byte[l1 * i2 * 4];
                }

                for (int l2 = 0; l2 < l1; l2++)
                {
                    for (int j3 = 0; j3 < i2; j3++)
                    {
                        int l3 = bytebuffer1.getInt((l2 * 2 + 0 + (j3 * 2 + 0) * k1) * 4);
                        int j4 = bytebuffer1.getInt((l2 * 2 + 1 + (j3 * 2 + 0) * k1) * 4);
                        int l4 = bytebuffer1.getInt((l2 * 2 + 1 + (j3 * 2 + 1) * k1) * 4);
                        int i5 = bytebuffer1.getInt((l2 * 2 + 0 + (j3 * 2 + 1) * k1) * 4);
                        int j5;

                        if (flag)
                        {
                            j5 = averageColor(averageColor(l3, j4), averageColor(l4, i5));
                        }
                        else
                        {
                            j5 = alphaBlend(l3, j4, l4, i5);
                        }

                        bytebuffer2.putInt((l2 + j3 * l1) * 4, j5);
                    }
                }

                if (abyte0 != null)
                {
                    bytebuffer2.rewind();
                    bytebuffer2.get(abyte1);
                    abyte0[j1] = abyte1;
                }
            }

            if (abyte1 != null)
            {
                bytebuffer2.rewind();
                bytebuffer2.put(abyte1);
            }

            bytebuffer2.rewind();

            for (int i3 = 0; i3 < i1; i3++)
            {
                for (int k3 = 0; k3 < i1; k3++)
                {
                    int i4 = i3 * l1;
                    int k4 = k3 * i2;
                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, j1, j2 + i4, k2 + k4, l1, i2, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytebuffer2);
                }
            }

            bytebuffer1 = bytebuffer2;
            j1++;
        }
        while (true);
    }

    public void updateDynamicTextures()
    {
        boolean flag = useMipmaps && Config.isUseMipmaps();
        checkHdTextures();
        tickCounter++;
        terrainTextureId = getTexture("/terrain.png");
        guiItemsTextureId = getTexture("/gui/items.png");
        ctmTextureId = getTexture("/ctm.png");
        StringBuffer stringbuffer = new StringBuffer();
        int i = -1;
        label0:

        for (int j = 0; j < textureList.size(); j++)
        {
            TextureFX texturefx = (TextureFX)textureList.get(j);
            texturefx.anaglyphEnabled = options.anaglyph;

            if (texturefx.getClass().getName().equals("ModTextureStatic") && dynamicTexturesUpdated)
            {
                continue;
            }

            int l = getTextureId(texturefx);
            Dimension dimension = getTextureDimensions(l);

            if (dimension == null)
            {
                throw new IllegalArgumentException((new StringBuilder()).append("Unknown dimensions for texture id: ").append(l).toString());
            }

            int j1 = dimension.width / 16;
            int k1 = dimension.height / 16;
            checkImageDataSize(dimension.width, dimension.height);
            imageData.limit(0);
            stringbuffer.setLength(0);
            boolean flag3 = updateCustomTexture(texturefx, l, imageData, dimension.width / 16, stringbuffer);

            if (flag3 && imageData.limit() <= 0)
            {
                continue;
            }

            if (imageData.limit() <= 0)
            {
                boolean flag4 = updateDefaultTexture(texturefx, l, imageData, dimension.width / 16, stringbuffer);

                if (flag4 && imageData.limit() <= 0)
                {
                    continue;
                }
            }

            if (imageData.limit() <= 0)
            {
                texturefx.onTick();

                if (Reflector.hasClass(7) && !Reflector.callBoolean(72, new Object[]
                        {
                            texturefx
                        }) || texturefx.imageData == null)
                {
                    continue;
                }
                int l1 = j1 * k1 * 4;

                if (texturefx.imageData.length == l1)
                {
                    imageData.clear();
                    imageData.put(texturefx.imageData);
                    imageData.position(0).limit(texturefx.imageData.length);
                }
                else
                {
                    copyScaled(texturefx.imageData, imageData, j1);
                }
            }

            if (l != i)
            {
                texturefx.bindImage(this);
                i = l;
            }

            boolean flag5 = scalesWithFastColor(texturefx);
            int i2 = 0;

            do
            {
                if (i2 >= texturefx.tileSize)
                {
                    continue label0;
                }

                for (int j2 = 0; j2 < texturefx.tileSize; j2++)
                {
                    int k2 = (texturefx.iconIndex % 16) * j1 + i2 * j1;
                    int l2 = (texturefx.iconIndex / 16) * k1 + j2 * k1;
                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, k2, l2, j1, k1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);

                    if (!flag || l == guiItemsTextureId)
                    {
                        continue;
                    }

                    String s = stringbuffer.toString();

                    if (i2 == 0 && j2 == 0)
                    {
                        generateMipMapsSub(k2, l2, j1, k1, imageData, texturefx.tileSize, flag5, s);
                    }
                }

                i2++;
            }
            while (true);
        }

        if (textureAnimations != null)
        {
            boolean flag1 = options.ofAnimatedTextures;

            for (int k = 0; k < textureAnimations.length; k++)
            {
                CustomAnimation customanimation = textureAnimations[k];
                int i1 = getTexture(customanimation.destTexture);

                if (i1 < 0)
                {
                    continue;
                }

                Dimension dimension1 = getTextureDimensions(i1);

                if (dimension1 == null)
                {
                    continue;
                }

                checkImageDataSize(dimension1.width, dimension1.height);
                imageData.limit(0);
                stringbuffer.setLength(0);
                boolean flag2 = customanimation.updateCustomTexture(imageData, flag1, dynamicTexturesUpdated, stringbuffer);

                if ((!flag2 || imageData.limit() > 0) && imageData.limit() > 0)
                {
                    bindTexture(i1);
                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, customanimation.destX, customanimation.destY, customanimation.frameWidth, customanimation.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
                }
            }
        }

        dynamicTexturesUpdated = true;
    }

    private int averageColor(int i, int j)
    {
        int k = (i & 0xff000000) >> 24 & 0xff;
        int l = (j & 0xff000000) >> 24 & 0xff;
        return ((k + l >> 1) << 24) + ((i & 0xfefefe) + (j & 0xfefefe) >> 1);
    }

    private int alphaBlend(int i, int j, int k, int l)
    {
        int i1 = alphaBlend(i, j);
        int j1 = alphaBlend(k, l);
        int k1 = alphaBlend(i1, j1);
        return k1;
    }

    /**
     * Uses the alpha of the two colors passed in to determine the contributions of each color.  If either of them has
     * an alpha greater than 0 then the returned alpha is 255 otherwise its zero if they are both zero. Args: color1,
     * color2
     */
    private int alphaBlend(int par1, int par2)
    {
        int i = (par1 & 0xff000000) >> 24 & 0xff;
        int j = (par2 & 0xff000000) >> 24 & 0xff;
        int k = (i + j) / 2;

        if (i == 0 && j == 0)
        {
            i = 1;
            j = 1;
        }
        else
        {
            if (i == 0)
            {
                par1 = par2;
                k /= 2;
            }

            if (j == 0)
            {
                par2 = par1;
                k /= 2;
            }
        }

        int l = (par1 >> 16 & 0xff) * i;
        int i1 = (par1 >> 8 & 0xff) * i;
        int j1 = (par1 & 0xff) * i;
        int k1 = (par2 >> 16 & 0xff) * j;
        int l1 = (par2 >> 8 & 0xff) * j;
        int i2 = (par2 & 0xff) * j;
        int j2 = (l + k1) / (i + j);
        int k2 = (i1 + l1) / (i + j);
        int l2 = (j1 + i2) / (i + j);
        return k << 24 | j2 << 16 | k2 << 8 | l2;
    }

    /**
     * Call setupTexture on all currently-loaded textures again to account for changes in rendering options
     */
    public void refreshTextures()
    {
        textureDataMap.clear();
        textureFxMap.clear();
        dynamicTexturesUpdated = false;
        Config.setTextureUpdateTime(System.currentTimeMillis());
        RandomMobs.resetTextures();
        mipDataBufsMap.clear();
        customAnimationMap.clear();
        TexturePackBase texturepackbase = texturePack.selectedTexturePack;
        int i;
        BufferedImage bufferedimage;

        for (Iterator iterator = textureNameToImageMap.getKeySet().iterator(); iterator.hasNext(); setupTexture(bufferedimage, i))
        {
            i = ((Integer)iterator.next()).intValue();
            bufferedimage = (BufferedImage)textureNameToImageMap.lookup(i);
        }

        for (Iterator iterator1 = urlToImageDataMap.values().iterator(); iterator1.hasNext();)
        {
            ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)iterator1.next();
            threaddownloadimagedata.textureSetupComplete = false;
        }

        Iterator iterator2 = textureMap.keySet().iterator();

        do
        {
            if (!iterator2.hasNext())
            {
                break;
            }

            String s = (String)iterator2.next();

            try
            {
                BufferedImage bufferedimage1;

                if (s.startsWith("##"))
                {
                    bufferedimage1 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s.substring(2))));
                }
                else if (s.startsWith("%clamp%"))
                {
                    clampTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(7)));
                }
                else if (s.startsWith("%blur%"))
                {
                    blurTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(6)));
                }
                else if (s.startsWith("%blurclamp%"))
                {
                    blurTexture = true;
                    clampTexture = true;
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s.substring(11)));
                }
                else
                {
                    bufferedimage1 = readTextureImage(texturepackbase.getResourceAsStream(s));
                }

                int j = ((Integer)textureMap.get(s)).intValue();
                setupTexture(bufferedimage1, j);
                blurTexture = false;
                clampTexture = false;
            }
            catch (Exception exception)
            {
                if (!"input == null!".equals(exception.getMessage()))
                {
                    exception.printStackTrace();
                }
            }
        }
        while (true);

        iterator2 = textureContentsMap.keySet().iterator();

        do
        {
            if (!iterator2.hasNext())
            {
                break;
            }

            String s1 = (String)iterator2.next();

            try
            {
                BufferedImage bufferedimage2;

                if (s1.startsWith("##"))
                {
                    bufferedimage2 = unwrapImageByColumns(readTextureImage(texturepackbase.getResourceAsStream(s1.substring(2))));
                }
                else if (s1.startsWith("%clamp%"))
                {
                    clampTexture = true;
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(7)));
                }
                else if (s1.startsWith("%blur%"))
                {
                    blurTexture = true;
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1.substring(6)));
                }
                else
                {
                    bufferedimage2 = readTextureImage(texturepackbase.getResourceAsStream(s1));
                }

                getImageContents(bufferedimage2, (int[])textureContentsMap.get(s1));
                blurTexture = false;
                clampTexture = false;
            }
            catch (Exception exception1)
            {
                if (!"input == null!".equals(exception1.getMessage()))
                {
                    exception1.printStackTrace();
                }
            }
        }
        while (true);

        registerCustomTexturesFX();
        CustomColorizer.update(this);
        ConnectedTextures.update(this);
        NaturalTextures.update(this);

        if (Reflector.hasClass(7))
        {
            Reflector.callVoid(73, new Object[]
                    {
                        this, texturepackbase, textureList
                    });
        }

        updateDynamicTextures();
    }

    /**
     * Returns a BufferedImage read off the provided input stream.  Args: inputStream
     */
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return bufferedimage;
    }

    public void bindTexture(int par1)
    {
        if (par1 < 0)
        {
            return;
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1);
            return;
        }
    }

    private void setTextureDimension(int i, Dimension dimension)
    {
        textureDimensionsMap.put(new Integer(i), dimension);

        if (i == terrainTextureId)
        {
            Config.setIconWidthTerrain(dimension.width / 16);
        }

        if (i == guiItemsTextureId)
        {
            Config.setIconWidthItems(dimension.width / 16);
        }

        updateDinamicTextures(i, dimension);
    }

    public Dimension getTextureDimensions(int i)
    {
        Dimension dimension = (Dimension)textureDimensionsMap.get(new Integer(i));
        return dimension;
    }

    private void updateDinamicTextures(int i, Dimension dimension)
    {
        for (int j = 0; j < textureList.size(); j++)
        {
            TextureFX texturefx = (TextureFX)textureList.get(j);
            int k = getTextureId(texturefx);

            if (k == i && (texturefx instanceof TextureHDFX))
            {
                TextureHDFX texturehdfx = (TextureHDFX)texturefx;
                texturehdfx.setTexturePackBase(texturePack.selectedTexturePack);
                texturehdfx.setTileWidth(dimension.width / 16);
                texturehdfx.onTick();
            }
        }
    }

    public boolean updateCustomTexture(TextureFX texturefx, int i, ByteBuffer bytebuffer, int j, StringBuffer stringbuffer)
    {
        if (i == terrainTextureId)
        {
            if (texturefx.iconIndex == Block.waterStill.blockIndexInTexture)
            {
                if (Config.isGeneratedWater())
                {
                    return false;
                }
                else
                {
                    return updateCustomTexture(texturefx, "/custom_water_still.png", bytebuffer, j, Config.isAnimatedWater(), 1, stringbuffer);
                }
            }

            if (texturefx.iconIndex == Block.waterStill.blockIndexInTexture + 1)
            {
                if (Config.isGeneratedWater())
                {
                    return false;
                }
                else
                {
                    return updateCustomTexture(texturefx, "/custom_water_flowing.png", bytebuffer, j, Config.isAnimatedWater(), 1, stringbuffer);
                }
            }

            if (texturefx.iconIndex == Block.lavaStill.blockIndexInTexture)
            {
                if (Config.isGeneratedLava())
                {
                    return false;
                }
                else
                {
                    return updateCustomTexture(texturefx, "/custom_lava_still.png", bytebuffer, j, Config.isAnimatedLava(), 1, stringbuffer);
                }
            }

            if (texturefx.iconIndex == Block.lavaStill.blockIndexInTexture + 1)
            {
                if (Config.isGeneratedLava())
                {
                    return false;
                }
                else
                {
                    return updateCustomTexture(texturefx, "/custom_lava_flowing.png", bytebuffer, j, Config.isAnimatedLava(), 1, stringbuffer);
                }
            }

            if (texturefx.iconIndex == Block.portal.blockIndexInTexture)
            {
                return updateCustomTexture(texturefx, "/custom_portal.png", bytebuffer, j, Config.isAnimatedPortal(), 1, stringbuffer);
            }

            if (texturefx.iconIndex == Block.fire.blockIndexInTexture)
            {
                return updateCustomTexture(texturefx, "/custom_fire_n_s.png", bytebuffer, j, Config.isAnimatedFire(), 1, stringbuffer);
            }

            if (texturefx.iconIndex == Block.fire.blockIndexInTexture + 16)
            {
                return updateCustomTexture(texturefx, "/custom_fire_e_w.png", bytebuffer, j, Config.isAnimatedFire(), 1, stringbuffer);
            }

            if (Config.isAnimatedTerrain())
            {
                return updateCustomTexture(texturefx, (new StringBuilder()).append("/custom_terrain_").append(texturefx.iconIndex).append(".png").toString(), bytebuffer, j, Config.isAnimatedTerrain(), 1, stringbuffer);
            }
        }

        if (i == guiItemsTextureId && Config.isAnimatedItems())
        {
            return updateCustomTexture(texturefx, (new StringBuilder()).append("/custom_item_").append(texturefx.iconIndex).append(".png").toString(), bytebuffer, j, Config.isAnimatedTerrain(), 1, stringbuffer);
        }
        else
        {
            return false;
        }
    }

    private boolean updateDefaultTexture(TextureFX texturefx, int i, ByteBuffer bytebuffer, int j, StringBuffer stringbuffer)
    {
        if (i != terrainTextureId)
        {
            return false;
        }

        if (texturePack.selectedTexturePack instanceof TexturePackDefault)
        {
            return false;
        }

        if (texturefx.iconIndex == Block.waterStill.blockIndexInTexture)
        {
            if (Config.isGeneratedWater())
            {
                return false;
            }
            else
            {
                return updateDefaultTexture(texturefx, bytebuffer, j, false, 1, stringbuffer);
            }
        }

        if (texturefx.iconIndex == Block.waterStill.blockIndexInTexture + 1)
        {
            if (Config.isGeneratedWater())
            {
                return false;
            }
            else
            {
                return updateDefaultTexture(texturefx, bytebuffer, j, Config.isAnimatedWater(), 1, stringbuffer);
            }
        }

        if (texturefx.iconIndex == Block.lavaStill.blockIndexInTexture)
        {
            if (Config.isGeneratedLava())
            {
                return false;
            }
            else
            {
                return updateDefaultTexture(texturefx, bytebuffer, j, false, 1, stringbuffer);
            }
        }

        if (texturefx.iconIndex == Block.lavaStill.blockIndexInTexture + 1)
        {
            if (Config.isGeneratedLava())
            {
                return false;
            }
            else
            {
                return updateDefaultTexture(texturefx, bytebuffer, j, Config.isAnimatedLava(), 3, stringbuffer);
            }
        }
        else
        {
            return false;
        }
    }

    private boolean updateDefaultTexture(TextureFX texturefx, ByteBuffer bytebuffer, int i, boolean flag, int j, StringBuffer stringbuffer)
    {
        int k = texturefx.iconIndex;

        if (!flag && dynamicTexturesUpdated)
        {
            return true;
        }

        byte abyte0[] = getTerrainIconData(k, i, stringbuffer);

        if (abyte0 == null)
        {
            return false;
        }

        bytebuffer.clear();
        int l = abyte0.length;

        if (flag)
        {
            int i1 = i - (tickCounter / j) % i;
            int j1 = i1 * i * 4;
            bytebuffer.put(abyte0, j1, l - j1);
            bytebuffer.put(abyte0, 0, j1);
            stringbuffer.append(":");
            stringbuffer.append(i1);
        }
        else
        {
            bytebuffer.put(abyte0, 0, l);
        }

        bytebuffer.position(0).limit(l);
        return true;
    }

    private boolean updateCustomTexture(TextureFX texturefx, String s, ByteBuffer bytebuffer, int i, boolean flag, int j, StringBuffer stringbuffer)
    {
        int k = i;
        CustomAnimation customanimation = getCustomAnimation(s, i, k, j);

        if (customanimation == null)
        {
            return false;
        }
        else
        {
            return customanimation.updateCustomTexture(bytebuffer, flag, dynamicTexturesUpdated, stringbuffer);
        }
    }

    private CustomAnimation getCustomAnimation(String s, int i, int j, int k)
    {
        CustomAnimation customanimation = (CustomAnimation)customAnimationMap.get(s);

        if (customanimation == null)
        {
            if (customAnimationMap.containsKey(s))
            {
                return null;
            }

            byte abyte0[] = getCustomTextureData(s, i);

            if (abyte0 == null)
            {
                customAnimationMap.put(s, null);
                return null;
            }

            Properties properties = new Properties();
            String s1 = makePropertiesName(s);

            if (s1 != null)
            {
                try
                {
                    InputStream inputstream = texturePack.selectedTexturePack.getResourceAsStream(s1);

                    if (inputstream == null)
                    {
                        inputstream = texturePack.selectedTexturePack.getResourceAsStream((new StringBuilder()).append("/anim").append(s1).toString());
                    }

                    if (inputstream != null)
                    {
                        properties.load(inputstream);
                    }
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }

            customanimation = new CustomAnimation(s, abyte0, i, j, properties, k);
            customAnimationMap.put(s, customanimation);
        }

        return customanimation;
    }

    private String makePropertiesName(String s)
    {
        if (!s.endsWith(".png"))
        {
            return null;
        }

        int i = s.lastIndexOf(".png");

        if (i < 0)
        {
            return null;
        }
        else
        {
            String s1 = (new StringBuilder()).append(s.substring(0, i)).append(".properties").toString();
            return s1;
        }
    }

    private byte[] getTerrainIconData(int i, int j, StringBuffer stringbuffer)
    {
        String s = (new StringBuilder()).append("Tile-").append(i).toString();
        byte abyte0[] = getCustomTextureData(s, j);

        if (abyte0 != null)
        {
            stringbuffer.append(s);
            return abyte0;
        }

        byte abyte1[] = getCustomTextureData("/terrain.png", j * 16);

        if (abyte1 == null)
        {
            return null;
        }

        abyte0 = new byte[j * j * 4];
        int k = i % 16;
        int l = i / 16;
        int i1 = k * j;
        int j1 = l * j;
        int k1 = i1 + j;
        int l1 = j1 + j;

        for (int i2 = 0; i2 < j; i2++)
        {
            int j2 = j1 + i2;

            for (int k2 = 0; k2 < j; k2++)
            {
                int l2 = i1 + k2;
                int i3 = 4 * (l2 + j2 * j * 16);
                int j3 = 4 * (k2 + i2 * j);
                abyte0[j3 + 0] = abyte1[i3 + 0];
                abyte0[j3 + 1] = abyte1[i3 + 1];
                abyte0[j3 + 2] = abyte1[i3 + 2];
                abyte0[j3 + 3] = abyte1[i3 + 3];
            }
        }

        setCustomTextureData(s, abyte0);
        stringbuffer.append(s);
        return abyte0;
    }

    public byte[] getCustomTextureData(String s, int i)
    {
        byte abyte0[] = (byte[])textureDataMap.get(s);

        if (abyte0 == null)
        {
            if (textureDataMap.containsKey(s))
            {
                return null;
            }

            abyte0 = loadImage(s, i);

            if (abyte0 == null)
            {
                abyte0 = loadImage((new StringBuilder()).append("/anim").append(s).toString(), i);
            }

            textureDataMap.put(s, abyte0);
        }

        return abyte0;
    }

    private void setCustomTextureData(String s, byte abyte0[])
    {
        textureDataMap.put(s, abyte0);
    }

    private byte[] loadImage(String s, int i)
    {
        try
        {
            TexturePackBase texturepackbase = texturePack.selectedTexturePack;

            if (texturepackbase == null)
            {
                return null;
            }

            InputStream inputstream = texturepackbase.getResourceAsStream(s);

            if (inputstream == null)
            {
                return null;
            }

            BufferedImage bufferedimage = readTextureImage(inputstream);

            if (bufferedimage == null)
            {
                return null;
            }

            if (i > 0 && bufferedimage.getWidth() != i)
            {
                double d = bufferedimage.getHeight() / bufferedimage.getWidth();
                int l = (int)((double)i * d);
                bufferedimage = scaleBufferedImage(bufferedimage, i, l);
            }

            int j = bufferedimage.getWidth();
            int k = bufferedimage.getHeight();
            int ai[] = new int[j * k];
            byte abyte0[] = new byte[j * k * 4];
            bufferedimage.getRGB(0, 0, j, k, ai, 0, j);

            for (int i1 = 0; i1 < ai.length; i1++)
            {
                int j1 = ai[i1] >> 24 & 0xff;
                int k1 = ai[i1] >> 16 & 0xff;
                int l1 = ai[i1] >> 8 & 0xff;
                int i2 = ai[i1] & 0xff;

                if (options != null && options.anaglyph)
                {
                    int j2 = (k1 * 30 + l1 * 59 + i2 * 11) / 100;
                    int k2 = (k1 * 30 + l1 * 70) / 100;
                    int l2 = (k1 * 30 + i2 * 70) / 100;
                    k1 = j2;
                    l1 = k2;
                    i2 = l2;
                }

                abyte0[i1 * 4 + 0] = (byte)k1;
                abyte0[i1 * 4 + 1] = (byte)l1;
                abyte0[i1 * 4 + 2] = (byte)i2;
                abyte0[i1 * 4 + 3] = (byte)j1;
            }

            return abyte0;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage bufferedimage, int i, int j)
    {
        BufferedImage bufferedimage1 = new BufferedImage(i, j, 2);
        Graphics2D graphics2d = bufferedimage1.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedimage, 0, 0, i, j, null);
        return bufferedimage1;
    }

    private void checkImageDataSize(int i, int j)
    {
        if (imageData != null)
        {
            int k = i * j * 4;

            if (imageData.capacity() >= k)
            {
                return;
            }
        }

        allocateImageData(i, j);
    }

    private void allocateImageData(int i, int j)
    {
        int k = i * j * 4;
        imageData = GLAllocation.createDirectByteBuffer(k);
        ArrayList arraylist = new ArrayList();
        int l = i / 2;

        for (int i1 = j / 2; l > 0 && i1 > 0; i1 /= 2)
        {
            int j1 = l * i1 * 4;
            ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(j1);
            arraylist.add(bytebuffer);
            l /= 2;
        }

        mipImageDatas = (ByteBuffer[])arraylist.toArray(new ByteBuffer[arraylist.size()]);
    }

    public void checkHdTextures()
    {
        if (hdTexturesInstalled)
        {
            return;
        }

        Minecraft minecraft = Config.getMinecraft();

        if (minecraft == null)
        {
            return;
        }
        else
        {
            hdTexturesInstalled = true;
            registerTextureFX(new TextureHDLavaFX());
            registerTextureFX(new TextureHDWaterFX());
            registerTextureFX(new TextureHDPortalFX());
            registerTextureFX(new TextureHDWaterFlowFX());
            registerTextureFX(new TextureHDLavaFlowFX());
            registerTextureFX(new TextureHDFlamesFX(0));
            registerTextureFX(new TextureHDFlamesFX(1));
            registerTextureFX(new TextureHDCompassFX(minecraft));
            registerTextureFX(new TextureHDWatchFX(minecraft));
            registerCustomTexturesFX();
            CustomColorizer.update(this);
            ConnectedTextures.update(this);
            NaturalTextures.update(this);
            return;
        }
    }

    private void registerCustomTexturesFX()
    {
        TextureFX atexturefx[] = getRegisteredTexturesFX(net.minecraft.src.TextureHDCustomFX.class);

        for (int i = 0; i < atexturefx.length; i++)
        {
            TextureFX texturefx = atexturefx[i];
            unregisterTextureFX(texturefx);
        }

        if (Config.isAnimatedTerrain())
        {
            for (int j = 0; j < 256; j++)
            {
                registerCustomTextureFX((new StringBuilder()).append("/custom_terrain_").append(j).append(".png").toString(), j, 0);
            }
        }

        if (Config.isAnimatedItems())
        {
            for (int k = 0; k < 256; k++)
            {
                registerCustomTextureFX((new StringBuilder()).append("/custom_item_").append(k).append(".png").toString(), k, 1);
            }
        }

        textureAnimations = getTextureAnimations();
    }

    private CustomAnimation[] getTextureAnimations()
    {
        String s = texturePack.selectedTexturePack.texturePackFileName;
        File file = new File(Config.getMinecraft().mcDataDir, "texturepacks");
        File file1 = new File(file, s);

        if (!file1.exists())
        {
            return null;
        }

        Properties aproperties[] = null;

        if (file1.isFile())
        {
            aproperties = getAnimationPropertiesZip(file1);
        }
        else
        {
            aproperties = getAnimationPropertiesDir(file1);
        }

        if (aproperties == null)
        {
            return null;
        }

        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < aproperties.length; i++)
        {
            Properties properties = aproperties[i];
            CustomAnimation customanimation = makeTextureAnimation(properties);

            if (customanimation != null)
            {
                arraylist.add(customanimation);
            }
        }

        CustomAnimation acustomanimation[] = (CustomAnimation[])arraylist.toArray(new CustomAnimation[arraylist.size()]);
        return acustomanimation;
    }

    private CustomAnimation makeTextureAnimation(Properties properties)
    {
        String s = properties.getProperty("from");
        String s1 = properties.getProperty("to");
        int i = Config.parseInt(properties.getProperty("x"), -1);
        int j = Config.parseInt(properties.getProperty("y"), -1);
        int k = Config.parseInt(properties.getProperty("w"), -1);
        int l = Config.parseInt(properties.getProperty("h"), -1);

        if (s == null || s1 == null)
        {
            return null;
        }

        if (i < 0 || j < 0 || k < 0 || l < 0)
        {
            return null;
        }

        byte abyte0[] = getCustomTextureData(s, k);

        if (abyte0 == null)
        {
            return null;
        }
        else
        {
            CustomAnimation customanimation = new CustomAnimation(s, abyte0, k, l, properties, 1);
            customanimation.destTexture = s1;
            customanimation.destX = i;
            customanimation.destY = j;
            return customanimation;
        }
    }

    private Properties[] getAnimationPropertiesDir(File file)
    {
        File file1 = new File(file, "anim");

        if (!file1.exists())
        {
            return null;
        }

        if (!file1.isDirectory())
        {
            return null;
        }

        File afile[] = file1.listFiles();

        if (afile == null)
        {
            return null;
        }

        try
        {
            ArrayList arraylist = new ArrayList();

            for (int i = 0; i < afile.length; i++)
            {
                File file2 = afile[i];
                String s = file2.getName();

                if (!s.startsWith("custom_") && s.endsWith(".properties") && file2.isFile() && file2.canRead())
                {
                    FileInputStream fileinputstream = new FileInputStream(file2);
                    Properties properties = new Properties();
                    properties.load(fileinputstream);
                    fileinputstream.close();
                    arraylist.add(properties);
                }
            }

            Properties aproperties[] = (Properties[])arraylist.toArray(new Properties[arraylist.size()]);
            return aproperties;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        return null;
    }

    private Properties[] getAnimationPropertiesZip(File file)
    {
        try
        {
            ZipFile zipfile = new ZipFile(file);
            Enumeration enumeration = zipfile.entries();
            ArrayList arraylist = new ArrayList();

            do
            {
                if (!enumeration.hasMoreElements())
                {
                    break;
                }

                ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                String s = zipentry.getName();

                if (s.startsWith("anim/") && !s.startsWith("anim/custom_") && s.endsWith(".properties"))
                {
                    InputStream inputstream = zipfile.getInputStream(zipentry);
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    inputstream.close();
                    arraylist.add(properties);
                }
            }
            while (true);

            Properties aproperties[] = (Properties[])arraylist.toArray(new Properties[arraylist.size()]);
            return aproperties;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        return null;
    }

    private void unregisterTextureFX(TextureFX texturefx)
    {
        for (int i = 0; i < textureList.size(); i++)
        {
            TextureFX texturefx1 = (TextureFX)textureList.get(i);

            if (texturefx1 == texturefx)
            {
                textureList.remove(i);
                i--;
            }
        }
    }

    private TextureFX[] getRegisteredTexturesFX(Class class1)
    {
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < textureList.size(); i++)
        {
            TextureFX texturefx = (TextureFX)textureList.get(i);

            if (class1.isAssignableFrom(texturefx.getClass()))
            {
                arraylist.add(texturefx);
            }
        }

        TextureFX atexturefx[] = (TextureFX[])arraylist.toArray(new TextureFX[arraylist.size()]);
        return atexturefx;
    }

    private void registerCustomTextureFX(String s, int i, int j)
    {
        byte abyte0[] = null;

        if (j == 0)
        {
            abyte0 = getCustomTextureData(s, Config.getIconWidthTerrain());
        }
        else
        {
            abyte0 = getCustomTextureData(s, Config.getIconWidthItems());
        }

        if (abyte0 == null)
        {
            return;
        }
        else
        {
            registerTextureFX(new TextureHDCustomFX(i, j));
            return;
        }
    }

    private int getMaxMipmapLevel(int i)
    {
        int j;

        for (j = 0; i > 0; j++)
        {
            i /= 2;
        }

        return j - 1;
    }

    private void copyScaled(byte abyte0[], ByteBuffer bytebuffer, int i)
    {
        int j = (int)Math.sqrt(abyte0.length / 4);
        int k = i / j;
        byte abyte1[] = new byte[4];
        int l = i * i;
        bytebuffer.clear();

        if (k > 1)
        {
            for (int i1 = 0; i1 < j; i1++)
            {
                int j1 = i1 * j;
                int k1 = i1 * k;
                int l1 = k1 * i;

                for (int i2 = 0; i2 < j; i2++)
                {
                    int j2 = (i2 + j1) * 4;
                    abyte1[0] = abyte0[j2];
                    abyte1[1] = abyte0[j2 + 1];
                    abyte1[2] = abyte0[j2 + 2];
                    abyte1[3] = abyte0[j2 + 3];
                    int k2 = i2 * k;
                    int l2 = k2 + l1;

                    for (int i3 = 0; i3 < k; i3++)
                    {
                        int j3 = l2 + i3 * i;
                        bytebuffer.position(j3 * 4);

                        for (int k3 = 0; k3 < k; k3++)
                        {
                            bytebuffer.put(abyte1);
                        }
                    }
                }
            }
        }

        bytebuffer.position(0).limit(i * i * 4);
    }

    private boolean scalesWithFastColor(TextureFX texturefx)
    {
        return !texturefx.getClass().getName().equals("ModTextureStatic");
    }

    public TexturePackList getTexturePack()
    {
        return texturePack;
    }
}
