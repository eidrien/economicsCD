package cd.commits;

public class FatalErrorCommit extends Commit {

	public FatalErrorCommit(int functionalityId) {
		super(functionalityId);
	}

	public String toString(){
		return "Commit=> Type:FatalError, id:"+functionalityId;
	}
}
