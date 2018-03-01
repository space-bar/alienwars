# Alienwars 
[![MIT LICENSE](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/space-bar/alienwars/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/space-bar/alienwars.svg?branch=master)](https://travis-ci.org/space-bar/alienwars) 
[![Coverage Status](https://coveralls.io/repos/github/space-bar/alienwars/badge.svg?branch=master)](https://coveralls.io/github/space-bar/alienwars?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/21f72c06650e817fe8c1/maintainability)](https://codeclimate.com/github/space-bar/alienwars/maintainability)

----
 Alienwars is a CLI-RPG application of an alien invasion, you are expected to defend your planet at all cost.
 The good new is the aliens do not have weapons, the bad new is they out-number you and can inhabit your planet by mere landing on it.
 So Space Fighter, you have an obligation to defend or die.

## Installation
> 
Ensure you have java >=1.8 runtime installed in your environment. You also need to install **[git](https://git-scm.com/downloads)** and **[maven](https://maven.apache.org/download.cgi)** to run the commands for setup.
1.  Clone this repository
```
git clone https://github.com/space-bar/alienwars
```
2.  Goto the directory where the repository is created and run the command;
```
mvn clean install
```        
3. Open a command line terminal on your Mac or PC and run the command below from the location you cloned the repository;

```java
java -jar alienwars-1.0.0.jar
```


##  Control COMMANDS
>
1. ``HOME`` to goto the Home main Menu
2. ``BACK`` to go back to previous display
3. ``SAVE`` to save a game on the save display screen
4. ``STAT`` to view a full statistics report of current game in play
5. ``EXIT`` to terminate the application
6. ``L`` or ``A`` to move left (using multiple letter ``LLL`` or ``AAA`` to move 3 steps left)
7. ``R`` or ``D`` to move right (using multiple letter ``RRR`` or ``DDD`` to move 3 steps right)
8. ``1`` or ``ENTER`` to fire an alien

##  The XP Factor

You will need to get XP faster inorder to level up, there fore you need to understand how points are calculated.
1. ``MOVES:`` you have a max of 2 moves to make a kill, anything more you lose the moves point
2. ``HITS:``  for every hit you make, you definitely get points, yeah basic stuff.
3. ``HEAD SHOTS:``  making a hit is one thing but can you hit the bullseye, an headshot is the center of the spaceship, hit that you get extra points
4. ``HIT TIME INTERVAL:`` how long from the start of the game or from your last hit did you make another hit, time is of the essence, 15sec to make a hit and you get an extra point.

##  Things to know

While you play the game you will need to watch you game statistics:

1. ``LEVEL``           level in which you are currently playing, higher level gets more difficult as you play
2. ``POINTS``          Your XP points shows how well you re performing, check how XP is determined above
3. ``WEAPON ROUNDS``   Your rounds decreases as you shoot, if you exceed your rounds then you lose
4. ``Health``          Number of life of a player

You``LOSE`` the game when the aliens land on your planet or when you round out of weapon rounds or when you have no more health
 
 
 ## License
 
 [MIT](https://github.com/space-bar/alienwars/blob/master/LICENSE) © [Oluwaseun Ogunjimi](). Released under the MIT license. 
