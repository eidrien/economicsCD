package cd;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class Functionality {

	int id;
	int value;
	int validationTime;
	boolean error, fatalError;
	boolean test;
	
	public Functionality(Commit commit){
		id = commit.getFunctionalityId();
		value = validationTime = 0;
		error = fatalError = test = false;
		addModification(commit);
	}
	
	public void addModification(Commit commit) {
		if(commit.getClass().equals(FatalErrorCommit.class))
			addFatalError();
		if(commit.getClass().equals(ErrorCommit.class))
			addError();
		if(commit.getClass().equals(TestCommit.class)){
			addTest((TestCommit)commit);			
		}
		if(commit.getClass().equals(FixCommit.class))
			addFix();
		if(commit.getClass().equals(FunctionalityCommit.class))
			addValue(((FunctionalityCommit)commit).getValue());
	}

	private void addValue(int value2) {
		value += value2;
	}

	private void addError() {
		error = true;
	}

	private void addFatalError() {
		fatalError = true;
		addError();
	}

	public boolean hasError() {
		return error;
	}

	private void addTest(TestCommit commit) {
		test = true;
		validationTime += commit.getExecutionTime();
	}

	public boolean isErrorDetected() {
		return error && test;
	}

	private void addFix() {
		error = false;
		fatalError = false;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		if(error){
			return 0;
		}else{
			return value;
		}
	}

	public boolean hasFatalError() {
		return fatalError;
	}

	public int getValidationTime() {
		return validationTime;
	}

	public String toString(){
		return "Functionality => id:"+id+", value:"+value+
				", test:"+test+", validationTime:"+validationTime+
				", error:"+error+", fatal:"+fatalError;
	}
}
