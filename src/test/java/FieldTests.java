import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pro.lurk.command.CustomEmbedField;

public class FieldTests {

	public static void main(String[] args) {

		ArrayList<CustomEmbedField> embedFieldList = new ArrayList<CustomEmbedField>();
		embedFieldList.add(new CustomEmbedField(1, "Mander", "Scepter", false));
		embedFieldList.add(new CustomEmbedField(10, "cirPrise", "A Cirno Emote!", false));
		embedFieldList.add(new CustomEmbedField(3, "Scholar", "Shield Healer", false));
		embedFieldList.add(new CustomEmbedField(2, "White Mage", "HoT Healer - Powerful", false));
		embedFieldList.add(new CustomEmbedField(4, "Astrologian", "HoT / Shild Healer", false));

		Collections.sort(embedFieldList);
		System.out.println(embedFieldList);
		// for (CustomEmbedField field : embedFieldList) {
		// System.out.println(field);
		// }

		String json = toJson(embedFieldList);
		System.out.println(json);

		ArrayList<CustomEmbedField> mander = new ArrayList<CustomEmbedField>();
		mander = jsonToObject(json);
		mander.add(new CustomEmbedField(7, "json", "works!", false));
		System.out.println(mander);

	}

	public static String toJson(ArrayList<CustomEmbedField> list) {
		String json = new Gson().toJson(list);
		return json;
	}

	public static ArrayList<CustomEmbedField> jsonToObject(String json) {
		Type myType = new TypeToken<ArrayList<CustomEmbedField>>() {}.getType();
		ArrayList<CustomEmbedField> list = new Gson().fromJson(json, myType);
		return list;
	}

}
