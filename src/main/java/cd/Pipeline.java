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

	public Set<Functionality> getDetectedErrors() {
		if(inTest == null){
			return new HashSet<Functionality>();
		}
		return inTest.getDetectedErrors();
	}

	public Build getBuildIn(Stages stage) {
		switch(stage){
		case PROD: return inProd;
		case TEST: return inTest;
		case WAITING_FOR_TEST: return waitingForTest;
		}
		return inProd;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Pipeline status=> ");
		if(waitingForTest != null){
			sb.append("WAIT:").append(waitingForTest.getId());
		}
		if(inTest != null){
			sb.append(", TEST:").append(inTest.getId());
		}
		if(inProd != null){
			sb.append(", PROD:").append(inProd.getId());
		}
		return sb.toString();
	}
}
