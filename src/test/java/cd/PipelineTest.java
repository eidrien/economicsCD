package cd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cd.commits.ErrorCommit;
import cd.commits.TestCommit;

public class PipelineTest {

	Pipeline pipeline;
	Codebase codebase;
	
	@Before
	public void setUp() throws Exception {
		pipeline = new Pipeline();
	}
	
	@Test
	public void newCodebaseEntersTestingInmediatelyInEmptyPipeline(){
		Codebase c = givenCodebaseWithValidationTime(0);
		whenRunThroughPipeline(c);
		thenCodebaseIsIn(c, Stages.TEST);
	}

	@Test
	public void codebaseNotDetectingErrorsMakesItToProdAfterRunningAllTests() {
		Codebase c = givenCodebaseWithValidationTime(0);
		whenRunThroughPipeline(c);
		givenTimeStepsElapsed(1);
		thenCodebaseIsIn(c, Stages.PROD);
	}
	
	@Test
	public void newCodebaseWaitsForTestIfItIsAlreadyBusy(){
		Codebase c1 = givenCodebaseWithValidationTime(10);
		Codebase c2 = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c1);
		whenRunThroughPipeline(c2);
		thenCodebaseIsIn(c1, Stages.TEST);
		thenCodebaseIsIn(c2, Stages.WAITING_FOR_TEST);
	}
		
	@Test
	public void codebasePassingValidationsMovesToProd(){
		Codebase c = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c);
		givenTimeStepsElapsed(10);
		thenCodebaseIsIn(c, Stages.PROD);
	}

	@Test
	public void ifNotEnoughTimeHasPassedForValidationToHaveFinishedCodebaseStaysInTest(){
		Codebase c = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c);
		givenTimeStepsElapsed(5);
		thenCodebaseIsIn(c, Stages.TEST);
	}

	
	@Test
	public void codebaseWaitingForTestMovesToTestWhenValidationIsFinished(){
		Codebase c1 = givenCodebaseWithValidationTime(10);
		Codebase c2 = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c1);
		whenRunThroughPipeline(c2);
		givenTimeStepsElapsed(10);
		thenCodebaseIsIn(c2, Stages.TEST);
	}

//	@Test
//	public void codebaseDetectingErrorsGetsBlockedByPipeline(){
//		Codebase c = givenCodebaseDetectingErrors();
//		whenRunThroughPipeline(c);
//		thenProdIsNotReached();
//	}
//	
//	@Test
//	public void codebaseDoesNotRunThroughPipelineWhenItsFull(){
//		Codebase c1 = givenCodebaseWithTests(100);
//		Codebase c2 = givenCodebaseWithTets(100);
//		whenRunThroughPipeline(c1);
//		whenRunThroughPipeline(c2);
//	}
	
	//EmptyPipelineDoesntGenerateValue
	//PipelineWithCodebaseInProdIncreasesValueByCodebaseValue
	
	private void thenCodebaseIsIn(Codebase c, Stages stage) {
		assertEquals(stage, pipeline.stageOf(c));
	}

	private void thenCodebaseIsInProd(Codebase c) {
		assertEquals(Stages.PROD, pipeline.stageOf(c));
	}

	private void givenTimeStepsElapsed(int i) {
		pipeline.timeStepsElapsed(i);
	}

	private void thenCodebaseIsInTest(Codebase c) {
		assertEquals(Stages.TEST, pipeline.stageOf(c));
	}

	private void whenRunThroughPipeline(Codebase c) {
		pipeline.push(c);
	}

	private Codebase givenCodebaseWithValidationTime(int validationTime) {
		Codebase c = Mockito.mock(Codebase.class);
		when(c.detectsErrors()).thenReturn(false);
		when(c.getValidationTime()).thenReturn(validationTime);
		return c;
	}

}
