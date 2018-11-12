package pro.lurk.command;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Calculator extends Command {
	// Pet Stats
	public int strength;
	public int intellect;
	public int agility;
	public int will;
	public int power;

	private static String title[] = { "By Thetechman", "https://twitter.com/binaryconjurer" };
	private static String description = "Below you will see the values of nearly every talent in the game based upon the stats you have entered!";
	private static String[] author = { "Wizard101 Pet Stat Calculator", "https://i.imgur.com/AvgpKtj.png" };

	String petSyntaxError = ". The !stat command requires you to specify Strength, Intellect, Agility, Will, and Power."
			+ "\nExample: !stats 255 250 " + "260 " + "260 " + "250";

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		MessageChannel channel = e.getChannel();
		Member member = e.getMember();
		if (channel.getId().equals("443135403571281920")) {
			if (args.length == 6) {
				Optional<List<Integer>> stats = formatStats(args);
				if (stats.isPresent()) {
					// Assigns the stats from the array to the regular variables to be passed to
					// PetCalculations
					// Transfers the String numbers into real numbers in nums array
					strength = stats.get().get(0);
					intellect = stats.get().get(1);
					agility = stats.get().get(2);
					will = stats.get().get(3);
					power = stats.get().get(4);
					// We get the current channel, then print the message after formatting it! Hype
					// with all the emotes!
					// Note to self: Add
					channel.sendMessage(PetEmbed().build()).queue();
				}
			} else {
				channel.sendMessage(member.getAsMention() + petSyntaxError).queue();
			}

		}
	}

	private static Optional<List<Integer>> formatStats(String[] args) {
		List<Integer> result = Arrays.stream(args) // convert the array to a stream
				.skip(1) // ignore the first element, which isn't part of what we're validating
				// .limit(5) // inspect only the next 5 elements
				.filter(s -> s.matches("\\A\\d{1,3}\\z")).map(Integer::parseInt).collect(Collectors.toList()); // ensure
																												// everything
																												// remaining
																												// matches
		return result.size() == 5 ? Optional.of(result) : Optional.empty();
	}

	/*
	 * private boolean checkNumbers(String[] args) { boolean result =
	 * Arrays.stream(args) // convert the array to a stream .skip(1) // ignore the
	 * first element, which isn't part of what we're validating .limit(5) // inspect
	 * only the next 5 elements .allMatch(s -> s.matches("\\A\\d{1,3}\\z")); //
	 * ensure everything remaining matches return result; }
	 */

	// Formats the text all the pet talent value text into a readable format. Praise
	// Wampus!
	public EmbedBuilder PetEmbed() {
		// Create the EmbedBuilder instance

		EmbedBuilder embed = new EmbedBuilder();
		PetCalculations pc = new PetCalculations(strength, intellect, agility, will, power);

		embed.setTitle(title[0], title[1]);
		embed.setColor(new Color(0xFF6419));
		embed.setDescription(description);
		embed.setAuthor(author[0], null, author[1]);

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
		embed.addField("Armor Breaker:", "" + pc.getArmorBreaker() + " (" + Math.round(pc.getArmorBreaker()) + "%)",
				true);
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
		embed.addField("Fishing Luck:", "" + pc.getFishingLuck() + " (" + Math.round(pc.getFishingLuck()) + "%)", true);

		return embed;
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(".stats", ".calc");
	}

	@Override
	public String getDescription() {
		return "Used to get the values of pet talents based on given stats.";
	}

	@Override
	public String getName() {
		return "Wizard101 Pet Calaculator";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("\n`.stats` - Find pet talent vaules\n\n"
				+ "`.stats [strength] [intellect] [agility] [will] [power] ` - intended use");
	}

}
