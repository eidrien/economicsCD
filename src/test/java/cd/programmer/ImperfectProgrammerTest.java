package cd.programmer;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import cd.Functionality;
import cd.commits.Commit;
import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;
import cd.commits.FixCommit;
import cd.commits.TestCommit;

public class ImperfectProgrammerTest {

	@Test
	public void createsAPercentageOfErrors() {
		double errorPercentage = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setRandomSeed(100);
		programmer.setErrorRate(errorPercentage);
		int totalErrors = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(ErrorCommit.class)){
				totalErrors++;
			}
		}
		assertEquals("Errors should be close to 50", 40, totalErrors);
	}

	@Test
	public void createsAPercentageOfFatalErrors() {
		double fatalErrorPercentage = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setRandomSeed(100);
		programmer.setErrorRate(1);
		programmer.setFatalErrorRate(fatalErrorPercentage);
		int totalFatalErrors = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(FatalErrorCommit.class)){
				totalFatalErrors++;
			}
		}
		assertEquals("Fatal errors should be close to 50", 53, totalFatalErrors);
	}
	
	@Test
	public void createsAPercentageOfTests(){
		double testRate = 0.5;
		
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setRandomSeed(100);
		programmer.setErrorRate(0);
		programmer.setTestRate(testRate);
		int totalTests = 0;
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			if(commit.getClass().equals(TestCommit.class)){
				totalTests++;
			}
		}
		assertEquals("Tests should be close to 50", 52, totalTests);

	}
	
	@Test
	public void fixesBugsEventually(){
		ImperfectProgrammer programmer = new ImperfectProgrammer();
		programmer.setRandomSeed(100);
		programmer.setErrorRate(0.0);
		programmer.setTestRate(0.0);
		programmer.setFixRate(0.5);
		
		int commitsUntilFix = 0;
		int functionalityId = 1;
		Commit commit = null;
		do {
			HashSet<Functionality> bugs = new HashSet<Functionality>();
			bugs.add(new Functionality(functionalityId));
			programmer.errorsDetected(bugs);
			commit = programmer.code();
			commitsUntilFix++;
		} while (!commit.getClass().equals(FixCommit.class));
		FixCommit fix = (FixCommit)commit;
		assertEquals(functionalityId, fix.getFunctionalityId() );
		assertEquals("Should take more than one commit to fix", 6, commitsUntilFix);
	}
	
}
