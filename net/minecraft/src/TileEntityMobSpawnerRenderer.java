package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    /**
     * Hash map of the entities that the mob spawner has rendered/rendering spinning inside of them
     */
    private Map entityHashMap;

    public TileEntityMobSpawnerRenderer()
    {
        entityHashMap = new HashMap();
    }

    public void renderTileEntityMobSpawner(TileEntityMobSpawner par1TileEntityMobSpawner, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
        Entity entity = (Entity)entityHashMap.get(par1TileEntityMobSpawner.getMobID());

        if (entity == null)
        {
            entity = EntityList.createEntityByName(par1TileEntityMobSpawner.getMobID(), null);
            entityHashMap.put(par1TileEntityMobSpawner.getMobID(), entity);
        }

        if (entity != null)
        {
            entity.setWorld(par1TileEntityMobSpawner.worldObj);
            float f = 0.4375F;
            GL11.glTranslatef(0.0F, 0.4F, 0.0F);
            GL11.glRotatef((float)(par1TileEntityMobSpawner.yaw2 + (par1TileEntityMobSpawner.yaw - par1TileEntityMobSpawner.yaw2) * (double)par8) * 10F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(f, f, f);
            entity.setLocationAndAngles(par2, par4, par6, 0.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, par8);
        }

        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntityMobSpawner((TileEntityMobSpawner)par1TileEntity, par2, par4, par6, par8);
    }
}
