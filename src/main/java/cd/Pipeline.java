package cd;

public class Pipeline {
	
	Codebase waitingForTest;
	Codebase inTest;
	Codebase inProd;
	
	int currentTime = 0;
	int testStartTime = 0;

	public void push(Codebase codebase) {
		waitingForTest = codebase;
		if(canPromoteToTest()){
			promoteToTest();
		}
	}

	private boolean canPromoteToTest() {
		return inTest == null;
	}

	private void promoteToTest() {
		inTest = waitingForTest;
		waitingForTest = null;
		testStartTime = currentTime;
	}

	public Stages stageOf(Codebase c) {
		if(waitingForTest == c){
			return Stages.WAITING_FOR_TEST;
		}
		if(inTest == c){
			return Stages.TEST;
		}
		if(inProd == c){
			return Stages.PROD;
		}
		return null;
	}

	public void timeStepsElapsed(int i) {
		currentTime += i;
		if(canPromoteToProd()){			
			promoteToProd();
		}
		if(canPromoteToTest()){
			promoteToTest();
		}
		
		waitingForTest = null;
	}

	private boolean canPromoteToProd() {
		return validationTimeIsOver() && !inTest.detectsErrors();
	}

	private boolean validationTimeIsOver() {
		return currentTime >= inTest.getValidationTime() + testStartTime;
	}

	private void promoteToProd() {
		inProd = inTest;
		inTest = null;
	}

}
