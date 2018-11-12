import java.util.HashMap;

import pro.lurk.command.EmbedHelper;
import pro.lurk.util.Database;

public class MapTest {

	public static void main(String[] args) {

		Database db = new Database();
		HashMap<String, String> map = new HashMap<String, String>();		
		map.put("VoHiYo", "Emote");
		map.put("cirBaka","Dumb");
//		System.out.println(map);
//		
//		String json = db.convertToJson(map);
//		System.out.println(json);
//		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2 = db.convertJsonToHashMap(json);
//		System.out.println(map2);
		
		EmbedHelper helper = new EmbedHelper();
		helper.setTitle("Thetechman");
		helper.setTitleURL("https://twitch.tv/thetechboy");
		helper.setTitle("Tech Demo");
		helper.setDescription("An amazing description!");
		helper.setColor("0x6edac0");
		helper.setFields(map);
		helper.setMessageID(511401944582193153L);
		db.save(helper);

	}

}
