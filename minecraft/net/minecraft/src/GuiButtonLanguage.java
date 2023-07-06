package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiButtonLanguage extends GuiButton
{
    public GuiButtonLanguage(int par1, int par2, int par3)
    {
        super(par1, par2, par3, 20, 20, "");
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + field_52008_a && par3 < yPosition + field_52007_b;
        int i = 106;

        if (flag)
        {
            i += field_52007_b;
        }

        drawTexturedModalRect(xPosition, yPosition, 0, i, field_52008_a, field_52007_b);
    }
}
