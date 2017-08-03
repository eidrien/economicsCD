package cd;

public class Functionality {

	boolean error;
	boolean test;
	
	public Functionality(){
		error = false;
		test = false;
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

}
