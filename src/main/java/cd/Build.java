package cd;

import java.util.HashSet;
import java.util.Set;

public class Build {
	
	private boolean hasErrors;
	private Set<Integer> functionalitiesWithDetectedErrors;
	private int validationTime;
	private int value;
	private int numberOfFunctionalities;
	
	public Build(int value, int validationTime, int numberOfFunctionalities, 
			boolean hasErrors, Set<Integer> functionalitiesWithDetectedErrors){
		
		this.value = value;
		this.validationTime = validationTime;
		this.numberOfFunctionalities = numberOfFunctionalities;
		this.hasErrors = hasErrors;
		this.functionalitiesWithDetectedErrors = new HashSet<Integer>(functionalitiesWithDetectedErrors);
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public Set<Integer> getDetectedErrorIds() {
		return new HashSet<Integer>(functionalitiesWithDetectedErrors);
	}

	public int getValidationTime() {
		return validationTime;
	}

	public int getValue() {
		return value;
	}

	public int getNumberOfFunctionalities() {
		return numberOfFunctionalities;
	}

	public boolean detectsErrors() {
		return !functionalitiesWithDetectedErrors.isEmpty();
	}

}
