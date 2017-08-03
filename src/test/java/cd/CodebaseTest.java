package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cd.commits.ErrorCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class CodebaseTest {

	Codebase codebase;
	
	@Test
	public void emptyCodebaseHasNoErrors() {
		givenNewCodebase();
		thenHasNoErrors();
	}
	
	@Test
	public void firstFunctionalCommitAddsFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommitWithId(1);
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void secondFunctionalityCommitDoesntAddFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommitWithId(1);
		givenFunctionalityCommitWithId(1);
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void errorCommitAddsError(){
		givenNewCodebase();
		whenErrorCommitIsAdded();
		thenHasErrors();
	}

	@Test
	public void testCommitAddsTest(){
		givenNewCodebase();
		whenTestCommitIsAdded();
		thenHasTests();
	}
	
	private void thenHasTests() {
		assertTrue(codebase.hasTests());
	}

	private void whenTestCommitIsAdded() {
		codebase.addCommit(new TestCommit(1));
	}

	private void thenHasErrors() {
		assertTrue(codebase.hasErrors());
	}

	private void whenErrorCommitIsAdded() {
		codebase.addCommit(new ErrorCommit(1));
	}

	private void thenNumberOfFunctionalities(int totalFunctionalities) {
		assertEquals(totalFunctionalities, codebase.getNumberOfFunctionalities());
	}

	private void givenFunctionalityCommitWithId(int functionalityId) {
		codebase.addCommit(new FunctionalityCommit(functionalityId));
	}

	private void thenHasNoErrors() {
		assertFalse(codebase.hasErrors());
	}

	private void givenNewCodebase() {
		codebase = new Codebase();
	}

}
