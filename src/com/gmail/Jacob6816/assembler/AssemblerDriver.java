package com.gmail.Jacob6816.assembler;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gmail.Jacob6816.assembler.abstracts.AbstractCoder;
import com.gmail.Jacob6816.assembler.abstracts.AbstractParser;
import com.gmail.Jacob6816.assembler.abstracts.AbstractSymbolTable;
import com.gmail.Jacob6816.assembler.handlers.v2.CoderV2;
import com.gmail.Jacob6816.assembler.handlers.v2.ParserV2;
import com.gmail.Jacob6816.assembler.handlers.v2.SymbolTableV2;

public class AssemblerDriver {

	protected static AbstractParser			dataParser	= null;
	protected static AbstractSymbolTable	symbolTable	= null;
	protected static AbstractCoder			dataCoder	= null;

	public static void main(String[] args) throws Exception {

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembler Files", ".asm");
		fileChooser.addChoosableFileFilter(filter);

		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// TODO Handle file not found
			return;
		}

		// Initialization
		File asmFile = fileChooser.getSelectedFile();
		Scanner asm = new Scanner(asmFile);
		symbolTable = new SymbolTableV2();
		dataCoder = new CoderV2(symbolTable);

		List<String> parsables = new ArrayList<String>();
		// Handle pre-processing
		while (asm.hasNext()) {
			String next = asm.nextLine().toUpperCase().replaceAll("\\s", "");
			if (next.contains("/")) next = next.substring(0, next.indexOf("/"));
			if (next.length() == 0) continue;

			System.out.println(next);
			if (next.startsWith("(")) {
				symbolTable.addEntryToNextFree(next.substring(1, next.length() - 1));
				continue;
			}
			if (next.contains(",")) next = next.replace(',', ';');
			if (next.startsWith("@")) symbolTable.addEntryToNextFree(next.substring(1));
			parsables.add(next);
		}

		File output = new File(asmFile.getAbsolutePath().substring(0, asmFile.getAbsolutePath().lastIndexOf('.')) + ".hack");
		if (output.exists()) output.delete();
		output.createNewFile();
		PrintWriter writer = new PrintWriter(output);
		dataParser = new ParserV2(parsables);
		asm.close();
		asm = null;
		asmFile = null;

		// Handle the information processing
		System.out.println("BEGINNING PROCESSING");
		while (dataParser.hasMoreCommands()) {
			dataParser.advance();
			String codedOutput = dataCoder.decode(dataParser.getCommandType(), dataParser.getSymbol(), dataParser.getDest(), dataParser.getComp(), dataParser.getJump());
			writer.println(codedOutput);
			System.out.println(codedOutput);
		}

		writer.close();
		writer = null;
		System.out.println(output.getAbsolutePath());
		output = null;
	}

}
