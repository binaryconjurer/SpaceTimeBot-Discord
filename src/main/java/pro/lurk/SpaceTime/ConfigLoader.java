package pro.lurk.SpaceTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class ConfigLoader {

	// Config File
	File config = new File("config/token.cfg");

	//

	private String TOKEN;

	public ConfigLoader() throws IOException {

		if (!config.isFile() && !config.createNewFile()) {
			throw new IOException("Error creating new file: " + config.getAbsolutePath());
		}

		BufferedReader r = new BufferedReader(new FileReader(config));
		try {
			// read data
			readToken();
		} finally {
			r.close();
		}

	}
	// Gets the token from the config file
	private void readToken() {
		try (FileReader reader = new FileReader(config)) {
			Properties prop = new Properties();
			prop.load(reader);
			this.TOKEN = prop.getProperty("token");
		} catch (Exception e) {
			e.printStackTrace();

		}

		//return TOKEN;
	}
	// Gets the token from object
	public String getToken() {
		return TOKEN;
	}
}
