package pro.lurk.command.CustomEmbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.lurk.SpaceTime.Bot;
import pro.lurk.command.Command;
import pro.lurk.util.CommandParser;
import pro.lurk.util.Database;

public class CustomEmbedManager extends Command {

	private String commandName = ".embed";

	// Array of all possible commandArgs for standard embed actions
	private String[] commandArgs = { "author", "aURL", "aIconURL", "t", "tURL", "d", "c", "image", "thumbnail", "fn",
			"ft", "fd", "fi", "footer", "footerIconURL", "m", "o", "insert", "swap" };

	private LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();

	private Database db = new Database();
	private CommandParser commandParser = new CommandParser(commandArgs);
	// private CommandParser fieldCommandParser = new CommandParser(commandArgs);

	// Useful data that doesn't change
	private String args[] = null;
	private Guild guild = null;
	private MessageChannel channel = null;
	private User author = null;
	private Message message = null;
	private String userMessage = "";
	private String userMessageLower = "";

	// Error Messages
	private String INVALID_ERROR_MESSAGE = ".embed requires you to use an operation like add, edit, delete, forceupdate, or field followed by at least one argument!";
	private String ADD_ERROR_MESSAGE = "To add an embed you must speficy a title or Message ID. You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String EDIT_ERROR_MESSAGE = "To edit an embed you must speficy a title or Message ID. You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String DELETE_ERROR_MESSAGE = "To delete an embed you must speficy a title or Message ID. Use .help .embed for more info!";
	private String MISSING_CONTENTS_ERROR_MESSAGE = "Please specify the contents in your arugment(s)!";
	private String FORCE_UPDATE_ERROR_MESSAGE = "Please specify which embed you wish to force update!";
	// Field Error Messages
	private String FIELD_ERROR_MESSAGE = "To edit an embed you must speficy a title alongside field args -ft, -fd, or -fi . You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String FIELD_OVER25_ERROR_MESSAGE = "You are trying to add an embed over the cap of 25. Please consider removing or modifying the existing stack.";
	private String FIELD_NEGATIVE_NUMBER_INPUT_ERROR_MESSAGE = "You have input a negative number for your operation, please use a number from 1-25.";

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) {
		// Sees if user has entered enough arguments
		// If not it returns an error message defined within that method
		this.args = args;
		guild = event.getGuild();
		channel = event.getChannel();
		author = event.getAuthor();
		message = event.getMessage();

		this.userMessage = message.getContentRaw();
		this.userMessageLower = userMessage.toLowerCase();

		if (isEnoughArguments() && isValidOperation()) {
			// Parses user input
			this.commandArgumentsFromUser = parseUserInput();
			// Checks to see which command operation the user wishes to perform.
			// Ex: .embed add
			String commandOperator = args[1].toLowerCase();
			switch (commandOperator) {
			// .embed add
			case "add":
				addEmbed();
				break;
			// .embed edit
			case "edit":
				editEmbed();
				break;
			// .embed list
			case "list":
				listEmbeds();
				break;
			// .embed delete
			case "delete":
				deleteEmbed();
				break;
			// .embed forceupdate
			case "forceupdate":
				forceUpdate();
				break;
			// .embed field
			case "field":
				modifyFields();
				break;
			// TODO: Add list, copy, move, and swap.
			case "copy":
				break;
			case "move":
				break;
			case "swap":
				break;
			}
		}

	}

	// Use this to add an embed to the system.
	private void addEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed add
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + ADD_ERROR_MESSAGE).queue();
			return;
		}
		// This is the main logic behind adding a command and it's checks.
		if (args.length > 2) {
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Add new by Title
			if (!commandArgumentsFromUser.get("t").isEmpty()) {
				// Creates CustomEmbed from parse, sends it to Discord while recording it's
				// message id, then saving to database.
				CustomEmbed discordEmbed = new CustomEmbed(commandArgumentsFromUser);
				channel.sendMessage(makeDiscordFormattedEmbed(discordEmbed).build()).queue();
				discordEmbed.setMessageID(getLastMessageIDByUser(channel, Bot.getAPI().getSelfUser()));
				db.save(discordEmbed);
				return;
			}
		}
	}

	// Use this to edit any non field property of an embed.
	private void editEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed edit
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + EDIT_ERROR_MESSAGE).queue();
			return;
		}
		// This is the main logic behind adding a command and it's checks.
		if (args.length > 2) {
			// Converts message to lower case so .startsWith methods are case insensitive
			// If user doesn't detail what he wants for any arugment.
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Edit by Title
			if (userMessageLower.startsWith(".embed edit -t")) {
				CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
				discordEmbed.configCustomEmbed(commandArgumentsFromUser);
				embedUpdate(discordEmbed, channel);
				db.save(discordEmbed);
				return;
			}
		}
	}

	// List all the embeds in the database.
	private void listEmbeds() {

		if (userMessageLower.equals(".embed list")) {
			// Creates CustomEmbed from parse, sends it to Discord while recording it's
			// message id, then saving to database.
			ArrayList<String> customEmbedList = new ArrayList<String>();
			customEmbedList = db.getListOfCustomEmbeds();

			String formattedEmbeds = String.join(", ", customEmbedList);
			channel.sendMessageFormat("%s, Here is a list of the custom embeds in the database: %s.",
					author.getAsMention(), formattedEmbeds).queue();
			return;
		}
	}

	// Use this to delete an embed from Discord and the db.
	private void deleteEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed delete
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + DELETE_ERROR_MESSAGE).queue();
			return;
		}
		if (args.length > 2) {
			// If user doesn't detail what he wants for any arugment.
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Delete by Title
			if (userMessageLower.startsWith(".embed delete -t")) {
				String embedTitle = commandArgumentsFromUser.get("t").get(0);
				CustomEmbed discordEmbed = db.getbyTitle(embedTitle);
				channel.deleteMessageById(discordEmbed.getMessageID()).queue();
				db.deleteByTitle(embedTitle);
				return;
			}
		}
	}

	// Use this to force update a change to an embed from the db to Discord.
	private void forceUpdate() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed delete
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + FORCE_UPDATE_ERROR_MESSAGE).queue();
			return;
		}
		if (args.length > 2) {
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			embedUpdate(discordEmbed, channel);
		}
	}

	private void modifyFields() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed field
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + FIELD_ERROR_MESSAGE).queue();
			return;
		}
		// This is the main logic behind adding a command and it's checks.
		if (args.length > 2) {
			// Command Syntax:
			// .embed field -add
			String commandOperator = args[2].toLowerCase();

			switch (commandOperator) {
			// .embed field add -t meow -ft meow -fd meow2
			case "add":
				addField();
				break;
			// Command Syntax:
			// .embed field edit -t meow -ft meow -ft meow2 -fd meow3
			case "edit":
				editField();
				break;
			// Command Syntax:
			// .embed field -insert [#] -t meow -ft meow
			case "-insert":
				insertField();
				break;
			// Command Syntax:
			// .embed field -swap [#] [#] -t meow
			case "-swap":
				swapField();
				break;
			// Command Syntax:
			// .embed field delete -t meow -ft meow
			case "delete":
				deleteField();
				break;
			}
		}
	}

	private void addField() {
		String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
		String fieldDescription = commandArgumentsFromUser.get("fd").get(0);
		// Sometimes inline isn't set so by default it's false, if it is specified used
		// that value instead.
		boolean isInline = false;
		if (!commandArgumentsFromUser.get("fi").isEmpty()) {
			isInline = Boolean.parseBoolean(commandArgumentsFromUser.get("fi").get(0));
		}

		// Add by title
		if (userMessageLower.startsWith(".embed field add -t")) {
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = discordEmbed.getFields();
			int fieldSize = fields.size();
			if (fieldSize < 25) {
				CustomEmbedField newField = new CustomEmbedField(fieldTitle, fieldDescription, isInline);
				fields.add(newField);
				discordEmbed.setFields(fields);
				embedUpdate(discordEmbed, channel);
				db.save(discordEmbed);
				return;
			}
			if (fieldSize == 25) {
				channel.sendMessage(author.getAsMention() + ", " + FIELD_OVER25_ERROR_MESSAGE).queue();
				return;
			}

		}
	}

	private void editField() {
		// Sometimes inline isn't set so by default it's false, if it is specified used
		// that value instead.
		boolean isInline = false;
		if (!commandArgumentsFromUser.get("fi").isEmpty()) {
			isInline = Boolean.parseBoolean(commandArgumentsFromUser.get("fi").get(0));
		}

		if (userMessageLower.startsWith(".embed field edit -t")) {
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
			String fieldDescription = "";

			fields = discordEmbed.getFields();
			int fieldSize = fields.size();
			// Search for the existing field title in order to edit.
			for (int i = 0; i < fieldSize; i++) {
				if (fields.get(i).getFieldTitle().equals(fieldTitle)) {
					if (commandArgumentsFromUser.get("ft").size() > 1) {
						fields.get(i).setFieldTitle(commandArgumentsFromUser.get("ft").get(1));
					}
					// If user enters in a fieldDescription change it out.
					if (!commandArgumentsFromUser.get("fd").isEmpty()) {
						fieldDescription = commandArgumentsFromUser.get("fd").get(0);
						fields.get(i).setFieldDescription(fieldDescription);
					}
					if (!commandArgumentsFromUser.get("fi").isEmpty()) {
						isInline = Boolean.parseBoolean(commandArgumentsFromUser.get("fi").get(0));
						fields.get(i).setInline(isInline);
					}
					// If user enters in two field titles, one to search by, the other is the one to
					// switch out.
					// Also sets the field description from the user.
				}
			}
			discordEmbed.setFields(fields);
			embedUpdate(discordEmbed, channel);
			db.save(discordEmbed);
			return;
		}
	}

	private void insertField() {
		int insertNumber = Integer.parseInt(commandArgumentsFromUser.get("insert").get(0)) - 1;
		boolean inline = false;

		// Checks to see if the user is using 1-25 for their insert.
		if (insertNumber < 0) {
			channel.sendMessage(author.getAsMention() + ", " + FIELD_NEGATIVE_NUMBER_INPUT_ERROR_MESSAGE).queue();
			return;
		}

		if (userMessageLower.startsWith(".embed field -insert")) {
			String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
			String fieldDescription = commandArgumentsFromUser.get("fd").get(0);
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = discordEmbed.getFields();
			int fieldSize = fields.size();
			if (fieldSize < 25) {
				CustomEmbedField newField = new CustomEmbedField(fieldTitle, fieldDescription, inline);
				fields.add(insertNumber, newField);
				discordEmbed.setFields(fields);
				embedUpdate(discordEmbed, channel);
				db.save(discordEmbed);
				return;
			}
			if (fieldSize == 25) {
				channel.sendMessage(author.getAsMention() + ", " + FIELD_OVER25_ERROR_MESSAGE).queue();
				return;
			}

		}
	}

	private void swapField() {
		ArrayList<String> swapValues = commandArgumentsFromUser.get("swap");
		String swapNums = swapValues.get(0);
		String[] swapArray = swapNums.split(" ");

		int swap1 = Integer.parseInt(swapArray[0]) - 1;
		int swap2 = Integer.parseInt(swapArray[1]) - 1;
		boolean inline = false;

		// Checks to see if the user is using 1-25 for their swap.
		if (swap1 < 0 || swap2 < 0) {
			channel.sendMessage(author.getAsMention() + ", " + FIELD_NEGATIVE_NUMBER_INPUT_ERROR_MESSAGE).queue();
			return;
		}
		if (userMessageLower.startsWith(".embed field -swap")) {
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = discordEmbed.getFields();
			Collections.swap(fields, swap1, swap2);
			discordEmbed.setFields(fields);
			embedUpdate(discordEmbed, channel);
			db.save(discordEmbed);
			return;
		}
	}

	private void deleteField() {
		String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
		if (userMessageLower.startsWith(".embed field delete -t")) {
			CustomEmbed discordEmbed = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = discordEmbed.getFields();
			int fieldSize = fields.size();
			for (int i = 0; i < fieldSize; i++) {
				if (fields.get(i).getFieldTitle().equals(fieldTitle)) {
					fields.remove(i);
					break;
				}
			}
			discordEmbed.setFields(fields);
			embedUpdate(discordEmbed, channel);
			db.save(discordEmbed);
			return;

		}
	}

	private void embedUpdate(CustomEmbed discordEmbed, MessageChannel channel) {
		channel.editMessageById(discordEmbed.getMessageID(), makeDiscordFormattedEmbed(discordEmbed).build()).queue();
	}

	private EmbedBuilder makeDiscordFormattedEmbed(CustomEmbed discordEmbed) {
		EmbedBuilder customEmbed = new EmbedBuilder();
		// Just AuthorName

		// customEmbed.setTimestamp(Instant.now());

		customEmbed.setColor(discordEmbed.getColor());

		// Just Author
		if (!discordEmbed.getAuthorName().isEmpty() && discordEmbed.getAuthorURL().isEmpty()) {
			customEmbed.setAuthor(discordEmbed.getAuthorName());
		}
		// AuthorName and AuthorURL
		if (!discordEmbed.getAuthorName().isEmpty() && !discordEmbed.getAuthorURL().isEmpty()) {
			customEmbed.setAuthor(discordEmbed.getAuthorName(), discordEmbed.getAuthorURL());
		}
		// AuthorName and AuthorIconURL
		if (!discordEmbed.getAuthorName().isEmpty() && discordEmbed.getAuthorURL().isEmpty()
				&& !discordEmbed.getAuthorIconURL().isEmpty()) {
			customEmbed.setAuthor(discordEmbed.getAuthorName(), null, discordEmbed.getAuthorIconURL());
		}
		// AuthorName, AuthorURL, and AuthorIconURL
		if (!discordEmbed.getAuthorName().isEmpty() && !discordEmbed.getAuthorURL().isEmpty()
				&& !discordEmbed.getAuthorIconURL().isEmpty()) {
			customEmbed.setAuthor(discordEmbed.getAuthorName(), discordEmbed.getAuthorURL(),
					discordEmbed.getAuthorIconURL());
		}
		// Just Title
		if (!discordEmbed.getTitle().isEmpty() && discordEmbed.getTitleURL().isEmpty()) {
			customEmbed.setTitle(discordEmbed.getTitle());
		}
		// Title and TitleURL
		if (!discordEmbed.getTitle().isEmpty() && !discordEmbed.getTitleURL().isEmpty()) {
			customEmbed.setTitle(discordEmbed.getTitle(), discordEmbed.getTitleURL());
		}
		// Description
		if (!discordEmbed.getDescription().isEmpty()) {
			customEmbed.setDescription(discordEmbed.getDescription());
		}

		// Image
		if (!discordEmbed.getImage().isEmpty()) {
			customEmbed.setImage(discordEmbed.getImage());
		}
		// Thumbnail
		if (!discordEmbed.getThumbnail().isEmpty()) {
			customEmbed.setThumbnail(discordEmbed.getThumbnail());
		}
		// Just Footer
		if (!discordEmbed.getFooter().isEmpty() && discordEmbed.getFooterIconURL().isEmpty()) {
			customEmbed.setFooter(discordEmbed.getFooter(), null);
		}
		// Footer and Footer IconURL
		if (!discordEmbed.getFooter().isEmpty() && !discordEmbed.getFooterIconURL().isEmpty()) {
			customEmbed.setFooter(discordEmbed.getFooter(), discordEmbed.getFooterIconURL());
		}
		if (!discordEmbed.getFields().isEmpty()) {
			ArrayList<CustomEmbedField> fields = discordEmbed.getFields();
			for (CustomEmbedField field : fields) {
				// Sets the title, description, and set inline status.
				customEmbed.addField(field.getFieldTitle(), field.getFieldDescription(), field.isInline());
			}
		}
		return customEmbed;
	}

	private long getLastMessageIDByUser(MessageChannel channel, User user) {
		for (Message message : channel.getIterableHistory()) {
			if (message.getAuthor().equals(user)) {
				return message.getIdLong();
			}
		}
		return -1;
	}

	// Parses user input into a LinkedHashMap with key being the tag leading to a
	// ArrayList<String> of it's matching info
	private LinkedHashMap<String, ArrayList<String>> parseUserInput() {
		LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();
		// Parses user input
		commandArgumentsFromUser = commandParser.parse(userMessage);
		return commandArgumentsFromUser;

	}

	// Checks to see if the user has submitted enough arguments
	private boolean isEnoughArguments() {
		// Triggers when the user has less then the required arguments
		// Ex:
		// .embed
		// .embed add
		if (args.length < 2) {
			channel.sendMessage(author.getAsMention() + ", " + INVALID_ERROR_MESSAGE).queue();
			return false;
		}
		// When the user has entered enough arguments 3 or more items.
		// Ex: .embed add -t Cool Links
		else {
			return true;
		}
	}

	private boolean isValidOperation() {
		// Triggers when the user has less then the required arguments
		// Ex:
		// .embed
		// .embed add
		// When the user has entered enough arguments 3 or more items.
		// Ex: .embed add -t Cool Links
		if (args[1].equals("add") || args[1].equals("edit") || args[1].equals("delete") || args[1].equals("forceupdate")
				|| args[1].equals("field") || args[1].equals("list")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(".embed", ".embedman");
	}

	@Override
	public String getDescription() {

		return "This command manages all custom embeds. Allowing you to create embeds with custom fields, among being able to add and delete them after creation.";
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
