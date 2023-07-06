package net.minecraft.src;

import java.util.List;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen
{
    private static String field_52009_d = "";

    /** Needed a change as a local variable was conflicting on construct */
    private final GuiScreen guiScreen;

    /** This GUI's instance to the server list's storage */
    private final ServerNBTStorage serverListStorage;
    private GuiTextField serverTextField;

    public GuiScreenServerList(GuiScreen par1GuiScreen, ServerNBTStorage par2ServerNBTStorage)
    {
        guiScreen = par1GuiScreen;
        serverListStorage = par2ServerNBTStorage;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        serverTextField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("selectServer.select")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
        serverTextField.setMaxStringLength(128);
        serverTextField.func_50033_b(true);
        serverTextField.setText(field_52009_d);
        ((GuiButton)controlList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        field_52009_d = serverTextField.getText();
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
            guiScreen.confirmClicked(false, 0);
        }
        else if (par1GuiButton.id == 0)
        {
            serverListStorage.host = serverTextField.getText();
            guiScreen.confirmClicked(true, 0);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        serverTextField.func_50037_a(par1, par2);

        if (par1 == '\034')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        serverTextField.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("selectServer.direct"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("addServer.enterIp"), width / 2 - 100, 100, 0xa0a0a0);
        serverTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
