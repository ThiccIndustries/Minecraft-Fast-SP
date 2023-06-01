package argo.jdom;

import java.util.Map;

final class JsonNodeSelectors_Field extends LeafFunctor
{
    final JsonStringNode field_27066_a;

    JsonNodeSelectors_Field(JsonStringNode par1JsonStringNode)
    {
        field_27066_a = par1JsonStringNode;
    }

    public boolean func_27065_a(Map par1Map)
    {
        return par1Map.containsKey(field_27066_a);
    }

    public String shortForm()
    {
        return (new StringBuilder()).append("\"").append(field_27066_a.getText()).append("\"").toString();
    }

    public JsonNode func_27064_b(Map par1Map)
    {
        return (JsonNode)par1Map.get(field_27066_a);
    }

    public String toString()
    {
        return (new StringBuilder()).append("a field called [\"").append(field_27066_a.getText()).append("\"]").toString();
    }

    public Object typeSafeApplyTo(Object par1Obj)
    {
        return func_27064_b((Map)par1Obj);
    }

    public boolean matchesNode(Object par1Obj)
    {
        return func_27065_a((Map)par1Obj);
    }
}
