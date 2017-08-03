package cd;

import java.util.HashSet;
import java.util.Set;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class Codebase {

	Set<Functionality> functionalities;
	boolean hasErrors, hasTests;
	
	public Codebase(){
		functionalities = new HashSet<Functionality>();
		hasErrors = false;
		hasTests = false;
	}
	
	public boolean hasErrors() {
		return hasErrors;
	}
	
	public boolean hasTests() {
		return hasTests;
	}

	public void addCommit(Commit commit) {
		Functionality functionality = findFunctionalityWithId(commit.getFunctionalityId());
		if(functionality == null){
			functionality = new Functionality(commit);
			functionalities.add(functionality);
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

	public void addCommit(ErrorCommit errorCommit) {
		hasErrors = true;
	}

	public void addCommit(TestCommit testCommit) {
		hasTests = true;		
	}

	public boolean detectsErrors() {
		return hasErrors && hasTests;
	}

}
