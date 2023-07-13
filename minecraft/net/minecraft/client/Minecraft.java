package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import net.minecraft.src.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements Runnable
{
    public static byte field_28006_b[] = new byte[0xa00000];

    /**
     * Set to 'this' in Minecraft constructor; used by some settings get methods
     */
    private static Minecraft theMinecraft;
    public PlayerController playerController;
    private boolean fullscreen;
    private boolean hasCrashed;
    public int displayWidth;
    public int displayHeight;

    /** Checks OpenGL capabilities (as of 1.2.3_04 effectively unused). */
    private OpenGlCapsChecker glCapabilities;
    private Timer timer;

    /** The World instance that Minecraft uses. */
    public World theWorld;
    public RenderGlobal renderGlobal;

    /** The player who's actually in control of this game. */
    public EntityPlayerSP thePlayer;

    /**
     * The Entity from which the renderer determines the render viewpoint. Currently is always the parent Minecraft
     * class's 'thePlayer' instance. Modification of its location, rotation, or other settings at render time will
     * modify the camera likewise, with the caveat of triggering chunk rebuilds as it moves, making it unsuitable for
     * changing the viewpoint mid-render.
     */
    public EntityLiving renderViewEntity;
    public EffectRenderer effectRenderer;
    public Session session;
    public String minecraftUri;
    public Canvas mcCanvas;

    /** a boolean to hide a Quit button from the main menu */
    public boolean hideQuitButton;
    public volatile boolean isGamePaused;

    /** The RenderEngine instance used by Minecraft */
    public RenderEngine renderEngine;

    /** The font renderer used for displaying and measuring text. */
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;

    /** The GuiScreen that's being displayed at the moment. */
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;

    /** Reference to the download resources thread. */
    private ThreadDownloadResources downloadResourcesThread;

    /** Number of ticks ran since the program was started. */
    private int ticksRan;

    /** Mouse left click counter */
    private int leftClickCounter;

    /** Display width */
    private int tempDisplayWidth;

    /** Display height */
    private int tempDisplayHeight;

    /** Gui achievement */
    public GuiAchievement guiAchievement;
    public GuiIngame ingameGUI;

    /** Skip render world */
    public boolean skipRenderWorld;

    /** The ModelBiped of the player */
    public ModelBiped playerModelBiped;

    /** The ray trace hit that the mouse is over. */
    public MovingObjectPosition objectMouseOver;

    /** The game settings that currently hold effect. */
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager;

    /** Mouse helper instance. */
    public MouseHelper mouseHelper;

    /** The TexturePackLister used by this instance of Minecraft... */
    public TexturePackList texturePackList;
    public File mcDataDir;
    private ISaveFormat saveLoader;
    public static long frameTimes[] = new long[512];
    public static long tickTimes[] = new long[512];
    public static int numRecordedFrameTimes = 0;

    /**
     * time in milliseconds when TheadCheckHasPaid determined you have not paid. 0 if you have paid. Used in
     * GuiAchievement whether to display the nag text.
     */
    public static long hasPaidCheckTime = 0L;

    /**
     * When you place a block, it's set to 6, decremented once per tick, when it's 0, you can place another block.
     */
    private int rightClickDelayTimer;

    /** Stat file writer */
    public StatFileWriter statFileWriter;
    private String serverName;
    private int serverPort;
    private TextureWaterFX textureWaterFX;
    private TextureLavaFX textureLavaFX;

    /** The working dir (OS specific) for minecraft */
    private static File minecraftDir = null;

    /**
     * Set to true to keep the game loop running. Set to false by shutdown() to allow the game loop to exit cleanly.
     */
    public volatile boolean running;

    /** String that shows the debug information */
    public String debug;

    /** Approximate time (in ms) of last update to debug string */
    long debugUpdateTime;

    /** holds the current fps */
    int fpsCounter;

    /**
     * Makes sure it doesn't keep taking screenshots when both buttons are down.
     */
    boolean isTakingScreenshot;
    long prevFrameTime;

    /** Profiler currently displayed in the debug screen pie chart */
    private String debugProfilerName;

    /**
     * Does the actual gameplay have focus. If so then mouse and keys will effect the player instead of menus.
     */
    public boolean inGameHasFocus;
    public boolean isRaining;
    long systemTime;

    /** Join player counter */
    private int joinPlayerCounter;

    public Minecraft(Component par1Component, Canvas par2Canvas, MinecraftApplet par3MinecraftApplet, int par4, int par5, boolean par6)
    {
        fullscreen = false;
        hasCrashed = false;
        timer = new Timer(20F);
        session = null;
        hideQuitButton = false;
        isGamePaused = false;
        currentScreen = null;
        ticksRan = 0;
        leftClickCounter = 0;
        guiAchievement = new GuiAchievement(this);
        skipRenderWorld = false;
        playerModelBiped = new ModelBiped(0.0F);
        objectMouseOver = null;
        sndManager = new SoundManager();
        rightClickDelayTimer = 0;
        textureWaterFX = new TextureWaterFX();
        textureLavaFX = new TextureLavaFX();
        running = true;
        debug = "";
        debugUpdateTime = System.currentTimeMillis();
        fpsCounter = 0;
        isTakingScreenshot = false;
        prevFrameTime = -1L;
        debugProfilerName = "root";
        inGameHasFocus = false;
        isRaining = false;
        systemTime = System.currentTimeMillis();
        joinPlayerCounter = 0;
        StatList.func_27360_a();
        tempDisplayHeight = par5;
        fullscreen = par6;
        mcApplet = par3MinecraftApplet;
        Packet3Chat.field_52010_b = 32767;
        new ThreadClientSleep(this, "Timer hack thread");
        mcCanvas = par2Canvas;
        displayWidth = par4;
        displayHeight = par5;
        fullscreen = par6;

        if (par3MinecraftApplet == null || "true".equals(par3MinecraftApplet.getParameter("stand-alone")))
        {
            hideQuitButton = false;
        }

        theMinecraft = this;
    }

    public void onMinecraftCrash(UnexpectedThrowable par1UnexpectedThrowable)
    {
        hasCrashed = true;
        displayUnexpectedThrowable(par1UnexpectedThrowable);
    }

    /**
     * Displays an unexpected error that has come up during the game.
     */
    public abstract void displayUnexpectedThrowable(UnexpectedThrowable unexpectedthrowable);

    public void setServer(String par1Str, int par2)
    {
        serverName = par1Str;
        serverPort = par2;
    }

    /**
     * Starts the game: initializes the canvas, the title, the settings, etcetera.
     */
    public void startGame() throws LWJGLException
    {
        if (mcCanvas != null)
        {
            Graphics g = mcCanvas.getGraphics();

            if (g != null)
            {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, displayWidth, displayHeight);
                g.dispose();
            }

            Display.setParent(mcCanvas);
        }
        else if (fullscreen)
        {
            Display.setFullscreen(true);
            displayWidth = Display.getDisplayMode().getWidth();
            displayHeight = Display.getDisplayMode().getHeight();

            if (displayWidth <= 0)
            {
                displayWidth = 1;
            }

            if (displayHeight <= 0)
            {
                displayHeight = 1;
            }
        }
        else
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
        }

        Display.setTitle("Minecraft Minecraft 1.2.5");
        System.out.println((new StringBuilder()).append("LWJGL Version: ").append(Sys.getVersion()).toString());

        try
        {
            PixelFormat pixelformat = new PixelFormat();
            pixelformat = pixelformat.withDepthBits(24);
            Display.create(pixelformat);
        }
        catch (LWJGLException lwjglexception)
        {
            lwjglexception.printStackTrace();

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedexception) { }

            Display.create();
        }

        OpenGlHelper.initializeTextures();
        mcDataDir = getMinecraftDir();
        saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
        gameSettings = new GameSettings(this, mcDataDir);
        texturePackList = new TexturePackList(this, mcDataDir);
        renderEngine = new RenderEngine(texturePackList, gameSettings);
        loadScreen();
        fontRenderer = new FontRenderer(gameSettings, "/font/default.png", renderEngine, false);
        standardGalacticFontRenderer = new FontRenderer(gameSettings, "/font/alternate.png", renderEngine, false);

        if (gameSettings.language != null)
        {
            StringTranslate.getInstance().setLanguage(gameSettings.language);
            fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
            fontRenderer.setBidiFlag(StringTranslate.isBidrectional(gameSettings.language));
        }

        ColorizerWater.setWaterBiomeColorizer(renderEngine.getTextureContents("/misc/watercolor.png"));
        ColorizerGrass.setGrassBiomeColorizer(renderEngine.getTextureContents("/misc/grasscolor.png"));
        ColorizerFoliage.getFoilageBiomeColorizer(renderEngine.getTextureContents("/misc/foliagecolor.png"));
        entityRenderer = new EntityRenderer(this);
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        statFileWriter = new StatFileWriter(session, mcDataDir);
        AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
        loadScreen();
        Mouse.create();
        mouseHelper = new MouseHelper(mcCanvas);

        try
        {
            Controllers.create();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        func_52004_D();
        checkGLError("Pre startup");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        checkGLError("Startup");
        glCapabilities = new OpenGlCapsChecker();
        sndManager.loadSoundSettings(gameSettings);
        renderEngine.registerTextureFX(textureLavaFX);
        renderEngine.registerTextureFX(textureWaterFX);
        renderEngine.registerTextureFX(new TexturePortalFX());
        renderEngine.registerTextureFX(new TextureCompassFX(this));
        renderEngine.registerTextureFX(new TextureWatchFX(this));
        renderEngine.registerTextureFX(new TextureWaterFlowFX());
        renderEngine.registerTextureFX(new TextureLavaFlowFX());
        renderEngine.registerTextureFX(new TextureFlamesFX(0));
        renderEngine.registerTextureFX(new TextureFlamesFX(1));
        renderGlobal = new RenderGlobal(this, renderEngine);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        effectRenderer = new EffectRenderer(theWorld, renderEngine);

        try
        {
            downloadResourcesThread = new ThreadDownloadResources(mcDataDir, this);
            downloadResourcesThread.start();
        }
        catch (Exception exception1) { }

        checkGLError("Post startup");
        ingameGUI = new GuiIngame(this);

        if (serverName != null)
        {
            displayGuiScreen(new GuiConnecting(this, serverName, serverPort));
        }
        else
        {
            displayGuiScreen(new GuiMainMenu());
        }

        loadingScreen = new LoadingScreenRenderer(this);
    }

    /**
     * Displays a new screen.
     */
    private void loadScreen() throws LWJGLException
    {
        ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
        GL11.glClear(16640);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.scaledWidthD, scaledresolution.scaledHeightD, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/title/mojang.png"));
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0xffffff);
        tessellator.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        char c = 256;
        char c1 = 256;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.setColorOpaque_I(0xffffff);
        scaledTessellator((scaledresolution.getScaledWidth() - c) / 2, (scaledresolution.getScaledHeight() - c1) / 2, 0, 0, c, c1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        Display.swapBuffers();
    }

	/**
     * Loads Tessellator with a scaled resolution
     */
    public void scaledTessellator(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (float)(par3 + 0) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (float)(par3 + par5) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (float)(par3 + par5) * f, (float)(par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (float)(par3 + 0) * f, (float)(par4 + 0) * f1);
        tessellator.draw();
    }

    /**
     * gets the working dir (OS specific) for minecraft
     */
    public static File getMinecraftDir()
    {
    	minecraftDir = getAppDir("minecraftfsp");
    	return minecraftDir;
    }

    /**
     * gets the working dir (OS specific) for the specific application (which is always minecraft)
     */
    public static File getAppDir(String par0Str)
    {
        String s = System.getProperty("user.home", ".");
        File file;

        switch (EnumOSMappingHelper.enumOSMappingArray[getOs().ordinal()])
        {
            case 1:
            case 2:
                file = new File(s, (new StringBuilder()).append('.').append(par0Str).append('/').toString());
                break;

            case 3:
                String s1 = System.getenv("APPDATA");

                if (s1 != null)
                {
                    file = new File(s1, (new StringBuilder()).append(".").append(par0Str).append('/').toString());
                }
                else
                {
                    file = new File(s, (new StringBuilder()).append('.').append(par0Str).append('/').toString());
                }

                break;

            case 4:
                file = new File(s, (new StringBuilder()).append("Library/Application Support/").append(par0Str).toString());
                break;

            default:
                file = new File(s, (new StringBuilder()).append(par0Str).append('/').toString());
                break;
        }

        if (!file.exists() && !file.mkdirs())
        {
            throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(file).toString());
        }
        else
        {
            return file;
        }
    }

    private static EnumOS2 getOs()
    {
        String s = System.getProperty("os.name").toLowerCase();

        if (s.contains("win"))
        {
            return EnumOS2.windows;
        }

        if (s.contains("mac"))
        {
            return EnumOS2.macos;
        }

        if (s.contains("solaris"))
        {
            return EnumOS2.solaris;
        }

        if (s.contains("sunos"))
        {
            return EnumOS2.solaris;
        }

        if (s.contains("linux"))
        {
            return EnumOS2.linux;
        }

        if (s.contains("unix"))
        {
            return EnumOS2.linux;
        }
        else
        {
            return EnumOS2.unknown;
        }
    }

    /**
     * Returns the save loader that is currently being used
     */
    public ISaveFormat getSaveLoader()
    {
        return saveLoader;
    }

    /**
     * Sets the argument GuiScreen as the main (topmost visible) screen.
     */
    public void displayGuiScreen(GuiScreen par1GuiScreen)
    {
        if (currentScreen instanceof GuiErrorScreen)
        {
            return;
        }

        if (currentScreen != null)
        {
            currentScreen.onGuiClosed();
        }

        if (par1GuiScreen instanceof GuiMainMenu)
        {
            statFileWriter.func_27175_b();
        }

        statFileWriter.syncStats();

        if (par1GuiScreen == null && theWorld == null)
        {
            par1GuiScreen = new GuiMainMenu();
        }
        else if (par1GuiScreen == null && thePlayer.getHealth() <= 0)
        {
            par1GuiScreen = new GuiGameOver();
        }

        if (par1GuiScreen instanceof GuiMainMenu)
        {
            gameSettings.showDebugInfo = false;
            ingameGUI.clearChatMessages();
        }

        currentScreen = par1GuiScreen;

        if (par1GuiScreen != null)
        {
            setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            par1GuiScreen.setWorldAndResolution(this, i, j);
            skipRenderWorld = false;
        }
        else
        {
            setIngameFocus();
        }
    }

    /**
     * Checks for an OpenGL error. If there is one, prints the error ID and error string.
     */
    private void checkGLError(String par1Str)
    {
        int i = GL11.glGetError();

        if (i != 0)
        {
            String s = GLU.gluErrorString(i);
            System.out.println("########## GL ERROR ##########");
            System.out.println((new StringBuilder()).append("@ ").append(par1Str).toString());
            System.out.println((new StringBuilder()).append(i).append(": ").append(s).toString());
        }
    }

    /**
     * Shuts down the minecraft applet by stopping the resource downloads, and clearing up GL stuff; called when the
     * application (or web page) is exited.
     */
    public void shutdownMinecraftApplet()
    {
        try
        {
            statFileWriter.func_27175_b();
            statFileWriter.syncStats();

            if (mcApplet != null)
            {
                mcApplet.clearApplet();
            }

            try
            {
                if (downloadResourcesThread != null)
                {
                    downloadResourcesThread.closeMinecraft();
                }
            }
            catch (Exception exception) { }

            System.out.println("Stopping!");

            try
            {
                changeWorld1(null, false);
            }
            catch (Throwable throwable) { }

            try
            {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable throwable1) { }

            sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally
        {
            Display.destroy();

            if (!hasCrashed)
            {
                System.exit(0);
            }
        }

        System.gc();
    }

    public void run()
    {
        running = true;

        try
        {
            startGame();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            onMinecraftCrash(new UnexpectedThrowable("Failed to start game", exception));
            return;
        }

        try
        {
            while (running)
            {
                try
                {
                    runGameLoop();
                }
                catch (MinecraftException minecraftexception)
                {
                    theWorld = null;
                    changeWorld1(null, false);
                    displayGuiScreen(new GuiConflictWarning());
                }
                catch (OutOfMemoryError outofmemoryerror)
                {
                    freeMemory();
                    displayGuiScreen(new GuiMemoryErrorScreen());
                    System.gc();
                }
            }
        }
        catch (MinecraftError minecrafterror) { }
        catch (Throwable throwable)
        {
            freeMemory();
            throwable.printStackTrace();
            onMinecraftCrash(new UnexpectedThrowable("Unexpected error", throwable));
        }
        finally
        {
            shutdownMinecraftApplet();
        }
    }

    /**
     * Called repeatedly from run()
     */
    private void runGameLoop()
    {
        if (mcApplet != null && !mcApplet.isActive())
        {
            running = false;
            return;
        }

        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        Profiler.startSection("root");

        if (mcCanvas == null && Display.isCloseRequested())
        {
            shutdown();
        }

        if (isGamePaused && theWorld != null)
        {
            float f = timer.renderPartialTicks;
            timer.updateTimer();
            timer.renderPartialTicks = f;
        }
        else
        {
            timer.updateTimer();
        }

        long l = System.nanoTime();
        Profiler.startSection("tick");

        for (int i = 0; i < timer.elapsedTicks; i++)
        {
            ticksRan++;

            try
            {
                runTick();
                continue;
            }
            catch (MinecraftException minecraftexception)
            {
                theWorld = null;
            }

            changeWorld1(null, false);
            displayGuiScreen(new GuiConflictWarning());
        }

        Profiler.endSection();
        long l1 = System.nanoTime() - l;
        checkGLError("Pre render");
        RenderBlocks.fancyGrass = gameSettings.fancyGraphics;
        Profiler.startSection("sound");
        sndManager.setListener(thePlayer, timer.renderPartialTicks);
        Profiler.endStartSection("updatelights");

        if (theWorld != null)
        {
            theWorld.updatingLighting();
        }

        Profiler.endSection();
        Profiler.startSection("render");
        Profiler.startSection("display");
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        if (!Keyboard.isKeyDown(65))
        {
            Display.update();
        }

        if (thePlayer != null && thePlayer.isEntityInsideOpaqueBlock())
        {
            gameSettings.thirdPersonView = 0;
        }

        Profiler.endSection();

        if (!skipRenderWorld)
        {
            Profiler.startSection("gameMode");

            if (playerController != null)
            {
                playerController.setPartialTime(timer.renderPartialTicks);
            }

            Profiler.endStartSection("gameRenderer");
            entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
            Profiler.endSection();
        }

        GL11.glFlush();
        Profiler.endSection();

        if (!Display.isActive() && fullscreen)
        {
            toggleFullscreen();
        }

        Profiler.endSection();

        if (gameSettings.showDebugInfo && gameSettings.field_50119_G)
        {
            if (!Profiler.profilingEnabled)
            {
                Profiler.clearProfiling();
            }

            Profiler.profilingEnabled = true;
            displayDebugInfo(l1);
        }
        else
        {
            Profiler.profilingEnabled = false;
            prevFrameTime = System.nanoTime();
        }

        guiAchievement.updateAchievementWindow();
        Profiler.startSection("root");
        Thread.yield();

        if (Keyboard.isKeyDown(65))
        {
            Display.update();
        }

        screenshotListener();

        if (mcCanvas != null && !fullscreen && (mcCanvas.getWidth() != displayWidth || mcCanvas.getHeight() != displayHeight))
        {
            displayWidth = mcCanvas.getWidth();
            displayHeight = mcCanvas.getHeight();

            if (displayWidth <= 0)
            {
                displayWidth = 1;
            }

            if (displayHeight <= 0)
            {
                displayHeight = 1;
            }

            resize(displayWidth, displayHeight);
        }

        checkGLError("Post render");
        fpsCounter++;
        isGamePaused = !isMultiplayerWorld() && currentScreen != null && currentScreen.doesGuiPauseGame();

        while (System.currentTimeMillis() >= debugUpdateTime + 1000L)
        {
            debug = (new StringBuilder()).append(fpsCounter).append(" fps, ").append(WorldRenderer.chunksUpdated).append(" chunk updates").toString();
            WorldRenderer.chunksUpdated = 0;
            debugUpdateTime += 1000L;
            fpsCounter = 0;
        }

        Profiler.endSection();
    }

    public void freeMemory()
    {
        try
        {
            field_28006_b = new byte[0];
            renderGlobal.func_28137_f();
        }
        catch (Throwable throwable) { }

        try
        {
            System.gc();
            AxisAlignedBB.clearBoundingBoxes();
            Vec3D.clearVectorList();
        }
        catch (Throwable throwable1) { }

        try
        {
            System.gc();
            changeWorld1(null, false);
        }
        catch (Throwable throwable2) { }

        System.gc();
    }

    /**
     * checks if keys are down
     */
    private void screenshotListener()
    {
        if (Keyboard.isKeyDown(60))
        {
            if (!isTakingScreenshot)
            {
                isTakingScreenshot = true;
                ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, displayWidth, displayHeight));
            }
        }
        else
        {
            isTakingScreenshot = false;
        }
    }

    /**
     * Update debugProfilerName in response to number keys in debug screen
     */
    private void updateDebugProfilerName(int par1)
    {
        java.util.List list;
        ProfilerResult profilerresult;
        list = Profiler.getProfilingData(debugProfilerName);

        if (list == null || list.size() == 0)
        {
            return;
        }

        profilerresult = (ProfilerResult)list.remove(0);

        if (!(par1 != 0))
        {
            if (profilerresult.name.length() > 0)
            {
                int i = debugProfilerName.lastIndexOf(".");

                if (i >= 0)
                {
                    debugProfilerName = debugProfilerName.substring(0, i);
                }
            }
        }
        else if (!(--par1 >= list.size() || ((ProfilerResult)list.get(par1)).name.equals("unspecified")))
        {
            if (!(debugProfilerName.length() <= 0))
            {
                debugProfilerName += ".";
            }

            debugProfilerName += ((ProfilerResult)list.get(par1)).name;
        }
    }

    private void displayDebugInfo(long par1)
    {
        java.util.List list = Profiler.getProfilingData(debugProfilerName);
        ProfilerResult profilerresult = (ProfilerResult)list.remove(0);
        long l = 0xfe502aL;
        //long l = 0;
        if (prevFrameTime == -1L)
        {
            prevFrameTime = System.nanoTime();
        }

        long l1 = System.nanoTime();
        tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = par1;
        frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = l1 - prevFrameTime;
        prevFrameTime = l1;
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, displayWidth, displayHeight, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(7);
        int i = (int)(l / 0x30d40L);
        tessellator.setColorOpaque_I(0x20000000);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.setColorOpaque_I(0x20200000);
        tessellator.addVertex(0.0D, displayHeight - i * 2, 0.0D);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i * 2, 0.0D);
        tessellator.draw();
        long l2 = 0L;

        for (int j = 0; j < frameTimes.length; j++)
        {
            l2 += frameTimes[j];
        }

        int k = (int)(l2 / 0x30d40L / (long)frameTimes.length);
        tessellator.startDrawing(7);
        tessellator.setColorOpaque_I(0x20400000);
        tessellator.addVertex(0.0D, displayHeight - k, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - k, 0.0D);
        tessellator.draw();
        tessellator.startDrawing(1);

        for (int i1 = 0; i1 < frameTimes.length; i1++)
        {
            int k1 = ((i1 - numRecordedFrameTimes & frameTimes.length - 1) * 255) / frameTimes.length;
            int j2 = (k1 * k1) / 255;
            j2 = (j2 * j2) / 255;
            int i3 = (j2 * j2) / 255;
            i3 = (i3 * i3) / 255;

            if (frameTimes[i1] > l)
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000);
            }
            else
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 256);
            }

            long l3 = frameTimes[i1] / 0x30d40L;
            long l4 = tickTimes[i1] / 0x30d40L;
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l3) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)displayHeight + 0.5F, 0.0D);
            tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000 + j2 * 256 + j2 * 1);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l3) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - (l3 - l4)) + 0.5F, 0.0D);
        }

        tessellator.draw();
        int j1 = 160;
        int i2 = displayWidth - j1 - 10;
        int k2 = displayHeight - j1 * 2;
        GL11.glEnable(GL11.GL_BLEND);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 200);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        double d = 0.0D;
        
        for (int j3 = 0; j3 < list.size(); j3++)
        {
            ProfilerResult profilerresult1 = (ProfilerResult)list.get(j3);
            int i4 = MathHelper.floor_double(profilerresult1.sectionPercentage / 4D) + 1;
            tessellator.startDrawing(6);
            tessellator.setColorOpaque_I(profilerresult1.getDisplayColor());
            tessellator.addVertex(i2, k2, 0.0D);

            for (int k4 = i4; k4 >= 0; k4--)
            {
                float f = (float)(((d + (profilerresult1.sectionPercentage * (double)k4) / (double)i4) * Math.PI * 2D) / 100D);
                float f2 = MathHelper.sin(f) * (float)j1;
                float f4 = MathHelper.cos(f) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f2, (float)k2 - f4, 0.0D);
            }

            tessellator.draw();
            tessellator.startDrawing(5);
            tessellator.setColorOpaque_I((profilerresult1.getDisplayColor() & 0xfefefe) >> 1);

            for (int i5 = i4; i5 >= 0; i5--)
            {
                float f1 = (float)(((d + (profilerresult1.sectionPercentage * (double)i5) / (double)i4) * Math.PI * 2D) / 100D);
                float f3 = MathHelper.sin(f1) * (float)j1;
                float f5 = MathHelper.cos(f1) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f3, (float)k2 - f5, 0.0D);
                tessellator.addVertex((float)i2 + f3, ((float)k2 - f5) + 10F, 0.0D);
            }

            tessellator.draw();
            d += profilerresult1.sectionPercentage;
        }

        DecimalFormat decimalformat = new DecimalFormat("##0.00");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        String s = "";

        if (!profilerresult.name.equals("unspecified"))
        {
            s = (new StringBuilder()).append(s).append("[0] ").toString();
        }

        if (profilerresult.name.length() == 0)
        {
            s = (new StringBuilder()).append(s).append("ROOT ").toString();
        }
        else
        {
            s = (new StringBuilder()).append(s).append(profilerresult.name).append(" ").toString();
        }

        int j4 = 0xffffff;
        fontRenderer.drawStringWithShadow(s, i2 - j1, k2 - j1 / 2 - 16, j4);
        fontRenderer.drawStringWithShadow(s = (new StringBuilder()).append(decimalformat.format(profilerresult.globalPercentage)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s), k2 - j1 / 2 - 16, j4);

        for (int k3 = 0; k3 < list.size(); k3++)
        {
            ProfilerResult profilerresult2 = (ProfilerResult)list.get(k3);
            String s1 = "";

            if (!profilerresult2.name.equals("unspecified"))
            {
                s1 = (new StringBuilder()).append(s1).append("[").append(k3 + 1).append("] ").toString();
            }
            else
            {
                s1 = (new StringBuilder()).append(s1).append("[?] ").toString();
            }

            s1 = (new StringBuilder()).append(s1).append(profilerresult2.name).toString();
            fontRenderer.drawStringWithShadow(s1, i2 - j1, k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.sectionPercentage)).append("%").toString(), (i2 + j1) - 50 - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.globalPercentage)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.getDisplayColor());
        }
    }

    /**
     * Called when the window is closing. Sets 'running' to false which allows the game loop to exit cleanly.
     */
    public void shutdown()
    {
        running = false;
    }

    /**
     * Will set the focus to ingame if the Minecraft window is the active with focus. Also clears any GUI screen
     * currently displayed
     */
    public void setIngameFocus()
    {
        if (!Display.isActive())
        {
            return;
        }

        if (inGameHasFocus)
        {
            return;
        }
        else
        {
            inGameHasFocus = true;
            mouseHelper.grabMouseCursor();
            displayGuiScreen(null);
            leftClickCounter = 10000;
            return;
        }
    }

    /**
     * Resets the player keystate, disables the ingame focus, and ungrabs the mouse cursor.
     */
    public void setIngameNotInFocus()
    {
        if (!inGameHasFocus)
        {
            return;
        }
        else
        {
            KeyBinding.unPressAllKeys();
            inGameHasFocus = false;
            mouseHelper.ungrabMouseCursor();
            return;
        }
    }

    /**
     * Displays the ingame menu
     */
    public void displayInGameMenu()
    {
        if (currentScreen != null)
        {
            return;
        }
        else
        {
            displayGuiScreen(new GuiIngameMenu());
            return;
        }
    }

    private void sendClickBlockToController(int par1, boolean par2)
    {
        if (!par2)
        {
            leftClickCounter = 0;
        }

        if (par1 == 0 && leftClickCounter > 0)
        {
            return;
        }

        if (par2 && objectMouseOver != null && objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && par1 == 0)
        {
            int i = objectMouseOver.blockX;
            int j = objectMouseOver.blockY;
            int k = objectMouseOver.blockZ;
            playerController.onPlayerDamageBlock(i, j, k, objectMouseOver.sideHit);

            if (thePlayer.canPlayerEdit(i, j, k))
            {
                effectRenderer.addBlockHitEffects(i, j, k, objectMouseOver.sideHit);
                thePlayer.swingItem();
            }
        }
        else
        {
            playerController.resetBlockRemoving();
        }
    }

    /**
     * Called whenever the mouse is clicked. Button clicked is 0 for left clicking and 1 for right clicking. Args:
     * buttonClicked
     */
    private void clickMouse(int par1)
    {
        if (par1 == 0 && leftClickCounter > 0)
        {
            return;
        }

        if (par1 == 0)
        {
            thePlayer.swingItem();
        }

        if (par1 == 1)
        {
            rightClickDelayTimer = 4;
        }

        boolean flag = true;
        ItemStack itemstack = thePlayer.inventory.getCurrentItem();

        if (objectMouseOver == null)
        {
            if (par1 == 0 && playerController.isNotCreative())
            {
                leftClickCounter = 10;
            }
        }
        else if (objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)
        {
            if (par1 == 0)
            {
                playerController.attackEntity(thePlayer, objectMouseOver.entityHit);
            }

            if (par1 == 1)
            {
                playerController.interactWithEntity(thePlayer, objectMouseOver.entityHit);
            }
        }
        else if (objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = objectMouseOver.blockX;
            int j = objectMouseOver.blockY;
            int k = objectMouseOver.blockZ;
            int l = objectMouseOver.sideHit;

            if (par1 == 0)
            {
                playerController.clickBlock(i, j, k, objectMouseOver.sideHit);
            }
            else
            {
                ItemStack itemstack2 = itemstack;
                int i1 = itemstack2 == null ? 0 : itemstack2.stackSize;

                if (playerController.onPlayerRightClick(thePlayer, theWorld, itemstack2, i, j, k, l))
                {
                    flag = false;
                    thePlayer.swingItem();
                }

                if (itemstack2 == null)
                {
                    return;
                }

                if (itemstack2.stackSize == 0)
                {
                    thePlayer.inventory.mainInventory[thePlayer.inventory.currentItem] = null;
                }
                else if (itemstack2.stackSize != i1 || playerController.isInCreativeMode())
                {
                    entityRenderer.itemRenderer.func_9449_b();
                }
            }
        }

        if (flag && par1 == 1)
        {
            ItemStack itemstack1 = thePlayer.inventory.getCurrentItem();

            if (itemstack1 != null && playerController.sendUseItem(thePlayer, theWorld, itemstack1))
            {
                entityRenderer.itemRenderer.func_9450_c();
            }
        }
    }

    /**
     * Toggles fullscreen mode.
     */
    public void toggleFullscreen()
    {
        try
        {
            fullscreen = !fullscreen;

            if (fullscreen)
            {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                displayWidth = Display.getDisplayMode().getWidth();
                displayHeight = Display.getDisplayMode().getHeight();

                if (displayWidth <= 0)
                {
                    displayWidth = 1;
                }

                if (displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            }
            else
            {
                if (mcCanvas != null)
                {
                    displayWidth = mcCanvas.getWidth();
                    displayHeight = mcCanvas.getHeight();
                }
                else
                {
                    displayWidth = tempDisplayWidth;
                    displayHeight = tempDisplayHeight;
                }

                if (displayWidth <= 0)
                {
                    displayWidth = 1;
                }

                if (displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            }

            if (currentScreen != null)
            {
                resize(displayWidth, displayHeight);
            }

            Display.setFullscreen(fullscreen);
            Display.update();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Called to resize the current screen.
     */
    private void resize(int par1, int par2)
    {
        if (par1 <= 0)
        {
            par1 = 1;
        }

        if (par2 <= 0)
        {
            par2 = 1;
        }

        displayWidth = par1;
        displayHeight = par2;

        if (currentScreen != null)
        {
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, par1, par2);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            currentScreen.setWorldAndResolution(this, i, j);
        }
    }

    private void startThreadCheckHasPaid()
    {
        (new ThreadCheckHasPaid(this)).start();
    }

    /**
     * Runs the current tick.
     */
    public void runTick()
    {
        if (rightClickDelayTimer > 0)
        {
            rightClickDelayTimer--;
        }

        if (ticksRan == 6000)
        {
            startThreadCheckHasPaid();
        }

        Profiler.startSection("stats");
        statFileWriter.func_27178_d();
        Profiler.endStartSection("gui");

        if (!isGamePaused)
        {
            ingameGUI.updateTick();
        }

        Profiler.endStartSection("pick");
        entityRenderer.getMouseOver(1.0F);
        Profiler.endStartSection("centerChunkSource");

        if (thePlayer != null)
        {
            net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getChunkProvider();

            if (ichunkprovider instanceof ChunkProviderLoadOrGenerate)
            {
                ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
                int k = MathHelper.floor_float((int)thePlayer.posX) >> 4;
                int j1 = MathHelper.floor_float((int)thePlayer.posZ) >> 4;
                chunkproviderloadorgenerate.setCurrentChunkOver(k, j1);
            }
        }

        Profiler.endStartSection("gameMode");

        if (!isGamePaused && theWorld != null)
        {
            playerController.updateController();
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/terrain.png"));
        Profiler.endStartSection("textures");

        if (!isGamePaused)
        {
            renderEngine.updateDynamicTextures();
        }

        if (currentScreen == null && thePlayer != null)
        {
            if (thePlayer.getHealth() <= 0)
            {
                displayGuiScreen(null);
            }
            else if (thePlayer.isPlayerSleeping() && theWorld != null && theWorld.isRemote)
            {
                displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (currentScreen != null && (currentScreen instanceof GuiSleepMP) && !thePlayer.isPlayerSleeping())
        {
            displayGuiScreen(null);
        }

        if (currentScreen != null)
        {
            leftClickCounter = 10000;
        }

        if (currentScreen != null)
        {
            currentScreen.handleInput();

            if (currentScreen != null)
            {
                currentScreen.guiParticles.update();
                currentScreen.updateScreen();
            }
        }

        if (currentScreen == null || currentScreen.allowUserInput)
        {
            Profiler.endStartSection("mouse");

            do
            {
                if (!Mouse.next())
                {
                    break;
                }

                KeyBinding.setKeyBindState(Mouse.getEventButton() - 100, Mouse.getEventButtonState());

                if (Mouse.getEventButtonState())
                {
                    KeyBinding.onTick(Mouse.getEventButton() - 100);
                }

                long l = System.currentTimeMillis() - systemTime;

                if (l <= 200L)
                {
                    int i1 = Mouse.getEventDWheel();

                    if (i1 != 0)
                    {
                        thePlayer.inventory.changeCurrentItem(i1);

                        if (gameSettings.noclip)
                        {
                            if (i1 > 0)
                            {
                                i1 = 1;
                            }

                            if (i1 < 0)
                            {
                                i1 = -1;
                            }

                            gameSettings.noclipRate += (float)i1 * 0.25F;
                        }
                    }

                    if (currentScreen == null)
                    {
                        if (!inGameHasFocus && Mouse.getEventButtonState())
                        {
                            setIngameFocus();
                        }
                    }
                    else if (currentScreen != null)
                    {
                        currentScreen.handleMouseInput();
                    }
                }
            }
            while (true);

            if (leftClickCounter > 0)
            {
                leftClickCounter--;
            }

            Profiler.endStartSection("keyboard");

            do
            {
                if (!Keyboard.next())
                {
                    break;
                }

                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());

                if (Keyboard.getEventKeyState())
                {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }

                if (Keyboard.getEventKeyState())
                {
                    if (Keyboard.getEventKey() == 87)
                    {
                        toggleFullscreen();
                    }
                    else
                    {
                        if (currentScreen != null)
                        {
                            currentScreen.handleKeyboardInput();
                        }
                        else
                        {
                            if (Keyboard.getEventKey() == 1)
                            {
                                displayInGameMenu();
                            }

                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
                            {
                                forceReload();
                            }

                            if (Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
                            {
                                renderEngine.refreshTextures();
                            }

                            if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61))
                            {
                                boolean flag = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                                gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, flag ? -1 : 1);
                            }

                            if (Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61))
                            {
                                renderGlobal.loadRenderers();
                            }

                            if (Keyboard.getEventKey() == 59)
                            {
                                gameSettings.hideGUI = !gameSettings.hideGUI;
                            }

                            if (Keyboard.getEventKey() == 61)
                            {
                                gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
                                gameSettings.field_50119_G = !GuiScreen.func_50049_m();
                            }

                            if (Keyboard.getEventKey() == 63)
                            {
                                gameSettings.thirdPersonView++;

                                if (gameSettings.thirdPersonView > 2)
                                {
                                    gameSettings.thirdPersonView = 0;
                                }
                            }

                            if (Keyboard.getEventKey() == 66)
                            {
                                gameSettings.smoothCamera = !gameSettings.smoothCamera;
                            }
                        }

                        for (int i = 0; i < 9; i++)
                        {
                            if (Keyboard.getEventKey() == 2 + i)
                            {
                                thePlayer.inventory.currentItem = i;
                            }
                        }

                        if (gameSettings.showDebugInfo && gameSettings.field_50119_G)
                        {
                            if (Keyboard.getEventKey() == 11)
                            {
                                updateDebugProfilerName(0);
                            }

                            int j = 0;

                            while (j < 9)
                            {
                                if (Keyboard.getEventKey() == 2 + j)
                                {
                                    updateDebugProfilerName(j + 1);
                                }

                                j++;
                            }
                        }
                    }
                }
            }
            while (true);

            for (; gameSettings.keyBindInventory.isPressed(); displayGuiScreen(new GuiInventory(thePlayer))) { }

            for (; gameSettings.keyBindDrop.isPressed(); thePlayer.dropOneItem()) { }

            for (; isMultiplayerWorld() && gameSettings.keyBindChat.isPressed(); displayGuiScreen(new GuiChat())) { }

            if (isMultiplayerWorld() && currentScreen == null && (Keyboard.isKeyDown(53) || Keyboard.isKeyDown(181)))
            {
                displayGuiScreen(new GuiChat("/"));
            }

            if (thePlayer.isUsingItem())
            {
                if (!gameSettings.keyBindUseItem.pressed)
                {
                    playerController.onStoppedUsingItem(thePlayer);
                }

                while (gameSettings.keyBindAttack.isPressed()) ;

                while (gameSettings.keyBindUseItem.isPressed()) ;

                while (gameSettings.keyBindPickBlock.isPressed()) ;
            }
            else
            {
                for (; gameSettings.keyBindAttack.isPressed(); clickMouse(0)) { }

                for (; gameSettings.keyBindUseItem.isPressed(); clickMouse(1)) { }

                for (; gameSettings.keyBindPickBlock.isPressed(); clickMiddleMouseButton()) { }
            }

            if (gameSettings.keyBindUseItem.pressed && rightClickDelayTimer == 0 && !thePlayer.isUsingItem())
            {
                clickMouse(1);
            }

            sendClickBlockToController(0, currentScreen == null && gameSettings.keyBindAttack.pressed && inGameHasFocus);
        }

        if (theWorld != null)
        {
            if (thePlayer != null)
            {
                joinPlayerCounter++;

                if (joinPlayerCounter == 30)
                {
                    joinPlayerCounter = 0;
                    theWorld.joinEntityInSurroundings(thePlayer);
                }
            }

            if (theWorld.getWorldInfo().isHardcoreModeEnabled())
            {
                theWorld.difficultySetting = 3;
            }
            else
            {
                theWorld.difficultySetting = gameSettings.difficulty;
            }

            if (theWorld.isRemote)
            {
                theWorld.difficultySetting = 1;
            }

            Profiler.endStartSection("gameRenderer");

            if (!isGamePaused)
            {
                entityRenderer.updateRenderer();
            }

            Profiler.endStartSection("levelRenderer");

            if (!isGamePaused)
            {
                renderGlobal.updateClouds();
            }

            Profiler.endStartSection("level");

            if (!isGamePaused)
            {
                if (theWorld.lightningFlash > 0)
                {
                    theWorld.lightningFlash--;
                }

                theWorld.updateEntities();
            }

            if (!isGamePaused || isMultiplayerWorld())
            {
                theWorld.setAllowedSpawnTypes(theWorld.difficultySetting > 0, true);
                theWorld.tick();
            }

            Profiler.endStartSection("animateTick");

            if (!isGamePaused && theWorld != null)
            {
                theWorld.randomDisplayUpdates(MathHelper.floor_double(thePlayer.posX), MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
            }

            Profiler.endStartSection("particles");

            if (!isGamePaused)
            {
                effectRenderer.updateEffects();
            }
        }

        Profiler.endSection();
        systemTime = System.currentTimeMillis();
    }

    /**
     * Forces a reload of the sound manager and all the resources. Called in game by holding 'F3' and pressing 'S'.
     */
    private void forceReload()
    {
        System.out.println("FORCING RELOAD!");
        sndManager = new SoundManager();
        sndManager.loadSoundSettings(gameSettings);
        downloadResourcesThread.reloadResources();
    }

    /**
     * Checks if the current world is a multiplayer world, returns true if it is, false otherwise.
     */
    public boolean isMultiplayerWorld()
    {
        return theWorld != null && theWorld.isRemote;
    }

    /**
     * creates a new world or loads an existing one
     */
    public void startWorld(String par1Str, String par2Str, WorldSettings par3WorldSettings)
    {
        loadingScreen.printText(StatCollector.translateToLocal("menu.switchingLevel"));
        loadingScreen.displayLoadingString("Loading textures");
        
        if(gameSettings.preloadTextures)
        	preloadTextures();
        
        changeWorld1(null, false);
        System.gc();

        if (saveLoader.isOldMapFormat(par1Str))
        {
            convertMapFormat(par1Str, par2Str);
        }
        else
        {
            if (loadingScreen != null)
            {
                loadingScreen.printText(StatCollector.translateToLocal("menu.switchingLevel"));
                loadingScreen.displayLoadingString("");
            }
            
            net.minecraft.src.ISaveHandler isavehandler = saveLoader.getSaveLoader(par1Str, false);
            World world = null;
            world = new World(isavehandler, par2Str, par3WorldSettings);

            if (world.isNewWorld)
            {
                statFileWriter.readStat(StatList.createWorldStat, 1);
                statFileWriter.readStat(StatList.startGameStat, 1);
                changeWorld2(world, StatCollector.translateToLocal("menu.generatingLevel"), true);
            }
            else
            {
                statFileWriter.readStat(StatList.loadWorldStat, 1);
                statFileWriter.readStat(StatList.startGameStat, 1);
                changeWorld2(world, StatCollector.translateToLocal("menu.loadingLevel"), false);
            }
        }
    }

    /**
     * Will use a portal teleport switching the dimension the player is in.
     */
    public void usePortal(int par1)
    {
        int i = thePlayer.dimension;
        thePlayer.dimension = par1;
        theWorld.setEntityDead(thePlayer);
        thePlayer.isDead = false;
        double d = thePlayer.posX;
        double d1 = thePlayer.posZ;
        double d2 = 1.0D;

        if (i > -1 && thePlayer.dimension == -1)
        {
            d2 = 1D;
        }
        else if (i == -1 && thePlayer.dimension > -1)
        {
            d2 = 1D;
        }

        d *= d2;
        d1 *= d2;

        if (thePlayer.dimension == -1)
        {
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);

            if (thePlayer.isEntityAlive())
            {
                theWorld.updateEntityWithOptionalForce(thePlayer, false);
            }

            World world = null;
            world = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));
            changeWorld(world, "Entering the Nether", thePlayer, false);
        }
        else if (thePlayer.dimension == 0)
        {
            if (thePlayer.isEntityAlive())
            {
                thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
                theWorld.updateEntityWithOptionalForce(thePlayer, false);
            }

            World world1 = null;
            world1 = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));

            if (i == -1)
            {
                changeWorld(world1, "Leaving the Nether", thePlayer, false);
            }
            else
            {
                changeWorld(world1, "Leaving the End", thePlayer, false);
            }
        }
        else
        {
            World world2 = null;
            world2 = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));
            System.out.println(WorldProvider.getProviderForDimension(thePlayer.dimension));
            ChunkCoordinates chunkcoordinates = world2.getEntrancePortalLocation();
            d = chunkcoordinates.posX;
            thePlayer.posY = chunkcoordinates.posY;
            d1 = chunkcoordinates.posZ;
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, 90F, 0.0F);

            if (thePlayer.isEntityAlive())
            {
                world2.updateEntityWithOptionalForce(thePlayer, false);
            }

            changeWorld(world2, "Entering the End", thePlayer, false);
        }

        thePlayer.worldObj = theWorld;
        System.out.println((new StringBuilder()).append("Teleported to ").append(theWorld.worldProvider.worldType).toString());

        if (thePlayer.isEntityAlive() && i < 1)
        {
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
            theWorld.updateEntityWithOptionalForce(thePlayer, false);
            (new Teleporter()).placeInPortal(theWorld, thePlayer);
        }
    }

    /**
     * Unloads the current world, and displays a String while waiting
     */
    public void exitToMainMenu(String par1Str)
    {
        theWorld = null;
        changeWorld2(null, par1Str, false);
    }

    /**
     * Changes the world, no message, no player.
     */
    public void changeWorld1(World par1World, boolean isNewWorld)
    {
        changeWorld2(par1World, "", false);
    }

    /**
     * Changes the world with given message, no player.
     */
    public void changeWorld2(World par1World, String par2Str, boolean isNewWorld)
    {
        changeWorld(par1World, par2Str, null, isNewWorld);
    }

    /**
     * first argument is the world to change to, second one is a loading message and the third the player itself
     */
    public void changeWorld(World par1World, String par2Str, EntityPlayer par3EntityPlayer, boolean isNewWorld)
    {
        statFileWriter.func_27175_b();
        statFileWriter.syncStats();
        renderViewEntity = null;

        if (loadingScreen != null)
        {
            loadingScreen.printText(par2Str);
            loadingScreen.displayLoadingString("");
        }

        sndManager.playStreaming(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        if (theWorld != null)
        {
            theWorld.saveWorldIndirectly(loadingScreen, true);
        }
        
        theWorld = par1World;

        if (par1World != null)
        {
            if (playerController != null)
            {
                playerController.onWorldChange(par1World);
            }

            if (!isMultiplayerWorld())
            {
                if (par3EntityPlayer == null)
                {
                    thePlayer = (EntityPlayerSP)par1World.func_4085_a(net.minecraft.src.EntityPlayerSP.class);
                }
            }
            else if (thePlayer != null)
            {
                thePlayer.preparePlayerToSpawn();

                if (par1World != null)
                {
                    par1World.spawnEntityInWorld(thePlayer);
                }
            }

            if (!par1World.isRemote)
            {
                preloadWorld(par2Str, isNewWorld);
            } 

            if (thePlayer == null)
            {
                thePlayer = (EntityPlayerSP)playerController.createPlayer(par1World);
                thePlayer.preparePlayerToSpawn();
                playerController.flipPlayer(thePlayer);
            }

            thePlayer.movementInput = new MovementInputFromOptions(gameSettings);

            if (renderGlobal != null)
            {
                renderGlobal.changeWorld(par1World);
            }

            if (effectRenderer != null)
            {
                effectRenderer.clearEffects(par1World);
            }

            if (par3EntityPlayer != null)
            {
                par1World.func_6464_c();
            }

            net.minecraft.src.IChunkProvider ichunkprovider = par1World.getChunkProvider();
            
            if (ichunkprovider instanceof ChunkProviderLoadOrGenerate)
            {
                ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
                int i = MathHelper.floor_float((int)thePlayer.posX) >> 4;
                int j = MathHelper.floor_float((int)thePlayer.posZ) >> 4;
                chunkproviderloadorgenerate.setCurrentChunkOver(i, j);
            }

            par1World.spawnPlayerWithLoadedChunks(thePlayer);
            playerController.func_6473_b(thePlayer);

            if (par1World.isNewWorld)
            {
                par1World.saveWorldIndirectly(loadingScreen, true);
            }

            renderViewEntity = thePlayer;
        }
        else
        {
            saveLoader.flushCache();
            thePlayer = null;
        }

        System.gc();
        systemTime = 0L;
    }

    /**
     * Converts from old map format to new map format
     */
    private void convertMapFormat(String par1Str, String par2Str)
    {
        loadingScreen.printText((new StringBuilder()).append("Converting World to ").append(saveLoader.getFormatName()).toString());
        loadingScreen.displayLoadingString("This may take a while :)");
        saveLoader.convertMapFormat(par1Str, loadingScreen);
        startWorld(par1Str, par2Str, new WorldSettings(0L, 0, true, false, WorldType.DEFAULT, 16));
    }

    /**
     * Display the preload world loading screen then load SP World.
     */
    private void preloadWorld(String par1Str, boolean isNewWorld)
    {
    	int c = theWorld.getWorldInfo().getWorldSize();
    	
        if (loadingScreen != null)
        {
            loadingScreen.printText(par1Str);
            loadingScreen.displayLoadingString("Building terrain");
        }

        if (playerController.func_35643_e())
        {
            c = '@';
        }

        int i = 0;
        int j = (c * 2);
        j *= j;
        net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getChunkProvider();
        
        ChunkCoordinates chunkcoordinates = theWorld.getSpawnPoint();

        if (thePlayer != null)
        {
            chunkcoordinates.posX = (int)thePlayer.posX;
            chunkcoordinates.posZ = (int)thePlayer.posZ;
        }

        if (ichunkprovider instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
            chunkproviderloadorgenerate.setCurrentChunkOver(chunkcoordinates.posX >> 4, chunkcoordinates.posZ >> 4);
        }
        
        Runtime rt = Runtime.getRuntime();
        int totalChunks = 0;
        long startTime = System.nanoTime();
        for (int k = -c; k < c; k ++)
        {
            for (int l = -c; l < c; l ++)
            {
                if (loadingScreen != null)
                {
                    loadingScreen.setLoadingProgress((i++ * 100) / j);
                }
                
                ichunkprovider.preloadChunk(k, l);
                totalChunks++;
                if (playerController.func_35643_e())
                {
                    continue;
                }

                while (theWorld.updatingLighting()) ;
            }
        }
        long endTime = System.nanoTime();
        float totalTime = (endTime - startTime) / 1000000000.0f;
        float avg = totalTime / totalChunks;
        
        System.out.println("Generated " + totalChunks + " chunks in " + totalTime + " @ " + avg + " s/chunk");
        
        if(isNewWorld){
        	gameSettings.avgChunkTime = avg;
        	gameSettings.saveOptions();
        	System.out.println("New world, saving new avgerage");
        }
        
        if (!playerController.func_35643_e())
        {
            if (loadingScreen != null)
            {
                loadingScreen.displayLoadingString(StatCollector.translateToLocal("menu.simulating"));
            }

            char c1 = 2000;
            theWorld.dropOldChunks();
        }
    }

    /**
     * Installs a resource. Currently only sounds are download so this method just adds them to the SoundManager.
     */
    public void installResource(String par1Str, File par2File)
    {
        int i = par1Str.indexOf("/");
        String s = par1Str.substring(0, i);
        par1Str = par1Str.substring(i + 1);

        if (s.equalsIgnoreCase("sound"))
        {
            sndManager.addSound(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("newsound"))
        {
            sndManager.addSound(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("streaming"))
        {
            sndManager.addStreaming(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("music"))
        {
            sndManager.addMusic(par1Str, par2File);
        }
        else if (s.equalsIgnoreCase("newmusic"))
        {
            sndManager.addMusic(par1Str, par2File);
        }
    }

    /**
     * A String of renderGlobal.getDebugInfoRenders
     */
    public String debugInfoRenders()
    {
        return renderGlobal.getDebugInfoRenders();
    }

    /**
     * Gets the information in the F3 menu about how many entities are infront/around you
     */
    public String getEntityDebug()
    {
        return renderGlobal.getDebugInfoEntities();
    }

    /**
     * Gets the name of the world's current chunk provider
     */
    public String getWorldProviderName()
    {
        return theWorld.getProviderName();
    }

    /**
     * A String of how many entities are in the world
     */
    public String debugInfoEntities()
    {
        return (new StringBuilder()).append("P: ").append(effectRenderer.getStatistics()).append(". T: ").append(theWorld.getDebugLoadedEntities()).toString();
    }

    /**
     * Called when the respawn button is pressed after the player dies.
     */
    public void respawn(boolean par1, int par2, boolean par3)
    {
        if (!theWorld.isRemote && !theWorld.worldProvider.canRespawnHere())
        {
            usePortal(0);
        }

        ChunkCoordinates chunkcoordinates = null;
        ChunkCoordinates chunkcoordinates1 = null;
        boolean flag = true;

        if (thePlayer != null && !par1)
        {
            chunkcoordinates = thePlayer.getSpawnChunk();

            if (chunkcoordinates != null)
            {
                chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(theWorld, chunkcoordinates);

                if (chunkcoordinates1 == null)
                {
                    thePlayer.addChatMessage("tile.bed.notValid");
                }
            }
        }

        if (chunkcoordinates1 == null)
        {
            chunkcoordinates1 = theWorld.getSpawnPoint();
            flag = false;
        }

        net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getChunkProvider();

        if (ichunkprovider instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
            chunkproviderloadorgenerate.setCurrentChunkOver(chunkcoordinates1.posX >> 4, chunkcoordinates1.posZ >> 4);
        }

        theWorld.setSpawnLocation();
        theWorld.updateEntityList();
        int i = 0;

        if (thePlayer != null)
        {
            i = thePlayer.entityId;
            theWorld.setEntityDead(thePlayer);
        }

        EntityPlayerSP entityplayersp = thePlayer;
        renderViewEntity = null;
        thePlayer = (EntityPlayerSP)playerController.createPlayer(theWorld);

        if (par3)
        {
            thePlayer.copyPlayer(entityplayersp);
        }

        thePlayer.dimension = par2;
        renderViewEntity = thePlayer;
        thePlayer.preparePlayerToSpawn();

        if (flag)
        {
            thePlayer.setSpawnChunk(chunkcoordinates);
            thePlayer.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
        }

        playerController.flipPlayer(thePlayer);
        theWorld.spawnPlayerWithLoadedChunks(thePlayer);
        thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
        thePlayer.entityId = i;
        thePlayer.func_6420_o();
        playerController.func_6473_b(thePlayer);
        preloadWorld(StatCollector.translateToLocal("menu.respawning"), false);

        if (currentScreen instanceof GuiGameOver)
        {
            displayGuiScreen(null);
        }
    }

    public static void startMainThread1(String par0Str, String par1Str)
    {
        startMainThread(par0Str, par1Str, null);
    }

    public static void startMainThread(String par0Str, String par1Str, String par2Str)
    {
        boolean flag = false;
        String s = par0Str;
        Frame frame = new Frame("Minecraft Fast SP");
        Canvas canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new Dimension(854, 480));
        frame.pack();
        frame.setLocationRelativeTo(null);
        MinecraftImpl minecraftimpl = new MinecraftImpl(frame, canvas, null, 854, 480, flag, frame);
        Thread thread = new Thread(minecraftimpl, "Minecraft main thread");
        thread.setPriority(10);
        minecraftimpl.minecraftUri = "www.minecraft.net";

        if (s != null && par1Str != null)
        {
            minecraftimpl.session = new Session(s, par1Str);
        }
        else
        {
            minecraftimpl.session = new Session((new StringBuilder()).append("Player").append(System.currentTimeMillis() % 1000L).toString(), "");
        }

        if (par2Str != null)
        {
            String as[] = par2Str.split(":");
            minecraftimpl.setServer(as[0], Integer.parseInt(as[1]));
        }

        frame.setVisible(true);
        frame.addWindowListener(new GameWindowListener(minecraftimpl, thread));
        thread.start();
    }

    /**
     * get the client packet send queue
     */
    public NetClientHandler getSendQueue()
    {
        if (thePlayer instanceof EntityClientPlayerMP)
        {
            return ((EntityClientPlayerMP)thePlayer).sendQueue;
        }
        else
        {
            return null;
        }
    }

    public static void main(String par0ArrayOfStr[])
    {
        String s = null;
        String s1 = null;
        s = (new StringBuilder()).append("Player").append(System.currentTimeMillis() % 1000L).toString();

        if (par0ArrayOfStr.length > 0)
        {
            s = par0ArrayOfStr[0];
        }

        s1 = "-";

        if (par0ArrayOfStr.length > 1)
        {
            s1 = par0ArrayOfStr[1];
        }

        startMainThread1(s, s1);
    }

    public static boolean isGuiEnabled()
    {
        return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
    }

    /**
     * Returns if ambient occlusion is enabled
     */
    public static boolean isAmbientOcclusionEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion;
    }

    public static boolean isDebugInfoEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.showDebugInfo;
    }

    /**
     * Returns true if string begins with '/'
     */
    public boolean lineIsCommand(String par1Str)
    {
        if (!par1Str.startsWith("/"));

        return false;
    }

    /**
     * Called when the middle mouse button gets clicked
     */
    private void clickMiddleMouseButton()
    {
        if (objectMouseOver != null)
        {
            boolean flag = thePlayer.capabilities.isCreativeMode;
            int i = theWorld.getBlockId(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);

            if (!flag)
            {
                if (i == Block.grass.blockID)
                {
                    i = Block.dirt.blockID;
                }

                if (i == Block.stairDouble.blockID)
                {
                    i = Block.stairSingle.blockID;
                }

                if (i == Block.bedrock.blockID)
                {
                    i = Block.stone.blockID;
                }
            }

            int j = 0;
            boolean flag1 = false;

            if (Item.itemsList[i] != null && Item.itemsList[i].getHasSubtypes())
            {
                j = theWorld.getBlockMetadata(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
                flag1 = true;
            }

            if (Item.itemsList[i] != null && (Item.itemsList[i] instanceof ItemBlock))
            {
                Block block = Block.blocksList[i];
                int l = block.idDropped(j, thePlayer.worldObj.rand, 0);

                if (l > 0)
                {
                    i = l;
                }
            }

            thePlayer.inventory.setCurrentItem(i, j, flag1, flag);

            if (flag)
            {
                int k = (thePlayer.inventorySlots.inventorySlots.size() - 9) + thePlayer.inventory.currentItem;
                playerController.sendSlotPacket(thePlayer.inventory.getStackInSlot(thePlayer.inventory.currentItem), k);
            }
        }
    }

    public static String func_52003_C()
    {
        return "1.2.5";
    }

    public static void func_52004_D()
    {
        PlayerUsageSnooper playerusagesnooper = new PlayerUsageSnooper("client");
        playerusagesnooper.func_52022_a("version", func_52003_C());
        playerusagesnooper.func_52022_a("os_name", System.getProperty("os.name"));
        playerusagesnooper.func_52022_a("os_version", System.getProperty("os.version"));
        playerusagesnooper.func_52022_a("os_architecture", System.getProperty("os.arch"));
        playerusagesnooper.func_52022_a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        playerusagesnooper.func_52022_a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        playerusagesnooper.func_52022_a("java_version", System.getProperty("java.version"));
        playerusagesnooper.func_52022_a("opengl_version", GL11.glGetString(GL11.GL_VERSION));
        playerusagesnooper.func_52022_a("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
        playerusagesnooper.func_52021_a();
    }
    

    private void preloadTextures() {
    	int total = textureList.length;
    	float dt = 100f / (float)total;
    	float progress = 0;
		for(int i = 0; i < total; i++){
			progress = dt * i;
			loadingScreen.setLoadingProgress((int)progress);
			renderEngine.getTexture(textureList[i]);
		}
		
	}
    
    static final String[] textureList = {"/achievement/bg.png",
                                         "/achivement/icons.png",
                                         "/armor/chain_1.png",
                                         "/armor/chain_2.png",
                                         "/armor/cloth_1.png",
                                         "/armor/cloth_2.png",
                                         "/armor/diamond_1.png",
                                         "/armor/diamond_2.png",
                                         "/armor/gold_1.png",
                                         "/armor/gold_2.png",
                                         "/armor/iron_1.png",
                                         "/armor/iron_2.png",
                                         "/armor/power.png",
                                         "/art/kz.png",
                                         "/environment/clouds.png",
                                         "/environment/light_normal.png",
                                         "/environment/rain.png",
                                         "/environment/snow.png",
                                         "/font/default.png",
                                         "/gui/alchemy.png",
                                         "/gui/allitems.png",
                                         "/gui/background.png",
                                         "/gui/container.png",
                                         "/gui/crafting.png",
                                         "/gui/crash_logo.png",
                                         "/gui/enchant.png",
                                         "/gui/furnace.png",
                                         "/gui/gui.png",
                                         "/gui/icons.png",
                                         "/gui/inventory.png",
                                         "/gui/items.png",
                                         "/gui/particles.png",
                                         "/gui/slot.png",
                                         "/gui/trap.png",
                                         "/gui/unknown_pack.png",
                                         "/item/arrows.png",
                                         "/item/boat.png",
                                         "/item/book.png",
                                         "/item/cart.png",
                                         "/item/chest.png",
                                         "/item/door.png",
                                         "/item/largechest.png",
                                         "/item/sign.png",
                                         "/item/xporb.png",
                                         "/misc/dial.png",
                                         "/misc/explosion.png",
                                         "/misc/foliagecolor.png",
                                         "/misc/footprint.png",
                                         "/misc/glint.png",
                                         "/misc/grasscolor.png",
                                         "/misc/mapbg.png",
                                         "/misc/mapicons.png",
                                         "/misc/particlefield.png",
                                         "/misc/pumpkinblur.png",
                                         "%clamp%/misc/shadow.png",
                                         "/misc/tunnel.png",
                                         "%blur%/misc/vignette.png",
                                         "/misc/water.png",
                                         "/misc/watercolor.png",
                                         "/mob/cat_black.png",
                                         "/mob/cat_red.png",
                                         "/mob/cat_siamese.png",
                                         "/mob/cavespider.png",
                                         "/mob/char.png",
                                         "/mob/chicken.png",
                                         "/mob/cow.png",
                                         "/mob/creeper.png",
                                         "/mob/enderman_eyes.png",
                                         "/mob/enderman.png",
                                         "/mob/fire.png",
                                         "/mob/ghast_fire.png",
                                         "/mob/ghast.png",
                                         "/mob/lava.png",
                                         "/mob/ozelot.png",
                                         "/mob/pig.png",
                                         "/mob/pigman.png",
                                         "/mob/pigzombie.png",
                                         "/mob/redcow.png",
                                         "/mob/saddle.png",
                                         "/mob/sheep_fur.png",
                                         "/mob/sheep.png",
                                         "/mob/silverfish.png",
                                         "/mob/skeleton.png",
                                         "/mob/slime.png",
                                         "/mob/snowman.png",
                                         "/mob/spider_eyes.png",
                                         "/mob/spider.png",
                                         "/mob/squid.png",
                                         "/mob/villager_golem.png",
                                         "/mob/villager.png",
                                         "/mob/wolf_angry.png",
                                         "/mob/wolf_tame.png",
                                         "/mob/wolf.png",
                                         "/mob/zombie.png",
                                         "/mob/enderdragon/beam.png",
                                         "/mob/enderdragon/body.png",
                                         "/mob/enderdragon/crystal.png",
                                         "/mob/enderdragon/dragon.png",
                                         "/mob/enderdragon/ender_eyes.png",
                                         "/mob/enderdragon/ender.png",
                                         "/mob/enderdragon/shuffle.png",
                                         "/mob/villager/butcher.png",
                                         "/mob/villager/farmer.png",
                                         "/mob/villager/librarian.png",
                                         "/mob/villager/priest.png",
                                         "/mob/villager/smith.png",
                                         "/mob/villager/villager.png",
                                         "/terrain/moon_phases.png",
                                         "/terrain/moon.png",
                                         "/terrain/sun.png",
                                         "/title/black.png",
                                         "/title/mclogo.png",
                                         "/title/mojang.png",
                                         "/ctm.png",
                                         "/pack.png",
                                         "/particles.png",
                                         "/terrain.png"};
}
