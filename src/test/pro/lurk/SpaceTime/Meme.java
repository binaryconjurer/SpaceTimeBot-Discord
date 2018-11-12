package pro.lurk.SpaceTime;

import pro.lurk.command.PetCalculations;

public class Meme {

	public static void main(String args[]) {
		double strength = 255;
		double intellect = 250;
		double agility = 260;
		double will = 260;
		double power = 250;

		PetCalculations pc = new PetCalculations(255, 250, 260, 260, 250);

		r(pc.getDealer());
		r(pc.getGiver());
		r(pc.getBoon());
		r(pc.getProof());
		r(pc.getDefy());
		r(pc.getWard());
		
		double var = 10.11;
		String str = Double.toString(var);
		System.out.println(str);
		
	}
	public int round(double num) {
		return (int) Math.round(num);
	}

	public static void r(double num) {
		System.out.println("Raw: " + num);
		System.out.println("Rounded: " + (int) Math.round(num));
		
	}
}
