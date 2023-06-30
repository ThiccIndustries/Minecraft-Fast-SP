package argo.jdom;

final class JsonNodeBuilders_False implements JsonNodeBuilder
{
    JsonNodeBuilders_False()
    {
    }

    public JsonNode buildNode()
    {
        return JsonNodeFactories.aJsonFalse();
    }
}
