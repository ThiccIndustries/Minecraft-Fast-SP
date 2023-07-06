package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.*;
import net.minecraft.client.Minecraft;

public class TexturePackList
{
    /** The list of the available texture packs. */
    private List availableTexturePacks;

    /** The default texture pack. */
    private TexturePackBase defaultTexturePack;

    /** The TexturePack that will be used. */
    public TexturePackBase selectedTexturePack;
    private Map field_6538_d;

    /** The Minecraft instance used by this TexturePackList */
    private Minecraft mc;

    /** The directory the texture packs will be loaded from. */
    private File texturePackDir;
    private String currentTexturePack;

    public TexturePackList(Minecraft par1Minecraft, File par2File)
    {
        availableTexturePacks = new ArrayList();
        defaultTexturePack = new TexturePackDefault();
        field_6538_d = new HashMap();
        mc = par1Minecraft;
        texturePackDir = new File(par2File, "texturepacks");

        if (texturePackDir.exists())
        {
            if (!texturePackDir.isDirectory())
            {
                texturePackDir.delete();
                texturePackDir.mkdirs();
            }
        }
        else
        {
            texturePackDir.mkdirs();
        }

        currentTexturePack = par1Minecraft.gameSettings.skin;
        updateAvaliableTexturePacks();
        selectedTexturePack.func_6482_a();
    }

    /**
     * Sets the new TexturePack to be used, returning true if it has actually changed, false if nothing changed.
     */
    public boolean setTexturePack(TexturePackBase par1TexturePackBase)
    {
        if (par1TexturePackBase == selectedTexturePack)
        {
            return false;
        }
        else
        {
            selectedTexturePack.closeTexturePackFile();
            currentTexturePack = par1TexturePackBase.texturePackFileName;
            selectedTexturePack = par1TexturePackBase;
            mc.gameSettings.skin = currentTexturePack;
            mc.gameSettings.saveOptions();
            selectedTexturePack.func_6482_a();
            return true;
        }
    }

    /**
     * check the texture packs the client has installed
     */
    public void updateAvaliableTexturePacks()
    {
        ArrayList arraylist = new ArrayList();
        selectedTexturePack = null;
        arraylist.add(defaultTexturePack);

        if (texturePackDir.exists() && texturePackDir.isDirectory())
        {
            File afile[] = texturePackDir.listFiles();
            File afile1[] = afile;
            int i = afile1.length;

            for (int j = 0; j < i; j++)
            {
                File file = afile1[j];

                if (file.isFile() && file.getName().toLowerCase().endsWith(".zip"))
                {
                    String s = (new StringBuilder()).append(file.getName()).append(":").append(file.length()).append(":").append(file.lastModified()).toString();

                    try
                    {
                        if (!field_6538_d.containsKey(s))
                        {
                            TexturePackCustom texturepackcustom = new TexturePackCustom(file);
                            texturepackcustom.texturePackID = s;
                            field_6538_d.put(s, texturepackcustom);
                            texturepackcustom.func_6485_a(mc);
                        }

                        TexturePackBase texturepackbase1 = (TexturePackBase)field_6538_d.get(s);

                        if (texturepackbase1.texturePackFileName.equals(currentTexturePack))
                        {
                            selectedTexturePack = texturepackbase1;
                        }

                        arraylist.add(texturepackbase1);
                    }
                    catch (IOException ioexception)
                    {
                        ioexception.printStackTrace();
                    }

                    continue;
                }

                if (!file.isDirectory() || !(new File(file, "pack.txt")).exists())
                {
                    continue;
                }

                String s1 = (new StringBuilder()).append(file.getName()).append(":folder:").append(file.lastModified()).toString();

                try
                {
                    if (!field_6538_d.containsKey(s1))
                    {
                        TexturePackFolder texturepackfolder = new TexturePackFolder(file);
                        texturepackfolder.texturePackID = s1;
                        field_6538_d.put(s1, texturepackfolder);
                        texturepackfolder.func_6485_a(mc);
                    }

                    TexturePackBase texturepackbase2 = (TexturePackBase)field_6538_d.get(s1);

                    if (texturepackbase2.texturePackFileName.equals(currentTexturePack))
                    {
                        selectedTexturePack = texturepackbase2;
                    }

                    arraylist.add(texturepackbase2);
                }
                catch (IOException ioexception1)
                {
                    ioexception1.printStackTrace();
                }
            }
        }

        if (selectedTexturePack == null)
        {
            selectedTexturePack = defaultTexturePack;
        }

        availableTexturePacks.removeAll(arraylist);
        TexturePackBase texturepackbase;

        for (Iterator iterator = availableTexturePacks.iterator(); iterator.hasNext(); field_6538_d.remove(texturepackbase.texturePackID))
        {
            texturepackbase = (TexturePackBase)iterator.next();
            texturepackbase.unbindThumbnailTexture(mc);
        }

        availableTexturePacks = arraylist;
    }

    /**
     * Returns a list of the available texture packs.
     */
    public List availableTexturePacks()
    {
        return new ArrayList(availableTexturePacks);
    }
}
