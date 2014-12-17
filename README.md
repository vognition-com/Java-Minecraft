Java-Minecraft
==============
Ever wanted to have voice control in Minecraft, Yes? Well it's been done right here. Now you can use Vognition's powerful voice intend parsing in your own Minecraft mod. You dont have to worrie about mis recognition from standard Speach to Text engines, as Vognition parses the speach into intent, aka it does all the heavy lifing. So we can add to our mods things like Turning on and off the lights with just a couple lines of code.

Installation Instructions
==============
For Developers:

Requirements
*Minecraft Forge Sorce (1.7.10)
*Gradle
*Eclipse or Intellij
*Java JDK (1.6+)

Note owning Minecraft is not required, as Minecraft Forge contains the Minecraft Source code

Step 1: Follow MrrGingerNinjas guide to installing and setting up your Minecraft Forge Enviornment http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571599-1-7-x-modding-with-forge-1-jdk-eclipse-forge-and

Step 2: Clone this repository into the Tutorial file in the guide made above and rename it to whatever you would like your mod to be called (Guide on Cloning repositories https://help.github.com/articles/fork-a-repo/)

Step 3: While still in your command prompt enter the Java-Minecraft directory move all the file up a directory ("mv . ..") this can also be done in file explorer. Then while in your renamed Tutorial folder run "gradle"

Step 4: Compile and run using your modified compile script made in the tutorial from Step 1

For Users:

Requirements
*Purchased Copy of Minecraft (Minecraft.com/downloads)

To add the mod, put the Voice Control Mod-development-v*.jar file in the Java-Minecraft/lib directory to:
*Windows: %appdata%/.minecraft/mods
*Unix: ~/Library/Application Data/Minecraft/mods 
