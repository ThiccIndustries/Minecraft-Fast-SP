package argo.jdom;

final class JsonFieldBuilder
{
    private JsonNodeBuilder key;
    private JsonNodeBuilder valueBuilder;

    private JsonFieldBuilder()
    {
    }

    static JsonFieldBuilder aJsonFieldBuilder()
    {
        return new JsonFieldBuilder();
    }

    JsonFieldBuilder withKey(JsonNodeBuilder par1JsonNodeBuilder)
    {
        key = par1JsonNodeBuilder;
        return this;
    }

    JsonFieldBuilder withValue(JsonNodeBuilder par1JsonNodeBuilder)
    {
        valueBuilder = par1JsonNodeBuilder;
        return this;
    }

    JsonStringNode func_27303_b()
    {
        return (JsonStringNode)key.buildNode();
    }

    JsonNode buildValue()
    {
        return valueBuilder.buildNode();
    }
}
