package cd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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

	@Test
	public void newCodebasePushedIntoPipelineBumpsOutPreviousCodebaseWaitingForTest(){
		Codebase c1 = givenCodebaseWithValidationTime(10);
		Codebase c2 = givenCodebaseWithValidationTime(10);
		Codebase c3 = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c1);
		whenRunThroughPipeline(c2);
		whenRunThroughPipeline(c3);
		thenCodebaseIsIn(c3, Stages.WAITING_FOR_TEST);
	}

	@Test
	public void emptyPipelineDoesntGenerateValue(){
		thenAccumulatedValueIs(0);
	}
	
	@Test
	public void pipelineWithNothingInProdDoesntGenerateValue(){
		Codebase c1 = givenCodebaseWithValidationTime(10);
		Codebase c2 = givenCodebaseWithValidationTime(10);
		whenRunThroughPipeline(c1);
		whenRunThroughPipeline(c2);
		thenAccumulatedValueIs(0);
	}
	
	@Test
	public void pipelineWithCodebaseInProdIncrementsValueByCodebaseValueInEachStep(){
		Codebase c = givenCodebaseWithValidationTimeAndValue(1, 11);
		whenRunThroughPipeline(c);
		givenTimeStepsElapsed(1);
		thenAccumulatedValueIs(0);
		givenTimeStepsElapsed(10);
		thenAccumulatedValueIs(110);
	}
	
	private Codebase givenCodebaseWithValidationTimeAndValue(int validationTime, int value) {
		Codebase c = Mockito.mock(Codebase.class);
		when(c.detectsErrors()).thenReturn(false);
		when(c.getValidationTime()).thenReturn(validationTime);
		when(c.getValue()).thenReturn(value);
		return c;
	}

	private void thenAccumulatedValueIs(int value) {
		assertEquals(value, pipeline.getAccumulatedValue());
	}

	private void thenCodebaseIsIn(Codebase c, Stages stage) {
		assertEquals(stage, pipeline.stageOf(c));
	}

	private void givenTimeStepsElapsed(int i) {
		pipeline.timeStepsElapsed(i);
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
