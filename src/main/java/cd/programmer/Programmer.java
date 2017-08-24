package cd.programmer;

import java.util.HashSet;
import java.util.Set;

import cd.Functionality;
import cd.commits.Commit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;
import utils.RandomGenerator;

public abstract class Programmer {

	RandomGenerator random;
	
	int maxFunctionalityId = 100;
	int maxFunctionalityValue = 100;
	int maxTestExecutionTime = 100;
	
	Set<Functionality> detectedErrors;
	
	public Programmer(){
		random = new RandomGenerator();
	}
	
	public void setRandomGenerator(RandomGenerator random){
		this.random = random;
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
		return detectedErrors != null && !detectedErrors.isEmpty();
	}

	protected Commit codeFunctionality() {
		int functionalityId = random.getRandomNumber(maxFunctionalityId);
		int value = random.getRandomNumber(maxFunctionalityValue);
		return new FunctionalityCommit(functionalityId, value);
	}

	protected Commit codeFix() {
		Functionality functionalityToFix = random.getRandomItem(detectedErrors);
		FixCommit fix = new FixCommit(functionalityToFix.getId());
		detectedErrors.remove(functionalityToFix);
		return fix;
	}
	
	protected Commit codeTest() {
		int functionalityId = random.getRandomNumber(maxFunctionalityId);
		int executionTime = random.getRandomNumber(maxTestExecutionTime) + 1;
		return new TestCommit(functionalityId, executionTime);
	}
	
	public void errorsDetected(Set<Functionality> ids) {
		this.detectedErrors = new HashSet<Functionality>(ids);
	}

}