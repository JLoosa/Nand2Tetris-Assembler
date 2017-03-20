package com.gmail.Jacob6816.assembler.handlers.v2;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import com.gmail.Jacob6816.assembler.abstracts.AbstractCoder;
import com.gmail.Jacob6816.assembler.abstracts.AbstractSymbolTable;
import com.gmail.Jacob6816.assembler.abstracts.CommandType;

public class CoderV2 extends AbstractCoder {

	private AbstractSymbolTable	symbolTable;
	private Bindings			computations, destinations, jumps;

	public CoderV2(AbstractSymbolTable symbolTable) {
		this.symbolTable = symbolTable;

		// Computations are 6 bits plus a switch value
		this.computations = new SimpleBindings();
		computations.put("0", "101010");
		computations.put("1", "111111");
		computations.put("-1", "111010");
		computations.put("D", "001100");
		computations.put("A", "110000");
		computations.put("!D", "001101");
		computations.put("!A", "110001");
		computations.put("-D", "001111");
		computations.put("-A", "110011");
		computations.put("D+1", "011111");
		computations.put("A+1", "110111");
		computations.put("D-1", "001110");
		computations.put("A-1", "110010");
		computations.put("D+A", "000010");
		computations.put("D-A", "010011");
		computations.put("A-D", "000111");
		computations.put("D&A", "000000");
		computations.put("D|A", "010101");

		// Destinations are 3 bits
		this.destinations = new SimpleBindings();
		destinations.put("M", "001");
		destinations.put("D", "010");
		destinations.put("MD", "011");
		destinations.put("A", "100");
		destinations.put("AM", "101");
		destinations.put("AD", "110");
		destinations.put("AMD", "111");

		// Jumps are 3 bits
		this.jumps = new SimpleBindings();
		jumps.put("JGT", "001");
		jumps.put("JEQ", "010");
		jumps.put("JGE", "011");
		jumps.put("JLT", "100");
		jumps.put("JNE", "101");
		jumps.put("JLE", "110");
		jumps.put("JMP", "111");
	}

	@Override
	public String decode(CommandType commandType, String symbol, String destination, String comp, String jump) {
		StringBuilder builder = new StringBuilder(16);
		builder.append(commandType.getPrefix());
		if (commandType == CommandType.A_COMMAND) {
			builder.append(symbolTable.getAddress(symbol));
		} else {
			builder.append(getComp(comp));
			builder.append(getDest(destination));
			builder.append(getJump(jump));
		}
		return builder.toString();
	}

	@Override
	public String getDest(String mnemonic) {
		return destinations.getOrDefault(mnemonic, "000").toString();
	}

	@Override
	public String getComp(String mnemonic) {
		char start = '0';
		if (mnemonic.contains("M")) {
			start = '1';
			mnemonic = mnemonic.replace('M', 'A');
		}
		return start + computations.getOrDefault(mnemonic, "000000").toString();
	}

	@Override
	public String getJump(String mnemonic) {
		return jumps.getOrDefault(mnemonic, "000").toString();
	}

}
