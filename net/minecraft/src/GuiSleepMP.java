package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiSleepMP extends GuiChat
{
    public GuiSleepMP()
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(new GuiButton(1, width / 2 - 100, height - 40, stringtranslate.translateKey("multiplayer.stopSleeping")));
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1)
        {
            wakeEntity();
        }
        else if (par2 == 28)
        {
            String s = field_50064_a.getText().trim();

            if (s.length() > 0)
            {
                mc.thePlayer.sendChatMessage(s);
            }

            field_50064_a.setText("");
            mc.ingameGUI.func_50014_d();
        }
        else
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 1)
        {
            wakeEntity();
        }
        else
        {
            super.actionPerformed(par1GuiButton);
        }
    }

    /**
     * Wakes the entity from the bed
     */
    private void wakeEntity()
    {
        if (mc.thePlayer instanceof EntityClientPlayerMP)
        {
            NetClientHandler netclienthandler = ((EntityClientPlayerMP)mc.thePlayer).sendQueue;
            netclienthandler.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 3));
        }
    }
}
