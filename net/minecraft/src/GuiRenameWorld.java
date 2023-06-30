package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    private GuiTextField theGuiTextField;
    private final String worldName;

    public GuiRenameWorld(GuiScreen par1GuiScreen, String par2Str)
    {
        parentGuiScreen = par1GuiScreen;
        worldName = par2Str;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        theGuiTextField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("selectWorld.renameButton")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        ISaveFormat isaveformat = mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo(worldName);
        String s = worldinfo.getWorldName();
        theGuiTextField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        theGuiTextField.func_50033_b(true);
        theGuiTextField.setText(s);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(parentGuiScreen);
        }
        else if (par1GuiButton.id == 0)
        {
            ISaveFormat isaveformat = mc.getSaveLoader();
            isaveformat.renameWorld(worldName, theGuiTextField.getText().trim());
            mc.displayGuiScreen(parentGuiScreen);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        theGuiTextField.func_50037_a(par1, par2);
        ((GuiButton)controlList.get(0)).enabled = theGuiTextField.getText().trim().length() > 0;

        if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        theGuiTextField.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("selectWorld.renameTitle"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 0xa0a0a0);
        theGuiTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
