package wheelOfFortune;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

	private String name;
	private int points;
	private JLabel nameLabel;
	private JLabel pointLabel;
	
	public Player(String _name) {
		name = _name;
		points = 0;
		nameLabel = new JLabel(name);
		pointLabel = new JLabel(new Integer(points).toString());
		pointLabel.setPreferredSize(new Dimension(50, 10));
		add(nameLabel);
		add(pointLabel);
	}

	public String getName(){
		return name;
	}
	
	
	public void addPoints(int _points)
	{
		points += _points;
		pointLabel.setText(new Integer(points).toString());

	}
	
	public int getPoints()
	{
		return points;
	}
	public void bankrupt()
	{
		points = 0;
		pointLabel.setText(new Integer(points).toString());
	}
	
	

}
