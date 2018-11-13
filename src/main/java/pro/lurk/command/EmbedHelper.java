package pro.lurk.command;

import java.util.HashMap;

public class EmbedHelper {

	private String authorName = "";
	private String authorURL = "";
	private String authorIconURL = "";
	private String title = "";
	private String titleURL = "";
	private String description = "";
	private int color = 0xd96017;
	private String image = "";
	private String thumbnail = "";
	private HashMap<String, String> fields = new HashMap<String, String>();
	private String footer = "";
	private String footerURL = "";

	public String getFooterURL() {
		return footerURL;
	}

	public void setFooterURL(String footerURL) {
		this.footerURL = footerURL;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	private long messageID = 0;

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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
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
