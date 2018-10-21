package pro.lurk.SpaceTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

// Improved Config Loader, much cleaner then previous design.
// Could still use improvement in the future.

public class ConfigLoader {

	protected Properties prop = new Properties();
	private String token;

	public ConfigLoader() {
		load();
	}

	private void load() {
		InputStream input = null;
		try {
			File config = new File("config/config.properties");

			// If config doesn't exist make a new one and ask user for bot token
			if (!config.exists()) {
				config.createNewFile();
				FileOutputStream oFile = new FileOutputStream(config, false);

				input = new FileInputStream(config);
				prop.load(input);

				System.out.println("Please enter in your Discord Bot token in order to login!");
				Scanner scan = new Scanner(System.in);
				token = scan.nextLine();
				scan.close();
				prop.put("token", token);
				System.out.println("Token has been recieved, saving...");
				prop.store(oFile, null);
				oFile.close();
				load();
			} else {
				input = new FileInputStream(config);
				// load a properties file
				prop.load(input);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("config.properties not found, please make a new one and include token=token");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Returns token
	public String getToken() {
		return prop.getProperty("token");
	}

}
