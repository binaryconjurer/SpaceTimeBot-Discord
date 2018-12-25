import java.awt.Color;

import pro.lurk.command.EmbedHelper;
import pro.lurk.util.Database;

public class DbTest {
	
	public static void main(String args[]) {
		Database db = new Database();
		
		EmbedHelper helper = new EmbedHelper();
		
		helper = db.getbyTitle("Mander Demo");
		
		
		Color c = new Color (helper.getColor());
		//System.out.println(Integer.toHexString(helper.getColor()));
		
		
	}

}
