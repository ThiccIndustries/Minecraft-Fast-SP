package net.minecraft.src;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityAITasks
{
    private ArrayList tasksToDo;

    /** Tasks currently being executed */
    private ArrayList executingTasks;

    public EntityAITasks()
    {
        tasksToDo = new ArrayList();
        executingTasks = new ArrayList();
    }

    public void addTask(int par1, EntityAIBase par2EntityAIBase)
    {
        tasksToDo.add(new EntityAITaskEntry(this, par1, par2EntityAIBase));
    }

    public void onUpdateTasks()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = tasksToDo.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityAITaskEntry entityaitaskentry = (EntityAITaskEntry)iterator.next();
            boolean flag1 = executingTasks.contains(entityaitaskentry);

            if (flag1)
            {
                if (func_46116_a(entityaitaskentry) && entityaitaskentry.action.continueExecuting())
                {
                    continue;
                }

                entityaitaskentry.action.resetTask();
                executingTasks.remove(entityaitaskentry);
            }

            if (func_46116_a(entityaitaskentry) && entityaitaskentry.action.shouldExecute())
            {
                arraylist.add(entityaitaskentry);
                executingTasks.add(entityaitaskentry);
            }
        }
        while (true);

        boolean flag = false;

        if (flag && arraylist.size() > 0)
        {
            System.out.println("Starting: ");
        }

        EntityAITaskEntry entityaitaskentry1;

        for (Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); entityaitaskentry1.action.startExecuting())
        {
            entityaitaskentry1 = (EntityAITaskEntry)iterator1.next();

            if (flag)
            {
                System.out.println((new StringBuilder()).append(entityaitaskentry1.action.toString()).append(", ").toString());
            }
        }

        if (flag && executingTasks.size() > 0)
        {
            System.out.println("Running: ");
        }

        EntityAITaskEntry entityaitaskentry2;

        for (Iterator iterator2 = executingTasks.iterator(); iterator2.hasNext(); entityaitaskentry2.action.updateTask())
        {
            entityaitaskentry2 = (EntityAITaskEntry)iterator2.next();

            if (flag)
            {
                System.out.println(entityaitaskentry2.action.toString());
            }
        }
    }

    private boolean func_46116_a(EntityAITaskEntry par1EntityAITaskEntry)
    {
        label0:
        {
            Iterator iterator = tasksToDo.iterator();
            EntityAITaskEntry entityaitaskentry;
            label1:

            do
            {
                do
                {
                    do
                    {
                        if (!iterator.hasNext())
                        {
                            break label0;
                        }

                        entityaitaskentry = (EntityAITaskEntry)iterator.next();
                    }
                    while (entityaitaskentry == par1EntityAITaskEntry);

                    if (par1EntityAITaskEntry.priority < entityaitaskentry.priority)
                    {
                        continue label1;
                    }
                }
                while (!executingTasks.contains(entityaitaskentry) || areTasksCompatible(par1EntityAITaskEntry, entityaitaskentry));

                return false;
            }
            while (!executingTasks.contains(entityaitaskentry) || entityaitaskentry.action.isContinuous());

            return false;
        }
        return true;
    }

    /**
     * Returns whether two EntityAITaskEntries can be executed concurrently
     */
    private boolean areTasksCompatible(EntityAITaskEntry par1EntityAITaskEntry, EntityAITaskEntry par2EntityAITaskEntry)
    {
        return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0;
    }
}
