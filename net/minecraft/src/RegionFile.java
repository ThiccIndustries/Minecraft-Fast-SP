package net.minecraft.src;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.*;

public class RegionFile
{
    private static final byte emptySector[] = new byte[4096];
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int offsets[] = new int[1024];
    private final int chunkTimestamps[] = new int[1024];
    private ArrayList sectorFree;

    /** McRegion sizeDelta */
    private int sizeDelta;
    private long lastModified;

    public RegionFile(File par1File)
    {
        lastModified = 0L;
        fileName = par1File;
        debugln((new StringBuilder()).append("REGION LOAD ").append(fileName).toString());
        sizeDelta = 0;

        try
        {
            if (par1File.exists())
            {
                lastModified = par1File.lastModified();
            }

            dataFile = new RandomAccessFile(par1File, "rw");

            if (dataFile.length() < 4096L)
            {
                for (int i = 0; i < 1024; i++)
                {
                    dataFile.writeInt(0);
                }

                for (int j = 0; j < 1024; j++)
                {
                    dataFile.writeInt(0);
                }

                sizeDelta += 8192;
            }

            if ((dataFile.length() & 4095L) != 0L)
            {
                for (int k = 0; (long)k < (dataFile.length() & 4095L); k++)
                {
                    dataFile.write(0);
                }
            }

            int l = (int)dataFile.length() / 4096;
            sectorFree = new ArrayList(l);

            for (int i1 = 0; i1 < l; i1++)
            {
                sectorFree.add(Boolean.valueOf(true));
            }

            sectorFree.set(0, Boolean.valueOf(false));
            sectorFree.set(1, Boolean.valueOf(false));
            dataFile.seek(0L);

            for (int j1 = 0; j1 < 1024; j1++)
            {
                int l1 = dataFile.readInt();
                offsets[j1] = l1;

                if (l1 == 0 || (l1 >> 8) + (l1 & 0xff) > sectorFree.size())
                {
                    continue;
                }

                for (int j2 = 0; j2 < (l1 & 0xff); j2++)
                {
                    sectorFree.set((l1 >> 8) + j2, Boolean.valueOf(false));
                }
            }

            for (int k1 = 0; k1 < 1024; k1++)
            {
                int i2 = dataFile.readInt();
                chunkTimestamps[k1] = i2;
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private void debug(String s)
    {
    }

    private void debugln(String par1Str)
    {
        debug((new StringBuilder()).append(par1Str).append("\n").toString());
    }

    private void debug(String par1Str, int par2, int par3, String par4Str)
    {
        debug((new StringBuilder()).append("REGION ").append(par1Str).append(" ").append(fileName.getName()).append("[").append(par2).append(",").append(par3).append("] = ").append(par4Str).toString());
    }

    private void debug(String par1Str, int par2, int par3, int par4, String par5Str)
    {
        debug((new StringBuilder()).append("REGION ").append(par1Str).append(" ").append(fileName.getName()).append("[").append(par2).append(",").append(par3).append("] ").append(par4).append("B = ").append(par5Str).toString());
    }

    private void debugln(String par1Str, int par2, int par3, String par4Str)
    {
        debug(par1Str, par2, par3, (new StringBuilder()).append(par4Str).append("\n").toString());
    }

    /**
     * args: x, y - get uncompressed chunk stream from the region file
     */
    public synchronized DataInputStream getChunkDataInputStream(int par1, int par2)
    {
        if (outOfBounds(par1, par2))
        {
            debugln("READ", par1, par2, "out of bounds");
            return null;
        }

        try
        {
            int i = getOffset(par1, par2);

            if (i == 0)
            {
                return null;
            }

            int j = i >> 8;
            int k = i & 0xff;

            if (j + k > sectorFree.size())
            {
                debugln("READ", par1, par2, "invalid sector");
                return null;
            }

            dataFile.seek(j * 4096);
            int l = dataFile.readInt();

            if (l > 4096 * k)
            {
                debugln("READ", par1, par2, (new StringBuilder()).append("invalid length: ").append(l).append(" > 4096 * ").append(k).toString());
                return null;
            }

            if (l <= 0)
            {
                debugln("READ", par1, par2, (new StringBuilder()).append("invalid length: ").append(l).append(" < 1").toString());
                return null;
            }

            byte byte0 = dataFile.readByte();

            if (byte0 == 1)
            {
                byte abyte0[] = new byte[l - 1];
                dataFile.read(abyte0);
                DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte0))));
                return datainputstream;
            }

            if (byte0 == 2)
            {
                byte abyte1[] = new byte[l - 1];
                dataFile.read(abyte1);
                DataInputStream datainputstream1 = new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte1))));
                return datainputstream1;
            }
            else
            {
                debugln("READ", par1, par2, (new StringBuilder()).append("unknown version ").append(byte0).toString());
                return null;
            }
        }
        catch (IOException ioexception)
        {
            debugln("READ", par1, par2, "exception");
        }

        return null;
    }

    /**
     * args: x, z - get an output stream used to write chunk data, data is on disk when the returned stream is closed
     */
    public DataOutputStream getChunkDataOutputStream(int par1, int par2)
    {
        if (outOfBounds(par1, par2))
        {
            return null;
        }
        else
        {
            return new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, par1, par2)));
        }
    }

    /**
     * args: x, z, data, length - write chunk data at (x, z) to disk
     */
    protected synchronized void write(int par1, int par2, byte par3ArrayOfByte[], int par4)
    {
        try
        {
            int i = getOffset(par1, par2);
            int j = i >> 8;
            int i1 = i & 0xff;
            int j1 = (par4 + 5) / 4096 + 1;

            if (j1 >= 256)
            {
                return;
            }

            if (j != 0 && i1 == j1)
            {
                debug("SAVE", par1, par2, par4, "rewrite");
                write(j, par3ArrayOfByte, par4);
            }
            else
            {
                for (int k1 = 0; k1 < i1; k1++)
                {
                    sectorFree.set(j + k1, Boolean.valueOf(true));
                }

                int l1 = sectorFree.indexOf(Boolean.valueOf(true));
                int i2 = 0;

                if (l1 != -1)
                {
                    int j2 = l1;

                    do
                    {
                        if (j2 >= sectorFree.size())
                        {
                            break;
                        }

                        if (i2 != 0)
                        {
                            if (((Boolean)sectorFree.get(j2)).booleanValue())
                            {
                                i2++;
                            }
                            else
                            {
                                i2 = 0;
                            }
                        }
                        else if (((Boolean)sectorFree.get(j2)).booleanValue())
                        {
                            l1 = j2;
                            i2 = 1;
                        }

                        if (i2 >= j1)
                        {
                            break;
                        }

                        j2++;
                    }
                    while (true);
                }

                if (i2 >= j1)
                {
                    debug("SAVE", par1, par2, par4, "reuse");
                    int k = l1;
                    setOffset(par1, par2, k << 8 | j1);

                    for (int k2 = 0; k2 < j1; k2++)
                    {
                        sectorFree.set(k + k2, Boolean.valueOf(false));
                    }

                    write(k, par3ArrayOfByte, par4);
                }
                else
                {
                    debug("SAVE", par1, par2, par4, "grow");
                    dataFile.seek(dataFile.length());
                    int l = sectorFree.size();

                    for (int l2 = 0; l2 < j1; l2++)
                    {
                        dataFile.write(emptySector);
                        sectorFree.add(Boolean.valueOf(false));
                    }

                    sizeDelta += 4096 * j1;
                    write(l, par3ArrayOfByte, par4);
                    setOffset(par1, par2, l << 8 | j1);
                }
            }

            setChunkTimestamp(par1, par2, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    /**
     * args: sectorNumber, data, length - write the chunk data to this RegionFile
     */
    private void write(int par1, byte par2ArrayOfByte[], int par3) throws IOException
    {
        debugln((new StringBuilder()).append(" ").append(par1).toString());
        dataFile.seek(par1 * 4096);
        dataFile.writeInt(par3 + 1);
        dataFile.writeByte(2);
        dataFile.write(par2ArrayOfByte, 0, par3);
    }

    /**
     * args: x, z - check region bounds
     */
    private boolean outOfBounds(int par1, int par2)
    {
        return par1 < 0 || par1 >= 32 || par2 < 0 || par2 >= 32;
    }

    /**
     * args: x, y - get chunk's offset in region file
     */
    private int getOffset(int par1, int par2)
    {
        return offsets[par1 + par2 * 32];
    }

    /**
     * args: x, z, - true if chunk has been saved / converted
     */
    public boolean isChunkSaved(int par1, int par2)
    {
        return getOffset(par1, par2) != 0;
    }

    /**
     * args: x, z, offset - sets the chunk's offset in the region file
     */
    private void setOffset(int par1, int par2, int par3) throws IOException
    {
        offsets[par1 + par2 * 32] = par3;
        dataFile.seek((par1 + par2 * 32) * 4);
        dataFile.writeInt(par3);
    }

    /**
     * args: x, z, timestamp - sets the chunk's write timestamp
     */
    private void setChunkTimestamp(int par1, int par2, int par3) throws IOException
    {
        chunkTimestamps[par1 + par2 * 32] = par3;
        dataFile.seek(4096 + (par1 + par2 * 32) * 4);
        dataFile.writeInt(par3);
    }

    /**
     * close this RegionFile and prevent further writes
     */
    public void close() throws IOException
    {
        dataFile.close();
    }
}
