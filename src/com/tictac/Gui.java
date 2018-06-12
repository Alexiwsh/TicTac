package com.tictac;
import static com.tictac.constants.FIELD;
import static com.tictac.constants.VACANT;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	public GameButton Buttons[][] = new GameButton[com.tictac.constants.FIELD][com.tictac.constants.FIELD];
	private JPanel game_field = new JPanel();
	private JPanel score_turn_field = new JPanel();
	public JLabel game_score = new JLabel("Score: 0/0");
	public JLabel game_turn = new JLabel("--");
	public JButton Restart = new JButton("Restart");
	Gui(Game Game)
	{
		super("Tic-Tac-Toe");
		Container lp = getContentPane();
		setBounds(600, 250, 560, 450);
		setMinimumSize(new Dimension(360, 360));
		setPreferredSize(new Dimension(560, 450));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener (new WindowAdapter() {
		    @Override
			public void windowClosing(WindowEvent e) {
		    int res = JOptionPane.showConfirmDialog(null, "Выйти из программы?");
		    if (res == JOptionPane.YES_OPTION)
		    	try {
					Game.in.close();
			    	Game.out.close();
			    	Game.socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    	finally {
		    	System.exit(0);
		    	}
		    }
		});
		
		game_field.setLayout(new GridLayout(com.tictac.constants.FIELD, com.tictac.constants.FIELD, 0, 0));
		score_turn_field.setLayout(new BoxLayout(score_turn_field, BoxLayout.Y_AXIS));
		score_turn_field.add(game_score);
		score_turn_field.add(game_turn);
		Restart.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(Game.socket != null)
							try {
								Game.out.writeObject(new RestartMessage());
							} catch (IOException e1){e1.printStackTrace();}
						EnableButtons(true);
						game_turn.setText("--");
						Game.cur_turn = constants.CROSSED;
				}});
		lp.add(score_turn_field, BorderLayout.NORTH);
		lp.add(Restart, BorderLayout.SOUTH);
		lp.add(game_field, BorderLayout.CENTER);
		FieldAction Ac = new FieldAction();
		for(int y = 0; y < com.tictac.constants.FIELD; ++y)
			for(int x = 0; x < com.tictac.constants.FIELD; ++x) {
				Buttons[y][x] = new GameButton(Ac);
				game_field.add(Buttons[y][x]);
			}
		DisableButtons(false);
		setVisible(true);
	}
	
	void DisableButtons(boolean iswin)
	{
		for(int y = 0; y < FIELD; ++y)
			for(GameButton G : Buttons[y])
				G.setEnabled(false);
		if(iswin)
			game_turn.setText("--");
	}
	void EnableButtons(boolean isrestart) {
		for(int y = 0; y < FIELD; ++y)
			for(GameButton G : Buttons[y]) {
				G.setEnabled(true);
				if(isrestart) {
					G.setType(VACANT);
					game_turn.setText("First Turn");
				}
			}
	}
}
