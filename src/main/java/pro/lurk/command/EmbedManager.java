package pro.lurk.command;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.lurk.SpaceTime.Bot;
import pro.lurk.util.Database;

public class EmbedManager extends Command {

	public Database db = new Database();

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) {

		// Event specific information
		User author = event.getAuthor(); // The user that sent the message
		Message message = event.getMessage(); // The message that was received.
		MessageChannel channel = event.getChannel(); // This is the MessageChannel that the message was sent to.
		// Guild guild = event.getGuild();
		// Nothing
		if (args.length == 1) {
			channel.sendMessage("Baka try again!").queue();
		}
		// .embed add
		else if (args[1].equalsIgnoreCase("add") && args.length == 2) {
			// TODO Add better help message
			channel.sendMessage(author.getAsMention()
					+ ", Please add an embed with at least a title, and a description if you like!").queue();
			;
		}
		// ADD
		// .embed add [title]
		else if (args[1].equalsIgnoreCase("add") && args[1].equals("add") && args.length == 3) {
			EmbedHelper helper = new EmbedHelper();
			helper.setTitle(getDescription(args, message));
			channel.sendMessage(minnalEmbed(helper).build()).queue();
			helper.setMessageID(getLastMessageIDByUser(channel, Bot.getAPI().getSelfUser()));
			db.save(helper);

		}
		// .embed add [title] [description]
		else if (args[1].equalsIgnoreCase("add") && args.length >= 4) {
			EmbedHelper helper = new EmbedHelper();
			helper.setTitle(getDescription(args, message));
			helper.setDescription(getDescription(args, message));
			channel.sendMessage(minnalEmbed(helper).build()).queue();
			helper.setMessageID(getLastMessageIDByUser(channel, Bot.getAPI().getSelfUser()));
			db.save(helper);
		}
		// .embed edit [title]
		else if (args[1].equalsIgnoreCase("add") && args.length == 2) {
			
		}

	}
	
	private String getTitle(String args[], Message message) {

		int messageLocation = args[0].length() + 1 + args[1].length() + 1;
		String fullDescription = message.getContentDisplay().substring(messageLocation);
		return fullDescription;
	}

	private String getDescription(String args[], Message message) {

		int messageLocation = args[0].length() + 1 + args[1].length() + 1 + args[2].length() + 1;
		String fullDescription = message.getContentDisplay().substring(messageLocation);
		return fullDescription;
	}

	private EmbedBuilder minnalEmbed(EmbedHelper helper) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor(helper.getAuthorName());
		embed.setTitle(helper.getTitle());
		embed.setDescription(helper.getDescription());
		embed.setColor(new Color(Integer.parseInt(helper.getColor(), 16)));
		embed.addField("Field Title", "This is text!", false);
		return embed;
	}

	private long getLastMessageIDByUser(MessageChannel channel, User user) {
		for (Message message : channel.getIterableHistory()) {
			if (message.getAuthor().equals(user)) {
				return message.getIdLong();
			}
		}
		return -1;
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(".embed", ".embedman");
	}

	@Override
	public String getDescription() {

		return "This command manages all custom embeds. Allowing you to create embeds with custom fields, among being able to add and remove them after creation.";
	}

	@Override
	public String getName() {

		return "Embed Manager™";
	}

	@Override
	public List<String> getUsageInstructions() {

		return null;
	}

}
