package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess
{
    public List tileEntities;

    /** A reference to the World object. */
    public World worldObj;

    /** The RenderEngine instance used by RenderGlobal */
    public RenderEngine renderEngine;
    private List worldRenderersToUpdate;
    private WorldRenderer sortedWorldRenderers[];
    private WorldRenderer worldRenderers[];
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;

    /** OpenGL render lists base */
    private int glRenderListBase;

    /** A reference to the Minecraft object. */
    public Minecraft mc;

    /** Global render blocks */
    public RenderBlocks globalRenderBlocks;

    /** OpenGL occlusion query base */
    private IntBuffer glOcclusionQueryBase;

    /** Is occlusion testing enabled */
    private boolean occlusionEnabled;
    private int cloudOffsetX;

    /** The star GL Call list */
    private int starGLCallList;

    /** OpenGL sky list */
    private int glSkyList;

    /** OpenGL sky list 2 */
    private int glSkyList2;

    /** Minimum block X */
    private int minBlockX;

    /** Minimum block Y */
    private int minBlockY;

    /** Minimum block Z */
    private int minBlockZ;

    /** Maximum block X */
    private int maxBlockX;

    /** Maximum block Y */
    private int maxBlockY;

    /** Maximum block Z */
    private int maxBlockZ;
    private int renderDistance;

    /** Render entities startup counter (init value=2) */
    private int renderEntitiesStartupCounter;

    /** Count entities total */
    private int countEntitiesTotal;

    /** Count entities rendered */
    private int countEntitiesRendered;

    /** Count entities hidden */
    private int countEntitiesHidden;
    int dummyBuf50k[];

    /** Occlusion query result */
    IntBuffer occlusionResult;

    /** How many renderers are loaded this frame that try to be rendered */
    private int renderersLoaded;

    /** How many renderers are being clipped by the frustrum this frame */
    private int renderersBeingClipped;

    /** How many renderers are being occluded this frame */
    private int renderersBeingOccluded;

    /** How many renderers are actually being rendered this frame */
    private int renderersBeingRendered;

    /**
     * How many renderers are skipping rendering due to not having a render pass this frame
     */
    private int renderersSkippingRenderPass;

    /** Dummy render int */
    private int dummyRenderInt;

    /** World renderers check index */
    private int worldRenderersCheckIndex;
    private IntBuffer glListBuffer;

    /**
     * Previous x position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortX;

    /**
     * Previous y position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortY;

    /**
     * Previous Z position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortZ;

    /** Damage partial time */
    public float damagePartialTime;

    /**
     * The offset used to determine if a renderer is one of the sixteenth that are being updated this frame
     */
    int frustumCheckOffset;
    double prevReposX;
    double prevReposY;
    double prevReposZ;
    private long lastMovedTime;
    
    public RenderGlobal(Minecraft par1Minecraft, RenderEngine par2RenderEngine)
    {
        lastMovedTime = System.currentTimeMillis();
        tileEntities = new ArrayList();
        worldRenderersToUpdate = new ArrayList();
        occlusionEnabled = false;
        cloudOffsetX = 0;
        renderDistance = -1;
        renderEntitiesStartupCounter = 2;
        dummyBuf50k = new int[50000];
        occlusionResult = GLAllocation.createDirectIntBuffer(64);
        glListBuffer = BufferUtils.createIntBuffer(0x10000);
        prevSortX = -9999D;
        prevSortY = -9999D;
        prevSortZ = -9999D;
        frustumCheckOffset = 0;
        mc = par1Minecraft;
        renderEngine = par2RenderEngine;
        byte byte0 = 65;
        byte byte1 = 16;
        glRenderListBase = GLAllocation.generateDisplayLists(byte0 * byte0 * byte1 * 3);
        occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
        System.out.println("Occlusion supported:" + occlusionEnabled);
        
        if(occlusionEnabled && !GameSettings.active.allowOcc){
        	System.out.println("Occlusion disabled by user");
        	occlusionEnabled = false;
        }
        
        System.out.println("occ: " + occlusionEnabled);
        if (occlusionEnabled)
        {
            occlusionResult.clear();
            glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(byte0 * byte0 * byte1);
            glOcclusionQueryBase.clear();
            glOcclusionQueryBase.position(0);
            glOcclusionQueryBase.limit(byte0 * byte0 * byte1);
            ARBOcclusionQuery.glGenQueriesARB(glOcclusionQueryBase);
        }

        starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(starGLCallList, GL11.GL_COMPILE);
        renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.instance;
        glSkyList = starGLCallList + 1;
        GL11.glNewList(glSkyList, GL11.GL_COMPILE);
        byte byte2 = 64;
        int i = 256 / byte2 + 2;
        float f = 16F;

        for (int j = -byte2 * i; j <= byte2 * i; j += byte2)
        {
            for (int l = -byte2 * i; l <= byte2 * i; l += byte2)
            {
                tessellator.startDrawingQuads();
                tessellator.addVertex(j + 0, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + byte2);
                tessellator.addVertex(j + 0, f, l + byte2);
                tessellator.draw();
            }
        }

        GL11.glEndList();
        glSkyList2 = starGLCallList + 2;
        GL11.glNewList(glSkyList2, GL11.GL_COMPILE);
        f = -16F;
        tessellator.startDrawingQuads();

        for (int k = -byte2 * i; k <= byte2 * i; k += byte2)
        {
            for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2)
            {
                tessellator.addVertex(k + byte2, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + byte2);
                tessellator.addVertex(k + byte2, f, i1 + byte2);
            }
        }

        tessellator.draw();
        GL11.glEndList();
        renderEngine.updateDynamicTextures();
    }
    
    private void renderStars()
    {
        Random random = new Random(10842L);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        for (int i = 0; i < 1500; i++)
        {
            double d = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.25F + random.nextFloat() * 0.25F;
            double d4 = d * d + d1 * d1 + d2 * d2;

            if (d4 >= 1.0D || d4 <= 0.01D)
            {
                continue;
            }

            d4 = 1.0D / Math.sqrt(d4);
            d *= d4;
            d1 *= d4;
            d2 *= d4;
            double d5 = d * 100D;
            double d6 = d1 * 100D;
            double d7 = d2 * 100D;
            double d8 = Math.atan2(d, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2D;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);

            for (int j = 0; j < 4; j++)
            {
                double d17 = 0.0D;
                double d18 = (double)((j & 2) - 1) * d3;
                double d19 = (double)((j + 1 & 2) - 1) * d3;
                double d20 = d17;
                double d21 = d18 * d16 - d19 * d15;
                double d22 = d19 * d16 + d18 * d15;
                double d23 = d22;
                double d24 = d21 * d12 + d20 * d13;
                double d25 = d20 * d12 - d21 * d13;
                double d26 = d25 * d9 - d23 * d10;
                double d27 = d24;
                double d28 = d23 * d9 + d25 * d10;
                tessellator.addVertex(d5 + d26, d6 + d27, d7 + d28);
            }
        }

        tessellator.draw();
    }

    /**
     * Changes the world reference in RenderGlobal
     */
    public void changeWorld(World par1World)
    {
        if (worldObj != null)
        {
            worldObj.removeWorldAccess(this);
        }

        prevSortX = -9999D;
        prevSortY = -9999D;
        prevSortZ = -9999D;
        RenderManager.instance.set(par1World);
        worldObj = par1World;
        globalRenderBlocks = new RenderBlocks(par1World);

        if (par1World != null)
        {
            par1World.addWorldAccess(this);
            loadRenderers();
        }
    }

    /**
     * Loads all the renderers and sets up the basic settings usage
     */
    public void loadRenderers()
    {
        if (worldObj == null)
        {
            return;
        }

        Block.leaves.setGraphicsLevel(Config.isTreesFancy());
        renderDistance = mc.gameSettings.renderDistance;

        if (worldRenderers != null)
        {
            for (int i = 0; i < worldRenderers.length; i++)
            {
                worldRenderers[i].stopRendering();
            }
        }

        int j = 64 << 3 - renderDistance;
        char c = 512;
        j = 2 * mc.gameSettings.ofRenderDistanceFine;

        if (Config.isLoadChunksFar() && j < c)
        {
            j = c;
        }

        j += Config.getPreloadedChunks() * 2 * 16;
        char c1 = 400;

        if (mc.gameSettings.ofRenderDistanceFine > 256)
        {
            c1 = 1024;
        }

        if (j > c1)
        {
            j = c1;
        }

        prevReposX = -9999D;
        prevReposY = -9999D;
        prevReposZ = -9999D;
        renderChunksWide = j / 16 + 1;
        renderChunksTall = mc.gameSettings.verticalRenderDistance ? (j / 16 + 1) : 16;
        renderChunksDeep = j / 16 + 1;
        worldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
        sortedWorldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
        int k = 0;
        int l = 0;
        minBlockX = 0;
        minBlockY = 0;
        minBlockZ = 0;
        maxBlockX = renderChunksWide;
        maxBlockY = renderChunksTall;
        maxBlockZ = renderChunksDeep;

        for (int i1 = 0; i1 < worldRenderersToUpdate.size(); i1++)
        {
            WorldRenderer worldrenderer = (WorldRenderer)worldRenderersToUpdate.get(i1);

            if (worldrenderer != null)
            {
                worldrenderer.needsUpdate = false;
            }
        }

        worldRenderersToUpdate.clear();
        tileEntities.clear();

        for (int j1 = 0; j1 < renderChunksWide; j1++)
        {
            for (int k1 = 0; k1 < renderChunksTall; k1++)
            {
                for (int l1 = 0; l1 < renderChunksDeep; l1++)
                {
                    int i2 = (l1 * renderChunksTall + k1) * renderChunksWide + j1;
                    worldRenderers[i2] = new WorldRenderer(worldObj, tileEntities, j1 * 16, k1 * 16, l1 * 16, glRenderListBase + k);

                    if (occlusionEnabled)
                    {
                        worldRenderers[i2].glOcclusionQuery = glOcclusionQueryBase.get(l);
                    }

                    worldRenderers[i2].isWaitingOnOcclusionQuery = false;
                    worldRenderers[i2].isVisible = true;
                    worldRenderers[i2].isInFrustum = false;
                    worldRenderers[i2].chunkIndex = l++;
                    worldRenderers[i2].markDirty();
                    sortedWorldRenderers[i2] = worldRenderers[i2];
                    worldRenderersToUpdate.add(worldRenderers[i2]);
                    k += 3;
                }
            }
        }

        if (worldObj != null)
        {
            Object obj = mc.renderViewEntity;

            if (obj == null)
            {
                obj = mc.thePlayer;
            }

            if (obj != null)
            {
                markRenderersForNewPosition(MathHelper.floor_double(((Entity)(obj)).posX), MathHelper.floor_double(((Entity)(obj)).posY), MathHelper.floor_double(((Entity)(obj)).posZ));
                Arrays.sort(sortedWorldRenderers, new EntitySorter(((Entity)(obj))));
            }
        }

        renderEntitiesStartupCounter = 2;
    }

    /**
     * Renders all entities within range and within the frustrum. Args: pos, frustrum, partialTickTime
     */
    public void renderEntities(Vec3D par1Vec3D, ICamera par2ICamera, float par3)
    {
        if (renderEntitiesStartupCounter > 0)
        {
            renderEntitiesStartupCounter--;
            return;
        }

        Profiler.startSection("prepare");
        TileEntityRenderer.instance.cacheActiveRenderInfo(worldObj, renderEngine, mc.fontRenderer, mc.renderViewEntity, par3);
        RenderManager.instance.cacheActiveRenderInfo(worldObj, renderEngine, mc.fontRenderer, mc.renderViewEntity, mc.gameSettings, par3);
        TileEntityRenderer.instance.func_40742_a();
        countEntitiesTotal = 0;
        countEntitiesRendered = 0;
        countEntitiesHidden = 0;
        EntityLiving entityliving = mc.renderViewEntity;
        RenderManager.renderPosX = ((Entity)(entityliving)).lastTickPosX + (((Entity)(entityliving)).posX - ((Entity)(entityliving)).lastTickPosX) * (double)par3;
        RenderManager.renderPosY = ((Entity)(entityliving)).lastTickPosY + (((Entity)(entityliving)).posY - ((Entity)(entityliving)).lastTickPosY) * (double)par3;
        RenderManager.renderPosZ = ((Entity)(entityliving)).lastTickPosZ + (((Entity)(entityliving)).posZ - ((Entity)(entityliving)).lastTickPosZ) * (double)par3;
        TileEntityRenderer.staticPlayerX = ((Entity)(entityliving)).lastTickPosX + (((Entity)(entityliving)).posX - ((Entity)(entityliving)).lastTickPosX) * (double)par3;
        TileEntityRenderer.staticPlayerY = ((Entity)(entityliving)).lastTickPosY + (((Entity)(entityliving)).posY - ((Entity)(entityliving)).lastTickPosY) * (double)par3;
        TileEntityRenderer.staticPlayerZ = ((Entity)(entityliving)).lastTickPosZ + (((Entity)(entityliving)).posZ - ((Entity)(entityliving)).lastTickPosZ) * (double)par3;
        mc.entityRenderer.enableLightmap(par3);
        Profiler.endStartSection("global");
        List list = worldObj.getLoadedEntityList();
        countEntitiesTotal = list.size();
        int distance = GameSettings.active.entityDistance;
        
        for (int i = 0; i < worldObj.weatherEffects.size(); i++)
        {
            Entity entity = (Entity)worldObj.weatherEffects.get(i);
            countEntitiesRendered++;

            if (entity.isInRangeSimple(par1Vec3D, distance))
            {
                RenderManager.instance.renderEntity(entity, par3);
            }
        }

        Profiler.endStartSection("entities");

        for (int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            
            if (entity1.isInRangeSimple(par1Vec3D, distance) && (entity1.ignoreFrustumCheck || par2ICamera.isBoundingBoxInFrustum(entity1.boundingBox)) && (entity1 != mc.renderViewEntity || mc.gameSettings.thirdPersonView != 0 || mc.renderViewEntity.isPlayerSleeping()) && worldObj.blockExists(MathHelper.floor_double(entity1.posX), 0, MathHelper.floor_double(entity1.posZ)))
            {
                countEntitiesRendered++;
                RenderManager.instance.renderEntity(entity1, par3);
            }
        }

        Profiler.endStartSection("tileentities");
        RenderHelper.enableStandardItemLighting();

      
        for (int k = 0; k < tileEntities.size(); k++)
        {
        	TileEntity entity2 = (TileEntity)tileEntities.get(k);
        	if(entity2.isInRangeSimple(par1Vec3D, distance))
        		TileEntityRenderer.instance.renderTileEntity(entity2, par3);
        }

        mc.entityRenderer.disableLightmap(par3);
        Profiler.endSection();
    }

    /**
     * Gets the render info for use on the Debug screen
     */
    public String getDebugInfoRenders()
    {
        return (new StringBuilder()).append("C: ").append(renderersBeingRendered).append("/").append(renderersLoaded).append(". F: ").append(renderersBeingClipped).append(", O: ").append(renderersBeingOccluded).append(", E: ").append(renderersSkippingRenderPass).toString();
    }

    /**
     * Gets the entities info for use on the Debug screen
     */
    public String getDebugInfoEntities()
    {
        return (new StringBuilder()).append((new StringBuilder()).append("E: ").append(countEntitiesRendered).append("/").append(countEntitiesTotal).append(". B: ").append(countEntitiesHidden).append(", I: ").append(countEntitiesTotal - countEntitiesHidden - countEntitiesRendered).toString()).append(", ").append(Config.getVersion()).toString();
    }

    /**
     * Goes through all the renderers setting new positions on them and those that have their position changed are
     * adding to be updated
     */
    private void markRenderersForNewPosition(int par1, int par2, int par3)
    {
        par1 -= 8;
        par2 -= 8;
        par3 -= 8;
        minBlockX = 0x7fffffff;
        minBlockY = 0x7fffffff;
        minBlockZ = 0x7fffffff;
        maxBlockX = 0x80000000;
        maxBlockY = 0x80000000;
        maxBlockZ = 0x80000000;
        int i = renderChunksWide * 16;
        int j = i / 2;

        for (int k = 0; k < renderChunksWide; k++)
        {
            int l = k * 16;
            int i1 = (l + j) - par1;

            if (i1 < 0)
            {
                i1 -= i - 1;
            }

            i1 /= i;
            l -= i1 * i;

            if (l < minBlockX)
            {
                minBlockX = l;
            }

            if (l > maxBlockX)
            {
                maxBlockX = l;
            }

            for (int j1 = 0; j1 < renderChunksDeep; j1++)
            {
                int k1 = j1 * 16;
                int l1 = (k1 + j) - par3;

                if (l1 < 0)
                {
                    l1 -= i - 1;
                }

                l1 /= i;
                k1 -= l1 * i;

                if (k1 < minBlockZ)
                {
                    minBlockZ = k1;
                }

                if (k1 > maxBlockZ)
                {
                    maxBlockZ = k1;
                }

                for (int i2 = 0; i2 < renderChunksTall; i2++)
                {
                    int j2 = i2 * 16;
                    
                    if(mc.gameSettings.verticalRenderDistance){
                    	int j3 = (j2 + j) - par2;
                    	if(j3 < 0)
                    	{
                    		j3 -= i - 1;
                    	}
                    	j3 /= i;
                    	j2 -= j3 * i;
                    }
                    
                    if (j2 < minBlockY)
                    {
                        minBlockY = j2;
                    }

                    if (j2 > maxBlockY)
                    {
                        maxBlockY = j2;
                    }

                    WorldRenderer worldrenderer = worldRenderers[(j1 * renderChunksTall + i2) * renderChunksWide + k];
                    boolean flag = worldrenderer.needsUpdate;
                    
                    worldrenderer.setPosition(l, j2, k1);

                    if (!flag && worldrenderer.needsUpdate)
                    {
                        worldRenderersToUpdate.add(worldrenderer);
                    }
                }
            }
        }
    }

    /**
     * Sorts all renderers based on the passed in entity. Args: entityLiving, renderPass, partialTickTime
     */
    public int sortAndRender(EntityLiving par1EntityLiving, int par2, double par3)
    {
        Profiler.startSection("sortchunks");

        if (worldRenderersToUpdate.size() < 10)
        {
            byte byte0 = 10;

            for (int i = 0; i < byte0; i++)
            {
                worldRenderersCheckIndex = (worldRenderersCheckIndex + 1) % worldRenderers.length;
                WorldRenderer worldrenderer = worldRenderers[worldRenderersCheckIndex];

                if (worldrenderer.needsUpdate && !worldRenderersToUpdate.contains(worldrenderer))
                {
                    worldRenderersToUpdate.add(worldrenderer);
                }
            }
        }

        if (mc.gameSettings.renderDistance != renderDistance && !Config.isLoadChunksFar())
        {
            loadRenderers();
        }

        if (par2 == 0)
        {
            renderersLoaded = 0;
            dummyRenderInt = 0;
            renderersBeingClipped = 0;
            renderersBeingOccluded = 0;
            renderersBeingRendered = 0;
            renderersSkippingRenderPass = 0;
        }

        double d = par1EntityLiving.lastTickPosX + (par1EntityLiving.posX - par1EntityLiving.lastTickPosX) * par3;
        double d1 = par1EntityLiving.lastTickPosY + (par1EntityLiving.posY - par1EntityLiving.lastTickPosY) * par3;
        double d2 = par1EntityLiving.lastTickPosZ + (par1EntityLiving.posZ - par1EntityLiving.lastTickPosZ) * par3;
        double d3 = par1EntityLiving.posX - prevSortX;
        double d4 = par1EntityLiving.posY - prevSortY;
        double d5 = par1EntityLiving.posZ - prevSortZ;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;

        if (d6 > 16D)
        {
            prevSortX = par1EntityLiving.posX;
            prevSortY = par1EntityLiving.posY;
            prevSortZ = par1EntityLiving.posZ;
            int j = Config.getPreloadedChunks() * 16;
            double d7 = par1EntityLiving.posX - prevReposX;
            double d8 = par1EntityLiving.posY - prevReposY;
            double d9 = par1EntityLiving.posZ - prevReposZ;
            double d10 = d7 * d7 + d8 * d8 + d9 * d9;

            if (d10 > (double)(j * j) + 16D)
            {
                prevReposX = par1EntityLiving.posX;
                prevReposY = par1EntityLiving.posY;
                prevReposZ = par1EntityLiving.posZ;
                markRenderersForNewPosition(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ));
            }

            Arrays.sort(sortedWorldRenderers, new EntitySorter(par1EntityLiving));
            int j2 = (int)par1EntityLiving.posX;
            int k2 = (int)par1EntityLiving.posZ;
            char c = 2000;

            if (Math.abs(j2 - WorldRenderer.globalChunkOffsetX) > c || Math.abs(k2 - WorldRenderer.globalChunkOffsetZ) > c)
            {
                WorldRenderer.globalChunkOffsetX = j2;
                WorldRenderer.globalChunkOffsetZ = k2;
                loadRenderers();
            }
        }

        RenderHelper.disableStandardItemLighting();

        if (mc.gameSettings.ofSmoothFps && par2 == 0)
        {
            GL11.glFinish();
        }

        int k = 0;
        int l = 0;
        
        //occlusionEnabled = false;
        if (occlusionEnabled && mc.gameSettings.advancedOpengl && !mc.gameSettings.anaglyph && par2 == 0)
        {
            int i1 = 0;
            byte byte1 = 20;
            checkOcclusionQueryResult(i1, byte1, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);

            for (int j1 = i1; j1 < byte1; j1++)
            {
                sortedWorldRenderers[j1].isVisible = true;
            }

            Profiler.endStartSection("render");
            //TODO: weird?
            k += renderSortedRenderers(i1, byte1, par2, par3);
            int k1 = byte1;
            int l1 = 0;
            byte byte2 = 30;
            int i2 = renderChunksWide / 2;

            while (k1 < sortedWorldRenderers.length)
            {
                Profiler.endStartSection("occ");
                int byte3 = k1;

                if (l1 < i2)
                {
                    l1++;
                }
                else
                {
                    l1--;
                }

                k1 = byte3 + l1 * byte2;

                if (k1 <= byte3)
                {
                    k1 = byte3 + 10;
                }

                if (k1 > sortedWorldRenderers.length)
                {
                    k1 = sortedWorldRenderers.length;
                }

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_FOG);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                Profiler.startSection("check");
                checkOcclusionQueryResult(byte3, k1, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
                Profiler.endSection();
                GL11.glPushMatrix();
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;

                for (int l2 = byte3; l2 < k1; l2++)
                {
                    WorldRenderer worldrenderer1 = sortedWorldRenderers[l2];

                    if (worldrenderer1.skipAllRenderPasses())
                    {
                        worldrenderer1.isInFrustum = false;
                        continue;
                    }

                    if (!worldrenderer1.isInFrustum)
                    {
                        continue;
                    }

                    if (Config.isOcclusionFancy() && !worldrenderer1.isInFrustrumFully)
                    {
                        worldrenderer1.isVisible = true;
                        continue;
                    }

                    if (!worldrenderer1.isInFrustum || worldrenderer1.isWaitingOnOcclusionQuery)
                    {
                        continue;
                    }

                    if (worldrenderer1.isVisibleFromPosition)
                    {
                        float f3 = Math.abs((float)(worldrenderer1.visibleFromX - par1EntityLiving.posX));
                        float f5 = Math.abs((float)(worldrenderer1.visibleFromY - par1EntityLiving.posY));
                        float f7 = Math.abs((float)(worldrenderer1.visibleFromZ - par1EntityLiving.posZ));
                        float f9 = f3 + f5 + f7;

                        if ((double)f9 < 10D + (double)l2 / 1000D)
                        {
                            worldrenderer1.isVisible = true;
                            continue;
                        }

                        worldrenderer1.isVisibleFromPosition = false;
                    }

                    float f4 = (float)((double)worldrenderer1.posXMinus - d);
                    float f6 = (float)((double)worldrenderer1.posYMinus - d1);
                    float f8 = (float)((double)worldrenderer1.posZMinus - d2);
                    float f10 = f4 - f;
                    float f11 = f6 - f1;
                    float f12 = f8 - f2;

                    if (f10 != 0.0F || f11 != 0.0F || f12 != 0.0F)
                    {
                        GL11.glTranslatef(f10, f11, f12);
                        f += f10;
                        f1 += f11;
                        f2 += f12;
                    }

                    Profiler.startSection("bb");
                    ARBOcclusionQuery.glBeginQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB, worldrenderer1.glOcclusionQuery);
                    worldrenderer1.callOcclusionQueryList();
                    ARBOcclusionQuery.glEndQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB);
                    Profiler.endSection();
                    worldrenderer1.isWaitingOnOcclusionQuery = true;
                    l++;
                }

                GL11.glPopMatrix();

                if (mc.gameSettings.anaglyph)
                {
                    if (EntityRenderer.anaglyphField == 0)
                    {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else
                    {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else
                {
                    GL11.glColorMask(true, true, true, true);
                }

                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_FOG);
                Profiler.endStartSection("render");
                k += renderSortedRenderers(byte3, k1, par2, par3);
            }
        }
        else
        {
            Profiler.endStartSection("render");
            k += renderSortedRenderers(0, sortedWorldRenderers.length, par2, par3);
        }

        Profiler.endSection();
        return k;
    }

    private void checkOcclusionQueryResult(int i, int j, double d, double d1, double d2)
    {
        for (int k = i; k < j; k++)
        {
            WorldRenderer worldrenderer = sortedWorldRenderers[k];

            if (!worldrenderer.isWaitingOnOcclusionQuery)
            {
                continue;
            }

            occlusionResult.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(worldrenderer.glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_AVAILABLE_ARB, occlusionResult);

            if (occlusionResult.get(0) == 0)
            {
                continue;
            }

            worldrenderer.isWaitingOnOcclusionQuery = false;
            occlusionResult.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(worldrenderer.glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_ARB, occlusionResult);
            boolean flag = worldrenderer.isVisible;
            worldrenderer.isVisible = occlusionResult.get(0) > 0;

            if (flag && worldrenderer.isVisible)
            {
                worldrenderer.isVisibleFromPosition = true;
                worldrenderer.visibleFromX = d;
                worldrenderer.visibleFromY = d1;
                worldrenderer.visibleFromZ = d2;
            }
        }
    }

    /**
     * Renders the sorted renders for the specified render pass. Args: startRenderer, numRenderers, renderPass,
     * partialTickTime
     */
    private int renderSortedRenderers(int par1, int par2, int par3, double par4)
    {
        if (Config.isFogOff())
        {
            GL11.glDisable(GL11.GL_FOG);
        }

        glListBuffer.clear();
        int i = 0;

        for (int j = par1; j < par2; j++)
        {
            WorldRenderer worldrenderer = sortedWorldRenderers[j];

            if (par3 == 0)
            {
                renderersLoaded++;

                if (worldrenderer.skipRenderPass[par3])
                {
                    renderersSkippingRenderPass++;
                }
                else if (!worldrenderer.isInFrustum)
                {
                    renderersBeingClipped++;
                }
                else if (occlusionEnabled && !worldrenderer.isVisible)
                {
                    renderersBeingOccluded++;
                }
                else
                {
                    renderersBeingRendered++;
                }
            }

            if (worldrenderer.skipRenderPass[par3] || !worldrenderer.isInFrustum || occlusionEnabled && !worldrenderer.isVisible)
            {
                continue;
            }

            int k = worldrenderer.getGLCallListForPass(par3);

            if (k >= 0)
            {
                glListBuffer.put(k);
                i++;
            }
        }

        glListBuffer.flip();
        EntityLiving entityliving = mc.renderViewEntity;
        double d = (entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * par4) - (double)WorldRenderer.globalChunkOffsetX;
        double d1 = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * par4;
        double d2 = (entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * par4) - (double)WorldRenderer.globalChunkOffsetZ;
        mc.entityRenderer.enableLightmap(par4);
        GL11.glTranslatef((float)(-d), (float)(-d1), (float)(-d2));
        GL11.glCallLists(glListBuffer);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        mc.entityRenderer.disableLightmap(par4);
        return i;
    }

    /**
     * Render all render lists
     */
    public void renderAllRenderLists(int i, double d)
    {
    }

    public void updateClouds()
    {
        cloudOffsetX++;
    }

    /**
     * Renders the sky with the partial tick time. Args: partialTickTime
     */
    public void renderSky(float par1)
    {
        if (mc.theWorld.worldProvider.worldType == 1)
        {
            if (!Config.isSkyEnabled())
            {
                return;
            }

            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask(false);
            renderEngine.bindTexture(renderEngine.getTexture("/misc/tunnel.png"));
            Tessellator tessellator = Tessellator.instance;

            for (int i = 0; i < 6; i++)
            {
                GL11.glPushMatrix();

                if (i == 1)
                {
                    GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 2)
                {
                    GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 3)
                {
                    GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 4)
                {
                    GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                }

                if (i == 5)
                {
                    GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0x181818);
                tessellator.addVertexWithUV(-100D, -100D, -100D, 0.0D, 0.0D);
                tessellator.addVertexWithUV(-100D, -100D, 100D, 0.0D, 16D);
                tessellator.addVertexWithUV(100D, -100D, 100D, 16D, 16D);
                tessellator.addVertexWithUV(100D, -100D, -100D, 16D, 0.0D);
                tessellator.draw();
                GL11.glPopMatrix();
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            return;
        }

        if (!mc.theWorld.worldProvider.func_48217_e())
        {
            return;
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3D vec3d = worldObj.getSkyColor(mc.renderViewEntity, par1);
        vec3d = CustomColorizer.getSkyColor(vec3d, mc.theWorld, mc.renderViewEntity.posX, mc.renderViewEntity.posY + 1.0D, mc.renderViewEntity.posZ);
        float f = (float)vec3d.xCoord;
        float f1 = (float)vec3d.yCoord;
        float f2 = (float)vec3d.zCoord;

        if (mc.gameSettings.anaglyph)
        {
            float f3 = (f * 30F + f1 * 59F + f2 * 11F) / 100F;
            float f4 = (f * 30F + f1 * 70F) / 100F;
            float f5 = (f * 30F + f2 * 70F) / 100F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        GL11.glColor3f(f, f1, f2);
        Tessellator tessellator1 = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(f, f1, f2);

        if (Config.isSkyEnabled())
        {
            GL11.glCallList(glSkyList);
        }

        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        float af[] = worldObj.worldProvider.calcSunriseSunsetColors(worldObj.getCelestialAngle(par1), par1);

        if (af != null && Config.isSunMoonEnabled())
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glPushMatrix();
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.sin(worldObj.getCelestialAngleRadians(par1)) < 0.0F ? 180F : 0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
            float f6 = af[0];
            float f7 = af[1];
            float f8 = af[2];

            if (mc.gameSettings.anaglyph)
            {
                float f10 = (f6 * 30F + f7 * 59F + f8 * 11F) / 100F;
                float f12 = (f6 * 30F + f7 * 70F) / 100F;
                float f14 = (f6 * 30F + f8 * 70F) / 100F;
                f6 = f10;
                f7 = f12;
                f8 = f14;
            }

            tessellator1.startDrawing(6);
            tessellator1.setColorRGBA_F(f6, f7, f8, af[3]);
            tessellator1.addVertex(0.0D, 100D, 0.0D);
            int j = 16;
            tessellator1.setColorRGBA_F(af[0], af[1], af[2], 0.0F);

            for (int k = 0; k <= j; k++)
            {
                float f15 = ((float)k * (float)Math.PI * 2.0F) / (float)j;
                float f18 = MathHelper.sin(f15);
                float f20 = MathHelper.cos(f15);
                tessellator1.addVertex(f18 * 120F, f20 * 120F, -f20 * 40F * af[3]);
            }

            tessellator1.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glPushMatrix();
        double d = 1.0F - worldObj.getRainStrength(par1);
        float f9 = 0.0F;
        float f11 = 0.0F;
        float f13 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)d);
        GL11.glTranslatef(f9, f11, f13);
        GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(worldObj.getCelestialAngle(par1) * 360F, 1.0F, 0.0F, 0.0F);

        if (Config.isSunMoonEnabled())
        {
            float f16 = 30F;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/terrain/sun.png"));
            tessellator1.startDrawingQuads();
            tessellator1.addVertexWithUV(-f16, 100D, -f16, 0.0D, 0.0D);
            tessellator1.addVertexWithUV(f16, 100D, -f16, 1.0D, 0.0D);
            tessellator1.addVertexWithUV(f16, 100D, f16, 1.0D, 1.0D);
            tessellator1.addVertexWithUV(-f16, 100D, f16, 0.0D, 1.0D);
            tessellator1.draw();
            f16 = 20F;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/terrain/moon_phases.png"));
            int l = worldObj.getMoonPhase(par1);
            int i1 = l % 4;
            int j1 = (l / 4) % 2;
            float f23 = (float)(i1 + 0) / 4F;
            float f25 = (float)(j1 + 0) / 2.0F;
            float f26 = (float)(i1 + 1) / 4F;
            float f27 = (float)(j1 + 1) / 2.0F;
            tessellator1.startDrawingQuads();
            tessellator1.addVertexWithUV(-f16, -100D, f16, f26, f27);
            tessellator1.addVertexWithUV(f16, -100D, f16, f23, f27);
            tessellator1.addVertexWithUV(f16, -100D, -f16, f23, f25);
            tessellator1.addVertexWithUV(-f16, -100D, -f16, f26, f25);
            tessellator1.draw();
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float f17 = (float)((double)worldObj.getStarBrightness(par1) * d);

        if (f17 > 0.0F && Config.isStarsEnabled())
        {
            GL11.glColor4f(f17, f17, f17, f17);
            GL11.glCallList(starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        d = mc.thePlayer.getPosition(par1).yCoord - worldObj.getSeaLevel();

        if (d < 0.0D && Config.isSkyEnabled())
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12F, 0.0F);
            GL11.glCallList(glSkyList2);
            GL11.glPopMatrix();
            float f19 = 1.0F;
            float f21 = -(float)(d + 65D);
            float f22 = -f19;
            float f24 = f21;
            tessellator1.startDrawingQuads();
            tessellator1.setColorRGBA_I(0, 255);
            tessellator1.addVertex(-f19, f24, f19);
            tessellator1.addVertex(f19, f24, f19);
            tessellator1.addVertex(f19, f22, f19);
            tessellator1.addVertex(-f19, f22, f19);
            tessellator1.addVertex(-f19, f22, -f19);
            tessellator1.addVertex(f19, f22, -f19);
            tessellator1.addVertex(f19, f24, -f19);
            tessellator1.addVertex(-f19, f24, -f19);
            tessellator1.addVertex(f19, f22, -f19);
            tessellator1.addVertex(f19, f22, f19);
            tessellator1.addVertex(f19, f24, f19);
            tessellator1.addVertex(f19, f24, -f19);
            tessellator1.addVertex(-f19, f24, -f19);
            tessellator1.addVertex(-f19, f24, f19);
            tessellator1.addVertex(-f19, f22, f19);
            tessellator1.addVertex(-f19, f22, -f19);
            tessellator1.addVertex(-f19, f22, -f19);
            tessellator1.addVertex(-f19, f22, f19);
            tessellator1.addVertex(f19, f22, f19);
            tessellator1.addVertex(f19, f22, -f19);
            tessellator1.draw();
        }

        if (worldObj.worldProvider.isSkyColored())
        {
            GL11.glColor3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f, f1, f2);
        }

        if (mc.gameSettings.ofRenderDistanceFine <= 64)
        {
            GL11.glColor3f(mc.entityRenderer.fogColorRed, mc.entityRenderer.fogColorGreen, mc.entityRenderer.fogColorBlue);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -(float)(d - 16D), 0.0F);

        if (Config.isSkyEnabled())
        {
            GL11.glCallList(glSkyList2);
        }

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
    }

    public void renderClouds(float par1)
    {
        if (!mc.theWorld.worldProvider.func_48217_e())
        {
            return;
        }

        if (mc.gameSettings.ofClouds == 3)
        {
            return;
        }

        if (Config.isCloudsFancy())
        {
            renderCloudsFancy(par1);
            return;
        }

        GL11.glDisable(GL11.GL_CULL_FACE);
        float f = (float)(mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * (double)par1);
        byte byte0 = 32;
        int i = 256 / byte0;
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Vec3D vec3d = worldObj.drawClouds(par1);
        float f1 = (float)vec3d.xCoord;
        float f2 = (float)vec3d.yCoord;
        float f3 = (float)vec3d.zCoord;

        if (mc.gameSettings.anaglyph)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f6 = (f1 * 30F + f2 * 70F) / 100F;
            float f7 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f6;
            f3 = f7;
        }

        float f5 = 0.0004882813F;
        double d = (float)cloudOffsetX + par1;
        double d1 = mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * (double)par1 + d * 0.029999999329447746D;
        double d2 = mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * (double)par1;
        int j = MathHelper.floor_double(d1 / 2048D);
        int k = MathHelper.floor_double(d2 / 2048D);
        d1 -= j * 2048;
        d2 -= k * 2048;
        float f8 = (worldObj.worldProvider.getCloudHeight() - f) + 0.33F;
        f8 += mc.gameSettings.ofCloudsHeight * 128F;
        float f9 = (float)(d1 * (double)f5);
        float f10 = (float)(d2 * (double)f5);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, 0.8F);

        for (int l = -byte0 * i; l < byte0 * i; l += byte0)
        {
            for (int i1 = -byte0 * i; i1 < byte0 * i; i1 += byte0)
            {
                tessellator.addVertexWithUV(l + 0, f8, i1 + byte0, (float)(l + 0) * f5 + f9, (float)(i1 + byte0) * f5 + f10);
                tessellator.addVertexWithUV(l + byte0, f8, i1 + byte0, (float)(l + byte0) * f5 + f9, (float)(i1 + byte0) * f5 + f10);
                tessellator.addVertexWithUV(l + byte0, f8, i1 + 0, (float)(l + byte0) * f5 + f9, (float)(i1 + 0) * f5 + f10);
                tessellator.addVertexWithUV(l + 0, f8, i1 + 0, (float)(l + 0) * f5 + f9, (float)(i1 + 0) * f5 + f10);
            }
        }

        tessellator.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public boolean func_27307_a(double par1, double par3, double d, float f)
    {
        return false;
    }

    /**
     * Renders the 3d fancy clouds
     */
    public void renderCloudsFancy(float par1)
    {
        GL11.glDisable(GL11.GL_CULL_FACE);
        float f = (float)(mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * (double)par1);
        Tessellator tessellator = Tessellator.instance;
        float f1 = 12F;
        float f2 = 4F;
        double d = (float)cloudOffsetX + par1;
        double d1 = (mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * (double)par1 + d * 0.029999999329447746D) / (double)f1;
        double d2 = (mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * (double)par1) / (double)f1 + 0.33000001311302185D;
        float f3 = (worldObj.worldProvider.getCloudHeight() - f) + 0.33F;
        f3 += mc.gameSettings.ofCloudsHeight * 128F;
        int i = MathHelper.floor_double(d1 / 2048D);
        int j = MathHelper.floor_double(d2 / 2048D);
        d1 -= i * 2048;
        d2 -= j * 2048;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Vec3D vec3d = worldObj.drawClouds(par1);
        float f4 = (float)vec3d.xCoord;
        float f5 = (float)vec3d.yCoord;
        float f6 = (float)vec3d.zCoord;

        if (mc.gameSettings.anaglyph)
        {
            float f7 = (f4 * 30F + f5 * 59F + f6 * 11F) / 100F;
            float f9 = (f4 * 30F + f5 * 70F) / 100F;
            float f11 = (f4 * 30F + f6 * 70F) / 100F;
            f4 = f7;
            f5 = f9;
            f6 = f11;
        }

        float f8 = (float)(d1 * 0.0D);
        float f10 = (float)(d2 * 0.0D);
        float f12 = 0.00390625F;
        f8 = (float)MathHelper.floor_double(d1) * f12;
        f10 = (float)MathHelper.floor_double(d2) * f12;
        float f13 = (float)(d1 - (double)MathHelper.floor_double(d1));
        float f14 = (float)(d2 - (double)MathHelper.floor_double(d2));
        int k = 8;
        byte byte0 = 4;
        float f15 = 0.0009765625F;
        GL11.glScalef(f1, 1.0F, f1);

        for (int l = 0; l < 2; l++)
        {
            if (l == 0)
            {
                GL11.glColorMask(false, false, false, false);
            }
            else if (mc.gameSettings.anaglyph)
            {
                if (EntityRenderer.anaglyphField == 0)
                {
                    GL11.glColorMask(false, true, true, true);
                }
                else
                {
                    GL11.glColorMask(true, false, false, true);
                }
            }
            else
            {
                GL11.glColorMask(true, true, true, true);
            }

            for (int i1 = -byte0 + 1; i1 <= byte0; i1++)
            {
                for (int j1 = -byte0 + 1; j1 <= byte0; j1++)
                {
                    tessellator.startDrawingQuads();
                    float f16 = i1 * k;
                    float f17 = j1 * k;
                    float f18 = f16 - f13;
                    float f19 = f17 - f14;

                    if (f3 > -f2 - 1.0F)
                    {
                        tessellator.setColorRGBA_F(f4 * 0.7F, f5 * 0.7F, f6 * 0.7F, 0.8F);
                        tessellator.setNormal(0.0F, -1F, 0.0F);
                        tessellator.addVertexWithUV(f18 + 0.0F, f3 + 0.0F, f19 + (float)k, (f16 + 0.0F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + (float)k, f3 + 0.0F, f19 + (float)k, (f16 + (float)k) * f12 + f8, (f17 + (float)k) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + (float)k, f3 + 0.0F, f19 + 0.0F, (f16 + (float)k) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + 0.0F, f3 + 0.0F, f19 + 0.0F, (f16 + 0.0F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                    }

                    if (f3 <= f2 + 1.0F)
                    {
                        tessellator.setColorRGBA_F(f4, f5, f6, 0.8F);
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        tessellator.addVertexWithUV(f18 + 0.0F, (f3 + f2) - f15, f19 + (float)k, (f16 + 0.0F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + (float)k, (f3 + f2) - f15, f19 + (float)k, (f16 + (float)k) * f12 + f8, (f17 + (float)k) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + (float)k, (f3 + f2) - f15, f19 + 0.0F, (f16 + (float)k) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                        tessellator.addVertexWithUV(f18 + 0.0F, (f3 + f2) - f15, f19 + 0.0F, (f16 + 0.0F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                    }

                    tessellator.setColorRGBA_F(f4 * 0.9F, f5 * 0.9F, f6 * 0.9F, 0.8F);

                    if (i1 > -1)
                    {
                        tessellator.setNormal(-1F, 0.0F, 0.0F);

                        for (int k1 = 0; k1 < k; k1++)
                        {
                            tessellator.addVertexWithUV(f18 + (float)k1 + 0.0F, f3 + 0.0F, f19 + (float)k, (f16 + (float)k1 + 0.5F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k1 + 0.0F, f3 + f2, f19 + (float)k, (f16 + (float)k1 + 0.5F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k1 + 0.0F, f3 + f2, f19 + 0.0F, (f16 + (float)k1 + 0.5F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k1 + 0.0F, f3 + 0.0F, f19 + 0.0F, (f16 + (float)k1 + 0.5F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                        }
                    }

                    if (i1 <= 1)
                    {
                        tessellator.setNormal(1.0F, 0.0F, 0.0F);

                        for (int l1 = 0; l1 < k; l1++)
                        {
                            tessellator.addVertexWithUV((f18 + (float)l1 + 1.0F) - f15, f3 + 0.0F, f19 + (float)k, (f16 + (float)l1 + 0.5F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                            tessellator.addVertexWithUV((f18 + (float)l1 + 1.0F) - f15, f3 + f2, f19 + (float)k, (f16 + (float)l1 + 0.5F) * f12 + f8, (f17 + (float)k) * f12 + f10);
                            tessellator.addVertexWithUV((f18 + (float)l1 + 1.0F) - f15, f3 + f2, f19 + 0.0F, (f16 + (float)l1 + 0.5F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                            tessellator.addVertexWithUV((f18 + (float)l1 + 1.0F) - f15, f3 + 0.0F, f19 + 0.0F, (f16 + (float)l1 + 0.5F) * f12 + f8, (f17 + 0.0F) * f12 + f10);
                        }
                    }

                    tessellator.setColorRGBA_F(f4 * 0.8F, f5 * 0.8F, f6 * 0.8F, 0.8F);

                    if (j1 > -1)
                    {
                        tessellator.setNormal(0.0F, 0.0F, -1F);

                        for (int i2 = 0; i2 < k; i2++)
                        {
                            tessellator.addVertexWithUV(f18 + 0.0F, f3 + f2, f19 + (float)i2 + 0.0F, (f16 + 0.0F) * f12 + f8, (f17 + (float)i2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k, f3 + f2, f19 + (float)i2 + 0.0F, (f16 + (float)k) * f12 + f8, (f17 + (float)i2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k, f3 + 0.0F, f19 + (float)i2 + 0.0F, (f16 + (float)k) * f12 + f8, (f17 + (float)i2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + 0.0F, f3 + 0.0F, f19 + (float)i2 + 0.0F, (f16 + 0.0F) * f12 + f8, (f17 + (float)i2 + 0.5F) * f12 + f10);
                        }
                    }

                    if (j1 <= 1)
                    {
                        tessellator.setNormal(0.0F, 0.0F, 1.0F);

                        for (int j2 = 0; j2 < k; j2++)
                        {
                            tessellator.addVertexWithUV(f18 + 0.0F, f3 + f2, (f19 + (float)j2 + 1.0F) - f15, (f16 + 0.0F) * f12 + f8, (f17 + (float)j2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k, f3 + f2, (f19 + (float)j2 + 1.0F) - f15, (f16 + (float)k) * f12 + f8, (f17 + (float)j2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + (float)k, f3 + 0.0F, (f19 + (float)j2 + 1.0F) - f15, (f16 + (float)k) * f12 + f8, (f17 + (float)j2 + 0.5F) * f12 + f10);
                            tessellator.addVertexWithUV(f18 + 0.0F, f3 + 0.0F, (f19 + (float)j2 + 1.0F) - f15, (f16 + 0.0F) * f12 + f8, (f17 + (float)j2 + 0.5F) * f12 + f10);
                        }
                    }

                    tessellator.draw();
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    /**
     * Updates some of the renderers sorted by distance from the player
     */
    public boolean updateRenderers(EntityLiving par1EntityLiving, boolean par2)
    {
        if (worldRenderersToUpdate.size() <= 0)
        {
            return false;
        }
        
        int i = 0;
        int j = Config.getUpdatesPerFrame();

        if (Config.isDynamicUpdates() && !isMoving(par1EntityLiving))
        {
            j *= 3;
        }

        int k = 4;
        int l = 0;
        WorldRenderer worldrenderer = null;
        float f = 3.402823E+038F;
        int i1 = -1;
        
        for (int j1 = 0; j1 < worldRenderersToUpdate.size(); j1++)
        {
            WorldRenderer worldrenderer1 = (WorldRenderer)worldRenderersToUpdate.get(j1);

            if (worldrenderer1 == null)
            {
                continue;
            }

            l++;
            
        	if (!worldrenderer1.needsUpdate)
            {
                worldRenderersToUpdate.set(j1, null);
                continue;
            }

            float f2 = worldrenderer1.distanceToEntitySquared(par1EntityLiving);

            if (f2 <= 256F && isActingNow())
            {
                worldrenderer1.updateRenderer();
                worldrenderer1.needsUpdate = false;
                worldRenderersToUpdate.set(j1, null);
                i++;
                continue;
            }

            if (f2 > 256F && i >= j)
            {
                break;
            }

            if (!worldrenderer1.isInFrustum)
            {
                f2 *= k;
            }

            if (worldrenderer == null)
            {
                worldrenderer = worldrenderer1;
                f = f2;
                i1 = j1;
                continue;
            }

            if (f2 < f)
            {
                worldrenderer = worldrenderer1;
                f = f2;
                i1 = j1;
            }
        }
        
        if (worldrenderer != null)
        {
            worldrenderer.updateRenderer();
            worldrenderer.needsUpdate = false;
            worldRenderersToUpdate.set(i1, null);
            i++;
            float f1 = f / 5F;

            for (int l1 = 0; l1 < worldRenderersToUpdate.size() && i < j; l1++)
            {
                WorldRenderer worldrenderer2 = (WorldRenderer)worldRenderersToUpdate.get(l1);

                if (worldrenderer2 == null)
                {
                    continue;
                }

                float f3 = worldrenderer2.distanceToEntitySquared(par1EntityLiving);

                if (!worldrenderer2.isInFrustum)
                {
                    f3 *= k;
                }

                float f4 = Math.abs(f3 - f);

                if (f4 < f1)
                {
                    worldrenderer2.updateRenderer();
                    worldrenderer2.needsUpdate = false;
                    worldRenderersToUpdate.set(l1, null);
                    i++;
                }
            }
        }

        if (l == 0)
        {
            worldRenderersToUpdate.clear();
        }

        if (worldRenderersToUpdate.size() > 100 && l < (worldRenderersToUpdate.size() * 4) / 5)
        {
            int k1 = 0;

            for (int i2 = 0; i2 < worldRenderersToUpdate.size(); i2++)
            {
                Object obj = worldRenderersToUpdate.get(i2);

                if (obj == null)
                {
                    continue;
                }

                if (i2 != k1)
                {
                    worldRenderersToUpdate.set(k1, obj);
                }

                k1++;
            }

            for (int j2 = worldRenderersToUpdate.size() - 1; j2 >= k1; j2--)
            {
                worldRenderersToUpdate.remove(j2);
            }
        }
        
        return true;
    }

    public void drawBlockBreaking(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, ItemStack par4ItemStack, float par5)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100F) * 0.2F + 0.4F) * 0.5F);

        if (par3 == 0)
        {
            if (damagePartialTime > 0.0F)
            {
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
                int i = renderEngine.getTexture("/terrain.png");
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, i);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                GL11.glPushMatrix();
                int j = worldObj.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                Block block = j > 0 ? Block.blocksList[j] : null;
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glPolygonOffset(-3F, -3F);
                GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                double d = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par5;
                double d1 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par5;
                double d2 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par5;

                if (block == null)
                {
                    block = Block.stone;
                }

                GL11.glEnable(GL11.GL_ALPHA_TEST);
                tessellator.startDrawingQuads();
                tessellator.setTranslation(-d, -d1, -d2);
                tessellator.disableColor();
                globalRenderBlocks.renderBlockUsingTexture(block, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ, 240 + (int)(damagePartialTime * 10F));
                tessellator.draw();
                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glPolygonOffset(0.0F, 0.0F);
                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        }
        else if (par4ItemStack != null)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float f = MathHelper.sin((float)System.currentTimeMillis() / 100F) * 0.2F + 0.8F;
            GL11.glColor4f(f, f, f, MathHelper.sin((float)System.currentTimeMillis() / 200F) * 0.2F + 0.5F);
            int k = renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, k);
            int l = par2MovingObjectPosition.blockX;
            int i1 = par2MovingObjectPosition.blockY;
            int j1 = par2MovingObjectPosition.blockZ;

            if (par2MovingObjectPosition.sideHit == 0)
            {
                i1--;
            }

            if (par2MovingObjectPosition.sideHit == 1)
            {
                i1++;
            }

            if (par2MovingObjectPosition.sideHit == 2)
            {
                j1--;
            }

            if (par2MovingObjectPosition.sideHit == 3)
            {
                j1++;
            }

            if (par2MovingObjectPosition.sideHit == 4)
            {
                l--;
            }

            if (par2MovingObjectPosition.sideHit == 5)
            {
                l++;
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Draws the selection box for the player. Args: entityPlayer, rayTraceHit, i, itemStack, partialTickTime
     */
    public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, ItemStack par4ItemStack, float par5)
    {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            float f = 0.002F;
            int i = worldObj.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);

            if (i > 0)
            {
                Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                double d = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par5;
                double d1 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par5;
                double d2 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par5;
                drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(worldObj, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    /**
     * Draws lines for the edges of the bounding box.
     */
    private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.draw();
    }

    /**
     * Marks the blocks in the given range for update
     */
    public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
    {
    	
    	int i = MathHelper.bucketInt(par1, 16);
        int j = MathHelper.bucketInt(par2, 16);
        int k = MathHelper.bucketInt(par3, 16);
        int l = MathHelper.bucketInt(par4, 16);
        int i1 = MathHelper.bucketInt(par5, 16);
        int j1 = MathHelper.bucketInt(par6, 16);

        for (int k1 = i; k1 <= l; k1++)
        {
            int l1 = k1 % renderChunksWide;

            if (l1 < 0)
            {
                l1 += renderChunksWide;
            }

            for (int i2 = j; i2 <= i1; i2++)
            {
                int j2 = i2 % renderChunksTall;

                if (j2 < 0)
                {
                    j2 += renderChunksTall;
                }

                for (int k2 = k; k2 <= j1; k2++)
                {
                    int l2 = k2 % renderChunksDeep;

                    if (l2 < 0)
                    {
                        l2 += renderChunksDeep;
                    }

                    int i3 = (l2 * renderChunksTall + j2) * renderChunksWide + l1;
                    WorldRenderer worldrenderer = worldRenderers[i3];

                    if (!worldrenderer.needsUpdate)
                    {
                        worldRenderersToUpdate.add(worldrenderer); //Place blocks?
                        worldrenderer.markDirty();
                    }
                }
            }
        }
    }

    /**
     * Will mark the block and neighbors that their renderers need an update (could be all the same renderer
     * potentially) Args: x, y, z
     */
    public void markBlockNeedsUpdate(int par1, int par2, int par3)
    {
        markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }

    /**
     * As of mc 1.2.3 this method has exactly the same signature and does exactly the same as markBlockNeedsUpdate
     */
    public void markBlockNeedsUpdate2(int par1, int par2, int par3)
    {
        markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }

    /**
     * Called across all registered IWorldAccess instances when a block range is invalidated. Args: minX, minY, minZ,
     * maxX, maxY, maxZ
     */
    public void markBlockRangeNeedsUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par4 + 1, par5 + 1, par6 + 1);
    }

    /**
     * Checks all renderers that previously weren't in the frustum and 1/16th of those that previously were in the
     * frustum for frustum clipping Args: frustum, partialTickTime
     */
    public void clipRenderersByFrustum(ICamera par1ICamera, float par2)
    {
        for (int i = 0; i < worldRenderers.length; i++)
        {
            if (!worldRenderers[i].skipAllRenderPasses())
            {
                worldRenderers[i].updateInFrustum(par1ICamera);
            }
        }

        frustumCheckOffset++;
    }

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    public void playRecord(String par1Str, int par2, int par3, int par4)
    {
        if (par1Str != null)
        {
            mc.ingameGUI.setRecordPlayingMessage((new StringBuilder()).append("C418 - ").append(par1Str).toString());
        }

        mc.sndManager.playStreaming(par1Str, par2, par3, par4, 1.0F, 1.0F);
    }

    /**
     * Plays the specified sound. Arg: x, y, z, soundName, unknown1, unknown2
     */
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        float f = 16F;

        if (par8 > 1.0F)
        {
            f *= par8;
        }

        if (mc.renderViewEntity.getDistanceSq(par2, par4, par6) < (double)(f * f))
        {
            mc.sndManager.playSound(par1Str, (float)par2, (float)par4, (float)par6, par8, par9);
        }
    }

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        func_40193_b(par1Str, par2, par4, par6, par8, par10, par12);
    }

    public EntityFX func_40193_b(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        if (mc == null || mc.renderViewEntity == null || mc.effectRenderer == null)
        {
            return null;
        }

        int i = mc.gameSettings.particleSetting;

        if (i == 1 && worldObj.rand.nextInt(3) == 0)
        {
            i = 2;
        }

        double d = mc.renderViewEntity.posX - par2;
        double d1 = mc.renderViewEntity.posY - par4;
        double d2 = mc.renderViewEntity.posZ - par6;
        Object obj = null;
        Item item = null;

        if (par1Str.equals("hugeexplosion"))
        {
            if (Config.isAnimatedExplosion())
            {
                mc.effectRenderer.addEffect((EntityFX)(obj = new EntityHugeExplodeFX(worldObj, par2, par4, par6, par8, par10, par12)));
            }
        }
        else if (par1Str.equals("largeexplode") && Config.isAnimatedExplosion())
        {
            mc.effectRenderer.addEffect((EntityFX)(obj = new EntityLargeExplodeFX(renderEngine, worldObj, par2, par4, par6, par8, par10, par12)));
        }

        if (obj != null)
        {
            return (EntityFX)obj;
        }

        double d3 = 16D;

        if (par1Str.equals("crit"))
        {
            d3 = 196D;
        }

        if (d * d + d1 * d1 + d2 * d2 > d3 * d3)
        {
            return null;
        }

        if (i > 1)
        {
            return null;
        }

        if (par1Str.equals("bubble"))
        {
            obj = new EntityBubbleFX(worldObj, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX((EntityFX)obj, worldObj);
        }
        else if (par1Str.equals("suspended"))
        {
            if (Config.isWaterParticles())
            {
                obj = new EntitySuspendFX(worldObj, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("depthsuspend"))
        {
            if (Config.isVoidParticles())
            {
                obj = new EntityAuraFX(worldObj, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("townaura"))
        {
            obj = new EntityAuraFX(worldObj, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateMyceliumFX((EntityFX)obj);
        }
        else if (par1Str.equals("crit"))
        {
            obj = new EntityCritFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("magicCrit"))
        {
            obj = new EntityCritFX(worldObj, par2, par4, par6, par8, par10, par12);
            ((EntityFX)obj).func_40097_b(((EntityFX)obj).func_40098_n() * 0.3F, ((EntityFX)obj).func_40101_o() * 0.8F, ((EntityFX)obj).func_40102_p());
            ((EntityFX)obj).setParticleTextureIndex(((EntityFX)obj).getParticleTextureIndex() + 1);
        }
        else if (par1Str.equals("smoke"))
        {
            if (Config.isAnimatedSmoke())
            {
                obj = new EntitySmokeFX(worldObj, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("mobSpell"))
        {
            obj = new EntitySpellParticleFX(worldObj, par2, par4, par6, 0.0D, 0.0D, 0.0D);
            ((EntityFX)obj).func_40097_b((float)par8, (float)par10, (float)par12);
        }
        else if (par1Str.equals("spell"))
        {
            obj = new EntitySpellParticleFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("instantSpell"))
        {
            obj = new EntitySpellParticleFX(worldObj, par2, par4, par6, par8, par10, par12);
            ((EntitySpellParticleFX)obj).func_40110_b(144);
        }
        else if (par1Str.equals("note"))
        {
            obj = new EntityNoteFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("portal"))
        {
            if (Config.isPortalParticles())
            {
                obj = new EntityPortalFX(worldObj, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updatePortalFX((EntityFX)obj);
            }
        }
        else if (par1Str.equals("enchantmenttable"))
        {
            obj = new EntityEnchantmentTableParticleFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("explode"))
        {
            if (Config.isAnimatedExplosion())
            {
                obj = new EntityExplodeFX(worldObj, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("flame"))
        {
            if (Config.isAnimatedFlame())
            {
                obj = new EntityFlameFX(worldObj, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("lava"))
        {
            obj = new EntityLavaFX(worldObj, par2, par4, par6);
        }
        else if (par1Str.equals("footstep"))
        {
            obj = new EntityFootStepFX(renderEngine, worldObj, par2, par4, par6);
        }
        else if (par1Str.equals("splash"))
        {
            obj = new EntitySplashFX(worldObj, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX((EntityFX)obj, worldObj);
        }
        else if (par1Str.equals("largesmoke"))
        {
            if (Config.isAnimatedSmoke())
            {
                obj = new EntitySmokeFX(worldObj, par2, par4, par6, par8, par10, par12, 2.5F);
            }
        }
        else if (par1Str.equals("cloud"))
        {
            obj = new EntityCloudFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("reddust"))
        {
            if (Config.isAnimatedRedstone())
            {
                obj = new EntityReddustFX(worldObj, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                CustomColorizer.updateReddustFX((EntityFX)obj, worldObj, d, d1, d2);
            }
        }
        else if (par1Str.equals("snowballpoof"))
        {
            obj = new EntityBreakingFX(worldObj, par2, par4, par6, Item.snowball);
            item = Item.snowball;
        }
        else if (par1Str.equals("dripWater"))
        {
            if (Config.isDrippingWaterLava())
            {
                obj = new EntityDropParticleFX(worldObj, par2, par4, par6, Material.water);
            }
        }
        else if (par1Str.equals("dripLava"))
        {
            if (Config.isDrippingWaterLava())
            {
                obj = new EntityDropParticleFX(worldObj, par2, par4, par6, Material.lava);
            }
        }
        else if (par1Str.equals("snowshovel"))
        {
            obj = new EntitySnowShovelFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("slime"))
        {
            obj = new EntityBreakingFX(worldObj, par2, par4, par6, Item.slimeBall);
            item = Item.slimeBall;
        }
        else if (par1Str.equals("heart"))
        {
            obj = new EntityHeartFX(worldObj, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.startsWith("iconcrack_"))
        {
            int j = Integer.parseInt(par1Str.substring(par1Str.indexOf("_") + 1));
            obj = new EntityBreakingFX(worldObj, par2, par4, par6, par8, par10, par12, Item.itemsList[j]);
            item = Item.itemsList[j];
        }
        else if (par1Str.startsWith("tilecrack_"))
        {
            int k = Integer.parseInt(par1Str.substring(par1Str.indexOf("_") + 1));
            obj = new EntityDiggingFX(worldObj, par2, par4, par6, par8, par10, par12, Block.blocksList[k], 0, 0);
            item = Item.itemsList[k];
        }

        if (obj != null)
        {
            if (Reflector.hasClass(1))
            {
                Reflector.callVoid(mc.effectRenderer, 80, new Object[]
                        {
                            obj, item
                        });
            }
            else
            {
                mc.effectRenderer.addEffect((EntityFX)obj);
            }
        }

        return (EntityFX)obj;
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    public void obtainEntitySkin(Entity par1Entity)
    {
        par1Entity.updateCloak();

        if (par1Entity.skinUrl != null)
        {
            renderEngine.obtainImageData(par1Entity.skinUrl, new ImageBufferDownload());
        }

        if (par1Entity.cloakUrl != null)
        {
            renderEngine.obtainImageData(par1Entity.cloakUrl, new ImageBufferDownload());

            if (par1Entity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)par1Entity;
                ThreadDownloadImageData threaddownloadimagedata = renderEngine.obtainImageData(entityplayer.cloakUrl, new ImageBufferDownload());
                renderEngine.releaseImageData(entityplayer.cloakUrl);

                if (!Config.isShowCapes())
                {
                    entityplayer.playerCloakUrl = "";
                }
            }
        }

        if (Config.isRandomMobs())
        {
            RandomMobs.entityLoaded(par1Entity);
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    public void releaseEntitySkin(Entity par1Entity)
    {
        if (par1Entity.skinUrl != null)
        {
            renderEngine.releaseImageData(par1Entity.skinUrl);
        }

        if (par1Entity.cloakUrl != null)
        {
            renderEngine.releaseImageData(par1Entity.cloakUrl);
        }
    }

    public void setAllRenderersVisible()
    {
        if (worldRenderers == null)
        {
            return;
        }

        for (int i = 0; i < worldRenderers.length; i++)
        {
            worldRenderers[i].isVisible = true;
        }
    }

    /**
     * In all implementations, this method does nothing.
     */
    public void doNothingWithTileEntity(int i, int j, int k, TileEntity tileentity)
    {
    }

    public void func_28137_f()
    {
        GLAllocation.deleteDisplayLists(glRenderListBase);
    }

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        Random random = worldObj.rand;

        switch (par2)
        {
            default:
                break;

            case 1001:
                worldObj.playSoundEffect(par3, par4, par5, "random.click", 1.0F, 1.2F);
                break;

            case 1000:
                worldObj.playSoundEffect(par3, par4, par5, "random.click", 1.0F, 1.0F);
                break;

            case 1002:
                worldObj.playSoundEffect(par3, par4, par5, "random.bow", 1.0F, 1.2F);
                break;

            case 2000:
                int i = par6 % 3 - 1;
                int j = (par6 / 3) % 3 - 1;
                double d = (double)par3 + (double)i * 0.59999999999999998D + 0.5D;
                double d1 = (double)par4 + 0.5D;
                double d2 = (double)par5 + (double)j * 0.59999999999999998D + 0.5D;

                for (int k = 0; k < 10; k++)
                {
                    double d4 = random.nextDouble() * 0.20000000000000001D + 0.01D;
                    double d6 = d + (double)i * 0.01D + (random.nextDouble() - 0.5D) * (double)j * 0.5D;
                    double d8 = d1 + (random.nextDouble() - 0.5D) * 0.5D;
                    double d9 = d2 + (double)j * 0.01D + (random.nextDouble() - 0.5D) * (double)i * 0.5D;
                    double d12 = (double)i * d4 + random.nextGaussian() * 0.01D;
                    double d14 = -0.029999999999999999D + random.nextGaussian() * 0.01D;
                    double d16 = (double)j * d4 + random.nextGaussian() * 0.01D;
                    spawnParticle("smoke", d6, d8, d9, d12, d14, d16);
                }

                break;

            case 2003:
                double d3 = (double)par3 + 0.5D;
                double d5 = par4;
                double d7 = (double)par5 + 0.5D;
                String s = (new StringBuilder()).append("iconcrack_").append(Item.eyeOfEnder.shiftedIndex).toString();

                for (int l = 0; l < 8; l++)
                {
                    spawnParticle(s, d3, d5, d7, random.nextGaussian() * 0.14999999999999999D, random.nextDouble() * 0.20000000000000001D, random.nextGaussian() * 0.14999999999999999D);
                }

                for (double d10 = 0.0D; d10 < (Math.PI * 2D); d10 += 0.15707963267948966D)
                {
                    spawnParticle("portal", d3 + Math.cos(d10) * 5D, d5 - 0.40000000000000002D, d7 + Math.sin(d10) * 5D, Math.cos(d10) * -5D, 0.0D, Math.sin(d10) * -5D);
                    spawnParticle("portal", d3 + Math.cos(d10) * 5D, d5 - 0.40000000000000002D, d7 + Math.sin(d10) * 5D, Math.cos(d10) * -7D, 0.0D, Math.sin(d10) * -7D);
                }

                break;

            case 2002:
                double d11 = par3;
                double d13 = par4;
                double d15 = par5;
                String s1 = (new StringBuilder()).append("iconcrack_").append(Item.potion.shiftedIndex).toString();

                for (int i1 = 0; i1 < 8; i1++)
                {
                    spawnParticle(s1, d11, d13, d15, random.nextGaussian() * 0.14999999999999999D, random.nextDouble() * 0.20000000000000001D, random.nextGaussian() * 0.14999999999999999D);
                }

                int j1 = Item.potion.getColorFromDamage(par6, 0);
                float f = (float)(j1 >> 16 & 0xff) / 255F;
                float f1 = (float)(j1 >> 8 & 0xff) / 255F;
                float f2 = (float)(j1 >> 0 & 0xff) / 255F;
                String s2 = "spell";

                if (Item.potion.isEffectInstant(par6))
                {
                    s2 = "instantSpell";
                }

                for (int k1 = 0; k1 < 100; k1++)
                {
                    double d17 = random.nextDouble() * 4D;
                    double d19 = random.nextDouble() * Math.PI * 2D;
                    double d21 = Math.cos(d19) * d17;
                    double d23 = 0.01D + random.nextDouble() * 0.5D;
                    double d24 = Math.sin(d19) * d17;
                    EntityFX entityfx = func_40193_b(s2, d11 + d21 * 0.10000000000000001D, d13 + 0.29999999999999999D, d15 + d24 * 0.10000000000000001D, d21, d23, d24);

                    if (entityfx != null)
                    {
                        float f3 = 0.75F + random.nextFloat() * 0.25F;
                        entityfx.func_40097_b(f * f3, f1 * f3, f2 * f3);
                        entityfx.multiplyVelocity((float)d17);
                    }
                }

                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.glass", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
                break;

            case 2001:
                int l1 = par6 & 0xfff;

                if (l1 > 0)
                {
                    Block block = Block.blocksList[l1];
                    mc.sndManager.playSound(block.stepSound.getBreakSound(), (float)par3 + 0.5F, (float)par4 + 0.5F, (float)par5 + 0.5F, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                }

                mc.effectRenderer.addBlockDestroyEffects(par3, par4, par5, par6 & 0xfff, par6 >> 12 & 0xff);
                break;

            case 2004:
                for (int i2 = 0; i2 < 20; i2++)
                {
                    double d18 = (double)par3 + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
                    double d20 = (double)par4 + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
                    double d22 = (double)par5 + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
                    worldObj.spawnParticle("smoke", d18, d20, d22, 0.0D, 0.0D, 0.0D);
                    worldObj.spawnParticle("flame", d18, d20, d22, 0.0D, 0.0D, 0.0D);
                }

                break;

            case 1003:
                if (Math.random() < 0.5D)
                {
                    worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_open", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
                else
                {
                    worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_close", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }

                break;

            case 1004:
                worldObj.playSoundEffect((float)par3 + 0.5F, (float)par4 + 0.5F, (float)par5 + 0.5F, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
                break;

            case 1005:
                if (Item.itemsList[par6] instanceof ItemRecord)
                {
                    worldObj.playRecord(((ItemRecord)Item.itemsList[par6]).recordName, par3, par4, par5);
                }
                else
                {
                    worldObj.playRecord(null, par3, par4, par5);
                }

                break;

            case 1007:
                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.charge", 10F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                break;

            case 1008:
                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.fireball", 10F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                break;

            case 1010:
                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.wood", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                break;

            case 1012:
                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.woodbreak", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                break;

            case 1011:
                worldObj.playSoundEffect((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.metal", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                break;
        }
    }

    private boolean isMoving(EntityLiving entityliving)
    {
        boolean flag = isMovingNow(entityliving);

        if (flag)
        {
            lastMovedTime = System.currentTimeMillis();
            return true;
        }
        else
        {
            return System.currentTimeMillis() - lastMovedTime < 2000L;
        }
    }

    private boolean isMovingNow(EntityLiving entityliving)
    {
        double d = 0.001D;

        if (entityliving.isJumping)
        {
            return true;
        }

        if (entityliving.isSneaking())
        {
            return true;
        }

        if ((double)entityliving.prevSwingProgress > d)
        {
            return true;
        }

        if (mc.mouseHelper.deltaX != 0)
        {
            return true;
        }

        if (mc.mouseHelper.deltaY != 0)
        {
            return true;
        }

        if (Math.abs(entityliving.posX - entityliving.prevPosX) > d)
        {
            return true;
        }

        if (Math.abs(entityliving.posY - entityliving.prevPosY) > d)
        {
            return true;
        }

        return Math.abs(entityliving.posZ - entityliving.prevPosZ) > d;
    }

    private boolean isActingNow()
    {
        if (Mouse.isButtonDown(0))
        {
            return true;
        }

        return Mouse.isButtonDown(1);
    }

    public int renderAllSortedRenderers(int i, double d)
    {
        return renderSortedRenderers(0, sortedWorldRenderers.length, i, d);
    }

    public void updateCapes()
    {
        if (worldObj == null)
        {
            return;
        }

        boolean flag = Config.isShowCapes();
        List list = worldObj.playerEntities;

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);

            if (!(entity instanceof EntityPlayer))
            {
                continue;
            }

            EntityPlayer entityplayer = (EntityPlayer)entity;

            if (flag)
            {
                entityplayer.playerCloakUrl = entityplayer.cloakUrl;
            }
            else
            {
                entityplayer.playerCloakUrl = "";
            }
        }
    }
}
