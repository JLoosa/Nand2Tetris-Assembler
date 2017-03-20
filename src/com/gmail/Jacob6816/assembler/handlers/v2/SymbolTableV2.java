package com.gmail.Jacob6816.assembler.handlers.v2;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import com.gmail.Jacob6816.assembler.abstracts.AbstractSymbolTable;

public class SymbolTableV2 extends AbstractSymbolTable {

	private Bindings	mapping;
	public int			nextRamSlot;

	public SymbolTableV2() {
		mapping = new SimpleBindings();
		for (int i = 0; i < 16; i++)
			mapping.put("R" + i, intTo16Bit(i));
		mapping.put("SCREEN", intTo16Bit(16384));
		mapping.put("KBD", intTo16Bit(24576));
		mapping.put("SP", intTo16Bit(0));
		mapping.put("LCL", intTo16Bit(1));
		mapping.put("ARG", intTo16Bit(2));
		mapping.put("THIS", intTo16Bit(3));
		mapping.put("THAT", intTo16Bit(4));

		nextRamSlot = 16;
	}

	@Override
	public boolean addEntry(String entry, String value) {
		entry = entry.toUpperCase();
		if (mapping.containsKey(entry)) return false;
		mapping.put(entry, value);
		return true;
	}

	@Override
	public boolean contains(String entry) {
		entry = entry.toUpperCase();
		return mapping.containsKey(entry);
	}

	@Override
	public String getAddress(String entry) {
		entry = entry.toUpperCase();
		return mapping.getOrDefault(entry, intTo16Bit(0)).toString();
	}

	@Override
	public boolean addEntryToNextFree(String entry) {
		if (addEntry(entry, intTo16Bit(nextRamSlot))) {
			nextRamSlot++;
			return true;
		}
		return false;
	}

}
