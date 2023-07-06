package net.minecraft.src;

import java.util.*;

abstract class ComponentNetherBridgePiece extends StructureComponent
{
    protected ComponentNetherBridgePiece(int par1)
    {
        super(par1);
    }

    private int getTotalWeight(List par1List)
    {
        boolean flag = false;
        int i = 0;

        for (Iterator iterator = par1List.iterator(); iterator.hasNext();)
        {
            StructureNetherBridgePieceWeight structurenetherbridgepieceweight = (StructureNetherBridgePieceWeight)iterator.next();

            if (structurenetherbridgepieceweight.field_40695_d > 0 && structurenetherbridgepieceweight.field_40698_c < structurenetherbridgepieceweight.field_40695_d)
            {
                flag = true;
            }

            i += structurenetherbridgepieceweight.field_40697_b;
        }

        return flag ? i : -1;
    }

    private ComponentNetherBridgePiece getNextComponent(ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, List par2List, List par3List, Random par4Random, int par5, int par6, int par7, int par8, int par9)
    {
        int var10 = this.getTotalWeight(par2List);
        boolean var11 = var10 > 0 && par9 <= 30;
        int var12 = 0;

        while (var12 < 5 && var11)
        {
            ++var12;
            int var13 = par4Random.nextInt(var10);
            Iterator var14 = par2List.iterator();

            while (var14.hasNext())
            {
                StructureNetherBridgePieceWeight var15 = (StructureNetherBridgePieceWeight)var14.next();
                var13 -= var15.field_40697_b;

                if (var13 < 0)
                {
                    if (!var15.func_40693_a(par9) || var15 == par1ComponentNetherBridgeStartPiece.field_40037_a && !var15.field_40696_e)
                    {
                        break;
                    }

                    ComponentNetherBridgePiece var16 = StructureNetherBridgePieces.createNextComponent(var15, par3List, par4Random, par5, par6, par7, par8, par9);

                    if (var16 != null)
                    {
                        ++var15.field_40698_c;
                        par1ComponentNetherBridgeStartPiece.field_40037_a = var15;

                        if (!var15.func_40694_a())
                        {
                            par2List.remove(var15);
                        }

                        return var16;
                    }
                }
            }
        }

        ComponentNetherBridgeEnd var17 = ComponentNetherBridgeEnd.func_40023_a(par3List, par4Random, par5, par6, par7, par8, par9);
        return var17;
    }

    /**
     * Finds a random component to tack on to the bridge. Or builds the end.
     */
    private StructureComponent getNextComponent(ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8, boolean par9)
    {
        if (Math.abs(par4 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minX) > 112 || Math.abs(par6 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minZ) > 112)
        {
            ComponentNetherBridgeEnd componentnetherbridgeend = ComponentNetherBridgeEnd.func_40023_a(par2List, par3Random, par4, par5, par6, par7, par8);
            return componentnetherbridgeend;
        }

        List list = par1ComponentNetherBridgeStartPiece.field_40035_b;

        if (par9)
        {
            list = par1ComponentNetherBridgeStartPiece.field_40036_c;
        }

        ComponentNetherBridgePiece componentnetherbridgepiece = getNextComponent(par1ComponentNetherBridgeStartPiece, list, par2List, par3Random, par4, par5, par6, par7, par8 + 1);

        if (componentnetherbridgepiece != null)
        {
            par2List.add(componentnetherbridgepiece);
            par1ComponentNetherBridgeStartPiece.field_40034_d.add(componentnetherbridgepiece);
        }

        return componentnetherbridgepiece;
    }

    /**
     * Gets the next component in any cardinal direction
     */
    protected StructureComponent getNextComponentNormal(ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (coordBaseMode)
        {
            case 2:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.minZ - 1, coordBaseMode, getComponentType(), par6);

            case 0:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.maxZ + 1, coordBaseMode, getComponentType(), par6);

            case 1:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType(), par6);

            case 3:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType(), par6);
        }

        return null;
    }

    /**
     * Gets the next component in the +/- X direction
     */
    protected StructureComponent getNextComponentX(ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (coordBaseMode)
        {
            case 2:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType(), par6);

            case 0:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType(), par6);

            case 1:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType(), par6);

            case 3:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType(), par6);
        }

        return null;
    }

    /**
     * Gets the next component in the +/- Z direction
     */
    protected StructureComponent getNextComponentZ(ComponentNetherBridgeStartPiece par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (coordBaseMode)
        {
            case 2:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType(), par6);

            case 0:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType(), par6);

            case 1:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType(), par6);

            case 3:
                return getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType(), par6);
        }

        return null;
    }

    /**
     * Checks if the bounding box's minY is > 10
     */
    protected static boolean isAboveGround(StructureBoundingBox par0StructureBoundingBox)
    {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
    }
}
