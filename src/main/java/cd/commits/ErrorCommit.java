package cd.commits;

public class ErrorCommit extends Commit {

	public ErrorCommit(int functionalityId) {
		super(functionalityId);
		// TODO Auto-generated constructor stub
	}

	public String toString(){
		return "Error - id:"+functionalityId;
	}
}
