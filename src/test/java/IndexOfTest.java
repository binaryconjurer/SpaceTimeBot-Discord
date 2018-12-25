import java.util.ArrayList;

public class IndexOfTest {

	public static void main(String[] args) {
		String test1 = "Manders -f test -fd memes";
		String letter = "-f";
		ArrayList<Integer> locations = new ArrayList<Integer>();
		int loc = test1.indexOf(letter);
		
		while (loc != -1) {
			   locations.add(loc);
			   loc = test1.indexOf(letter, loc + 1);
			}
		
		System.out.println(locations);
	}
	
}
