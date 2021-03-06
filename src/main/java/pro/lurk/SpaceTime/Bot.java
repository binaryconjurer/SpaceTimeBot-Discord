package pro.lurk.SpaceTime;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import pro.lurk.command.Calculator;
import pro.lurk.util.ConfigLoader;
import pro.lurk.command.HelpCommand;

public class Bot {
	private static JDA api;

	public static JDA getAPI() {
		return api;
	}

	public static void main(String[] arguments) throws Exception {

		setupBot();
	}

	// Setups the bot by retriving config data, logging in, setting the game, and adding listener(s) for functionality.
	private static void setupBot() throws IOException {
		ConfigLoader config;
		try {
			config = new ConfigLoader();
			JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken(config.getToken());
			// Set initial game playing
			jdaBuilder.setGame(Game.playing("Time Travel!"));
			// Setup Event Listeners
			HelpCommand help = new HelpCommand();
			jdaBuilder.addEventListener(help.registerCommand(help));
			jdaBuilder.addEventListener(help.registerCommand(new Calculator()));
			// Finally build
			api = jdaBuilder.build();
			api.awaitReady();
			// TODO Actually handle the expectations
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
