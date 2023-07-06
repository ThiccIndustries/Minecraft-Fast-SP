package argo.jdom;

final class JsonNumberNodeBuilder implements JsonNodeBuilder
{
    private final JsonNode field_27239_a;

    JsonNumberNodeBuilder(String par1Str)
    {
        field_27239_a = JsonNodeFactories.aJsonNumber(par1Str);
    }

    public JsonNode buildNode()
    {
        return field_27239_a;
    }
}
