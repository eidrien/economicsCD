package cd;

import java.util.HashSet;
import java.util.Set;

public class Build {
	
	private int id;
	private Set<Functionality> errors;
	private Set<Functionality> detectedErrors;
	private Set<Functionality> untested;
	private int validationTime;
	private int value;
	private int numberOfFunctionalities;
	
	public Build(int id, int value, int validationTime, int numberOfFunctionalities, 
			Set<Functionality> errors, Set<Functionality> detectedErrors,
			Set<Functionality> untested){
		this.id = id;
		this.value = value;
		this.validationTime = validationTime;
		this.numberOfFunctionalities = numberOfFunctionalities;
		this.errors = new HashSet<Functionality>(errors);
		this.detectedErrors = new HashSet<Functionality>(detectedErrors);
		this.untested = new HashSet<Functionality>(untested);
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Build => ");
		sb.append("id:").append(id).append(", ");
		sb.append("value:").append(getValue()).append(", ");
		sb.append("test time:").append(getValidationTime()).append(", ");
		sb.append("#functionalities:").append(numberOfFunctionalities).append(", ");
		sb.append("#errors:").append(errors.size()).append(", ");
		sb.append("#detected:").append(detectedErrors.size());
		
		return sb.toString();
	}

	public Set<Functionality> getErrors() {
		return new HashSet<Functionality>(errors);
	}

	public Set<Functionality> getUntestedFunctionalities() {
		return untested;
	}

}
