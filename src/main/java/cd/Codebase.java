package cd;

public class Codebase {

	int numberOfFunctionalities;
	boolean hasErrors, hasTests;
	
	public Codebase(){
		numberOfFunctionalities = 0;
		hasErrors = false;
		hasTests = false;
	}
	
	public boolean hasErrors() {
		return hasErrors;
	}

	public void addCommit(FunctionalityCommit commit) {
		numberOfFunctionalities++;
	}

	public int getNumberOfFunctionalities() {
		return numberOfFunctionalities;
	}

	public void addCommit(ErrorCommit errorCommit) {
		hasErrors = true;
	}

	public void addCommit(TestCommit testCommit) {
		hasTests = true;		
	}

	public boolean hasTests() {
		return hasTests;
	}

}
