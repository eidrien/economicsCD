package cd.programmer;

import static org.junit.Assert.*;

import org.junit.Test;

import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;
import cd.commits.TestCommit;

public class ImperfectProgrammerTest {

	@Test
	public void createsAPercentageOfErrors() {
		double errorPercentage = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setErrorRate(errorPercentage);
		int totalErrors = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(ErrorCommit.class)){
				totalErrors++;
			}
		}
		assertTrue(totalErrors < ((errorPercentage+0.1)*100));
		assertTrue(totalErrors > ((errorPercentage-0.1)*100));
	}

	@Test
	public void createsAPercentageOfFatalErrors() {
		double fatalErrorPercentage = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setErrorRate(1);
		programmer.setFatalErrorRate(fatalErrorPercentage);
		int totalFatalErrors = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(FatalErrorCommit.class)){
				totalFatalErrors++;
			}
		}
		assertTrue(totalFatalErrors < ((fatalErrorPercentage+0.1)*100));
		assertTrue(totalFatalErrors > ((fatalErrorPercentage-0.1)*100));
	}
	
	@Test
	public void createsAPercentageOfTests(){
		double testRate = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setErrorRate(0);
		programmer.setTestRate(testRate);
		int totalTests = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(TestCommit.class)){
				totalTests++;
			}
		}
		assertTrue(totalTests < ((testRate+0.1)*100));
		assertTrue(totalTests > ((testRate-0.1)*100));
	}
	
	@Test
	public void worksOnFixingErrorsOnlyPartially(){
		fail("Not implemented");
	}
	
	@Test
	public void manualTesterTakesLongerToExecuteTests(){
		fail("Not even sure this should be implemented");
	}
}
