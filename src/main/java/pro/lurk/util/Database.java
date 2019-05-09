package pro.lurk.util;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pro.lurk.command.CustomEmbed;
import pro.lurk.command.CustomEmbedField;

public class Database {

	private String url = "jdbc:sqlite:config/SpaceTimeBot.db";

	private String customEmbeds = "CREATE TABLE IF NOT EXISTS customEmbeds (authorName text, authorURL text, authorIconURL text, title text,"
			+ " titleURL text, description text, color text, image text, thumbnail text, fields text, footer text, footerIconURL text, messageID integer primary key)";

	private Connection connect = null;

	private Gson gson = new Gson();
	private Type customEmbedFieldType = new TypeToken<ArrayList<CustomEmbedField>>() {
	}.getType();

	public Database() {
		this.connect = connect();
		System.out.println("Connceted!");
		createTables();
		System.out.println("Created Tables!");
	}

	private String toJson(ArrayList<CustomEmbedField> list) {
		String json = gson.toJson(list);
		return json;
	}

	private ArrayList<CustomEmbedField> jsonToArrayList(String json) {
		ArrayList<CustomEmbedField> list = gson.fromJson(json, customEmbedFieldType);
		return list;
	}

	public void deleteByTitle(String title) {
		String delete = "DELETE FROM customEmbeds WHERE title = ?";

		try (Connection conn = this.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(delete);
			pstmt.setString(1, title);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteByMessageID(long messageID) {
		String delete = "DELETE FROM customEmbeds WHERE messageID = ?";

		try (Connection conn = this.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(delete);
			pstmt.setLong(1, messageID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CustomEmbed getbyTitle(String title) {
		CustomEmbed helper = new CustomEmbed();

		String select = "SELECT * FROM customEmbeds WHERE title = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(select)) {
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				helper.setAuthorName(rs.getString("authorName"));
				helper.setAuthorURL(rs.getString("authorURL"));
				helper.setAuthorIconURL(rs.getString("authorIconURL"));
				helper.setTitle(rs.getString("title"));
				helper.setTitleURL(rs.getString("titleURL"));
				helper.setDescription(rs.getString("description"));
				helper.setColor(rs.getString("color"));
				helper.setImage(rs.getString("image"));
				helper.setThumbnail(rs.getString("thumbnail"));
				helper.setFields(jsonToArrayList(rs.getString("fields")));
				helper.setFooter(rs.getString("footer"));
				helper.setFooterIconURL(rs.getString("footerIconURL"));
				helper.setMessageID(rs.getLong("messageID"));
			}
			return helper;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return helper;
	}

	public CustomEmbed getByMessageID(String messageID) {
		CustomEmbed helper = new CustomEmbed();

		String select = "SELECT * FROM customEmbeds WHERE messageID = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(select)) {
			pstmt.setString(1, messageID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				helper.setAuthorName(rs.getString("authorName"));
				helper.setAuthorURL(rs.getString("authorURL"));
				helper.setAuthorIconURL(rs.getString("authorURL"));
				helper.setTitle(rs.getString("title"));
				helper.setTitleURL(rs.getString("titleURL"));
				helper.setDescription(rs.getString("description"));
				helper.setColor(rs.getString("color"));
				helper.setImage(rs.getString("image"));
				helper.setThumbnail(rs.getString("thumbnail"));
				helper.setFields(jsonToArrayList(rs.getString("fields")));
				helper.setFooter(rs.getString("footer"));
				helper.setFooterIconURL(rs.getString("footerIconURL"));
				helper.setMessageID(rs.getLong("messageID"));
			}
			return helper;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return helper;
	}

	// Adds or updates embeds into customEmbeds table
	public void save(CustomEmbed helper) {
		String operation = "INSERT OR REPLACE INTO customEmbeds(authorName, authorURL, authorIconURL, title, titleURL, description, color, image, thumbnail, fields, footer, footerIconURL, messageID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(operation)) {
			pstmt.setString(1, helper.getAuthorName());
			pstmt.setString(2, helper.getAuthorURL());
			pstmt.setString(3, helper.getAuthorIconURL());
			pstmt.setString(4, helper.getTitle());
			pstmt.setString(5, helper.getTitleURL());
			pstmt.setString(6, helper.getDescription());
			pstmt.setString(7, helper.getColor());
			pstmt.setString(8, helper.getImage());
			pstmt.setString(9, helper.getThumbnail());
			pstmt.setString(10, toJson(helper.getFields()));
			pstmt.setString(11, helper.getFooter());
			pstmt.setString(12, helper.getFooterIconURL());
			pstmt.setLong(13, helper.getMessageID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void createTables() {
		try (Connection conn = this.connect();) {
			Statement stmt = conn.createStatement();
			stmt.execute(customEmbeds);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
