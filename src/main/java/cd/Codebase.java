package cd;

public class Codebase {

	int numberOfFunctionalities;
	boolean hasErrors;
	
	public Codebase(){
		numberOfFunctionalities = 0;
		hasErrors = false;
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

}
