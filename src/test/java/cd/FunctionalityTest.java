package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cd.commits.ErrorCommit;
import cd.commits.FatalErrorCommit;
import cd.commits.FixCommit;
import cd.commits.FunctionalityCommit;
import cd.commits.TestCommit;

public class FunctionalityTest {

	Functionality functionality;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void newFunctionalityFromFunctionalityCommitDoesntHaveOrDetectError(){
		givenNewFunctionalityCommit();
		thenErrorNotDetected();
		thenHasNoError();
	}
	
	@Test
	public void newFunctionalityFromErrorCommitHasErrorThatIsntDetected(){
		givenNewErrorCommit();
		thenErrorNotDetected();
		thenHasError();
	}

	@Test
	public void newFunctionalityFromTestCommitHasNoError(){
		givenNewTestCommit();
		thenHasNoError();
		thenErrorNotDetected();
	}
	
	@Test
	public void errorCommitAddsError(){
		givenNewFunctionalityCommit();
		givenError();
		thenHasError();
	}

	@Test
	public void testDetectsErrors(){
		givenNewFunctionalityCommit();
		givenError();
		givenTest();
		thenTestDetectsError();
	}
	
	@Test
	public void errorWithoutTestIsntDetected(){
		givenNewFunctionalityCommit();
		givenError();
		thenErrorNotDetected();
	}
	
	@Test
	public void testWithoutErrorDoesntDetectError(){
		givenNewFunctionalityCommit();
		givenTest();
		thenErrorNotDetected();
	}
	
	@Test
	public void testAddedBeforeErrorStillDetectsError(){
		givenNewFunctionalityCommit();
		givenTest();
		givenError();
		thenTestDetectsError();
	}
	
	@Test
	public void fixRemovesError(){
		givenNewFunctionalityCommit();
		givenError();
		givenFix();
		thenHasNoError();
	}
	
	@Test
	public void fixedErrorIsNotDetected(){
		givenNewFunctionalityCommit();
		givenError();
		givenFix();
		thenErrorNotDetected();
	}
	
	@Test
	public void valueFromAllFunctionalityCommitsIsAccummulated(){
		givenNewFunctionalityCommit();
		givenFunctionalityCommit();
		thenFunctionalityValueIs(2000);
	}
	
	@Test
	public void valueIsZeroWhenFunctionalityHasError(){
		givenNewFunctionalityCommit();
		givenError();
		thenFunctionalityValueIs(0);
	}
	
	@Test
	public void fixedFunctionalityDoesNotHaveFatalError(){
		givenNewFunctionalityCommit();
		givenFatalError();
		givenFix();
		thenNoFatalError();
	}
	
	@Test
	public void addingFatalErrorCommitGivesFatalErrorToFunctionality(){
		givenNewFunctionalityCommit();
		givenFatalError();
		thenFatalError();
	}
	
	@Test
	public void validationTimeIsTheSumOfAllTestTimes(){
		givenNewTestCommit();
		givenTest();
		givenTest();
		thenValidationTimeIs(30);
	}
	
	private void thenValidationTimeIs(int validationTime) {
		assertEquals(validationTime, functionality.getValidationTime());
	}

	private void thenFatalError() {
		assertTrue(functionality.hasFatalError());
	}

	private void thenNoFatalError() {
		assertFalse(functionality.hasFatalError());
	}

	private void givenFatalError() {
		functionality.addModification(new FatalErrorCommit(1));
	}

	private void thenFunctionalityValueIs(int value) {
		assertEquals(value, functionality.getValue());
	}

	private void givenFix() {
		functionality.addModification(new FixCommit(1));
	}

	private void thenHasNoError() {
		assertFalse(functionality.hasError());
	}

	private void thenHasError() {
		assertTrue(functionality.hasError());
	}

	private void givenError() {
		functionality.addModification(new ErrorCommit(1));
	}

	private void givenNewFunctionalityCommit() {
		functionality = new Functionality(new FunctionalityCommit(1, 1000));
	}
	
	private void givenFunctionalityCommit() {
		functionality.addModification(new FunctionalityCommit(1, 1000));
	}

	private void givenNewErrorCommit() {
		functionality = new Functionality(new ErrorCommit(1));
	}

	private void givenNewTestCommit() {
		functionality = new Functionality(new TestCommit(1, 10));
	}

	private void thenTestDetectsError() {
		assertTrue(functionality.isErrorDetected());
	}

	private void givenTest() {
		functionality.addModification(new TestCommit(1, 10));
	}

	private void thenErrorNotDetected() {
		assertFalse(functionality.isErrorDetected());
	}
	

}
