package com.gmail.Jacob6816.assembler.handlers;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import com.gmail.Jacob6816.assembler.abstracts.AbstractCoder;
import com.gmail.Jacob6816.assembler.abstracts.AbstractSymbolTable;
import com.gmail.Jacob6816.assembler.abstracts.CommandType;

public class DefaultCoder extends AbstractCoder {

	private AbstractSymbolTable	symbolTable;
	private Bindings	compMap, destMap, jumpMap;

	public DefaultCoder(AbstractSymbolTable symbolTable) {

		this.symbolTable = symbolTable;

		this.compMap = new SimpleBindings();
		compMap.put("0", "101010");
		compMap.put("1", "111111");
		compMap.put("-1", "111010");
		compMap.put("D", "001100");
		compMap.put("A", "11000");
		compMap.put("!D", "001101");
		compMap.put("!A", "110001");
		compMap.put("-D", "001111");
		compMap.put("-A", "110011");
		compMap.put("D+1", "011111");
		compMap.put("A+1", "110111");
		compMap.put("D-1", "001110");
		compMap.put("A-1", "110010");
		compMap.put("D+A", "000010");
		compMap.put("D-A", "010011");
		compMap.put("A-D", "000111");
		compMap.put("D&A", "000000");
		compMap.put("D|A", "010101");

		this.destMap = new SimpleBindings();
		destMap.put("M", "001");
		destMap.put("D", "010");
		destMap.put("MD", "011");
		destMap.put("A", "100");
		destMap.put("AM", "101");
		destMap.put("AD", "110");
		destMap.put("AMD", "111");

		this.jumpMap = new SimpleBindings();
		jumpMap.put("JGT", "001");
		jumpMap.put("JEQ", "010");
		jumpMap.put("JGE", "011");
		jumpMap.put("JLT", "100");
		jumpMap.put("JNE", "101");
		jumpMap.put("JLE", "110");
		jumpMap.put("JMP", "111");
	}

	@Override
	public String decode(CommandType commandType, String symbol, String destination, String comp, String jump) {
		StringBuilder builder = new StringBuilder(16);
		builder.append(commandType.getPrefix());
		if (commandType == CommandType.A_COMMAND) parseA(builder, symbol);
		else parseCL(builder, destination, comp, jump);
		return builder.toString();
	}

	@Override
	public String getDest(String mnemonic) {
		if (mnemonic == null || mnemonic.length() == 0) return "000";
		return destMap.getOrDefault(mnemonic, "000").toString();
	}

	@Override
	public String getComp(String mnemonic) {
		int a = mnemonic.contains("M") ? 1 : 0;
		return a + compMap.getOrDefault(mnemonic.replaceAll("M", "A"), "000000").toString();
	}

	@Override
	public String getJump(String mnemonic) {
		if (mnemonic == null || mnemonic.length() == 0) return "000";
		return jumpMap.getOrDefault(mnemonic, "000").toString();
	}

	private void parseA(StringBuilder builder, String symbol) {
		builder.append(symbolTable.getAddress(symbol));
	}

	private void parseCL(StringBuilder builder, String destination, String comp, String jump) {
		builder.append(getComp(comp));
		builder.append(getDest(destination));
		builder.append(getJump(jump));
	}
}
