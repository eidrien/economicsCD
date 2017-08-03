package cd;

import cd.commits.ErrorCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class Functionality {

	boolean error;
	boolean test;
	
	public Functionality(FunctionalityCommit functionalityCommit){
		error = false;
		test = false;
	}
	
	public Functionality(ErrorCommit errorCommit) {
		error = true;
		test = false;
	}

	public Functionality(TestCommit testCommit) {
		error = false;
		test = true;
	}

	public void addError() {
		error = true;
	}

	public boolean hasError() {
		return error;
	}

	public void addTest() {
		test = true;
	}

	public boolean isErrorDetected() {
		return error && test;
	}

	public void addFix() {
		error = false;
	}

}
