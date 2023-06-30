package net.minecraft.src;

public class GuiErrorScreen extends GuiScreen
{
    /**
     * Unused class. Would contain a message drawn to the center of the screen.
     */
    private String message1;

    /**
     * Unused class. Would contain a message drawn to the center of the screen.
     */
    private String message2;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawGradientRect(0, 0, width, height, 0xff402020, 0xff501010);
        drawCenteredString(fontRenderer, message1, width / 2, 90, 0xffffff);
        drawCenteredString(fontRenderer, message2, width / 2, 110, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
    }
}
