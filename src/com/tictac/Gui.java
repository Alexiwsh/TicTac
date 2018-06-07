package com.tictac;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	public GameButton Buttons[][] = new GameButton[com.tictac.constants.FIELD][com.tictac.constants.FIELD];
	private JPanel game_field = new JPanel();
	private JPanel score_turn_field = new JPanel();
	public LabelStat game_score = new LabelStat("Score: 0/0");
	public LabelStat game_turn = new LabelStat("--");
	private JButton Restart = new JButton("Restart");
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
		         System.exit(0);
		    }
		});
		
		game_field.setLayout(new GridLayout(com.tictac.constants.FIELD, com.tictac.constants.FIELD, 0, 0));
		score_turn_field.setLayout(new BoxLayout(score_turn_field, BoxLayout.Y_AXIS));
		score_turn_field.add(game_score);
		score_turn_field.add(game_turn);
		Restart.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						for(int y = 0; y < com.tictac.constants.FIELD; ++y)
							for(GameButton G : Buttons[y]){
							G.setEnabled(true);
							G.setType(com.tictac.constants.VACANT);
							Game.cur_turn = constants.CROSSED;
						}
						game_turn.setText("--");
				}});
		lp.add(score_turn_field, BorderLayout.NORTH);
		lp.add(Restart, BorderLayout.SOUTH);
		lp.add(game_field, BorderLayout.CENTER);
		FieldAction Ac = new FieldAction();
		for(int y = 0; y < com.tictac.constants.FIELD; ++y)
			for(int x = 0; x < com.tictac.constants.FIELD; ++x) {
				Buttons[y][x] = new GameButton(Game);
				Buttons[y][x].addActionListener(Ac);
				game_field.add(Buttons[y][x]);
			}
		setVisible(true);
	}}
