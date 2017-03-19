package com.gmail.Jacob6816.assembler.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gmail.Jacob6816.assembler.abstracts.AbstractParser;
import com.gmail.Jacob6816.assembler.abstracts.CommandType;

public class DefaultParser extends AbstractParser {

	private boolean				listIsFinal;
	private List<String>		commandList;
	private Iterator<String>	commandIterator;

	public DefaultParser() {
		commandList = new ArrayList<>();
		listIsFinal = false;
	}

	public void addToList(String input) {
		if (listIsFinal) return;
		commandList.add(input);
	}

	public void startResetIterator() {
		listIsFinal = true;
		commandIterator = commandList.iterator();
	}

	@Override
	public boolean hasMoreCommands() {
		if (!listIsFinal) return false;
		return commandIterator.hasNext();
	}

	private String		currentToken, symbol, destination, comp, jump;
	private CommandType	commandType;

	@Override
	public void advance() {
		currentToken = commandIterator.next();
		commandType = currentToken.startsWith("@") ? CommandType.A_COMMAND :  CommandType.C_COMMAND;

		if (commandType == CommandType.A_COMMAND) {
			symbol = currentToken.substring(1);
			destination = null;
			comp = null;
			jump = null;
		} else {
			symbol = null;

			int tmpDest = currentToken.indexOf("=");
			destination = tmpDest > -1 ? currentToken.substring(0, tmpDest) : null;

			int tmpJmp = currentToken.indexOf(";");
			jump = tmpJmp > -1 ? currentToken.substring(tmpJmp + 1) : null;

			this.comp = currentToken.substring(tmpDest + 1, tmpJmp);
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
		return destination;
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
