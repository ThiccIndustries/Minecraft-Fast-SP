package argo.jdom;

interface Functor
{
    public abstract boolean matchesNode(Object obj);

    public abstract Object applyTo(Object obj);

    public abstract String shortForm();
}
