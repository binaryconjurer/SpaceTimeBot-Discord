import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		String pattern = "\\A\\d{1,3}(\\s+\\d{1,3}){4}\\z";

		// Passing
		System.out.println(Pattern.matches(pattern, "133 12 13 13 13"));
		System.out.println(Pattern.matches(pattern, "1 2 3 4 5"));
		System.out.println(Pattern.matches(pattern, "100 200 300 400 500"));

		// Failing
		System.out.println(Pattern.matches(pattern, "100 200 3000 400 500"));
		System.out.println(Pattern.matches(pattern, "1 2 3"));

	}
}
