package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen
{
    /** The RNG used by the Main Menu Screen. */
    private static final Random rand = new Random();

    /** Counts the number of screen updates. */
    private float updateCounter;

    /** The splash message. */
    private String splashText;
    private GuiButton multiplayerButton;

    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private int viewportTexture;

    public GuiMainMenu()
    {
        updateCounter = 0.0F;
        panoramaTimer = 0;
        splashText = "missingno";

        try
        {
            ArrayList arraylist = new ArrayList();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((net.minecraft.src.GuiMainMenu.class).getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String s = "";

            do
            {
                String s1;

                if ((s1 = bufferedreader.readLine()) == null)
                {
                    break;
                }

                s1 = s1.trim();

                if (s1.length() > 0)
                {
                    arraylist.add(s1);
                }
            }
            while (true);

            do
            {
                splashText = (String)arraylist.get(rand.nextInt(arraylist.size()));
            }
            while (splashText.hashCode() == 0x77f432f);
        }
        catch (Exception exception) { }

        updateCounter = rand.nextFloat();
        //splashText = "InfDev was a mistake";
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        //panoramaTimer++;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
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
        viewportTexture = mc.renderEngine.allocateAndSetupTexture(new java.awt.image.BufferedImage(256, 256, 2));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9)
        {
            splashText = "Happy birthday, ez!";
        }
        else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1)
        {
            splashText = "Happy birthday, Notch!";
        }
        else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            splashText = "Happy new year!";
        }

        StringTranslate stringtranslate = StringTranslate.getInstance();
        int i = height / 4 + 48;
        controlList.add(new GuiButton(1, width / 2 - 100, i, "Play Game"));
        //controlList.add(multiplayerButton = new GuiButton(2, width / 2 - 100, i + 24, stringtranslate.translateKey("menu.multiplayer")));
        
        //TODO: Disable multiplayer for now
        //multiplayerButton.enabled = false;
        controlList.add(new GuiButton(3, width / 2 - 100, i + 24, stringtranslate.translateKey("menu.mods")));

        controlList.add(new GuiButton(0, width / 2 - 100, i + 48 + 12, 98, 20, stringtranslate.translateKey("menu.options")));
        controlList.add(new GuiButton(4, width / 2 + 2, i + 48 + 12, 98, 20, stringtranslate.translateKey("menu.quit")));

        controlList.add(new GuiButtonLanguage(5, width / 2 - 124, i + 48 + 12));

        if (mc.session == null)
        {
            multiplayerButton.enabled = false;
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }

        if (par1GuiButton.id == 5)
        {
            mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings));
        }

        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (par1GuiButton.id == 2)
        {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (par1GuiButton.id == 3)
        {
            mc.displayGuiScreen(new GuiTexturePacks(this));
        }

        if (par1GuiButton.id == 4)
        {
            mc.shutdown();
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int par1, int par2, float par3)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluPerspective(120F, 1.0F, 0.05F, 10F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int i = 8;

        for (int j = 0; j < i * i; j++)
        {
            GL11.glPushMatrix();
            float f = ((float)(j % i) / (float)i - 0.5F) / 64F;
            float f1 = ((float)(j / i) / (float)i - 0.5F) / 64F;
            float f2 = 0.0F;
            GL11.glTranslatef(f, f1, f2);
            GL11.glRotatef(MathHelper.sin(((float)panoramaTimer + par3) / 400F) * 25F + 20F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-((float)panoramaTimer + par3) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; k++)
            {
                GL11.glPushMatrix();

                if (k == 1)
                {
                    GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2)
                {
                    GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3)
                {
                    GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4)
                {
                    GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5)
                {
                    GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                }

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture((new StringBuilder()).append("/title/bg/panorama").append(k).append(".png").toString()));
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_I(0xffffff, 255 / (j + 1));
                float f3 = 0.0F;
                tessellator.addVertexWithUV(-1D, -1D, 1.0D, 0.0F + f3, 0.0F + f3);
                tessellator.addVertexWithUV(1.0D, -1D, 1.0D, 1.0F - f3, 0.0F + f3);
                tessellator.addVertexWithUV(1.0D, 1.0D, 1.0D, 1.0F - f3, 1.0F - f3);
                tessellator.addVertexWithUV(-1D, 1.0D, 1.0D, 0.0F + f3, 1.0F - f3);
                tessellator.draw();
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }

        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox(float par1)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, viewportTexture);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        byte byte0 = 3;

        for (int i = 0; i < byte0; i++)
        {
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float)(i + 1));
            int j = width;
            int k = height;
            float f = (float)(i - byte0 / 2) / 256F;
            tessellator.addVertexWithUV(j, k, zLevel, 0.0F + f, 0.0D);
            tessellator.addVertexWithUV(j, 0.0D, zLevel, 1.0F + f, 0.0D);
            tessellator.addVertexWithUV(0.0D, 0.0D, zLevel, 1.0F + f, 1.0D);
            tessellator.addVertexWithUV(0.0D, k, zLevel, 0.0F + f, 1.0D);
        }

        tessellator.draw();
        GL11.glColorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int par1, int par2, float par3)
    {
        GL11.glViewport(0, 0, 256, 256);
        drawPanorama(par1, par2, par3);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float f = width <= height ? 120F / (float)height : 120F / (float)width;
        float f1 = ((float)height * f) / 256F;
        float f2 = ((float)width * f) / 256F;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int i = width;
        int j = height;
        tessellator.addVertexWithUV(0.0D, j, zLevel, 0.5F - f1, 0.5F + f2);
        tessellator.addVertexWithUV(i, j, zLevel, 0.5F - f1, 0.5F - f2);
        tessellator.addVertexWithUV(i, 0.0D, zLevel, 0.5F + f1, 0.5F - f2);
        tessellator.addVertexWithUV(0.0D, 0.0D, zLevel, 0.5F + f1, 0.5F + f2);
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        //renderSkybox(par1, par2, par3);
    	drawDefaultBackground();
        Tessellator tessellator = Tessellator.instance;
        char c = 274;
        int i = width / 2 - c / 2;
        byte byte0 = 30;
        //drawGradientRect(0, 0, width, height, 0x80ffffff, 0xffffff);
        //drawGradientRect(0, 0, width, height, 0, 0x80000000);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/title/mclogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if ((double)updateCounter < 0.0001D)
        {
            drawTexturedModalRect(i + 0, byte0 + 0, 0, 0, 99, 44);
            drawTexturedModalRect(i + 99, byte0 + 0, 129, 0, 27, 44);
            drawTexturedModalRect(i + 99 + 26, byte0 + 0, 126, 0, 3, 44);
            drawTexturedModalRect(i + 99 + 26 + 3, byte0 + 0, 99, 0, 26, 44);
            drawTexturedModalRect(i + 155, byte0 + 0, 0, 45, 155, 44);
        }
        else
        {
            drawTexturedModalRect(i + 0, byte0 + 0, 0, 0, 155, 44);
            drawTexturedModalRect(i + 155, byte0 + 0, 0, 45, 155, 44);
        }

        tessellator.setColorOpaque_I(0xffffff);
        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2 + 90, 70F, 0.0F);
        GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin(((float)(System.currentTimeMillis() % 1000L) / 1000F) * (float)Math.PI * 2.0F) * 0.1F);
        f = (f * 100F) / (float)(fontRenderer.getStringWidth(splashText) + 32);
        GL11.glScalef(f, f, f);
        drawCenteredString(fontRenderer, splashText, 0, -8, 0xffff00);
        GL11.glPopMatrix();
        drawString(fontRenderer, "Minecraft 1.2.5 FastSP", 2, height - 10, 0xffffff);
        String s = "Copyright Mojang AB. Do not distribute! ";
        drawString(fontRenderer, s, width - fontRenderer.getStringWidth(s) - 2, height - 20, 0xffffff);
        s = "Thicc Industries. Do distribute! ";
        drawString(fontRenderer, s, width - fontRenderer.getStringWidth(s), height - 10, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
