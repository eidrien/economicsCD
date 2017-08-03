package cd.programmer;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;

public class ImperfectProgrammer extends Programmer {

	double errorRate;
	double fatalErrorRate;
	double testRate;
	
	public void setErrorRate(double rate) {
		errorRate = rate;
	}
	
	public void setFatalErrorRate(double rate){
		fatalErrorRate = rate;
	}
	
	public void setTestRate(double rate) {
		this.testRate = rate;
	}

	/* (non-Javadoc)
	 * @see cd.programmer.Programmer#code()
	 */
	public Commit code() {
		if(Math.random() < testRate){
			return codeTest();
		}
		if(Math.random() < errorRate){
			return codeError();
		}
		return codeFixIfNeededOrFunctionality();
	}

	private Commit codeError() {
		int functionalityId = (int)(Math.random() * maxFunctionalityId);
		if(Math.random() < fatalErrorRate){
			return new FatalErrorCommit(functionalityId);
		}
		return new ErrorCommit(functionalityId);
	}

}
