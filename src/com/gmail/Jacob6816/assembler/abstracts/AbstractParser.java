package com.gmail.Jacob6816.assembler.abstracts;

public abstract class AbstractParser {
	public abstract boolean hasMoreCommands();

	public abstract void advance();

	public abstract CommandType getCommandType();

	public abstract String getSymbol();

	public abstract String getDest();

	public abstract String getComp();

	public abstract String getJump();
}
