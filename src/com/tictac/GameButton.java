package com.tictac;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class GameButton extends JButton {
	private Game Game;
	public int type = constants.VACANT;
	private static Font button_font =  new Font("Verdana", Font.BOLD, 100);
	GameButton(Game Game){
		super();
		this.Game = Game;
		setContentAreaFilled(false);
		this.setFont(button_font);
	}
	public Game getGame()
	{
		return Game;
	}
	public void setType(int vacant) {
		this.type = vacant;
		this.setText(constants.type_to_text(type));
	}
}
