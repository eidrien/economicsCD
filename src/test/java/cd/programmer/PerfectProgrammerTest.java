package cd.programmer;

import static org.junit.Assert.*;

import org.junit.Test;

import cd.commits.Commit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;

public class PerfectProgrammerTest {

	@Test
	public void onlyCreatesFunctionalityCommits() {
		Programmer programmer = new PerfectProgrammer();
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			assertEquals(FunctionalityCommit.class, commit.getClass());
		}
	}
	
	@Test
	public void createsFunctionalitiesWitIdsWithinSetLimits(){
		PerfectProgrammer programmer = new PerfectProgrammer();
		programmer.setMaxFunctionalityId(100);
		for(int i=0; i<100; i++){
			Commit commit = programmer.code();
			assertTrue(commit.getFunctionalityId() < 100);
			assertTrue(commit.getFunctionalityId() >= 0);
		}
	}

	@Test
	public void createsFunctionalitiesWithValuesWithinSetLimits(){
		PerfectProgrammer programmer = new PerfectProgrammer();
		programmer.setMaxValue(100);
		for(int i=0; i<100; i++){
			FunctionalityCommit commit = (FunctionalityCommit)programmer.code();
			assertTrue(commit.getValue() < 100);
			assertTrue(commit.getValue() >= 0);
		}
	}
	
	@Test
	public void fixesErrorsInmediately(){
		Programmer programmer = new PerfectProgrammer();
		programmer.errorDetected(1);
		Commit commit = programmer.code();
		assertEquals(FixCommit.class, commit.getClass());
		FixCommit fix = (FixCommit)commit;
		assertEquals(1, fix.getFunctionalityId());
	}
	
	@Test
	public void goesBackToAddingValueRightAfterFixingError(){
		Programmer programmer = new PerfectProgrammer();
		programmer.errorDetected(1);
		programmer.code();
		Commit code = programmer.code();
		assertEquals(FunctionalityCommit.class, code.getClass());
	}

}
