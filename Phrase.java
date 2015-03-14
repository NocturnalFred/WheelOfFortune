package wheelOfFortune;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.sound.sampled.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;


public class Phrase extends JPanel implements ActionListener {

    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
	private String theSentence;
	private JPanel phrase;
	private GameLogicController Controller;
	private int numLettersFound;
	private Timer timer;
	private boolean animating;
	private listenMouse mouseListen;
	Component[] letters;
	private int lettersLoc;
        private JButton solveIt;
        private MouseListener listener;
        private JOptionPane guessIt;
        
	public Phrase(phraseGenerator _phrase, GameLogicController _Controller, JButton solvePuzzbut) {
		// TODO Auto-generated constructor stub
		super();
		_phrase.pickNewPhrase();
		Controller = _Controller;
		theSentence = _phrase.getPhrase();
		animating = false;
		numLettersFound = 0;
		mouseListen = new listenMouse();
		phrase = new JPanel(new GridLayout(0, 14));
		timer = new Timer(1000, this);
		timer.start();
		lettersLoc = 0;
                solveIt = solvePuzzbut;
                addSolveListener();
		
		JPanel container = new JPanel();
		JLabel category = new JLabel(_phrase.getCategory());

		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		category.setAlignmentX((Component.CENTER_ALIGNMENT));
		category.setFont(new Font(category.getFont().getName(), Font.PLAIN, 30));
		fillPhraseBoard(phrase);
		
		container.add(category);
		container.add(phrase);
		setPreferredSize(new Dimension(80, 250));
		
		this.add(container);
	}
        
        public void addSolveListener() {
            solveIt.addMouseListener(mouseListen);
    
            
        }

        public void removeSolveListener() {
        	
        	solveIt.removeMouseListener(mouseListen);
        }
        
        public void solveAttempt(String guess){
            if (theSentence.compareToIgnoreCase(guess)==0) {
                
            	for(Component check: phrase.getComponents())
        		{
        			if (((Lettering) check).getText2() != "!" && ((Lettering) check).getText2() != "-" 
        					&& ((Lettering) check).getText2() != "*" 
        					&& ((Lettering) check).getText2() != ((Lettering) check).getText())
        			{
        				((Lettering) check).setText2();
        				((Lettering) check).setBackground(Color.WHITE);
        			}    		
        		}
                JOptionPane winmsg = new JOptionPane();
                winmsg.showMessageDialog(WheelOfFortune.topLevelFrame, "You solved the puzzle!");
                Controller.roundOver();
            }
            else
            {
            	numLettersFound = 0;
                JOptionPane wrong = new JOptionPane();
                wrong.showMessageDialog(WheelOfFortune.topLevelFrame, "Incorrect!");
                Controller.LettersDisplayed();
            }
        }
        
        /* this is a hodgepodge of crap.
         * Really, don't bother looking at this, this is strictly logic for formatting
         * the phrase board.  A better way of doing this would have been to put it in
         * its own JPanel and have a function for filling in each row
         * but it is done now and I don't think there is any legitimate reason to change
         * it since it works.
         */
	private void fillPhraseBoard(JPanel phrase)
	{
		Lettering temp;
		String[] sentenceSplit = theSentence.split(" ");
		int gridPlace = 0;
		int gridMax = 14;
		//put in top row of green buttons where corners are invisible
		for (int b = 0; b < gridMax; ++b)
		{
			if (b % gridMax == 0 || b % gridMax == 13)	//this denotes a corner piece, pieces with "-" as their value are invisible
			{
				temp = new Lettering("-");		//invisible letter
				phrase.add(temp);
			}
			else 
			{
				temp = new Lettering("!");		//green letter
				phrase.add(temp);
			}
		}
		
		boolean wordNotAdded;				//split up the strings and start printing them.
		
		/*	I don't have time to explain this part of the code line by line.
		 *  What it does is for each row it fills in as many words as it can
		 *  for each word it checks if the word can fit in a row.  If it can't
		 *  then it fills in the rest of the row with non-letter pieces
		 *  then it starts on a new row filling int words.  So on and so forth.
		 */
		
		for (String word: sentenceSplit)
		{
			wordNotAdded = true;
			while (wordNotAdded)
			{
			if (gridPlace % gridMax == 13 || gridPlace % gridMax == 0)
			{
				temp = new Lettering("!");
				phrase.add(temp);
				++gridPlace;
				continue;
			}
			else if (gridPlace % gridMax == 0)
			{
				temp = new Lettering("!");
				phrase.add(temp);
				++gridPlace;
			} else if (gridPlace % gridMax != 1)
			{
				temp = new Lettering("*");
				phrase.add(temp);
				++gridPlace;		
				if (gridPlace % gridMax == 13)
					continue;
				
			}
			
				if (word.length() > ( gridMax - (gridPlace % gridMax) ) - 1)
				{
					for (int i = gridPlace%gridMax; i < gridMax-1; ++i)
					{
						temp = new Lettering("*");
						phrase.add(temp);
						++gridPlace;
					}
					
				} else {
					for (int a = 0; a < word.length(); ++a)
					{
						temp = new Lettering(new Character(word.charAt(a)).toString());
						phrase.add(temp);
						++gridPlace;
						
					}
					wordNotAdded = false;
				}
				
				
			}
			
			
		}
		
		//after adding all the words there may be left over buttons to fill the right end of that row
		//this fills those in.
		if (gridPlace % gridMax != 0)
		{
			for (int b = gridPlace%gridMax; b < gridMax; ++b)
			{
				if (b % gridMax == 0 || b % gridMax == 13)
				{
					temp = new Lettering("!");
					phrase.add(temp);
				}
				else 
				{
					temp = new Lettering("*");
					phrase.add(temp);
				}
			}
		}
		
		//this fills in the bottom row just like the top row
		for (int b = 0; b < gridMax; ++b)
		{
			if (b % gridMax == 0 || b % gridMax == 13)
			{
				temp = new Lettering("-");
				phrase.add(temp);
			}
			else 
			{
				temp = new Lettering("!");
				phrase.add(temp);
			}
		}
		
		
	}
	
