package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiLanguage extends GuiScreen
{
    /** This GUI's parent GUI. */
    protected GuiScreen parentGui;

    /**
     * Timer used to update texture packs, decreases every tick and is reset to 20 and updates texture packs upon
     * reaching 0.
     */
    private int updateTimer;

    /** This GUI's language list. */
    private GuiSlotLanguage languageList;
    private final GameSettings field_44006_d;

    /** This GUI's 'Done' button. */
    private GuiSmallButton doneButton;

    public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        updateTimer = -1;
        parentGui = par1GuiScreen;
        field_44006_d = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(doneButton = new GuiSmallButton(6, width / 2 - 75, height - 38, stringtranslate.translateKey("gui.done")));
        languageList = new GuiSlotLanguage(this);
        languageList.registerScrollButtons(controlList, 7, 8);
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

        switch (par1GuiButton.id)
        {
            case 6:
                field_44006_d.saveOptions();
                mc.displayGuiScreen(parentGui);
                break;

            default:
                languageList.actionPerformed(par1GuiButton);
                break;

            case 5:
                break;
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
    protected void mouseMovedOrUp(int par1, int par2, int par3)
    {
        super.mouseMovedOrUp(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        languageList.drawScreen(par1, par2, par3);

        if (updateTimer <= 0)
        {
            mc.texturePackList.updateAvaliableTexturePacks();
            updateTimer += 20;
        }

        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("options.language"), width / 2, 16, 0xffffff);
        drawCenteredString(fontRenderer, (new StringBuilder()).append("(").append(stringtranslate.translateKey("options.languageWarning")).append(")").toString(), width / 2, height - 56, 0x808080);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        updateTimer--;
    }

    static GameSettings func_44005_a(GuiLanguage par0GuiLanguage)
    {
        return par0GuiLanguage.field_44006_d;
    }

    static GuiSmallButton func_46028_b(GuiLanguage par0GuiLanguage)
    {
        return par0GuiLanguage.doneButton;
    }
}
