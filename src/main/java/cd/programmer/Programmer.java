package cd.programmer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cd.commits.Commit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public abstract class Programmer {

	int maxFunctionalityId = 100;
	int maxFunctionalityValue = 100;
	int maxTestExecutionTime = 100;
	
	Set<Integer> functionalityIdsWithError;
	
	protected Random randomGenerator;
	
	public Programmer(){
		randomGenerator = new Random();
	}
	
	public void setRandomSeed(int seed){
		randomGenerator.setSeed(seed);
	}
	
	public abstract Commit code();

	public void setMaxFunctionalityId(int max) {
		this.maxFunctionalityId = max;
	}

	public void setMaxValue(int max) {
		maxFunctionalityValue = max;
	}
	
	public void setMaxTestExecutionTime(int max) {
		maxTestExecutionTime = max;
	}

	protected Commit codeFixIfNeededOrFunctionality() {
		if(hasErrorBeenDetected()){
			return codeFix();
		}else{
			return codeFunctionality();
		}
	}

	private boolean hasErrorBeenDetected() {
		return functionalityIdsWithError != null && !functionalityIdsWithError.isEmpty();
	}

	protected Commit codeFunctionality() {
		int functionalityId = getRandomNumber(maxFunctionalityId);
		int value = getRandomNumber(maxFunctionalityValue);
		return new FunctionalityCommit(functionalityId, value);
	}

	protected Commit codeFix() {
		int functionalityToFix = getRandomItem(functionalityIdsWithError);
		FixCommit fix = new FixCommit(functionalityToFix);
		functionalityIdsWithError.remove(functionalityToFix);
		return fix;
	}
	
	private Integer getRandomItem(Set<Integer> items) {
		int position = getRandomNumber(items.size());
		Iterator<Integer> iterator = items.iterator();
		Integer randomItem = iterator.next();
		for(int i=0; i<position; i++){
			randomItem = iterator.next();
		}
		return randomItem;
	}

	protected Commit codeTest() {
		int functionalityId = getRandomNumber(maxFunctionalityId);
		int executionTime = getRandomNumber(maxTestExecutionTime);
		return new TestCommit(functionalityId, executionTime);
	}
	
	public void errorsDetected(Set<Integer> ids) {
		this.functionalityIdsWithError = new HashSet<Integer>(ids);
	}

	protected int getRandomNumber(int max){
		return randomGenerator.nextInt(max);
	}
	
	protected boolean chooseWithProbability(double probability){
		return randomGenerator.nextDouble() < probability;
	}
}