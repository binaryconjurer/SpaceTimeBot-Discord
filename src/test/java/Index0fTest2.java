public class Index0fTest2 {
	public static void main(String[] args) {
		String str = "Manders -f test -fd memes";
		String[] arg = str.split("-");

		for (String s : arg) {
			System.out.println(s);
		}
	}
}