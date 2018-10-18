package pro.lurk.SpaceTime;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Bot {
	public static void main(String[] arguments) throws Exception {
		ConfigLoader c = new ConfigLoader();

		JDA api = new JDABuilder(AccountType.BOT).setToken(c.getToken()).setGame(Game.playing("Time Travel"))
				.buildAsync();

		// Registers MyListern Event
		// Contains commands and custom logic for the bot
		api.addEventListener(new MyListener());

		//System.out.println("Working Directory = " + System.getProperty("user.dir"));

	}
}
