package com.gmail.Jacob6816.assembler.abstracts;

/** A_COMMANDS are the @XXX commands
 * C_COMMANDS are for dest=comp;jump
 * L_COMMANDS are for defining labels (XXX) */
public enum CommandType {
	A_COMMAND((byte) 0), C_COMMAND((byte) 111);

	final byte prefix;

	private CommandType(byte prefix) {
		this.prefix = prefix;
	}

	public byte getPrefix() {
		return this.prefix;
	}
}
