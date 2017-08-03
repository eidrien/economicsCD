package cd;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.TestCommit;

public class Functionality {

	int id;
	boolean error;
	boolean test;
	
	public Functionality(Commit commit){
		id = commit.getFunctionalityId();
		error = commit.getClass().equals(ErrorCommit.class);
		test = commit.getClass().equals(TestCommit.class);
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

	public int getId() {
		return id;
	}

}
