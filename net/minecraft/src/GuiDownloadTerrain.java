package net.minecraft.src;

import java.util.List;

public class GuiDownloadTerrain extends GuiScreen
{
    /** Network object that downloads the terrain data. */
    private NetClientHandler netHandler;

    /** Counts the number of screen updates. */
    private int updateCounter;

    public GuiDownloadTerrain(NetClientHandler par1NetClientHandler)
    {
        updateCounter = 0;
        netHandler = par1NetClientHandler;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        updateCounter++;

        if (updateCounter % 20 == 0)
        {
            netHandler.addToSendQueue(new Packet0KeepAlive());
        }

        if (netHandler != null)
        {
            netHandler.processReadPackets();
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawBackground(0);
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.downloadingTerrain"), width / 2, height / 2 - 50, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
