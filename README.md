# pebble_game

This is a console app version of a pebble game that is played on a board containing rows of dishes. The game can be played either against another human player taking turns on the computer, or against a simple AI. Each row has a pebble 'X' in one dish and a pebble 'O' in another. The first row has 6 dishes, the second row has 7, etc. 
````
| X |   |   |   |   | O |
|   | X |   |   |   |   | O |
| X |   |   |   |   |   | O |   |
| X |   |   |   |   |   |   |   | O |
````

## Gameplay rules
The pebbles start in their home positions, X at the left of each row and O at the right. Players then take turns moving one of the pebbles.
- A player can move a pebble to an empty dish to the left or right.
- If one of their pebbles is in a dish next to an opponenent's pebble, then they can "hop over" the opponent's pebble and send it to its home position, unless:
  - If they hopped into the opponent's home, they can't send it home.
  - If there isn't an empty dish on the other side of the opponent's pebble, they can't hop.

The winner is the first player to get all of their pebbles into the opponent's home positions.

### How to play
When prompted by the console with `Number of rows in game (1-10): ` enter the number of rows you would like in your game. Respond to `AI as 2nd player? ` with `true` or `false`. This determines whether the 'O' pebbles are controlled by a player, or by a simple AI (wchich only picks a random pebble to move left). Each turn, input the number of the row on which you would like to move your pebble, then the direction to move it (L or R).
