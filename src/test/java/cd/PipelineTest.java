package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PipelineTest {

	Pipeline pipeline;
	Codebase codebase;
	
	@Before
	public void setUp() throws Exception {
		pipeline = new Pipeline();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emptyCodebaseMakesItToProd() {
		givenNewCodebase();
		whenRunThroughPipeline();
		thenNoErrorsDetected();
	}

	private void givenNewCodebase() {
		codebase = new Codebase();
	}
	
	@Test
	public void codebaseWithErrorsAndNoTestsFailsInProd(){
		givenCodebaseWithErrorsAndNoTests();
		whenRunThroughPipeline();
		thenNoErrorsDetected();
		thenFailsInProd();
	}
	
	@Test
	public void codebaseWithErrorsAndTestsGetsBlockedByPipeline(){
		codebase = new Codebase();
		codebase.addCommit(new ErrorCommit(1));
		codebase.addCommit(new TestCommit(1));
	}

	private void thenFailsInProd() {
		assertTrue(codebase.hasErrors() && !pipeline.hasDetectedErrors());
	}

	private Codebase givenCodebaseWithErrorsAndNoTests() {
		codebase = new Codebase();
		codebase.addCommit(new ErrorCommit(1));
		return codebase;
	}

	private void thenNoErrorsDetected() {
		assertFalse(pipeline.hasDetectedErrors());
	}

	private void whenRunThroughPipeline() {
		pipeline.validate(codebase);
	}

}
