package net.minecraft.src;

public class UnexpectedThrowable
{
    /** A description of the error that has occurred. */
    public final String description;

    /** The Throwable object that was thrown. */
    public final Throwable exception;

    public UnexpectedThrowable(String par1Str, Throwable par2Throwable)
    {
        description = par1Str;
        exception = par2Throwable;
    }
}
