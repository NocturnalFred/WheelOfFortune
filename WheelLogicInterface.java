package wheelOfFortune;

import javax.swing.JButton;

//interface for game logic controller
public interface WheelLogicInterface {

	public void LettersDisplayed();
	public void LetterSelected(JButton consonant);
	public void WantsToBuyaVowel(JButton vowel);
	public void SpinWheelSelected();
	public void WheelIsDoneSpinning();
        
	
	
	
	
}
