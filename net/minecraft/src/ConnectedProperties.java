package net.minecraft.src;

import java.util.*;

public class ConnectedProperties
{
    public int method;
    public String source;
    public int tiles[];
    public int connect;
    public int faces;
    public int metadata[];
    public int weights[];
    public int symmetry;
    public int width;
    public int height;
    public int sumWeights[];
    public int sumAllWeights;
    public int textureId;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int FACE_NONE = 0;
    public static final int FACE_BOTTOM = 1;
    public static final int FACE_TOP = 2;
    public static final int FACE_EAST = 4;
    public static final int FACE_WEST = 8;
    public static final int FACE_NORTH = 16;
    public static final int FACE_SOUTH = 32;
    public static final int FACE_SIDES = 60;
    public static final int FACE_ALL = 63;
    public static final int SYMMETRY_NONE = 1;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int SYMMETRY_ALL = 6;

    public ConnectedProperties(Properties properties)
    {
        method = 0;
        source = null;
        tiles = null;
        connect = 0;
        faces = 63;
        metadata = null;
        weights = null;
        symmetry = 1;
        width = 0;
        height = 0;
        sumWeights = null;
        sumAllWeights = 0;
        textureId = -1;
        method = parseMethod(properties.getProperty("method"));
        source = properties.getProperty("source");
        tiles = parseInts(properties.getProperty("tiles"));
        connect = parseConnect(properties.getProperty("connect"));
        faces = parseFaces(properties.getProperty("faces"));
        metadata = parseInts(properties.getProperty("metadata"));
        weights = parseInts(properties.getProperty("weights"));
        symmetry = parseSymmetry(properties.getProperty("symmetry"));
        width = parseInt(properties.getProperty("width"));
        height = parseInt(properties.getProperty("height"));
    }

    private int parseInt(String s)
    {
        if (s == null)
        {
            return -1;
        }

        int i = Config.parseInt(s, -1);

        if (i < 0)
        {
            Config.dbg((new StringBuilder()).append("Invalid number: ").append(s).toString());
        }

        return i;
    }

    private int parseSymmetry(String s)
    {
        if (s == null)
        {
            return 1;
        }

        if (s.equals("opposite"))
        {
            return 2;
        }

        if (s.equals("all"))
        {
            return 6;
        }
        else
        {
            Config.dbg((new StringBuilder()).append("Unknown symmetry: ").append(s).toString());
            return 1;
        }
    }

    private int parseFaces(String s)
    {
        if (s == null)
        {
            return 63;
        }

        String as[] = Config.tokenize(s, " ,");
        int i = 0;

        for (int j = 0; j < as.length; j++)
        {
            String s1 = as[j];
            int k = parseFace(s1);
            i |= k;
        }

        return i;
    }

    private int parseFace(String s)
    {
        if (s.equals("bottom"))
        {
            return 1;
        }

        if (s.equals("top"))
        {
            return 2;
        }

        if (s.equals("north"))
        {
            return 4;
        }

        if (s.equals("south"))
        {
            return 8;
        }

        if (s.equals("east"))
        {
            return 32;
        }

        if (s.equals("west"))
        {
            return 16;
        }

        if (s.equals("sides"))
        {
            return 60;
        }

        if (s.equals("all"))
        {
            return 63;
        }
        else
        {
            Config.dbg((new StringBuilder()).append("Unknown face: ").append(s).toString());
            return 0;
        }
    }

    private int parseConnect(String s)
    {
        if (s == null)
        {
            return 0;
        }

        if (s.equals("block"))
        {
            return 1;
        }

        if (s.equals("tile"))
        {
            return 2;
        }
        else
        {
            Config.dbg((new StringBuilder()).append("Unknown connect: ").append(s).toString());
            return 0;
        }
    }

    private int[] parseInts(String s)
    {
        if (s == null)
        {
            return null;
        }

        ArrayList arraylist = new ArrayList();
        String as[] = Config.tokenize(s, " ,");

        for (int i = 0; i < as.length; i++)
        {
            String s1 = as[i];

            if (s1.contains("-"))
            {
                String as1[] = Config.tokenize(s1, "-");

                if (as1.length != 2)
                {
                    Config.dbg((new StringBuilder()).append("Invalid interval: ").append(s1).append(", when parsing: ").append(s).toString());
                    continue;
                }

                int l = Config.parseInt(as1[0], -1);
                int i1 = Config.parseInt(as1[1], -1);

                if (l < 0 || i1 < 0 || l > i1)
                {
                    Config.dbg((new StringBuilder()).append("Invalid interval: ").append(s1).append(", when parsing: ").append(s).toString());
                    continue;
                }

                for (int j1 = l; j1 <= i1; j1++)
                {
                    arraylist.add(Integer.valueOf(j1));
                }

                continue;
            }

            int k = Config.parseInt(s1, -1);

            if (k < 0)
            {
                Config.dbg((new StringBuilder()).append("Invalid number: ").append(s1).append(", when parsing: ").append(s).toString());
            }
            else
            {
                arraylist.add(Integer.valueOf(k));
            }
        }

        int ai[] = new int[arraylist.size()];

        for (int j = 0; j < ai.length; j++)
        {
            ai[j] = ((Integer)arraylist.get(j)).intValue();
        }

        return ai;
    }

