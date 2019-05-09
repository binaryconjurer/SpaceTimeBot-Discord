package pro.lurk.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CustomEmbed {

	private String authorName = "";
	private String authorURL = "";
	private String authorIconURL = "";
	private String title = "";
	private String titleURL = "";
	private String description = "";
	private String color = "0xd96017";
	private String image = "";
	private String thumbnail = "";
	// private HashMap<String, String> fields = new HashMap<String, String>();
	private ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
	private String footer = "";
	private String footerIconURL = "";
	private long messageID = 0;

	private String[] commandArgs = { "author", "aURL", "aIconURL", "t", "tURL", "d", "c", "image", "thumbnail", "fn",
			"ft", "fd", "fi", "footer", "footerIconURL", "m" };

	// Creates an Empty Custom Embed with "Default settings"
	public CustomEmbed() {

	}

	// Creates a CustomEmbed based on data parsed from user
	public CustomEmbed(LinkedHashMap<String, ArrayList<String>> input) {
		// Checks to see if user has input the follow parms and puts them into the object
		if (!input.get("author").isEmpty()) {
			authorName = input.get("author").get(0);
		}
		if (!input.get("aURL").isEmpty()) {
			authorURL = input.get("aURL").get(0);
		}
		if (!input.get("aIconURL").isEmpty()) {
			authorIconURL = input.get("aIconURL").get(0);
		}
		if (!input.get("t").isEmpty()) {
			title = input.get("t").get(0);
		}
		if (!input.get("tURL").isEmpty()) {
			titleURL = input.get("tURL").get(0);
		}
		if (!input.get("d").isEmpty()) {
			description = input.get("d").get(0);
		}
		// TODO: Have logic to add hex without 0x and be able to use colors directly like blue
		if (!input.get("c").isEmpty()) {
			color = input.get("c").get(0);
		}
		if (!input.get("image").isEmpty()) {
			image = input.get("image").get(0);
		}
		if (!input.get("thumbnail").isEmpty()) {
			thumbnail = input.get("thumbnail").get(0);
		}

		if (!input.get("footer").isEmpty()) {
			footer = input.get("footer").get(0);
		}
		if (!input.get("footerIconURL").isEmpty()) {
			footerIconURL = input.get("footerIconURL").get(0);
		}
		// Fields
//		if (!input.get("-ft").isEmpty() && !input.get("-fd").isEmpty() && !input.get("-fi").isEmpty()) {
//			fields.add(new CustomEmbedField(1, input.get("-ft").get(0), input.get("-fd").get(0), false));
//		}
	}
	
	

	public String getFooterIconURL() {
		return footerIconURL;
	}

	public void setFooterIconURL(String footerIconURL) {
		this.footerIconURL = footerIconURL;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "EmbedHelper [getMessageID()=" + getMessageID() + ", getAuthorURL()=" + getAuthorURL()
				+ ", getAuthorIconURL()=" + getAuthorIconURL() + ", getTitle()=" + getTitle() + ", getTitleURL()="
				+ getTitleURL() + ", getDescription()=" + getDescription() + ", getColor()=" + getColor()
				+ ", getImage()=" + getImage() + ", getThumbnail()=" + getThumbnail() + ", getFields()=" + getFields()
				+ ", getAuthorName()=" + getAuthorName() + "]";
	}

	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorURL() {
		return authorURL;
	}

	public void setAuthorURL(String authorURL) {
		this.authorURL = authorURL;
	}

	public String getAuthorIconURL() {
		return authorIconURL;
	}

	public void setAuthorIconURL(String authorIconURL) {
		this.authorIconURL = authorIconURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleURL() {
		return titleURL;
	}

	public void setTitleURL(String titleURL) {
		this.titleURL = titleURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ArrayList<CustomEmbedField> getFields() {
		return fields;
	}

	public void setFields(ArrayList<CustomEmbedField> fields) {
		this.fields = fields;
	}

	public String getAuthorName() {
		return authorName;
	}
}
