package com.tictac;
import java.awt.Font;
import javax.swing.JLabel;

public class LabelStat extends JLabel {
	private static final long serialVersionUID = -4826925481706889271L;
	
	LabelStat(String text)
	{
		super(text);
		this.setFont(new Font("Verdana", Font.PLAIN, 15));
	}
}
