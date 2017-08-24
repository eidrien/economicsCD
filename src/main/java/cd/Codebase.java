package cd;

import java.util.HashSet;
import java.util.Set;

import cd.commits.Commit;

public class Codebase {

	Set<Functionality> functionalities;
	int lastBuildId = 0;
	
	public Codebase(){
		functionalities = new HashSet<Functionality>();
	}
	
	private Set<Functionality> getErrors() {
		HashSet<Functionality> errors = new HashSet<Functionality>();
		for(Functionality functionality : functionalities){
			if(functionality.hasError()){
				errors.add(functionality);
			}
		}
		return errors;
	}
	
	public void addCommit(Commit commit) {
		Functionality functionality = findFunctionalityWithId(commit.getFunctionalityId());
		if(functionality == null){
			functionality = new Functionality(commit);
			functionalities.add(functionality);
		}else{
			functionality.addModification(commit);
		}
	}

	private Functionality findFunctionalityWithId(int functionalityId) {
		for(Functionality functionality: functionalities){
			if(functionality.getId() == functionalityId){
				return functionality;
			}
		}
		return null;
	}

	private int getNumberOfFunctionalities() {
		return functionalities.size();
	}

	private Set<Functionality> getDetectedErrors() {
		HashSet<Functionality> detectedErrors = new HashSet<Functionality>();
		for(Functionality functionality : functionalities){
			if(functionality.isErrorDetected()){
				detectedErrors.add(functionality);
			}
		}
		return detectedErrors;
	}

	private int getValue() {
		int value = 0;
		for(Functionality functionality : functionalities){
			if(functionality.hasFatalError()){
				return 0;
			}
			value += functionality.getValue();
		}
		return value;
	}

	private int getValidationTime() {
		int time = 0;
		for(Functionality functionality : functionalities){
			time += functionality.getValidationTime();
		}
		return time;
	}


	public Build build() {
		lastBuildId++;
		return new Build(lastBuildId, getValue(), getValidationTime(), getNumberOfFunctionalities(), 
				getErrors(), getDetectedErrors());
	}

}
