package com.tictac;

public class Game {
	int score[] = new int[2];
	Gui Gui;
	Bot Bot;
	int cur_turn;
	Game(){
		cur_turn = constants.CROSSED;
		Gui = new Gui(this);
		Bot = new Bot(this);
	};
	public int getCurTurn()
	{
		return cur_turn;
	}
	public boolean button_with_flag(int y, int x, int flag) {
		return (Gui.Buttons[y][x].type == flag);
	}
	private void ChangePlayer()
	{
		if(cur_turn == constants.CROSSED) {
			cur_turn = constants.TOED;
			Bot.heuristic_turn();
		}
		else
			cur_turn = constants.CROSSED;
	}
	public void MakeTurn()
	{
		if(cur_turn == constants.CROSSED)
			Gui.game_turn.setText("Ai Turn");
		else
			Gui.game_turn.setText("Your Turn");
		int winner = checkVictory();
		if(winner > 0)
		{
			score[winner == constants.CROSSED ? 0 : 1]++;
			Gui.game_score.setText("Score: " + score[0] + "/" + score[1]);
			DisableButtons();
		}
		else
			ChangePlayer();
	}
	private byte checkVictory() {
		for(int y = 0; y < constants.FIELD; ++y)
			for(int x = 0; x < constants.FIELD; ++x)
				for(int i = 0; i < 8; i++) {
					if(Gui.Buttons[y][x].type == constants.CROSSED
							&& Bot.check_conjunction(y, x, i, constants.CROSSED) >= 3)
						return constants.CROSSED;
					if(Gui.Buttons[y][x].type == constants.TOED &&
							Bot.check_conjunction(y, x, i, constants.TOED) >= 3)
						return constants.TOED;
				}
		return constants.VACANT;
	}
	
	private void DisableButtons()
	{
		for(int y = 0; y < com.tictac.constants.FIELD; ++y)
			for(GameButton G : Gui.Buttons[y]){
				G.setEnabled(false);
			}
		Gui.game_turn.setText("--");
	}
	
}
