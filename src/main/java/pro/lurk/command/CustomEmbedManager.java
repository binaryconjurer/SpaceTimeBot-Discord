package pro.lurk.command;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import pro.lurk.SpaceTime.Bot;
import pro.lurk.util.CommandParser;
import pro.lurk.util.Database;

public class CustomEmbedManager extends Command {

	private String commandName = ".embed";

	// Event specific information
	// private static Guild guild;
	// private static MessageChannel channel;
	// private static User author;
	// private static Message message;

	// Array of all possible commandArgs for standard embed actions
	private String[] commandArgs = { "author", "aURL", "aIconURL", "t", "tURL", "d", "c", "image", "thumbnail", "fn",
			"ft", "fd", "fi", "footer", "footerIconURL", "m", "o" };
	// private String[] fieldCommandArgs = { "#", "t", "m", "ft", "fd", "fi" };

	private Database db = new Database();
	private CommandParser commandParser = new CommandParser(commandArgs);
	// private CommandParser fieldCommandParser = new CommandParser(commandArgs);

	// Useful data that doesn't change
	String args[] = null;
	Guild guild = null;
	MessageChannel channel = null;
	User author = null;
	Message message = null;

	// Error Messages
	private String INVALID_ERROR_MESSAGE = "Your command is invalid, please try again!";
	private String ADD_ERROR_MESSAGE = "To add an embed you must speficy a title or Message ID. You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String EDIT_ERROR_MESSAGE = "To edit an embed you must speficy a title or Message ID. You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String DELETE_ERROR_MESSAGE = "To delete an embed you must speficy a title or Message ID. Use .help .embed for more info!";
	private String MISSING_CONTENTS_ERROR_MESSAGE = "Please specify the contents in your arugments!";
	// Field Error Messages
	private String FIELD_ERROR_MESSAGE = "To edit an embed you must speficy a title or Message ID alongside field args (TBD). You may include other options with additoinal arguments. Use .help .embed for more info!";
	private String FIELD_OVER25 = "You are trying to add an embed over the cap of 25. Please consider removing or modifying the existing stack.";

