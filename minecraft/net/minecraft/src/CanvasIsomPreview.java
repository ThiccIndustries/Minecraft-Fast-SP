package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CanvasIsomPreview extends Canvas implements KeyListener, MouseListener, MouseMotionListener, Runnable
{
    private int currentRender;
    private int zoom;
    private boolean showHelp;
    private World level;
    private File workDir;
    private boolean running;
    private java.util.List zonesToRender;
    private IsoImageBuffer zoneMap[][];
    private int field_1785_i;
    private int field_1784_j;
    private int field_1783_k;
    private int field_1782_l;

    public File getWorkingDirectory()
    {
        if (workDir == null)
        {
            workDir = getWorkingDirectory("minecraft");
        }

        return workDir;
    }

    public File getWorkingDirectory(String par1Str)
    {
        String s = System.getProperty("user.home", ".");
        File file;

        switch (OsMap.osValues[getPlatform().ordinal()])
        {
            case 1:
            case 2:
                file = new File(s, (new StringBuilder()).append('.').append(par1Str).append('/').toString());
                break;

            case 3:
                String s1 = System.getenv("APPDATA");

                if (s1 != null)
                {
                    file = new File(s1, (new StringBuilder()).append(".").append(par1Str).append('/').toString());
                }
                else
                {
                    file = new File(s, (new StringBuilder()).append('.').append(par1Str).append('/').toString());
                }

                break;

            case 4:
                file = new File(s, (new StringBuilder()).append("Library/Application Support/").append(par1Str).toString());
                break;

            default:
                file = new File(s, (new StringBuilder()).append(par1Str).append('/').toString());
                break;
        }

        if (!file.exists() && !file.mkdirs())
        {
            throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(file).toString());
        }
        else
        {
            return file;
        }
    }

    private static EnumOS1 getPlatform()
    {
        String s = System.getProperty("os.name").toLowerCase();

        if (s.contains("win"))
        {
            return EnumOS1.windows;
        }

        if (s.contains("mac"))
        {
            return EnumOS1.macos;
        }

        if (s.contains("solaris"))
        {
            return EnumOS1.solaris;
        }

        if (s.contains("sunos"))
        {
            return EnumOS1.solaris;
        }

        if (s.contains("linux"))
        {
            return EnumOS1.linux;
        }

        if (s.contains("unix"))
        {
            return EnumOS1.linux;
        }
        else
        {
            return EnumOS1.unknown;
        }
    }

    public CanvasIsomPreview()
    {
        currentRender = 0;
        zoom = 2;
        showHelp = true;
        running = true;
        zonesToRender = Collections.synchronizedList(new LinkedList());
        zoneMap = new IsoImageBuffer[64][64];
        workDir = getWorkingDirectory();

        for (int i = 0; i < 64; i++)
        {
            for (int j = 0; j < 64; j++)
            {
                zoneMap[i][j] = new IsoImageBuffer(null, i, j);
            }
        }

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setBackground(Color.red);
    }

    public void loadLevel(String par1Str)
    {
        field_1785_i = field_1784_j = 0;
        level = new World(new SaveHandler(new File(workDir, "saves"), par1Str, false), par1Str, new WorldSettings((new Random()).nextLong(), 0, true, false, WorldType.DEFAULT, 0));
        level.skylightSubtracted = 0;

        synchronized (zonesToRender)
        {
            zonesToRender.clear();

            for (int i = 0; i < 64; i++)
            {
                for (int j = 0; j < 64; j++)
                {
                    zoneMap[i][j].init(level, i, j);
                }
            }
        }
    }

    private void setBrightness(int par1)
    {
        synchronized (zonesToRender)
        {
            level.skylightSubtracted = par1;
            zonesToRender.clear();

            for (int i = 0; i < 64; i++)
            {
                for (int j = 0; j < 64; j++)
                {
                    zoneMap[i][j].init(level, i, j);
                }
            }
        }
    }

    public void start()
    {
        (new ThreadRunIsoClient(this)).start();

        for (int i = 0; i < 8; i++)
        {
            (new Thread(this)).start();
        }
    }

    public void stop()
    {
        running = false;
    }

    private IsoImageBuffer getZone(int par1, int par2)
    {
        int i = par1 & 0x3f;
        int j = par2 & 0x3f;
        IsoImageBuffer isoimagebuffer = zoneMap[i][j];

        if (isoimagebuffer.x == par1 && isoimagebuffer.y == par2)
        {
            return isoimagebuffer;
        }

        synchronized (zonesToRender)
        {
            zonesToRender.remove(isoimagebuffer);
        }

        isoimagebuffer.init(par1, par2);
        return isoimagebuffer;
    }

    public void run()
    {
        TerrainTextureManager terraintexturemanager = new TerrainTextureManager();

        while (running)
        {
            IsoImageBuffer isoimagebuffer = null;

            synchronized (zonesToRender)
            {
                if (zonesToRender.size() > 0)
                {
                    isoimagebuffer = (IsoImageBuffer)zonesToRender.remove(0);
                }
            }

            if (isoimagebuffer != null)
            {
                if (currentRender - isoimagebuffer.lastVisible < 2)
                {
                    terraintexturemanager.render(isoimagebuffer);
                    repaint();
                }
                else
                {
                    isoimagebuffer.addedToRenderQueue = false;
                }
            }

            try
            {
                Thread.sleep(2L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    public void update(Graphics g)
    {
    }

    public void paint(Graphics g)
    {
    }

    public void render()
    {
        BufferStrategy bufferstrategy = getBufferStrategy();

        if (bufferstrategy == null)
        {
            createBufferStrategy(2);
            return;
        }
        else
        {
            render((Graphics2D)bufferstrategy.getDrawGraphics());
            bufferstrategy.show();
            return;
        }
    }

    public void render(Graphics2D par1Graphics2D)
    {
        currentRender++;
        java.awt.geom.AffineTransform affinetransform = par1Graphics2D.getTransform();
        par1Graphics2D.setClip(0, 0, getWidth(), getHeight());
        par1Graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        par1Graphics2D.translate(getWidth() / 2, getHeight() / 2);
        par1Graphics2D.scale(zoom, zoom);
        par1Graphics2D.translate(field_1785_i, field_1784_j);

        if (level != null)
        {
            ChunkCoordinates chunkcoordinates = level.getSpawnPoint();
            par1Graphics2D.translate(-(chunkcoordinates.posX + chunkcoordinates.posZ), -(-chunkcoordinates.posX + chunkcoordinates.posZ) + 64);
        }

        Rectangle rectangle = par1Graphics2D.getClipBounds();
        par1Graphics2D.setColor(new Color(0xff101020));
        par1Graphics2D.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        byte byte0 = 16;
        byte byte1 = 3;
        int i = rectangle.x / byte0 / 2 - 2 - byte1;
        int j = (rectangle.x + rectangle.width) / byte0 / 2 + 1 + byte1;
        int k = rectangle.y / byte0 - 1 - byte1 * 2;
        int l = (rectangle.y + rectangle.height + 16 + 128) / byte0 + 1 + byte1 * 2;

        for (int i1 = k; i1 <= l; i1++)
        {
            for (int k1 = i; k1 <= j; k1++)
            {
                int l1 = k1 - (i1 >> 1);
                int i2 = k1 + (i1 + 1 >> 1);
                IsoImageBuffer isoimagebuffer = getZone(l1, i2);
                isoimagebuffer.lastVisible = currentRender;

                if (!isoimagebuffer.rendered)
                {
                    if (!isoimagebuffer.addedToRenderQueue)
                    {
                        isoimagebuffer.addedToRenderQueue = true;
                        zonesToRender.add(isoimagebuffer);
                    }

                    continue;
                }

                isoimagebuffer.addedToRenderQueue = false;

                if (!isoimagebuffer.noContent)
                {
                    int j2 = k1 * byte0 * 2 + (i1 & 1) * byte0;
                    int k2 = i1 * byte0 - 128 - 16;
                    par1Graphics2D.drawImage(isoimagebuffer.image, j2, k2, null);
                }
            }
        }

        if (showHelp)
        {
            par1Graphics2D.setTransform(affinetransform);
            int j1 = getHeight() - 32 - 4;
            par1Graphics2D.setColor(new Color(0x80000000, true));
            par1Graphics2D.fillRect(4, getHeight() - 32 - 4, getWidth() - 8, 32);
            par1Graphics2D.setColor(Color.WHITE);
            String s = "F1 - F5: load levels   |   0-9: Set time of day   |   Space: return to spawn   |   Double click: zoom   |   Escape: hide this text";
            par1Graphics2D.drawString(s, getWidth() / 2 - par1Graphics2D.getFontMetrics().stringWidth(s) / 2, j1 + 20);
        }

        par1Graphics2D.dispose();
    }

    public void mouseDragged(MouseEvent par1MouseEvent)
    {
        int i = par1MouseEvent.getX() / zoom;
        int j = par1MouseEvent.getY() / zoom;
        field_1785_i += i - field_1783_k;
        field_1784_j += j - field_1782_l;
        field_1783_k = i;
        field_1782_l = j;
        repaint();
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mouseClicked(MouseEvent par1MouseEvent)
    {
        if (par1MouseEvent.getClickCount() == 2)
        {
            zoom = 3 - zoom;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent par1MouseEvent)
    {
        int i = par1MouseEvent.getX() / zoom;
        int j = par1MouseEvent.getY() / zoom;
        field_1783_k = i;
        field_1782_l = j;
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void keyPressed(KeyEvent par1KeyEvent)
    {
        if (par1KeyEvent.getKeyCode() == 48)
        {
            setBrightness(11);
        }

        if (par1KeyEvent.getKeyCode() == 49)
        {
            setBrightness(10);
        }

        if (par1KeyEvent.getKeyCode() == 50)
        {
            setBrightness(9);
        }

        if (par1KeyEvent.getKeyCode() == 51)
        {
            setBrightness(7);
        }

        if (par1KeyEvent.getKeyCode() == 52)
        {
            setBrightness(6);
        }

        if (par1KeyEvent.getKeyCode() == 53)
        {
            setBrightness(5);
        }

        if (par1KeyEvent.getKeyCode() == 54)
        {
            setBrightness(3);
        }

        if (par1KeyEvent.getKeyCode() == 55)
        {
            setBrightness(2);
        }

        if (par1KeyEvent.getKeyCode() == 56)
        {
            setBrightness(1);
        }

        if (par1KeyEvent.getKeyCode() == 57)
        {
            setBrightness(0);
        }

        if (par1KeyEvent.getKeyCode() == 112)
        {
            loadLevel("World1");
        }

        if (par1KeyEvent.getKeyCode() == 113)
        {
            loadLevel("World2");
        }

        if (par1KeyEvent.getKeyCode() == 114)
        {
            loadLevel("World3");
        }

        if (par1KeyEvent.getKeyCode() == 115)
        {
            loadLevel("World4");
        }

        if (par1KeyEvent.getKeyCode() == 116)
        {
            loadLevel("World5");
        }

        if (par1KeyEvent.getKeyCode() == 32)
        {
            field_1785_i = field_1784_j = 0;
        }

        if (par1KeyEvent.getKeyCode() == 27)
        {
            showHelp = !showHelp;
        }

        repaint();
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    static boolean isRunning(CanvasIsomPreview par0CanvasIsomPreview)
    {
        return par0CanvasIsomPreview.running;
    }
}
