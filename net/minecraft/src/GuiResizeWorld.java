package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiResizeWorld extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    private GuiTextField theGuiTextField;
    private final String worldName;
    private Integer size;
    private Integer prev_size;
    
    public GuiResizeWorld(GuiScreen par1GuiScreen, String par2Str)
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
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Resize"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        ISaveFormat isaveformat = mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo(worldName);
        
        size = prev_size = worldinfo.getWorldSize();
        theGuiTextField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        theGuiTextField.func_50033_b(true);
        theGuiTextField.setText(size.toString());
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
            isaveformat.resizeWorld(worldName, size);
            mc.displayGuiScreen(parentGuiScreen);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
    	if(isNumberalOrFunctions(par1)){
    		theGuiTextField.func_50037_a(par1, par2);
    		
    		if(theGuiTextField.getText().length() > 0)
    			size = Integer.parseInt(theGuiTextField.getText());
    		
    		((GuiButton)controlList.get(0)).enabled = theGuiTextField.getText().trim().length() > 0 && prev_size <= size;
    	}
    	
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
    
    private boolean isNumberalOrFunctions(char c){
    	System.out.println(c);
    	return c == '0' || c == '1' || c == '2' || c == '3' || 
    			c == '4' || c == '5' || c == '6' || c == '7'  || c == '8'  || c == '9' || c == ''; 
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Resize World", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Size", width / 2 - 100, 47, 0xa0a0a0);
        
        if(prev_size > size)
        	drawString(fontRenderer, "Must be larger than previous size", width / 2 - 100 + 14, height / 4 + 96, 0xaa0000);
        
        drawString(fontRenderer, "Total Chunks: ", width / 2 - 100, 87, 0xa0a0a0);
        
        Integer total = (size * 2) * (size * 2);
        drawString(fontRenderer, total.toString(), width / 2 - 30, 87, 0xaaaaaa);
        
        float loadtime = 0.08f * total;
        Integer loadtimeseconds = ((int)loadtime) % 60;
        Integer loadtimeminutes = (((int)loadtime) / 60) % 60;
        Integer loadtimehours = (((int)loadtime) / (60 * 60)) % 24;
        Integer loadtimedays = ((int)loadtime) / (60 * 60 * 24);
        StringBuilder s = new StringBuilder();
        s.append("Est. load time: ");

        if(loadtimedays != 0)
        	s.append (loadtimedays + "d ");
        
        if(loadtimehours != 0)
        	s.append (loadtimehours + "h ");
        
        if(loadtimeminutes != 0)
        	s.append (loadtimeminutes + "m ");
        
        s.append (loadtimeseconds + "s ");
        
        drawString(fontRenderer, s.toString(), width / 2 - 100, 97, 0xa0a0a0);
        theGuiTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
