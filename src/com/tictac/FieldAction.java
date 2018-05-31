package com.tictac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldAction implements ActionListener  {
	@Override
	public void actionPerformed(ActionEvent e) {
		GameButton btn = (GameButton) e.getSource();
		if(btn.getGame().cur_turn == constants.TOED && !btn.getGame().Bot.allowed)
			return;
		btn.setType(btn.getGame().getCurTurn());
		btn.setEnabled(false);
		btn.getGame().MakeTurn();
	}
	
}
