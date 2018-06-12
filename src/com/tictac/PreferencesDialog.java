package com.tictac;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;
public class PreferencesDialog extends JFrame {
	private static final long serialVersionUID = 8714157403139616958L;
	private JButton start = new JButton("Start");
	private JTextField ip_field = new JTextField(10);
	private JTextField port_field = new JTextField(10);
	private JLabel ip_tooltip = new JLabel("Ip:");
	private JLabel port_tooltip = new JLabel("Port:");
	private ButtonGroup Check_group = new ButtonGroup();
	private int type = -1;
	PreferencesDialog(){
		super("Preferences");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener (new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});
		Container lp = getContentPane();
        lp.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 0, 0);
        lp.add(ip_tooltip, constraints);
        constraints.gridy = 1;
        lp.add(port_tooltip, constraints);
        
        constraints.gridy = 0;
        constraints.gridx = 1;
        lp.add(ip_field, constraints);
        constraints.gridy = 1;
        lp.add(port_field, constraints);
        
        
        JPanel panelCheck = new JPanel();
        panelCheck.setLayout(new BoxLayout(panelCheck, BoxLayout.Y_AXIS)); 
        constraints.gridy = 0;
        constraints.gridx = 3;
        
        constraints.insets = new Insets(0, 0, 0, 0);
        lp.add(panelCheck, constraints);
        final class CheckListener implements ItemListener{
			public void itemStateChanged(ItemEvent e) {
				JCheckBox J = (JCheckBox)e.getSource();
				if(J.isSelected()) {
					boolean states[] = new boolean[2];
					switch(J.getText()) {
					case "Server":
						states[0] = false;
						states[1] = true;
						try {
							ip_field.setText(InetAddress.getLocalHost().getHostAddress());}
						catch (UnknownHostException e1) {e1.printStackTrace();}
						type = constants.G_SERVER;
						break;
					case "Client":
						states[0] = true;
						states[1] = true;
						type = constants.G_CLIENT;
						break;
					case "Bot":
						states[0] = false;
						states[1] = false;
						type = constants.G_BOT;
						break;
					}
					ip_field.setEnabled(states[0]);
					ip_tooltip.setEnabled(states[0]);
					port_field.setEnabled(states[1]);
					port_tooltip.setEnabled(states[1]);
				}
			}}
        start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(type == -1)
					return;
				JButton J = (JButton)e.getSource();
				PreferencesDialog Master = (PreferencesDialog)J.getParent().getParent().getParent().getParent(); // i know
				String ip = ip_field.getText();
				int port = 0;
				try {
				port = Integer.parseInt(port_field.getText());
				}catch(NumberFormatException e1) {
					port = 8189;
				}
				finally {
				new Game(type, ip.length() > 1 ? ip : null, port);
				Master.setVisible(false);
				Master.dispose();
				}
			}});
        CheckListener C_L = new CheckListener();
        String[] names = { "Server", "Client", "Bot" };
        for (int i = 0; i < names.length; i++) {
            JCheckBox check = new JCheckBox(names[i]);
            check.addItemListener(C_L);
            panelCheck.add(check, constraints);
            Check_group.add(check);
        }
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.insets = new Insets(20, 0, 0, 0);
        lp.add(start, constraints);
        setLocationRelativeTo(null);
		pack();
		setResizable(false);
		setVisible(true);
	}
}
