package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cd.commits.ErrorCommit;
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
	
	private void givenFix() {
		functionality.addFix();
	}

	private void thenHasNoError() {
		assertFalse(functionality.hasError());
	}

	private void thenHasError() {
		assertTrue(functionality.hasError());
	}

	private void givenError() {
		functionality.addError();
	}

	private void givenNewFunctionalityCommit() {
		functionality = new Functionality(new FunctionalityCommit(1));
	}

	private void givenNewErrorCommit() {
		functionality = new Functionality(new ErrorCommit(1));
	}

	private void givenNewTestCommit() {
		functionality = new Functionality(new TestCommit(1));
	}

	private void thenTestDetectsError() {
		assertTrue(functionality.isErrorDetected());
	}

	private void givenTest() {
		functionality.addTest();
	}

	private void thenErrorNotDetected() {
		assertFalse(functionality.isErrorDetected());
	}
	

}
