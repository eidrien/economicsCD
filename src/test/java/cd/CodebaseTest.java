package cd;

import static org.junit.Assert.*;

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
	public void firstCommitOnFunctionalityAddsFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommitWithId(1);
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void differentCommitsWithDifferentIdsAddManyFunctionalities(){
		givenNewCodebase();
		givenFunctionalityCommitWithId(1);
		givenFunctionalityCommitWithId(2);
		givenErrorCommitWithId(3);
		givenTestCommitWithId(4);
		thenNumberOfFunctionalities(4);
	}
	
	@Test
	public void secondCommitOnSameFunctionalityDoesntAddFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommitWithId(1);
		givenFunctionalityCommitWithId(1);
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void hasErrorsIfAnyOfTheFunctionalitiesHasErrors(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		thenHasErrors();
	}
	
	@Test
	public void detectsErrorsIfAnyOfTheFunctionalitiesDetectsError(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		givenTestCommitWithId(1);
		thenDetectsErrors();
	}
	
	@Test
	public void testForDifferentFunctionalityDoesntDetectError(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		givenTestCommitWithId(2);
		thenErrorNotDetected();
		thenHasErrors();
	}
	
	private void thenErrorNotDetected() {
		assertFalse(codebase.detectsErrors());
	}

	private void thenDetectsErrors() {
		assertTrue(codebase.detectsErrors());
	}

	private void givenTestCommitWithId(int functionalityId) {
		codebase.addCommit(new TestCommit(functionalityId));
	}

	private void thenHasErrors() {
		assertTrue(codebase.hasErrors());
	}

	private void givenErrorCommitWithId(int functionalityId) {
		codebase.addCommit(new ErrorCommit(functionalityId));
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
