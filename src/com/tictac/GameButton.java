package com.tictac;
import java.awt.Graphics;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class GameButton extends JButton {
	private Game Game;
	public int type = constants.VACANT;
	GameButton(Game Game){
		super();
		this.Game = Game;
	}
	public Game getGame()
	{
		return Game;
	}
	public void setType(int vacant) {
		this.type = vacant;
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		int n_width = (int) Math.round(getWidth() * 0.8);
		int n_height = (int) Math.round(getHeight() * 0.8);
    	if(type == com.tictac.constants.CROSSED) {
    		g.drawLine((int) Math.round(getHeight() * 0.2), (int) Math.round(getHeight() * 0.2), n_width, n_height);
    		g.drawLine(n_width, (int) Math.round(getHeight() * 0.2), (int) Math.round(getHeight() * 0.2), n_height);
    	}
    	else if(type == com.tictac.constants.TOED)
    		g.drawOval((int) Math.round(getHeight() * 0.1), (int) Math.round(getHeight() * 0.1), n_width, n_height);
    	else
    		g.clearRect(0, 0, getWidth(), getHeight());
    	g.drawRect(0, 0, getWidth(), getHeight());
	}
}
