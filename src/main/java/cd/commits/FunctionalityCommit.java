package cd.commits;

public class FunctionalityCommit extends Commit {

	int value;
	
	public FunctionalityCommit(int functionalityId, int value) {
		super(functionalityId);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
