package com.tictac;

public class RestartMessage extends AbstractMessage {
	private static final long serialVersionUID = 8581500260324430580L;
	void OnReceive() {
		Game G = com.tictac.Game.getGame();
		G.Gui.EnableButtons(true);
		G.Gui.game_turn.setText("--");
		G.cur_turn = constants.CROSSED;
	}

}
