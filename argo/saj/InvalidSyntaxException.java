package argo.saj;

public final class InvalidSyntaxException extends Exception
{
    private final int column;
    private final int row;

    InvalidSyntaxException(String par1Str, ThingWithPosition par2ThingWithPosition)
    {
        super((new StringBuilder()).append("At line ").append(par2ThingWithPosition.getRow()).append(", column ").append(par2ThingWithPosition.getColumn()).append(":  ").append(par1Str).toString());
        column = par2ThingWithPosition.getColumn();
        row = par2ThingWithPosition.getRow();
    }

    InvalidSyntaxException(String par1Str, Throwable par2Throwable, ThingWithPosition par3ThingWithPosition)
    {
        super((new StringBuilder()).append("At line ").append(par3ThingWithPosition.getRow()).append(", column ").append(par3ThingWithPosition.getColumn()).append(":  ").append(par1Str).toString(), par2Throwable);
        column = par3ThingWithPosition.getColumn();
        row = par3ThingWithPosition.getRow();
    }
}
