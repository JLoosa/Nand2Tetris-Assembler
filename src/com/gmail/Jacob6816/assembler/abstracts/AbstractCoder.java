package com.gmail.Jacob6816.assembler.abstracts;

public abstract class AbstractCoder {

	public abstract String decode(CommandType commandType, String symbol, String destination, String comp, String jump);

	public abstract String getDest(String mnemonic);

	public abstract String getComp(String mnemonic);

	public abstract String getJump(String mnemonic);

}
