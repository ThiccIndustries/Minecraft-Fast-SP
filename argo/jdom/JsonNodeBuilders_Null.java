package argo.jdom;

final class JsonNodeBuilders_Null implements JsonNodeBuilder
{
    JsonNodeBuilders_Null()
    {
    }

    public JsonNode buildNode()
    {
        return JsonNodeFactories.aJsonNull();
    }
}
