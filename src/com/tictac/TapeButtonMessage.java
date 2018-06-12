package com.tictac;

public class TapeButtonMessage extends AbstractMessage {
	private static final long serialVersionUID = 7973004594602332859L;
	int y, x;
	TapeButtonMessage(int y, int x){
		this.y = y;
		this.x = x;
	}
	TapeButtonMessage(GameButton G){
		for(int y = 0; y < constants.FIELD; y++)
			for(int x = 0; x < constants.FIELD; x++)
				if(Game.getGame().Gui.Buttons[y][x].equals(G)) {
					this.y = y;
					this.x = x;
					return;
				}
	}
	void OnReceive() {
		Game G = com.tictac.Game.getGame();
		G.TapeButton(G.Gui.Buttons[y][x], false);
	}
}
