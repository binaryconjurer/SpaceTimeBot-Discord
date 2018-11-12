package pro.lurk.SpaceTime;

import java.util.regex.Pattern;

public class Meme2 {

	static String split[] = { "255", "250", "260", "260", "250" };

	static Pattern numPattern = Pattern.compile("\\d+");

	public static void main(String args[]) {
		System.out.println(isNumber(split));
	}

	// Checks if there are just numbers in the split array
	public static boolean isNumber(String split[]) {
		int counter = 0;
		for (String str : split) {
			if (numPattern.matcher(str).matches()) {
				counter++;
			}
		}
		if (counter == split.length) {
			return true;
		}
		return false;

	}
}
