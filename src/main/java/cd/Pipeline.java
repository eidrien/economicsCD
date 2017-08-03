package cd;

public class Pipeline {
	
	Codebase waitingForTest, inTest, inProd;
	
	int currentTime = 0;
	int testStartTime = 0;
	int prodDeployTime = 0;
	
	int accumulatedValue = 0;

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

	public void timeStepElapsed() {
		currentTime ++;
		if(inProd != null){
			accumulatedValue += inProd.getValue();
		}
		doPromotionsIfPossible();
	}

	private void doPromotionsIfPossible() {
		if(canPromoteToProd()){			
			promoteToProd();
		}
		if(canPromoteToTest()){
			promoteToTest();
		}
	}

	private boolean canPromoteToProd() {
		return inTest!=null && validationTimeIsOver() && !inTest.detectsErrors();
	}

	private boolean validationTimeIsOver() {
		return currentTime >= inTest.getValidationTime() + testStartTime;
	}

	private void promoteToProd() {
		inProd = inTest;
		inTest = null;
		prodDeployTime = testStartTime + inProd.getValidationTime();
	}

	public int getAccumulatedValue() {
		return accumulatedValue;
	}

}
