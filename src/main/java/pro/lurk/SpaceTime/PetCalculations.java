package pro.lurk.SpaceTime;

public class PetCalculations {

	// Pet Stats
	private static double strength;
	private static double intellect;
	private static double agility;
	private static double will;
	private static double power;

	// Talent Values
	private double dealer;
	private double giver;
	private double boon;
	private double proof;
	private double defy;
	private double ward;
	private double criticalStriker;
	private double criticalHitter;
	private double schoolAssailiant;
	private double schoolStriker;
	private double defender;
	private double blocker;
	private double medic;
	private double healer;
	private double lively;
	private double healthy;
	private double armorBreaker;
	private double armorPiercer;
	private double stunRecalcitrant;
	private double stunResistant;
	private double fishingLuck;
	private double addHealth;
	private double healthBoost;
	private double healthGift;
	private double healthBounty;
	private double extraMana;
	private double manaBoost;
	private double manaBounty;
	private double manaGift;

	// Fill in the rest of talents later :P

	// Constructure inputs pet stats into object
	public PetCalculations(int strength, double intellect, double agility, double will, double power) {
		// Inputting the pet's stats to the object
		this.strength = strength;
		this.intellect = intellect;
		this.agility = agility;
		this.will = will;
		this.power = power;

		// Calculating Talent Values upon creation

		findDealer();
		findGiver();
		findBoon();
		findProof();
		findDefy();
		findWard();
		findCriticalStriker();
		findCriticalHitter();
		findSchoolAssailiant();
		findSchoolStriker();
		findDefender();
		findBlocker();
		findMedic();
		findHealer();
		findLively();
		findHealthy();
		findArmorBreaker();
		findArmorPiercer();
		findStunRecalcitrant();
		findStunResistant();
		findFishingLuck();
		// The health/mana talents are inactive as it takes too much space and rather useless in most cases, we have !petcalc for that
/*		findAddHealth();
		findHealthBoost();
		findHealthBounty();
		findExraMana();
		findManaBoost();
		findManaBounty();
		findManaGift();*/

	}

	// Methods to calculate talents based on stats within object

	// Damage Calculations
	// Dealer Calculation
	public void findDealer() {
		dealer = (((strength * 2) + (will * 2) + power) * 3 / 400);
		// System.out.println("MANDERS " + dealer);
	}

	// Giver Calculation
	private void findGiver() {
		giver = (((strength * 2) + (will * 2) + power) / 200);
	}

	// Boon Calculation
	private void findBoon() {
		boon = (((strength * 2) + (will * 2) + power) / 400);
	}

	// Resist Calculations
	// Proof Calculation
	private void findProof() {
		proof = (((strength * 2) + (agility * 2) + power) / 125);
	}

	// Defy Calculation
	private void findDefy() {
		defy = (((strength * 2) + (agility * 2) + power) / 250);
	}

	// Ward Calculation
	private void findWard() {
		ward = (((strength * 2) + (agility * 2) + power) * 3 / 250);
	}

	// Critical and Block Calculations
	// Critical Striker Calculation
	private void findCriticalStriker() {
		criticalStriker = (((agility * 2) + (will * 2) + power) * 3 / 125);
	}

	// Critical Hitter Calculation
	private void findCriticalHitter() {
		criticalHitter = (((agility * 2) + (will * 2) + power) * 9 / 500);
	}

	// School Assailiant Calculation
	private void findSchoolAssailiant() {
		schoolAssailiant = (((agility * 2) + (will * 2) + power) / 40);
	}

	// School Striker Calculation
	private void findSchoolStriker() {
		schoolStriker = (((agility * 2) + (will * 2) + power) * 3 / 150);
	}

	// Defender Calculation
	private void findDefender() {
		defender = (((intellect * 2) + (will * 2) + power) * 3 / 128);
	}

	// Blocker Calculation
	private void findBlocker() {
		blocker = (((intellect * 2) + (will * 2) + power) * 9 / 500);
	}

	// Healing
	// Medic
	private void findMedic() {
		medic = (((strength * 2) + (will * 2) + power) * 13 / 2000);
	}

