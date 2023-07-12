# Minecraft Fast SP

### About:
A singleplayer mod of Minecraft 1.2.5 with optimizations for slow computers.  
Mainly created/Optimized for PowerPC Macintosh Computers, but should work on any computer that struggles with singleplayer.  
This is a standalone version of the game, and won't work with any other existing mods.
### Requirements:
* Java 1.5.0_22
* 256mb of ram
### Usage:
Download launcher from Releases tab  
  
Manual installation:  
Install game using launcher of choice  
Extract minecraft.jar from latest Update Package from Releases tab  
Replace minecraft.jar in bin folder  
Run from command line:  
``java -cp Minecraft.jar:jinput.jar:lwjgl_util.jar:lwjgl.jar -Djava.library.path=natives net.minecraft.client.Minecraft -Xmx512M``

### Updating:
Download Update Package from Releases tab  
Open launcher  
select "Settings / Update"  
select "Install Package"  
Navigate to, and select downloaded update  

### Changes:
* Worlds are generated before playing with a limited size
* Park entities beyond a certain distance
* Stop random chunk updates beyond a certain distance
* Apply chunk render distance vertically as well
* Preload all textures into VRAM

Modifications are licensed under the:
ThiccIndustries do-whatever-you-want-I-dont-care license


