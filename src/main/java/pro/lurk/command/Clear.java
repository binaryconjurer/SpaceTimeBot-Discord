package pro.lurk.command;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Clear extends Command {
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		Message message = e.getMessage();
		MessageChannel channel = e.getChannel();

		// .clear 1
		if (args.length < 2) {
			// Usage
			EmbedBuilder usage = new EmbedBuilder();
			usage.setColor(0xff3923);
			usage.setTitle("Specify amount to delete");
			usage.setDescription("Usage: `" + "." + "clear [# of messages]`");
			channel.sendMessage(usage.build())
					.queue((sentMessage) -> sentMessage.delete().queueAfter(3, TimeUnit.SECONDS));
		} else {
			try {
				int num = Integer.parseInt(args[1]);
				List<Message> messages = channel.getHistory().retrievePast(num + 1).complete();
				channel.purgeMessages(messages);
				// Success
				EmbedBuilder success = new EmbedBuilder();
				success.setColor(0x22ff2a);
				 String title = String.format("✅ Successfully deleted %d %s.", num, (num > 1 ? "messages" : "message"));
//				success.setTitle("✅ Successfully deleted " + args[1] + " messages.");
				success.setTitle(title);
				channel.sendMessage(success.build())
						.queue((sentMessage) -> sentMessage.delete().queueAfter(3, TimeUnit.SECONDS));
			} catch (IllegalArgumentException ex) {
				if (ex.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
					// Too many messages
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("🔴 Too many messages selected");
					error.setDescription("Between 1-100 messages can be deleted at one time.");
					channel.sendMessage(error.build())
							.queue((sentMessage) -> sentMessage.delete().queueAfter(3, TimeUnit.SECONDS));
				} else {
					// Messages too old
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("🔴 Selected messages are older than 2 weeks");
					error.setDescription("Messages older than 2 weeks cannot be deleted.");
					channel.sendMessage(error.build())
							.queue((sentMessage) -> sentMessage.delete().queueAfter(3, TimeUnit.SECONDS));
				}
			}
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(".clear", ".remove", ".delete");
	}

	@Override
	public String getDescription() {
		return "Clears up to 100 messages in the room this command is called upon.";
	}

	@Override
	public String getName() {
		return "Clear Command";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("`.clear [# of messages]`");
	}

}
