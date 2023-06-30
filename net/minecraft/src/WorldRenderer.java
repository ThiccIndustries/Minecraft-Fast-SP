package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.GL11;

public class WorldRenderer
{
    /** Reference to the World object. */
    public World worldObj;
    private int glRenderList;
    public static int chunksUpdated = 0;
    public int posX;
    public int posY;
    public int posZ;

    /** Pos X minus */
    public int posXMinus;

    /** Pos Y minus */
    public int posYMinus;

    /** Pos Z minus */
    public int posZMinus;

    /** Pos X clipped */
    public int posXClip;

    /** Pos Y clipped */
    public int posYClip;

    /** Pos Z clipped */
    public int posZClip;
    public boolean isInFrustum;
    public boolean skipRenderPass[];

    /** Pos X plus */
    public int posXPlus;

    /** Pos Y plus */
    public int posYPlus;

    /** Pos Z plus */
    public int posZPlus;

    /** Boolean for whether this renderer needs to be updated or not */
    public boolean needsUpdate;

    /** Axis aligned bounding box */
    public AxisAlignedBB rendererBoundingBox;

    /** Chunk index */
    public int chunkIndex;

    /** Is this renderer visible according to the occlusion query */
    public boolean isVisible;

    /** Is this renderer waiting on the result of the occlusion query */
    public boolean isWaitingOnOcclusionQuery;

    /** OpenGL occlusion query */
    public int glOcclusionQuery;

    /** Is the chunk lit */
    public boolean isChunkLit;
    private boolean isInitialized;

    /** All the tile entities that have special rendering code for this chunk */
    public List tileEntityRenderers;
    private List tileEntities;

    /** Bytes sent to the GPU */
    private int bytesDrawn;
    public boolean isVisibleFromPosition;
    public double visibleFromX;
    public double visibleFromY;
    public double visibleFromZ;
    private boolean needsBoxUpdate;
    public boolean isInFrustrumFully;
    public static int globalChunkOffsetX = 0;
    public static int globalChunkOffsetZ = 0;

    public WorldRenderer(World par1World, List par2List, int par3, int par4, int par5, int par6)
    {
        isVisibleFromPosition = false;
        needsBoxUpdate = false;
        isInFrustrumFully = false;
        glRenderList = -1;
        isInFrustum = false;
        skipRenderPass = new boolean[2];
        isVisible = true;
        isInitialized = false;
        tileEntityRenderers = new ArrayList();
        worldObj = par1World;
        tileEntities = par2List;
        glRenderList = par6;
        posX = -999;
        setPosition(par3, par4, par5);
        needsUpdate = false;
    }

    /**
     * Sets a new position for the renderer and setting it up so it can be reloaded with the new data for that position
     */
    public void setPosition(int par1, int par2, int par3)
    {
        if (par1 == posX && par2 == posY && par3 == posZ)
        {
            return;
        }
        else
        {
            setDontDraw();
            posX = par1;
            posY = par2;
            posZ = par3;
            posXPlus = par1 + 8;
            posYPlus = par2 + 8;
            posZPlus = par3 + 8;
            posXClip = par1 & 0x3ff;
            posYClip = par2;
            posZClip = par3 & 0x3ff;
            posXMinus = par1 - posXClip;
            posYMinus = par2 - posYClip;
            posZMinus = par3 - posZClip;
            float f = 0.0F;
            rendererBoundingBox = AxisAlignedBB.getBoundingBox((float)par1 - f, (float)par2 - f, (float)par3 - f, (float)(par1 + 16) + f, (float)(par2 + 16) + f, (float)(par3 + 16) + f);
            needsBoxUpdate = true;
            markDirty();
            isVisibleFromPosition = false;
            return;
        }
    }

    private void setupGLTranslation()
    {
        GL11.glTranslatef(posXClip, posYClip, posZClip);
    }

