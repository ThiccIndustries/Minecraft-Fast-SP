package net.minecraft.src;

import java.io.InputStream;
import java.util.ArrayList;

public class NaturalTextures
{
    private static RenderEngine renderEngine = null;
    private static NaturalProperties propertiesByTex[][] = new NaturalProperties[0][];

    public NaturalTextures()
    {
    }

    public static void update(RenderEngine renderengine)
    {
        propertiesByTex = new NaturalProperties[0][];
        renderEngine = renderengine;

        if (!Config.isNaturalTextures())
        {
            return;
        }

        InputStream inputstream = renderengine.texturePack.selectedTexturePack.getResourceAsStream("/natural.properties");

        if (inputstream == null)
        {
            Config.dbg("natural.properties not found");
            propertiesByTex = makeDefaultProperties();
            return;
        }

        try
        {
            ArrayList arraylist = new ArrayList(1024);
            String s = Config.readInputStream(inputstream);
            inputstream.close();
            String as[] = Config.tokenize(s, "\n\r");
            Config.dbg("Parsing natural.properties");

            for (int i = 0; i < as.length; i++)
            {
                String s1 = as[i].trim();

                if (s1.startsWith("#"))
                {
                    continue;
                }

                String as1[] = Config.tokenize(s1, "=");

                if (as1.length != 2)
                {
                    Config.dbg((new StringBuilder()).append("Invalid natural.properties line: ").append(s1).toString());
                    continue;
                }

                String s2 = as1[0].trim();
                String s3 = as1[1].trim();
                String as2[] = Config.tokenize(s2, ":");

                if (as2.length != 2)
                {
                    Config.dbg((new StringBuilder()).append("Invalid natural.properties line: ").append(s1).toString());
                    continue;
                }

                String s4 = as2[0];
                String s5 = as2[1];
                int j = Config.parseInt(s5, -1);

                if (j < 0 || j > 255)
                {
                    Config.dbg((new StringBuilder()).append("Invalid natural.properties line: ").append(s1).toString());
                    continue;
                }

                NaturalProperties naturalproperties = new NaturalProperties(s3);

                if (!naturalproperties.isValid())
                {
                    continue;
                }

                int k = renderengine.getTexture(s4);

                if (k < 0)
                {
                    continue;
                }

                for (; arraylist.size() <= k; arraylist.add(null)) { }

                NaturalProperties anaturalproperties[] = (NaturalProperties[])arraylist.get(k);

                if (anaturalproperties == null)
                {
                    anaturalproperties = new NaturalProperties[256];
                    arraylist.set(k, anaturalproperties);
                }

                anaturalproperties[j] = naturalproperties;
            }

            propertiesByTex = (NaturalProperties[][])arraylist.toArray(new NaturalProperties[arraylist.size()][]);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static NaturalProperties getNaturalProperties(int i, int j)
    {
        if (i < 0)
        {
            return null;
        }

        if (i == 0)
        {
            i = renderEngine.terrainTextureId;
        }

        if (i > propertiesByTex.length)
        {
            return null;
        }

        NaturalProperties anaturalproperties[] = propertiesByTex[i];

        if (anaturalproperties == null)
        {
            return null;
        }

        if (j < 0 || j >= anaturalproperties.length)
        {
            return null;
        }
        else
        {
            NaturalProperties naturalproperties = anaturalproperties[j];
            return naturalproperties;
        }
    }

    private static NaturalProperties[][] makeDefaultProperties()
    {
        if (!renderEngine.texturePack.selectedTexturePack.texturePackFileName.equals("Default"))
        {
            Config.dbg("Texture pack is not default, ignoring default configuration for Natural Textures.");
            return new NaturalProperties[0][];
        }
        else
        {
            Config.dbg("Using default configuration for Natural Textures.");
            NaturalProperties anaturalproperties[] = new NaturalProperties[256];
            anaturalproperties[0] = new NaturalProperties("4F");
            anaturalproperties[1] = new NaturalProperties("2F");
            anaturalproperties[2] = new NaturalProperties("4F");
            anaturalproperties[3] = new NaturalProperties("F");
            anaturalproperties[38] = new NaturalProperties("F");
            anaturalproperties[6] = new NaturalProperties("F");
            anaturalproperties[17] = new NaturalProperties("2F");
            anaturalproperties[18] = new NaturalProperties("4F");
            anaturalproperties[19] = new NaturalProperties("4");
            anaturalproperties[20] = new NaturalProperties("2F");
            anaturalproperties[21] = new NaturalProperties("4F");
            anaturalproperties[32] = new NaturalProperties("2F");
            anaturalproperties[33] = new NaturalProperties("2F");
            anaturalproperties[34] = new NaturalProperties("2F");
            anaturalproperties[50] = new NaturalProperties("2F");
            anaturalproperties[51] = new NaturalProperties("2F");
            anaturalproperties[160] = new NaturalProperties("2F");
            anaturalproperties[37] = new NaturalProperties("4F");
            anaturalproperties[52] = new NaturalProperties("2F");
            anaturalproperties[53] = new NaturalProperties("2F");
            anaturalproperties[196] = new NaturalProperties("2");
            anaturalproperties[197] = new NaturalProperties("2");
            anaturalproperties[66] = new NaturalProperties("4F");
            anaturalproperties[68] = new NaturalProperties("F");
            anaturalproperties[70] = new NaturalProperties("2F");
            anaturalproperties[72] = new NaturalProperties("4F");
            anaturalproperties[77] = new NaturalProperties("F");
            anaturalproperties[78] = new NaturalProperties("4F");
            anaturalproperties[86] = new NaturalProperties("2F");
            anaturalproperties[87] = new NaturalProperties("2F");
            anaturalproperties[103] = new NaturalProperties("4F");
            anaturalproperties[104] = new NaturalProperties("4F");
            anaturalproperties[105] = new NaturalProperties("4");
            anaturalproperties[116] = new NaturalProperties("2F");
            anaturalproperties[117] = new NaturalProperties("F");
            anaturalproperties[132] = new NaturalProperties("2F");
            anaturalproperties[133] = new NaturalProperties("2F");
            anaturalproperties[153] = new NaturalProperties("2F");
            anaturalproperties[175] = new NaturalProperties("4");
            anaturalproperties[176] = new NaturalProperties("4");
            anaturalproperties[208] = new NaturalProperties("4F");
            anaturalproperties[211] = new NaturalProperties("4F");
            anaturalproperties[212] = new NaturalProperties("4F");
            int i = renderEngine.terrainTextureId;
            NaturalProperties anaturalproperties1[][] = new NaturalProperties[i + 1][];
            anaturalproperties1[i] = anaturalproperties;
            return anaturalproperties1;
        }
    }
}