    private int parseMethod(String s)
    {
        if (s == null)
        {
            return 1;
        }

        if (s.equals("ctm"))
        {
            return 1;
        }

        if (s.equals("horizontal"))
        {
            return 2;
        }

        if (s.equals("vertical"))
        {
            return 6;
        }

        if (s.equals("top"))
        {
            return 3;
        }

        if (s.equals("random"))
        {
            return 4;
        }

        if (s.equals("repeat"))
        {
            return 5;
        }
        else
        {
            Config.dbg((new StringBuilder()).append("Unknown method: ").append(s).toString());
            return 0;
        }
    }

    public boolean isValid(String s)
    {
        if (source == null)
        {
            Config.dbg((new StringBuilder()).append("No source texture: ").append(s).toString());
            return false;
        }

        if (method == 0)
        {
            Config.dbg((new StringBuilder()).append("No method: ").append(s).toString());
            return false;
        }

        if (tiles != null)
        {
            for (int i = 0; i < tiles.length; i++)
            {
                int j = tiles[i];

                if (j < 0 || j > 255)
                {
                    Config.dbg((new StringBuilder()).append("Invalid tile: ").append(j).append(", in ").append(s).toString());
                    return false;
                }
            }
        }

        switch (method)
        {
            case 1:
                return isValidCtm(s);

            case 2:
                return isValidHorizontal(s);

            case 6:
                return isValidVertical(s);

            case 3:
                return isValidTop(s);

            case 4:
                return isValidRandom(s);

            case 5:
                return isValidRepeat(s);
        }

        Config.dbg((new StringBuilder()).append("Unknown method: ").append(s).toString());
        return false;
    }

    private boolean isValidCtm(String s)
    {
        if (tiles == null)
        {
            tiles = parseInts("0-11 16-27 32-43 48-59");
        }

        if (tiles.length != 48)
        {
            Config.dbg((new StringBuilder()).append("Invalid tiles, must be exactly 48: ").append(s).toString());
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidHorizontal(String s)
    {
        if (tiles == null)
        {
            tiles = parseInts("12-15");
        }

        if (tiles.length != 4)
        {
            Config.dbg((new StringBuilder()).append("Invalid tiles, must be exactly 4: ").append(s).toString());
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidVertical(String s)
    {
        if (tiles == null)
        {
            Config.dbg((new StringBuilder()).append("No tiles defined for vertical: ").append(s).toString());
            return false;
        }

        if (tiles.length != 4)
        {
            Config.dbg((new StringBuilder()).append("Invalid tiles, must be exactly 4: ").append(s).toString());
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidRandom(String s)
    {
        if (tiles == null)
        {
            Config.dbg((new StringBuilder()).append("Tiles not defined: ").append(s).toString());
            return false;
        }

        if (weights != null && weights.length != tiles.length)
        {
            Config.dbg((new StringBuilder()).append("Number of weights must equal the number of tiles: ").append(s).toString());
            return false;
        }

        if (weights != null)
        {
            sumWeights = new int[weights.length];
            int i = 0;

            for (int j = 0; j < weights.length; j++)
            {
                i += weights[j];
                sumWeights[j] = i;
            }

            sumAllWeights = i;
        }

        return true;
    }

    private boolean isValidRepeat(String s)
    {
        if (tiles == null)
        {
            Config.dbg((new StringBuilder()).append("Tiles not defined: ").append(s).toString());
            return false;
        }

        if (width < 0 || width > 16)
        {
            Config.dbg((new StringBuilder()).append("Invalid width: ").append(s).toString());
            return false;
        }

        if (height < 0 || height > 16)
        {
            Config.dbg((new StringBuilder()).append("Invalid height: ").append(s).toString());
            return false;
        }

        if (tiles.length != width * height)
        {
            Config.dbg((new StringBuilder()).append("Number of tiles does not equal width x height: ").append(s).toString());
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidTop(String s)
    {
        if (tiles == null)
        {
            tiles = parseInts("66");
        }

        if (tiles.length != 1)
        {
            Config.dbg((new StringBuilder()).append("Invalid tiles, must be exactly 1: ").append(s).toString());
            return false;
        }
        else
        {
            return true;
        }
    }
}
