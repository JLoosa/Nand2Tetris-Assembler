package com.gmail.Jacob6816.assembler.handlers.v2;

import java.util.Iterator;
import java.util.List;

import com.gmail.Jacob6816.assembler.abstracts.AbstractParser;
import com.gmail.Jacob6816.assembler.abstracts.CommandType;

public class ParserV2 extends AbstractParser {

	private Iterator<String> queue;

	public ParserV2(List<String> parsables) {
		queue = parsables.iterator();
	}

	@Override
	public boolean hasMoreCommands() {
		return queue.hasNext();
	}

	private CommandType	commandType;
	private String		symbol, comp, dest, jump;

	@Override
	public void advance() {
		symbol = "null";
		comp = "null";
		dest = "null";
		jump = "null";
		String current = queue.next();

		commandType = current.startsWith("@") ? CommandType.A_COMMAND : CommandType.C_COMMAND;

		if (commandType == CommandType.A_COMMAND) {
			symbol = current.substring(1);
		} else {
			if (current.contains(";")) {
				int index = current.indexOf(";");
				jump = current.substring(index);
				current = current.substring(0, index);
			}
			if (current.contains("=")) {
				int index = current.indexOf("=");
				dest = current.substring(0, index);
				current = current.substring(index + 1);
			}
			comp = current;
		}
	}

	@Override
	public CommandType getCommandType() {
		return commandType;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String getDest() {
		return dest;
	}

	@Override
	public String getComp() {
		return comp;
	}

	@Override
	public String getJump() {
		return jump;
	}

}
