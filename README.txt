This is an edit for a tutorial, no real work was done.

This is a description of the Wheel Of Fortune Game created by 
Fred Rodd and Michael Ritchhart.


Special Instructions: 
	1 player mode has a timer and a maximum number of "losing"
	moves.
	
	1 and 2 player mode only play for 1 round.
	
	3 player mode plays 2 rounds kicking out the loser from round 1

Interface: 
	Users shouldn't be able to mess up, but basically choose the version 
	of the game you want with the first buttons presented.

	Then once playing your first move has to be either spin the wheel
	or solve the puzzle.

	After that you may click on a consonant to attempt to get it on the board.
	If you have 200 points or more you may buy a vowel BEFORE spinning the wheel
	but if you spin the wheel you must choose a consonant, you will be unable to 
	try to solve the puzzle or choose a vowel.

	Game ends when timer runs out in 1 player mode or when the phrase board is 
	completely filled out.

Applet or application?:
	We only implemented it as an application.  It is a runnable jar file.

Descriptions of any extra features implemented:
	Beyond basic necessary functions for the game we added sounds for the phrase board
	the spinning graphic of our wheel is beyond "necessary" for playing
	We added a special version of "1 player mode."
	We have a whole bunch of categories and phrases to choose 
	from.
If working with a partner, include a description of the separation of work.
	
	Fred Rodd's Primary Classes:
	Player.java
	Wheel.java
	Phrase.java
	Letters.java

	Michael Ritchhart's Primary Classes:
	PhraseGenerator.java
	Solve Puzzle button Functionality
	Intro Graphics
	wheel.png

	Shared Files:
	GameLogicController.java
	WheelOfFortune.java


________________________________________________________________
****************************************************************
**			Known Possible Bugs		      **
****************************************************************
If a WheelText.txt or BobText.txt has too many phrases it will
cause the game to crash on start up

if any of the phrases in those same files have 
non-alphabetical characters then the game may be unsolvable
except for by the "solve puzzle" option where the user can
use the keybaord to enter his or her guesses.  (For example
"Lucy & and boys" would be unsolvable without the "solv puzzle" 
button

The graphics seem to display differently based on what OS you're
using.

Code from 1 player mode may spill over to 2 and 3 player mode
cuasing loss of turns if a player doesn't make a move in 15 seconds.  
Its not a bug its a feature!

****************************************************************



________________________________________________________________
****************************************************************
**			Extra Credit			      **
****************************************************************
This is a list of "extra credit" things we hope you'll give us 
extracredit for.


1) I think our wheel is sufficiently complex for extra credit.
	it actually spins and everything.
2) We have sounds for when letters are displayed.  The little 
	"ding" sound.  Although I really wish we had had time 
	to implement a sound disabler button.
3) We have a whole bunch of categories and phrases to choose 
	from.
4) There are multiple ways to play the game, and 
	a single player version of the game that can actually
	be quite fun.


Although I don't think this merits extra credit, there is a 
	special "Bob Phrases" version of the game.
	This version of the game is just the "single player"
	version of wheel of fortune using phrases that 
	Professor Myers has used in class that I thought were
	funny.  It's not an exhaustive list. 
****************************************************************



________________________________________________________________
****************************************************************
**	Things we wish we had time to implement		      **
****************************************************************
I really wish we had had time to implement serializable and make
I way to save the game state.  That would have been a good skill
to hone.

I wish that our GUI were better looking. 
****************************************************************


________________________________________________________________
****************************************************************
**			   GAME MODES			      **
****************************************************************
The first game is 1 player mode.

In one player mode you only try to solve one phrase.

In one player mode there is are ten failures permitted by the user.
A failure is defined as failing to perform an action
in within a time limit (15 seconds) and can be difficult
as the length of time that the wheel is spinning is variable.

Every successful answer stops the timer and after 
the phrase board is redrawn the timer restarts at 15 seconds

A win is a completion of the board before time runs out
a loss is failing to finish the board by the time the timer 
runs out.
_______________________________________________________________

The second type is 2 player mode.

In two player mode you only try to solve one phrase.

The game is completed by getting all letters on the board.

Whoever has the most points when the board is drawn in wins.

________________________________________________________________

The third type is 3 player mode.  It follows the same rules 
as 2 player mode except there are now 2 rounds.

After the first round the lowest scoring player is removed
Tying losers are removed based on their player number.
Game completes when both rounds are finished.


****************************************************************





