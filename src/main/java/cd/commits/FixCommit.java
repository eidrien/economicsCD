package cd.commits;

public class FixCommit extends Commit {

	public FixCommit(int functionalityId) {
		super(functionalityId);
	}

	public String toString(){
		return "Commit=> Type:Fix, id:"+functionalityId;
	}
}
