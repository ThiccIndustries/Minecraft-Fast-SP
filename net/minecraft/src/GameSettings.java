package net.minecraft.src;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GameSettings
{
    private static final String RENDER_DISTANCES[] =
    {
        "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"
    };
    private static final String DIFFICULTIES[] =
    {
        "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"
    };
    private static final String GUISCALES[] =
    {
        "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"
    };
    private static final String PARTICLES[] =
    {
        "options.particles.all", "options.particles.decreased", "options.particles.minimal"
    };
    private static final String LIMIT_FRAMERATES[] =
    {
        "performance.max", "performance.balanced", "performance.powersaver"
    };
    
    public static GameSettings active;
    public float musicVolume;
    public float soundVolume;
    public float mouseSensitivity;
    public boolean invertMouse;
    public int renderDistance;
    public boolean viewBobbing;
    public boolean anaglyph;

    /** Advanced OpenGL */
    public boolean advancedOpengl;
    public int limitFramerate;
    public boolean fancyGraphics;

    /** Smooth Lighting */
    public boolean ambientOcclusion;

    /** Clouds flag */
    public boolean clouds;
    public int ofRenderDistanceFine;
    public int ofFogType;
    public float ofFogStart;
    public int ofMipmapLevel;
    public boolean ofMipmapLinear;
    public boolean ofLoadFar;
    public int ofPreloadedChunks;
    public boolean ofOcclusionFancy;
    public boolean ofSmoothFps;
    public boolean ofSmoothInput;
    public float ofAoLevel;
    public int ofAaLevel;
    public int ofAfLevel;
    public int ofClouds;
    public float ofCloudsHeight;
    public int ofTrees;
    public int ofGrass;
    public int ofRain;
    public int ofWater;
    public int ofBetterGrass;
    public int ofAutoSaveTicks;
    public boolean ofFastDebugInfo;
    public boolean ofWeather;
    public boolean ofSky;
    public boolean ofStars;
    public boolean ofSunMoon;
    public int ofChunkUpdates;
    public boolean ofChunkUpdatesDynamic;
    public int ofTime;
    public boolean ofClearWater;
    public boolean ofDepthFog;
    public boolean ofProfiler;
    public boolean ofBetterSnow;
    public String ofFullscreenMode;
    public boolean ofSwampColors;
    public boolean ofRandomMobs;
    public boolean ofSmoothBiomes;
    public boolean ofCustomFonts;
    public boolean ofCustomColors;
    public boolean ofShowCapes;
    public int ofConnectedTextures;
    public boolean ofNaturalTextures;
    public int ofAnimatedWater;
    public int ofAnimatedLava;
    public boolean ofAnimatedFire;
    public boolean ofAnimatedPortal;
    public boolean ofAnimatedRedstone;
    public boolean ofAnimatedExplosion;
    public boolean ofAnimatedFlame;
    public boolean ofAnimatedSmoke;
    public boolean ofVoidParticles;
    public boolean ofWaterParticles;
    public boolean ofRainSplash;
    public boolean ofPortalParticles;
    public boolean ofDrippingWaterLava;
    public boolean ofAnimatedTerrain;
    public boolean ofAnimatedItems;
    public boolean ofAnimatedTextures;
    public static final int DEFAULT = 0;
    public static final int FAST = 1;
    public static final int FANCY = 2;
    public static final int OFF = 3;
    public static final int ANIM_ON = 0;
    public static final int ANIM_GENERATED = 1;
    public static final int ANIM_OFF = 2;
    public static final String DEFAULT_STR = "Default";
    public KeyBinding ofKeyBindZoom;

    /** The name of the selected texture pack. */
    public String skin;
    public KeyBinding keyBindForward;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindInventory;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindChat;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindAttack;
    public KeyBinding keyBindUseItem;
    public KeyBinding keyBindPlayerList;
    public KeyBinding keyBindPickBlock;
    public KeyBinding keyBindings[];
    protected Minecraft mc;
    private File optionsFile;
    public int difficulty;
    public boolean hideGUI;
    public int thirdPersonView;

    /** true if debug info should be displayed instead of version */
    public boolean showDebugInfo;
    public boolean field_50119_G;

    /** The lastServer string. */
    public String lastServer;

    /** No clipping for singleplayer */
    public boolean noclip;

    /** Smooth Camera Toggle */
    public boolean smoothCamera;
    public boolean debugCamEnable;

    /** No clipping movement rate */
    public float noclipRate;

    /** Change rate for debug camera */
    public float debugCamRate;
    public float fovSetting;
    public float gammaSetting;

    /** GUI scale */
    public int guiScale;

    /** Determines amount of particles. 0 = All, 1 = Decreased, 2 = Minimal */
    public int particleSetting;

    /** Game settings language */
    public String language;
    private File optionsFileOF;
    
    /** Thicc Industries Preformance settings */
    public boolean preloadTextures;
    public boolean allowOcc;
    public int entityDistance;
    public int randomUpdateDistance;
    public float avgChunkTime;	//Not a setting per say, but the best place to put it.
    							//Average time to load a single chunk. Used to calculate expected load time
    
    public GameSettings(Minecraft par1Minecraft, File par2File)
    {
    	System.out.println("new active settings");
    	active = this;
        ofRenderDistanceFine = 128;
        ofFogType = 1;
        ofFogStart = 0.8F;
        ofMipmapLevel = 0;
        ofMipmapLinear = false;
        ofLoadFar = false;
        ofPreloadedChunks = 0;
        ofOcclusionFancy = false;
        ofSmoothFps = false;
        ofSmoothInput = true;
        ofAoLevel = 1.0F;
        ofAaLevel = 0;
        ofAfLevel = 1;
        ofClouds = 0;
        ofCloudsHeight = 0.0F;
        ofTrees = 0;
        ofGrass = 0;
        ofRain = 0;
        ofWater = 0;
        ofBetterGrass = 3;
        ofAutoSaveTicks = 4000;
        ofFastDebugInfo = false;
        ofWeather = true;
        ofSky = true;
        ofStars = true;
        ofSunMoon = true;
        ofChunkUpdates = 1;
        ofChunkUpdatesDynamic = false;
        ofTime = 0;
        ofClearWater = false;
        ofDepthFog = true;
        ofProfiler = false;
        ofBetterSnow = false;
        ofFullscreenMode = "Default";
        ofSwampColors = true;
        ofRandomMobs = true;
        ofSmoothBiomes = true;
        ofCustomFonts = true;
        ofCustomColors = true;
        ofShowCapes = true;
        ofConnectedTextures = 2;
        ofNaturalTextures = false;
        ofAnimatedWater = 0;
        ofAnimatedLava = 0;
        ofAnimatedFire = true;
        ofAnimatedPortal = true;
        ofAnimatedRedstone = true;
        ofAnimatedExplosion = true;
        ofAnimatedFlame = true;
        ofAnimatedSmoke = true;
        ofVoidParticles = true;
        ofWaterParticles = true;
        ofRainSplash = true;
        ofPortalParticles = true;
        ofDrippingWaterLava = true;
        ofAnimatedTerrain = true;
        ofAnimatedItems = true;
        ofAnimatedTextures = true;
        musicVolume = 1.0F;
        soundVolume = 1.0F;
        mouseSensitivity = 0.5F;
        invertMouse = false;
        renderDistance = 0;
        viewBobbing = true;
        anaglyph = false;
        advancedOpengl = false;
        limitFramerate = 1;
        fancyGraphics = true;
        ambientOcclusion = true;
        clouds = true;
        skin = "Default";
        renderDistance = 1;
        limitFramerate = 0;
        keyBindForward = new KeyBinding("key.forward", 17);
        keyBindLeft = new KeyBinding("key.left", 30);
        keyBindBack = new KeyBinding("key.back", 31);
        keyBindRight = new KeyBinding("key.right", 32);
        keyBindJump = new KeyBinding("key.jump", 57);
        keyBindInventory = new KeyBinding("key.inventory", 18);
        keyBindDrop = new KeyBinding("key.drop", 16);
        keyBindChat = new KeyBinding("key.chat", 20);
        keyBindSneak = new KeyBinding("key.sneak", 42);
        keyBindAttack = new KeyBinding("key.attack", -100);
        keyBindUseItem = new KeyBinding("key.use", -99);
        keyBindPlayerList = new KeyBinding("key.playerlist", 15);
        keyBindPickBlock = new KeyBinding("key.pickItem", -98);
        ofKeyBindZoom = new KeyBinding("Zoom", 29);
        keyBindings = (new KeyBinding[]
                {
                    keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory,
                    keyBindChat, keyBindPlayerList, keyBindPickBlock, ofKeyBindZoom
                });
        difficulty = 2;
        hideGUI = false;
        thirdPersonView = 0;
        showDebugInfo = false;
        field_50119_G = false;
        lastServer = "";
        noclip = false;
        smoothCamera = false;
        debugCamEnable = false;
        noclipRate = 1.0F;
        debugCamRate = 1.0F;
        fovSetting = 0.0F;
        gammaSetting = 0.0F;
        guiScale = 0;
        particleSetting = 0;
        language = "en_US";
        
        preloadTextures = true;
        entityDistance = 16;
        randomUpdateDistance = 2;
        allowOcc = true;
        avgChunkTime = -1;
        
        mc = par1Minecraft;
        optionsFile = new File(par2File, "options.txt");
        optionsFileOF = new File(par2File, "optionsof.txt");
        loadOptions();
        Config.setGameSettings(this);
    }

    public GameSettings()
    {
    	System.out.println("new active settings");
    	active = this;
        ofRenderDistanceFine = 128;
        ofFogType = 1;
        ofFogStart = 0.8F;
        ofMipmapLevel = 0;
        ofMipmapLinear = false;
        ofLoadFar = false;
        ofPreloadedChunks = 0;
        ofOcclusionFancy = false;
        ofSmoothFps = false;
        ofSmoothInput = true;
        ofAoLevel = 1.0F;
        ofAaLevel = 0;
        ofAfLevel = 1;
        ofClouds = 0;
        ofCloudsHeight = 0.0F;
        ofTrees = 0;
        ofGrass = 0;
        ofRain = 0;
        ofWater = 0;
        ofBetterGrass = 3;
        ofAutoSaveTicks = 4000;
        ofFastDebugInfo = false;
        ofWeather = true;
        ofSky = true;
        ofStars = true;
        ofSunMoon = true;
        ofChunkUpdates = 1;
        ofChunkUpdatesDynamic = false;
        ofTime = 0;
        ofClearWater = false;
        ofDepthFog = true;
        ofProfiler = false;
        ofBetterSnow = false;
        ofFullscreenMode = "Default";
        ofSwampColors = true;
        ofRandomMobs = true;
        ofSmoothBiomes = true;
        ofCustomFonts = true;
        ofCustomColors = true;
        ofShowCapes = true;
        ofConnectedTextures = 2;
        ofNaturalTextures = false;
        ofAnimatedWater = 0;
        ofAnimatedLava = 0;
        ofAnimatedFire = true;
        ofAnimatedPortal = true;
        ofAnimatedRedstone = true;
        ofAnimatedExplosion = true;
        ofAnimatedFlame = true;
        ofAnimatedSmoke = true;
        ofVoidParticles = true;
        ofWaterParticles = true;
        ofRainSplash = true;
        ofPortalParticles = true;
        ofDrippingWaterLava = true;
        ofAnimatedTerrain = true;
        ofAnimatedItems = true;
        ofAnimatedTextures = true;
        musicVolume = 1.0F;
        soundVolume = 1.0F;
        mouseSensitivity = 0.5F;
        invertMouse = false;
        renderDistance = 0;
        viewBobbing = true;
        anaglyph = false;
        advancedOpengl = false;
        limitFramerate = 1;
        fancyGraphics = true;
        ambientOcclusion = true;
        clouds = true;
        skin = "Default";
        renderDistance = 1;
        limitFramerate = 0;
        keyBindForward = new KeyBinding("key.forward", 17);
        keyBindLeft = new KeyBinding("key.left", 30);
        keyBindBack = new KeyBinding("key.back", 31);
        keyBindRight = new KeyBinding("key.right", 32);
        keyBindJump = new KeyBinding("key.jump", 57);
        keyBindInventory = new KeyBinding("key.inventory", 18);
        keyBindDrop = new KeyBinding("key.drop", 16);
        keyBindChat = new KeyBinding("key.chat", 20);
        keyBindSneak = new KeyBinding("key.sneak", 42);
        keyBindAttack = new KeyBinding("key.attack", -100);
        keyBindUseItem = new KeyBinding("key.use", -99);
        keyBindPlayerList = new KeyBinding("key.playerlist", 15);
        keyBindPickBlock = new KeyBinding("key.pickItem", -98);
        keyBindings = (new KeyBinding[]
                {
                    keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory,
                    keyBindChat, keyBindPlayerList, keyBindPickBlock
                });
        difficulty = 2;
        hideGUI = false;
        thirdPersonView = 0;
        showDebugInfo = false;
        field_50119_G = false;
        lastServer = "";
        noclip = false;
        smoothCamera = false;
        debugCamEnable = false;
        noclipRate = 1.0F;
        debugCamRate = 1.0F;
        fovSetting = 0.0F;
        gammaSetting = 0.0F;
        guiScale = 0;
        particleSetting = 0;
        language = "en_US";
        
        preloadTextures = true;
        entityDistance = 16;
        randomUpdateDistance = 2;
        avgChunkTime = -1;
        allowOcc = true;
        
        Config.setGameSettings(this);
    }

    public String getKeyBindingDescription(int par1)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        return stringtranslate.translateKey(keyBindings[par1].keyDescription);
    }

    /**
     * The string that appears inside the button/slider in the options menu.
     */
    public String getOptionDisplayString(int par1)
    {
        int i = keyBindings[par1].keyCode;
        return getKeyDisplayString(i);
    }

    /**
     * Represents a key or mouse button as a string. Args: key
     */
    public static String getKeyDisplayString(int par0)
    {
        if (par0 < 0)
        {
            return StatCollector.translateToLocalFormatted("key.mouseButton", new Object[]
                    {
                        Integer.valueOf(par0 + 101)
                    });
        }
        else
        {
            return Keyboard.getKeyName(par0);
        }
    }

    /**
     * Sets a key binding.
     */
    public void setKeyBinding(int par1, int par2)
    {
        keyBindings[par1].keyCode = par2;
        saveOptions();
    }

    /**
     * If the specified option is controlled by a slider (float value), this will set the float value.
     */
    public void setOptionFloatValue(EnumOptions par1EnumOptions, float par2)
    {
    	if (par1EnumOptions == EnumOptions.ENTITY_DISTANCE)
    	{
    		int minimum = 8;
    		int maximum = 64;
    		int value = minimum + (int)((maximum - minimum) * par2);
    		entityDistance = value;
    	}
    	
    	if (par1EnumOptions == EnumOptions.RANDOM_UPDATES)
    	{
    		int minimum = 0;
    		int maximum = 8;
    		int value = minimum + (int)((maximum - minimum) * par2);
    		randomUpdateDistance = value;
    	}
    	
        if (par1EnumOptions == EnumOptions.MUSIC)
        {
            musicVolume = par2;
            mc.sndManager.onSoundOptionsChanged();
        }

        if (par1EnumOptions == EnumOptions.SOUND)
        {
            soundVolume = par2;
            mc.sndManager.onSoundOptionsChanged();
        }

        if (par1EnumOptions == EnumOptions.SENSITIVITY)
        {
            mouseSensitivity = par2;
        }

        if (par1EnumOptions == EnumOptions.FOV)
        {
            fovSetting = par2;
        }

        if (par1EnumOptions == EnumOptions.GAMMA)
        {
            gammaSetting = par2;
        }

        if (par1EnumOptions == EnumOptions.CLOUD_HEIGHT)
        {
            ofCloudsHeight = par2;
        }

        if (par1EnumOptions == EnumOptions.AO_LEVEL)
        {
            ofAoLevel = par2;
            ambientOcclusion = ofAoLevel > 0.0F;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE)
        {
            ofRenderDistanceFine = 32 + (int)(par2 * 480F);
            ofRenderDistanceFine = (ofRenderDistanceFine >> 4) << 4;
            ofRenderDistanceFine = Config.limit(ofRenderDistanceFine, 32, 512);
            renderDistance = 3;

            if (ofRenderDistanceFine > 32)
            {
                renderDistance = 2;
            }

            if (ofRenderDistanceFine > 64)
            {
                renderDistance = 1;
            }

            if (ofRenderDistanceFine > 128)
            {
                renderDistance = 0;
            }

            mc.renderGlobal.loadRenderers();
        }
    }

    private void updateWaterOpacity()
    {
        byte byte0 = 3;

        if (ofClearWater)
        {
            byte0 = 1;
        }

        Block.waterStill.setLightOpacity(byte0);
        Block.waterMoving.setLightOpacity(byte0);

        if (mc.theWorld == null)
        {
            return;
        }

        IChunkProvider ichunkprovider = mc.theWorld.chunkProvider;

        if (ichunkprovider == null)
        {
            return;
        }

        for (int i = -512; i < 512; i++)
        {
            for (int j = -512; j < 512; j++)
            {
                if (!ichunkprovider.chunkExists(i, j))
                {
                    continue;
                }

                Chunk chunk = ichunkprovider.provideChunk(i, j);

                if (chunk == null || (chunk instanceof EmptyChunk))
                {
                    continue;
                }

                ExtendedBlockStorage aextendedblockstorage[] = chunk.getBlockStorageArray();

                for (int k = 0; k < aextendedblockstorage.length; k++)
                {
                    ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[k];

                    if (extendedblockstorage == null)
                    {
                        continue;
                    }

                    NibbleArray nibblearray = extendedblockstorage.getSkylightArray();

                    if (nibblearray == null)
                    {
                        continue;
                    }

                    byte abyte0[] = nibblearray.data;

                    for (int l = 0; l < abyte0.length; l++)
                    {
                        abyte0[l] = 0;
                    }
                }

                chunk.generateSkylightMap();
            }
        }

        mc.renderGlobal.loadRenderers();
    }

    public void setAllAnimations(boolean flag)
    {
        int i = flag ? 0 : 2;
        ofAnimatedWater = i;
        ofAnimatedLava = i;
        ofAnimatedFire = flag;
        ofAnimatedPortal = flag;
        ofAnimatedRedstone = flag;
        ofAnimatedExplosion = flag;
        ofAnimatedFlame = flag;
        ofAnimatedSmoke = flag;
        ofVoidParticles = flag;
        ofWaterParticles = flag;
        ofRainSplash = flag;
        ofPortalParticles = flag;
        particleSetting = flag ? 0 : 2;
        ofDrippingWaterLava = flag;
        ofAnimatedTerrain = flag;
        ofAnimatedItems = flag;
        ofAnimatedTextures = flag;
        mc.renderEngine.refreshTextures();
    }

    /**
     * For non-float options. Toggles the option on/off, or cycles through the list i.e. render distances.
     */
    public void setOptionValue(EnumOptions par1EnumOptions, int par2)
    {
    	if(par1EnumOptions == EnumOptions.PRELOAD_TEXTURES){
    		preloadTextures = !preloadTextures;
    	}
    	
    	if(par1EnumOptions == EnumOptions.ALLOW_OCCLUSION){
    		allowOcc = !allowOcc;
    	}
    	
        if (par1EnumOptions == EnumOptions.INVERT_MOUSE)
        {
            invertMouse = !invertMouse;
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE)
        {
            renderDistance = renderDistance + par2 & 3;
            ofRenderDistanceFine = 32 << 3 - renderDistance;
        }

        if (par1EnumOptions == EnumOptions.GUI_SCALE)
        {
            guiScale = guiScale + par2 & 3;
        }

        if (par1EnumOptions == EnumOptions.PARTICLES)
        {
            particleSetting = (particleSetting + par2) % 3;
        }

        if (par1EnumOptions == EnumOptions.VIEW_BOBBING)
        {
            viewBobbing = !viewBobbing;
        }

        if (par1EnumOptions == EnumOptions.RENDER_CLOUDS)
        {
            clouds = !clouds;
        }

        if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL)
        {
            if (!Config.isOcclusionAvailable())
            {
                ofOcclusionFancy = false;
                advancedOpengl = false;
            }
            else if (!advancedOpengl)
            {
                advancedOpengl = true;
                ofOcclusionFancy = false;
            }
            else if (!ofOcclusionFancy)
            {
                ofOcclusionFancy = true;
            }
            else
            {
                ofOcclusionFancy = false;
                advancedOpengl = false;
            }

            mc.renderGlobal.setAllRenderersVisible();
        }

        if (par1EnumOptions == EnumOptions.ANAGLYPH)
        {
            anaglyph = !anaglyph;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT)
        {
            limitFramerate = (limitFramerate + par2) % 4;
            Display.setVSyncEnabled(limitFramerate == 3);
        }

        if (par1EnumOptions == EnumOptions.DIFFICULTY)
        {
            difficulty = difficulty + par2 & 3;
        }

        if (par1EnumOptions == EnumOptions.GRAPHICS)
        {
            fancyGraphics = !fancyGraphics;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION)
        {
            ambientOcclusion = !ambientOcclusion;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.FOG_FANCY)
        {
            switch (ofFogType)
            {
                case 1:
                    ofFogType = 2;

                    if (!Config.isFancyFogAvailable())
                    {
                        ofFogType = 3;
                    }

                    break;

                case 2:
                    ofFogType = 3;
                    break;

                case 3:
                    ofFogType = 1;
                    break;

                default:
                    ofFogType = 1;
                    break;
            }
        }

        if (par1EnumOptions == EnumOptions.FOG_START)
        {
            ofFogStart += 0.2F;

            if (ofFogStart > 0.81F)
            {
                ofFogStart = 0.2F;
            }
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL)
        {
            ofMipmapLevel++;

            if (ofMipmapLevel > 4)
            {
                ofMipmapLevel = 0;
            }

            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_TYPE)
        {
            ofMipmapLinear = !ofMipmapLinear;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.LOAD_FAR)
        {
            ofLoadFar = !ofLoadFar;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS)
        {
            ofPreloadedChunks += 2;

            if (ofPreloadedChunks > 8)
            {
                ofPreloadedChunks = 0;
            }

            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_FPS)
        {
            ofSmoothFps = !ofSmoothFps;
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_INPUT)
        {
            ofSmoothInput = !ofSmoothInput;
        }

        if (par1EnumOptions == EnumOptions.CLOUDS)
        {
            ofClouds++;

            if (ofClouds > 3)
            {
                ofClouds = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.TREES)
        {
            ofTrees++;

            if (ofTrees > 2)
            {
                ofTrees = 0;
            }

            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.GRASS)
        {
            ofGrass++;

            if (ofGrass > 2)
            {
                ofGrass = 0;
            }

            RenderBlocks.fancyGrass = Config.isGrassFancy();
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.RAIN)
        {
            ofRain++;

            if (ofRain > 3)
            {
                ofRain = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.WATER)
        {
            ofWater++;

            if (ofWater > 2)
            {
                ofWater = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_WATER)
        {
            ofAnimatedWater++;

            if (ofAnimatedWater > 2)
            {
                ofAnimatedWater = 0;
            }

            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_LAVA)
        {
            ofAnimatedLava++;

            if (ofAnimatedLava > 2)
            {
                ofAnimatedLava = 0;
            }

            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FIRE)
        {
            ofAnimatedFire = !ofAnimatedFire;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL)
        {
            ofAnimatedPortal = !ofAnimatedPortal;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE)
        {
            ofAnimatedRedstone = !ofAnimatedRedstone;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION)
        {
            ofAnimatedExplosion = !ofAnimatedExplosion;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FLAME)
        {
            ofAnimatedFlame = !ofAnimatedFlame;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE)
        {
            ofAnimatedSmoke = !ofAnimatedSmoke;
        }

        if (par1EnumOptions == EnumOptions.VOID_PARTICLES)
        {
            ofVoidParticles = !ofVoidParticles;
        }

        if (par1EnumOptions == EnumOptions.WATER_PARTICLES)
        {
            ofWaterParticles = !ofWaterParticles;
        }

        if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES)
        {
            ofPortalParticles = !ofPortalParticles;
        }

        if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA)
        {
            ofDrippingWaterLava = !ofDrippingWaterLava;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN)
        {
            ofAnimatedTerrain = !ofAnimatedTerrain;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES)
        {
            ofAnimatedTextures = !ofAnimatedTextures;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS)
        {
            ofAnimatedItems = !ofAnimatedItems;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.RAIN_SPLASH)
        {
            ofRainSplash = !ofRainSplash;
        }

        if (par1EnumOptions == EnumOptions.FAST_DEBUG_INFO)
        {
            ofFastDebugInfo = !ofFastDebugInfo;
        }

        if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS)
        {
            ofAutoSaveTicks *= 10;

            if (ofAutoSaveTicks > 40000)
            {
                ofAutoSaveTicks = 40;
            }
        }

        if (par1EnumOptions == EnumOptions.BETTER_GRASS)
        {
            ofBetterGrass++;

            if (ofBetterGrass > 3)
            {
                ofBetterGrass = 1;
            }

            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES)
        {
            ofConnectedTextures++;

            if (ofConnectedTextures > 3)
            {
                ofConnectedTextures = 1;
            }

            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.WEATHER)
        {
            ofWeather = !ofWeather;
        }

        if (par1EnumOptions == EnumOptions.SKY)
        {
            ofSky = !ofSky;
        }

        if (par1EnumOptions == EnumOptions.STARS)
        {
            ofStars = !ofStars;
        }

        if (par1EnumOptions == EnumOptions.SUN_MOON)
        {
            ofSunMoon = !ofSunMoon;
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES)
        {
            ofChunkUpdates++;

            if (ofChunkUpdates > 5)
            {
                ofChunkUpdates = 1;
            }
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC)
        {
            ofChunkUpdatesDynamic = !ofChunkUpdatesDynamic;
        }

        if (par1EnumOptions == EnumOptions.TIME)
        {
            ofTime++;

            if (ofTime > 3)
            {
                ofTime = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.CLEAR_WATER)
        {
            ofClearWater = !ofClearWater;
            updateWaterOpacity();
        }

        if (par1EnumOptions == EnumOptions.DEPTH_FOG)
        {
            ofDepthFog = !ofDepthFog;
        }

        if (par1EnumOptions == EnumOptions.PROFILER)
        {
            ofProfiler = !ofProfiler;
        }

        if (par1EnumOptions == EnumOptions.BETTER_SNOW)
        {
            ofBetterSnow = !ofBetterSnow;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SWAMP_COLORS)
        {
            ofSwampColors = !ofSwampColors;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.RANDOM_MOBS)
        {
            ofRandomMobs = !ofRandomMobs;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_BIOMES)
        {
            ofSmoothBiomes = !ofSmoothBiomes;
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_FONTS)
        {
            ofCustomFonts = !ofCustomFonts;
            mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_COLORS)
        {
            ofCustomColors = !ofCustomColors;
            mc.renderEngine.refreshTextures();
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SHOW_CAPES)
        {
            ofShowCapes = !ofShowCapes;
            mc.renderGlobal.updateCapes();
        }

        if (par1EnumOptions == EnumOptions.NATURAL_TEXTURES)
        {
            ofNaturalTextures = !ofNaturalTextures;
            mc.renderEngine.refreshTextures();
            mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.FULLSCREEN_MODE)
        {
            List list = Arrays.asList(Config.getFullscreenModes());

            if (ofFullscreenMode.equals("Default"))
            {
                ofFullscreenMode = (String)list.get(0);
            }
            else
            {
                int i = list.indexOf(ofFullscreenMode);

                if (i < 0)
                {
                    ofFullscreenMode = "Default";
                }
                else if (++i >= list.size())
                {
                    ofFullscreenMode = "Default";
                }
                else
                {
                    ofFullscreenMode = (String)list.get(i);
                }
            }
        }

        saveOptions();
    }

    public float getOptionFloatValue(EnumOptions par1EnumOptions)
    {
    	if(par1EnumOptions == EnumOptions.ENTITY_DISTANCE){
    		return (entityDistance - 8) / 56f;
    	}
    	
    	if(par1EnumOptions == EnumOptions.RANDOM_UPDATES){
    		return (randomUpdateDistance - 0) / 8f;
    	}
    	
        if (par1EnumOptions == EnumOptions.FOV)
        {
            return fovSetting;
        }

        if (par1EnumOptions == EnumOptions.GAMMA)
        {
            return gammaSetting;
        }

        if (par1EnumOptions == EnumOptions.MUSIC)
        {
            return musicVolume;
        }

        if (par1EnumOptions == EnumOptions.SOUND)
        {
            return soundVolume;
        }

        if (par1EnumOptions == EnumOptions.SENSITIVITY)
        {
            return mouseSensitivity;
        }

        if (par1EnumOptions == EnumOptions.CLOUD_HEIGHT)
        {
            return ofCloudsHeight;
        }

        if (par1EnumOptions == EnumOptions.AO_LEVEL)
        {
            return ofAoLevel;
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE)
        {
            return (float)(ofRenderDistanceFine - 32) / 480F;
        }
        else
        {
            return 0.0F;
        }
    }

    public boolean getOptionOrdinalValue(EnumOptions par1EnumOptions)
    {
        switch (EnumOptionsMappingHelper.enumOptionsMappingHelperArray[par1EnumOptions.ordinal()])
        {
            case 1:
                return invertMouse;

            case 2:
                return viewBobbing;

            case 3:
                return anaglyph;

            case 4:
                return advancedOpengl;

            case 5:
                return ambientOcclusion;

            case 6:
                return clouds;
        }

        return false;
    }

    private static String func_48571_a(String par0ArrayOfStr[], int par1)
    {
        if (par1 < 0 || par1 >= par0ArrayOfStr.length)
        {
            par1 = 0;
        }

        StringTranslate stringtranslate = StringTranslate.getInstance();
        return stringtranslate.translateKey(par0ArrayOfStr[par1]);
    }

    /**
     * Gets a key binding.
     */
    public String getKeyBinding(EnumOptions par1EnumOptions)
    {

        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = stringtranslate.translateKey(par1EnumOptions.getEnumString());

        if (s == null)
        {
            s = par1EnumOptions.getEnumString();
        }

        String s1 = (new StringBuilder()).append(s).append(": ").toString();
        
    	if(par1EnumOptions == EnumOptions.PRELOAD_TEXTURES){
            if (preloadTextures)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
    	}
    	
    	if(par1EnumOptions == EnumOptions.ALLOW_OCCLUSION){
            if (allowOcc)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
    	}
    	
    	if(par1EnumOptions == EnumOptions.ENTITY_DISTANCE){
        	return (new StringBuilder()).append(s1).append(entityDistance).toString();
    	}
    	
    	if(par1EnumOptions == EnumOptions.RANDOM_UPDATES){
    		if(randomUpdateDistance == 0){
    			return (new StringBuilder()).append(s1).append("none").toString();
    		}
    		return (new StringBuilder()).append(s1).append(randomUpdateDistance * 16).toString();
    	}
    	
        if (par1EnumOptions.getEnumFloat())
        {
            float f = getOptionFloatValue(par1EnumOptions);

            if (par1EnumOptions == EnumOptions.SENSITIVITY)
            {
                if (f == 0.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.sensitivity.min")).toString();
                }

                if (f == 1.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.sensitivity.max")).toString();
                }
                else
                {
                    return (new StringBuilder()).append(s1).append((int)(f * 200F)).append("%").toString();
                }
            }

            if (par1EnumOptions == EnumOptions.FOV)
            {
                if (f == 0.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.fov.min")).toString();
                }

                if (f == 1.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.fov.max")).toString();
                }
                else
                {
                    return (new StringBuilder()).append(s1).append((int)(70F + f * 40F)).toString();
                }
            }

            if (par1EnumOptions == EnumOptions.GAMMA)
            {
                if (f == 0.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.gamma.min")).toString();
                }

                if (f == 1.0F)
                {
                    return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.gamma.max")).toString();
                }
                else
                {
                    return (new StringBuilder()).append(s1).append("+").append((int)(f * 100F)).append("%").toString();
                }
            }

            if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE)
            {
                String s2 = "Tiny";
                char c = ' ';

                if (ofRenderDistanceFine >= 64)
                {
                    s2 = "Short";
                    c = '@';
                }

                if (ofRenderDistanceFine >= 128)
                {
                    s2 = "Normal";
                    c = '\200';
                }

                if (ofRenderDistanceFine >= 256)
                {
                    s2 = "Far";
                    c = 256;
                }

                if (ofRenderDistanceFine >= 512)
                {
                    s2 = "Extreme";
                    c = 512;
                }

                int i = ofRenderDistanceFine - c;

                if (i == 0)
                {
                    return (new StringBuilder()).append(s1).append(s2).toString();
                }
                else
                {
                    return (new StringBuilder()).append(s1).append(s2).append(" +").append(i).toString();
                }
            }

            if (f == 0.0F)
            {
                return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.off")).toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append((int)(f * 100F)).append("%").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL)
        {
            if (!advancedOpengl)
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            if (ofOcclusionFancy)
            {
                return (new StringBuilder()).append(s1).append("Fancy").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("Fast").toString();
            }
        }

        if (par1EnumOptions.getEnumBoolean())
        {
            boolean flag = getOptionOrdinalValue(par1EnumOptions);

            if (flag)
            {
                return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.on")).toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.off")).toString();
            }
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE)
        {
            return (new StringBuilder()).append(s1).append(func_48571_a(RENDER_DISTANCES, renderDistance)).toString();
        }

        if (par1EnumOptions == EnumOptions.DIFFICULTY)
        {
            return (new StringBuilder()).append(s1).append(func_48571_a(DIFFICULTIES, difficulty)).toString();
        }

        if (par1EnumOptions == EnumOptions.GUI_SCALE)
        {
            return (new StringBuilder()).append(s1).append(func_48571_a(GUISCALES, guiScale)).toString();
        }

        if (par1EnumOptions == EnumOptions.PARTICLES)
        {
            return (new StringBuilder()).append(s1).append(func_48571_a(PARTICLES, particleSetting)).toString();
        }

        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT)
        {
            if (limitFramerate == 3)
            {
                return (new StringBuilder()).append(s1).append("VSync").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append(func_48571_a(LIMIT_FRAMERATES, limitFramerate)).toString();
            }
        }

        if (par1EnumOptions == EnumOptions.FOG_FANCY)
        {
            switch (ofFogType)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();

                case 3:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("OFF").toString();
        }

        if (par1EnumOptions == EnumOptions.FOG_START)
        {
            return (new StringBuilder()).append(s1).append(ofFogStart).toString();
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL)
        {
            if (ofMipmapLevel == 0)
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            if (ofMipmapLevel == 4)
            {
                return (new StringBuilder()).append(s1).append("Max").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append(ofMipmapLevel).toString();
            }
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_TYPE)
        {
            if (ofMipmapLinear)
            {
                return (new StringBuilder()).append(s1).append("Linear").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("Nearest").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.LOAD_FAR)
        {
            if (ofLoadFar)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS)
        {
            if (ofPreloadedChunks == 0)
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append(ofPreloadedChunks).toString();
            }
        }
        
        if (par1EnumOptions == EnumOptions.SMOOTH_FPS)
        {
            if (ofSmoothFps)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_INPUT)
        {
            if (ofSmoothInput)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.CLOUDS)
        {
            switch (ofClouds)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();

                case 3:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("Default").toString();
        }

        if (par1EnumOptions == EnumOptions.TREES)
        {
            switch (ofTrees)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();
            }

            return (new StringBuilder()).append(s1).append("Default").toString();
        }

        if (par1EnumOptions == EnumOptions.GRASS)
        {
            switch (ofGrass)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();
            }

            return (new StringBuilder()).append(s1).append("Default").toString();
        }

        if (par1EnumOptions == EnumOptions.RAIN)
        {
            switch (ofRain)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();

                case 3:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("Default").toString();
        }

        if (par1EnumOptions == EnumOptions.WATER)
        {
            switch (ofWater)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();

                case 3:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("Default").toString();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_WATER)
        {
            switch (ofAnimatedWater)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Dynamic").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("ON").toString();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_LAVA)
        {
            switch (ofAnimatedLava)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Dynamic").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("OFF").toString();
            }

            return (new StringBuilder()).append(s1).append("ON").toString();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FIRE)
        {
            if (ofAnimatedFire)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL)
        {
            if (ofAnimatedPortal)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE)
        {
            if (ofAnimatedRedstone)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION)
        {
            if (ofAnimatedExplosion)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FLAME)
        {
            if (ofAnimatedFlame)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE)
        {
            if (ofAnimatedSmoke)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.VOID_PARTICLES)
        {
            if (ofVoidParticles)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.WATER_PARTICLES)
        {
            if (ofWaterParticles)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES)
        {
            if (ofPortalParticles)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA)
        {
            if (ofDrippingWaterLava)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN)
        {
            if (ofAnimatedTerrain)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES)
        {
            if (ofAnimatedTextures)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS)
        {
            if (ofAnimatedItems)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.RAIN_SPLASH)
        {
            if (ofRainSplash)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.FAST_DEBUG_INFO)
        {
            if (ofFastDebugInfo)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS)
        {
            if (ofAutoSaveTicks <= 40)
            {
                return (new StringBuilder()).append(s1).append("Default (2s)").toString();
            }

            if (ofAutoSaveTicks <= 400)
            {
                return (new StringBuilder()).append(s1).append("20s").toString();
            }

            if (ofAutoSaveTicks <= 4000)
            {
                return (new StringBuilder()).append(s1).append("3min").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("30min").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.BETTER_GRASS)
        {
            switch (ofBetterGrass)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();
            }

            return (new StringBuilder()).append(s1).append("OFF").toString();
        }

        if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES)
        {
            switch (ofConnectedTextures)
            {
                case 1:
                    return (new StringBuilder()).append(s1).append("Fast").toString();

                case 2:
                    return (new StringBuilder()).append(s1).append("Fancy").toString();
            }

            return (new StringBuilder()).append(s1).append("OFF").toString();
        }

        if (par1EnumOptions == EnumOptions.WEATHER)
        {
            if (ofWeather)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SKY)
        {
            if (ofSky)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.STARS)
        {
            if (ofStars)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SUN_MOON)
        {
            if (ofSunMoon)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES)
        {
            return (new StringBuilder()).append(s1).append(ofChunkUpdates).toString();
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC)
        {
            if (ofChunkUpdatesDynamic)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.TIME)
        {
            if (ofTime == 1)
            {
                return (new StringBuilder()).append(s1).append("Day Only").toString();
            }

            if (ofTime == 3)
            {
                return (new StringBuilder()).append(s1).append("Night Only").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("Default").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.CLEAR_WATER)
        {
            if (ofClearWater)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.DEPTH_FOG)
        {
            if (ofDepthFog)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.PROFILER)
        {
            if (ofProfiler)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.BETTER_SNOW)
        {
            if (ofBetterSnow)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SWAMP_COLORS)
        {
            if (ofSwampColors)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.RANDOM_MOBS)
        {
            if (ofRandomMobs)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_BIOMES)
        {
            if (ofSmoothBiomes)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_FONTS)
        {
            if (ofCustomFonts)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_COLORS)
        {
            if (ofCustomColors)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.SHOW_CAPES)
        {
            if (ofShowCapes)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.NATURAL_TEXTURES)
        {
            if (ofNaturalTextures)
            {
                return (new StringBuilder()).append(s1).append("ON").toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append("OFF").toString();
            }
        }

        if (par1EnumOptions == EnumOptions.FULLSCREEN_MODE)
        {
            return (new StringBuilder()).append(s1).append(ofFullscreenMode).toString();
        }

        if (par1EnumOptions == EnumOptions.GRAPHICS)
        {
            if (fancyGraphics)
            {
                return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.graphics.fancy")).toString();
            }
            else
            {
                return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("options.graphics.fast")).toString();
            }
        }
        else
        {
            return s1;
        }
    }

    /**
     * Loads the options from the options file. It appears that this has replaced the previous 'loadOptions'
     */
    public void loadOptions()
    {
        try
        {
            if (!optionsFile.exists())
            {
                return;
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                try
                {
                    String as[] = s.split(":");
                    
                    if (as[0].equals("preloadTextures"))
                    {
                    	preloadTextures = as[1].equals("true");
                    }
                    
                    if(as[0].equals("entityDistance"))
                    {
                    	entityDistance = Integer.parseInt(as[1]);
                    }
                    
                    if(as[0].equals("skipUpdates")){
                    	randomUpdateDistance = Integer.parseInt(as[1]);
                    }
                    if(as[0].equals("allowOcc")){
                    	allowOcc = as[1].equals("true"); 
                    }
                    
                    if (as[0].equals("music"))
                    {
                        musicVolume = parseFloat(as[1]);
                    }
                    if (as[0].equals("chunkTime")){
                    	avgChunkTime = parseFloat(as[1]);
                    }
                    if (as[0].equals("sound"))
                    {
                        soundVolume = parseFloat(as[1]);
                    }

                    if (as[0].equals("mouseSensitivity"))
                    {
                        mouseSensitivity = parseFloat(as[1]);
                    }

                    if (as[0].equals("fov"))
                    {
                        fovSetting = parseFloat(as[1]);
                    }

                    if (as[0].equals("gamma"))
                    {
                        gammaSetting = parseFloat(as[1]);
                    }

                    if (as[0].equals("invertYMouse"))
                    {
                        invertMouse = as[1].equals("true");
                    }

                    if (as[0].equals("viewDistance"))
                    {
                        renderDistance = Integer.parseInt(as[1]);
                        ofRenderDistanceFine = 32 << 3 - renderDistance;
                    }

                    if (as[0].equals("guiScale"))
                    {
                        guiScale = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("particles"))
                    {
                        particleSetting = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("bobView"))
                    {
                        viewBobbing = as[1].equals("true");
                    }

                    if (as[0].equals("anaglyph3d"))
                    {
                        anaglyph = as[1].equals("true");
                    }

                    if (as[0].equals("advancedOpengl"))
                    {
                        advancedOpengl = as[1].equals("true");
                    }

                    if (as[0].equals("fpsLimit"))
                    {
                        limitFramerate = Integer.parseInt(as[1]);
                        Display.setVSyncEnabled(limitFramerate == 3);
                    }

                    if (as[0].equals("difficulty"))
                    {
                        difficulty = Integer.parseInt(as[1]);
                    }

                    if (as[0].equals("fancyGraphics"))
                    {
                        fancyGraphics = as[1].equals("true");
                    }

                    if (as[0].equals("ao"))
                    {
                        ambientOcclusion = as[1].equals("true");

                        if (ambientOcclusion)
                        {
                            ofAoLevel = 1.0F;
                        }
                        else
                        {
                            ofAoLevel = 0.0F;
                        }
                    }

                    if (as[0].equals("clouds"))
                    {
                        clouds = as[1].equals("true");
                    }

                    if (as[0].equals("skin"))
                    {
                        skin = as[1];
                    }

                    if (as[0].equals("lastServer") && as.length >= 2)
                    {
                        lastServer = as[1];
                    }

                    if (as[0].equals("lang") && as.length >= 2)
                    {
                        language = as[1];
                    }

                    int i = 0;

                    while (i < keyBindings.length)
                    {
                        if (as[0].equals((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).toString()))
                        {
                            keyBindings[i].keyCode = Integer.parseInt(as[1]);
                        }

                        i++;
                    }
                }
                catch (Exception exception2)
                {
                    System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s).toString());
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        }
        catch (Exception exception)
        {
            System.out.println("Failed to load options");
            exception.printStackTrace();
        }

        try
        {
            File file = optionsFileOF;

            if (!file.exists())
            {
                file = optionsFile;
            }

            if (!file.exists())
            {
                return;
            }

            BufferedReader bufferedreader1 = new BufferedReader(new FileReader(file));
            String s1 = "";

            do
            {
                String s2;

                if ((s2 = bufferedreader1.readLine()) == null)
                {
                    break;
                }

                try
                {
                    String as1[] = s2.split(":");

                    if (as1[0].equals("ofRenderDistanceFine") && as1.length >= 2)
                    {
                        ofRenderDistanceFine = Integer.valueOf(as1[1]).intValue();
                        ofRenderDistanceFine = Config.limit(ofRenderDistanceFine, 32, 512);
                    }

                    if (as1[0].equals("ofFogType") && as1.length >= 2)
                    {
                        ofFogType = Integer.valueOf(as1[1]).intValue();
                        ofFogType = Config.limit(ofFogType, 1, 3);
                    }

                    if (as1[0].equals("ofFogStart") && as1.length >= 2)
                    {
                        ofFogStart = Float.valueOf(as1[1]).floatValue();

                        if (ofFogStart < 0.2F)
                        {
                            ofFogStart = 0.2F;
                        }

                        if (ofFogStart > 0.81F)
                        {
                            ofFogStart = 0.8F;
                        }
                    }

                    if (as1[0].equals("ofMipmapLevel") && as1.length >= 2)
                    {
                        ofMipmapLevel = Integer.valueOf(as1[1]).intValue();

                        if (ofMipmapLevel < 0)
                        {
                            ofMipmapLevel = 0;
                        }

                        if (ofMipmapLevel > 4)
                        {
                            ofMipmapLevel = 4;
                        }
                    }

                    if (as1[0].equals("ofMipmapLinear") && as1.length >= 2)
                    {
                        ofMipmapLinear = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofLoadFar") && as1.length >= 2)
                    {
                        ofLoadFar = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofPreloadedChunks") && as1.length >= 2)
                    {
                        ofPreloadedChunks = Integer.valueOf(as1[1]).intValue();

                        if (ofPreloadedChunks < 0)
                        {
                            ofPreloadedChunks = 0;
                        }

                        if (ofPreloadedChunks > 8)
                        {
                            ofPreloadedChunks = 8;
                        }
                    }

                    if (as1[0].equals("ofOcclusionFancy") && as1.length >= 2)
                    {
                        ofOcclusionFancy = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSmoothFps") && as1.length >= 2)
                    {
                        ofSmoothFps = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSmoothInput") && as1.length >= 2)
                    {
                        ofSmoothInput = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAoLevel") && as1.length >= 2)
                    {
                        ofAoLevel = Float.valueOf(as1[1]).floatValue();
                        ofAoLevel = Config.limit(ofAoLevel, 0.0F, 1.0F);
                        ambientOcclusion = ofAoLevel > 0.0F;
                    }

                    if (as1[0].equals("ofClouds") && as1.length >= 2)
                    {
                        ofClouds = Integer.valueOf(as1[1]).intValue();
                        ofClouds = Config.limit(ofClouds, 0, 3);
                    }

                    if (as1[0].equals("ofCloudsHeight") && as1.length >= 2)
                    {
                        ofCloudsHeight = Float.valueOf(as1[1]).floatValue();
                        ofCloudsHeight = Config.limit(ofCloudsHeight, 0.0F, 1.0F);
                    }

                    if (as1[0].equals("ofTrees") && as1.length >= 2)
                    {
                        ofTrees = Integer.valueOf(as1[1]).intValue();
                        ofTrees = Config.limit(ofTrees, 0, 2);
                    }

                    if (as1[0].equals("ofGrass") && as1.length >= 2)
                    {
                        ofGrass = Integer.valueOf(as1[1]).intValue();
                        ofGrass = Config.limit(ofGrass, 0, 2);
                    }

                    if (as1[0].equals("ofRain") && as1.length >= 2)
                    {
                        ofRain = Integer.valueOf(as1[1]).intValue();
                        ofRain = Config.limit(ofRain, 0, 3);
                    }

                    if (as1[0].equals("ofWater") && as1.length >= 2)
                    {
                        ofWater = Integer.valueOf(as1[1]).intValue();
                        ofWater = Config.limit(ofWater, 0, 3);
                    }

                    if (as1[0].equals("ofAnimatedWater") && as1.length >= 2)
                    {
                        ofAnimatedWater = Integer.valueOf(as1[1]).intValue();
                        ofAnimatedWater = Config.limit(ofAnimatedWater, 0, 2);
                    }

                    if (as1[0].equals("ofAnimatedLava") && as1.length >= 2)
                    {
                        ofAnimatedLava = Integer.valueOf(as1[1]).intValue();
                        ofAnimatedLava = Config.limit(ofAnimatedLava, 0, 2);
                    }

                    if (as1[0].equals("ofAnimatedFire") && as1.length >= 2)
                    {
                        ofAnimatedFire = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedPortal") && as1.length >= 2)
                    {
                        ofAnimatedPortal = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedRedstone") && as1.length >= 2)
                    {
                        ofAnimatedRedstone = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedExplosion") && as1.length >= 2)
                    {
                        ofAnimatedExplosion = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedFlame") && as1.length >= 2)
                    {
                        ofAnimatedFlame = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedSmoke") && as1.length >= 2)
                    {
                        ofAnimatedSmoke = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofVoidParticles") && as1.length >= 2)
                    {
                        ofVoidParticles = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofWaterParticles") && as1.length >= 2)
                    {
                        ofWaterParticles = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofPortalParticles") && as1.length >= 2)
                    {
                        ofPortalParticles = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofDrippingWaterLava") && as1.length >= 2)
                    {
                        ofDrippingWaterLava = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedTerrain") && as1.length >= 2)
                    {
                        ofAnimatedTerrain = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedTextures") && as1.length >= 2)
                    {
                        ofAnimatedTextures = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAnimatedItems") && as1.length >= 2)
                    {
                        ofAnimatedItems = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofRainSplash") && as1.length >= 2)
                    {
                        ofRainSplash = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofFastDebugInfo") && as1.length >= 2)
                    {
                        ofFastDebugInfo = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAutoSaveTicks") && as1.length >= 2)
                    {
                        ofAutoSaveTicks = Integer.valueOf(as1[1]).intValue();
                        ofAutoSaveTicks = Config.limit(ofAutoSaveTicks, 40, 40000);
                    }

                    if (as1[0].equals("ofBetterGrass") && as1.length >= 2)
                    {
                        ofBetterGrass = Integer.valueOf(as1[1]).intValue();
                        ofBetterGrass = Config.limit(ofBetterGrass, 1, 3);
                    }

                    if (as1[0].equals("ofConnectedTextures") && as1.length >= 2)
                    {
                        ofConnectedTextures = Integer.valueOf(as1[1]).intValue();
                        ofConnectedTextures = Config.limit(ofConnectedTextures, 1, 3);
                    }

                    if (as1[0].equals("ofWeather") && as1.length >= 2)
                    {
                        ofWeather = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSky") && as1.length >= 2)
                    {
                        ofSky = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofStars") && as1.length >= 2)
                    {
                        ofStars = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSunMoon") && as1.length >= 2)
                    {
                        ofSunMoon = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofChunkUpdates") && as1.length >= 2)
                    {
                        ofChunkUpdates = Integer.valueOf(as1[1]).intValue();
                        ofChunkUpdates = Config.limit(ofChunkUpdates, 1, 5);
                    }

                    if (as1[0].equals("ofChunkUpdatesDynamic") && as1.length >= 2)
                    {
                        ofChunkUpdatesDynamic = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofTime") && as1.length >= 2)
                    {
                        ofTime = Integer.valueOf(as1[1]).intValue();
                        ofTime = Config.limit(ofTime, 0, 3);
                    }

                    if (as1[0].equals("ofClearWater") && as1.length >= 2)
                    {
                        ofClearWater = Boolean.valueOf(as1[1]).booleanValue();
                        updateWaterOpacity();
                    }

                    if (as1[0].equals("ofDepthFog") && as1.length >= 2)
                    {
                        ofDepthFog = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofAaLevel") && as1.length >= 2)
                    {
                        ofAaLevel = Integer.valueOf(as1[1]).intValue();
                        ofAaLevel = Config.limit(ofAaLevel, 0, 16);
                    }

                    if (as1[0].equals("ofAfLevel") && as1.length >= 2)
                    {
                        ofAfLevel = Integer.valueOf(as1[1]).intValue();
                        ofAfLevel = Config.limit(ofAfLevel, 1, 16);
                    }

                    if (as1[0].equals("ofProfiler") && as1.length >= 2)
                    {
                        ofProfiler = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofBetterSnow") && as1.length >= 2)
                    {
                        ofBetterSnow = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSwampColors") && as1.length >= 2)
                    {
                        ofSwampColors = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofRandomMobs") && as1.length >= 2)
                    {
                        ofRandomMobs = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofSmoothBiomes") && as1.length >= 2)
                    {
                        ofSmoothBiomes = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofCustomFonts") && as1.length >= 2)
                    {
                        ofCustomFonts = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofCustomColors") && as1.length >= 2)
                    {
                        ofCustomColors = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofShowCapes") && as1.length >= 2)
                    {
                        ofShowCapes = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofNaturalTextures") && as1.length >= 2)
                    {
                        ofNaturalTextures = Boolean.valueOf(as1[1]).booleanValue();
                    }

                    if (as1[0].equals("ofFullscreenMode") && as1.length >= 2)
                    {
                        ofFullscreenMode = as1[1];
                    }
                }
                catch (Exception exception3)
                {
                    System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s2).toString());
                }
            }
            while (true);

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader1.close();
        }
        catch (Exception exception1)
        {
            System.out.println("Failed to load options");
            exception1.printStackTrace();
        }
    }

    /**
     * Parses a string into a float.
     */
    private float parseFloat(String par1Str)
    {
        if (par1Str.equals("true"))
        {
            return 1.0F;
        }

        if (par1Str.equals("false"))
        {
            return 0.0F;
        }
        else
        {
            return Float.parseFloat(par1Str);
        }
    }

    /**
     * Saves the options to the options file.
     */
    public void saveOptions()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
            printwriter.println((new StringBuilder()).append("music:").append(musicVolume).toString());
            printwriter.println((new StringBuilder()).append("sound:").append(soundVolume).toString());
            printwriter.println((new StringBuilder()).append("invertYMouse:").append(invertMouse).toString());
            printwriter.println((new StringBuilder()).append("mouseSensitivity:").append(mouseSensitivity).toString());
            printwriter.println((new StringBuilder()).append("fov:").append(fovSetting).toString());
            printwriter.println((new StringBuilder()).append("gamma:").append(gammaSetting).toString());
            printwriter.println((new StringBuilder()).append("viewDistance:").append(renderDistance).toString());
            printwriter.println((new StringBuilder()).append("guiScale:").append(guiScale).toString());
            printwriter.println((new StringBuilder()).append("particles:").append(particleSetting).toString());
            printwriter.println((new StringBuilder()).append("bobView:").append(viewBobbing).toString());
            printwriter.println((new StringBuilder()).append("anaglyph3d:").append(anaglyph).toString());
            printwriter.println((new StringBuilder()).append("advancedOpengl:").append(advancedOpengl).toString());
            printwriter.println((new StringBuilder()).append("fpsLimit:").append(limitFramerate).toString());
            printwriter.println((new StringBuilder()).append("difficulty:").append(difficulty).toString());
            printwriter.println((new StringBuilder()).append("fancyGraphics:").append(fancyGraphics).toString());
            printwriter.println((new StringBuilder()).append("ao:").append(ambientOcclusion).toString());
            printwriter.println((new StringBuilder()).append("clouds:").append(clouds).toString());
            printwriter.println((new StringBuilder()).append("skin:").append(skin).toString());
            printwriter.println((new StringBuilder()).append("lastServer:").append(lastServer).toString());
            printwriter.println((new StringBuilder()).append("lang:").append(language).toString());

            printwriter.println((new StringBuilder()).append("preloadTextures:").append(preloadTextures).toString());
            printwriter.println((new StringBuilder()).append("entityDistance:").append(entityDistance).toString());
            printwriter.println((new StringBuilder()).append("skipUpdates:").append(randomUpdateDistance).toString());
            printwriter.println((new StringBuilder()).append("allowOcc:").append(allowOcc).toString());
            printwriter.println((new StringBuilder()).append("chunkTime:").append(avgChunkTime).toString());
            
            for (int i = 0; i < keyBindings.length; i++)
            {
                printwriter.println((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).append(":").append(keyBindings[i].keyCode).toString());
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            System.out.println("Failed to save options");
            exception.printStackTrace();
        }

        try
        {
            PrintWriter printwriter1 = new PrintWriter(new FileWriter(optionsFileOF));
            printwriter1.println((new StringBuilder()).append("ofRenderDistanceFine:").append(ofRenderDistanceFine).toString());
            printwriter1.println((new StringBuilder()).append("ofFogType:").append(ofFogType).toString());
            printwriter1.println((new StringBuilder()).append("ofFogStart:").append(ofFogStart).toString());
            printwriter1.println((new StringBuilder()).append("ofMipmapLevel:").append(ofMipmapLevel).toString());
            printwriter1.println((new StringBuilder()).append("ofMipmapLinear:").append(ofMipmapLinear).toString());
            printwriter1.println((new StringBuilder()).append("ofLoadFar:").append(ofLoadFar).toString());
            printwriter1.println((new StringBuilder()).append("ofPreloadedChunks:").append(ofPreloadedChunks).toString());
            printwriter1.println((new StringBuilder()).append("ofOcclusionFancy:").append(ofOcclusionFancy).toString());
            printwriter1.println((new StringBuilder()).append("ofSmoothFps:").append(ofSmoothFps).toString());
            printwriter1.println((new StringBuilder()).append("ofSmoothInput:").append(ofSmoothInput).toString());
            printwriter1.println((new StringBuilder()).append("ofAoLevel:").append(ofAoLevel).toString());
            printwriter1.println((new StringBuilder()).append("ofClouds:").append(ofClouds).toString());
            printwriter1.println((new StringBuilder()).append("ofCloudsHeight:").append(ofCloudsHeight).toString());
            printwriter1.println((new StringBuilder()).append("ofTrees:").append(ofTrees).toString());
            printwriter1.println((new StringBuilder()).append("ofGrass:").append(ofGrass).toString());
            printwriter1.println((new StringBuilder()).append("ofRain:").append(ofRain).toString());
            printwriter1.println((new StringBuilder()).append("ofWater:").append(ofWater).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedWater:").append(ofAnimatedWater).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedLava:").append(ofAnimatedLava).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedFire:").append(ofAnimatedFire).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedPortal:").append(ofAnimatedPortal).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedRedstone:").append(ofAnimatedRedstone).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedExplosion:").append(ofAnimatedExplosion).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedFlame:").append(ofAnimatedFlame).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedSmoke:").append(ofAnimatedSmoke).toString());
            printwriter1.println((new StringBuilder()).append("ofVoidParticles:").append(ofVoidParticles).toString());
            printwriter1.println((new StringBuilder()).append("ofWaterParticles:").append(ofWaterParticles).toString());
            printwriter1.println((new StringBuilder()).append("ofPortalParticles:").append(ofPortalParticles).toString());
            printwriter1.println((new StringBuilder()).append("ofDrippingWaterLava:").append(ofDrippingWaterLava).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedTerrain:").append(ofAnimatedTerrain).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedTextures:").append(ofAnimatedTextures).toString());
            printwriter1.println((new StringBuilder()).append("ofAnimatedItems:").append(ofAnimatedItems).toString());
            printwriter1.println((new StringBuilder()).append("ofRainSplash:").append(ofRainSplash).toString());
            printwriter1.println((new StringBuilder()).append("ofFastDebugInfo:").append(ofFastDebugInfo).toString());
            printwriter1.println((new StringBuilder()).append("ofAutoSaveTicks:").append(ofAutoSaveTicks).toString());
            printwriter1.println((new StringBuilder()).append("ofBetterGrass:").append(ofBetterGrass).toString());
            printwriter1.println((new StringBuilder()).append("ofConnectedTextures:").append(ofConnectedTextures).toString());
            printwriter1.println((new StringBuilder()).append("ofWeather:").append(ofWeather).toString());
            printwriter1.println((new StringBuilder()).append("ofSky:").append(ofSky).toString());
            printwriter1.println((new StringBuilder()).append("ofStars:").append(ofStars).toString());
            printwriter1.println((new StringBuilder()).append("ofSunMoon:").append(ofSunMoon).toString());
            printwriter1.println((new StringBuilder()).append("ofChunkUpdates:").append(ofChunkUpdates).toString());
            printwriter1.println((new StringBuilder()).append("ofChunkUpdatesDynamic:").append(ofChunkUpdatesDynamic).toString());
            printwriter1.println((new StringBuilder()).append("ofTime:").append(ofTime).toString());
            printwriter1.println((new StringBuilder()).append("ofClearWater:").append(ofClearWater).toString());
            printwriter1.println((new StringBuilder()).append("ofDepthFog:").append(ofDepthFog).toString());
            printwriter1.println((new StringBuilder()).append("ofAaLevel:").append(ofAaLevel).toString());
            printwriter1.println((new StringBuilder()).append("ofAfLevel:").append(ofAfLevel).toString());
            printwriter1.println((new StringBuilder()).append("ofProfiler:").append(ofProfiler).toString());
            printwriter1.println((new StringBuilder()).append("ofBetterSnow:").append(ofBetterSnow).toString());
            printwriter1.println((new StringBuilder()).append("ofSwampColors:").append(ofSwampColors).toString());
            printwriter1.println((new StringBuilder()).append("ofRandomMobs:").append(ofRandomMobs).toString());
            printwriter1.println((new StringBuilder()).append("ofSmoothBiomes:").append(ofSmoothBiomes).toString());
            printwriter1.println((new StringBuilder()).append("ofCustomFonts:").append(ofCustomFonts).toString());
            printwriter1.println((new StringBuilder()).append("ofCustomColors:").append(ofCustomColors).toString());
            printwriter1.println((new StringBuilder()).append("ofShowCapes:").append(ofShowCapes).toString());
            printwriter1.println((new StringBuilder()).append("ofNaturalTextures:").append(ofNaturalTextures).toString());
            printwriter1.println((new StringBuilder()).append("ofFullscreenMode:").append(ofFullscreenMode).toString());
            printwriter1.close();
        }
        catch (Exception exception1)
        {
            System.out.println("Failed to save options");
            exception1.printStackTrace();
        }
    }

    public void resetSettings()
    {
        renderDistance = 1;
        viewBobbing = true;
        anaglyph = false;
        advancedOpengl = false;
        limitFramerate = 0;
        fancyGraphics = true;
        ambientOcclusion = true;
        clouds = true;
        fovSetting = 0.0F;
        gammaSetting = 0.0F;
        guiScale = 0;
        particleSetting = 0;
        ofRenderDistanceFine = 32 << 3 - renderDistance;
        ofFogType = 1;
        ofFogStart = 0.8F;
        ofMipmapLevel = 0;
        ofMipmapLinear = false;
        ofLoadFar = false;
        ofPreloadedChunks = 0;
        ofOcclusionFancy = false;
        ofSmoothFps = false;
        ofSmoothInput = true;

        if (ambientOcclusion)
        {
            ofAoLevel = 1.0F;
        }
        else
        {
            ofAaLevel = 0;
        }

        ofAaLevel = 0;
        ofAfLevel = 1;
        ofClouds = 0;
        ofCloudsHeight = 0.0F;
        ofTrees = 0;
        ofGrass = 0;
        ofRain = 0;
        ofWater = 0;
        ofBetterGrass = 3;
        ofAutoSaveTicks = 4000;
        ofFastDebugInfo = false;
        ofWeather = true;
        ofSky = true;
        ofStars = true;
        ofSunMoon = true;
        ofChunkUpdates = 1;
        ofChunkUpdatesDynamic = false;
        ofTime = 0;
        ofClearWater = false;
        ofDepthFog = true;
        ofProfiler = false;
        ofBetterSnow = false;
        ofFullscreenMode = "Default";
        ofSwampColors = true;
        ofRandomMobs = true;
        ofSmoothBiomes = true;
        ofCustomFonts = true;
        ofCustomColors = true;
        ofShowCapes = true;
        ofConnectedTextures = 2;
        ofNaturalTextures = false;
        ofAnimatedWater = 0;
        ofAnimatedLava = 0;
        ofAnimatedFire = true;
        ofAnimatedPortal = true;
        ofAnimatedRedstone = true;
        ofAnimatedExplosion = true;
        ofAnimatedFlame = true;
        ofAnimatedSmoke = true;
        ofVoidParticles = true;
        ofWaterParticles = true;
        ofRainSplash = true;
        ofPortalParticles = true;
        ofDrippingWaterLava = true;
        ofAnimatedTerrain = true;
        ofAnimatedItems = true;
        ofAnimatedTextures = true;
        mc.renderGlobal.updateCapes();
        updateWaterOpacity();
        mc.renderGlobal.setAllRenderersVisible();
        mc.renderEngine.refreshTextures();
        mc.renderGlobal.loadRenderers();
        
        entityDistance = 16;
        preloadTextures = true;
        randomUpdateDistance = 2;
        saveOptions();
    }

    /**
     * Should render clouds
     */
    public boolean shouldRenderClouds()
    {
        return ofRenderDistanceFine > 64 && clouds;
    }
}
