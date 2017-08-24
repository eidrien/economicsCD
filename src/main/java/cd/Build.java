package cd;

import java.util.HashSet;
import java.util.Set;

public class Build {
	
	private int id;
	private Set<Functionality> errors;
	private Set<Functionality> detectedErrors;
	private int validationTime;
	private int value;
	private int numberOfFunctionalities;
	
	public Build(int id, int value, int validationTime, int numberOfFunctionalities, 
			Set<Functionality> errors, Set<Functionality> detectedErrors){
		this.id = id;
		this.value = value;
		this.validationTime = validationTime;
		this.numberOfFunctionalities = numberOfFunctionalities;
		this.errors = new HashSet<Functionality>(errors);
		this.detectedErrors = new HashSet<Functionality>(detectedErrors);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public Set<Functionality> getDetectedErrors() {
		return new HashSet<Functionality>(detectedErrors);
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
		return !detectedErrors.isEmpty();
	}

	public int getId() {
		return id;
	}

}
