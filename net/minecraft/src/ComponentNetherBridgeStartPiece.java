package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeStartPiece extends ComponentNetherBridgeCrossing3
{
    public StructureNetherBridgePieceWeight field_40037_a;
    public List field_40035_b;
    public List field_40036_c;
    public ArrayList field_40034_d;

    public ComponentNetherBridgeStartPiece(Random par1Random, int par2, int par3)
    {
        super(par1Random, par2, par3);
        field_40034_d = new ArrayList();
        field_40035_b = new ArrayList();
        StructureNetherBridgePieceWeight astructurenetherbridgepieceweight[] = StructureNetherBridgePieces.getPrimaryComponents();
        int i = astructurenetherbridgepieceweight.length;

        for (int j = 0; j < i; j++)
        {
            StructureNetherBridgePieceWeight structurenetherbridgepieceweight = astructurenetherbridgepieceweight[j];
            structurenetherbridgepieceweight.field_40698_c = 0;
            field_40035_b.add(structurenetherbridgepieceweight);
        }

        field_40036_c = new ArrayList();
        astructurenetherbridgepieceweight = StructureNetherBridgePieces.getSecondaryComponents();
        i = astructurenetherbridgepieceweight.length;

        for (int k = 0; k < i; k++)
        {
            StructureNetherBridgePieceWeight structurenetherbridgepieceweight1 = astructurenetherbridgepieceweight[k];
            structurenetherbridgepieceweight1.field_40698_c = 0;
            field_40036_c.add(structurenetherbridgepieceweight1);
        }
    }
}
