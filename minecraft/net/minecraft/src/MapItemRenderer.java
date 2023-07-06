package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;

public class MapItemRenderer
{
    private int intArray[];
    private int bufferedImage;
    private GameSettings gameSettings;
    private FontRenderer fontRenderer;

    public MapItemRenderer(FontRenderer par1FontRenderer, GameSettings par2GameSettings, RenderEngine par3RenderEngine)
    {
        intArray = new int[16384];
        gameSettings = par2GameSettings;
        fontRenderer = par1FontRenderer;
        bufferedImage = par3RenderEngine.allocateAndSetupTexture(new BufferedImage(128, 128, 2));

        for (int i = 0; i < 16384; i++)
        {
            intArray[i] = 0;
        }
    }

    public void renderMap(EntityPlayer par1EntityPlayer, RenderEngine par2RenderEngine, MapData par3MapData)
    {
        for (int i = 0; i < 16384; i++)
        {
            byte byte0 = par3MapData.colors[i];

            if (byte0 / 4 == 0)
            {
                intArray[i] = (i + i / 128 & 1) * 8 + 16 << 24;
                continue;
            }

            int l = MapColor.mapColorArray[byte0 / 4].colorValue;
            int i1 = byte0 & 3;
            char c = '\334';

            if (i1 == 2)
            {
                c = '\377';
            }

            if (i1 == 0)
            {
                c = '\264';
            }

            int j1 = ((l >> 16 & 0xff) * c) / 255;
            int k1 = ((l >> 8 & 0xff) * c) / 255;
            int l1 = ((l & 0xff) * c) / 255;

            if (gameSettings.anaglyph)
            {
                int i2 = (j1 * 30 + k1 * 59 + l1 * 11) / 100;
                int j2 = (j1 * 30 + k1 * 70) / 100;
                int k2 = (j1 * 30 + l1 * 70) / 100;
                j1 = i2;
                k1 = j2;
                l1 = k2;
            }

            intArray[i] = 0xff000000 | j1 << 16 | k1 << 8 | l1;
        }

        par2RenderEngine.createTextureFromBytes(intArray, 128, 128, bufferedImage);
        int j = 0;
        int k = 0;
        Tessellator tessellator = Tessellator.instance;
        float f = 0.0F;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, bufferedImage);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((float)(j + 0) + f, (float)(k + 128) - f, -0.0099999997764825821D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((float)(j + 128) - f, (float)(k + 128) - f, -0.0099999997764825821D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((float)(j + 128) - f, (float)(k + 0) + f, -0.0099999997764825821D, 1.0D, 0.0D);
        tessellator.addVertexWithUV((float)(j + 0) + f, (float)(k + 0) + f, -0.0099999997764825821D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        par2RenderEngine.bindTexture(par2RenderEngine.getTexture("/misc/mapicons.png"));

        for (Iterator iterator = par3MapData.playersVisibleOnMap.iterator(); iterator.hasNext(); GL11.glPopMatrix())
        {
            MapCoord mapcoord = (MapCoord)iterator.next();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)j + (float)mapcoord.centerX / 2.0F + 64F, (float)k + (float)mapcoord.centerZ / 2.0F + 64F, -0.02F);
            GL11.glRotatef((float)(mapcoord.iconRotation * 360) / 16F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(4F, 4F, 3F);
            GL11.glTranslatef(-0.125F, 0.125F, 0.0F);
            float f1 = (float)(mapcoord.field_28217_a % 4 + 0) / 4F;
            float f2 = (float)(mapcoord.field_28217_a / 4 + 0) / 4F;
            float f3 = (float)(mapcoord.field_28217_a % 4 + 1) / 4F;
            float f4 = (float)(mapcoord.field_28217_a / 4 + 1) / 4F;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-1D, 1.0D, 0.0D, f1, f2);
            tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, f3, f2);
            tessellator.addVertexWithUV(1.0D, -1D, 0.0D, f3, f4);
            tessellator.addVertexWithUV(-1D, -1D, 0.0D, f1, f4);
            tessellator.draw();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -0.04F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        fontRenderer.drawString(par3MapData.mapName, j, k, 0xff000000);
        GL11.glPopMatrix();
    }
}
