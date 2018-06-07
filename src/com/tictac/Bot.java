package com.tictac;
import static com.tictac.constants.*;

import java.util.ArrayList;
import java.util.Random;
public class Bot {
	Game Game;
	boolean allowed = false;
	int turn = 1;
	Bot(Game Game){
		this.Game = Game;
		constants.initiate();
		
	}
	public void heuristic_turn() {
		allowed = true;
		int preffered_square[][] = new int [FIELD][FIELD];
		int max_priority[] = new int[] {-1, -1};
		for(int y = 0; y < FIELD; y++)
			for(int x = 0; x < FIELD; x++)
				preffered_square[y][x] = 0;
		
		for(int y = 0; y < FIELD; y++)
			for(int x = 0; x < FIELD; x++)
				if(Game.Gui.Buttons[y][x].type == VACANT) {
					preffered_square[y][x]++;
					if(x == 1 && y == 1) // central square with the highest priority
						preffered_square[y][x] += 2;
					else if( (y == 0 || y == 2) && (x == 0 || x == 2) ) // corner squares
						preffered_square[y][x]++;
				}
		for(int y = 0; y < FIELD; y++)
			for(int x = 0; x < FIELD; x++)
				for(int dir = 0; dir < 8; dir++) {
					int conj = 0;
					int free_square[] = new int[]{-1, -1}; 
					conj = check_conjunction(y, x, dir, TOED, free_square);
					if(conj == 2 && free_square[0] != -1) {
						Game.Gui.Buttons[free_square[0]][free_square[1]].doClick();
						allowed = false;
						return;
					}
					conj = check_conjunction(y, x, dir, CROSSED, free_square);
					if(conj == 2 && free_square[0] != -1) {
						max_priority[0] = free_square[0];
						max_priority[1] = free_square[1];
					}
				}
		
		
		if(max_priority[0] != -1) {
			Game.Gui.Buttons[max_priority[0]][max_priority[1]].doClick();
			allowed = false;
			return;
		}
		ArrayList<Integer> collission_y = new ArrayList<Integer>();
		ArrayList<Integer> collission_x = new ArrayList<Integer>();
		int max_preffered_square = -1;
		if(max_priority[0] == -1){
			for(int y = 0; y < FIELD; y++)
				for(int x = 0; x < FIELD; x++)
					if(preffered_square[y][x] > max_preffered_square) {
						max_preffered_square = preffered_square[y][x];
						max_priority[0] = y;
						max_priority[1] = x;
					}
			for(int y = 0; y < FIELD; y++)
				for(int x = 0; x < FIELD; x++)
					if(preffered_square[y][x] == max_preffered_square)
					{
						collission_y.add(y);
						collission_x.add(x);
					}
			Random rand = new Random();
			int random_position_of_best = rand.nextInt(collission_y.size());
			max_priority[0] = collission_y.get(random_position_of_best);
			max_priority[1] = collission_x.get(random_position_of_best);
		}
		Game.Gui.Buttons[max_priority[0]][max_priority[1]].doClick();
		allowed = false;
	}
	
	int check_conjunction(int y, int x, int dir, int filter, int[] free){
		int mod_y = y + modificators[dir][0];
		int mod_x = x + modificators[dir][1];
		int ret = (Game.Gui.Buttons[y][x].type == filter) ? 1 : 0;
		if(free != null && Game.Gui.Buttons[y][x].type == VACANT && free[0] == -1) {
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

}
