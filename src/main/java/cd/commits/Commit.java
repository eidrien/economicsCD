package cd.commits;

public class Commit {

	int functionalityId;
	
	public Commit(int functionalityId){
		this.functionalityId = functionalityId;
	}
	
	int getFunctionalityId(){
		return functionalityId;
	}
	
	public boolean equals(Object obj){
		Commit other = (Commit)obj;
		return other.getClass().equals(this.getClass()) && other.functionalityId == this.functionalityId;
	}
	
	public int hashCode(){
		return this.functionalityId;
	}
}
