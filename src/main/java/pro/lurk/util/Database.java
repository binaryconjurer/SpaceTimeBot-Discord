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
		CustomEmbed discordEmbed = new CustomEmbed();

		String select = "SELECT * FROM customEmbeds WHERE title = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(select)) {
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				discordEmbed.setAuthorName(rs.getString("authorName"));
				discordEmbed.setAuthorURL(rs.getString("authorURL"));
				discordEmbed.setAuthorIconURL(rs.getString("authorIconURL"));
				discordEmbed.setTitle(rs.getString("title"));
				discordEmbed.setTitleURL(rs.getString("titleURL"));
				discordEmbed.setDescription(rs.getString("description"));
				discordEmbed.setColor(rs.getString("color"));
				discordEmbed.setImage(rs.getString("image"));
				discordEmbed.setThumbnail(rs.getString("thumbnail"));
				discordEmbed.setFields(jsonToArrayList(rs.getString("fields")));
				discordEmbed.setFooter(rs.getString("footer"));
				discordEmbed.setFooterIconURL(rs.getString("footerIconURL"));
				discordEmbed.setMessageID(rs.getLong("messageID"));
			}
			return discordEmbed;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return discordEmbed;
	}

	public CustomEmbed getByMessageID(String messageID) {
		CustomEmbed discordEmbed = new CustomEmbed();

		String select = "SELECT * FROM customEmbeds WHERE messageID = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(select)) {
			pstmt.setString(1, messageID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				discordEmbed.setAuthorName(rs.getString("authorName"));
				discordEmbed.setAuthorURL(rs.getString("authorURL"));
				discordEmbed.setAuthorIconURL(rs.getString("authorURL"));
				discordEmbed.setTitle(rs.getString("title"));
				discordEmbed.setTitleURL(rs.getString("titleURL"));
				discordEmbed.setDescription(rs.getString("description"));
				discordEmbed.setColor(rs.getString("color"));
				discordEmbed.setImage(rs.getString("image"));
				discordEmbed.setThumbnail(rs.getString("thumbnail"));
				discordEmbed.setFields(jsonToArrayList(rs.getString("fields")));
				discordEmbed.setFooter(rs.getString("footer"));
				discordEmbed.setFooterIconURL(rs.getString("footerIconURL"));
				discordEmbed.setMessageID(rs.getLong("messageID"));
			}
			return discordEmbed;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return discordEmbed;
	}

	// Adds or updates embeds into customEmbeds table
	public void save(CustomEmbed discordEmbed) {
		String operation = "INSERT OR REPLACE INTO customEmbeds(authorName, authorURL, authorIconURL, title, titleURL, description, color, image, thumbnail, fields, footer, footerIconURL, messageID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(operation)) {
			pstmt.setString(1, discordEmbed.getAuthorName());
			pstmt.setString(2, discordEmbed.getAuthorURL());
			pstmt.setString(3, discordEmbed.getAuthorIconURL());
			pstmt.setString(4, discordEmbed.getTitle());
			pstmt.setString(5, discordEmbed.getTitleURL());
			pstmt.setString(6, discordEmbed.getDescription());
			pstmt.setString(7, Integer.toString(discordEmbed.getColor().getRGB()));
			pstmt.setString(8, discordEmbed.getImage());
			pstmt.setString(9, discordEmbed.getThumbnail());
			pstmt.setString(10, toJson(discordEmbed.getFields()));
			pstmt.setString(11, discordEmbed.getFooter());
			pstmt.setString(12, discordEmbed.getFooterIconURL());
			pstmt.setLong(13, discordEmbed.getMessageID());
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
