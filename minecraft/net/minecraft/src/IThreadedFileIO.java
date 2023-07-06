package net.minecraft.src;

public interface IThreadedFileIO
{
    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    public abstract boolean writeNextIO();
}
