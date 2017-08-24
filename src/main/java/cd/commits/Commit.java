package cd.commits;

public class Commit {

	Integer functionalityId;
	
	public Commit(int functionalityId){
		this.functionalityId = functionalityId;
	}
	
	public int getFunctionalityId(){
		return functionalityId;
	}
	
	public boolean equals(Object obj){
		Commit other = (Commit)obj;
		return other.getClass().equals(this.getClass()) && other.getFunctionalityId() == this.getFunctionalityId();
	}
	
	public int hashCode(){
		return this.getFunctionalityId();
	}
}
