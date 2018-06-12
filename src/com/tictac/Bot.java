package com.tictac;
import static com.tictac.constants.*;

import java.util.ArrayList;
import java.util.Random;
public class Bot {
	Game Game;
	Bot(Game Game){
		this.Game = Game;
		
	}
	public void heuristic_turn() {
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
					conj = Game.check_conjunction(y, x, dir, TOED, free_square);
					if(conj == 2 && free_square[0] != -1) {
						Game.TapeButton(Game.Gui.Buttons[free_square[0]][free_square[1]], false);
						return;
					}
					conj = Game.check_conjunction(y, x, dir, CROSSED, free_square);
					if(conj == 2 && free_square[0] != -1) {
						max_priority[0] = free_square[0];
						max_priority[1] = free_square[1];
					}
				}
		
		
		if(max_priority[0] != -1) {
			Game.TapeButton(Game.Gui.Buttons[max_priority[0]][max_priority[1]], false);
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
		Game.TapeButton(Game.Gui.Buttons[max_priority[0]][max_priority[1]], false);
	}
}
