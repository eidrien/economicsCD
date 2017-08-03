package cd;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FixCommit;
import cd.commits.TestCommit;

public class Functionality {

	int id;
	boolean error;
	boolean test;
	
	public Functionality(Commit commit){
		id = commit.getFunctionalityId();
		addModification(commit);
	}
	
	public void addModification(Commit commit) {
		if(commit.getClass().equals(ErrorCommit.class))
			addError();
		if(commit.getClass().equals(TestCommit.class))
			addTest();
		if(commit.getClass().equals(FixCommit.class))
			addFix();
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
