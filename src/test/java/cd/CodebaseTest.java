package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CodebaseTest {

	Codebase codebase;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emptyCodebaseHasNoErrors() {
		givenNewCodebase();
		thenHasNoErrors();
	}
	
	@Test
	public void functionalCommitAddsFunctionality(){
		givenNewCodebase();
		whenAddingNewFunctionalCommit();
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
		codebase.addCommit(new TestCommit());
	}

	private void thenHasErrors() {
		assertTrue(codebase.hasErrors());
	}

	private void whenErrorCommitIsAdded() {
		codebase.addCommit(new ErrorCommit());
	}

	private void thenNumberOfFunctionalities(int totalFunctionalities) {
		assertEquals(totalFunctionalities, codebase.getNumberOfFunctionalities());
	}

	private void whenAddingNewFunctionalCommit() {
		codebase.addCommit(new FunctionalityCommit());
	}

	private void thenHasNoErrors() {
		assertFalse(codebase.hasErrors());
	}

	private void givenNewCodebase() {
		codebase = new Codebase();
	}

}
