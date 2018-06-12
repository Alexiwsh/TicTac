package com.tictac;
import java.io.Serializable;
public abstract class AbstractMessage implements Serializable {
	private static final long serialVersionUID = 4201850082704035719L;
	abstract void OnReceive();
}