package cd;

import java.util.HashSet;
import java.util.Set;

import cd.commits.Commit;

public class Codebase {

	Set<Functionality> functionalities;
	Set<Integer> detectedErrorIds;

	
	public Codebase(){
		functionalities = new HashSet<Functionality>();
		detectedErrorIds = new HashSet<Integer>();
	}
	
	private boolean hasErrors() {
		for(Functionality functionality : functionalities){
			if(functionality.hasError()){
				return true;
			}
		}
		return false;
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

	private boolean detectsErrors() {
		detectedErrorIds = new HashSet<Integer>();
		for(Functionality functionality : functionalities){
			if(functionality.isErrorDetected()){
				detectedErrorIds.add(functionality.getId());
			}
		}
		return !detectedErrorIds.isEmpty();
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

	private Set<Integer> getDetectedErrorIds() {
		return detectedErrorIds;
	}

	public Build build() {
		detectsErrors();
		return new Build(getValue(), getValidationTime(), getNumberOfFunctionalities(), 
				hasErrors(), getDetectedErrorIds());
	}

}
