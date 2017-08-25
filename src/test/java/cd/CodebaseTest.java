package cd;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class CodebaseTest {

	Codebase codebase;
	Build build;
	
	@Test
	public void emptyCodebaseHasNoErrors() {
		givenNewCodebase();
		whenBuildIsGenerated();
		thenHasNoErrors();
	}
	
	@Test
	public void firstCommitOnFunctionalityAddsFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommit(1);
		whenBuildIsGenerated();
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void differentCommitsWithDifferentIdsAddManyFunctionalities(){
		givenNewCodebase();
		givenFunctionalityCommit(1);
		givenFunctionalityCommit(2);
		givenErrorCommitWithId(3);
		givenTestCommitWithId(4);
		whenBuildIsGenerated();
		thenNumberOfFunctionalities(4);
	}
	
	@Test
	public void secondCommitOnSameFunctionalityDoesntAddFunctionality(){
		givenNewCodebase();
		givenFunctionalityCommit(1);
		givenFunctionalityCommit(1);
		whenBuildIsGenerated();
		thenNumberOfFunctionalities(1);
	}
	
	@Test
	public void hasErrorsIfAnyOfTheFunctionalitiesHasErrors(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		whenBuildIsGenerated();
		thenHasErrors();
	}
	
	@Test
	public void detectsErrorsIfAnyOfTheFunctionalitiesDetectsError(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		givenTestCommitWithId(1);
		whenBuildIsGenerated();
		thenDetectsErrors();
	}
	
	@Test
	public void testForDifferentFunctionalityDoesntDetectError(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		givenTestCommitWithId(2);
		whenBuildIsGenerated();
		thenErrorNotDetected();
		thenHasErrors();
	}
	
	@Test
	public void tellsWhichFunctionalityIdsHaveErrors(){
		givenNewCodebase();
		givenErrorCommitWithId(1);
		givenTestCommitWithId(1);
		givenErrorCommitWithId(20);
		givenTestCommitWithId(20);
		whenBuildIsGenerated();
		Set<Integer> errorFunctionalityIds = new HashSet<Integer>();
		errorFunctionalityIds.add(1);
		errorFunctionalityIds.add(20);
		thenDetectsErrorsWithIds(errorFunctionalityIds);
	}
	
	@Test
	public void setsUntestedFunctionalitiesInBuild(){
		givenNewCodebase();
		givenFunctionalityCommit(1, 10);
		givenFunctionalityCommit(2, 20);
		givenFunctionalityCommit(3, 30);
		givenFunctionalityCommit(4, 40);
		givenTestCommitWithId(1);
		givenTestCommitWithId(3);
		whenBuildIsGenerated();
		Set<Integer> untestedFunctionalityIds = new HashSet<Integer>();
		untestedFunctionalityIds.add(2);
		untestedFunctionalityIds.add(4);
		thenFindsUntestedFeaturesWithIds(untestedFunctionalityIds);
	}
	
	private void thenFindsUntestedFeaturesWithIds(Set<Integer> untestedFunctionalityIds) {
		Set<Functionality> untestedFunctionalities = build.getUntestedFunctionalities();
		assertEquals(untestedFunctionalities.size(), untestedFunctionalityIds.size());
		for(Functionality untestedFunctionality : untestedFunctionalities){
			assertTrue(untestedFunctionalityIds.contains(untestedFunctionality.getId()));				
		}
	}

	private void whenBuildIsGenerated() {
		build = codebase.build();
	}

	private void thenDetectsErrorsWithIds(Set<Integer> functionalityIdsWithErrors) {
		Set<Functionality> detectedErrors = build.getDetectedErrors();
		for(Functionality functionalityWithError : detectedErrors){
			assertTrue(functionalityIdsWithErrors.contains(functionalityWithError.getId()));				
		}
	}

	@Test
	public void valueOfACodebaseIsTheSumOfTheValuesOfAllItsFunctionalities(){
		givenNewCodebase();
		givenFunctionalityCommit(1, 1000);
		givenFunctionalityCommit(1, 500);
		givenFunctionalityCommit(2, 1000);
		givenErrorCommitWithId(2);
		whenBuildIsGenerated();
		thenValue(1500);
	}
	
	@Test
	public void valueOfACodebaseIsZeroWhenFatalError(){
		givenNewCodebase();
		givenFunctionalityCommit(1, 1000);
		givenFunctionalityCommit(2, 1000);
		givenFatalErrorCommit(2);
		whenBuildIsGenerated();
		thenValue(0);	
	}
	
	@Test
	public void codebaseHasValueWhenAllFatalErrorsAreFixed(){
		givenNewCodebase();
		givenFunctionalityCommit(1, 1000);
		givenFunctionalityCommit(2, 1000);
		givenFatalErrorCommit(1);
		givenFatalErrorCommit(2);
		givenFixCommit(1);
		givenFixCommit(2);
		whenBuildIsGenerated();
		thenValue(2000);
	}
	
	@Test
	public void testExecutionTimeIsTheSumOfAllTestsExecutionTimes(){
		givenNewCodebase();
		givenTestCommitWithId(1);
		givenTestCommitWithId(2);
		givenTestCommitWithId(3);
		whenBuildIsGenerated();
		thenTestExecutionTimeIs(30);
	}
	
	@Test
	public void buildsShouldHaveIncrementalIds(){
		givenNewCodebase();
		whenBuildIsGenerated();
		thenBuildIdIs(1);
		whenBuildIsGenerated();
		thenBuildIdIs(2);
		whenBuildIsGenerated();
		thenBuildIdIs(3);
	}
	
	private void thenBuildIdIs(int id) {
		assertEquals(id, build.getId());
	}

	private void thenTestExecutionTimeIs(int testExecutionTime) {
		assertEquals(testExecutionTime, build.getValidationTime());
	}

	private void givenFixCommit(int functionalityId) {
		codebase.addCommit(new FixCommit(functionalityId));
	}

	private void givenFatalErrorCommit(int functionalityId) {
		codebase.addCommit(new FatalErrorCommit(functionalityId));
	}

	private void thenValue(int value) {
		assertEquals(value, build.getValue());
	}

	private void thenErrorNotDetected() {
		assertFalse(build.detectsErrors());
	}

	private void thenDetectsErrors() {
		assertTrue(build.detectsErrors());
	}

	private void givenTestCommitWithId(int functionalityId) {
		codebase.addCommit(new TestCommit(functionalityId, 10));
	}

	private void thenHasErrors() {
		assertTrue(build.hasErrors());
	}

	private void givenErrorCommitWithId(int functionalityId) {
		codebase.addCommit(new ErrorCommit(functionalityId));
	}

	private void thenNumberOfFunctionalities(int totalFunctionalities) {
		assertEquals(totalFunctionalities, build.getNumberOfFunctionalities());
	}

	private void givenFunctionalityCommit(int functionalityId) {
		codebase.addCommit(new FunctionalityCommit(functionalityId, 1000));
	}

	private void givenFunctionalityCommit(int functionalityId, int value) {
		codebase.addCommit(new FunctionalityCommit(functionalityId, value));
	}

	private void thenHasNoErrors() {
		assertFalse(build.hasErrors());
	}

	private void givenNewCodebase() {
		codebase = new Codebase();
	}

}
