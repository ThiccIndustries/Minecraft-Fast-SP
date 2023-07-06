package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;

class ThreadConnectToServer extends Thread
{
    /** A reference to the Minecraft object. */
    final Minecraft mc;

    /** The IP address or domain used to connect. */
    final String ip;

    /** The port used to connect. */
    final int port;

    /** A reference to the GuiConnecting object. */
    final GuiConnecting connectingGui;

    ThreadConnectToServer(GuiConnecting par1GuiConnecting, Minecraft par2Minecraft, String par3Str, int par4)
    {
        connectingGui = par1GuiConnecting;
        mc = par2Minecraft;
        ip = par3Str;
        port = par4;
    }

    public void run()
    {
        try
        {
            GuiConnecting.setNetClientHandler(connectingGui, new NetClientHandler(mc, ip, port));

            if (GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }

            GuiConnecting.getNetClientHandler(connectingGui).addToSendQueue(new Packet2Handshake(mc.session.username, ip, port));
        }
        catch (UnknownHostException unknownhostexception)
        {
            if (GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }

            mc.displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        (new StringBuilder()).append("Unknown host '").append(ip).append("'").toString()
                    }));
        }
        catch (ConnectException connectexception)
        {
            if (GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }

            mc.displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        connectexception.getMessage()
                    }));
        }
        catch (Exception exception)
        {
            if (GuiConnecting.isCancelled(connectingGui))
            {
                return;
            }

            exception.printStackTrace();
            mc.displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        exception.toString()
                    }));
        }
    }
}
