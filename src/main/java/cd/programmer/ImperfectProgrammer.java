package cd.programmer;


import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;

public class ImperfectProgrammer extends Programmer {

	double errorRate;
	double fatalErrorRate;
	double testRate;
	double fixRate;
	
	public void setErrorRate(double rate) {
		errorRate = rate;
	}
	
	public void setFatalErrorRate(double rate){
		fatalErrorRate = rate;
	}
	
	public void setTestRate(double rate) {
		this.testRate = rate;
	}

	public void setFixRate(double fixRate) {
		this.fixRate = fixRate;
	}

	/* (non-Javadoc)
	 * @see cd.programmer.Programmer#code()
	 */
	public Commit code() {
		if(chooseWithProbability(testRate)){
			return codeTest();
		}
		if(chooseWithProbability(errorRate)){
			return codeError();
		}
		return codeFixIfNeededOrFunctionality();
	}

	public void errorDetected(int functionalityId) {
		if(chooseWithProbability(fixRate)){
			super.errorDetected(functionalityId);
		}
	}
	
	private Commit codeError() {
		int functionalityId = (int)(Math.random() * maxFunctionalityId);
		if(chooseWithProbability(fatalErrorRate)){
			return new FatalErrorCommit(functionalityId);
		}
		return new ErrorCommit(functionalityId);
	}

}
