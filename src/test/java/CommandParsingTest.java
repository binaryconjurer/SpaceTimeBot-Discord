import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommandParsingTest {

	public static String command1 = ".embed edit -t mander -t This is a title -d Manders are the greatest creatures alive! -c orange";
	public static String command2 = ".embed edit -t mander -f Mander Subdivision 1: -fd Praise tinu!";
	public static String[] commandArgs = { "-t", "-d", "-c", "-f", "-fd" };

	public static void main(String[] args) {
		// LinkedHashMap<String, ArrayList<String>> theCommandArgs = parse(command1);
		LinkedHashMap<String, ArrayList<String>> theCommandArgs1 = parse(command2);
		// System.out.println(theCommandArgs);
		System.out.println(theCommandArgs1);
		System.out.println(theCommandArgs1.get("-f").get(0));

	}

	public static LinkedHashMap<String, ArrayList<String>> parse(String command) {

		LinkedHashMap<String, ArrayList<String>> args = new LinkedHashMap<String, ArrayList<String>>();

		for (String argument : commandArgs) {
			ArrayList<Integer> amountofArugmentType = howManyOfArgument(argument, command);
			ArrayList<String> theWords = getTypeArg(amountofArugmentType, command, argument);
			args.put(argument, theWords);
		}

		return args;
	}

	public static ArrayList<String> getTypeArg(ArrayList<Integer> argLocations, String command, String argument) {
		ArrayList<String> items = new ArrayList<String>();
		int size = argLocations.size();
		int nextLocation = 0;

		for (int location : argLocations) {
			// If input is only one location.
			if (size == 1) {
				// Captures words in a String with multiple args of different types.
				// -t -d
				if (command.substring(location + 1).contains("-")) {
					int end = command.indexOf("-", location + 1);
					String words = command.substring(location + argument.length() + 1, end);
					words = words.trim();
					items.add(words);
				}
				// Only one arg
				else {
					String words = command.substring(location + argument.length() + 1);
					items.add(words);
					words = words.trim();
				}

			}
			// When input has multiple locations.
			else if (size > 1) {
				nextLocation = command.indexOf(argument, location + 1);
				String words = command.substring(location + argument.length() + 1, nextLocation);
				words = words.trim();
				items.add(words);
				size--;
			}
		}
		return items;
	}

	public static ArrayList<Integer> howManyOfArgument(String argument, String command) {
		int location = command.indexOf(argument);
		ArrayList<Integer> locations = new ArrayList<Integer>();
		// TODO: Figure out a way to exclude ending with arg
		if (argument.equals("-f")) {
			locations.add(location);
			return locations;
		}
		else if (!argument.equals("-f")) {
			while (location != -1) {
				locations.add(location);
				location = command.indexOf(argument, location + 1);

			}
			return locations;
		}
		return null;
	}

}
