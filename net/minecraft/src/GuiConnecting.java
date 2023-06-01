package net.minecraft.src;

import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{
    /** A reference to the NetClientHandler. */
    private NetClientHandler clientHandler;

    /** True if the connection attempt has been cancelled. */
    private boolean cancelled;

    public GuiConnecting(Minecraft par1Minecraft, String par2Str, int par3)
    {
        cancelled = false;
        System.out.println((new StringBuilder()).append("Connecting to ").append(par2Str).append(", ").append(par3).toString());
        par1Minecraft.changeWorld1(null);
        (new ThreadConnectToServer(this, par1Minecraft, par2Str, par3)).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (clientHandler != null)
        {
            clientHandler.processReadPackets();
        }
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
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            cancelled = true;

            if (clientHandler != null)
            {
                clientHandler.disconnect();
            }

            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        StringTranslate stringtranslate = StringTranslate.getInstance();

        if (clientHandler == null)
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.connecting"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 0xffffff);
        }
        else
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.authorizing"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, clientHandler.field_1209_a, width / 2, height / 2 - 10, 0xffffff);
        }

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Sets the NetClientHandler.
     */
    static NetClientHandler setNetClientHandler(GuiConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler)
    {
        return par0GuiConnecting.clientHandler = par1NetClientHandler;
    }

    /**
     * Returns true if the connection attempt has been cancelled, false otherwise.
     */
    static boolean isCancelled(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.cancelled;
    }

    /**
     * Gets the NetClientHandler.
     */
    static NetClientHandler getNetClientHandler(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.clientHandler;
    }
}
