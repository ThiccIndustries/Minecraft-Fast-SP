package argo.jdom;

final class JsonNodeBuilders_True implements JsonNodeBuilder
{
    JsonNodeBuilders_True()
    {
    }

    public JsonNode buildNode()
    {
        return JsonNodeFactories.aJsonTrue();
    }
}
