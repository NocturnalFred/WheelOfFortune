package wheelOfFortune;

//This is designed around a single player, if we want multiple players we'll
//have to create a player class and add an array here
//and move "points" and "name" to inside the player classes in the array

/* note, to start a new round call
 * WheelOfFortune.createGame(3, "Phrase", "Stop in the name of love");
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameLogicController extends JPanel implements WheelLogicInterface, ActionListener {

	private Wheel wheel;
	private Phrase phrase;
	private Letters letters;
	private JLabel Directions;
	private JLabel currentPlayerName;
	private JLabel soloPlayerCountdown;
	private Player player;
	private Player[] players;
	private int isConsonant;		//for logic avoids accidentally adding points when buying a vowel.
	private int currentPlayer;
	private int roundCountDown;
	private int turns;
	private int timingCounter;
	private Random wheelSpinValue;
	private String spinText;
	private String vowelText;
	private phraseGenerator thePhrase;

	private Timer timing;
	
	public GameLogicController (){
		super();
		setPreferredSize(new Dimension(200, 60));
	}
	
	
	public void linkComponents(Wheel _Wheel, Phrase _Phrase, Letters _Letters, Player[] _players, Player _player, phraseGenerator _thePhrase, int _roundCountDown){
		wheel = _Wheel;
		phrase = _Phrase;
		letters = _Letters;
		players = _players;
		player = _player;
		thePhrase = _thePhrase;
		turns = 10;
		timingCounter = 15;
		roundCountDown = _roundCountDown;
		spinText = "You may spin the wheel";
		vowelText = "or buy a vowel";
		isConsonant = 1;
		currentPlayer = 0;		
		
		Directions = new JLabel("Spin the wheel");
		soloPlayerCountdown = new JLabel("Turns left: 10 --- Time Until Current Turn Lost: 15");
		wheelSpinValue = new Random();
		currentPlayerName = new JLabel();
		timing = new Timer(1000, this);
		
		for (Player play: players)
		{
			if (play.getPoints() > player.getPoints())
			{
				player = play;
			}
		}
		
		currentPlayerName.setText(player.getName());
		if (players.length == 1)
		{
			timing.start();
			WheelOfFortune.PlayerInfo.add(soloPlayerCountdown);
		}
		add(Directions);
		add(currentPlayerName);
	}
	
	public void LettersDisplayed(){
		closeConsonants();
		isConsonant = 1;
		openVowelsAndSpinner();
		if (phrase.gameOver())
		{
			Directions.setText("Game Over Man Game Over");
			closeVowelAndSpinner();
			closeConsonants();
			roundOver();
			return;
		} else {
		if (letters.consonantsExhausted())
		{
			wheel.removeListener();
			spinText = "You may not spin the wheel any more!";
			if (letters.vowelsExhausted())
			{
				vowelText = "";
				spinText = "puzzle solved!";
			}
		} if (letters.vowelsExhausted())
		{
			vowelText = "";
			spinText = "Spin or solve the puzzle";
			
		}
		}
		if (phrase.getCount() == 0)
		{
			turnLost();
		} else if (phrase.getCount() != 0)
		{
			turnRestarted();
		}
		timing.start();
		Directions.setText(spinText + buyAVowel());

	}
	public void LetterSelected(JButton consonant) {
		closeConsonants();
		closeVowelAndSpinner();
		phrase.matchALetter(consonant.getText());
		player.addPoints(phrase.getCount() * wheel.getValue() * isConsonant);
		consonant.setEnabled(false);
		timing.stop();
		Directions.setText("Checking on your letters");
	}
	public void WantsToBuyaVowel(JButton vowel) {
		
		if ( player.getPoints() >= 200)
		{
			closeVowelAndSpinner();
			vowel.setEnabled(false);
			phrase.matchALetter(vowel.getText());
			isConsonant = 0;
			player.addPoints(-200);
			Directions.setText("Checking on your letters");
		
		if (phrase.getCount() == 0)
		{
			turnLost();
			Directions.setText("Moving right along to...");
		}
		}
		
	}
	
	public void SpinWheelSelected() {
		closeVowelAndSpinner();
		wheel.spinWheel(wheelSpinValue.nextInt(3600) + 400);
		Directions.setText("Wait for the spin to complete");
	}
	
	public void WheelIsDoneSpinning(){
		openConsonants();
		if (wheel.getValue() == -1) //lose a turn
		{
			openVowelsAndSpinner();
			turnLost();
			
		} else if (wheel.getValue() == -2) 	//bankrupt!
		{
			player.bankrupt();
			openVowelsAndSpinner();
			turnLost();
		}

	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		timingCounter--;
		if (timingCounter == 0 && players.length == 1)
		{
			turnLost();
		}
		if (turns == 0 && players.length == 1)
		{
			roundOver();
			return;
			
		}
		soloPlayerCountdown.setText("Turns left: "+turns+" --- Time Until Current Turn Lost: "+timingCounter);
	}
	
	public void roundOver()
	{
		stopTimers();
		if (players.length == 1)
		{
			//single player logic
			if (phrase.gameOver())
			{
				//single player won!
				WheelOfFortune.displayWinningGraphics(player);
				
			}
			else 
			{
				//single player lost!
				WheelOfFortune.displayLosingGraphics(player);
			}
			return ;
		}
		Player winner = player;
		Player loser = player;
		Player newPlayers[] = new Player[players.length - 1];
		
		for (Player play: players)
		{
			if (play.getPoints() > winner.getPoints())
			{
				winner = play;
			}
		}
		
		for (Player play: players)
		{
			if (play.getPoints() < loser.getPoints())
			{
				loser = play;
			}
		}
		int placeForNewPlayers = 0;
		
		if (players.length > 1)
		{
			for (Player play: players)
			{
				if (play != loser)
				{
					newPlayers[placeForNewPlayers] = play;
					++placeForNewPlayers;
				}
			}
			loser.setVisible(false);
		} else {
			newPlayers = players;
		}
		
		
		if (roundCountDown > 1)
		{
			WheelOfFortune.addNewPhrase(thePhrase, --roundCountDown);
			return ;
		}
			
			//multiplayer logic
			
			WheelOfFortune.displayWinningGraphics(winner);
		
		
		
		
	}
	
	private void stopTimers()
	{
		wheel.stopTimer();
		phrase.stopTimer();
		timing.stop();
	}
	
	private void openConsonants(){
		letters.addConsonantListeners();
		Directions.setText("Choose your consonant wisely");
	}
	
	private void closeConsonants(){
		letters.removeConsonantListeners();
	}
	
	private void openVowelsAndSpinner()
	{
		closeConsonants();
		phrase.addSolveListener();
		wheel.addListener();
		letters.addVowelListeners();
	}
	private void closeVowelAndSpinner()
	{
		phrase.removeSolveListener();
		wheel.removeListener();
		letters.removeVowelListeners();
	
		
	}
	
	private void nextPlayersTurn()
	{
		currentPlayer = (++currentPlayer)%players.length;
		player = players[currentPlayer];
		currentPlayerName.setText(player.getName());
	}
	
	private void turnLost()
	{
		--turns;
		timingCounter = 15;
		soloPlayerCountdown.setText("Turns left: "+turns+" --- Time Until Current Turn Lost: "+timingCounter);
		nextPlayersTurn();
	}
	
	private void turnRestarted()
	{
		timingCounter = 15;
	}
	
	private String buyAVowel()
	{
		if (player.getPoints() >= 200)
		{
			return " or buy a vowel";
		}
		else
		{
			return "";
		}
		
	}
	

}
