package net.minecraft.src;

public interface IProgressUpdate
{
    /**
     * Shows the 'Saving level' string.
     */
    public abstract void displaySavingString(String s);

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public abstract void displayLoadingString(String s);

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public abstract void setLoadingProgress(int i);
}
