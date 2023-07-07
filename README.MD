# Minecraft Fast SP

### 1.0 release is still in development. Version 0.3 is a minecraft.jar replacement only. ###
### About:
A singleplayer mod of Minecraft 1.2.5 with optimizations for slow computers.  
Mainly created/Optimized for PowerPC Macintosh Computers, but should work on any computer that struggles with singleplayer.  
If you're wondering why this is a source mod and not ModLoader or something, its because im dumb and this is easier.
Code Obfuscation is evil anyways.
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


