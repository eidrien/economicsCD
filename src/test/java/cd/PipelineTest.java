package cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PipelineTest {

	Pipeline pipeline;
	
	@Before
	public void setUp() throws Exception {
		pipeline = new Pipeline();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emptyCodebaseMakesItToProd() {
		Codebase codebase = new Codebase();
		whenRunThroughPipeline(codebase);
		thenNoErrorsDetected();
	}

	private void thenNoErrorsDetected() {
		assertFalse(pipeline.hasDetectedErrors());
	}

	private void whenRunThroughPipeline(Codebase codebase) {
		pipeline.validate(codebase);
	}

}
