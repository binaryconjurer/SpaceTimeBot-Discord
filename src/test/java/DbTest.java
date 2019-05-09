import pro.lurk.command.CustomEmbed;
import pro.lurk.util.Database;

public class DbTest {

	public static void main(String args[]) {
		Database db = new Database();
		
		CustomEmbed helper = db.getbyTitle("A Title");
		

		/*CustomEmbed helper = new CustomEmbed();

		helper.setTitle("A Title");
		;
		helper.setDescription("Testing");
		helper.setColor("0x9400D3");
		ArrayList<CustomEmbedField> embedFieldList = new ArrayList<CustomEmbedField>();
		embedFieldList.add(new CustomEmbedField(1, "Mander", "Scepter", false));
		embedFieldList.add(new CustomEmbedField(10, "cirPrise", "A Cirno Emote!", false));
		embedFieldList.add(new CustomEmbedField(3, "Scholar", "Shield Healer", false));
		embedFieldList.add(new CustomEmbedField(2, "White Mage", "HoT Healer - Powerful", false));
		embedFieldList.add(new CustomEmbedField(4, "Astrologian", "HoT / Shild Healer", false));

		helper.setFields(embedFieldList);
		System.out.println(helper);
		db.save(helper);*/

	}

}