    /**
     * Will update this chunk renderer
     */
    public void updateRenderer()
    {
        if (worldObj == null)
        {
            return;
        }

        if (!needsUpdate)
        {
            return;
        }

        if (needsBoxUpdate)
        {
            float f = 0.0F;
            GL11.glNewList(glRenderList + 2, GL11.GL_COMPILE);
            RenderItem.renderAABB(AxisAlignedBB.getBoundingBoxFromPool((float)posXClip - f, (float)posYClip - f, (float)posZClip - f, (float)(posXClip + 16) + f, (float)(posYClip + 16) + f, (float)(posZClip + 16) + f));
            GL11.glEndList();
            needsBoxUpdate = false;
        }

        isVisible = true;
        isVisibleFromPosition = false;
        needsUpdate = false;
        int i = posX;
        int j = posY;
        int k = posZ;
        int l = posX + 16;
        int i1 = posY + 16;
        int j1 = posZ + 16;

        for (int k1 = 0; k1 < 2; k1++)
        {
            skipRenderPass[k1] = true;
        }

        if (Reflector.hasClass(3))
        {
            Object obj = Reflector.getFieldValue(30);
            Reflector.callVoid(obj, 30, new Object[0]);
            Reflector.callVoid(40, new Object[0]);
        }

        Chunk.isLit = false;
        HashSet hashset = new HashSet();
        hashset.addAll(tileEntityRenderers);
        tileEntityRenderers.clear();
        int l1 = 1;
        ChunkCache chunkcache = new ChunkCache(worldObj, i - l1, j - l1, k - l1, l + l1, i1 + l1, j1 + l1);
        
        if (!chunkcache.func_48452_a())
        {
            chunksUpdated++;
            RenderBlocks renderblocks = new RenderBlocks(chunkcache);
            bytesDrawn = 0;
            Tessellator tessellator = Tessellator.instance;
            boolean flag = Reflector.hasClass(1);
            int i2 = 0;

            do
            {
                if (i2 >= 2)
                {
                    break;
                }

                boolean flag1 = false;
                boolean flag2 = false;
                boolean flag3 = false;

                for (int j2 = j; j2 < i1; j2++)
                {
                    for (int k2 = k; k2 < j1; k2++)
                    {
                        for (int l2 = i; l2 < l; l2++)
                        {
                            int i3 = chunkcache.getBlockId(l2, j2, k2);

                            if (i3 <= 0)
                            {
                                continue;
                            }

                            if (!flag3)
                            {
                                flag3 = true;
                                GL11.glNewList(glRenderList + i2, GL11.GL_COMPILE);
                                tessellator.setRenderingChunk(true);

                                if (flag)
                                {
                                    Reflector.callVoid(13, new Object[]
                                            {
                                                Integer.valueOf(i2)
                                            });
                                }

                                tessellator.startDrawingQuads();
                                tessellator.setTranslation(-globalChunkOffsetX, 0.0D, -globalChunkOffsetZ);
                            }

                            Block block = Block.blocksList[i3];

                            if (i2 == 0 && block.hasTileEntity())
                            {
                                TileEntity tileentity = chunkcache.getBlockTileEntity(l2, j2, k2);

                                if (TileEntityRenderer.instance.hasSpecialRenderer(tileentity))
                                {
                                    tileEntityRenderers.add(tileentity);
                                }
                            }

                            int j3 = block.getRenderBlockPass();
                            boolean flag4 = true;

                            if (j3 != i2)
                            {
                                flag1 = true;
                                flag4 = false;
                            }

                            if (flag)
                            {
                                flag4 = Reflector.callBoolean(11, new Object[]
                                        {
                                            block, Integer.valueOf(i2)
                                        });
                            }

                            if (!flag4)
                            {
                                continue;
                            }

                            if (flag)
                            {
                                Reflector.callVoid(15, new Object[]
                                        {
                                            block, renderblocks
                                        });
                            }

                            flag2 |= renderblocks.renderBlockByRenderType(block, l2, j2, k2);

                            if (flag)
                            {
                                Reflector.callVoid(16, new Object[]
                                        {
                                            block, renderblocks
                                        });
                            }
                        }
                    }
                }

                if (flag3)
                {
                    if (flag)
                    {
                        Reflector.callVoid(14, new Object[]
                                {
                                    Integer.valueOf(i2)
                                });
                    }

                    bytesDrawn += tessellator.draw();
                    GL11.glEndList();
                    tessellator.setRenderingChunk(false);
                    tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                }
                else
                {
                    flag2 = false;
                }

                if (flag2)
                {
                    skipRenderPass[i2] = false;
                }

                if (!flag1)
                {
                    break;
                }

                i2++;
            }
            while (true);
        }
        
        HashSet hashset1 = new HashSet();
        hashset1.addAll(tileEntityRenderers);
        hashset1.removeAll(hashset);
        tileEntities.addAll(hashset1);
        hashset.removeAll(tileEntityRenderers);
        tileEntities.removeAll(hashset);
        isChunkLit = Chunk.isLit;
        isInitialized = true;
    }

    /**
     * Returns the distance of this chunk renderer to the entity without performing the final normalizing square root,
     * for performance reasons.
     */
    public float distanceToEntitySquared(Entity par1Entity)
    {
        float f = (float)(par1Entity.posX - (double)posXPlus);
        float f1 = (float)(par1Entity.posY - (double)posYPlus);
        float f2 = (float)(par1Entity.posZ - (double)posZPlus);
        return f * f + f1 * f1 + f2 * f2;
    }

    /**
     * When called this renderer won't draw anymore until its gets initialized again
     */
    public void setDontDraw()
    {
        for (int i = 0; i < 2; i++)
        {
            skipRenderPass[i] = true;
        }

        isInFrustum = false;
        isInitialized = false;
    }

    public void stopRendering()
    {
        setDontDraw();
        worldObj = null;
    }

    /**
     * Takes in the pass the call list is being requested for. Args: renderPass
     */
    public int getGLCallListForPass(int par1)
    {
        if (!isInFrustum)
        {
            return -1;
        }

        if (!skipRenderPass[par1])
        {
            return glRenderList + par1;
        }
        else
        {
            return -1;
        }
    }

    public void updateInFrustum(ICamera par1ICamera)
    {
        isInFrustum = par1ICamera.isBoundingBoxInFrustum(rendererBoundingBox);

        if (isInFrustum && Config.isOcclusionEnabled() && Config.isOcclusionFancy())
        {
            isInFrustrumFully = par1ICamera.isBoundingBoxInFrustumFully(rendererBoundingBox);
        }
        else
        {
            isInFrustrumFully = false;
        }
    }

    /**
     * Renders the occlusion query GL List
     */
    public void callOcclusionQueryList()
    {
        GL11.glCallList(glRenderList + 2);
    }

    /**
     * Checks if all render passes are to be skipped. Returns false if the renderer is not initialized
     */
    public boolean skipAllRenderPasses()
    {
        if (!isInitialized)
        {
            return false;
        }
        else
        {
            return skipRenderPass[0] && skipRenderPass[1];
        }
    }

    /**
     * Marks the current renderer data as dirty and needing to be updated.
     */
    public void markDirty()
    {
        needsUpdate = true;
    }
}
