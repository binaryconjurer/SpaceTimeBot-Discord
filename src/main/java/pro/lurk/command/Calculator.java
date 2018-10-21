package pro.lurk.SpaceTime;

import java.awt.Color;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Calculator extends ListenerAdapter {
	// Command prefix
	public static final String prefix = "!";
	public static int prefixL = prefix.length();
	public static final int prefixLength = prefixL - 1;

	// Pet Stats
	public int strength;
	public int intellect;
	public int agility;
	public int will;
	public int power;

	// Arrays and stray String
	int nums[] = { strength, intellect, agility, will, power };
	public static String split[];
	public static String numbers;

	// Sees if String is only numbers
	static Pattern numPattern = Pattern.compile("\\d+");

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		// We don't want to respond to other bot accounts, including ourself
		Message message = event.getMessage();
		String content = message.getContentRaw();
		// getContentRaw() is an atomic getter
		// getContentDisplay() is a lazy getter which modifies the content for e.g.
		// console view (strip discord formatting)

		// if (content.equals("!ping"))
		// {
		// MessageChannel channel = event.getChannel();
		// channel.sendMessage("Pong!").queue(); // Important to call .queue() on the
		// RestAction returned by sendMessage(...)
		// }

		// Catches all commands based on prefix
		if (content.startsWith(prefix)) {
			MessageChannel channel = event.getChannel();
			// !stats section for Pet Calulator, tons of baka proofing
			if (channel.getId().equals("443135403571281920") || channel.getId().equals("442845504813137921")) {
				if (content.startsWith(prefix + "stats")) {
					String petSyntaxError = ". The !stat command requires you to specify Strength, Intellect, Agility, Will, and Power."
							+ "\nExample: !stats 255 250 " + "260 " + "260 " + "250";
					// If the command is exactly !stats
					// appears to also work if a space was added.
					// Returns sytax error
					if (content.equals(prefix + "stats")) {

						Member member = event.getMember();

						channel.sendMessage(member.getAsMention() + petSyntaxError).queue();

						// If the user has input more then just !stats
					} else if (content.startsWith(prefix + "stats ")) {
						// Gets rid of prefix and stats leaving with remaining contents
						numbers = content.substring(prefixLength + 7);
						// Splits up all the hopefully numbers into an array
						String split[] = numbers.split(" ");

						// Checks if there are more then 5 elements, if so pop out an error message.
						if (split.length > 5 || split.length < 5) {

							Member member = event.getMember();
							channel.sendMessage(member.getAsMention() + petSyntaxError).queue();
						}
						// If a user inputs non numbers they get an error.
						else if (!isNumber(split)) {
							// System.out.println("FALSE YOU BAKA!");

							Member member = event.getMember();
							channel.sendMessage(member.getAsMention() + petSyntaxError).queue();
						}
						// Checks if the content in the String Array are all numbers, if so convert to
						// number array num
						else if (isNumber(split)) {
							stringToInt(split);
							// System.out.println("TEST");

							// If there are exactly 5 first check if they are negative or zero.
							if (nums.length == 5) {
								// Checks if there are any numbers containing zero or negative.
								// If so pop an error message
								if (isPositive(nums) == false) {

									Member member = event.getMember();
									channel.sendMessage(member.getAsMention() + petSyntaxError).queue();
								}
								// After checking that the user actually entered the proper syntax may info rain
								// upon all!
								else {
									// Assigns the stats from the array to the regular variables to be passed to
									// PetCalculations
									// Transfers the String numbers into real numbers in nums array

									strength = nums[0];
									intellect = nums[1];
									agility = nums[2];
									will = nums[3];
									power = nums[4];

									// We get the current channel, then print the message after formatting it! Hype
									// with all the emotes!
									// Note to self: Add

									channel.sendMessage(PetEmbed().build()).queue();
								}
							}
						}
					}
				}
			}

		}
	}

	// Checks if numbers in an array are positive
	public boolean isPositive(int[] nums) {
		for (int i : nums) {
			if (i <= 0) {
				return false;
			}
		}
		return true;
	}

	// Checks if there are just numbers in the split array
	public static boolean isNumber(String split[]) {
		int counter = 0;
		for (String str : split) {
			if (numPattern.matcher(str).matches()) {
				counter++;
			}
		}
		if (counter == split.length) {
			return true;
		}
		return false;

	}

	// Converts String Numbers to Ints
	public void stringToInt(String[] split) {
		if (!(split.length > 5 || split.length == 0)) {
			for (int i = 0; i < split.length; i++) {
				nums[i] = Integer.parseInt(split[i]);
			}
		}
	}

	// Formats the text all the pet talent value text into a readable format. Praise
	// Wampus!
	public EmbedBuilder PetEmbed() {
		// Create the EmbedBuilder instance

		EmbedBuilder embed = new EmbedBuilder();
		PetCalculations pc = new PetCalculations(strength, intellect, agility, will, power);
		/*
		 * Set the title: 1. Arg: title as string 2. Arg: URL as string or could also be
		 * null
		 */
		embed.setTitle("By Thetechman", "https://twitter.com/binaryconjurerv");

		/*
		 * Set the color
		 */
		embed.setColor(Color.red);
		embed.setColor(new Color(0xFF6419));
		embed.setColor(new Color(255, 100, 25));

		/*
		 * Set the text of the Embed: Arg: text as string
		 */
		embed.setDescription(
				"Below you will see the values of nearly every talent in the game based upon the stats you have entered!");

		/*
		 * Add fields to embed: 1. Arg: title as string 2. Arg: text as string 3. Arg:
		 * inline mode true / false
		 */
		// All of the Talents!!
		// Dealer
		embed.addField("Dealer:", "" + pc.getDealer() + " (" + Math.round(pc.getDealer()) + "%)", true);
		// Giver
		embed.addField("Giver:", "" + pc.getGiver() + " (" + Math.round(pc.getGiver()) + "%)", true);
		// Boon
		embed.addField("Boon:", "" + pc.getBoon() + " (" + Math.round(pc.getBoon()) + "%)", true);
		// Proof
		embed.addField("Proof:", "" + pc.getProof() + " (" + Math.round(pc.getProof()) + "%)", true);
		// Defy
		embed.addField("Defy:", "" + pc.getDefy() + " (" + Math.round(pc.getDefy()) + "%)", true);
		// Ward
		embed.addField("Ward:", "" + pc.getWard() + " (" + Math.round(pc.getWard()) + "%)", true);
		// Critical Striker
		embed.addField("Critical Striker:",
				"" + pc.getCriticalStriker() + " (" + Math.round(pc.getCriticalStriker()) + "%)", true);
		// Critical Hitter
		embed.addField("Critical Hitter:",
				"" + pc.getCriticalHitter() + " (" + Math.round(pc.getCriticalHitter()) + "%)", true);
		// School Assailiant
		embed.addField("School Assailiant:",
				"" + pc.getSchoolAssailiant() + " (" + Math.round(pc.getSchoolAssailiant()) + "%)", true);
		// School Striker
		embed.addField("School Striker:", "" + pc.getSchoolStriker() + " (" + Math.round(pc.getSchoolStriker()) + "%)",
				true);
		// Defender
		embed.addField("Defender:", "" + pc.getDefender() + " (" + Math.round(pc.getDefender()) + "%)", true);
		// Blocker
		embed.addField("Blocker:", "" + pc.getBlocker() + " (" + Math.round(pc.getBlocker()) + "%)", true);
		// Medic
		embed.addField("Medic:", "" + pc.getMedic() + " (" + Math.round(pc.getMedic()) + "%)", true);
		// Healer
		embed.addField("Healer:", "" + pc.getHealer() + " (" + Math.round(pc.getHealer()) + "%)", true);
		// Lively
		embed.addField("Lively:", "" + pc.getLively() + " (" + Math.round(pc.getLively()) + "%)", true);
		// Healthy
		embed.addField("Healthy:", "" + pc.getHealthy() + " (" + Math.round(pc.getHealthy()) + "%)", true);
		// Armor Breaker
		embed.addField("Armor Breaker:", "" + pc.getArmorBreaker() + " (" + Math.round(pc.getArmorBreaker()) + "%)", true);
		// Armor Piercer
		embed.addField("Armor Piercer:", "" + pc.getArmorPiercer() + " (" + Math.round(pc.getArmorPiercer()) + "%)",
				true);
		// Stun Recalcitrant
		embed.addField("Stun Recalcitrant:",
				"" + pc.getStunRecalcitrant() + " (" + Math.round(pc.getStunRecalcitrant()) + "%)", true);
		// Stun Resistant
		embed.addField("Stun Resistant:", "" + pc.getStunResistant() + " (" + Math.round(pc.getStunResistant()) + "%)",
				true);
		// Fishing Luck
				embed.addField("Fishing Luck:", "" + pc.getFishingLuck() + " (" + Math.round(pc.getFishingLuck()) + "%)",
						true);
		/*
		 * // Add Health embed.addField("Add Health:", "" + pc.getAddHealth() + " (" +
		 * Math.round(pc.getAddHealth()) + "%)", false); // Health Boost
		 * embed.addField("Health Boost:", "" + pc.getHealthBoost() + " (" +
		 * Math.round(pc.getHealthBoost()) + "%)", false); // Health Gift
		 * embed.addField("Health Gift:", "" + pc.getHealthGift() + " (" +
		 * Math.round(pc.getHealthGift()) + "%)", false); // Health Bounty
		 * embed.addField("Health Bounty:", "" + pc.getHealthBounty() + " (" +
		 * Math.round(pc.getHealthBounty()) + "%)", false); // Extra Mana
		 * embed.addField("Extra Mana:", "" + pc.getExtraMana() + " (" +
		 * Math.round(pc.getExtraMana()) + "%)", false); // Mana Boost
		 * embed.addField("Mana Boost:", "" + pc.getManaBoost() + " (" +
		 * Math.round(pc.getManaBoost()) + "%)", false); // Mana Bounty
		 * embed.addField("Mana Bounty:", "" + pc.getManaBounty() + " (" +
		 * Math.round(pc.getManaBounty()) + "%)", false); // Mana Gift
		 * embed.addField("Mana Gift:", "" + pc.getManaGift() + " (" +
		 * Math.round(pc.getManaGift()) + "%)", false);
		 */
		/*
		 * Add spacer like field Arg: inline mode true / false
		 */
		// embed.addBlankField(false);

		/*
		 * Add embed author: 1. Arg: name as string 2. Arg: url as string (can be null)
		 * 3. Arg: icon url as string (can be null)
		 */
		embed.setAuthor("Wizard101 Pet Stat Calculator", null, "https://i.imgur.com/AvgpKtj.png");

		/*
		 * Set footer: 1. Arg: text as string 2. icon url as string (can be null)
		 */
		// embed.setFooter("Text", "https://i.imgur.com/Sxfywla.png");

		/*
		 * Set image: Arg: image url as string
		 */
		// embed.setImage("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");

		/*
		 * Set thumbnail image: Arg: image url as string
		 */
		// embed.setThumbnail("https://i.imgur.com/dHj40UZ.png");

		return embed;
	}

}
