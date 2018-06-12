package com.tictac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldAction implements ActionListener  {
	@Override
	public void actionPerformed(ActionEvent e) {
		Game G = com.tictac.Game.getGame();
		if(G.game_type == constants.G_BOT && G.cur_turn == constants.TOED)
			return;
		if(G.side != G.cur_turn)
			return;
		GameButton btn = (GameButton) e.getSource();
		G.TapeButton(btn, true);
	}
	
}
