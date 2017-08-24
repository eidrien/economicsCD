package cd.programmer;


import java.util.Set;

import cd.Functionality;
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
		if(random.chooseWithProbability(testRate)){
			return codeTest();
		}
		if(random.chooseWithProbability(errorRate)){
			return codeError();
		}
		return codeFixIfNeededOrFunctionality();
	}

	public void errorsDetected(Set<Functionality> errorsDetected) {
		if(random.chooseWithProbability(fixRate)){
			super.errorsDetected(errorsDetected);
		}
	}
	
	private Commit codeError() {
		int functionalityId = random.getRandomNumber(maxFunctionalityId);
		if(random.chooseWithProbability(fatalErrorRate)){
			return new FatalErrorCommit(functionalityId);
		}
		return new ErrorCommit(functionalityId);
	}

}
