package com.tictac;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageHandler extends Thread {
	Socket S;
	ObjectInputStream in;
	
	MessageHandler(Socket S, ObjectInputStream in){
		this.S = S;
		this.in = in;
		start();
	}
	@Override
	public void run(){
		try {
			while(!S.isClosed()) {
				AbstractMessage M = (AbstractMessage) in.readObject();
				M.OnReceive();
			} 
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
	}
}