	// Medic
	private void findHealer() {
		healer = (((strength * 2) + (will * 2) + power) * 3 / 1000);
	}

	// Lively
	private void findLively() {
		lively = (((intellect * 2) + (agility * 2) + power) * 13 / 2000);
	}
	
	// Lively
		private void findHealthy() {
			healthy = (((intellect * 2) + (agility * 2) + power) * 3 / 1000);
		}
	
	// Armor Breaker
	private void findArmorBreaker() {
		armorBreaker = (((strength * 2) + (agility * 2) + power) / 400);
	}

	// Armor Piercer
	private void findArmorPiercer() {
		armorPiercer = (((strength * 2) + (agility * 2) + power) * 3 / 2000);
	}

	// Stun Recalcitrant
	private void findStunRecalcitrant() {
		stunRecalcitrant = (((strength * 2) + (intellect * 2) + power) / 125);
	}

	// Stun Resistant
	private void findStunResistant() {
		stunResistant = (((strength * 2) + (intellect * 2) + power) / 250);
	}

	// Fishing Luck
	private void findFishingLuck() {
		fishingLuck = (((intellect * 2) + (will * 2) + power) / 400);
	}
	
	// Add Health
/*	private void findAddHealth() {
		addHealth = (((agility * 2) + (will * 2) + power) * 3 / 50);
	}

	// Health Boost
	private void findHealthBoost() {
		healthBoost = (((agility * 2) + (will * 2) + power) * 3 / 50);
	}

	// Health Gift
	private void findHealthGift() {
		healthGift = (((intellect * 2) + (will * 2) + power) / 10);
	}

	// Health Bounty
	private void findHealthBounty() {
		healthBounty = (((agility * 2) + (will * 2) + power) * 3 / 25);
	}

	// Extra Mana
	private void findExraMana() {
		extraMana = (((intellect * 2) + (will * 2) + power) / 25);
	}

	// Mana Boost
	private void findManaBoost() {
		manaBoost = (((intellect * 2) + (will * 2) + power) * 3 / 50);
	}

	// Mana Bounty
	private void findManaBounty() {
		manaBounty = (((intellect * 2) + (will * 2) + power) * 3 / 10);
	}

	// Mana Gift
	private void findManaGift() {
		manaGift = (((intellect * 2) + (will * 2) + power) * 6 / 75);
	}*/
	// Pet Talent get methods

	public double getDealer() {
		return dealer;
	}

	public double getGiver() {
		return giver;
	}

	public double getBoon() {
		return boon;
	}

	public double getProof() {
		return proof;
	}

	public double getDefy() {
		return defy;
	}

	public double getWard() {
		return ward;
	}

	public double getCriticalStriker() {
		return criticalStriker;
	}

	public double getCriticalHitter() {
		return criticalHitter;
	}

	public double getSchoolAssailiant() {
		return schoolAssailiant;
	}

	public double getSchoolStriker() {
		return schoolStriker;
	}

	public double getDefender() {
		return defender;
	}
	
	public double getBlocker() {
		return blocker;
	}

	public double getMedic() {
		return medic;
	}

	public double getHealer() {
		return healer;
	}
	
	public double getLively() {
		return lively;
	}
	
	public double getHealthy() {
		return healthy;
	}

	public double getArmorBreaker() {
		return armorBreaker;
	}

	public double getArmorPiercer() {
		return armorPiercer;
	}

	public double getStunRecalcitrant() {
		return stunRecalcitrant;
	}

	public double getStunResistant() {
		return stunResistant;
	}
	
	public double getFishingLuck() {
		return fishingLuck;
	}

/*	public double getAddHealth() {
		return addHealth;
	}

	public double getHealthBoost() {
		return healthBoost;
	}

	public double getHealthGift() {
		return healthGift;
	}

	public double getHealthBounty() {
		return healthBounty;
	}

	public double getExtraMana() {
		return extraMana;
	}

	public double getManaBoost() {
		return manaBoost;
	}

	public double getManaBounty() {
		return manaBounty;
	}

	public double getManaGift() {
		return manaGift;
	}*/
}
