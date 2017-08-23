package cd.programmer;

import java.util.Random;

import cd.commits.Commit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public abstract class Programmer {

	int maxFunctionalityId = 100;
	int maxFunctionalityValue = 100;
	int maxTestExecutionTime = 100;
	
	boolean errorHasBeenDetected;
	int functionalityIdWithError;
	
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
		return errorHasBeenDetected;
	}

	protected Commit codeFunctionality() {
		int functionalityId = getRandomNumber(maxFunctionalityId);
		int value = getRandomNumber(maxFunctionalityValue);
		return new FunctionalityCommit(functionalityId, value);
	}

	protected Commit codeFix() {
		FixCommit fix = new FixCommit(functionalityIdWithError);
		errorHasBeenDetected = false;
		return fix;
	}
	
	protected Commit codeTest() {
		int functionalityId = getRandomNumber(maxFunctionalityId);
		int executionTime = getRandomNumber(maxTestExecutionTime);
		return new TestCommit(functionalityId, executionTime);
	}
	
	public void errorDetected(int functionalityId) {
		errorHasBeenDetected = true;
		functionalityIdWithError = functionalityId;
	}

	protected int getRandomNumber(int max){
		return randomGenerator.nextInt(max);
	}
	
	protected boolean chooseWithProbability(double probability){
		return randomGenerator.nextDouble() < probability;
	}
}