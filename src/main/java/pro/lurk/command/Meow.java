package pro.lurk.command;

import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.lurk.SpaceTime.Bot;

public class Meow extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		MessageChannel channel = e.getChannel();
		
		User user = e.getAuthor();
		User target = Bot.getAPI().getUserById("99939277932101632");

		String output = String.format("Meow? %s - <@99939277932101632>", user.getAsMention());
		channel.sendMessage(output).queue();
		String dmMessage = String.format("%s, has summoned you %s", user.getAsMention(), "<@99939277932101632> :fish:");
		sendPrivateMessage(target, dmMessage);

	}
	
	public void sendPrivateMessage(User user, String content)
	{
	    // openPrivateChannel provides a RestAction<PrivateChannel> 
	    // which means it supplies you with the resulting channel
	    user.openPrivateChannel().queue((channel) ->
	    {
	        // value is a parameter for the `accept(T channel)` method of our callback.
	        // here we implement the body of that method, which will be called later by JDA automatically.
	        channel.sendMessage(content).queue();
	        // here we access the enclosing scope variable -content-
	        // which was provided to sendPrivateMessage(User, String) as a parameter
	    });
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
