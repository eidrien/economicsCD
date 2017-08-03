package cd;

import java.util.HashSet;
import java.util.Set;

import cd.commits.Commit;

public class Codebase {

	Set<Functionality> functionalities;
	
	public Codebase(){
		functionalities = new HashSet<Functionality>();
	}
	
	public boolean hasErrors() {
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

	public int getNumberOfFunctionalities() {
		return functionalities.size();
	}

	public boolean detectsErrors() {
		for(Functionality functionality : functionalities){
			if(functionality.isErrorDetected()){
				return true;
			}
		}
		return false;
	}

	public int getValue() {
		int value = 0;
		for(Functionality functionality : functionalities){
			value += functionality.getValue();
		}
		return value;
	}

}
