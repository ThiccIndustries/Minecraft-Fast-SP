package net.minecraft.src;

import java.util.List;

public class GuiYesNo extends GuiScreen
{
    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;

    /** First line of text. */
    private String message1;

    /** Second line of text. */
    private String message2;

    /** The text shown for the first button in GuiYesNo */
    protected String buttonText1;

    /** The text shown for the second button in GuiYesNo */
    protected String buttonText2;

    /** World number to be deleted. */
    private int worldNumber;

    public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, int par4)
    {
        parentScreen = par1GuiScreen;
        message1 = par2Str;
        message2 = par3Str;
        worldNumber = par4;
        StringTranslate stringtranslate = StringTranslate.getInstance();
        buttonText1 = stringtranslate.translateKey("gui.yes");
        buttonText2 = stringtranslate.translateKey("gui.no");
    }

    public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, String par5Str, int par6)
    {
        parentScreen = par1GuiScreen;
        message1 = par2Str;
        message2 = par3Str;
        buttonText1 = par4Str;
        buttonText2 = par5Str;
        worldNumber = par6;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.add(new GuiSmallButton(0, width / 2 - 155, height / 6 + 96, buttonText1));
        controlList.add(new GuiSmallButton(1, (width / 2 - 155) + 160, height / 6 + 96, buttonText2));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        parentScreen.confirmClicked(par1GuiButton.id == 0, worldNumber);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, message1, width / 2, 70, 0xffffff);
        drawCenteredString(fontRenderer, message2, width / 2, 90, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
