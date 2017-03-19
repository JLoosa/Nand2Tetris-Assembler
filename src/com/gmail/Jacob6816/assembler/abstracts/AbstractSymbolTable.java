package com.gmail.Jacob6816.assembler.abstracts;

public abstract class AbstractSymbolTable {

	public String intTo16Bit(int value) {
		StringBuilder sb = new StringBuilder(16);
		sb.append(Integer.toBinaryString(value));
		sb.reverse();
		while (sb.length() < 15)
			sb.append("0");
		sb.reverse();
		return sb.toString();
	}

	public abstract boolean addEntryToNextFree(String entry);

	public abstract boolean addEntry(String entry, String value);

	public abstract boolean contains(String entry);

	public abstract String getAddress(String entry);
}
