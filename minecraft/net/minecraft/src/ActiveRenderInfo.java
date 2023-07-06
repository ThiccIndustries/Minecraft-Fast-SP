package net.minecraft.src;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo
{
    /** The calculated view object X coordinate */
    public static float objectX = 0.0F;

    /** The calculated view object Y coordinate */
    public static float objectY = 0.0F;

    /** The calculated view object Z coordinate */
    public static float objectZ = 0.0F;

    /** The current GL viewport */
    private static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);

    /** The current GL modelview matrix */
    private static FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);

    /** The current GL projection matrix */
    private static FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);

    /** The computed view object coordinates */
    private static FloatBuffer objectCoords = GLAllocation.createDirectFloatBuffer(3);

    /** The X component of the entity's yaw rotation */
    public static float rotationX;

    /** The combined X and Z components of the entity's pitch rotation */
    public static float rotationXZ;

    /** The Z component of the entity's yaw rotation */
    public static float rotationZ;

    /**
     * The Y component (scaled along the Z axis) of the entity's pitch rotation
     */
    public static float rotationYZ;

    /**
     * The Y component (scaled along the X axis) of the entity's pitch rotation
     */
    public static float rotationXY;

    public ActiveRenderInfo()
    {
    }

    /**
     * Updates the current render info and camera location based on entity look angles and 1st/3rd person view mode
     */
    public static void updateRenderInfo(EntityPlayer par0EntityPlayer, boolean par1)
    {
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        float f = (viewport.get(0) + viewport.get(2)) / 2;
        float f1 = (viewport.get(1) + viewport.get(3)) / 2;
        GLU.gluUnProject(f, f1, 0.0F, modelview, projection, viewport, objectCoords);
        objectX = objectCoords.get(0);
        objectY = objectCoords.get(1);
        objectZ = objectCoords.get(2);
        int i = par1 ? 1 : 0;
        float f2 = par0EntityPlayer.rotationPitch;
        float f3 = par0EntityPlayer.rotationYaw;
        rotationX = MathHelper.cos((f3 * (float)Math.PI) / 180F) * (float)(1 - i * 2);
        rotationZ = MathHelper.sin((f3 * (float)Math.PI) / 180F) * (float)(1 - i * 2);
        rotationYZ = -rotationZ * MathHelper.sin((f2 * (float)Math.PI) / 180F) * (float)(1 - i * 2);
        rotationXY = rotationX * MathHelper.sin((f2 * (float)Math.PI) / 180F) * (float)(1 - i * 2);
        rotationXZ = MathHelper.cos((f2 * (float)Math.PI) / 180F);
    }

    /**
     * Returns a vector representing the projection along the given entity's view for the given distance
     */
    public static Vec3D projectViewFromEntity(EntityLiving par0EntityLiving, double par1)
    {
        double d = par0EntityLiving.prevPosX + (par0EntityLiving.posX - par0EntityLiving.prevPosX) * par1;
        double d1 = par0EntityLiving.prevPosY + (par0EntityLiving.posY - par0EntityLiving.prevPosY) * par1 + (double)par0EntityLiving.getEyeHeight();
        double d2 = par0EntityLiving.prevPosZ + (par0EntityLiving.posZ - par0EntityLiving.prevPosZ) * par1;
        double d3 = d + (double)(objectX * 1.0F);
        double d4 = d1 + (double)(objectY * 1.0F);
        double d5 = d2 + (double)(objectZ * 1.0F);
        return Vec3D.createVector(d3, d4, d5);
    }

    /**
     * Returns the block ID at the current camera location (either air or fluid), taking into account the height of
     * fluid blocks
     */
    public static int getBlockIdAtEntityViewpoint(World par0World, EntityLiving par1EntityLiving, float par2)
    {
        Vec3D vec3d = projectViewFromEntity(par1EntityLiving, par2);
        ChunkPosition chunkposition = new ChunkPosition(vec3d);
        int i = par0World.getBlockId(chunkposition.x, chunkposition.y, chunkposition.z);

        if (i != 0 && Block.blocksList[i].blockMaterial.isLiquid())
        {
            float f = BlockFluid.getFluidHeightPercent(par0World.getBlockMetadata(chunkposition.x, chunkposition.y, chunkposition.z)) - 0.1111111F;
            float f1 = (float)(chunkposition.y + 1) - f;

            if (vec3d.yCoord >= (double)f1)
            {
                i = par0World.getBlockId(chunkposition.x, chunkposition.y + 1, chunkposition.z);
            }
        }

        return i;
    }
}
