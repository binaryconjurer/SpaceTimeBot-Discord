package pro.lurk.command;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Meow extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		MessageChannel channel = e.getChannel();
		channel.sendMessage("\"Meow! Meow!\" - Dese a Mander Cat").queue();
		
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(".meow");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Meow!";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

}
