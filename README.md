# Basic-Game-COMP229-
## Assignment Two

In this assignment you will make (up to) three extensions to the program. We recommend you work from the assignment one solution that is published in the bitbucket repository. 

## Quick Save (50 marks) 

Eventually, this game will have some very hard levels and so you have been asked to implement quick save functionality:  When the user hits the space-bar (i.e. types a space character), the state of the game is stored. When the user types an 'r' character, the last-saved state of the game is restored. Furthermore, you must achieve this using a faithful implementation of the Momento pattern  Your submission should include a file MOMENTO.md that describes how your solution maps to the standard momento pattern. For our purposes, the above linked description or the Gang of Four description count as standard momento patterns.  For full marks in this part, also explain why a momento pattern is not a good solution for general save states in this situation. I.e. if we wanted to save game state between games, momento would not be a good choice - why not? 

## Blocks (20 marks) 

Extend the SAW file so you can specify certain cells as being "blocked". Those cells are displayed as brown with a diagonal pattern. Your marker will add their own SAW file to test if theirs is reflected in the running program.  The lines in the SAW file that described blocked cells should look like  block: (1,2) and will match the regular expression block:\\s*\\((\\d+),\\s*(\\d+)\\).  

## Blocking (10 marks)

Extend your program so characters and players can't move through blocks. If the natural behaviour of a character is to move onto a block it can just stay still. 

## Path Finding (20 marks) 🤔 

Implement better path-finding for characters. They should still have the same behaviour but if there is a block in the way they should try and find a way around that block. When waiting for the player to move, the player should be able to hover over a character and see a representation of the path it is planning to take.
