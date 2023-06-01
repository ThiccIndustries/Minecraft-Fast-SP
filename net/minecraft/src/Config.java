package net.minecraft.src;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class Config
{
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.2.5";
    public static final String OF_EDITION = "HD";
    public static final String OF_RELEASE = "C6";
    public static final String VERSION = "OptiFine_1.2.5_HD_C6";
    private static String newRelease = null;
    private static GameSettings gameSettings = null;
    private static Minecraft minecraft = null;
    private static int iconWidthTerrain = 16;
    private static int iconWidthItems = 16;
    private static Map foundClassesMap = new HashMap();
    private static long textureUpdateTime = 0L;
    private static DisplayMode desktopDisplayMode = null;
    private static File logFile = null;
    public static final Boolean DEF_FOG_FANCY = Boolean.valueOf(true);
    public static final Float DEF_FOG_START = Float.valueOf(0.2F);
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE = Boolean.valueOf(false);
    public static final Boolean DEF_OCCLUSION_ENABLED = Boolean.valueOf(false);
    public static final Integer DEF_MIPMAP_LEVEL = Integer.valueOf(0);
    public static final Integer DEF_MIPMAP_TYPE = Integer.valueOf(9984);
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
    public static final Boolean DEF_LOAD_CHUNKS_FAR = Boolean.valueOf(false);
    public static final Integer DEF_PRELOADED_CHUNKS = Integer.valueOf(0);
    public static final Integer DEF_CHUNKS_LIMIT = Integer.valueOf(25);
    public static final Integer DEF_UPDATES_PER_FRAME = Integer.valueOf(3);
    public static final Boolean DEF_DYNAMIC_UPDATES = Boolean.valueOf(false);

    private Config()
    {
    }

    public static String getVersion()
    {
        return "OptiFine_1.2.5_HD_C6";
    }

    private static void checkOpenGlCaps()
    {
        log("");
        log(getVersion());
        log((new StringBuilder()).append("").append(new Date()).toString());
        log((new StringBuilder()).append("OS: ").append(System.getProperty("os.name")).append(" (").append(System.getProperty("os.arch")).append(") version ").append(System.getProperty("os.version")).toString());
        log((new StringBuilder()).append("Java: ").append(System.getProperty("java.version")).append(", ").append(System.getProperty("java.vendor")).toString());
        log((new StringBuilder()).append("VM: ").append(System.getProperty("java.vm.name")).append(" (").append(System.getProperty("java.vm.info")).append("), ").append(System.getProperty("java.vm.vendor")).toString());
        log((new StringBuilder()).append("LWJGL: ").append(Sys.getVersion()).toString());
        log((new StringBuilder()).append("OpenGL: ").append(GL11.glGetString(GL11.GL_RENDERER)).append(" version ").append(GL11.glGetString(GL11.GL_VERSION)).append(", ").append(GL11.glGetString(GL11.GL_VENDOR)).toString());
        int i = getOpenGlVersion();
        String s = (new StringBuilder()).append("").append(i / 10).append(".").append(i % 10).toString();
        log((new StringBuilder()).append("OpenGL Version: ").append(s).toString());

        if (!GLContext.getCapabilities().OpenGL12)
        {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }

        if (!GLContext.getCapabilities().GL_NV_fog_distance)
        {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }

        if (!GLContext.getCapabilities().GL_ARB_occlusion_query)
        {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
    }

    public static boolean isFancyFogAvailable()
    {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }

    public static boolean isOcclusionAvailable()
    {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }

    private static int getOpenGlVersion()
    {
        if (!GLContext.getCapabilities().OpenGL11)
        {
            return 10;
        }

        if (!GLContext.getCapabilities().OpenGL12)
        {
            return 11;
        }

        if (!GLContext.getCapabilities().OpenGL13)
        {
            return 12;
        }

        if (!GLContext.getCapabilities().OpenGL14)
        {
            return 13;
        }

        if (!GLContext.getCapabilities().OpenGL15)
        {
            return 14;
        }

        if (!GLContext.getCapabilities().OpenGL20)
        {
            return 15;
        }

        if (!GLContext.getCapabilities().OpenGL21)
        {
            return 20;
        }

        if (!GLContext.getCapabilities().OpenGL30)
        {
            return 21;
        }

        if (!GLContext.getCapabilities().OpenGL31)
        {
            return 30;
        }

        if (!GLContext.getCapabilities().OpenGL32)
        {
            return 31;
        }

        if (!GLContext.getCapabilities().OpenGL33)
        {
            return 32;
        }

        return GLContext.getCapabilities().OpenGL40 ? 40 : 33;
    }

    public static void setGameSettings(GameSettings gamesettings)
    {
        if (gameSettings == null)
        {
            checkOpenGlCaps();
            startVersionCheckThread();
        }

        gameSettings = gamesettings;
        minecraft = gameSettings.mc;
    }

    private static void startVersionCheckThread()
    {
        VersionCheckThread versioncheckthread = new VersionCheckThread();
        versioncheckthread.start();
    }

    public static boolean isUseMipmaps()
    {
        int i = getMipmapLevel();
        return i > 0;
    }

    public static int getMipmapLevel()
    {
        if (gameSettings == null)
        {
            return DEF_MIPMAP_LEVEL.intValue();
        }
        else
        {
            return gameSettings.ofMipmapLevel;
        }
    }

    public static int getMipmapType()
    {
        if (gameSettings == null)
        {
            return DEF_MIPMAP_TYPE.intValue();
        }

        return !gameSettings.ofMipmapLinear ? 9984 : 9986;
    }

    public static boolean isUseAlphaFunc()
    {
        float f = getAlphaFuncLevel();
        return f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1E-005F;
    }

    public static float getAlphaFuncLevel()
    {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy()
    {
        if (!isFancyFogAvailable())
        {
            return false;
        }

        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofFogType == 2;
        }
    }

    public static boolean isFogFast()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofFogType == 1;
        }
    }

    public static boolean isFogOff()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofFogType == 3;
        }
    }

    public static float getFogStart()
    {
        if (gameSettings == null)
        {
            return DEF_FOG_START.floatValue();
        }
        else
        {
            return gameSettings.ofFogStart;
        }
    }

    public static boolean isOcclusionEnabled()
    {
        if (gameSettings == null)
        {
            return DEF_OCCLUSION_ENABLED.booleanValue();
        }
        else
        {
            return gameSettings.advancedOpengl;
        }
    }

    public static boolean isOcclusionFancy()
    {
        if (!isOcclusionEnabled())
        {
            return false;
        }

        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofOcclusionFancy;
        }
    }

    public static boolean isLoadChunksFar()
    {
        if (gameSettings == null)
        {
            return DEF_LOAD_CHUNKS_FAR.booleanValue();
        }
        else
        {
            return gameSettings.ofLoadFar;
        }
    }

    public static int getPreloadedChunks()
    {
        if (gameSettings == null)
        {
            return DEF_PRELOADED_CHUNKS.intValue();
        }
        else
        {
            return gameSettings.ofPreloadedChunks;
        }
    }

    public static void dbg(String s)
    {
        System.out.println(s);
    }

    public static void log(String s)
    {
        dbg(s);

        try
        {
            if (logFile == null)
            {
                logFile = new File(Minecraft.getMinecraftDir(), "optifog.log");
                logFile.delete();
                logFile.createNewFile();
            }

            FileOutputStream fileoutputstream = new FileOutputStream(logFile, true);
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(fileoutputstream, "ASCII");

            try
            {
                outputstreamwriter.write(s);
                outputstreamwriter.write("\n");
                outputstreamwriter.flush();
            }
            finally
            {
                outputstreamwriter.close();
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static int getUpdatesPerFrame()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofChunkUpdates;
        }
        else
        {
            return 1;
        }
    }

    public static boolean isDynamicUpdates()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofChunkUpdatesDynamic;
        }
        else
        {
            return true;
        }
    }

    public static boolean isRainFancy()
    {
        if (gameSettings.ofRain == 0)
        {
            return gameSettings.fancyGraphics;
        }
        else
        {
            return gameSettings.ofRain == 2;
        }
    }

    public static boolean isWaterFancy()
    {
        if (gameSettings.ofWater == 0)
        {
            return gameSettings.fancyGraphics;
        }
        else
        {
            return gameSettings.ofWater == 2;
        }
    }

    public static boolean isRainOff()
    {
        return gameSettings.ofRain == 3;
    }

    public static boolean isCloudsFancy()
    {
        if (gameSettings.ofClouds == 0)
        {
            return gameSettings.fancyGraphics;
        }
        else
        {
            return gameSettings.ofClouds == 2;
        }
    }

    public static boolean isCloudsOff()
    {
        return gameSettings.ofClouds == 3;
    }

    public static boolean isTreesFancy()
    {
        if (gameSettings.ofTrees == 0)
        {
            return gameSettings.fancyGraphics;
        }
        else
        {
            return gameSettings.ofTrees == 2;
        }
    }

    public static boolean isGrassFancy()
    {
        if (gameSettings.ofGrass == 0)
        {
            return gameSettings.fancyGraphics;
        }
        else
        {
            return gameSettings.ofGrass == 2;
        }
    }

    public static int limit(int i, int j, int k)
    {
        if (i < j)
        {
            return j;
        }

        if (i > k)
        {
            return k;
        }
        else
        {
            return i;
        }
    }

    public static float limit(float f, float f1, float f2)
    {
        if (f < f1)
        {
            return f1;
        }

        if (f > f2)
        {
            return f2;
        }
        else
        {
            return f;
        }
    }

    public static float limitTo1(float f)
    {
        if (f < 0.0F)
        {
            return 0.0F;
        }

        if (f > 1.0F)
        {
            return 1.0F;
        }
        else
        {
            return f;
        }
    }

    public static boolean isAnimatedWater()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedWater != 2;
        }
        else
        {
            return true;
        }
    }

    public static boolean isGeneratedWater()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedWater == 1;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedPortal()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedPortal;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedLava()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedLava != 2;
        }
        else
        {
            return true;
        }
    }

    public static boolean isGeneratedLava()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedLava == 1;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedFire()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedFire;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedRedstone()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedRedstone;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedExplosion()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedExplosion;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedFlame()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedFlame;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedSmoke()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedSmoke;
        }
        else
        {
            return true;
        }
    }

    public static boolean isVoidParticles()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofVoidParticles;
        }
        else
        {
            return true;
        }
    }

    public static boolean isWaterParticles()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofWaterParticles;
        }
        else
        {
            return true;
        }
    }

    public static boolean isRainSplash()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofRainSplash;
        }
        else
        {
            return true;
        }
    }

    public static boolean isPortalParticles()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofPortalParticles;
        }
        else
        {
            return true;
        }
    }

    public static boolean isDepthFog()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofDepthFog;
        }
        else
        {
            return true;
        }
    }

    public static float getAmbientOcclusionLevel()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAoLevel;
        }
        else
        {
            return 0.0F;
        }
    }

    private static Method getMethod(Class class1, String s, Object aobj[])
    {
        Method amethod[] = class1.getMethods();

        for (int i = 0; i < amethod.length; i++)
        {
            Method method = amethod[i];

            if (method.getName().equals(s) && method.getParameterTypes().length == aobj.length)
            {
                return method;
            }
        }

        dbg((new StringBuilder()).append("No method found for: ").append(class1.getName()).append(".").append(s).append("(").append(arrayToString(aobj)).append(")").toString());
        return null;
    }

    public static String arrayToString(Object aobj[])
    {
        StringBuffer stringbuffer = new StringBuffer(aobj.length * 5);

        for (int i = 0; i < aobj.length; i++)
        {
            Object obj = aobj[i];

            if (i > 0)
            {
                stringbuffer.append(", ");
            }

            stringbuffer.append(String.valueOf(obj));
        }

        return stringbuffer.toString();
    }

    public static Minecraft getMinecraft()
    {
        return minecraft;
    }

    public static int getIconWidthTerrain()
    {
        return iconWidthTerrain;
    }

    public static int getIconWidthItems()
    {
        return iconWidthItems;
    }

    public static void setIconWidthItems(int i)
    {
        iconWidthItems = i;
    }

    public static void setIconWidthTerrain(int i)
    {
        iconWidthTerrain = i;
    }

    public static int getMaxDynamicTileWidth()
    {
        return 64;
    }

    public static int getSideGrassTexture(IBlockAccess iblockaccess, int i, int j, int k, int l, int i1)
    {
        if (!isBetterGrass())
        {
            return i1;
        }

        byte byte0 = 0;
        byte byte1 = 2;

        if (i1 == 77)
        {
            byte0 = 78;
            byte1 = 110;
        }

        if (isBetterGrassFancy())
        {
            j--;

            switch (l)
            {
                case 2:
                    k--;
                    break;

                case 3:
                    k++;
                    break;

                case 4:
                    i--;
                    break;

                case 5:
                    i++;
                    break;
            }

            int j1 = iblockaccess.getBlockId(i, j, k);

            if (j1 != byte1)
            {
                return i1;
            }
        }

        return byte0;
    }

    public static int getSideSnowGrassTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if (!isBetterGrass())
        {
            return 68;
        }

        if (isBetterGrassFancy())
        {
            switch (l)
            {
                case 2:
                    k--;
                    break;

                case 3:
                    k++;
                    break;

                case 4:
                    i--;
                    break;

                case 5:
                    i++;
                    break;
            }

            int i1 = iblockaccess.getBlockId(i, j, k);

            if (i1 != 78 && i1 != 80)
            {
                return 68;
            }
        }

        return 66;
    }

    public static boolean isBetterGrass()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofBetterGrass != 3;
        }
    }

    public static boolean isBetterGrassFancy()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofBetterGrass == 2;
        }
    }

    public static long getTextureUpdateTime()
    {
        return textureUpdateTime;
    }

    public static void setTextureUpdateTime(long l)
    {
        textureUpdateTime = l;
    }

    public static boolean isWeatherEnabled()
    {
        if (gameSettings == null)
        {
            return true;
        }
        else
        {
            return gameSettings.ofWeather;
        }
    }

    public static boolean isSkyEnabled()
    {
        if (gameSettings == null)
        {
            return true;
        }
        else
        {
            return gameSettings.ofSky;
        }
    }

    public static boolean isSunMoonEnabled()
    {
        if (gameSettings == null)
        {
            return true;
        }
        else
        {
            return gameSettings.ofSunMoon;
        }
    }

    public static boolean isStarsEnabled()
    {
        if (gameSettings == null)
        {
            return true;
        }
        else
        {
            return gameSettings.ofStars;
        }
    }

    public static void sleep(long l)
    {
        try
        {
            Thread.currentThread();
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }

    public static boolean isTimeDayOnly()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofTime == 1;
        }
    }

    public static boolean isTimeNightOnly()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofTime == 3;
        }
    }

    public static boolean isClearWater()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofClearWater;
        }
    }

    public static boolean isDrippingWaterLava()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofDrippingWaterLava;
        }
    }

    public static boolean isBetterSnow()
    {
        if (gameSettings == null)
        {
            return false;
        }
        else
        {
            return gameSettings.ofBetterSnow;
        }
    }

    public static Dimension getFullscreenDimension()
    {
        if (gameSettings == null)
        {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }

        String s = gameSettings.ofFullscreenMode;

        if (s.equals("Default"))
        {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }

        String as[] = tokenize(s, " x");

        if (as.length < 2)
        {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        else
        {
            return new Dimension(parseInt(as[0], -1), parseInt(as[1], -1));
        }
    }

    public static int parseInt(String s, int i)
    {
        try
        {
            if (s == null)
            {
                return i;
            }
            else
            {
                return Integer.parseInt(s);
            }
        }
        catch (NumberFormatException numberformatexception)
        {
            return i;
        }
    }

    public static float parseFloat(String s, float f)
    {
        try
        {
            if (s == null)
            {
                return f;
            }
            else
            {
                return Float.parseFloat(s);
            }
        }
        catch (NumberFormatException numberformatexception)
        {
            return f;
        }
    }

    public static String[] tokenize(String s, String s1)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
        ArrayList arraylist = new ArrayList();
        String s2;

        for (; stringtokenizer.hasMoreTokens(); arraylist.add(s2))
        {
            s2 = stringtokenizer.nextToken();
        }

        String as[] = (String[])arraylist.toArray(new String[arraylist.size()]);
        return as;
    }

    public static DisplayMode getDesktopDisplayMode()
    {
        return desktopDisplayMode;
    }

    public static void setDesktopDisplayMode(DisplayMode displaymode)
    {
        desktopDisplayMode = displaymode;
    }

    public static DisplayMode[] getFullscreenDisplayModes()
    {
        try
        {
            DisplayMode adisplaymode[] = Display.getAvailableDisplayModes();
            ArrayList arraylist = new ArrayList();

            for (int i = 0; i < adisplaymode.length; i++)
            {
                DisplayMode displaymode = adisplaymode[i];

                if (desktopDisplayMode == null || displaymode.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == desktopDisplayMode.getFrequency())
                {
                    arraylist.add(displaymode);
                }
            }

            DisplayMode adisplaymode1[] = (DisplayMode[])arraylist.toArray(new DisplayMode[arraylist.size()]);
            Comparator comparator = new Comparator()
            {
                public int compare(Object obj, Object obj1)
                {
                    DisplayMode displaymode1 = (DisplayMode)obj;
                    DisplayMode displaymode2 = (DisplayMode)obj1;

                    if (displaymode1.getWidth() != displaymode2.getWidth())
                    {
                        return displaymode2.getWidth() - displaymode1.getWidth();
                    }

                    if (displaymode1.getHeight() != displaymode2.getHeight())
                    {
                        return displaymode2.getHeight() - displaymode1.getHeight();
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
            ;
            Arrays.sort(adisplaymode1, comparator);
            return adisplaymode1;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return (new DisplayMode[]
                {
                    desktopDisplayMode
                });
    }

    public static String[] getFullscreenModes()
    {
        DisplayMode adisplaymode[] = getFullscreenDisplayModes();
        String as[] = new String[adisplaymode.length];

        for (int i = 0; i < adisplaymode.length; i++)
        {
            DisplayMode displaymode = adisplaymode[i];
            String s = (new StringBuilder()).append("").append(displaymode.getWidth()).append("x").append(displaymode.getHeight()).toString();
            as[i] = s;
        }

        return as;
    }

    public static DisplayMode getDisplayMode(Dimension dimension) throws LWJGLException
    {
        DisplayMode adisplaymode[] = Display.getAvailableDisplayModes();

        for (int i = 0; i < adisplaymode.length; i++)
        {
            DisplayMode displaymode = adisplaymode[i];

            if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height && (desktopDisplayMode == null || displaymode.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && displaymode.getFrequency() == desktopDisplayMode.getFrequency()))
            {
                return displaymode;
            }
        }

        return desktopDisplayMode;
    }

    public static boolean isAnimatedTerrain()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedTerrain;
        }
        else
        {
            return true;
        }
    }

    public static boolean isAnimatedItems()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofAnimatedItems;
        }
        else
        {
            return true;
        }
    }

    public static boolean isSwampColors()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofSwampColors;
        }
        else
        {
            return true;
        }
    }

    public static boolean isRandomMobs()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofRandomMobs;
        }
        else
        {
            return true;
        }
    }

    public static void checkGlError(String s)
    {
        int i = GL11.glGetError();

        if (i != 0)
        {
            String s1 = GLU.gluErrorString(i);
            System.out.println((new StringBuilder()).append("OpenGlError: ").append(i).append(" (").append(s1).append("), at: ").append(s).toString());
        }
    }

    public static boolean isSmoothBiomes()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofSmoothBiomes;
        }
        else
        {
            return true;
        }
    }

    public static boolean isCustomColors()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofCustomColors;
        }
        else
        {
            return true;
        }
    }

    public static boolean isShowCapes()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofShowCapes;
        }
        else
        {
            return true;
        }
    }

    public static boolean isConnectedTextures()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofConnectedTextures != 3;
        }
        else
        {
            return false;
        }
    }

    public static boolean isNaturalTextures()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofNaturalTextures;
        }
        else
        {
            return false;
        }
    }

    public static boolean isConnectedTexturesFancy()
    {
        if (gameSettings != null)
        {
            return gameSettings.ofConnectedTextures == 2;
        }
        else
        {
            return false;
        }
    }

    public static String[] readLines(File file) throws IOException
    {
        ArrayList arraylist = new ArrayList();
        FileInputStream fileinputstream = new FileInputStream(file);
        InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream, "ASCII");
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        do
        {
            String s = bufferedreader.readLine();

            if (s != null)
            {
                arraylist.add(s);
            }
            else
            {
                String as[] = (String[])arraylist.toArray(new String[arraylist.size()]);
                return as;
            }
        }
        while (true);
    }

    public static String readFile(File file) throws IOException
    {
        FileInputStream fileinputstream = new FileInputStream(file);
        return readInputStream(fileinputstream, "ASCII");
    }

    public static String readInputStream(InputStream inputstream) throws IOException
    {
        return readInputStream(inputstream, "ASCII");
    }

    public static String readInputStream(InputStream inputstream, String s) throws IOException
    {
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream, s);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        StringBuffer stringbuffer = new StringBuffer();

        do
        {
            String s1 = bufferedreader.readLine();

            if (s1 != null)
            {
                stringbuffer.append(s1);
                stringbuffer.append("\n");
            }
            else
            {
                return stringbuffer.toString();
            }
        }
        while (true);
    }

    public static GameSettings getGameSettings()
    {
        return gameSettings;
    }

    public static String getNewRelease()
    {
        return newRelease;
    }

    public static void setNewRelease(String s)
    {
        newRelease = s;
    }

    public static int compareRelease(String s, String s1)
    {
        String as[] = splitRelease(s);
        String as1[] = splitRelease(s1);
        String s2 = as[0];
        String s3 = as1[0];

        if (!s2.equals(s3))
        {
            return s2.compareTo(s3);
        }

        int i = parseInt(as[1], -1);
        int j = parseInt(as1[1], -1);

        if (i != j)
        {
            return i - j;
        }
        else
        {
            String s4 = as[2];
            String s5 = as1[2];
            return s4.compareTo(s5);
        }
    }

    private static String[] splitRelease(String s)
    {
        if (s == null || s.length() <= 0)
        {
            return (new String[]
                    {
                        "", "", ""
                    });
        }

        String s1 = s.substring(0, 1);

        if (s.length() <= 1)
        {
            return (new String[]
                    {
                        s1, "", ""
                    });
        }

        int i;

        for (i = 1; i < s.length() && Character.isDigit(s.charAt(i)); i++) { }

        String s2 = s.substring(1, i);

        if (i >= s.length())
        {
            return (new String[]
                    {
                        s1, s2, ""
                    });
        }
        else
        {
            String s3 = s.substring(i);
            return (new String[]
                    {
                        s1, s2, s3
                    });
        }
    }

    public static int intHash(int i)
    {
        i = i ^ 0x3d ^ i >> 16;
        i += i << 3;
        i ^= i >> 4;
        i *= 0x27d4eb2d;
        i ^= i >> 15;
        return i;
    }

    public static int getRandom(int i, int j, int k, int l)
    {
        int i1 = intHash(l + 37);
        i1 = intHash(i1 + i);
        i1 = intHash(i1 + k);
        i1 = intHash(i1 + j);
        return i1;
    }
}
