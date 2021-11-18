# CS611-Legends

Name
-------------------------------------------------------------------------------------------------
Haoyu Zhang
U81614811


Files
-------------------------------------------------------------------------------------------------
Armor.java - Store the information of armor and be computed during fight.
Cell.java - Single cell in the map, the super class of CommonSpace, Inaccessible and Market.
CommonSpace.java -  A subclass of cell, fight will happen in the common space under a probability.
Dragon.java - A subclass of monster, has a higher damage.
Equipment.java - All things heroes can be equipped with and use are thought as equipment.
Exoskeleton.java - A subclass of monster, has increased defense.
Fight.java - Control the process of a fight between heroes and monsters.
FireSpell.java - A subclass of spell, it will reduce the defense of monsters.
Hero.java - Store the information of hero in the player's team, all things they have and what they can do.
IceSpell.java - A subclass of spell, it will reduce the damage of monsters.
Inaccessible.java - A subclass of cell, it represents the cell can't be accessed by heroes.
Inventory.java - Store all things in hero's inventory.
Legends.java - Control the process of the game, initiate and proceed the game.
LightningSpell.java - A subclass of spell, it will reduce the dodge chance of monsters.
Main.java - Main function to run the programme.
Map.java - Store information of each cell in the map and heroes team can access on the map.
Market.java - A subclass of cell, heroes can access a market and buy or sell items.
Monster.java - The enemy that heroes will encounter and fight against.
Paladin.java - A type of heroes, favored on the strength and dexterity.
Player.java - The player in this game, store the heroes team and what player can do in the game.
Potion.java - Potions consumed to increase some attributes of heroes.
RPGGame.java - The abstract class of all process the RPG game will proceed.
Sorcerer.java - A type of heroes, flavored on the dexterity and agility.
Spell.java - Heroes can have all kinds of spells, and spells can cause some damage and extra effect to enemy.
Spirit.java - A subclass of monsters, has a higher dodge chance.
Utils.java - Tools to obtain a list of random number and scan text files, which will be used sometimes.
Warrior.java - A type of heroes, favored on the strength and agility.
Weapon.java - Store the damage and other information of weapon, used to attack a monster.


Notes:
-------------------------------------------------------------------------------------------------
1. Files to be parsed should be stored in ConfigFiles, for parser class to read class
2. Bonus Done:
Add some colored output:
1) The signs representing Market, Inaccessible and heroes team on the map are colored.
2) The name of heroes and monster are colored.

3. Things instructions to note:
I'm coding my project in Mac.


How to run:
-------------------------------------------------------------------------------------------------
1. Navigate to the directory after downloading the project
2. Run the following instructions on command line:
	javac *.java
	java Main.java