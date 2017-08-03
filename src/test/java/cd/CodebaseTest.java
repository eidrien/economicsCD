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
		givenFunctionalityCommit(1);
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void differentCommitsWithDifferentIdsAddManyFunctionalities(){
		givenNewCodebase();
		givenFunctionalityCommit(1);
		givenFunctionalityCommit(2);
		givenErrorCommitWithId(3);
		givenTestCommitWithId(4);
		thenNumberOfFunctionalities(4);
	}
	
	@Test
	public void secondCommitOnSameFunctionalityDoesntAddFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommit(1);
		givenFunctionalityCommit(1);
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
	
	@Test
	public void valueOfACodebaseWithoutErrorsIsTheSumOfTheValuesOfAllItsFunctionalities(){
		givenNewCodebase();
		givenFunctionalityCommit(1, 1000);
		givenFunctionalityCommit(1, 500);
		givenFunctionalityCommit(2, 1000);
		givenErrorCommitWithId(2);
		thenValue(1500);
	}
	
	private void thenValue(int value) {
		assertEquals(value, codebase.getValue());
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

	private void givenFunctionalityCommit(int functionalityId) {
		codebase.addCommit(new FunctionalityCommit(functionalityId, 1000));
	}

	private void givenFunctionalityCommit(int functionalityId, int value) {
		codebase.addCommit(new FunctionalityCommit(functionalityId, value));
	}

	private void thenHasNoErrors() {
		assertFalse(codebase.hasErrors());
	}

	private void givenNewCodebase() {
		codebase = new Codebase();
	}

}
