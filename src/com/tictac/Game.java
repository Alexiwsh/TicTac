package com.tictac;
import static com.tictac.constants.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class Game {
	int score[] = new int[2];
	int cur_turn = CROSSED;
	int side;
	int game_type;
	Gui Gui;
	Bot Bot;
	private static Game _Game;
	Socket socket;
	ServerSocket server;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	Game(int game_type, String ip, int port){
		constants.initiate();
		this.game_type = game_type;
		side = (game_type == G_CLIENT ? TOED : CROSSED);
		System.out.println(game_type == G_CLIENT ? "Client: " : "Server: ");
		_Game = this;
		Gui = new Gui(this);
		if(game_type == G_BOT) {
			Bot = new Bot(this);
			Gui.EnableButtons(false);
		}
		else {
			Gui.Restart.setEnabled(false);
			Gui.game_turn.setText("Waiting for connection...");
			Thread T = new Thread(new Runnable(){public void run(){InitiateConnection(ip, port);}});
			T.start();
			}
	};
	private void InitiateConnection(String ip, int port) {
		try {
			if(game_type == G_SERVER) {
				server = new ServerSocket(port);
				socket = server.accept();
			}
			else
				for(int i = 0; i < 10; i++)
					try {
						socket = new Socket(ip, port);
						break;}
					catch(ConnectException e1) {
						Thread.sleep(1000);
						continue;
					}
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			Gui.EnableButtons(false);
			Gui.game_turn.setText((side == CROSSED ? "Your" : "Enemy") + " Turn");
			Gui.Restart.setEnabled(true);
		    Thread T = new MessageHandler(socket, in);
	        T.join();
			
		} catch(IOException | InterruptedException  e){
			e.printStackTrace();
			System.exit(-1);
			}
	}
	public void MakeTurn(GameButton G)
	{
		int winner = checkVictory();
		if(winner > 0)
		{
			score[winner == CROSSED ? 0 : 1]++;
			Gui.game_score.setText("Score: " + score[0] + "/" + score[1]);
			Gui.DisableButtons(true);
		}
		ChangePlayer(G, winner);
	}
	private void ChangePlayer(GameButton G, int winner)
	{
		if(cur_turn == CROSSED) {
			cur_turn = TOED;
			if(game_type == G_BOT && winner <= 0)
				Bot.heuristic_turn();
		}
		else
			cur_turn = CROSSED;
		if(cur_turn == side)
			Gui.game_turn.setText("Your Turn");
		else {
			Gui.game_turn.setText("Enemy Turn");
			if(G != null && game_type != G_BOT)
				try {
					out.writeObject(new TapeButtonMessage(G));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public void TapeButton(GameButton G, boolean is_player) {
		G.setType(cur_turn);
		G.setEnabled(false);
		MakeTurn(is_player ? G : null);
	}
	public void TapeButton(int y, int x, boolean is_player) {
		TapeButton(Gui.Buttons[y][x], is_player);
	}
	private int checkVictory() {
		for(int y = 0; y < FIELD; ++y)
			for(int x = 0; x < FIELD; ++x)
				for(int i = 0; i < 8; i++) {
					if(Gui.Buttons[y][x].type == CROSSED
							&& check_conjunction(y, x, i, CROSSED, null) >= 3)
						return CROSSED;
					if(Gui.Buttons[y][x].type == TOED &&
							check_conjunction(y, x, i, TOED, null) >= 3)
						return TOED;
				}
		return VACANT;
	}
	int check_conjunction(int y, int x, int dir, int filter, int[] free){
		int mod_y = y + modificators[dir][0];
		int mod_x = x + modificators[dir][1];
		int ret = (Gui.Buttons[y][x].type == filter) ? 1 : 0;
		if(free != null && Gui.Buttons[y][x].type == VACANT && free[0] == -1) {
			free[0] = y;
			free[1] = x;
		}
		if(check_field_borders(mod_y, mod_x))
			return (ret + check_conjunction(mod_y, mod_x, dir, filter, free));
		return ret;
	}
	
	private boolean check_field_borders(int y, int x) {
		return (y >= 0 && y < FIELD && x >= 0 && x < FIELD);
	}
	static public Game getGame() {
		if(_Game == null)
			throw new NullPointerException("The Method getGame() that returns Game was called before initiating Game");
		return _Game;
	}
	public int getCurTurn()
	{
		return cur_turn;
	}
	
}
