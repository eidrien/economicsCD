package cd.commits;

public class TestCommit extends Commit {

	int executionTime;
	
	public TestCommit(int functionalityId, int executionTime) {
		super(functionalityId);
		this.executionTime = executionTime;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public String toString(){
		return "Commit=> Type:Test, id:"+functionalityId+",time:"+executionTime;
	}
}
