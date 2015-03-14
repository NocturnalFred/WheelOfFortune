package wheelOfFortune;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.Timer;


public class Wheel extends Component implements ActionListener{
    
private BufferedImage wheelImg;			//stores the wheel image
private BufferedImage arrowImg;			//stores the arrow that tells you where the wheel landed.
private Graphics2D g2g;					//for paint function
private int radian;				//for angle to transform wheel (I.E. rotation)
private Timer timing;			//timing for repaints to keep up with changes
private int timesToMoveSpinner; //Number to set when wheel is spun, high number means longer spin
private int fastestSpin;
private int midSpin;
private int[] pointsArray;
private wheelListener listener;
private GameLogicController Controller;
private JButton spinButton;



/* 
 * Paint gets called based on the action event that is created by the Timer timing.
 * It redraws the wheel based on the radian value created.
 * timesToMoveSpinner is a value that should be more or less random that tells you
 * how much to increase the radian by in total for a particular spin.
 * Thus if you enter "100" in the spinWheel(value) function it will rotate the
 * wheel by 100 radians.  An if nesting structure is used to change the speed
 * of the rotation based on how close to zero timesToMoveSpinner is.
 * 
 */
public void paint(Graphics g) {
	
	int speedMult = 3;
	g2g = (Graphics2D)g;
	//g2g.drawImage(wheelImg, 0, 0, null);
	//change the image to reflect a rotation of the original image based on 
	//a change in radians  
	//code from here to "gwg.drawImage(..." was taken from a snippet on stackOverFlow
	double rotationRequired = Math.toRadians(radian);
	double locationX = wheelImg.getWidth() / 2;
	double locationY = wheelImg.getHeight() / 2;
	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

	// Drawing the rotated image at the required drawing locations
	g2g.drawImage(op.filter(wheelImg, null), 0,0, null);
	g2g.drawImage(arrowImg, wheelImg.getWidth()/2 - arrowImg.getWidth()/2, 0, null);
	
	//this nesting structure defines how many degrees to rotate the spinner during a paint() call
	if (timesToMoveSpinner > 2)
	{
		if (timesToMoveSpinner > fastestSpin)			//if timesToMoveSpinner is still relatively high move it more than others
			{
				radian = (radian + speedMult*5) % 359;			//keep radians from going negative by using modulus operator
				timesToMoveSpinner -= speedMult*5;
			}
		else if (timesToMoveSpinner > (fastestSpin / 2))	//if it isn't super high move it just a little bit more
		{
				radian = (radian + speedMult*4) % 359;
				timesToMoveSpinner -= speedMult*4;
		}
		else if (timesToMoveSpinner > midSpin)			//if it has only got a bit more to go move it a little less.
		{
				radian = (radian + speedMult*3) % 359;
				timesToMoveSpinner -= speedMult*3;
		}
		else if (timesToMoveSpinner > (midSpin / 2))	//if it has more than 150 radians left move it by 5 degrees
		{
			radian = (radian + speedMult*2) % 359;
			timesToMoveSpinner -= speedMult*2;
		}
		else 
		{
				radian = (radian + 1) % 359;			//otherwise if there are only 150 more degrees to go move it by 1 degree
				timesToMoveSpinner -= 1;
		}
														//2 degrees left is our stopping point, so don't do anything.
	}
	
}

public Wheel(GameLogicController _Controller, JButton _spinButton) {
	Controller = _Controller;
	radian = 0;
	fastestSpin = 2000;
	midSpin = 100;
	timing = new Timer(10, this);
	timing.start();
	spinButton = _spinButton;
	listener = new wheelListener();
	addListener();
	// the first index of pointsArray is ignored.  In order to save 
	// programming execution and programming logic we sacrificed the 
	// very small space necessary to house the first variable.
	pointsArray = new int[] { 		0, 		300, 	650, 	250, 	500, 	350, 
									700, 	100, 	800,	150, 	-2, 	
									600,	200, 	550, 	-1, 	750, 	
									400, 	200, 	-2, 	450, 	100 };
	
	
	
try {
	wheelImg = ImageIO.read(this.getClass().getResourceAsStream("wheelOF.png"));
	arrowImg = ImageIO.read(this.getClass().getResourceAsStream("graphic_black_triangle_upsidedown.png"));
} catch (IOException e) {
}

}

public void addListener(){
	spinButton.addActionListener(listener);
}

public void removeListener()
{
	spinButton.removeActionListener(listener);
}

public int getValue()
{
	//stupid math.  Technically our "radian" as used for the index is off.  This can be 
	//fixed either programmatically or by editting the wheel
	//so that the first degree span lines up with our code.
	//I prefer not doing it programmatically.
	if (radian % 18 == 0)
	{
		++radian;
	}

		return pointsArray[20 - radian/18];
}

//this function is called from the gamelogiccontroller to start moving the wheel
public void spinWheel(int timesToMove){
	timesToMoveSpinner = timesToMove;
}

public Dimension getPreferredSize() {
 if (wheelImg == null) {
      return new Dimension(100,100);
 } else {
    return new Dimension(wheelImg.getWidth(null), wheelImg.getHeight(null));
}
}

public void actionPerformed(ActionEvent arg0) {
	if (timesToMoveSpinner == 2)
	{
		timesToMoveSpinner -= 1;
		Controller.WheelIsDoneSpinning();
	}
	repaint();
}

public void stopTimer()
{
	timing.stop();
}

class wheelListener implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Controller.SpinWheelSelected();
	}
	
}

}
