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

	public String toString(){
		return "Commit=> Type:Functionality, id:"+functionalityId+",value:"+value;
	}
}
