package argo.jdom;

abstract class LeafFunctor implements Functor
{
    LeafFunctor()
    {
    }

    public final Object applyTo(Object par1Obj)
    {
        if (!matchesNode(par1Obj))
        {
            throw JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27322_a(this);
        }
        else
        {
            return typeSafeApplyTo(par1Obj);
        }
    }

    protected abstract Object typeSafeApplyTo(Object obj);
}
