/**
 * NOTE: Largely untested and possible non-functioning. Use the V2 instead
 */

package com.gmail.Jacob6816.assembler.handlers;

import java.util.HashMap;
import java.util.Map;

import com.gmail.Jacob6816.assembler.abstracts.AbstractSymbolTable;

public class DefaultST extends AbstractSymbolTable {

	protected Map<String, String>	map;
	public int						nextRamSlot;

	public DefaultST() {
		map = new HashMap<>();
		for (int i = 0; i < 16; i++)
			addEntry("R" + i, i); //R0 - R15
		addEntry("SCREEN", 16384);
		addEntry("KBD", 24576);
		addEntry("SP", 0);
		addEntry("LCL", 1);
		addEntry("ARG", 2);
		addEntry("THIS", 3);
		addEntry("THAT", 4);
		nextRamSlot = 16; // All the others are used by pre-allocation
	}

	public boolean addEntryToNextFree(String entry) {
		return addEntry(entry, intTo16Bit(nextRamSlot++));
	}

	public boolean addEntry(String entry, String value) {
		if (map.containsKey(entry)) return false;
		map.put(entry, value);
		System.out.println("[ST] Added entry to index " + value + ": " + entry);
		return true;
	}

	public void addEntry(String entry, int value) {
		addEntry(entry, intTo16Bit(value));
	}

	public boolean contains(String entry) {
		return map.containsKey(entry);
	}

	public String getAddress(String entry) {
		return map.get(entry);
	}

	public String getKeyEasy(String entry) {
		if (!map.containsKey(entry)) addEntryToNextFree(entry);
		return map.get(entry);
	}
}