	@Override
	//TODO: Do all command checks before entering into operation specific actions
	//TODO: Once checks are done attempt to get an an CustomEmbed object ahead of time to prevent clutter later on
	//TODO: Refactor and make all the things easier to read!
	public void onCommand(MessageReceivedEvent event, String[] args) {
		// Sees if user has entered enough arguments
		// If not it returns an error message defined within that method
		this.args = args;
		guild = event.getGuild();
		channel = event.getChannel();
		author = event.getAuthor();
		message = event.getMessage();

		if (isEnoughArguments()) {

			String commandOperator = args[1].toLowerCase();
			switch (commandOperator) {
			case "add":
				addEmbed();
				break;
			case "edit":
				editEmbed();
				break;
			case "delete":
				deleteEmbed();
				break;
			case "field":
				modifyFields();
				break;
			// TODO: Create a force update method for situations where we need to just pull
			// whatever the db has
			case "forceupdate":
				forceUpdate();
				break;
			}
		}
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

	// Use this to add an embed to the system
	private void addEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed add
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + ADD_ERROR_MESSAGE).queue();
			return;
		}
		// This is the main logic behind adding a command and it's checks.
		if (args.length > 2) {
			// Parses user input and sorts through args into a usable object
			String userMessage = message.getContentDisplay();
			// The LinkedHashMap holds each key as any of the possible args, each key points
			// to an ArrayList holding any Strings that are to that arg
			LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();
			commandArgumentsFromUser = commandParser.parse(userMessage);
			// If user doesn't detail what he wants for any arugment.
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Add new by Title
			if (!commandArgumentsFromUser.get("t").isEmpty()) {
				// Creates CustomEmbed from parse, sends it to Discord while recording it's
				// message id, then saving to database.
				CustomEmbed helper = new CustomEmbed(commandArgumentsFromUser);
				channel.sendMessage(makeDiscordFormattedEmbed(helper).build()).queue();
				helper.setMessageID(getLastMessageIDByUser(channel, Bot.getAPI().getSelfUser()));
				db.save(helper);
				return;
			}
		}
	}

	private void editEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed edit
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + EDIT_ERROR_MESSAGE).queue();
			return;
		}
		// This is the main logic behind adding a command and it's checks.
		if (args.length > 2) {
			String userMessage = message.getContentDisplay();
			LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();
			// Parses user input
			commandArgumentsFromUser = commandParser.parse(userMessage);
			// Converts message to lower case so .startsWith methods are case insensitive
			userMessage = userMessage.toLowerCase();
			// If user doesn't detail what he wants for any arugment.
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Edit by Title
			if (userMessage.startsWith(".embed edit -t")) {
				CustomEmbed helper = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
				CustomEmbed updatedHelper = editCustomEmbed(helper, commandArgumentsFromUser, "t");
				embedUpdate(updatedHelper, channel);
				db.save(updatedHelper);
				return;
			}
			// Edit by MessageID
			if (userMessage.startsWith(".embed edit -m")) {
				CustomEmbed helper = db.getByMessageID(commandArgumentsFromUser.get("m").get(0));
				CustomEmbed updatedHelper = editCustomEmbed(helper, commandArgumentsFromUser, "m");
				embedUpdate(updatedHelper, channel);
				db.save(updatedHelper);
				return;
			}
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
			String userMessage = message.getContentDisplay();
			LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();
			// Parses user input
			commandArgumentsFromUser = commandParser.parse(userMessage);
			// Converts message to lower case so .startsWith methods are case insensitive
			userMessage = userMessage.toLowerCase();

			// Operation arg is always 4th (5th) value.
			String commandOperator = args[4].toLowerCase();
			switch (commandOperator) {
			case "-add":
				addField(commandArgumentsFromUser);
				break;
			case "-edit":
				editField(commandArgumentsFromUser);
				break;
			case "-insert":
				System.out.println();
				break;
			case "-delete":
				System.out.println();
				break;
			}
		}
	}

	private void addField(LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser) {
		String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
		String fieldDescription = commandArgumentsFromUser.get("fd").get(0);
		boolean inline = false;
		// Edit by type is always the 2nd (3rd) value.
		if (args[2].equalsIgnoreCase("-t")) {
			CustomEmbed helper = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = helper.getFields();
			int fieldSize = fields.size();
			if (fieldSize < 25) {
				CustomEmbedField newField = new CustomEmbedField(fieldTitle, fieldDescription, inline);
				fields.add(newField);
				helper.setFields(fields);
				embedUpdate(helper, channel);
				db.save(helper);
				return;
			}
			if (fieldSize == 25) {
				channel.sendMessage(author.getAsMention() + ", " + FIELD_OVER25).queue();
				return;
			}

		}
	}

	private void editField(LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser) {
		String fieldTitle = commandArgumentsFromUser.get("ft").get(0);
		String fieldDescription = commandArgumentsFromUser.get("fd").get(0);
		boolean inline = false;
//		inline = Boolean.parseBoolean(commandArgumentsFromUser.get("fi").get(0));
		
		if (args[2].equalsIgnoreCase("-t")) {
			CustomEmbed helper = db.getbyTitle(commandArgumentsFromUser.get("t").get(0));
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
			fields = helper.getFields();
			int fieldSize = fields.size();
			for (int i = 0; i < fieldSize; i++) {
				if (fields.get(i).getFieldTitle().equals(fieldTitle)) {
					if (!commandArgumentsFromUser.get("ft").get(1).isEmpty()) {
						fields.get(i).setFieldTitle(commandArgumentsFromUser.get("ft").get(1));
					}
					fields.get(i).setFieldTitle(fieldTitle);
					fields.get(i).setFieldDescription(fieldDescription);
				}
				
			}
			
			helper.setFields(fields);
			embedUpdate(helper, channel);
			db.save(helper);
			return;
		}
	}

	private void deleteEmbed() {
		// If the user has passed no additional arguments provide error message
		// Ex: .embed delete
		if (args.length == 2) {
			channel.sendMessage(author.getAsMention() + ", " + DELETE_ERROR_MESSAGE).queue();
			return;
		}
		if (args.length > 2) {
			String userMessage = message.getContentDisplay();
			LinkedHashMap<String, ArrayList<String>> commandArgumentsFromUser = new LinkedHashMap<String, ArrayList<String>>();
			// Parses user input
			commandArgumentsFromUser = commandParser.parse(userMessage);
			// Converts message to lower case so .startsWith methods are case insensitive
			userMessage = userMessage.toLowerCase();
			// If user doesn't detail what he wants for any arugment.
			if (commandParser.isCommandEmpty(commandArgumentsFromUser)) {
				channel.sendMessage(author.getAsMention() + ", " + MISSING_CONTENTS_ERROR_MESSAGE).queue();
				return;
			}
			// Delete by Title
			if (userMessage.startsWith(".embed delete -t")) {
				String embedTitle = commandArgumentsFromUser.get("t").get(0);
				CustomEmbed helper = db.getbyTitle(embedTitle);
				channel.deleteMessageById(helper.getMessageID()).queue();
				db.deleteByTitle(embedTitle);
				return;
			}
			// Delete by MessageID
			if (userMessage.startsWith(".embed delete -m")) {
				long embedMessageID = Long.parseLong(commandArgumentsFromUser.get("m").get(0));
				channel.deleteMessageById(embedMessageID).queue();
				db.deleteByMessageID(embedMessageID);
				return;
			}
		}
	}

	private void forceUpdate() {
		// embedUpdate(helper, channel);
	}

	private CustomEmbed editCustomEmbed(CustomEmbed helper, LinkedHashMap<String, ArrayList<String>> input,
			String editType) {
		// Editing by Title and there is a new Title
		if (editType.equals("t")) {
			//
			if (!input.get("t").isEmpty() && input.get("t").size() == 2) {
				helper.setTitle(input.get("t").get(1));
			}
		}
		// Editing by MessageID and there is a title arg
		if (editType.equals("m")) {
			if (!input.get("t").isEmpty() && input.get("t").size() == 1) {
				helper.setTitle(input.get("t").get(0));
			}
		}
		if (!input.get("t").isEmpty()) {
			helper.setTitle(input.get("t").get(0));
		}

		if (!input.get("author").isEmpty()) {
			helper.setAuthorName(input.get("author").get(0));
		}
		if (!input.get("aURL").isEmpty()) {
			helper.setAuthorURL(input.get("aURL").get(0));
		}
		if (!input.get("aIconURL").isEmpty()) {
			helper.setAuthorIconURL(input.get("aIconURL").get(0));
		}

		if (!input.get("tURL").isEmpty()) {
			helper.setTitleURL(input.get("tURL").get(0));
		}
		if (!input.get("d").isEmpty()) {
			helper.setDescription(input.get("d").get(0));
		}
		if (!input.get("c").isEmpty()) {
			helper.setColor(input.get("c").get(0));
		}
		if (!input.get("image").isEmpty()) {
			helper.setImage(input.get("image").get(0));
		}
		if (!input.get("thumbnail").isEmpty()) {
			helper.setThumbnail(input.get("thumbnail").get(0));
		}
		// Fields
		// TODO: Add proper field management
		if (!input.get("fn").isEmpty() && !input.get("f").isEmpty() && !input.get("fd").isEmpty()) {
			int fieldOrder = Integer.parseInt(input.get("fn").get(0));
			String fieldName = input.get("f").get(0);
			String fieldDescriptor = input.get("fd").get(0);
			ArrayList<CustomEmbedField> fields = new ArrayList<CustomEmbedField>();
		}
		if (!input.get("footer").isEmpty()) {
			helper.setFooter(input.get("footer").get(0));
		}
		if (!input.get("footerIconURL").isEmpty()) {
			helper.setFooterIconURL(input.get("footerIconURL").get(0));
		}
		return helper;
	}

	private void embedUpdate(CustomEmbed helper, MessageChannel channel) {
		channel.editMessageById(helper.getMessageID(), makeDiscordFormattedEmbed(helper).build()).queue();
	}

	private EmbedBuilder makeDiscordFormattedEmbed(CustomEmbed helper) {
		EmbedBuilder customEmbed = new EmbedBuilder();
		// Just AuthorName

		customEmbed.setColor(new Color(Integer.decode(helper.getColor())));

		// Just Author
		if (!helper.getAuthorName().isEmpty() && helper.getAuthorURL().isEmpty()) {
			customEmbed.setAuthor(helper.getAuthorName());
		}
		// AuthorName and AuthorURL
		if (!helper.getAuthorName().isEmpty() && !helper.getAuthorURL().isEmpty()) {
			customEmbed.setAuthor(helper.getAuthorName(), helper.getAuthorURL());
		}
		// AuthorName and AuthorIconURL
		if (!helper.getAuthorName().isEmpty() && helper.getAuthorURL().isEmpty()
				&& !helper.getAuthorIconURL().isEmpty()) {
			customEmbed.setAuthor(helper.getAuthorName(), null, helper.getAuthorIconURL());
		}
		// AuthorName, AuthorURL, and AuthorIconURL
		if (!helper.getAuthorName().isEmpty() && !helper.getAuthorURL().isEmpty()
				&& !helper.getAuthorIconURL().isEmpty()) {
			customEmbed.setAuthor(helper.getAuthorName(), helper.getAuthorURL(), helper.getAuthorIconURL());
		}
		// Just Title
		if (!helper.getTitle().isEmpty() && helper.getTitleURL().isEmpty()) {
			customEmbed.setTitle(helper.getTitle());
		}
		// Title and TitleURL
		if (!helper.getTitle().isEmpty() && !helper.getTitleURL().isEmpty()) {
			customEmbed.setTitle(helper.getTitle(), helper.getTitleURL());
		}
		// Description
		if (!helper.getDescription().isEmpty()) {
			customEmbed.setDescription(helper.getDescription());
		}

		// Image
		if (!helper.getImage().isEmpty()) {
			customEmbed.setImage(helper.getImage());
		}
		// Thumbnail
		if (!helper.getThumbnail().isEmpty()) {
			customEmbed.setThumbnail(helper.getThumbnail());
		}
		// Just Footer
		if (!helper.getFooter().isEmpty() && helper.getFooterIconURL().isEmpty()) {
			customEmbed.setFooter(helper.getFooter(), null);
		}
		// Footer and Footer IconURL
		if (!helper.getFooter().isEmpty() && !helper.getFooterIconURL().isEmpty()) {
			customEmbed.setFooter(helper.getFooter(), helper.getFooterIconURL());
		}
		if (!helper.getFields().isEmpty()) {
			ArrayList<CustomEmbedField> fields = helper.getFields();
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
