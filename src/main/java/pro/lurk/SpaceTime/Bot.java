package pro.lurk.SpaceTime;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.EventListener;

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
			jdaBuilder.addEventListener(new Calculator());
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
