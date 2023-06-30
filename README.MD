# Minecraft Fast SP
### About:
A singleplayer mod of Minecraft 1.2.5 with aggressive optimization for slow computers.  
If you're wondering why this is a source mod and not ModLoader or something, its because im dumb and this is easier.
### Requirements:
* Java 1.5.0_22
* 128mb of ram
### Usage:
Launcher use:

Replace minecraft.jar with modded version:  
``[Mac] ~/Library/Application Support/Minecraft/bin``  
``[Windows] %appdata%\.minecraft\bin``  
``[Linux] ~/.minecraft/bin``  

Manual launch (need natives and lwjgl libraries):
Run from command line:  
``java -cp Minecraft.jar:jinput.jar:lwjgl_util.jar:lwjgl.jar -Djava.library.path=natives net.minecraft.client.Minecraft -Xmx128M``
### Changes:
* Worlds are generated before playing with a limited size
* Park entities beyond a certain distance
* Stop random chunk updates beyond a certain distance
* Apply chunk render distance vertically as well

Modifications are licensed under the:
ThiccIndustries do-whatever-you-want-I-dont-care license


