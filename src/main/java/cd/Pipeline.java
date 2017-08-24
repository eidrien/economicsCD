package cd;

import java.util.HashSet;
import java.util.Set;

public class Pipeline {
	
	Build waitingForTest, inTest, inProd;
	
	int currentTime = 0;
	int testStartTime = 0;
	int prodDeployTime = 0;
	
	int accumulatedValue = 0;

	public void push(Build build) {
		waitingForTest = build;
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

	public Stages stageOf(Build b) {
		if(waitingForTest == b){
			return Stages.WAITING_FOR_TEST;
		}
		if(inTest == b){
			return Stages.TEST;
		}
		if(inProd == b){
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
		return inTest!=null && isValidationTimeOver() && !inTest.detectsErrors();
	}

	private boolean isValidationTimeOver() {
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

	public Set<Integer> getDetectedErrors() {
		if(inTest == null){
			return new HashSet<Integer>();
		}
		return inTest.getDetectedErrorIds();
	}

	public Build getProductionBuild() {
		return inProd;
	}

}
