package cd;

public class Codebase {

	int numberOfFunctionalities;
	boolean hasFunctionality, hasErrors, hasTests;
	
	public Codebase(){
		numberOfFunctionalities = 0;
		hasFunctionality = false;
		hasErrors = false;
		hasTests = false;
	}
	
	public boolean hasErrors() {
		return hasErrors;
	}
	
	public boolean hasTests() {
		return hasTests;
	}

	public boolean hasFunctionality(){
		return hasFunctionality;
	}

	public void addCommit(FunctionalityCommit commit) {
		numberOfFunctionalities++;
		hasFunctionality = true;
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

}
