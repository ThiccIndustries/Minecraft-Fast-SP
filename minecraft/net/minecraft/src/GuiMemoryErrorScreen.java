package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiMemoryErrorScreen extends GuiScreen
{
    public GuiMemoryErrorScreen()
    {
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.clear();
        controlList.add(new GuiSmallButton(0, width / 2 - 155, height / 4 + 120 + 12, stringtranslate.translateKey("gui.toMenu")));
        controlList.add(new GuiSmallButton(1, (width / 2 - 155) + 160, height / 4 + 120 + 12, stringtranslate.translateKey("menu.quit")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        else if (par1GuiButton.id == 1)
        {
            mc.shutdown();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Out of memory!", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Minecraft has run out of memory.", width / 2 - 140, (height / 4 - 60) + 60 + 0, 0xa0a0a0);
        drawString(fontRenderer, "This could be caused by a bug in the game or by the", width / 2 - 140, (height / 4 - 60) + 60 + 18, 0xa0a0a0);
        drawString(fontRenderer, "Java Virtual Machine not being allocated enough", width / 2 - 140, (height / 4 - 60) + 60 + 27, 0xa0a0a0);
        drawString(fontRenderer, "memory. If you are playing in a web browser, try", width / 2 - 140, (height / 4 - 60) + 60 + 36, 0xa0a0a0);
        drawString(fontRenderer, "downloading the game and playing it offline.", width / 2 - 140, (height / 4 - 60) + 60 + 45, 0xa0a0a0);
        drawString(fontRenderer, "To prevent level corruption, the current game has quit.", width / 2 - 140, (height / 4 - 60) + 60 + 63, 0xa0a0a0);
        drawString(fontRenderer, "We've tried to free up enough memory to let you go back to", width / 2 - 140, (height / 4 - 60) + 60 + 81, 0xa0a0a0);
        drawString(fontRenderer, "the main menu and back to playing, but this may not have worked.", width / 2 - 140, (height / 4 - 60) + 60 + 90, 0xa0a0a0);
        drawString(fontRenderer, "Please restart the game if you see this message again.", width / 2 - 140, (height / 4 - 60) + 60 + 99, 0xa0a0a0);
        super.drawScreen(par1, par2, par3);
    }
}
