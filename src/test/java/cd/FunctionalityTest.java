package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionalityTest {

	Functionality functionality;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void errorCommitAddsError(){
		givenNewFunctionality();
		givenError();
		thenHasErrors();
	}

	@Test
	public void testDetectsErrors(){
		givenNewFunctionality();
		givenError();
		givenTest();
		thenTestDetectsError();
	}
	
	@Test
	public void errorWithoutTestIsntDetected(){
		givenNewFunctionality();
		givenError();
		thenErrorNotDetected();
	}
	
	@Test
	public void newFunctionalityDoesntHaveOrDetectErrors(){
		givenNewFunctionality();
		thenErrorNotDetected();
		thenNoError();
	}

	private void thenNoError() {
		assertFalse(functionality.hasError());
	}

	private void thenHasErrors() {
		assertTrue(functionality.hasError());
	}

	private void givenError() {
		functionality.addError();
	}

	private void givenNewFunctionality() {
		this.functionality = new Functionality();
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
