package cd.programmer;

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
	
	public abstract Commit code();

	public void setMaxFunctionalityId(int max) {
		this.maxFunctionalityId = max;
	}

	public void setMaxValue(int max) {
		maxFunctionalityValue = max;
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
		int functionalityId = (int)(Math.random() * maxFunctionalityId);
		int value = (int)(Math.random() * maxFunctionalityValue);
		return new FunctionalityCommit(functionalityId, value);
	}

	protected Commit codeFix() {
		FixCommit fix = new FixCommit(functionalityIdWithError);
		errorHasBeenDetected = false;
		return fix;
	}
	
	protected Commit codeTest() {
		int functionalityId = (int)(Math.random() * maxFunctionalityId);
		int executionTime = (int)(Math.random() * maxTestExecutionTime);
		return new TestCommit(functionalityId, executionTime);
	}
	
	public void errorDetected(int functionalityId) {
		errorHasBeenDetected = true;
		functionalityIdWithError = functionalityId;
	}


}