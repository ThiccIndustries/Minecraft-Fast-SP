package net.minecraft.src;

public class StructureVillagePieceWeight
{
    /** The Class object for the represantation of this village piece. */
    public Class villagePieceClass;
    public final int villagePieceWeight;
    public int villagePiecesSpawned;
    public int villagePiecesLimit;

    public StructureVillagePieceWeight(Class par1Class, int par2, int par3)
    {
        villagePieceClass = par1Class;
        villagePieceWeight = par2;
        villagePiecesLimit = par3;
    }

    public boolean canSpawnMoreVillagePiecesOfType(int par1)
    {
        return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
    }

    public boolean canSpawnMoreVillagePieces()
    {
        return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
    }
}
