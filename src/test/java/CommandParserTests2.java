import java.util.ArrayList;
import java.util.LinkedHashMap;

import pro.lurk.util.CommandParser;

public class CommandParserTests2 {
	public static String[] commandArgs = { "a", "aURL", "aIconURL", "t", "tURL", "d", "c", "thumbnail", "f", "fd", "footer",
			"footerURL", "m" };

	public static String command1 = ".embed edit -t mander -t This is a title -d Manders are the greatest creatures alive! -c orange";
	public static String command2 = ".embed edit -t mander -f Mander Subdivision 1: -fd Praise tinu!";
	public static String command3 = ".embed edit";

	public static CommandParser manderPraser = new CommandParser(commandArgs);
	
	public static void main(String[] args) {

		LinkedHashMap<String, ArrayList<String>> set1 = new LinkedHashMap<String, ArrayList<String>>();
		set1 = manderPraser.parse(command1);
		System.out.println(set1);
//		LinkedHashMap<String, ArrayList<String>> set2 = new LinkedHashMap<String, ArrayList<String>>();
//		set2 = manderPraser.parse(command2);
//		System.out.println(set2);
//		LinkedHashMap<String, ArrayList<String>> set3 = new LinkedHashMap<String, ArrayList<String>>();
//		set3 = manderPraser.parse(command3);
//		System.out.println(set3);
		
		/*ArrayList<String> testString = set1.get("t");
		for (String s : testString) {
			System.out.println(s);
		}*/
	
	}

}
