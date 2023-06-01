package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiSlotServer extends GuiSlot
{
    /** Instance to the GUI this list is on. */
    final GuiMultiplayer parentGui;

    public GuiSlotServer(GuiMultiplayer par1GuiMultiplayer)
    {
        super(par1GuiMultiplayer.mc, par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 36);
        parentGui = par1GuiMultiplayer;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiMultiplayer.getServerList(parentGui).size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        GuiMultiplayer.setSelectedServer(parentGui, par1);
        boolean flag = GuiMultiplayer.getSelectedServer(parentGui) >= 0 && GuiMultiplayer.getSelectedServer(parentGui) < getSize();
        GuiMultiplayer.getButtonSelect(parentGui).enabled = flag;
        GuiMultiplayer.getButtonEdit(parentGui).enabled = flag;
        GuiMultiplayer.getButtonDelete(parentGui).enabled = flag;

        if (par2 && flag)
        {
            GuiMultiplayer.joinServer(parentGui, par1);
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return par1 == GuiMultiplayer.getSelectedServer(parentGui);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return GuiMultiplayer.getServerList(parentGui).size() * 36;
    }

    protected void drawBackground()
    {
        parentGui.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        ServerNBTStorage servernbtstorage = (ServerNBTStorage)GuiMultiplayer.getServerList(parentGui).get(par1);

        synchronized (GuiMultiplayer.getLock())
        {
            if (GuiMultiplayer.getThreadsPending() < 5 && !servernbtstorage.polled)
            {
                servernbtstorage.polled = true;
                servernbtstorage.lag = -2L;
                servernbtstorage.motd = "";
                servernbtstorage.playerCount = "";
                GuiMultiplayer.incrementThreadsPending();
                (new ThreadPollServers(this, servernbtstorage)).start();
            }
        }

        parentGui.drawString(parentGui.fontRenderer, servernbtstorage.name, par2 + 2, par3 + 1, 0xffffff);
        parentGui.drawString(parentGui.fontRenderer, servernbtstorage.motd, par2 + 2, par3 + 12, 0x808080);
        parentGui.drawString(parentGui.fontRenderer, servernbtstorage.playerCount, (par2 + 215) - parentGui.fontRenderer.getStringWidth(servernbtstorage.playerCount), par3 + 12, 0x808080);
        parentGui.drawString(parentGui.fontRenderer, servernbtstorage.host, par2 + 2, par3 + 12 + 11, 0x303030);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        parentGui.mc.renderEngine.bindTexture(parentGui.mc.renderEngine.getTexture("/gui/icons.png"));
        String s = "";
        int i;
        int j;

        if (servernbtstorage.polled && servernbtstorage.lag != -2L)
        {
            i = 0;
            j = 0;

            if (servernbtstorage.lag < 0L)
            {
                j = 5;
            }
            else if (servernbtstorage.lag < 150L)
            {
                j = 0;
            }
            else if (servernbtstorage.lag < 300L)
            {
                j = 1;
            }
            else if (servernbtstorage.lag < 600L)
            {
                j = 2;
            }
            else if (servernbtstorage.lag < 1000L)
            {
                j = 3;
            }
            else
            {
                j = 4;
            }

            if (servernbtstorage.lag < 0L)
            {
                s = "(no connection)";
            }
            else
            {
                s = (new StringBuilder()).append(servernbtstorage.lag).append("ms").toString();
            }
        }
        else
        {
            i = 1;
            j = (int)(System.currentTimeMillis() / 100L + (long)(par1 * 2) & 7L);

            if (j > 4)
            {
                j = 8 - j;
            }

            s = "Polling..";
        }

        parentGui.drawTexturedModalRect(par2 + 205, par3, 0 + i * 10, 176 + j * 8, 10, 8);
        byte byte0 = 4;

        if (mouseX >= (par2 + 205) - byte0 && mouseY >= par3 - byte0 && mouseX <= par2 + 205 + 10 + byte0 && mouseY <= par3 + 8 + byte0)
        {
            GuiMultiplayer.setTooltipText(parentGui, s);
        }
    }
}
