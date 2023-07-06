package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiParticle extends Gui
{
    private List particles;
    private Minecraft mc;

    public GuiParticle(Minecraft par1Minecraft)
    {
        particles = new ArrayList();
        mc = par1Minecraft;
    }

    public void update()
    {
        for (int i = 0; i < particles.size(); i++)
        {
            Particle particle = (Particle)particles.get(i);
            particle.preUpdate();
            particle.update(this);

            if (particle.isDead)
            {
                particles.remove(i--);
            }
        }
    }

    public void draw(float par1)
    {
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/particles.png"));

        for (int i = 0; i < particles.size(); i++)
        {
            Particle particle = (Particle)particles.get(i);
            int j = (int)((particle.prevPosX + (particle.posX - particle.prevPosX) * (double)par1) - 4D);
            int k = (int)((particle.prevPosY + (particle.posY - particle.prevPosY) * (double)par1) - 4D);
            float f = (float)(particle.prevTintAlpha + (particle.tintAlpha - particle.prevTintAlpha) * (double)par1);
            float f1 = (float)(particle.prevTintRed + (particle.tintRed - particle.prevTintRed) * (double)par1);
            float f2 = (float)(particle.prevTintGreen + (particle.tintGreen - particle.prevTintGreen) * (double)par1);
            float f3 = (float)(particle.prevTintBlue + (particle.tintBlue - particle.prevTintBlue) * (double)par1);
            GL11.glColor4f(f1, f2, f3, f);
            drawTexturedModalRect(j, k, 40, 0, 8, 8);
        }
    }
}
