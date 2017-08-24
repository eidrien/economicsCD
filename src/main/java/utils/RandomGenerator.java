package utils;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cd.Functionality;

public class RandomGenerator {

	protected Random randomGenerator;

	public RandomGenerator(){
		randomGenerator = new Random();
	}
	
	public void setRandomSeed(int seed){
		randomGenerator.setSeed(seed);
	}
	
	public Functionality getRandomItem(Set<Functionality> items) {
		int position = getRandomNumber(items.size());
		Iterator<Functionality> iterator = items.iterator();
		Functionality randomItem = iterator.next();
		for(int i=0; i<position; i++){
			randomItem = iterator.next();
		}
		return randomItem;
	}

	public int getRandomNumber(int max){
		return randomGenerator.nextInt(max);
	}
	
	public boolean chooseWithProbability(double probability){
		return randomGenerator.nextDouble() < probability;
	}

}
