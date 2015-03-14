package wheelOfFortune;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Letters extends JPanel implements ActionListener{

	private JPanel vowels;
	private JPanel consonants;
	char[] vowel;
	char[] consonant;
	private GameLogicController Controller;
	public Letters(GameLogicController _Controller) {
		// TODO Auto-generated constructor stub
		super();
		Controller = _Controller;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		vowels = new JPanel(new GridLayout(0, 5));
		consonants = new JPanel(new GridLayout(3, 7));
		vowel = new char[] { 'a', 'e', 'i', 'o', 'u' };
		consonant = new char[] {'b', 'c', 'd', 'f', 'g', 'h', 'j', 
							'k', 'l', 'm', 'n', 'p', 'q', 'r', 
							's', 't', 'v', 'w', 'x', 'y', 'z' };
		JButton temp;
		for (char v: vowel)
		{
			temp = new Letter(new Character(v).toString());
			vowels.add(temp);
		}
		
		for (char c: consonant)
		{
			temp = new Letter(new Character(c).toString());
			consonants.add(temp);
		}
		this.add(vowels);
			
		JPanel consonantContainer = new JPanel();
		consonantContainer.setLayout(new BoxLayout(consonantContainer, BoxLayout.PAGE_AXIS));

		JLabel choose = new JLabel("Choose a Consonant");
		choose.setAlignmentX((Component.CENTER_ALIGNMENT));
		choose.setFont(new Font(choose.getFont().getName(), Font.PLAIN, 20));
		//consonantContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		consonantContainer.setBackground(Color.YELLOW);
		consonantContainer.add(choose);
		consonantContainer.add(consonants);
		
		JPanel vowelContainer = new JPanel();
		vowelContainer.setLayout(new BoxLayout(vowelContainer, BoxLayout.PAGE_AXIS));
		JLabel buy = new JLabel("Buy a Vowel");
		buy.setAlignmentX((Component.CENTER_ALIGNMENT));
		buy.setFont(new Font(buy.getFont().getName(), Font.PLAIN, 20));
		vowelContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		vowelContainer.setBackground(Color.YELLOW);
		vowelContainer.add(buy);
		vowelContainer.add(vowels);
		
		this.add(vowelContainer);
		this.add(consonantContainer);
		addVowelListeners();
		
	}

	public void actionPerformed(ActionEvent event) {
			/*
			 * get the text of the button clicked with event.getActionCommand()
			 * set the text of the button to something to show that the letter has been used temp.setText(something);
			 * remove the listener
			 * More to worry about:
			 * 		Vowels can only be purchased when you have enough money, so we need to add/remove listeners
			 * 		based on how much money they have.
			 */

			JButton temp = (JButton)event.getSource();
			if (temp.getParent() == vowels)
			{
				Controller.WantsToBuyaVowel(temp);
				
			} else if (temp.getParent() == consonants)
			{
				Controller.LetterSelected(temp);
				
			}

			
			//need a reference here to the phrasePanel and the PlayerTable
			//When clicked pass in the letter to a function in phrasePanel, have the phrasePanel return number of 
			//hits on the phrase and then call a function from playerTable 
		}	
	
	public void addVowelListeners()
	{
		String ignore = "*";
		Component[] vowelListeners = vowels.getComponents();
		for (Component button: vowelListeners)
		{
			if (!((AbstractButton) button).getText().equals(ignore))
			((JButton)button).addActionListener(this);		
		}
		
		
	}
	
	public void addConsonantListeners()
	{
		String ignore = "*";
		Component[] consonantListeners = consonants.getComponents();
		for (Component button: consonantListeners)
		{
			if (!((AbstractButton) button).getText().equals(ignore))
			((JButton)button).addActionListener(this);
			
		}
		
	}
	
	public void removeVowelListeners()
	{	
		Component[] vowelListeners = vowels.getComponents();
		for (Component button: vowelListeners)
		{
			((JButton)button).removeActionListener(this);		
		}
		
	}
	
	public void removeConsonantListeners()
	{
		Component[] consonantListeners = consonants.getComponents();
		for (Component button: consonantListeners)
		{
			((JButton)button).removeActionListener(this);		
		}
	}
	
	public boolean consonantsExhausted(){
		Component[] consonantListeners = consonants.getComponents();
		
		for (Component button: consonantListeners)
		{
			if (((JButton)button).isEnabled())
				return false;		
		}
		return true;
	}
	
	public boolean vowelsExhausted()
	{
		Component[] vowelsListeners = vowels.getComponents();
		
		for (Component button: vowelsListeners)
		{
			if (((JButton)button).isEnabled())
				return false;		
		}
		return true;
	}
	
	
	private class Letter extends JButton
	{
		public Letter(String let)
		{
			super(let);
			//setPreferredSize(new Dimension(50, 30));
			setBackground(new Color(210, 180, 140));
			setOpaque(true);
		}
	
	}
	
}
