package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectedTextures
{
    private static ConnectedProperties blockProperties[][] = (ConnectedProperties[][])null;
    private static ConnectedProperties terrainProperties[][] = (ConnectedProperties[][])null;
    private static boolean matchingCtmPng = false;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final String propSuffixes[] =
    {
        "", "a", "b", "c", "d", "e", "f", "g", "h", "i",
        "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z"
    };

    public ConnectedTextures()
    {
    }

    public static void update(RenderEngine renderengine)
    {
        blockProperties = (ConnectedProperties[][])null;
        terrainProperties = (ConnectedProperties[][])null;
        matchingCtmPng = false;
        blockProperties = readConnectedProperties("/ctm/block", 256, renderengine, 1);
        terrainProperties = readConnectedProperties("/ctm/terrain", 256, renderengine, 2);
        matchingCtmPng = getMatchingCtmPng(renderengine);
        Config.dbg((new StringBuilder()).append("MatchingCtmPng: ").append(matchingCtmPng).toString());

        if (blockProperties == null && terrainProperties == null && matchingCtmPng)
        {
            Config.dbg("Registering default ConnectedTextures");
            blockProperties = new ConnectedProperties[256][];
            blockProperties[Block.glass.blockID] = new ConnectedProperties[1];
            blockProperties[Block.glass.blockID][0] = makeDefaultProperties("ctm", renderengine);
            blockProperties[Block.bookShelf.blockID] = new ConnectedProperties[1];
            blockProperties[Block.bookShelf.blockID][0] = makeDefaultProperties("horizontal", renderengine);
            terrainProperties = new ConnectedProperties[256][];
            terrainProperties[Block.sandStone.blockIndexInTexture] = new ConnectedProperties[1];
            terrainProperties[Block.sandStone.blockIndexInTexture][0] = makeDefaultProperties("top", renderengine);
        }
    }

    private static ConnectedProperties[][] readConnectedProperties(String s, int i, RenderEngine renderengine, int j)
    {
        ConnectedProperties aconnectedproperties[][] = (ConnectedProperties[][])null;

        for (int k = 0; k < i; k++)
        {
            ArrayList arraylist = new ArrayList();

            for (int l = 0; l < propSuffixes.length; l++)
            {
                String s1 = propSuffixes[l];
                String s2 = (new StringBuilder()).append(s).append(k).append(s1).append(".properties").toString();
                InputStream inputstream = renderengine.texturePack.selectedTexturePack.getResourceAsStream(s2);

                if (inputstream == null)
                {
                    break;
                }

                try
                {
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    Config.dbg((new StringBuilder()).append("Connected texture: ").append(s2).toString());
                    ConnectedProperties connectedproperties = new ConnectedProperties(properties);

                    if (connectedproperties.connect == 0)
                    {
                        connectedproperties.connect = j;
                    }

                    if (connectedproperties.isValid(s2))
                    {
                        connectedproperties.textureId = renderengine.getTexture(connectedproperties.source);
                        arraylist.add(connectedproperties);
                        inputstream.close();
                    }
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }

            if (arraylist.size() <= 0)
            {
                continue;
            }

            if (aconnectedproperties == null)
            {
                aconnectedproperties = new ConnectedProperties[i][0];
            }

            aconnectedproperties[k] = (ConnectedProperties[])arraylist.toArray(new ConnectedProperties[arraylist.size()]);
        }

        return aconnectedproperties;
    }

    public static int getConnectedTexture(IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        if (iblockaccess == null)
        {
            return -1;
        }

        if (terrainProperties != null && Tessellator.instance.defaultTexture && i1 >= 0 && i1 < terrainProperties.length)
        {
            ConnectedProperties aconnectedproperties[] = terrainProperties[i1];

            if (aconnectedproperties != null)
            {
                int k1 = getConnectedTexture(aconnectedproperties, iblockaccess, block, i, j, k, l, i1);

                if (k1 >= 0)
                {
                    return k1;
                }
            }
        }

        if (blockProperties != null)
        {
            int j1 = block.blockID;

            if (j1 >= 0 && j1 < blockProperties.length)
            {
                ConnectedProperties aconnectedproperties1[] = blockProperties[j1];

                if (aconnectedproperties1 != null)
                {
                    int l1 = getConnectedTexture(aconnectedproperties1, iblockaccess, block, i, j, k, l, i1);

                    if (l1 >= 0)
                    {
                        return l1;
                    }
                }
            }
        }

        return -1;
    }

    private static int getConnectedTexture(ConnectedProperties aconnectedproperties[], IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        for (int j1 = 0; j1 < aconnectedproperties.length; j1++)
        {
            ConnectedProperties connectedproperties = aconnectedproperties[j1];

            if (connectedproperties == null)
            {
                continue;
            }

            int k1 = getConnectedTexture(connectedproperties, iblockaccess, block, i, j, k, l, i1);

            if (k1 >= 0)
            {
                return k1;
            }
        }

        return -1;
    }

    private static int getConnectedTexture(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        if (l >= 0 && (1 << l & connectedproperties.faces) == 0)
        {
            return -1;
        }

        if (connectedproperties.metadata != null)
        {
            int ai[] = connectedproperties.metadata;
            int j1 = iblockaccess.getBlockMetadata(i, j, k);
            boolean flag = false;
            int k1 = 0;

            do
            {
                if (k1 >= ai.length)
                {
                    break;
                }

                if (ai[k1] == j1)
                {
                    flag = true;
                    break;
                }

                k1++;
            }
            while (true);

            if (!flag)
            {
                return -1;
            }
        }

        switch (connectedproperties.method)
        {
            case 1:
                return getConnectedTextureCtm(connectedproperties, iblockaccess, block, i, j, k, l, i1);

            case 2:
                return getConnectedTextureHorizontal(connectedproperties, iblockaccess, block, i, j, k, l, i1);

            case 6:
                return getConnectedTextureVertical(connectedproperties, iblockaccess, block, i, j, k, l, i1);

            case 3:
                return getConnectedTextureTop(connectedproperties, iblockaccess, block, i, j, k, l, i1);

            case 4:
                return getConnectedTextureRandom(connectedproperties, i, j, k, l);

            case 5:
                return getConnectedTextureRepeat(connectedproperties, i, j, k, l);
        }

        return -1;
    }

    private static int getConnectedTextureRandom(ConnectedProperties connectedproperties, int i, int j, int k, int l)
    {
        int i1 = (l / connectedproperties.symmetry) * connectedproperties.symmetry;
        int j1 = Config.getRandom(i, j, k, l) & 0x7fffffff;
        int k1 = 0;

        if (connectedproperties.weights == null)
        {
            k1 = j1 % connectedproperties.tiles.length;
        }
        else
        {
            int l1 = j1 % connectedproperties.sumAllWeights;
            int ai[] = connectedproperties.sumWeights;
            int i2 = 0;

            do
            {
                if (i2 >= ai.length)
                {
                    break;
                }

                if (l1 < ai[i2])
                {
                    k1 = i2;
                    break;
                }

                i2++;
            }
            while (true);
        }

        return connectedproperties.textureId * 256 + connectedproperties.tiles[k1];
    }

    private static int getConnectedTextureRepeat(ConnectedProperties connectedproperties, int i, int j, int k, int l)
    {
        int i1 = 0;
        int j1 = 0;

        switch (l)
        {
            case 0:
                i1 = i;
                j1 = k;
                break;

            case 1:
                i1 = i;
                j1 = k;
                break;

            case 2:
                i1 = -i - 1;
                j1 = -j;
                break;

            case 3:
                i1 = i;
                j1 = -j;
                break;

            case 4:
                i1 = k;
                j1 = -j;
                break;

            case 5:
                i1 = -k - 1;
                j1 = -j;
                break;
        }

        i1 %= connectedproperties.width;
        j1 %= connectedproperties.height;

        if (i1 < 0)
        {
            i1 += connectedproperties.width;
        }

        if (j1 < 0)
        {
            j1 += connectedproperties.height;
        }

        int k1 = j1 * connectedproperties.width + i1;
        return connectedproperties.textureId * 256 + connectedproperties.tiles[k1];
    }

    private static int getConnectedTextureCtm(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        boolean aflag[] = new boolean[6];
        int j1 = block.blockID;

        switch (l)
        {
            case 0:
            case 1:
                aflag[0] = isNeighbour(connectedproperties, iblockaccess, i - 1, j, k, j1, l, i1);
                aflag[1] = isNeighbour(connectedproperties, iblockaccess, i + 1, j, k, j1, l, i1);
                aflag[2] = isNeighbour(connectedproperties, iblockaccess, i, j, k + 1, j1, l, i1);
                aflag[3] = isNeighbour(connectedproperties, iblockaccess, i, j, k - 1, j1, l, i1);
                break;

            case 2:
                aflag[0] = isNeighbour(connectedproperties, iblockaccess, i + 1, j, k, j1, l, i1);
                aflag[1] = isNeighbour(connectedproperties, iblockaccess, i - 1, j, k, j1, l, i1);
                aflag[2] = isNeighbour(connectedproperties, iblockaccess, i, j - 1, k, j1, l, i1);
                aflag[3] = isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1);
                break;

            case 3:
                aflag[0] = isNeighbour(connectedproperties, iblockaccess, i - 1, j, k, j1, l, i1);
                aflag[1] = isNeighbour(connectedproperties, iblockaccess, i + 1, j, k, j1, l, i1);
                aflag[2] = isNeighbour(connectedproperties, iblockaccess, i, j - 1, k, j1, l, i1);
                aflag[3] = isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1);
                break;

            case 4:
                aflag[0] = isNeighbour(connectedproperties, iblockaccess, i, j, k - 1, j1, l, i1);
                aflag[1] = isNeighbour(connectedproperties, iblockaccess, i, j, k + 1, j1, l, i1);
                aflag[2] = isNeighbour(connectedproperties, iblockaccess, i, j - 1, k, j1, l, i1);
                aflag[3] = isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1);
                break;

            case 5:
                aflag[0] = isNeighbour(connectedproperties, iblockaccess, i, j, k + 1, j1, l, i1);
                aflag[1] = isNeighbour(connectedproperties, iblockaccess, i, j, k - 1, j1, l, i1);
                aflag[2] = isNeighbour(connectedproperties, iblockaccess, i, j - 1, k, j1, l, i1);
                aflag[3] = isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1);
                break;
        }

        byte byte0 = 0;

        if (aflag[0] & (!aflag[1]) & (!aflag[2]) & (!aflag[3]))
        {
            byte0 = 3;
        }
        else if ((!aflag[0]) & aflag[1] & (!aflag[2]) & (!aflag[3]))
        {
            byte0 = 1;
        }
        else if ((!aflag[0]) & (!aflag[1]) & aflag[2] & (!aflag[3]))
        {
            byte0 = 12;
        }
        else if ((!aflag[0]) & (!aflag[1]) & (!aflag[2]) & aflag[3])
        {
            byte0 = 36;
        }
        else if (aflag[0] & aflag[1] & (!aflag[2]) & (!aflag[3]))
        {
            byte0 = 2;
        }
        else if ((!aflag[0]) & (!aflag[1]) & aflag[2] & aflag[3])
        {
            byte0 = 24;
        }
        else if (aflag[0] & (!aflag[1]) & aflag[2] & (!aflag[3]))
        {
            byte0 = 15;
        }
        else if (aflag[0] & (!aflag[1]) & (!aflag[2]) & aflag[3])
        {
            byte0 = 39;
        }
        else if ((!aflag[0]) & aflag[1] & aflag[2] & (!aflag[3]))
        {
            byte0 = 13;
        }
        else if ((!aflag[0]) & aflag[1] & (!aflag[2]) & aflag[3])
        {
            byte0 = 37;
        }
        else if ((!aflag[0]) & aflag[1] & aflag[2] & aflag[3])
        {
            byte0 = 25;
        }
        else if (aflag[0] & (!aflag[1]) & aflag[2] & aflag[3])
        {
            byte0 = 27;
        }
        else if (aflag[0] & aflag[1] & (!aflag[2]) & aflag[3])
        {
            byte0 = 38;
        }
        else if (aflag[0] & aflag[1] & aflag[2] & (!aflag[3]))
        {
            byte0 = 14;
        }
        else if (aflag[0] & aflag[1] & aflag[2] & aflag[3])
        {
            byte0 = 26;
        }

        if (!Config.isConnectedTexturesFancy())
        {
            return connectedproperties.textureId * 256 + connectedproperties.tiles[byte0];
        }

        boolean aflag1[] = new boolean[6];

        switch (l)
        {
            case 0:
            case 1:
                aflag1[0] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j, k + 1, j1, l, i1);
                aflag1[1] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j, k + 1, j1, l, i1);
                aflag1[2] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j, k - 1, j1, l, i1);
                aflag1[3] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j, k - 1, j1, l, i1);
                break;

            case 2:
                aflag1[0] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j - 1, k, j1, l, i1);
                aflag1[1] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j - 1, k, j1, l, i1);
                aflag1[2] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j + 1, k, j1, l, i1);
                aflag1[3] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j + 1, k, j1, l, i1);
                break;

            case 3:
                aflag1[0] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j - 1, k, j1, l, i1);
                aflag1[1] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j - 1, k, j1, l, i1);
                aflag1[2] = !isNeighbour(connectedproperties, iblockaccess, i + 1, j + 1, k, j1, l, i1);
                aflag1[3] = !isNeighbour(connectedproperties, iblockaccess, i - 1, j + 1, k, j1, l, i1);
                break;

            case 4:
                aflag1[0] = !isNeighbour(connectedproperties, iblockaccess, i, j - 1, k + 1, j1, l, i1);
                aflag1[1] = !isNeighbour(connectedproperties, iblockaccess, i, j - 1, k - 1, j1, l, i1);
                aflag1[2] = !isNeighbour(connectedproperties, iblockaccess, i, j + 1, k + 1, j1, l, i1);
                aflag1[3] = !isNeighbour(connectedproperties, iblockaccess, i, j + 1, k - 1, j1, l, i1);
                break;

            case 5:
                aflag1[0] = !isNeighbour(connectedproperties, iblockaccess, i, j - 1, k - 1, j1, l, i1);
                aflag1[1] = !isNeighbour(connectedproperties, iblockaccess, i, j - 1, k + 1, j1, l, i1);
                aflag1[2] = !isNeighbour(connectedproperties, iblockaccess, i, j + 1, k - 1, j1, l, i1);
                aflag1[3] = !isNeighbour(connectedproperties, iblockaccess, i, j + 1, k + 1, j1, l, i1);
                break;
        }

        if (byte0 == 13 && aflag1[0])
        {
            byte0 = 4;
        }

        if (byte0 == 15 && aflag1[1])
        {
            byte0 = 5;
        }

        if (byte0 == 37 && aflag1[2])
        {
            byte0 = 16;
        }

        if (byte0 == 39 && aflag1[3])
        {
            byte0 = 17;
        }

        if (byte0 == 14 && aflag1[0] && aflag1[1])
        {
            byte0 = 7;
        }

        if (byte0 == 25 && aflag1[0] && aflag1[2])
        {
            byte0 = 6;
        }

        if (byte0 == 27 && aflag1[3] && aflag1[1])
        {
            byte0 = 19;
        }

        if (byte0 == 38 && aflag1[3] && aflag1[2])
        {
            byte0 = 18;
        }

        if (byte0 == 14 && !aflag1[0] && aflag1[1])
        {
            byte0 = 31;
        }

        if (byte0 == 25 && aflag1[0] && !aflag1[2])
        {
            byte0 = 30;
        }

        if (byte0 == 27 && !aflag1[3] && aflag1[1])
        {
            byte0 = 41;
        }

        if (byte0 == 38 && aflag1[3] && !aflag1[2])
        {
            byte0 = 40;
        }

        if (byte0 == 14 && aflag1[0] && !aflag1[1])
        {
            byte0 = 29;
        }

        if (byte0 == 25 && !aflag1[0] && aflag1[2])
        {
            byte0 = 28;
        }

        if (byte0 == 27 && aflag1[3] && !aflag1[1])
        {
            byte0 = 43;
        }

        if (byte0 == 38 && !aflag1[3] && aflag1[2])
        {
            byte0 = 42;
        }

        if (byte0 == 26 && aflag1[0] && aflag1[1] && aflag1[2] && aflag1[3])
        {
            byte0 = 46;
        }

        if (byte0 == 26 && !aflag1[0] && aflag1[1] && aflag1[2] && aflag1[3])
        {
            byte0 = 9;
        }

        if (byte0 == 26 && aflag1[0] && !aflag1[1] && aflag1[2] && aflag1[3])
        {
            byte0 = 21;
        }

        if (byte0 == 26 && aflag1[0] && aflag1[1] && !aflag1[2] && aflag1[3])
        {
            byte0 = 8;
        }

        if (byte0 == 26 && aflag1[0] && aflag1[1] && aflag1[2] && !aflag1[3])
        {
            byte0 = 20;
        }

        if (byte0 == 26 && aflag1[0] && aflag1[1] && !aflag1[2] && !aflag1[3])
        {
            byte0 = 11;
        }

        if (byte0 == 26 && !aflag1[0] && !aflag1[1] && aflag1[2] && aflag1[3])
        {
            byte0 = 22;
        }

        if (byte0 == 26 && !aflag1[0] && aflag1[1] && !aflag1[2] && aflag1[3])
        {
            byte0 = 23;
        }

        if (byte0 == 26 && aflag1[0] && !aflag1[1] && aflag1[2] && !aflag1[3])
        {
            byte0 = 10;
        }

        if (byte0 == 26 && aflag1[0] && !aflag1[1] && !aflag1[2] && aflag1[3])
        {
            byte0 = 34;
        }

        if (byte0 == 26 && !aflag1[0] && aflag1[1] && aflag1[2] && !aflag1[3])
        {
            byte0 = 35;
        }

        if (byte0 == 26 && aflag1[0] && !aflag1[1] && !aflag1[2] && !aflag1[3])
        {
            byte0 = 32;
        }

        if (byte0 == 26 && !aflag1[0] && aflag1[1] && !aflag1[2] && !aflag1[3])
        {
            byte0 = 33;
        }

        if (byte0 == 26 && !aflag1[0] && !aflag1[1] && aflag1[2] && !aflag1[3])
        {
            byte0 = 44;
        }

        if (byte0 == 26 && !aflag1[0] && !aflag1[1] && !aflag1[2] && aflag1[3])
        {
            byte0 = 45;
        }

        return connectedproperties.textureId * 256 + connectedproperties.tiles[byte0];
    }

    private static boolean isNeighbour(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, int i, int j, int k, int l, int i1, int j1)
    {
        int k1 = iblockaccess.getBlockId(i, j, k);

        if (connectedproperties.connect == 2)
        {
            Block block = Block.blocksList[k1];

            if (block == null)
            {
                return false;
            }
            else
            {
                int l1 = block.getBlockTexture(iblockaccess, i, j, k, i1);
                return l1 == j1;
            }
        }
        else
        {
            return k1 == l;
        }
    }

    private static int getConnectedTextureHorizontal(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        if (l == 0 || l == 1)
        {
            return -1;
        }

        boolean flag = false;
        boolean flag1 = false;
        int j1 = block.blockID;

        switch (l)
        {
            case 2:
                flag = isNeighbour(connectedproperties, iblockaccess, i + 1, j, k, j1, l, i1);
                flag1 = isNeighbour(connectedproperties, iblockaccess, i - 1, j, k, j1, l, i1);
                break;

            case 3:
                flag = isNeighbour(connectedproperties, iblockaccess, i - 1, j, k, j1, l, i1);
                flag1 = isNeighbour(connectedproperties, iblockaccess, i + 1, j, k, j1, l, i1);
                break;

            case 4:
                flag = isNeighbour(connectedproperties, iblockaccess, i, j, k - 1, j1, l, i1);
                flag1 = isNeighbour(connectedproperties, iblockaccess, i, j, k + 1, j1, l, i1);
                break;

            case 5:
                flag = isNeighbour(connectedproperties, iblockaccess, i, j, k + 1, j1, l, i1);
                flag1 = isNeighbour(connectedproperties, iblockaccess, i, j, k - 1, j1, l, i1);
                break;
        }

        byte byte0 = 3;

        if (flag)
        {
            if (flag1)
            {
                byte0 = 1;
            }
            else
            {
                byte0 = 2;
            }
        }
        else if (flag1)
        {
            byte0 = 0;
        }
        else
        {
            byte0 = 3;
        }

        return connectedproperties.textureId * 256 + connectedproperties.tiles[byte0];
    }

    private static int getConnectedTextureVertical(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        if (l == 0 || l == 1)
        {
            return -1;
        }

        int j1 = block.blockID;
        boolean flag = isNeighbour(connectedproperties, iblockaccess, i, j - 1, k, j1, l, i1);
        boolean flag1 = isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1);
        byte byte0 = 3;

        if (flag)
        {
            if (flag1)
            {
                byte0 = 1;
            }
            else
            {
                byte0 = 2;
            }
        }
        else if (flag1)
        {
            byte0 = 0;
        }
        else
        {
            byte0 = 3;
        }

        return connectedproperties.textureId * 256 + connectedproperties.tiles[byte0];
    }

    private static int getConnectedTextureTop(ConnectedProperties connectedproperties, IBlockAccess iblockaccess, Block block, int i, int j, int k, int l, int i1)
    {
        if (l == 0 || l == 1)
        {
            return -1;
        }

        int j1 = block.blockID;

        if (isNeighbour(connectedproperties, iblockaccess, i, j + 1, k, j1, l, i1))
        {
            return connectedproperties.textureId * 256 + connectedproperties.tiles[0];
        }
        else
        {
            return -1;
        }
    }

    public static boolean isConnectedGlassPanes()
    {
        return Config.isConnectedTextures() && matchingCtmPng;
    }

    private static boolean getMatchingCtmPng(RenderEngine renderengine)
    {
        java.awt.Dimension dimension = renderengine.getTextureDimensions(renderengine.getTexture("/ctm.png"));

        if (dimension == null)
        {
            return false;
        }

        java.awt.Dimension dimension1 = renderengine.getTextureDimensions(renderengine.getTexture("/terrain.png"));

        if (dimension1 == null)
        {
            return false;
        }
        else
        {
            return dimension.width == dimension1.width && dimension.height == dimension1.height;
        }
    }

    private static ConnectedProperties makeDefaultProperties(String s, RenderEngine renderengine)
    {
        Properties properties = new Properties();
        properties.put("source", "/ctm.png");
        properties.put("method", s);
        ConnectedProperties connectedproperties = new ConnectedProperties(properties);
        connectedproperties.isValid("(default)");
        connectedproperties.textureId = renderengine.getTexture(connectedproperties.source);
        return connectedproperties;
    }
}
