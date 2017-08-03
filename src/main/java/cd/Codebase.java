package cd;

import java.util.HashSet;
import java.util.Set;

import cd.commits.ErrorCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class Codebase {

	Set<FunctionalityCommit> functionalities;
	boolean hasErrors, hasTests;
	
	public Codebase(){
		functionalities = new HashSet<FunctionalityCommit>();
		hasErrors = false;
		hasTests = false;
	}
	
	public boolean hasErrors() {
		return hasErrors;
	}
	
	public boolean hasTests() {
		return hasTests;
	}

	public void addCommit(FunctionalityCommit commit) {
		functionalities.add(commit);
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

}
