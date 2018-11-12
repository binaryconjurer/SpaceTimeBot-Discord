package pro.lurk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.Gson;

import pro.lurk.command.EmbedHelper;

public class Database {

	private String url = "jdbc:sqlite:config/SpaceTimeBot.db";

	private String customEmbeds = "CREATE TABLE IF NOT EXISTS customEmbeds (authorName text, authorURL text, authorIconURL text, title text,"
			+ " titleURL text, description text, color text, image text, thumbnail text, fields text, messageID int primary key)";

	private Connection connect = null;

	public Database() {
		this.connect = connect();
		System.out.println("Connceted!");
		createTables();
		System.out.println("Created Tables!");

	}

	public String convertToJson(HashMap<String, String> map) {
		JSONObject json = new JSONObject(map);
		return json.toString();

	}

	public HashMap<String, String> convertJsonToHashMap(String json) {
		HashMap<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map = (HashMap<String, String>) gson.fromJson(json, HashMap.class);
		return map;
	}

	public EmbedHelper getbyTitle(String title) {
		EmbedHelper helper = new EmbedHelper();

		String select = "SELECT * FROM customEmbeds WHERE title = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(select)) {
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				helper.setAuthorName(rs.getString("authorName"));
				helper.setAuthorURL(rs.getString("authorURL"));
				helper.setAuthorIconURL(rs.getString("authorURL"));
				helper.setTitle(rs.getString("title"));
				helper.setTitleURL(rs.getString("titleURL"));
				helper.setDescription(rs.getString("description"));
				helper.setAuthorName(rs.getString("color"));
				helper.setImage(rs.getString("image"));
				helper.setThumbnail(rs.getString("thumbnail"));
				helper.setFields(convertJsonToHashMap(rs.getString("fields")));
				helper.setMessageID(rs.getLong("messageID"));
			}
			return helper;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return helper;
	}

	public void save(EmbedHelper helper) {
		String operation = "INSERT OR REPLACE INTO customEmbeds(authorName, authorURL, authorIconURL, title, titleURL, description, color, image, thumbnail, fields, messageID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

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
			pstmt.setString(10, convertToJson(helper.getFields()));
			pstmt.setLong(11, helper.getMessageID());
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
