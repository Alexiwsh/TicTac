package com.tictac;

public final class constants {
	public static final int VACANT = 0, CROSSED = 1, TOED = 2, FIELD = 3;
	public static final int NORTH = 0, SOUTH = 1, WEST = 2, EAST = 3, NORTH_WEST = 4, NORTH_EAST = 5, SOUTH_EAST = 6, SOUTH_WEST = 7;
	public static final int modificators[][] = new int[8][2];
	private static boolean isinit = false;
	private constants() {};
	public static void initiate() {
		if(isinit)
			return;
		modificators[NORTH][0] = -1;
		modificators[NORTH][1] = 0;
		
		modificators[SOUTH][0] = 1;
		modificators[SOUTH][1] = 0;
		
		modificators[WEST][0] = 0;
		modificators[WEST][1] = -1;
		
		modificators[EAST][0] = 0;
		modificators[EAST][1] = 1;
		
		modificators[NORTH_WEST][0] = -1;
		modificators[NORTH_WEST][1] = -1;
		
		modificators[NORTH_EAST][0] = -1;
		modificators[NORTH_EAST][1] = 1;
		
		modificators[SOUTH_WEST][0] = 1;
		modificators[SOUTH_WEST][1] = -1;
		
		modificators[SOUTH_EAST][0] = 1;
		modificators[SOUTH_EAST][1] = 1;
		isinit = true;
	}
	public static String type_to_text(int type) {
		switch(type) {
		case VACANT:
			return "";
		case CROSSED:
			return "x";
		case TOED:
			return "0";
		}
		return null;
	}
}
