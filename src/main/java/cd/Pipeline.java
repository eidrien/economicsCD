package cd;

import java.util.HashSet;
import java.util.Set;

public class Pipeline {
	
	Build waitingForTest, inTest, lastTested, inProd;
	
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
		if(isTestFinished()){
			saveLastTestedBuild();
			if(lastTestedBuildHasNoErrors()){
				promoteToProd();
			}
		}
		if(canPromoteToTest()){
			promoteToTest();
		}
	}

	private void saveLastTestedBuild() {
		lastTested = inTest;
	}

	private boolean isTestFinished() {
		return inTest!=null && isValidationTimeOver();
	}

	private boolean lastTestedBuildHasNoErrors() {
		return lastTested != null && !lastTested.detectsErrors();
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
		HashSet<Functionality> detectedErrors = new HashSet<Functionality>();
		Build lastTested = getLastTestedBuild();
		if(lastTested != null){
			detectedErrors.addAll(lastTested.getDetectedErrors());
		}
		if(inProd != null){
			Set<Functionality> errors = inProd.getErrors();
			for(Functionality error : errors){
				if(error.hasFatalError()){
					detectedErrors.add(error);
				}
			}
		}
		return detectedErrors;
	}

	private Build getLastTestedBuild() {
		return lastTested;
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