	public void matchALetter(String a)
	{
		int count = 0;
		
		Component[] letters2 = phrase.getComponents();
		for (Component letter: letters2)
		{
			if ( ((Lettering) letter).getText2().equalsIgnoreCase(a))
				{
					letter.setBackground(Color.WHITE);
					++count;
				}
		}
		
		letters = new Component[count];
		lettersLoc = 0;
		for (Component letter: letters2)
		{
			if ( ((Lettering) letter).getText2().equalsIgnoreCase(a))
				{
					letters[lettersLoc] = letter;
					++lettersLoc;
				}
		}
		lettersLoc = 0;
		numLettersFound = count;
		animating = true;
		
	}
	
	public int getCount()
	{
		return numLettersFound;
	}

	private class Lettering extends JButton{
	
		private String myLetter;
		public Lettering(String let){
			super();
			myLetter = let;
			String space = " ";
			setEnabled(false);
			setPreferredSize(new Dimension(50, 30));
			setOpaque(true);
			if (let.equals("-"))
			{
				setVisible(false);
			}
			else if (let.equals(space) || let.equals("*") || let.equals("!"))
			{
				setBackground(new Color(50, 248, 100));
			} else 
			{
				setBackground(new Color(100, 0, 255));
			}		
		}
		private String getText2()
		{
			return myLetter;
		}
		private void setText2()
		{
			setText(myLetter);
		}	
	}

	public void actionPerformed(ActionEvent arg0) {
		if (animating)
		{
			
			if (lettersLoc < letters.length)
			{
				//stole this code for the sound clip from 
				//http://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
				//sound clip file is from http://soundjax.com/ding_sounds-1.html
				try {
			
					//soundFile = this.getClass.getResourceAsStream(("DING.WAV");
				    stream = AudioSystem.getAudioInputStream(getClass().getResource("DING.WAV"));
				    format = stream.getFormat();
				    info = new DataLine.Info(Clip.class, format);
				    clip = (Clip) AudioSystem.getLine(info);
				    clip.open(stream);
				    clip.start();
				}
				catch (Exception e) {
				    
				} 
				((Lettering) letters[lettersLoc]).setText2();
				++lettersLoc;
			} 
			if (lettersLoc >= letters.length)
			{
				Controller.LettersDisplayed();
				animating = false;
			}
		
			
		}
		
	}
	public void stopTimer()
	{
		timer.stop();
	}
	
	public boolean gameOver()
	{
		boolean gameover = true;
		for(Component check: phrase.getComponents())
		{
			if (((Lettering) check).getText2() != "!" && ((Lettering) check).getText2() != "-" 
					&& ((Lettering) check).getText2() != "*" 
					&& ((Lettering) check).getText2() != ((Lettering) check).getText())
			{
				gameover = false;
			}
		
		}
		return gameover;
	}
	
	
	class wheelListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Controller.SpinWheelSelected();
		}
		
	}
	
		class listenMouse implements MouseListener {
		
			public void mouseClicked(MouseEvent e) {
       
            guessIt = new JOptionPane();
            String guess = guessIt.showInputDialog("Please input a value");
            solveAttempt(guess);
            
			}

			public void mouseEntered(MouseEvent arg0) {
				
				
			}

			public void mouseExited(MouseEvent arg0) {
				
				
			}

			public void mousePressed(MouseEvent arg0) {
				
				
			}

			public void mouseReleased(MouseEvent arg0) {
				
				
			}
		}
	
}



