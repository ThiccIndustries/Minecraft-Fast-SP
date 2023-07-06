package argo.jdom;

import java.util.List;

final class JsonNodeSelectors_Element extends LeafFunctor
{
    final int index;

    JsonNodeSelectors_Element(int par1)
    {
        index = par1;
    }

    public boolean matchesNode_(List par1List)
    {
        return par1List.size() > index;
    }

    public String shortForm()
    {
        return Integer.toString(index);
    }

    public JsonNode typeSafeApplyTo_(List par1List)
    {
        return (JsonNode)par1List.get(index);
    }

    public String toString()
    {
        return (new StringBuilder()).append("an element at index [").append(index).append("]").toString();
    }

    public Object typeSafeApplyTo(Object par1Obj)
    {
        return typeSafeApplyTo_((List)par1Obj);
    }

    public boolean matchesNode(Object par1Obj)
    {
        return matchesNode_((List)par1Obj);
    }
}
