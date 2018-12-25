package pro.lurk.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// CommandParser purpose is to parse any user string and give back an array of arg to value in a usable way.
public class CommandParser {
	public String[] commandArgs = null;

	// Give an array of commandArgs to suite the user needs.
	public CommandParser(String[] commandArgs) {
		this.commandArgs = commandArgs;
	}

	// Default creation. Though not something you should do as we need to have the right commandArgs to parse.
	public CommandParser() {
		this.commandArgs = null;

	}

	// Splits by -
	private String[] splitByHyphen(String command) {
		String[] split = command.split("-");
		return split;
	}

	// Takes in user input and type of arugment and returns a list of contents
	// related per argument.
	// Generally output will just be one item for things like color, things like
	// titles will most likely result in multiple contents due to modular
	// design.
	private ArrayList<String> getCommandArgumentContents(String[] command, String argType) {
		ArrayList<String> listOfContents = new ArrayList<String>();
		String contents = null;

		for (String section : command) {
			if (section.startsWith(argType)) {
				contents = section.substring(argType.length() + 1);
				contents = contents.trim();
				listOfContents.add(contents);
			}
		}
		return listOfContents;
	}

	// Main way to interact with the command parser. Simply enter the user's input
	// and the command will be parsed to the type of args passed upon object
	// creation.
	public LinkedHashMap<String, ArrayList<String>> parse(String command) {

		LinkedHashMap<String, ArrayList<String>> finalArgs = new LinkedHashMap<String, ArrayList<String>>();

		String commandSplit[] = splitByHyphen(command);

		for (String argument : commandArgs) {
			ArrayList<String> listOfContents = new ArrayList<String>();
			finalArgs.put(argument, getCommandArgumentContents(commandSplit, argument));
		}

		return finalArgs;
	}

}
