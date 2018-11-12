import java.util.regex.Pattern;

public class Test2 {
	static Pattern numPattern = Pattern.compile("\\A\\d{1,3}(\\s+\\d{1,3}){4}\\z");
	static String[] numbers1 = {"!test","255","250","260","260","250"};

	public static void main(String[] args) {
		System.out.println(numPattern.matcher(getNumbers(numbers1)).matches());
	}

	private static String getNumbers(String[] args) {
		String full = null;
		for (int i = 1; args.length <= 5; i++) {
			full += args[i] = " ";
		}

		return "";
	}
}