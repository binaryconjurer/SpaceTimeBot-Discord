package pro.lurk.command;

import java.util.HashMap;

public class EmbedHelper {
	private String authorName = "Thetechman";
	private String authorURL = "";
	private String authorIconURL = "";
	private String title = "";
	private String titleURL = "";
	private String description = "This is a description!";
	private String color = "d96017";
	private String image = "";
	private String thumbnail = "";
	private HashMap<String, String> fields = new HashMap<String, String>();
	private long messageID = 0;

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

	public HashMap<String, String> getFields() {
		return fields;
	}

	public void setFields(HashMap<String, String> fields) {
		this.fields = fields;
	}

	public String getAuthorName() {
		return authorName;
	}
}
